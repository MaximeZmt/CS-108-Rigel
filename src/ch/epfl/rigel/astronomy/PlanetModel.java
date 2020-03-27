package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.List;

/**
 * Represents models of different planets of the solar system
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public enum PlanetModel implements CelestialObjectModel<Planet> {

    MERCURY("Mercure", 0.24085, 75.5671, 77.612, 0.205627,
            0.387098, 7.0051, 48.449, 6.74, -0.42),
    VENUS("Vénus", 0.615207, 272.30044, 131.54, 0.006812,
            0.723329, 3.3947, 76.769, 16.92, -4.40),
    EARTH("Terre", 0.999996, 99.556772, 103.2055, 0.016671,
            0.999985, 0, 0, 0, 0),
    MARS("Mars", 1.880765, 109.09646, 336.217, 0.093348,
            1.523689, 1.8497, 49.632, 9.36, -1.52),
    JUPITER("Jupiter", 11.857911, 337.917132, 14.6633, 0.048907,
            5.20278, 1.3035, 100.595, 196.74, -9.40),
    SATURN("Saturne", 29.310579, 172.398316, 89.567, 0.053853,
            9.51134, 2.4873, 113.752, 165.60, -8.88),
    URANUS("Uranus", 84.039492, 271.063148, 172.884833, 0.046321,
            19.21814, 0.773059, 73.926961, 65.80, -7.19),
    NEPTUNE("Neptune", 165.84539, 326.895127, 23.07, 0.010483,
            30.1985, 1.7673, 131.879, 62.20, -6.87);

    private final String nameFr;
    private final double tropicalYear;
    private final double lonJ2010;
    private final double lonPerigee;
    private final double orbitalEccentricity;
    private final double semiMajorAxis;
    private final double orbitalInclination;
    private final double lonAscendingNode;
    private final double angularSize;
    private final double magnitude;

    public static final List<PlanetModel> ALL = List.of(MERCURY, VENUS, EARTH, MARS, JUPITER, SATURN, URANUS, NEPTUNE);

    /**
     * Constructor for a planet model
     *
     * @param nameFr french name
     * @param tropicalYear tropical year
     * @param lonJ2010 longitude at J2010 epoch
     * @param lonPerigee longitude at perigee
     * @param orbitalEccentricity orbital eccentricity
     * @param semiMajorAxis semi major axis
     * @param orbitalInclination orbital inclination
     * @param lonAscendingNode longitude of the ascending node
     * @param angularSize angular size
     * @param magnitude magnitude
     */

    PlanetModel(String nameFr, double tropicalYear, double lonJ2010, double lonPerigee,
                        double orbitalEccentricity, double semiMajorAxis, double orbitalInclination,
                        double lonAscendingNode, double angularSize, double magnitude){
        this.nameFr = nameFr;
        this.tropicalYear = tropicalYear;
        this.lonJ2010 = Angle.ofDeg(lonJ2010);
        this.lonPerigee = Angle.ofDeg(lonPerigee);
        this.orbitalEccentricity = orbitalEccentricity;
        this.semiMajorAxis = semiMajorAxis;
        this.orbitalInclination = Angle.ofDeg(orbitalInclination);
        this.lonAscendingNode = Angle.ofDeg(lonAscendingNode);
        this.angularSize = angularSize;
        this.magnitude = magnitude;
    }

    /**
     * @see CelestialObjectModel<Planet>{@link #at(double, EclipticToEquatorialConversion)}
     */
    @Override
    public Planet at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {

        //data needed to compute the position of all planets
        double m = (Angle.TAU/365.242191)*(daysSinceJ2010/tropicalYear)+lonJ2010-lonPerigee;
        double v = m+2*orbitalEccentricity*Math.sin(m);
        double r = (semiMajorAxis*(1-orbitalEccentricity*orbitalEccentricity))/
                (1+orbitalEccentricity*Math.cos(v));
        double l = v+lonPerigee;
        double psi = Math.asin(Math.sin(l-lonAscendingNode)*Math.sin(orbitalInclination));
        double rPrime = r*Math.cos(psi);
        double lPrime = Math.atan2(Math.sin(l-lonAscendingNode)*Math.cos(orbitalInclination),
                Math.cos(l-lonAscendingNode))+lonAscendingNode;

        //data for the position of the earth
        double mEarth = (Angle.TAU/365.242191)*(daysSinceJ2010/EARTH.tropicalYear)
                +EARTH.lonJ2010-EARTH.lonPerigee;
        double vEarth = mEarth+2*EARTH.orbitalEccentricity*Math.sin(mEarth);
        double rEarth = (EARTH.semiMajorAxis*(1-EARTH.orbitalEccentricity*EARTH.orbitalEccentricity))/
                (1+EARTH.orbitalEccentricity*Math.cos(vEarth));
        double lEarth = vEarth+EARTH.lonPerigee;

        //redundant calculation
        double rEarthSinLPrimeMinusLEarth = rEarth*Math.sin(lPrime-lEarth);

        //different calculations for longitude of inferior and superior planets
        double lambda;
        if (this.equals(MERCURY) || this.equals(VENUS)){
            lambda = Math.PI+lEarth+Math.atan2(
                    rPrime*Math.sin(lEarth-lPrime),
                    rEarth-rPrime*Math.cos(lEarth-lPrime));
        } else {
            lambda = lPrime+Math.atan2(rEarthSinLPrimeMinusLEarth,
                    rPrime-rEarth*Math.cos(lPrime-lEarth));
        }

        //latitude for all planets
        double beta = Math.atan(
                (rPrime*Math.tan(psi)*Math.sin(lambda-lPrime))/
                (rEarthSinLPrimeMinusLEarth));

        //position in equatorial coordinates of the given planet computed with the previous data
        EclipticCoordinates eclipticCoordinates = EclipticCoordinates.of(
                Angle.normalizePositive(lambda),beta);
        EquatorialCoordinates equatorialCoordinates = eclipticToEquatorialConversion.apply(eclipticCoordinates);

        //angular size of the given planet
        double rho = Math.sqrt(rEarth*rEarth+r*r-2*rEarth*r*Math.cos(l-lEarth)*Math.cos(psi));
        double thetaArcSec = angularSize/rho;
        double theta = Angle.ofDMS(0,0,thetaArcSec);

        //magnitude of the given planet
        double phase = (1+Math.cos(lambda-l))/2;
        double magn = magnitude+5*Math.log10((r*rho)/Math.sqrt(phase));

        return new Planet(nameFr, equatorialCoordinates, (float)theta, (float)magn);
    }
}
