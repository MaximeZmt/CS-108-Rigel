package ch.epfl.rigel.astronomy;


import ch.epfl.rigel.coordinates.*;

import java.time.ZonedDateTime;
import java.util.*;

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
    private final Sun sunInstance;
    private final CartesianCoordinates sunCartCoordinates;

    private final Moon moonInstance;
    private final CartesianCoordinates moonCartCoordinates;

    private final List<Planet> planetsList;
    private final double[] planetPosArray;

    private final List<Star> starList;
    private final double[] starPosArray;

    ObservedSky(ZonedDateTime zdt, GeographicCoordinates observPos, StereographicProjection stereoProj, StarCatalogue starCatalogue){ //public or package-private or private ?
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(zdt);
        EquatorialToHorizontalConversion ethc = new EquatorialToHorizontalConversion(zdt,observPos);

        //sun
        sunInstance = SunModel.SUN.at(Epoch.J2010.daysUntil(zdt),etec);
        sunCartCoordinates = stereoProj.apply(ethc.apply(sunInstance.equatorialPos()));

        //moon
        moonInstance = MoonModel.MOON.at(Epoch.J2010.daysUntil(zdt),etec);
        moonCartCoordinates = stereoProj.apply(ethc.apply(moonInstance.equatorialPos()));

        //planet
        planetsList = new ArrayList<>(); //TODO ask if it's better ArrayList or LinkedList
        planetPosArray = new double[14]; //only 7 planet, earth does'nt count
        Iterator pIterator = PlanetModel.ALL.iterator();
        int counter = 0;
        while(pIterator.hasNext()){
            PlanetModel pmodel = (PlanetModel)pIterator.next();
            if(!pmodel.equals(PlanetModel.EARTH)) {
                Planet p = pmodel.at(Epoch.J2010.daysUntil(zdt), etec);
                planetsList.add(p);
                CartesianCoordinates planetTempoCartesianCoord = stereoProj.apply(ethc.apply(p.equatorialPos()));
                planetPosArray[(counter * 2)] = planetTempoCartesianCoord.x();
                planetPosArray[(counter * 2) + 1] = planetTempoCartesianCoord.y();
                counter++;
            }
        }

        //stars
        starList = starCatalogue.stars();
        starPosArray = new double[starList.size()*2]; //2* cause coord x and y.
        counter = 0;
        Iterator starIterator = starList.iterator();
        while(starIterator.hasNext()){
            Star s = (Star)starIterator.next();
            CartesianCoordinates starCartCoord = stereoProj.apply(ethc.apply(s.equatorialPos()));
            starPosArray[(counter*2)] = starCartCoord.x();
            starPosArray[(counter*2)+1] = starCartCoord.y();
            counter++;
        }


    }


    //should be public ? methode d'acces
    Sun sun(){
        return sunInstance;
    }
    CartesianCoordinates sunPosition(){
        return sunCartCoordinates;
    }

    Moon moon(){
        return moonInstance;
    }

    CartesianCoordinates moonPosition(){
        return moonCartCoordinates;
    }

    List<Planet> planets(){
        return List.copyOf(planetsList);
    }

    double[] planetPositions(){
        return Arrays.copyOf(planetPosArray,planetPosArray.length); // list de 14 coord pos 0: x planet 1, pos 1: y planet 1, ...
    }

    List<Star> stars(){
        return List.copyOf(starList);
    }

    double[] starsPosition(){
        return Arrays.copyOf(starPosArray,starPosArray.length);
    }

}
