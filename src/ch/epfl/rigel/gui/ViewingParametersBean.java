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

    public double getFieldOfViewDeg() {
        return fieldOfViewDeg.get();
    }
    public DoubleProperty fieldOfViewDegProperty() {
        return fieldOfViewDeg;
    }
    public void setFieldOfViewDeg(double fieldOfViewDeg) {
        this.fieldOfViewDeg.set(fieldOfViewDeg);
    }

    public HorizontalCoordinates getCenter() {
        return center.get();
    }
    public ObjectProperty<HorizontalCoordinates> centerProperty() {
        return center;
    }
    public void setCenter(HorizontalCoordinates center) {
        this.center.set(center);
    }

}
