package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * JavaFx Bean containing the viewing parameters defining which portion of the sky is visible
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class ViewingParametersBean {
    private final DoubleProperty fieldOfViewDeg = new SimpleDoubleProperty(0);
    private final ObjectProperty<HorizontalCoordinates> center = new SimpleObjectProperty<>(null);

    /**
     * Getter for the field of view property
     *
     * @return field of view property
     */
    public DoubleProperty fieldOfViewDegProperty() {
        return fieldOfViewDeg;
    }

    /**
     * Setter for the field of view in degrees
     *
     * @param fieldOfViewDeg field of view
     */
    public void setFieldOfViewDeg(double fieldOfViewDeg) {
        this.fieldOfViewDeg.set(fieldOfViewDeg);
    }

    /**
     * Getter for the center  property
     *
     * @return center property
     */
    public ObjectProperty<HorizontalCoordinates> centerProperty() {
        return center;
    }

    /**
     * Getter for the center
     *
     * @return center
     */
    public HorizontalCoordinates getCenter() {
        return center.get();
    }

    /**
     * Setter for the center
     *
     * @param center center
     */
    public void setCenter(HorizontalCoordinates center) {
        this.center.set(center);
    }
}
