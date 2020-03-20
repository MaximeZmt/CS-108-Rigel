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
        BufferedReader br = new BufferedReader(isr);
        List<Star> starList = builder.stars();
        String line = "";
        while ((line = br.readLine())!=null){
            String[] hipparcosIdListString = line.split(",");
            List<Star> starListAsterism = new ArrayList<>();
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
            builder.addAsterism(new Asterism(starListAsterism));
        }
        isr.close();
    }
}
