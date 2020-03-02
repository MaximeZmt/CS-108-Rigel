package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

/**
 * Represents The Earth at a certain moment
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */


public final class Planet extends CelestialObject {
    public Planet(String name, EquatorialCoordinates equatorialPos, float angularSize, float magnitude) {
        super(name, equatorialPos, angularSize, magnitude);
    }
}
