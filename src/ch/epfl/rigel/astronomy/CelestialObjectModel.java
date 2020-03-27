package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;

/**
 * Represents a model of a Celestial Object at a given time (generic Interface)
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public interface CelestialObjectModel<O> {
    /**
     * Return the object at a given time depending on the given model
     * @param daysSinceJ2010 The number of days between the date of the modeling and J2010
     * @param eclipticToEquatorialConversion The Converter for the coordinates in order
     *                                       to get EquatorialCoordinates with the Ecliptic one.
     * @return the modeled object (O) by the modeler after a number of days until J2010 (could be negative as positive)
     */
    O at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion);
}
