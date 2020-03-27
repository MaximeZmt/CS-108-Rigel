package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


/**
 * Represents a HYG catalogue loader
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public enum HygDatabaseLoader implements StarCatalogue.Loader { // public et immuable

    INSTANCE();


    /**
     * @see StarCatalogue.Loader#load(InputStream, StarCatalogue.Builder)
     */
    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        BufferedReader br = new BufferedReader(isr);
        br.readLine(); // remove first one
        String line = "";
        int c = 0;
        while ((line = br.readLine())!=null){
            c++;
            String[] starArray = line.split(",");

            int hipId;
            if(starArray[1].equals("")){
                hipId = 0;
            }else{
                hipId = Integer.valueOf(starArray[1]);
            }

            /*
            to determine the name
            first we assign the proper column to the sName var
            But if sName var still empty
            we fill it with bayer index (or ? by default) a blank
            space and the con index
             */
            String sName = starArray[6];
            if (sName.equals("")){
                if (starArray[27].equals("")){
                    sName = "?";
                }
                sName = sName+starArray[27]+" "+starArray[29];
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
        //System.out.println(c);
        isr.close();

    }
}
