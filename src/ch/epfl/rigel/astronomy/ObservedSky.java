package ch.epfl.rigel.astronomy;


import ch.epfl.rigel.coordinates.*;

import javax.swing.text.html.Option;
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


    /**
     * Constructor Which receive information to simulate the sky
     * @param zdt A ZonedDateTime, is the object containing the time zone, the date and the time.
     * @param observPos The position of the observer around the earth (in latitude and longitude)
     * @param stereoProj The stereographic projection that translate 3d to 2d.
     * @param starCatalogue Object that is containing a list of all the stars and asterism
     */
    public ObservedSky(ZonedDateTime zdt, GeographicCoordinates observPos, StereographicProjection stereoProj, StarCatalogue starCatalogue){
        this.starCatalogue = starCatalogue;
        EclipticToEquatorialConversion eclipToEquatC = new EclipticToEquatorialConversion(zdt);
        EquatorialToHorizontalConversion equatToHorizonC = new EquatorialToHorizontalConversion(zdt,observPos);

        //sun
        sunInstance = SunModel.SUN.at(Epoch.J2010.daysUntil(zdt),eclipToEquatC);
        sunCartCoordinates = stereoProj.apply(equatToHorizonC.apply(sunInstance.equatorialPos()));

        //moon
        moonInstance = MoonModel.MOON.at(Epoch.J2010.daysUntil(zdt),eclipToEquatC);
        moonCartCoordinates = stereoProj.apply(equatToHorizonC.apply(moonInstance.equatorialPos()));

        //planet
        planetsList = new ArrayList<>();
        planetPosArray = new double[14]; //only 7 planet, earth does'nt count
        Iterator pIterator = PlanetModel.ALL.iterator();
        int counter = 0;
        while(pIterator.hasNext()){
            PlanetModel pmodel = (PlanetModel)pIterator.next();
            if(!pmodel.equals(PlanetModel.EARTH)) {
                Planet p = pmodel.at(Epoch.J2010.daysUntil(zdt), eclipToEquatC);
                planetsList.add(p);
                CartesianCoordinates planetTempoCartesianCoord = stereoProj.apply(equatToHorizonC.apply(p.equatorialPos()));
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
            CartesianCoordinates starCartCoord = stereoProj.apply(equatToHorizonC.apply(s.equatorialPos()));
            starPosArray[(counter*2)] = starCartCoord.x();
            starPosArray[(counter*2)+1] = starCartCoord.y();
            counter++;
        }

    }

    /**
     * Getter for the sun Instance at a given time, with a given observer
     * @return an instance of the sun
     */
    public Sun sun(){
        return sunInstance;
    }

    /**
     * Getter for the sun position
     * @return CartesianCoordinates that represent the Sun Position
     */
    public CartesianCoordinates sunPosition(){
        return sunCartCoordinates;
    }

    /**
     * Getter for the Moon instance at a given time, with a given observer.
     * @return an instance of the moon
     */
    public Moon moon(){
        return moonInstance;
    }

    /**
     * Getter for the moon position
     * @return CartesianCoordinates that represent the moon Position
     */
    public CartesianCoordinates moonPosition(){
        return moonCartCoordinates;
    }

    /**
     * Getter for the list containing the Planet List Instance
     * @return a List of Planet Instance
     */
    public List<Planet> planets(){
        return List.copyOf(planetsList);
    }

    /**
     * Getter for the planetPosition
     * @return an array of cartesian Coordinates
     */
    public double[] planetPositions(){
        return Arrays.copyOf(planetPosArray,planetPosArray.length);
        // list de 14 coord pos 0: x planet 1, pos 1: y planet 1, ...
    }

    /**
     * Getter for the List of Star Instance
     * @return a List of Star Instance
     */
    public List<Star> stars(){
        return List.copyOf(starList);
    }

    /**
     * Getter for the starPosition
     * @return an array of cartesian Coordinates containing the pos of the star
     */
    public double[] starsPosition(){
        return Arrays.copyOf(starPosArray,starPosArray.length);
    }


    /**
     * Getter for a set that is containing Asterisms instance
     * @return a set of Asterisms instance
     */
    public Set<Asterism> getAsterism(){
        return starCatalogue.asterisms();
    }

    /**
     * Getter that return given an Asterism the Hipparcosid of the stars that are contained
     * @param asterism An instance of an Asterism
     * @return a List of HipparcosId of the stars
     */
    public List<Integer> getAsterismIndices(Asterism asterism){
        return starCatalogue.asterismIndices(asterism);
    }

    //asterismAccess method
    //listIndexstar asterismgiven -> both calling starcatalogue method

    /**
     * Given cartesian coordinates and a max distance, return the closest celestial object
     * @param cc The cartesian coordinates where we want the object
     * @param maxDist the maximum distance
     * @return An Optional of a CelestialObject that may containing one.
     */
    public Optional<CelestialObject> objectClosestTo(CartesianCoordinates cc,double maxDist){
        double closestDist = maxDist;
        double tempoDist = 0;
        Optional<CelestialObject> co = Optional.empty(); //TODO may use Optional.Empty or Optional.of
        //sun
        tempoDist = dist(sunPosition().x(),cc.x(),sunPosition().y(),cc.y());
        //System.out.println(tempoDist);
        if (tempoDist<closestDist){
            co = Optional.of(sunInstance);
            //System.out.println("SUUUUUUNNNNNNNNNNNNNNNNNNNNNN!");
            closestDist = tempoDist;
        }

        //moon
        tempoDist = dist(moonPosition().x(),cc.x(),moonPosition().y(),cc.y());
        if(tempoDist<closestDist){
            co = Optional.of(moonInstance);
            //System.out.println("MOOOOONNNNNNNNNNNNNNNNNNNN");
            closestDist = tempoDist;
        }

        //planet

        for(Planet p : planetsList){
            int index = planetsList.indexOf(p);
            tempoDist = dist(planetPosArray[index*2],cc.x(),planetPosArray[(index*2)+1],cc.y());
            if(tempoDist<closestDist){
                //System.out.println("PLANET : "+p);
                closestDist = tempoDist;
                co = Optional.of(p);
            }
        }
        //stars
        int index;
        for(Star s : starList){
            index = starList.indexOf(s);
            tempoDist = dist(starPosArray[index*2],cc.x(),starPosArray[(index*2)+1],cc.y());
            if(tempoDist<closestDist){
                //System.out.println("STAR :"+s);
                closestDist = tempoDist;
                co = Optional.of(s);
            }
        }
        //System.out.println(closestDist);
        return co;
    }

    private double dist(double x1, double x2, double y1, double y2){
        return Math.hypot(x1-x2,y1-y2);
    }

}
