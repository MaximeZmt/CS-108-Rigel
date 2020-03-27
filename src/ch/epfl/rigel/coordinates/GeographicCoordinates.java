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
    private final static RightOpenInterval LON_INTERVAL = RightOpenInterval.of(Angle.ofDeg(-180), Angle.ofDeg(180));
    private final static ClosedInterval LAT_INTERVAL = ClosedInterval.of(Angle.ofDeg(-90),Angle.ofDeg(90));
    /*
    as the of() method that is used to build new Geographic coordinates receive angle, we set the two
    intervals that we accept in order to check if the given angle are in the interval
     */

    private GeographicCoordinates(double longitude, double latitude) {
        super(longitude, latitude);
    }

    /**
     * Generates a geographical coordinate with the given longitude and latitude in degrees.
     * <p>
     * The longitude must be between -180° and 180°
     * <p>
     * The latitude must be between -90° and 90°
     *
     * @param lonDeg longitude in degrees
     * @param latDeg latitude in degrees
     * @return a geographic coordinate (GeographicCoordinates)
     * @throws IllegalArgumentException if the inputs are not in the correct interval
     */
    public static GeographicCoordinates ofDeg(double lonDeg, double latDeg){
        Preconditions.checkInInterval(LON_INTERVAL, Angle.ofDeg(lonDeg));
        Preconditions.checkInInterval(LAT_INTERVAL, Angle.ofDeg(latDeg));
        return new GeographicCoordinates(Angle.ofDeg(lonDeg), Angle.ofDeg(latDeg));
    }

    /**
     * Checks if the given longitude in degrees is in the interval -180° and 180° (-Pi and Pi)
     *
     * @param lonDeg the longitude to be checked
     * @return (boolean)
     */
    public static boolean isValidLonDeg(double lonDeg){
        try {
            Preconditions.checkInInterval(LON_INTERVAL, Angle.ofDeg(lonDeg));
        } catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

    /**
     * Checks if the given latitude in degrees is in the interval -90° and 90° (-Pi/2 and Pi/2)
     *
     * @param latDeg the latitude to be checked
     * @return (boolean)
     */
    public static boolean isValidLatDeg(double latDeg){
        try {
            Preconditions.checkInInterval(LAT_INTERVAL, Angle.ofDeg(latDeg));
        } catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

    /**
     * @see SphericalCoordinates#lon()
     */
    @Override
    public double lon() {
        return super.lon();
    }

    /**
     * @see SphericalCoordinates#lonDeg()
     */
    @Override
    public double lonDeg() {
        return super.lonDeg();
    }


    /**
     * @see SphericalCoordinates#lat()
     */
    @Override
    public double lat() {
        return super.lat();
    }

    /**
     * @see SphericalCoordinates#latDeg()
     */
    @Override
    public double latDeg() {
        return super.latDeg();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(lon=%.4f°, lat=%.4f°)", lonDeg(), latDeg());
    }
}
