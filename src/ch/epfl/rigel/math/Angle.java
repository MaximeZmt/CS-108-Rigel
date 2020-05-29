package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

/**
 * Angle transformation. An angle is represented by a double
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class Angle {
    public final static double TAU = 2 * Math.PI;
    public final static double RAD_PER_ARCSEC = TAU / (360 * 3600);
    public final static double RAD_PER_HR = TAU / 24.0;
    public final static double HR_PER_RAD = 24.0 / TAU;
    public final static double SEC_PER_DEG = 3600;
    public final static double SEC_PER_MIN = 60;
    public final static RightOpenInterval ANGLE_INTERVAL = RightOpenInterval.of(0, TAU);
    public final static RightOpenInterval BASIS_60 = RightOpenInterval.of(0,60);

    private Angle(){}

    /**
     * Reduces an angle to it's equivalent between 0 and 2*Pi
     *
     * @param rad angle in radians
     * @return the reduced angle (double)
     */
    public static double normalizePositive(double rad){ //angle from 0 to 2pi
        return ANGLE_INTERVAL.reduce(rad);
    }

    /**
     * Transforms an angle from arc-seconds to radians.
     *
     * @param sec angle in arc-seconds
     * @return the transformed angle in radians (double)
     */
    public static double ofArcsec(double sec){ // transform second to rad
        return sec * RAD_PER_ARCSEC;
    }

    /**
     * Transforms an angle from DMS (degrees minutes seconds) to radians
     *
     * @param deg degrees
     * @param min minutes
     * @param sec seconds
     * @return the transformed angle in radians (double)
     * @throws IllegalArgumentException if min and sec are not between 0 and 60 excluded
     */
    public static double ofDMS(int deg, int min, double sec){
        Preconditions.checkInInterval(BASIS_60, min);
        Preconditions.checkInInterval(BASIS_60, sec);
        Preconditions.checkArgument(deg >= 0);
        sec = sec + min*SEC_PER_MIN + deg * SEC_PER_DEG;
        return ofArcsec(sec);
    }

    /**
     * Transforms an angle from degrees to radians
     *
     * @param deg angle in degrees
     * @return the transformed angle in radians (double)
     */
    public static double ofDeg(double deg){
        return Math.toRadians(deg);
    }

    /**
     * Transforms an angle from radians to degrees
     *
     * @param rad angle in radians
     * @return the transformed angle in degrees (double)
     */
    public static double toDeg(double rad){
        return Math.toDegrees(rad);
    }

    /**
     * Transforms an angle from hours to radians
     *
     * @param hr angle in hours
     * @return the transformed angle in radians (double)
     */
    public static double ofHr(double hr){
        return hr * RAD_PER_HR;
    }

    /**
     * Transforms an angle from radians to hours
     *
     * @param rad angle in radians
     * @return the transformed angle in hours (double)
     */
    public static double toHr(double rad){
        return rad * HR_PER_RAD;
    }
}
