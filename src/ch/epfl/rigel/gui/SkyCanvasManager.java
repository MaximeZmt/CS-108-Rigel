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
import javafx.scene.input.KeyCode;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Transform;

import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * [description text]
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class SkyCanvasManager {




    //canvas and mousePosition
    private final Canvas canvas;
    private final ObjectProperty<CartesianCoordinates> mousePosition;

    private final SkyCanvasPainter painter;

    private final Map<KeyCode,int[]> centerCoordinateChanger;


    //projection
    //private final ObjectProperty<HorizontalCoordinates> center;
    private final ObjectBinding<StereographicProjection> projection;

    //planeToCanvas
    //private final DoubleProperty canvasWidth;
    //private final DoubleProperty canvasHeight;
    private final DoubleProperty fieldOfViewDeg;
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
    private final DoubleBinding mouseAzDeg;
    private final DoubleBinding mouseAltDeg; //TODO check if public as online -> privé mais accesseur

    private final ObjectBinding<CelestialObject> objectUnderMouse; //TODO check if public as online

    public SkyCanvasManager(StarCatalogue catalogue,
                            DateTimeBean dateTimeBean,
                            ObserverLocationBean observerLocationBean,
                            ViewingParametersBean viewingParametersBean
    ){
        centerCoordinateChanger = Map.of(
                KeyCode.UP, new int[]{0,5},
                KeyCode.RIGHT, new int[]{10,0},
                KeyCode.DOWN, new int[]{0,-5},
                KeyCode.LEFT, new int[]{-10,0});

        //TODO check for initial value
        //canvas and painter
        canvas = new Canvas();
        //canvas.setHeight(500);
        //canvas.setWidth(500);
        painter = new SkyCanvasPainter(canvas);
        mousePosition = new SimpleObjectProperty<>(CartesianCoordinates.of(0,0));

        //projection
        //center = viewingParametersBean.centerProperty();
        projection = Bindings.createObjectBinding(()->
                new StereographicProjection(viewingParametersBean.getCenter()),
                viewingParametersBean.centerProperty()
        );

        //planeToCanvas
        fieldOfViewDeg = viewingParametersBean.fieldOfViewDegProperty();
        dilatationFactor = Bindings.createDoubleBinding(()->
                canvas.widthProperty().get()/(projection.get().applyToAngle(Angle.ofDeg(fieldOfViewDeg.get()))),
                canvas.widthProperty(), projection, fieldOfViewDeg
        );
        planeToCanvas = Bindings.createObjectBinding(()->
                Transform.affine(
                        dilatationFactor.get(),
                        0,
                        0,
                        -dilatationFactor.get(),
                        canvas.widthProperty().get()/2,
                        canvas.heightProperty().get()/2
                ),
                dilatationFactor, canvas.widthProperty(), canvas.heightProperty()
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
            //try catch because height and width of canvas are 0 and inverseDeltaTransform is impossible
            try {
                Point2D canvasToPlane = planeToCanvas.get().inverseDeltaTransform(x,y);
                CartesianCoordinates coordinates = CartesianCoordinates.of(canvasToPlane.getX(), canvasToPlane.getY());
                return projection.get().inverseApply(coordinates);
            } catch (NonInvertibleTransformException e){
                return null;
            }
        }, mousePosition, planeToCanvas, projection);
        mouseAzDeg = Bindings.createDoubleBinding(()->mouseHorizontalPosition.get().azDeg(), mouseHorizontalPosition);
        mouseAltDeg = Bindings.createDoubleBinding(()->mouseHorizontalPosition.get().altDeg(), mouseHorizontalPosition);

        //objectUnderMouse
        //TODO check bc looks shitty
        //TODO FIXME objects under mouse are not the correct ones
        objectUnderMouse = Bindings.createObjectBinding(()-> {
            double x = mousePosition.get().x();
            double y = mousePosition.get().y();
            //try catch because height and width of canvas are 0 and inverseDeltaTransform is impossible
            try {
                Point2D canvasToPlane = planeToCanvas.get().inverseTransform(x,y); //TODO it was inverseTransform and not inversedeltaTransform cause it is a point
                CartesianCoordinates coordinates = CartesianCoordinates.of(canvasToPlane.getX(), canvasToPlane.getY()); //TODO now it's working
                double dist = planeToCanvas.get().deltaTransform(10,0).getX(); //TODO check that
                return observedSky.get().objectClosestTo(coordinates ,dist).get(); //TODO put '10' in a static variable
            } catch (NonInvertibleTransformException e){
                return null;
            }

        }, mousePosition, planeToCanvas, observedSky);

        //listeners
        canvas.setOnMouseMoved(e -> mousePosition.set(CartesianCoordinates.of(e.getX(),e.getY())));
        canvas.setOnMousePressed(e->{
            if (e.isPrimaryButtonDown()){
                canvas.requestFocus();
            }
        });
        //it is normal if scroll is inverted
        //TODO add limit for zoom
        canvas.setOnScroll(e->{
            double newFieldOfViewDeg;
            if (Math.abs(e.getDeltaX()) >= Math.abs(e.getDeltaY())){
                newFieldOfViewDeg = fieldOfViewDeg.get()+e.getDeltaX();
            } else {
                newFieldOfViewDeg = fieldOfViewDeg.get()+e.getDeltaY();
            }

            if (30 <= newFieldOfViewDeg && newFieldOfViewDeg <= 150){
                viewingParametersBean.setFieldOfViewDeg(newFieldOfViewDeg);
            }
            e.consume();
        });
        canvas.setOnKeyPressed(e->{
            //TODO put '5' in a static attribute
            //TODO check for default switch
            //TODO sun and moon (maybe planets) move when changing center of projection -> modifier sunmodel pour voir d ou vient  l erreur
            //TODO use map
            double newAzDeg = viewingParametersBean.getCenter().azDeg()+centerCoordinateChanger.get(e.getCode())[0];
            double newAltDeg = viewingParametersBean.getCenter().altDeg()+centerCoordinateChanger.get(e.getCode())[1];
            if (5 <= newAltDeg && newAltDeg <= 90
            && 0 <= newAzDeg && newAzDeg < 360){
                HorizontalCoordinates newCoordinates = HorizontalCoordinates.ofDeg(newAzDeg, newAltDeg);
                viewingParametersBean.setCenter(newCoordinates);
            }
            e.consume();
        });

        //drawing listeners
        observedSky.addListener((o, oV, nV) -> drawSky(painter));
        planeToCanvas.addListener((o, oV, nV) -> drawSky(painter));

    }

    //TODO check if private setters are ok or if we just have to make the set within the code


    public ObjectBinding<CelestialObject> objectUnderMouseProperty() {
        return objectUnderMouse;
    }

    //TODO is it useful ?
    public Number getMouseAzDeg() {
        return mouseAzDeg.get();
    }

    public DoubleBinding mouseAzDegProperty() {
        return mouseAzDeg;
    }

    public Number getMouseAltDeg() {
        return mouseAltDeg.get();
    }

    public DoubleBinding mouseAltDegProperty() {
        return mouseAltDeg;
    }

    public CelestialObject getObjectUnderMouse() {
        return objectUnderMouse.get();
    }

    public double getFieldOfViewDeg() { return fieldOfViewDeg.get(); }

    public DoubleProperty fieldOfViewDegProperty() { return fieldOfViewDeg; }

    public GeographicCoordinates getObserverCoordinates() {
        return observerCoordinates.get();
    }

    public ObjectBinding<GeographicCoordinates> observerCoordinatesProperty() {
        return observerCoordinates;
    }

    private void drawSky(SkyCanvasPainter painter){
        System.out.println("OUI");
        painter.clear();
        painter.drawStars(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawSun(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawMoon(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawPlanets(observedSky.get(), projection.get(), planeToCanvas.get());
        //TODO horizon doesnt seems to be cruked (tordu)
        painter.drawHorizon(projection.get(), planeToCanvas.get());
    }

    public Canvas canvas(){
        return canvas;
    }

}
