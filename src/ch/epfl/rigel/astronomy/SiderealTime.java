package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
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
     * compute the siderealTime (all related to Greenwich even timezone) knowing when in greenwich
     * @param when
     * @return SiderealTime in radian in [0;pi[
     */
    static double greenwich(ZonedDateTime when){
        when = when.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime trucatedDays = when.truncatedTo(ChronoUnit.DAYS);
        int daysUtil = (int) Epoch.J2000.daysUntil(trucatedDays)/36525;
        double deltaHours = trucatedDays.until(when, ChronoUnit.MILLIS)/3600000;
        Polynomial s0 = Polynomial.of(0.000025862,2400.051336,6.697374558);
        Polynomial s1 = Polynomial.of(1.002737909);
        RightOpenInterval i0to24h = RightOpenInterval.of(0,24);
        double reducedS0 = i0to24h.reduce(s0.at(daysUtil)+s1.at(deltaHours));
        return reducedS0; // miss radian conversion
    }

    /**
     * compute the local sidereal time in radian in interval [0,PI[ for date/hour and position
     * @param when
     * @param where
     * @return SiderealTime in radian in [0;pi[
     */
    static double local(ZonedDateTime when, GeographicCoordinates where){
        return 0;
    }

}
