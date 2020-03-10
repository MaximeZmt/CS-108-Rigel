package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;

import java.util.List;

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

    //TODO check if correct way for immuable list
    public static final List<PlanetModel> ALL = List.of(MERCURY, VENUS, EARTH, MARS, JUPITER, SATURN, URANUS, NEPTUNE);

    //TODO check if private or package-private
    //TODO check if have to create attributes
    private PlanetModel(String nameFr, double tropicalYear, double lonJ2010, double lonPerigee,
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

    @Override
    //TODO check if have to use getter or attribute (or smthg else)
    //TODO check if this.attribute is necessary (or only write attribute)
    public Planet at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        double meanAnomaly = (Angle.TAU/365.242191)*(daysSinceJ2010/tropicalYear)+lonJ2010-lonPerigee;
        double trueAnomaly = meanAnomaly+2*orbitalEccentricity*Math.sin(meanAnomaly);
        double radiusPlanet = (semiMajorAxis*(1-orbitalEccentricity*orbitalEccentricity))/
                1+orbitalEccentricity*Math.cos(trueAnomaly);
        double lonPlanet = trueAnomaly+lonPerigee;
        double latEclipticHeliocentric = Math.asin(Math.sin(lonPlanet-lonAscendingNode)*Math.sin(orbitalInclination));
        double radiusProjection = radiusPlanet*Math.cos(latEclipticHeliocentric);
        double lonProjection = Math.atan2(Math.sin(lonPlanet-lonAscendingNode)*Math.cos(orbitalInclination),
                Math.cos(lonPlanet-lonAscendingNode))+lonAscendingNode;

        double meanAnomalyEarth = (Angle.TAU/365.242191)*(daysSinceJ2010/EARTH.tropicalYear)
                +EARTH.lonJ2010-EARTH.lonPerigee;
        double trueAnomalyEarth = meanAnomalyEarth+2*EARTH.orbitalEccentricity*Math.sin(meanAnomalyEarth);
        double radiusEarth = (EARTH.semiMajorAxis*(1-EARTH.orbitalEccentricity*EARTH.orbitalEccentricity))/
                1+EARTH.orbitalEccentricity*Math.cos(trueAnomalyEarth);
        double lonEarth = trueAnomalyEarth+EARTH.lonPerigee;

        double lonEcliptic;
        double rSinLProjectionMinusLEarth = radiusEarth*Math.sin(lonProjection-lonEarth);

        //TODO check if 'if' is correct (Also check for calculation of earth values)
        if (this.equals(MERCURY) || this.equals(VENUS)){
            lonEcliptic = Math.PI+lonEarth+Math.atan2(
                    radiusProjection*Math.sin(lonEarth-lonProjection),
                    radiusEarth-radiusProjection*Math.cos(lonEarth-lonProjection));
        } else {
            lonEcliptic = lonProjection+Math.atan2(rSinLProjectionMinusLEarth,
                    radiusProjection-radiusEarth*Math.cos(lonProjection-lonEarth));
        }

        double latEcliptic = Math.atan2(
                radiusProjection*Math.tan(latEclipticHeliocentric)*Math.sin(lonEcliptic-lonProjection),
                rSinLProjectionMinusLEarth);

        //TODO check if normalize is correct
        EclipticCoordinates eclipticCoordinates = EclipticCoordinates.of(
                Angle.normalizePositive(lonEcliptic),latEcliptic);
        EquatorialCoordinates equatorialCoordinates = eclipticToEquatorialConversion.apply(eclipticCoordinates);

        //TODO check if cast is correct
        return new Planet(nameFr, equatorialCoordinates, (float)angularSize, (float)magnitude);

    }
}
