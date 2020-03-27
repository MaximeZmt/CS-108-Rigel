package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Represents a catalogue of stars and asterisms
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class StarCatalogue {

    private final List<Star> stars;
    private final List<Asterism> asterisms;
    private final Map<Asterism, List<Integer>> map;

    /**
     * Builds a catalogue of stars and asterisms
     *
     * @param stars given stars
     * @param asterisms given asterisms
     * @throws IllegalArgumentException if one of the asterisms contains a star that is not in the given list of stars
     */
    public StarCatalogue(List<Star> stars, List<Asterism> asterisms){
        for (Asterism a : asterisms){
            for (Star s : a.stars()){
                Preconditions.checkArgument(stars.contains(s));
            }
        }

        this.stars = stars;
        this.asterisms = asterisms;

        //TODO check
        map = new HashMap<>();
        for (Asterism a : asterisms){
            List<Integer> indexList = new ArrayList<>();
            for (Star s : a.stars()){
                indexList.add(stars.indexOf(s));
            }
            map.put(a, indexList);
        }
    }

    /**
     * Getter for the stars
     *
     * @return stars
     */
    public List<Star> stars(){
        return stars;
    }

    /**
     * Getter for the asterisms
     *
     * @return a set of asterisms
     */
    public Set<Asterism> asterisms(){
        Set<Asterism> ast = map.keySet();
        return Collections.unmodifiableSet(ast);
    }

    /**
     * Returns a list of all the indexes of the star catalogue that are contained in the given asterism
     *
     * @param asterism the given asterism
     * @return list of indexes
     * @throws IllegalArgumentException if Asterism is not contained in the list
     */
    public List<Integer> asterismIndices(Asterism asterism){
        Preconditions.checkArgument(this.asterisms.contains(asterism));
        return map.get(asterism);
    }

    /**
     * Represents a star and asterism catalogue loader
     */
    public interface Loader{

        /**
         * Loads the given stars and/or asterisms of the input stream and adds them to the catalogue builder
         *
         * @param inputStream input stream
         * @param builder current catalogue builder
         * @throws IOException if input/output error occurs
         */
        public abstract void load(InputStream inputStream, Builder builder) throws IOException;
    }

    /**
     * Represents a star catalogue builder
     */
    public final static class Builder{

        //StarCatalogue starCatalogue;
        private final List<Star> starList;
        private final List<Asterism> asterismList;

        /**
         * Default constructor
         */
        //TODO ask if it is like this or do we have to call an interface "Builder" or something
        public Builder(){
            starList = new ArrayList<>();
            asterismList = new ArrayList<>();
        }

        /**
         * Adds the given star to the currently building catalogue
         *
         * @param star given star
         * @return current builder
         */
        public Builder addStar(Star star){
            starList.add(star);
            return this;
        }

        /**
         * Returns an unmodifiable view of the stars of the currently building catalogue
         *
         * @return unmodifiable view of the stars
         */
        public List<Star> stars(){
            //TODO check if really immuable and unmodifiable
            return Collections.unmodifiableList(starList);
        }

        /**
         * Adds the given asterism to the currently building catalogue
         *
         * @param asterism given asterism
         * @return current builder
         */
        public Builder addAsterism(Asterism asterism){
            asterismList.add(asterism);
            return this;
        }

        /**
         * Returns an unmodifiable view of the asterisms of the currently building catalogue
         *
         * @return unmodifiable view of the asterisms
         */
        public List<Asterism> asterisms(){
            return Collections.unmodifiableList(asterismList);
        }

        /**
         * Asks the loader to add to the catalogue the stars and/or asterisms thar are given by the input stream
         *
         * @param inputStream input stream
         * @param loader catalogue loader
         * @return current builder
         * @throws IOException if input/output errors occurs
         */
        public Builder loadFrom(InputStream inputStream, Loader loader) throws IOException{
            //TODO check if correct
            loader.load(inputStream, this);
            return this;
        }

        /**
         * Returns the catalogue of stars and asterisms that have been added thus far to the builder
         *
         * @return catalogue of star and asterism
         */
        public StarCatalogue build(){
            return new StarCatalogue(starList, asterismList);
        }
    }
}
