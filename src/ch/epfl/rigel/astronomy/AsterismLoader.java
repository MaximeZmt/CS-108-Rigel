package ch.epfl.rigel.astronomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an asterism catalogue loader
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public enum AsterismLoader implements StarCatalogue.Loader{
    INSTANCE();

    /**
     * @see  StarCatalogue.Loader#load(InputStream, StarCatalogue.Builder) 
     */
    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {

        try(InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.US_ASCII)) {

            BufferedReader br = new BufferedReader(isr);

            List<Star> starList = builder.stars();
            String line;
            Map<Integer,Star> mapHipparcosStar = new HashMap<>();

            for (Star starFiller : starList){
                mapHipparcosStar.put(starFiller.hipparcosId(), starFiller);
            }


            while ((line = br.readLine()) != null) {
                String[] hipparcosIdListString = line.split(",");
                List<Star> starListAsterism = new ArrayList<>();

                //converts the hipparcos Id list from String to Integer
                for (String s : hipparcosIdListString) {
                    starListAsterism.add(mapHipparcosStar.get(Integer.valueOf(s)));
                }
                builder.addAsterism(new Asterism(starListAsterism));
            }
        }
    }
}
