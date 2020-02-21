package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;

/**
 * Represents spherical coordinates in general
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
abstract class SphericalCoordinates {

    private final double longitude;
    private final double latitude;

    /**
     * Constructor for a spherical coordinate. Takes in argument a longitude and a latitude in radians.
     *
     * @param longitude longitude in radians
     * @param latitude latitude in radians
     */
    SphericalCoordinates(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Getter for the longitude in radians
     *
     * @return the longitude in radians
     */
    double lon(){
        return longitude;
    }

    /**
     * Getter for the longitude in degrees
     *
     * @return the longitude in degrees
     */
    double lonDeg(){
        return Angle.toDeg(longitude);
    }

    /**
     * Getter for the latitude in radians
     *
     * @return the latitude in radians
     */
    double lat(){
        return latitude;
    }

    /**
     * Getter for the latitude in degrees
     *
     * @return the latitude in degrees
     */
    double latDeg(){
        return Angle.toDeg(latitude);
    }

    @Override
    public final int hashCode() {
        throw new UnsupportedOperationException("Try to call hashCode in SphericalCoordinates class");
    }

    @Override
    public final boolean equals(Object obj) {
        throw new UnsupportedOperationException("Try to call equals in SphericalCoordinates class");
    }
}
