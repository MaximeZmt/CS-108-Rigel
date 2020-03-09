package ch.epfl.rigel.astronomy;

import java.util.List;

public final class Asterism { //immuable: liste étoile
    private final List<Star> starList;

    public Asterism(List<Star> stars){
        if(stars.isEmpty()){
            throw new IllegalArgumentException();
        }
        starList = stars;
    }

    public List<Star> stars(){
        return List.copyOf(starList); //cause of immuability: has been already asked and confirmed better than vue
    }

}
