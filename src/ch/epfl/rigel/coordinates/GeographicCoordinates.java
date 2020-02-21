package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

/**
 * Represents geographic coordinates
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */

public final class GeographicCoordinates extends SphericalCoordinates {

    private final static RightOpenInterval LON_INTERVAL = RightOpenInterval.of(-180, 180);
    private final static ClosedInterval LAT_INTERVAL = ClosedInterval.of(-90,90);

    private GeographicCoordinates(double longitude, double latitude) {
        super(longitude, latitude);
    }

    /**
     * Generates a geographical coordinate with the given longitude and latitude in degrees.
     * <p>
     * the longitude must be between -180° and 180°
     * <p>
     * the latitude must be between -90° and 90°
     *
     *
     * @param lonDeg  longitude in degrees
     * @param latDeg  latitude in degrees
     * @return a geographic coordinate
     * @throws IllegalArgumentException if the inputs are not in the correct interval
     */
    public static GeographicCoordinates ofDeg(double lonDeg, double latDeg){
        Preconditions.checkInInterval(LON_INTERVAL, lonDeg);
        Preconditions.checkInInterval(LAT_INTERVAL, latDeg);
        return new GeographicCoordinates(Angle.ofDeg(lonDeg), Angle.ofDeg(latDeg));
    }

    /**
     * Checks if the given longitude in degrees is in the interval -180° and 180°
     *
     * @param lonDeg  the longitude to be checked
     * @return a boolean
     */
    public static boolean isValidLonDeg(double lonDeg){
        try {
            Preconditions.checkInInterval(LON_INTERVAL, lonDeg);
        } catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

    /**
     * Checks if the given latitude in degrees is in the interval -90° and 90°
     *
     * @param latDeg  the latitude to be checked
     * @return a boolean
     */
    public static boolean isValidLatDeg(double latDeg){
        try {
            Preconditions.checkInInterval(LAT_INTERVAL, latDeg);
        } catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

    @Override
    public double lon() {
        return super.lon();
    }

    @Override
    public double lonDeg() {
        return super.lonDeg();
    }

    @Override
    public double lat() {
        return super.lat();
    }

    @Override
    public double latDeg() {
        return super.latDeg();
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(lon=%.4f°, lat=%.4f°)", lonDeg(), latDeg());
    }
}
