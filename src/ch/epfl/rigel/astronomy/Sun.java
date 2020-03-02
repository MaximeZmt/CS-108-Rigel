package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

public final class Sun extends CelestialObject {

    private final EclipticCoordinates eclipticPos;
    private final double meanAnomaly;

    public Sun(EclipticCoordinates eclipticPos, EquatorialCoordinates equatorialPos, float angularSize, float meanAnomaly) {
        super("Soleil", equatorialPos, angularSize, -26.7f);
        this.eclipticPos = eclipticPos;
        this.meanAnomaly = meanAnomaly;
    }

    public EclipticCoordinates eclipticPos(){
        return eclipticPos;
    }

    public double meanAnomaly(){
        return meanAnomaly;
    }


}
