package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.stream.Stream;


public enum HygDatabaseLoader implements StarCatalogue.Loader { // public et immuable
    INSTANCE();


    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        BufferedReader br = new BufferedReader(isr); //TODO Check if this is the correct syntax
        Stream<String> lineOfStar = br.lines();
        Iterator i = lineOfStar.iterator();
        i.next(); // remove first one
        int c = 0;
        while (i.hasNext()){
            c++;
            String line = i.next().toString();
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
            //System.out.println(star.toString());
            builder.addStar(star);
        }
        System.out.println(c);

    }
}
