package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;


//immuable

public final class Star extends CelestialObject {
    private final static ClosedInterval colorI = ClosedInterval.of(-0.5,5.5); // need static ? common to other
    private final int hipparcosId;
    private final int colorTemperature;

    public Star(int hipparcosId, String name, EquatorialCoordinates equatorialPos, float magnitude, float colorIndex) {
        super(name, equatorialPos, 0, magnitude); //TODO check angular size: p4 say planet and stars are null and p 5 planet are defined, weird

        if (hipparcosId<0 && !colorI.contains(colorIndex)) { //have asked -> yes it is strictly negative -> 0 special case but exist
            throw new IllegalArgumentException();
        }
        this.hipparcosId = hipparcosId;
        colorTemperature = (int)(4600*Math.floor((1/(0.92*colorIndex+1.7))+(1/(0.92*colorIndex+0.62)))); //is a floor and better in constructor. Cast is also better in that case, avoid heavy transtype
    }

    public int hipparcodID(){
        return hipparcosId;
    }

    public int colorTemperature(){
        return colorTemperature;
    }


}
