package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.Epoch;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.Polynomial;

import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * Represents a coordinate system transformation from ecliptic to equatorial at a given moment
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class EclipticToEquatorialConversion implements Function<EclipticCoordinates, EquatorialCoordinates> {
    private static final Polynomial EPSILON_POLYNOMIAL = Polynomial.of(
            Angle.ofArcsec(0.00181),
            -Angle.ofArcsec(0.0006),
            -Angle.ofArcsec(46.815),
            Angle.ofDMS(23,26,21.45));
    private final double cosEpsilon;
    private final double sinEpsilon;

    /**
     * Builds a coordinate system transformation between ecliptic and
     * equatorial coordinates for the given date, time and zone
     *
     * @param when date, time and zone
     */
    public EclipticToEquatorialConversion(ZonedDateTime when){
        double epsilon = EPSILON_POLYNOMIAL.at(Epoch.J2000.julianCenturiesUntil(when));
        cosEpsilon = Math.cos(epsilon);
        sinEpsilon = Math.sin(epsilon);
    }

    /**
     * @see Function#apply(Object)
     */
    @Override
    public EquatorialCoordinates apply(EclipticCoordinates ecl) {
        double lon = ecl.lon();
        double lat = ecl.lat();
        double ra = Math.atan2(Math.sin(lon)*cosEpsilon-Math.tan(lat)*sinEpsilon, Math.cos(lon));
        double dec = Math.asin(Math.sin(lat)*cosEpsilon+Math.cos(lat)*sinEpsilon*Math.sin(lon));
        return EquatorialCoordinates.of(Angle.normalizePositive(ra), dec);
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Called hashCode from EclipticToEquatorialConversion");
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Called equals from EclipticToEquatorialConversion");
    }
}
