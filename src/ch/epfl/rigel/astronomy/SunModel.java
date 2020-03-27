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

    private final static double THETA0 = Angle.ofDeg(0.533128);
    private final static double ECCENTRICITY = 0.016705;
    private final static double SUN_LONGITUDE_AT_J2010 = Angle.ofDeg(279.557208);
    private final static double SUN_LONGITUDE_AT_PERIGEE = Angle.ofDeg(283.112438);

    /**
     * @see CelestialObjectModel#at(double, EclipticToEquatorialConversion)
     */
    @Override
    public Sun at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        double meanAnomaly = (Angle.TAU/365.242191)*daysSinceJ2010 + SUN_LONGITUDE_AT_J2010 - SUN_LONGITUDE_AT_PERIGEE;
        double trueAnomaly = meanAnomaly+2* ECCENTRICITY *Math.sin(meanAnomaly);
        double longEcliptique = trueAnomaly + SUN_LONGITUDE_AT_PERIGEE; // verify if normalize
        double latEcliptique = 0;
        float angularSize = (float)(THETA0 * ((1+ ECCENTRICITY *Math.cos(trueAnomaly))/(1- ECCENTRICITY * ECCENTRICITY)));
        EclipticCoordinates ecliC = EclipticCoordinates.of(Angle.normalizePositive(longEcliptique),latEcliptique);
        EquatorialCoordinates equaC = eclipticToEquatorialConversion.apply(ecliC);

        return new Sun(ecliC, equaC, angularSize, (float)meanAnomaly);
    }
}
