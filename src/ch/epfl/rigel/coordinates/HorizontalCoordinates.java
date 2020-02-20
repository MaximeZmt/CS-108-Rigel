package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

public final class HorizontalCoordinates extends SphericalCoordinates {

    private final static RightOpenInterval AZIMUTH_INTERVAL = RightOpenInterval.of(Angle.ofDeg(0),Angle.ofDeg(360));
    private final static ClosedInterval ALTITUDE_INTERVAL = ClosedInterval.of(Angle.ofDeg(-90),Angle.ofDeg(90));

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

    public String azOctantName(String n, String e, String s, String w){
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
}
