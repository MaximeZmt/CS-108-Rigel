package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.util.Objects;

/**
 * Represents the sun at a certain moment
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class Sun extends CelestialObject {

    private final EclipticCoordinates eclipticPos;
    private final double meanAnomaly;

    /**
     * Builds an object that represents the sun with the given ecliptic position, equatorial position,
     * angular size and mean anomaly
     *
     * @param eclipticPos ecliptic position
     * @param equatorialPos equatorial position
     * @param angularSize angular size
     * @param meanAnomaly mean anomaly
     * @throws NullPointerException if the ecliptic position is null
     */
    public Sun(EclipticCoordinates eclipticPos, EquatorialCoordinates equatorialPos, float angularSize, float meanAnomaly) {
        super("Soleil", equatorialPos, angularSize, -26.7f);
        Objects.requireNonNull(eclipticPos);
        this.eclipticPos = eclipticPos;
        this.meanAnomaly = meanAnomaly;
    }

    /**
     * Getter for the ecliptic coordinates
     *
     * @return ecliptic coordinates (EclipticCoordinates)
     */
    public EclipticCoordinates eclipticPos(){
        return eclipticPos;
    }

    /**
     * Getter for the mean anomaly
     *
     * @return mean anomaly (double)
     */
    public double meanAnomaly(){
        return meanAnomaly;
    }


}
