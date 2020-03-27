package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.math.Angle;

/**
 * Represents moon model
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public enum MoonModel implements CelestialObjectModel<Moon> { // public et immuable
    MOON();

    private final static double LONGITUDE_MOYENNE = Angle.ofDeg(91.929336);
    private final static double LONGITUDE_MOYENE_PERIGEE = Angle.ofDeg(130.143076);
    private final static double LONGITUDE_NOEUD_ASCENDANT = Angle.ofDeg(291.682547);
    private final static double INCLINAISON_ORBITE = Angle.ofDeg(5.145396);
    private final static double EXCENTRICITE = 0.0549;

    //TODO check why link points to itself (maybe case of genericity)
    /**
     * @see CelestialObjectModel<Moon>{@link #at(double, EclipticToEquatorialConversion)}
     */
    @Override
    public Moon at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        //--
        float magnitude = 0;
        Sun sun = SunModel.SUN.at(daysSinceJ2010, eclipticToEquatorialConversion);
        //--
        double l = Angle.ofDeg(13.1763966)*daysSinceJ2010 + LONGITUDE_MOYENNE;

        double anomalie = l - (Angle.ofDeg(0.1114041) * daysSinceJ2010) - LONGITUDE_MOYENE_PERIGEE;

        double evection = Angle.ofDeg(1.2739) * Math.sin(2*(l-sun.eclipticPos().lon())-anomalie);
        double corrEqAnuelle = Angle.ofDeg(0.1858)*Math.sin(sun.meanAnomaly());
        double corr3 = Angle.ofDeg(0.37) * Math.sin(sun.meanAnomaly());
        double anomaliePrime = anomalie + evection - corrEqAnuelle - corr3;
        double corr4 = Angle.ofDeg(0.214) * Math.sin(2*anomaliePrime);
        double corrEqCentre = Angle.ofDeg(6.2886)* Math.sin(anomaliePrime);
        double lprime = l + evection + corrEqCentre - corrEqAnuelle+ corr4;
        double variation = Angle.ofDeg(0.6583)*Math.sin(2*(lprime-sun.eclipticPos().lon()));
        double l2prime = lprime + variation;

        double n = LONGITUDE_NOEUD_ASCENDANT - (Angle.ofDeg(0.0529539) * daysSinceJ2010);
        double nPrime = n - (Angle.ofDeg(0.16) * Math.sin(sun.meanAnomaly()));

        double lambda = Angle.normalizePositive(Math.atan2(Math.sin(l2prime-nPrime)*Math.cos(INCLINAISON_ORBITE),Math.cos(l2prime-nPrime))+nPrime);
        double beta = Math.asin(Math.sin(l2prime-nPrime)*Math.sin(INCLINAISON_ORBITE));

        //--
        float phase = (float)((1-Math.cos(l2prime-(sun.eclipticPos().lon())))/2.);
        //--
        double pho = (1-(EXCENTRICITE*EXCENTRICITE))/(1+EXCENTRICITE*Math.cos(anomaliePrime+corrEqCentre));
        float angularSize = (float)(Angle.ofDeg(0.5181)/pho);
        return new Moon( eclipticToEquatorialConversion.apply(EclipticCoordinates.of(lambda,beta)), angularSize, magnitude, phase);
    }
}
