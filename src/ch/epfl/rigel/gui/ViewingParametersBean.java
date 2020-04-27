package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * [fillTxt]
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class ViewingParametersBean { // public et instanciable

    private final DoubleProperty fieldOfViewDeg = new SimpleDoubleProperty(0);
    private final ObjectProperty<HorizontalCoordinates> center = new SimpleObjectProperty<>(null);

    public DoubleProperty fieldOfViewDegProperty(){return fieldOfViewDeg;}
    public double getfieldOfViewDeg(){return fieldOfViewDeg.doubleValue();}
    public void setFieldOfViewDeg(double newFieldOfViewDeg){fieldOfViewDeg.setValue(newFieldOfViewDeg);}

    public ObjectProperty<HorizontalCoordinates> centerProperty(){return center;}
    public HorizontalCoordinates getCenter(){return center.get();}
    public void setCenter(HorizontalCoordinates newCenter){center.set(newCenter);}

}
