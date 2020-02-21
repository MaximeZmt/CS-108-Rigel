package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

/**
 * Final Class that allow us to create EclipticCoordinates
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class EclipticCoordinates extends SphericalCoordinates {

    private final static RightOpenInterval LONGITUDE_INTERVAL = RightOpenInterval.of(Angle.ofDeg(0),Angle.ofDeg(360));
    private final static ClosedInterval LATITUDE_INTERVAL = ClosedInterval.of(Angle.ofDeg(-90),Angle.ofDeg(90));

    /** //TODO Comment the private constructor ?
     * Private Constructor that Call the super constructor that the class extends
     * @param longitude Angle in radian
     * @param latitude Angle in radian
     */
    private EclipticCoordinates(double longitude, double latitude) {
        super(longitude, latitude);
    }

    /**
     * Method that allow us to instantiate EclipticCoordinates and to create some. It calls the private constructor above.
     * @param lon component 1  of an Ecliptic Coordinates, in radian
     * @param lat component 2 of an Ecliptic Coordinates, in radian also.
     * @return an object of type EclipticCoordinates that has been built with the two arguments
     */
    public static EclipticCoordinates of(double lon, double lat){ //TODO Ask for interval
        // lat -90 90
        //long from 0 to 360
        Preconditions.checkInInterval(LONGITUDE_INTERVAL,lon);
        Preconditions.checkInInterval(LATITUDE_INTERVAL,lat);
        return new EclipticCoordinates(lon, lat);
    }

    /**
     * Get the longitude of some EclipticCoordinates
     * @return longitude in radian
     */
    public double lon(){
        return lon();
    }

    /**
     * Get the longitude of some EclipticCoordinates
     * @return longitude in degree
     */
    public double lonDeg(){
        return lonDeg();
    }

    /**
     * Get the latitude of some EclipticCoordinates
     * @return latitude in radian
     */
    public double lat(){
        return lat();
    }

    /**
     * Get the latitude of some EclipticCoordinates
     * @return latitude in degree
     */
    public double latDeg(){
        return latDeg();
    }

    /**
     * Get a String representation of the coordinates
     * @return a String containing the EclipticCoordinates
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(λ=%.4f°, β=%.4f°)", lonDeg(), latDeg());
    }
}
