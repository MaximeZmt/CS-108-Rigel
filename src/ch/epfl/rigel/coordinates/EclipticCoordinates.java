package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;


public final class EclipticCoordinates extends SphericalCoordinates {

    private final static RightOpenInterval LONGITUDE_INTERVAL = RightOpenInterval.of(Angle.ofDeg(0),Angle.ofDeg(360));
    private final static ClosedInterval LATITUDE_INTERVAL = ClosedInterval.of(Angle.ofDeg(-90),Angle.ofDeg(90));


    private EclipticCoordinates(double longitude, double latitude) {
        super(longitude, latitude);
    }

    public static EclipticCoordinates of(double lon, double lat){ //TODO Ask for interval
        // lat -90 90
        //long from 0 to 360
        Preconditions.checkInInterval(LONGITUDE_INTERVAL,lon);
        Preconditions.checkInInterval(LATITUDE_INTERVAL,lat);
        return new EclipticCoordinates(lon, lat);
    }

    public double lon(){
        return lon();
    }

    public double lonDeg(){
        return lonDeg();
    }

    public double lat(){
        return lat();
    }

    public double latDeg(){
        return latDeg();
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(λ=%.4f°, β=%.4f°)", lonDeg(), latDeg());
    }
}
