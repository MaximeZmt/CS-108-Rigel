package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.Polynomial;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Represents a sidereal time
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class SiderealTime {
    private final static Polynomial S0 = Polynomial.of(0.000025862,2400.051336, 6.697374558);
    private final static double S1 = 1.002737909;
    private final static double MILLISEC_PER_HOURS_FACTOR = 3600000;

    private SiderealTime(){}

    /*
     Class Formula are in Hr. We want to work with radian
     so we use two method withZoneSameInstant and truncatedTo
     */

    /**
     * Computes the siderealTime (all related to Greenwich even timezone) knowing when in greenwich
     *
     * @param when date, time and zone
     * @return SiderealTime in radian in [0;2Pi[ (double)
     */
    public static double greenwich(ZonedDateTime when){
        when = when.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime truncatedDays = when.truncatedTo(ChronoUnit.DAYS);
        double centuriesUntil = Epoch.J2000.julianCenturiesUntil(truncatedDays);
        double deltaHours = truncatedDays.until(when, ChronoUnit.MILLIS) / MILLISEC_PER_HOURS_FACTOR;

        double s = S0.at(centuriesUntil) + S1 * deltaHours;
        return Angle.normalizePositive(Angle.ofHr(s));
    }

    /**
     * Computes the local sidereal time in radian in interval [0,2Pi[ for date/hour and position
     *
     * @param when date, time and zone
     * @param where geographic location
     * @return SiderealTime in radian in [0;2Pi[ (double)
     */
    public static double local(ZonedDateTime when, GeographicCoordinates where){
        return Angle.normalizePositive(greenwich(when) + where.lon());
    }
}
