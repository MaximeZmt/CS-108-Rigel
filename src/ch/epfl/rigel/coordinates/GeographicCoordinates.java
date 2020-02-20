package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

public final class GeographicCoordinates extends SphericalCoordinates {

    private final static RightOpenInterval LON_INTERVAL = RightOpenInterval.of(-180, 180);
    private final static ClosedInterval LAT_INTERVAL = ClosedInterval.of(-90,90);


    private GeographicCoordinates(double longitude, double latitude) {
        super(longitude, latitude);
    }

    public static GeographicCoordinates ofDeg(double lonDeg, double latDeg){
        ClosedInterval latInterval = ClosedInterval.of(-90,90);
        Preconditions.checkInInterval(LON_INTERVAL, lonDeg);
        Preconditions.checkInInterval(LAT_INTERVAL, latDeg);
        return new GeographicCoordinates(Angle.ofDeg(lonDeg), Angle.ofDeg(latDeg));
    }
}
