package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.transform.Transform;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * [description text]
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class SkyCanvasManager {
    //TODO ask if type must be ObservableValue or ObjectBinding for links




    //canvas and mousePosition
    private final ObjectProperty<Canvas> canvas;
    private final ObjectProperty<CartesianCoordinates> mousePosition;

    private final SkyCanvasPainter painter;


    //projection
    private final ObjectProperty<HorizontalCoordinates> center;
    private final ObjectBinding<StereographicProjection> projection;

    //planeToCanvas
    private final DoubleProperty canvasWidth;
    private final DoubleProperty canvasHeight;
    private final DoubleProperty fieldOfView;
    private final DoubleBinding dilatationFactor;
    private final ObjectBinding<Transform> planeToCanvas;

    //observedSky
    private final ObjectProperty<LocalDate> date;
    private final ObjectProperty<LocalTime> time;
    private final ObjectProperty<ZoneId> zone;
    private final ObjectBinding<GeographicCoordinates> observerCoordinates;
    private final ObjectBinding<ObservedSky> observedSky;

    //mouseHorizontalPosition
    private final ObjectBinding<HorizontalCoordinates> mouseHorizontalPosition;
    public final DoubleBinding mouseAzDeg;
    public final DoubleBinding mouseAltDeg; //TODO check if public as online

    public final ObjectBinding<CelestialObject> objectUnderMouse; //TODO check if public as online

    public SkyCanvasManager(StarCatalogue catalogue,
                            DateTimeBean dateTimeBean,
                            ObserverLocationBean observerLocationBean,
                            ViewingParametersBean viewingParametersBean
    ){
        //TODO check for initial value
        mousePosition = new SimpleObjectProperty<>(CartesianCoordinates.of(0,0));

        //projection
        center = viewingParametersBean.centerProperty(); //TODO should we create getCenter and setCenter ? don't think so
        projection = Bindings.createObjectBinding(()->new StereographicProjection(center.get()),center);

        //planeToCanvas
        //TODO check initial values
        canvasWidth = new SimpleDoubleProperty(1000);
        canvasHeight = new SimpleDoubleProperty(800);
        fieldOfView = new SimpleDoubleProperty(68.4);
        dilatationFactor = Bindings.createDoubleBinding(()->
                canvasWidth.get()/(projection.get().applyToAngle(Angle.ofDeg(fieldOfView.get()))),
                canvasWidth, projection, fieldOfView
        );
        planeToCanvas = Bindings.createObjectBinding(()->
                Transform.affine(
                        dilatationFactor.get(),
                        0,
                        0,
                        -dilatationFactor.get(),
                        canvasWidth.get()/2,
                        canvasHeight.get()/2
                ),
                dilatationFactor, canvasWidth, canvasHeight
        );

        //observedSky
        date = dateTimeBean.dateProperty();
        time = dateTimeBean.timeProperty();
        zone = dateTimeBean.zoneProperty();
        observerCoordinates = observerLocationBean.coordinatesProperty();
        observedSky = Bindings.createObjectBinding(()->
                new ObservedSky(ZonedDateTime.of(date.get(),time.get(),zone.get()),
                        observerCoordinates.get(),
                        projection.get(),
                        catalogue
                ),
                date, time, zone, observerCoordinates, projection
        );



        //mouseHorizontalPosition
        //TODO check bc looks shitty
        mouseHorizontalPosition = Bindings.createObjectBinding(()-> {
            double x = mousePosition.get().x();
            double y = mousePosition.get().y();
            Point2D canvasToPlane = planeToCanvas.get().inverseDeltaTransform(x,y);
            CartesianCoordinates coordinates = CartesianCoordinates.of(canvasToPlane.getX(), canvasToPlane.getY());
            return projection.get().inverseApply(coordinates);
        }, mousePosition, planeToCanvas, projection);
        mouseAzDeg = Bindings.createDoubleBinding(()->mouseHorizontalPosition.get().azDeg(), mouseHorizontalPosition);
        mouseAltDeg = Bindings.createDoubleBinding(()->mouseHorizontalPosition.get().altDeg(), mouseHorizontalPosition);

        //objectUnderMouse
        //TODO check bc looks shitty
        objectUnderMouse = Bindings.createObjectBinding(()-> {
            double x = mousePosition.get().x();
            double y = mousePosition.get().y();
            Point2D canvasToPlane = planeToCanvas.get().inverseDeltaTransform(x,y);
            CartesianCoordinates coordinates = CartesianCoordinates.of(canvasToPlane.getX(), canvasToPlane.getY());
            return observedSky.get().objectClosestTo(coordinates ,10); //TODO put '10' in a static variable
        }, mousePosition, planeToCanvas, observedSky);

        //canvas and painter
        canvas = new SimpleObjectProperty<>(new Canvas(canvasWidth.get(), canvasHeight.get()));
        painter = new SkyCanvasPainter(canvas.get());
        drawSky(painter);

        //listeners
        canvas.get().setOnMouseMoved(e -> setMousePosition(CartesianCoordinates.of(e.getX(),e.getY())));
        canvas.get().setOnMousePressed(e->{
            if (e.isPrimaryButtonDown()){
                canvas.get().requestFocus();
            }
        });
        canvas.get().setOnScroll(e->{
            if (Math.abs(e.getDeltaX()) >= Math.abs(e.getDeltaY())){
                setFieldOfView(e.getDeltaX());
            } else {
                setFieldOfView(e.getDeltaY());
            }
        });
        canvas.get().setOnKeyPressed(e->{
            //TODO put '5' in a static attribute
            //TODO check for default switch
            switch(e.getCode()){
                case UP:
                    setCenter(HorizontalCoordinates.of(center.get().az(), center.get().alt()+5));
                    break;
                case DOWN:
                    setCenter(HorizontalCoordinates.of(center.get().az(), center.get().alt()-5));
                    break;
                case LEFT:
                    setCenter(HorizontalCoordinates.of(center.get().az()-5, center.get().alt()));
                    break;
                case RIGHT:
                    setCenter(HorizontalCoordinates.of(center.get().az()+5, center.get().alt()));
                    break;
            }
        });

        //drawing listeners
        canvas.addListener((o, oV, nV) -> drawSky(painter));
        center.addListener((o, oV, nV) -> drawSky(painter));
        observedSky.addListener((o, oV, nV) -> drawSky(painter));

/*
        crée un certain nombre de propriétés et liens décrits plus bas,
                installe un auditeur (listener) pour être informé des mouvements du curseur de la souris, et stocker sa position dans une propriété,
        installe un auditeur pour détecter les clics de la souris sur le canevas et en faire alors le destinataire des événements clavier,
        installe un auditeur pour réagir aux mouvements de la molette de la souris et/ou du trackpad et changer le champ de vue en fonction,
                installe un auditeur pour réagir aux pressions sur les touches du curseur et changer le centre de projection en fonction,
                installe des auditeurs pour être informé des changements des liens et propriétés ayant un impact sur le dessin du ciel, et demander dans ce cas au peintre de le redessiner.


 */

    }

    //TODO check if private setters are ok or if we just have to make the set within the code
    private void setMousePosition(CartesianCoordinates mousePosition) {
        this.mousePosition.set(mousePosition);
    }

    public ObjectBinding<CelestialObject> objectUnderMouseProperty() {
        return objectUnderMouse;
    }

    private void setFieldOfView(double fieldOfView) {
        this.fieldOfView.set(fieldOfView);
    }

    public void setCenter(HorizontalCoordinates center) {
        this.center.set(center);
    }

    private void drawSky(SkyCanvasPainter painter){
        painter.clear();
        painter.drawStars(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawSun(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawMoon(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawPlanets(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawHorizon(projection.get(), planeToCanvas.get());
    }

    public Canvas canvas(){
        return canvas.get();
    }

    /*
    créer le canevas sur lequel le ciel est dessiné,
créer le peintre chargé du dessin du ciel et lui faire redessiner le ciel chaque fois que cela est nécessaire,
changer le champ de vue lorsque l'utilisateur manipule la molette de la souris et/ou le trackpad,
changer le centre de projection lorsque l'utilisateur appuie sur les touches du curseur,
suivre les mouvements de la souris et exporter, via des propriétés, la position de son curseur dans le système de coordonnées horizontal, et l'objet céleste le plus proche de ce curseur.
     */
}
