package ch.epfl.rigel.astronomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public enum AsterismLoader implements StarCatalogue.Loader{

    INSTANCE();

    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        BufferedReader br = new BufferedReader(isr); //TODO Check if this is the correct syntax

        long numberOfAsterism = br.lines().count();
        List<Star> starList = builder.stars();
        for (long n=0 ; n<numberOfAsterism ; ++n){
            List<Star> starListAsterism = new ArrayList<>();
            String[] hipparcosIdListString = br.readLine().split(",");
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
    }
}
