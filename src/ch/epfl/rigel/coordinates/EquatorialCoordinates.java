package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

/**
 * Represents equatorial coordinates
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class EquatorialCoordinates extends SphericalCoordinates {

    private final static RightOpenInterval RA_INTERVAL = RightOpenInterval.of(0, 24);
    private final static ClosedInterval DEC_INTERVAL = ClosedInterval.of(-90,90);

    private EquatorialCoordinates(double rightAscension, double declination) {
        super(rightAscension, declination);
    }

    /**
     * Generates an equatorial coordinate with the given right ascension in hours and declination in degrees
     * <p>
     * the right ascension must be between 0h and 24h
     * <p>
     * the declination must be between -90° and 90°
     *
     * @param ra  right ascension in hours
     * @param dec  declination in degrees
     * @return an equatorial coordinate
     * @throws IllegalArgumentException if the inputs are not in the correct interval
     */
    public static EquatorialCoordinates of(double ra, double dec){
        Preconditions.checkInInterval(RA_INTERVAL, ra);
        Preconditions.checkInInterval(DEC_INTERVAL, dec);
        return new EquatorialCoordinates(Angle.ofHr(ra), Angle.ofDeg(dec));

    }

   /* public static boolean isValidRa(double ra){
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
    }*/

    /**
     * Getter for the right ascension in radians
     *
     * @return the right ascension in radians
     */
    public double ra() {
        return super.lon();
    }

    /**
     * Getter for the right ascension in degrees
     *
     * @return the right ascension in degrees
     */
    public double raDeg() {
        return super.lonDeg();
    }

    /**
     * Getter for the right ascension in hours
     *
     * @return the right ascension in hours
     */
    public double raHr(){
        return Angle.toHr(ra());
    }

    /**
     * Getter for the declination in radians
     *
     * @return the declination in radians
     */
    public double dec() {
        return super.lat();
    }

    /**
     * Getter for the declination in degrees
     *
     * @return the declination in degrees
     */
    public double decDeg() {
        return super.latDeg();
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(ra=%.4fh, dec=%.4f°)", raHr(), decDeg());
    }
}
