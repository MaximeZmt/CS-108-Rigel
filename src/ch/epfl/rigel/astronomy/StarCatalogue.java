package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import javafx.util.Builder;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public final class StarCatalogue {

    private final List<Star> stars;
    private final List<Asterism> asterisms;
    private final Map<Asterism, List<Integer>> map;

    StarCatalogue(List<Star> stars, List<Asterism> asterisms){
        //TODO check if double for is good
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

    public List<Star> stars(){
        return stars;
    }

    public Set<Asterism> asterisms(){
        //TODO check if correct way of doing

        return Collections.unmodifiableSet(Set.copyOf(asterisms));
    }

    public List<Integer> asterismIndices(Asterism asterism){
        Preconditions.checkArgument(this.asterisms.contains(asterism));
        //TODO check if correct
        List<Integer> list = new ArrayList<>();
        for (Star s : asterism.stars()){
            list.add(stars.indexOf(s));
        }
        return list;
    }

    public interface Loader{
        public abstract void load(InputStream inputStream, Builder builder) throws IOException;
    }
}
