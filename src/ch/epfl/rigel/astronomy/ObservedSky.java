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

public class ObservedSky {

    private final Sun sunInstance;
    private final CartesianCoordinates sunCartCoordinates;

    private final Moon moonInstance;
    private final CartesianCoordinates moonCartCoordinates;

    private final List<Planet> planetsList;
    private final double[] planetPosArray;

    private final List<Star> starList;
    private final double[] starPosArray;

    private final StarCatalogue starCatalogue;
    //TODO Finish javadoc
    /**
     *
     * @param zdt
     * @param observPos
     * @param stereoProj
     * @param starCatalogue
     */
    public ObservedSky(ZonedDateTime zdt, GeographicCoordinates observPos, StereographicProjection stereoProj, StarCatalogue starCatalogue){
        this.starCatalogue = starCatalogue;
         //TODO ask if ETEC is a good name or not
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(zdt);
        EquatorialToHorizontalConversion ethc = new EquatorialToHorizontalConversion(zdt,observPos);

        //sun
        sunInstance = SunModel.SUN.at(Epoch.J2010.daysUntil(zdt),etec);
        sunCartCoordinates = stereoProj.apply(ethc.apply(sunInstance.equatorialPos()));

        //moon
        moonInstance = MoonModel.MOON.at(Epoch.J2010.daysUntil(zdt),etec);
        moonCartCoordinates = stereoProj.apply(ethc.apply(moonInstance.equatorialPos()));

        //planet
        planetsList = new ArrayList<>();
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
    public Sun sun(){
        return sunInstance;
    }

    public CartesianCoordinates sunPosition(){
        return sunCartCoordinates;
    }

    public Moon moon(){
        return moonInstance;
    }

    public CartesianCoordinates moonPosition(){
        return moonCartCoordinates;
    }

    public List<Planet> planets(){
        return List.copyOf(planetsList);
    }

    public double[] planetPositions(){
        return Arrays.copyOf(planetPosArray,planetPosArray.length); // list de 14 coord pos 0: x planet 1, pos 1: y planet 1, ...
    }

    public List<Star> stars(){
        return List.copyOf(starList);
    }

    public double[] starsPosition(){
        return Arrays.copyOf(starPosArray,starPosArray.length);
    }

    /*
    La classe ObservedSky offre également des méthodes donnant accès aux astérismes du catalogue utilisé, ainsi qu'à la
    liste des index des étoiles d'un astérisme donné. Ces méthodes ne font rien d'autre qu'appeler les méthodes
    correspondantes du catalogue d'étoiles utilisé.
     */

    public Set<Asterism> getAsterism(){ //TODO add get
        return starCatalogue.asterisms();
    }

    public List<Integer> getAsterismIndices(Asterism asterism){
        return starCatalogue.asterismIndices(asterism);
    }

    //asterismAccess method
    //listIndexstar asterismgiven -> both calling starcatalogue method

    public Optional<CelestialObject> objectClosestTo(CartesianCoordinates cc,double maxDist){
        double closestDist = maxDist;
        double tempoDist = 0;
        Optional<CelestialObject> co = Optional.empty(); //TODO may use Optional.Empty or Optional.of
        //sun
        tempoDist = dist(sunPosition().x(),cc.x(),sunPosition().y(),cc.y());
        if (tempoDist<closestDist){
            co = Optional.of(sunInstance);
            closestDist = tempoDist;
        }

        //moon
        tempoDist = dist(moonPosition().x(),cc.x(),moonPosition().y(),cc.y());
        if(tempoDist<closestDist){
            co = Optional.of(moonInstance);
            closestDist = tempoDist;
        }

        //planet

        for(Planet p : planetsList){
            int index = planetsList.indexOf(p);
            tempoDist = dist(planetPosArray[index*2],cc.x(),planetPosArray[(index*2)+1],cc.y());
            if(tempoDist<closestDist){
                closestDist = tempoDist;
                co = Optional.of(p);
            }
        }


        //stars

        for(Star s : starList){
            int index = starList.indexOf(s);
            tempoDist = dist(starPosArray[index*2],cc.x(),starPosArray[(index*2)+1],cc.y());
            if(tempoDist<closestDist){
                closestDist = tempoDist;
                co = Optional.of(s);
            }
        }
        return co;
    }

    private double dist(double x1, double x2, double y1, double y2){ //TODO Math.hypot()
        return Math.hypot(x1-x2,y1-y2);
    }

}
