package ch.epfl.rigel.astronomy;


import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;

import java.time.ZonedDateTime;

/**
 * Represent a set of CelestialObject projected in a plan with a stereographic projection
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */

public class ObservedSky { // public et immuable

    /*
    info + model soleil lune planete -> calculer pos projetee dans plan de tout objet celeste (Soleil, Lune,planete, etoile) sauf terre
     */
    ObservedSky(ZonedDateTime zdt, GeographicCoordinates observPos, StereographicProjection stereoProj, StarCatalogue starCatalogue){ //public or package-private or private ?

    }

    Sun sun(){
        //return SunModel.SUN.at();
        return null;
    }
    CartesianCoordinates sunPosition(){
        return null;
    }

}
