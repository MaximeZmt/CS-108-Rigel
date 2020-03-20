package ch.epfl.rigel.astronomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Represents an asterism catalogue loader
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public enum AsterismLoader implements StarCatalogue.Loader{

    INSTANCE();

    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        BufferedReader br = new BufferedReader(isr); //TODO Check if this is the correct syntax

        Stream<String> lineOfAsterism = br.lines();
        Iterator i = lineOfAsterism.iterator();

        //long numberOfAsterism = br.lines().count();
        List<Star> starList = builder.stars();
        //for (long n=0 ; n<numberOfAsterism ; ++n){
        while (i.hasNext()){
            String line = i.next().toString();
            String[] hipparcosIdListString = line.split(",");

            List<Star> starListAsterism = new ArrayList<>();
            //String[] hipparcosIdListString = br.readLine().split(",");
            List<Integer> hipparcosIdListInt = new ArrayList<>();

            for (String s : hipparcosIdListString){
                hipparcosIdListInt.add(Integer.valueOf(s));
            }

            for (int hipparcosId : hipparcosIdListInt){
                for (Star star : starList){
                    if (star.hipparcosId()==hipparcosId){
                        starListAsterism.add(star);
                    }
                }
            }
            //System.out.println(starListAsterism.toString());
            builder.addAsterism(new Asterism(starListAsterism));
        }
        isr.close();
    }
}
