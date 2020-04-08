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
    //Intervals that are used to check if the given angle in of() method is valid.
    private final static RightOpenInterval RA_INTERVAL = RightOpenInterval.of(Angle.ofHr(0), Angle.ofHr(24));
    private final static ClosedInterval DEC_INTERVAL = ClosedInterval.of(Angle.ofDeg(-90),Angle.ofDeg(90));

    private EquatorialCoordinates(double rightAscension, double declination) {
        super(rightAscension, declination);
    }

    /**
     * Generates an equatorial coordinate with the given right ascension and declination in radians
     * <p>
     * The right ascension must be between 0 and 2*Pi
     * <p>
     * The declination must be between -Pi/2 and Pi/2
     *
     * @param ra right ascension in radians
     * @param dec declination in radians
     * @return an equatorial coordinate (EquatorialCoordinates)
     * @throws IllegalArgumentException if the inputs are not in the correct interval
     */
    public static EquatorialCoordinates of(double ra, double dec){
        Preconditions.checkInInterval(RA_INTERVAL, ra);
        Preconditions.checkInInterval(DEC_INTERVAL, dec);
        return new EquatorialCoordinates(ra, dec);
    }

    /**
     * Getter for the right ascension in radians
     *
     * @return the right ascension in radians (double)
     */
    public double ra() {
        return super.lon();
    }

    /**
     * Getter for the right ascension in degrees
     *
     * @return the right ascension in degrees (double)
     */
    public double raDeg() {
        return super.lonDeg();
    }

    /**
     * Getter for the right ascension in hours
     *
     * @return the right ascension in hours (double)
     */
    public double raHr(){
        return Angle.toHr(ra());
    }

    /**
     * Getter for the declination in radians
     *
     * @return the declination in radians (double)
     */
    public double dec() {
        return super.lat();
    }

    /**
     * Getter for the declination in degrees
     *
     * @return the declination in degrees (double)
     */
    public double decDeg() {
        return super.latDeg();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(ra=%.4fh, dec=%.4f°)", raHr(), decDeg());
    }
}
