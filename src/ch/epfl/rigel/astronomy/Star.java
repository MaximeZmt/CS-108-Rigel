package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;


/**
 * Represents Stars as a celestial object
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */

public final class Star extends CelestialObject {
    private final static ClosedInterval colorI = ClosedInterval.of(-0.5,5.5); // need static ? common to other
    private final int hipparcosId;
    private final int colorTemperature;


    /**
     * public constructor to create a star instance
     * @param hipparcosId Number of Hipparcos (id of the star)
     * @param name The name of the star
     * @param equatorialPos Equatorial position of the star
     * @param magnitude Its brightness
     * @param colorIndex Its color
     * @throws IllegalArgumentException if the Hipparcos Id is invalid
     * or if the color is not in the given interval
     */
    public Star(int hipparcosId, String name, EquatorialCoordinates equatorialPos, float magnitude, float colorIndex) {
        super(name, equatorialPos, 0, magnitude); //TODO check angular size: p4 say planet and stars are null and p 5 planet are defined, weird

        Preconditions.checkArgument(hipparcosId>=0 && colorI.contains(colorIndex));

        this.hipparcosId = hipparcosId;
        colorTemperature = (int)Math.floor(4600*((1/(0.92*colorIndex+1.7))+(1/(0.92*colorIndex+0.62)))); //is a floor and better in constructor. Cast is also better in that case, avoid heavy transtype
    }

    /**
     * Getter for the Hipparcos Id
     * @return the hipparcos Id (hipparcosId)
     */
    public int hipparcosId(){
        return hipparcosId;
    }


    /**
     * Getter for the colorTemperature
     * @return the color Temperature (colorTemperature)
     */
    public int colorTemperature(){
        return colorTemperature;
    }


}
