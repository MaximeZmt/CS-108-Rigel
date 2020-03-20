package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public final class StarCatalogue {

    private final List<Star> stars;
    private final List<Asterism> asterisms;
    private final Map<Asterism, List<Integer>> map;

    public StarCatalogue(List<Star> stars, List<Asterism> asterisms){
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
        Set<Asterism> ll = map.keySet();
        return Collections.unmodifiableSet(ll);
    }

    public List<Integer> asterismIndices(Asterism asterism){
        Preconditions.checkArgument(this.asterisms.contains(asterism));
        return map.get(asterism);
    }

    public interface Loader{
        public abstract void load(InputStream inputStream, Builder builder) throws IOException;
    }

    public final static class Builder{

        //StarCatalogue starCatalogue;
        private final List<Star> starList;
        private final List<Asterism> asterismList;

        //TODO check if we have to make a constructor or not -> has been asked and yes: by default constructor mean without argument
        public Builder(){
            starList = new ArrayList<>();
            asterismList = new ArrayList<>();
            //starCatalogue = new StarCatalogue(starList,asterismList);
        }

        public Builder addStar(Star star){
            starList.add(star);
            return this;
        }

        public List<Star> stars(){
            //TODO check if really immuable and unmodifiable
            return Collections.unmodifiableList(starList);
        }

        public Builder addAsterism(Asterism asterism){
            asterismList.add(asterism);
            return this;
        }

        public List<Asterism> asterisms(){
            return Collections.unmodifiableList(asterismList);
        }

        public Builder loadFrom(InputStream inputStream, Loader loader) throws IOException{
            //TODO check if correct
            loader.load(inputStream, this);
            return this;
        }

        public StarCatalogue build(){
            return new StarCatalogue(starList, asterismList);
        }

    }
}
