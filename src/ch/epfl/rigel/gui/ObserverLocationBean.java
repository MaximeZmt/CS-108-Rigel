package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;

/**
 * JavaFx bean containing the observer location
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class ObserverLocationBean {
    private final DoubleProperty lonDeg = new SimpleDoubleProperty(0);
    private final DoubleProperty latDeg = new SimpleDoubleProperty(0);
    private final ObjectBinding<GeographicCoordinates> coordinates = Bindings.createObjectBinding(()->
            GeographicCoordinates.ofDeg(lonDeg.get(),latDeg.get()),lonDeg,latDeg);

    /**
     * Getter for the longitude in degrees
     *
     * @return longitude
     */
    public double getLonDeg() {
        return lonDeg.get();
    }

    /**
     * Getter for the longitude property
     *
     * @return longitude property
     */
    public DoubleProperty lonDegProperty() {
        return lonDeg;
    }

    /**
     * Setter for the longitude in degrees
     *
     * @param lonDeg longitude in degrees
     */
    public void setLonDeg(double lonDeg) {
        this.lonDeg.set(lonDeg);
    }

    /**
     * Getter for the latitude in degrees
     *
     * @return latitude
     */
    public double getLatDeg() {
        return latDeg.get();
    }

    /**
     * Getter for the latitude property
     *
     * @return latitude property
     */
    public DoubleProperty latDegProperty() {
        return latDeg;
    }

    /**
     * Setter for the latitude in degrees
     *
     * @param latDeg latitude in degrees
     */
    public void setLatDeg(double latDeg) {
        this.latDeg.set(latDeg);
    }

    /**
     * Getter for the geographic coordinates
     *
     * @return geographic coordinates
     */
    public GeographicCoordinates getCoordinates() {
        return coordinates.get();
    }

    /**
     * Getter for the geographic coordinates property
     *
     * @return geographic coordinates property
     */
    public ObjectBinding<GeographicCoordinates> coordinatesProperty() {
        return coordinates;
    }

    /**
     * Setter for the geographic coordinates
     *
     * @param geographicCoordinates geographic coordinates
     */
    public void setCoordinates(GeographicCoordinates geographicCoordinates){
        setLonDeg(geographicCoordinates.lonDeg());
        setLatDeg(geographicCoordinates.latDeg());
    }
}

