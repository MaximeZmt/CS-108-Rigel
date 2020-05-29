package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * JavaFx bean containing the observer location in degrees
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class ObserverLocationBean {
    private final DoubleProperty lonDeg = new SimpleDoubleProperty(0);
    private final DoubleProperty latDeg = new SimpleDoubleProperty(0);
    private final ObjectBinding<GeographicCoordinates> coordinates = Bindings.createObjectBinding(() ->
            GeographicCoordinates.ofDeg(lonDeg.get(), latDeg.get()), lonDeg, latDeg);

    /**
     * Getter for the longitude property
     *
     * @return longitude property
     */
    public DoubleProperty lonDegProperty() {
        return lonDeg;
    }

    /**
     * Getter for the longitude in degrees
     *
     * @return longitude in degrees
     */
    public double getLonDeg() {
        return lonDeg.get();
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
     * Getter for the latitude property
     *
     * @return latitude property
     */
    public DoubleProperty latDegProperty() {
        return latDeg;
    }

    /**
     * Getter for the latitude in degrees
     *
     * @return latitude in degrees
     */
    public double getLatDeg() {
        return latDeg.get();
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
     * Getter for the geographic coordinates property
     *
     * @return geographic coordinates property
     */
    public ObjectBinding<GeographicCoordinates> coordinatesProperty() {
        return coordinates;
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
     * Setter for the geographic coordinates
     *
     * @param geographicCoordinates geographic coordinates
     */
    public void setCoordinates(GeographicCoordinates geographicCoordinates){
        setLonDeg(geographicCoordinates.lonDeg());
        setLatDeg(geographicCoordinates.latDeg());
    }
}

