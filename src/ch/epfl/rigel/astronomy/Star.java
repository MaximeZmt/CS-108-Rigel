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
    private final static ClosedInterval COLOR_INDEX_INTERVAL = ClosedInterval.of(-0.5,5.5); //Interval of valid ColorIndex
    private final int hipparcosId;
    private final int colorTemperature;

    /**
     * public constructor to create a star instance
     *
     * @param hipparcosId Number of Hipparcos (id of the star)
     * @param name The name of the star
     * @param equatorialPos Equatorial position of the star
     * @param magnitude Its brightness
     * @param colorIndex Its color
     * @throws IllegalArgumentException if the Hipparcos Id is invalid
     * or if the color is not in the given interval
     */
    public Star(int hipparcosId, String name, EquatorialCoordinates equatorialPos, float magnitude, float colorIndex) {
        super(name, equatorialPos, 0, magnitude);
        //angular size for star is 0 because they are represented as a point

        Preconditions.checkArgument(hipparcosId>=0);
        Preconditions.checkInInterval(COLOR_INDEX_INTERVAL,colorIndex);

        this.hipparcosId = hipparcosId;
        double colorIndexMultiplied = 0.92*colorIndex;
        colorTemperature = (int)Math.floor(4600*((1/(colorIndexMultiplied+1.7))+(1/(colorIndexMultiplied+0.62))));
    }

    /**
     * Getter for the Hipparcos Id
     *
     * @return the hipparcos Id (hipparcosId)
     */
    public int hipparcosId(){
        return hipparcosId;
    }

    /**
     * Getter for the colorTemperature
     *
     * @return the color Temperature (colorTemperature)
     */
    public int colorTemperature(){
        return colorTemperature;
    }
}
