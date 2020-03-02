package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.util.Objects;

/**
 * Represents a Celestial Object
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public abstract class CelestialObject {
    private final String name;
    private final EquatorialCoordinates equatorialPos;
    private final float magnitude;
    private final float angularSize;

    /**
     * Builds a celestial object with a given name, equatorial position, angular size and magnitude
     *
     * @param name name
     * @param equatorialPos equatorial position
     * @param angularSize angular size
     * @param magnitude magnitude
     * @throws IllegalArgumentException if the angular size is negative
     * @throws NullPointerException if the name or the equatorial position are null
     */
    CelestialObject(String name, EquatorialCoordinates equatorialPos, float angularSize, float magnitude){
        Preconditions.checkArgument(angularSize>=0);
        Objects.requireNonNull(equatorialPos);
        Objects.requireNonNull(name);
        this.name = name;
        this.equatorialPos = equatorialPos;
        this.magnitude = magnitude;
        this.angularSize = angularSize;
    }

    /**
     * Getter for the name
     *
     * @return name (String)
     */
    public String name(){
        return name;
    }

    /**
     * Getter for the angular size
     *
     * @return angular size (double)
     */
    public double angularSize(){
        return angularSize;
    }

    /**
     * Getter for the magnitude
     *
     * @return magnitude (double)
     */
    public double magnitude(){
        return magnitude;
    }

    /**
     * Getter for the equatorial position
     *
     * @return equatorial position (EquatorialCoordinates)
     */
    public EquatorialCoordinates equatorialPos(){
        return equatorialPos;
    }

    /**
     * Shows a short explanatory text of the object destined to be seen by the user
     *
     * @return an explanation (String)
     */
    public String info(){ //TODO check if enough as info (first seen in corr yes but is needed "info: %name%")
        return name;
    }

    @Override
    public String toString() {
        return info();
    }
}
