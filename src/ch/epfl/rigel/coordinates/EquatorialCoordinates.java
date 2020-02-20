package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

public final class EquatorialCoordinates extends SphericalCoordinates {

    private final static RightOpenInterval RA_INTERVAL = RightOpenInterval.of(0, 24);
    private final static ClosedInterval DEC_INTERVAL = ClosedInterval.of(-90,90);

    private EquatorialCoordinates(double rightAscension, double declination) {
        super(rightAscension, declination);
    }

    public static EquatorialCoordinates of(double ra, double dec){
        Preconditions.checkInInterval(RA_INTERVAL, ra);
        Preconditions.checkInInterval(DEC_INTERVAL, ra);
        return new EquatorialCoordinates(Angle.ofHr(ra), Angle.ofDeg(dec));

    }

    public static boolean isValidRa(double ra){
        try {
            Preconditions.checkInInterval(RA_INTERVAL, ra);
        } catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

    public static boolean isValidDec(double dec){
        try {
            Preconditions.checkInInterval(DEC_INTERVAL, dec);
        } catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

    public double ra() {
        return super.lon();
    }

    public double raDeg() {
        return super.lonDeg();
    }

    public double raHr(){
        return Angle.toHr(ra());
    }

    public double dec() {
        return super.lat();
    }

    public double decDeg() {
        return super.latDeg();
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(ra=%.4fh, dec=%.4f°)", raHr(), decDeg());
    }
}
