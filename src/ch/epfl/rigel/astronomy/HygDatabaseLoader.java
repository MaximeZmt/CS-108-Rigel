package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public enum HygDatabaseLoader implements StarCatalogue.Loader { // public et immuable
    INSTANCE();


    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        BufferedReader br = new BufferedReader(isr); //TODO Check if this is the correct syntax
        long numberOfStar = 5068;//br.lines().count();
        System.out.println(numberOfStar);
        String s = br.readLine(); // remove first line
        for (long i = 1; i<numberOfStar;i++){
            String line = br.readLine();
            String[] starArray = line.split(",");

            int hipId;
            if(starArray[1].equals("")){
                hipId = 0;
            }else{
                hipId = Integer.valueOf(starArray[1]);
            }



            String sName = starArray[6];
            if (sName.equals("")){
                sName = starArray[27]+"? "+starArray[29];
            }

            float magnitude;
            if (!starArray[13].equals("")){
                magnitude = Float.valueOf(starArray[13]);
            }else{
                magnitude = 0;
            }

            float colorIndex;
            if (!starArray[16].equals("")){
                colorIndex = Float.valueOf(starArray[16]);
            }else{
                colorIndex = 0;
            }

            Star star = new Star(hipId,sName, EquatorialCoordinates.of(Double.valueOf(starArray[23]),Double.valueOf(starArray[24])),magnitude,colorIndex);
            builder.addStar(star);
        }

    }
}
