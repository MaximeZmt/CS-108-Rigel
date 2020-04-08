package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

/**
 * Represents horizontal coordinates
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class HorizontalCoordinates extends SphericalCoordinates {
    //Intervals that are used to check if the given angle in of() method is valid.
    private final static RightOpenInterval AZIMUTH_INTERVAL = RightOpenInterval.of(Angle.ofDeg(0),Angle.ofDeg(360));
    private final static ClosedInterval ALTITUDE_INTERVAL = ClosedInterval.of(Angle.ofDeg(-90),Angle.ofDeg(90));

    private HorizontalCoordinates(double azimuth, double altitude) {
        super(azimuth, altitude);
    }

    /**
     * Generates a horizontal coordinate with the given azimuth and altitude in radians
     * <p>
     * The azimuth must be between 0 and 2*Pi
     * <p>
     * The altitude must be between -Pi/2 and Pi/2
     *
     * @param az azimuth in radians
     * @param alt altitude in radians
     * @return a horizontal coordinate (HorizontalCoordinates)
     * @throws IllegalArgumentException if the inputs are not in the correct interval
     */
    public static HorizontalCoordinates of(double az, double alt){
        Preconditions.checkInInterval(AZIMUTH_INTERVAL,az);
        Preconditions.checkInInterval(ALTITUDE_INTERVAL,alt);
        return new HorizontalCoordinates(az, alt);
    }

    /**
     * Generates a horizontal coordinate with the given azimuth and altitude in degrees
     * <p>
     * The azimuth must be between 0° and 360°
     * <p>
     * The altitude must be between -90° and 90°
     *
     * @param azDeg azimuth in radians
     * @param altDeg altitude in radians
     * @return a horizontal coordinate (HorizontalCoordinates)
     * @throws IllegalArgumentException if the inputs are not in the correct interval
     */
    public static HorizontalCoordinates ofDeg(double azDeg, double altDeg){ //with deg
        double az = Angle.ofDeg(azDeg);
        double alt = Angle.ofDeg(altDeg);
        Preconditions.checkInInterval(AZIMUTH_INTERVAL,az);
        Preconditions.checkInInterval(ALTITUDE_INTERVAL,alt);
        return new HorizontalCoordinates(az,alt);
    }

    /**
     * Getter for the azimuth in radians
     *
     * @return azimuth in radian (double)
     */
    public double az(){
        return lon();
    }

    /**
     * Getter for the azimuth in degrees
     *
     * @return azimuth in degrees (double)
     */
    public double azDeg(){
        return lonDeg();
    }

    /**
     * Compute the octant related to the azimuth angle
     *
     * @param n North string representation (E.g. "N")
     * @param e East string representation (E.g. "E")
     * @param s South string representation (E.g. "S")
     * @param w West string representation (E.g. "W")
     * @return return a string giving the Octant (E.g. for an angle of 135 degrees and s="S", e="E" it return "SE") (String)
     * @throws IllegalStateException if fails
     */
    public String azOctantName(String n, String e, String s, String w){
        int octantPosition = ((int) (azDeg()+22.5)/45)%8;
        switch(octantPosition){
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
            default:
                throw new IllegalStateException("azOctantName switch has failed");
        }
    }

    /**
     * Getter for the altitude in radians
     *
     * @return altitude in radians (double)
     */
    public double alt(){
        return lat();
    }

    /**
     * Getter for the altitude in degrees
     *
     * @return altitude in degrees (double)
     */
    public double altDeg(){
        return latDeg();
    }

    /**
     * Compute the distance between two Horizontal coordinates (this.angularDistanceTo(that))
     *
     * @param that an HorizontalCoordinates object
     * @return distance between that and this in radians (two Horizontal coordinates) (double)
     */
    public double angularDistanceTo(HorizontalCoordinates that){
        return Math.acos( Math.sin(this.alt()) * Math.sin(that.alt()) + Math.cos(this.alt()) * Math.cos(that.alt()) * Math.cos(this.az()-that.az())   );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(az=%.4f°, alt=%.4f°)", azDeg(), altDeg());
    }
}
