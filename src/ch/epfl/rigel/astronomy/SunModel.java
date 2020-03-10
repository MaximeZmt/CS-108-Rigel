package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;

/**
 * Represents the model of the Sun.
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public enum SunModel implements CelestialObjectModel<Sun> {
    SUN();

    private final static double theta0 = Angle.ofDeg(0.533128);
    private final static double e = 0.016705;
    private final static double sunLongitudeAtJ2010 = Angle.ofDeg(279.557208);
    private final static double sunLongitudeAtPerigee = Angle.ofDeg(283.112438);


    @Override
    public Sun at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        double meanAnomaly = (Angle.TAU/365.242191)*daysSinceJ2010 + sunLongitudeAtJ2010-sunLongitudeAtPerigee;
        double trueAnomaly = meanAnomaly+2*e*Math.sin(meanAnomaly);
        double longEcliptique = trueAnomaly + sunLongitudeAtPerigee; // verify if normalize
        double latEcliptique = 0;
        float angularSize = (float)(theta0 * ((1+e*Math.cos(trueAnomaly))/(1-e*e)));
        EclipticCoordinates ecliC = EclipticCoordinates.of(Angle.normalizePositive(longEcliptique),latEcliptique);
        EquatorialCoordinates equaC = eclipticToEquatorialConversion.apply(ecliC);
        Sun sunModel = new Sun(ecliC, equaC, angularSize, (float)meanAnomaly);

        return sunModel;
    } //immuable
}
