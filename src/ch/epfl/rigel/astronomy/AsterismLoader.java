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
        for (long i=0 ; i<numberOfAsterism ; ++i){
            String[] hipparcosIdListString = br.readLine().split(",");
            List<Integer> hipparcosIdListInt = new ArrayList<>();
            for (String s : hipparcosIdListString){
                hipparcosIdListInt.add(Integer.valueOf(s));
            }
        }
        builder.addAsterism();
    }
}
