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
        long numberOfStar = br.lines().count();
        br.readLine(); // remove first line
        for (long i = 1; i<numberOfStar;i++){
            String line = br.readLine();
            String[] sarray = line.split(",");
        }
        String s = br.readLine();
        System.out.println(s);
        s = br.readLine();
        String[] sarray = s.split(",");
        System.out.println(s);
        String sName = sarray[6];
        if (sName==null){
            sName = sarray[27]+"? "+sarray[29];
        }

        float magnitude;
        if (sarray[13]==null){
            magnitude = Float.valueOf(sarray[13]);
        }else{
            magnitude = 0;
        }

        float colorIndex;
        if (sarray[16]==null){
            colorIndex = Float.valueOf(sarray[16]);
        }else{
            colorIndex = 0;
        }


        Star star = new Star(Integer.valueOf(sarray[1]),sName, EquatorialCoordinates.of(Double.valueOf(sarray[23]),Double.valueOf(sarray[24])),magnitude,colorIndex);
        builder.addStar(star);
        //StarCatalogue sc = new StarCatalogue();
    }
}
