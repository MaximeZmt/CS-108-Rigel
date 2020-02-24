package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Polynomial;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class SiderealTime {

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
        double daysUtil = Epoch.J2000.daysUntil(trucatedDays);
        double deltaHours = 
        Polynomial s0 = Polynomial.of(0.000025862,2400.051336,6.697374558);
        Polynomial s1 = Polynomial.of(1.002737909);
        return 0;
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
