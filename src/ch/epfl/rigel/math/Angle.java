package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

/**
 * Angle transformation
 * An angle is represented by a double
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class Angle {
    private Angle(){}
    public final static double TAU = 2*Math.PI;
    public final static double RAD_PER_ARCSEC = TAU/(360*3600);
    public final static double RAD_PER_HR = TAU/24.0;
    public final static double HR_PER_RAD = 24.0/TAU;

    public final static RightOpenInterval ANGLE_INTERVAL = RightOpenInterval.of(0,TAU);
    public final static RightOpenInterval BASIS_60 = RightOpenInterval.of(0,60);

    /**
     * Reduces an angle to it's equivalent between 0 and 2*PI
     *
     * @param rad  angle in radians
     * @return the reduced angle
     */
    public static double normalizePositive(double rad){ //angle from 0 to 2pi
        return ANGLE_INTERVAL.reduce(rad);
    }

    /**
     * Transforms an angle from arc-seconds to radians.
     *
     * @param sec  angle in arc-seconds
     * @return the transformed angle in radians
     */
    public static double ofArcsec(double sec){ // transform second to rad
        return sec*RAD_PER_ARCSEC;
    }

    /**
     * Transforms an angle from DMS (degrees minutes seconds) to radians
     *
     * @param deg  degrees
     * @param min  minutes
     * @param sec  seconds
     * @return the transformed angle in radians
     */
    public static double ofDMS(int deg, int min, double sec){ //TODO CHECK
        Preconditions.checkArgument(BASIS_60.contains(min));
        Preconditions.checkArgument(BASIS_60.contains(sec));
        sec = sec + min*60 + deg * 3600;
        return ofArcsec(sec);
    }

    /**
     * Transforms an angle from degrees to radians
     *
     * @param deg  angle in degrees
     * @return the transformed angle in radians
     */
    public static double ofDeg(double deg){
        return Math.toRadians(deg);
    }

    /**
     * Transforms an angle from radians to degrees
     *
     * @param rad  angle in radians
     * @return the transformed angle in degrees
     */
    public static double toDeg(double rad){
        return Math.toDegrees(rad);
    }

    /**
     * Transforms an angle from hours to radians
     *
     * @param hr  angle in hours
     * @return the transformed angle in radians
     */
    public static double ofHr(double hr){
        return hr*RAD_PER_HR;
    }

    /**
     * Transforms an angle from radians to hours
     *
     * @param rad  angle in radians
     * @return the transformed angle in hours
     */
    public static double toHr(double rad){
        return rad * HR_PER_RAD;
    }


}
