package ch.epfl.rigel.astronomy;


import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;

import java.time.ZonedDateTime;
import java.util.List;

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


    //should be public
    Sun sun(){
        //return SunModel.SUN.at();
        return null;
    }
    CartesianCoordinates sunPosition(){
        return null;
    }

    Moon moon(){
        return null;
    }

    CartesianCoordinates moonPosition(){
        return null;
    }

    List<Planet> planets(){
        return null;
    }

    double[] planetPositions(){
        return null; // list de 14 coord pos 0: x planet 1, pos 1: y planet 1, ...
    }

    List<Star> stars(){
        return null;
    }

    double[] starsPosition(){
        return null;
    }

}
