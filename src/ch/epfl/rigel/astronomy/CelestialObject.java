package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.util.Objects;

public abstract class CelestialObject {
    private final String name;
    private final EquatorialCoordinates equatorialPos;
    private final float magnitude;
    private final float angularSize;


    CelestialObject(String name, EquatorialCoordinates equatorialPos, float angularSize, float magnitude){
        Preconditions.checkArgument(angularSize>=0);
        Objects.requireNonNull(equatorialPos);
        Objects.requireNonNull(name);
        this.name = name;
        this.equatorialPos = equatorialPos;
        this.magnitude = magnitude;
        this.angularSize = angularSize;
    }

    public String name(){
        return name;
    }

    public double angularSize(){
        return angularSize;
    }

    public double magnitude(){
        return magnitude;
    }

    public EquatorialCoordinates equatorialPos(){
        return equatorialPos;
    }


    //TODO check if enough as info (first seen in corr yes but is needed "info: %name%")
    public String info(){ //until now we use the name as an information
        return name;
    }

    @Override
    public String toString() {
        return info();
    }
}
