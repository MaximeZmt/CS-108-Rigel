package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.Polynomial;
import ch.epfl.rigel.math.RightOpenInterval;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public final class SiderealTime {

    /*
    Class Formula are in Hr -> want to work with radian
     Use two method withZoneSameInstant and truncatedTo
     */


    /**
     * Computes the siderealTime (all related to Greenwich even timezone) knowing when in greenwich
     *
     * @param when date, time and zone
     * @return SiderealTime in radian in [0;2Pi[ (double)
     */
    public static double greenwich(ZonedDateTime when){
        when = when.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime truncatedDays = when.truncatedTo(ChronoUnit.DAYS);
        double centuriesUntil = Epoch.J2000.julianCenturiesUntil(truncatedDays);
        double deltaHours = truncatedDays.until(when, ChronoUnit.MILLIS)/3600000.;
        Polynomial s0 = Polynomial.of(0.000025862,2400.051336,6.697374558);
        Polynomial s1 = Polynomial.of(1.002737909,0);
        double s = Angle.ofHr(s0.at(centuriesUntil)+s1.at(deltaHours));
        return Angle.normalizePositive(s);

    }

    /**
     * Computes the local sidereal time in radian in interval [0,2Pi[ for date/hour and position
     *
     * @param when date, time and zone
     * @param where geographic location
     * @return SiderealTime in radian in [0;2Pi[ (double)
     */
    public static double local(ZonedDateTime when, GeographicCoordinates where){
        return Angle.normalizePositive(greenwich(when)+where.lon());
    }

}
