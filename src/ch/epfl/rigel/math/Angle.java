package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

public final class Angle {
    private Angle(){}
    public final static double TAU = 2*Math.PI;
    public final static double RAD_PER_ARCSEC = TAU/(360*3600);


    public final static double RAD_PER_HR = TAU/24.0;
    public final static double HR_PER_RAD = 24.0/TAU;

    public final static RightOpenInterval angleInterval = RightOpenInterval.of(0,TAU);
    public final static RightOpenInterval basis60 = RightOpenInterval.of(0,60);

    public static double normalizePositive(double rad){ //angle from 0 to 2pi
        return angleInterval.reduce(rad);
    }

    public static double ofArcsec(double sec){ // transform second to rad
        return sec*RAD_PER_ARCSEC;
    }

    public static double ofDMS(int deg, int min, double sec){ //TODO CHECK
        Preconditions.checkArgument(basis60.contains(min));
        Preconditions.checkArgument(basis60.contains(sec));
        sec = sec + min*60 + deg * 3600;
        return ofArcsec(sec);
    }

    public static double ofDeg(double deg){
        return Math.toRadians(deg);
    }

    public static double toDeg(double rad){
        return Math.toDegrees(rad);
    }

    public static double ofHr(double hr){
        return hr*RAD_PER_HR;
    }

    public static double toHr(double rad){
        return rad * HR_PER_RAD;
    }


}
