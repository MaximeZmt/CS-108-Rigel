package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

/**
 * Represents ecliptic coordinates
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class EclipticCoordinates extends SphericalCoordinates {

    private final static RightOpenInterval LONGITUDE_INTERVAL = RightOpenInterval.of(Angle.ofDeg(0),Angle.ofDeg(360));
    private final static ClosedInterval LATITUDE_INTERVAL = ClosedInterval.of(Angle.ofDeg(-90),Angle.ofDeg(90));

    private EclipticCoordinates(double longitude, double latitude) {
        super(longitude, latitude);
    }

    /**
     * Generates an ecliptic coordinate with the given longitude and latitude in radians
     * <p>
     * The longitude must be between 0 and 2*Pi
     * <p>
     * The latitude must be between -Pi/2 and Pi/2
     *
     * @param lon longitude in radians
     * @param lat latitude in radians
     * @return an ecliptic coordinates (EclipticCoordinates)
     * @throws IllegalArgumentException if the inputs are not in the correct interval
     */
    public static EclipticCoordinates of(double lon, double lat){
        Preconditions.checkInInterval(LONGITUDE_INTERVAL,lon);
        Preconditions.checkInInterval(LATITUDE_INTERVAL,lat);
        return new EclipticCoordinates(lon, lat);
    }

    @Override
    public double lon(){
        return super.lon();
    }

    @Override
    public double lonDeg(){
        return super.lonDeg();
    }

    @Override
    public double lat(){
        return super.lat();
    }

    @Override
    public double latDeg(){
        return super.latDeg();
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(λ=%.4f°, β=%.4f°)", lonDeg(), latDeg());
    }
}
