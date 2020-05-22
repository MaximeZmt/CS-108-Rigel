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
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Transform;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * Represents the manager of the observed sky of the canvas
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class SkyCanvasManager {
    private final static int CENTER_VERTICAL_CHANGER = 5;
    private final static int CENTER_HORIZONTAL_CHANGER = 10;
    private final static double MAX_DISTANCE_FOR_OBJECT_UNDER_MOUSE = 10;
    private final static double MAX_FIELD_OF_VIEW = 150;
    private final static double MIN_FIELD_OF_VIEW = 30;
    private final static double MIN_ALTITUDE = 5;
    private final static double MAX_ALTITUDE = 90;
    private final static double MIN_AZIMUTH = 0;
    private final static double MAX_AZIMUTH = 360;

    private final SkyCanvasPainter painter;
    private final Map<KeyCode,int[]> centerCoordinateChanger;

    //canvas and mousePosition
    private final Canvas canvas;
    private final ObjectProperty<CartesianCoordinates> mousePosition;

    //projection
    private final ObjectBinding<StereographicProjection> projection;

    //planeToCanvas
    private final DoubleProperty fieldOfViewDeg;
    private final DoubleBinding dilatationFactor;
    private final ObjectBinding<Transform> planeToCanvas;

    //observedSky
    private final ObjectProperty<LocalDate> date;
    private final ObjectProperty<LocalTime> time;
    private final ObjectProperty<ZoneId> zone;
    private final DoubleProperty observerLatDeg;
    private final DoubleProperty observerLonDeg;
    private final ObjectBinding<GeographicCoordinates> observerCoordinates;
    private final ObjectBinding<ObservedSky> observedSky;

    //mouseHorizontalPosition
    private final ObjectBinding<HorizontalCoordinates> mouseHorizontalPosition;
    private final DoubleBinding mouseAzDeg;
    private final DoubleBinding mouseAltDeg;

    private final ObjectBinding<CelestialObject> objectUnderMouse;

    /**
     * Constructor
     *
     * @param catalogue star catalogue
     * @param dateTimeBean date time bean
     * @param observerLocationBean observer location bean
     * @param viewingParametersBean viewing parameters bean
     */
    public SkyCanvasManager(StarCatalogue catalogue,
                            DateTimeBean dateTimeBean,
                            ObserverLocationBean observerLocationBean,
                            ViewingParametersBean viewingParametersBean
    ){
        centerCoordinateChanger = Map.of(
                KeyCode.UP, new int[]{0,CENTER_VERTICAL_CHANGER},
                KeyCode.RIGHT, new int[]{CENTER_HORIZONTAL_CHANGER,0},
                KeyCode.DOWN, new int[]{0,-CENTER_VERTICAL_CHANGER},
                KeyCode.LEFT, new int[]{-CENTER_HORIZONTAL_CHANGER,0});

        canvas = new Canvas();
        painter = new SkyCanvasPainter(canvas);
        mousePosition = new SimpleObjectProperty<>(CartesianCoordinates.of(0,0));

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
        observerLatDeg = observerLocationBean.latDegProperty();
        observerLonDeg = observerLocationBean.lonDegProperty();
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
        mouseHorizontalPosition = Bindings.createObjectBinding(()-> {
            double x = mousePosition.get().x();
            double y = mousePosition.get().y();
            //try catch because height and width of canvas are 0 and inverseDeltaTransform is impossible
            try {
                Point2D canvasToPlane = planeToCanvas.get().inverseTransform(x,y);
                CartesianCoordinates coordinates = CartesianCoordinates.of(canvasToPlane.getX(), canvasToPlane.getY());
                return projection.get().inverseApply(coordinates);
            } catch (NonInvertibleTransformException e){
                //initial value assignment
                return HorizontalCoordinates.ofDeg(0,0);
            }
        }, mousePosition, planeToCanvas, projection);
        mouseAzDeg = Bindings.createDoubleBinding(()->mouseHorizontalPosition.get().azDeg(), mouseHorizontalPosition);
        mouseAltDeg = Bindings.createDoubleBinding(()->mouseHorizontalPosition.get().altDeg(), mouseHorizontalPosition);

        //objectUnderMouse
        objectUnderMouse = Bindings.createObjectBinding(()-> {
            double x = mousePosition.get().x();
            double y = mousePosition.get().y();
            //try catch because height and width of canvas are 0 and inverseDeltaTransform is impossible
            try {
                Point2D canvasToPlane = planeToCanvas.get().inverseTransform(x,y);
                CartesianCoordinates coordinates = CartesianCoordinates.of(canvasToPlane.getX(), canvasToPlane.getY());
                double dist = planeToCanvas.get().inverseDeltaTransform(MAX_DISTANCE_FOR_OBJECT_UNDER_MOUSE,0).getX();
                return observedSky.get().objectClosestTo(coordinates ,dist).orElse(null);
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
        canvas.setOnScroll(e->{
            double newFieldOfViewDeg;
            if (Math.abs(e.getDeltaX()) >= Math.abs(e.getDeltaY())){
                newFieldOfViewDeg = fieldOfViewDeg.get()+e.getDeltaX();
            } else {
                newFieldOfViewDeg = fieldOfViewDeg.get()+e.getDeltaY();
            }

            if (MIN_FIELD_OF_VIEW <= newFieldOfViewDeg && newFieldOfViewDeg <= MAX_FIELD_OF_VIEW){
                viewingParametersBean.setFieldOfViewDeg(newFieldOfViewDeg);
            }
            e.consume();
        });
        canvas.setOnKeyPressed(e->{
            if (centerCoordinateChanger.get(e.getCode())!=null){
                double newAzDeg = viewingParametersBean.getCenter().azDeg()+centerCoordinateChanger.get(e.getCode())[0];
                double newAltDeg = viewingParametersBean.getCenter().altDeg()+centerCoordinateChanger.get(e.getCode())[1];
                if (MIN_ALTITUDE <= newAltDeg && newAltDeg <= MAX_ALTITUDE
                        && MIN_AZIMUTH <= newAzDeg && newAzDeg < MAX_AZIMUTH){
                    HorizontalCoordinates newCoordinates = HorizontalCoordinates.ofDeg(newAzDeg, newAltDeg);
                    viewingParametersBean.setCenter(newCoordinates);
                }
                e.consume();
            }
        });

        //drawing listeners
        observedSky.addListener((o, oV, nV) -> drawSky(painter));
        planeToCanvas.addListener((o, oV, nV) -> drawSky(painter));
    }

    /**
     * Getter for the object under mouse property
     *
     * @return object under mouse property
     */
    public ObjectBinding<CelestialObject> objectUnderMouseProperty() {
        return objectUnderMouse;
    }

    /**
     * Getter for the azimuth in degrees of the mouse
     *
     * @return azimuth
     */
    public double getMouseAzDeg() {
        return mouseAzDeg.get();
    }

    /**
     * Getter for the property of the azimuth of the mouse
     *
     * @return azimuth of mouse property
     */
    public DoubleBinding mouseAzDegProperty() {
        return mouseAzDeg;
    }

    /**
     * Getter for the altitude in degrees of the mouse
     *
     * @return altitude
     */
    public double getMouseAltDeg() {
        return mouseAltDeg.get();
    }

    /**
     * Getter for the property of the altitude of the mouse
     *
     * @return altitude of the mouse
     */
    public DoubleBinding mouseAltDegProperty() {
        return mouseAltDeg;
    }

    /**
     * Getter for the field of view in degrees
     *
     * @return field of view
     */
    public double getFieldOfViewDeg() { return fieldOfViewDeg.get(); }

    /**
     * Getter for the property of the field of view
     *
     * @return field of view property
     */
    public DoubleProperty fieldOfViewDegProperty() { return fieldOfViewDeg; }

    /**
     * Getter for the property of the observer latitude
     *
     * @return observer latitude property
     */
    public DoubleProperty observerLatDegProperty() {
        return observerLatDeg;
    }

    /**
     * Getter for the property of the observer longitude
     *
     * @return observer longitude property
     */
    public DoubleProperty observerLonDegProperty() {
        return observerLonDeg;
    }

    /**
     * Getter for the date property
     *
     * @return date property
     */
    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    /**
     * Getter for the time
     *
     * @return time
     */
    public LocalTime getTime() {
        return time.get();
    }

    /**
     * Getter for the time property
     *
     * @return time property
     */
    public ObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    /**
     * Getter for the zone property
     *
     * @return zone property
     */
    public ObjectProperty<ZoneId> zoneProperty() {
        return zone;
    }

    /**
     * Setter for the date
     *
     * @param date date
     */
    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    /**
     * Setter for the time
     *
     * @param time time
     */
    public void setTime(LocalTime time) {
        this.time.set(time);
    }

    private void drawSky(SkyCanvasPainter painter){
        painter.clear();
        painter.drawStars(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawSun(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawMoon(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawPlanets(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawHorizon(projection.get(), planeToCanvas.get());
    }

    /**
     * returns the current canvas
     *
     * @return canvas
     */
    public Canvas canvas(){
        return canvas;
    }
}
