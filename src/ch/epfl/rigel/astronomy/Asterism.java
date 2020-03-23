package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;

import java.util.List;


/**
 * Represents an Asterism (List of stars)
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class Asterism {
    private final List<Star> starList;

    /**
     * Public constructor of an asterism
     * @param stars Take a List of individual star to build it
     * @throws IllegalArgumentException Throw an error if there is no star in the object
     */
    public Asterism(List<Star> stars){
        Preconditions.checkArgument(!stars.isEmpty());
        starList = stars;
    }


    /**
     * Getter of the star list that compose the Asterism
     * @return a defensive copy of the Star List
     */
    public List<Star> stars(){
        //return a defensive copy of the StarList, because it's an immutable class
        return List.copyOf(starList);
    }

}
