package ch.epfl.rigel.astronomy;


import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;

/**
 * Generical interface Represent a model of celestial object
 */

public interface CelestialObjectModel<O> {
    /**
     * javadoc is here but redefined after for sun and planet model
     * @param daysSinceJ2010
     * @param eclipticToEquatorialConversion
     * @return
     */
    O at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion);
    //is public and abstract
    //return object modelise by model for number (pos or negative) of days after j2010
    //by using conversion of data to get it's equatorial coodinates with ecliptic coordinates
}
