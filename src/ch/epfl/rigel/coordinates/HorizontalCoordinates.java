package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;


/**
 * Final Class that allow us to create HorizontalCoordinates
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class HorizontalCoordinates extends SphericalCoordinates {

    private final static RightOpenInterval AZIMUTH_INTERVAL = RightOpenInterval.of(Angle.ofDeg(0),Angle.ofDeg(360));
    private final static ClosedInterval ALTITUDE_INTERVAL = ClosedInterval.of(Angle.ofDeg(-90),Angle.ofDeg(90));

    //TODO ask if has to be registered in radian (knowing the theory and the precision in deg in the part two for the interval)

    /**
     * Private Constructor that Call the super constructor that the class extends, SphericalCoordinates
     * @param azimuth Angle in radian (longitude equivalent)
     * @param altitude Angle in radian (latitude equivalent)
     */
    private HorizontalCoordinates(double azimuth, double altitude) {
        super(azimuth, altitude);
    }

    /**
     * Method that allow us to instantiate HorizontalCoordinates. It calls the private constructor above.
     * @param az Azimuth in radian
     * @param alt Altitude in radian
     * @return an Object of type HorizontalCoordinates that has been built with the two arguments above.
     */
    public static HorizontalCoordinates of(double az, double alt){ // with radian
        Preconditions.checkInInterval(AZIMUTH_INTERVAL,az);
        Preconditions.checkInInterval(ALTITUDE_INTERVAL,alt);
        return new HorizontalCoordinates(az, alt);
    }

    /**
     * Second Method that allow us to instantiate HorizontalCoordinates. It calls the private constructor above.
     * <p>
     * (Its arguments are in degrees and converted in radian before calling the private constructor)
     * @param azDeg Azimuth in Degree
     * @param altDeg Altitude in Degree
     * @return an Object of type HorizontalCoordinates that has been built with the two arguments above.
     */
    public static HorizontalCoordinates ofDeg(double azDeg, double altDeg){ //with deg
        double az = Angle.ofDeg(azDeg);
        double alt = Angle.ofDeg(altDeg);
        Preconditions.checkInInterval(AZIMUTH_INTERVAL,az);
        Preconditions.checkInInterval(ALTITUDE_INTERVAL,alt);
        return new HorizontalCoordinates(az,alt);
    }

    /**
     * Get the Azimuth of some HorizontalCoordinates
     * @return Azimuth in radian
     */
    public double az(){
        return lon();
    }

    /**
     * Get the Azimuth of some HorizontalCoordinates
     * @return Azimuth in degree
     */
    public double azDeg(){
        return lonDeg();
    }

    /**
     * Compute the octant related to the azimuth angle
     * @param n North string representation (E.g. "N")
     * @param e East string representation (E.g. "E")
     * @param s South string representation (E.g. "S")
     * @param w West string representation (E.g. "W")
     * @return return a String giving the Octant(for an angle of 135 degrees and s="S", e="E" it return "SE")
     */
    public String azOctantName(String n, String e, String s, String w){ //TODO Check with T.A.
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

    /**
     * Get the Altitude of some HorizontalCoordinates
     * @return Altitude in radian
     */
    public double alt(){
        return lat();
    }

    /**
     * Get the Altitude of some HorizontalCoordinates
     * @return Altitude in degree
     */
    public double altDeg(){
        return latDeg();
    }

    /**
     * Compute the distance between two Horizontal coordinates (one from argument and this)
     * @param that an HorizontalCoordinates object (given from argument)
     * @return distance between that and this (two Horizontal coordinates)
     */
    public double angularDistanceTo(HorizontalCoordinates that){
        return Math.acos( Math.sin(this.alt()) * Math.sin(that.alt()) + Math.cos(this.alt()) * Math.cos(that.alt() * Math.cos(this.az()-that.az()))   );
    } // longitude/azimuth lambda et latitude/ altitude phi
    // 1 -> this et 2 -> that

    /**
     * Get a String representation of the coordinates in degrees
     * @return a String containing the HorizontalCoordinates
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(az=%.4f°, alt=%.4f°)", azDeg(), altDeg());
    }
}
