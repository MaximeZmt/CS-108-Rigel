package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

public final class GeographicCoordinates extends SphericalCoordinates {

    private final static RightOpenInterval LON_INTERVAL = RightOpenInterval.of(-180, 180);
    private final static ClosedInterval LAT_INTERVAL = ClosedInterval.of(-90,90);

    private GeographicCoordinates(double longitude, double latitude) {
        super(longitude, latitude);
    }

    public static GeographicCoordinates ofDeg(double lonDeg, double latDeg){
        Preconditions.checkInInterval(LON_INTERVAL, lonDeg);
        Preconditions.checkInInterval(LAT_INTERVAL, latDeg);
        return new GeographicCoordinates(Angle.ofDeg(lonDeg), Angle.ofDeg(latDeg));
    }

    public static boolean isValidLonDeg(double lonDeg){
        try {
            Preconditions.checkInInterval(LON_INTERVAL, lonDeg);
        } catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

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
