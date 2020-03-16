package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import javafx.util.Builder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class StarCatalogue {

    private final List<Star> stars;
    private final List<Asterism> asterisms;

    StarCatalogue(List<Star> stars, List<Asterism> asterisms){
        //TODO check if double for is good
        for (Asterism a : asterisms){
            for (Star s : a.stars()){
                Preconditions.checkArgument(stars.contains(s));
            }
        }
        this.stars = stars;
        this.asterisms = asterisms;
    }

    public List<Star> stars(){
        return stars;
    }

    public Set<Asterism> asterisms(){
        //TODO check if correct way of doing
        return Set.copyOf(asterisms);
    }

    public List<Integer> asterismIndices(Asterism asterism){
        Preconditions.checkArgument(this.asterisms.contains(asterism));
        return null;
    }

    public interface Loader{
        public abstract void load(InputStream inputStream, Builder builder) throws IOException;
    }
}
