package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

public final class HorizontalCoordinates extends SphericalCoordinates {

    private final static RightOpenInterval AZIMUTH_INTERVAL = RightOpenInterval.of(Angle.ofDeg(0),Angle.ofDeg(360));
    private final static ClosedInterval ALTITUDE_INTERVAL = ClosedInterval.of(Angle.ofDeg(-90),Angle.ofDeg(90));

    //TODO ask if has to be registered in radian (knowing the theory and the precision in deg in the part two for the interval)

    private HorizontalCoordinates(double azimuth, double altitude) {
        super(azimuth, altitude);
    }

    public static HorizontalCoordinates of(double az, double alt){ // with radian
        Preconditions.checkInInterval(AZIMUTH_INTERVAL,az);
        Preconditions.checkInInterval(ALTITUDE_INTERVAL,alt);
        return new HorizontalCoordinates(az, alt);
    }

    public static HorizontalCoordinates ofDeg(double azDeg, double altDeg){ //with deg
        double az = Angle.ofDeg(azDeg);
        double alt = Angle.ofDeg(altDeg);
        Preconditions.checkInInterval(AZIMUTH_INTERVAL,az);
        Preconditions.checkInInterval(ALTITUDE_INTERVAL,alt);
        return new HorizontalCoordinates(az,alt);
    }

    public double az(){
        return lon();
    }

    public double azDeg(){
        return lonDeg();
    }

    public static String azOctantName(String n, String e, String s, String w){ //TODO Check with T.A.
        int num = ((int) (azDeg()+22.5)/45)%8;
        switch(num){
            case 0:
                return n;
            case 1:
                return n+e;
            case 2:
                return e;
            case 3:
                return s+e;
            case 4:
                return s;
            case 5:
                return s+w;
            case 6:
                return w;
            case 7:
                return n+w;
        }
        return null; //TODO fill it
    }

    public double alt(){
        return lat();
    }

    public double altDeg(){
        return latDeg();
    }

    public double angularDistanceTo(HorizontalCoordinates that){
        return Math.acos( Math.sin(this.alt()) * Math.sin(that.alt()) + Math.cos(this.alt()) * Math.cos(that.alt() * Math.cos(this.az()-that.az()))   );
    } // longitude/azimuth lambda et latitude/ altitude phi
    // 1 -> this et 2 -> that


    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(az=%.4f°, alt=%.4f°)", azDeg(), altDeg());
    }
}
