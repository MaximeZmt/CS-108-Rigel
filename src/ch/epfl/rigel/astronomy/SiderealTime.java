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
     * compute the siderealTime (all related to Greenwich even timezone) knowing when in greenwich
     * @param when
     * @return SiderealTime in radian in [0;pi[
     */
    public static double greenwich(ZonedDateTime when){
        when = when.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime truncatedDays = when.truncatedTo(ChronoUnit.DAYS);
        double centuriesUntil = Epoch.J2000.julianCenturiesUntil(truncatedDays);
        //System.out.println(centuriesUntil); // correct
        double deltaHours = truncatedDays.until(when, ChronoUnit.MILLIS)/3600000.;
        //System.out.println(deltaHours); // more or less correct
        Polynomial s0 = Polynomial.of(0.000025862,2400.051336,6.697374558);
        Polynomial s1 = Polynomial.of(1.002737909,0);
        double s0V = s0.at(centuriesUntil);
        //System.out.println(s0V); //check
        double s1V = s1.at(deltaHours);
        //System.out.println(s1V);
        RightOpenInterval i0to24h = RightOpenInterval.of(0,24);
        double reducedS0 = i0to24h.reduce(s0V+s1V);
        return Angle.ofHr(reducedS0); // miss radian conversion
    }

    /**
     * compute the local sidereal time in radian in interval [0,PI[ for date/hour and position
     * @param when
     * @param where
     * @return SiderealTime in radian in [0;pi[
     */
    public static double local(ZonedDateTime when, GeographicCoordinates where){
        return greenwich(when)+where.lon();
    }

}
