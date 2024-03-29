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
public enum HygDatabaseLoader implements StarCatalogue.Loader {
    INSTANCE();

    /**
     * @see StarCatalogue.Loader#load(InputStream, StarCatalogue.Builder)
     */
    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {
        try(InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.US_ASCII)) {
            BufferedReader br = new BufferedReader(isr);
            br.readLine(); //ignore first line
            String line;
            while ((line = br.readLine()) != null) {
                String[] starArray = line.split(",");

                int hipId;
                if (starArray[1].equals("")) {
                    hipId = 0;
                } else {
                    hipId = Integer.parseInt(starArray[1]);
                }

            /*
            to determine the name
            first we assign the proper column to the sName var.
            If sName var is still empty
            we fill it with bayer index (or ? by default), a blank
            space and the con index
             */
                String sName = starArray[6];
                if (sName.equals("")) {
                    if (starArray[27].equals("")) {
                        sName = "?";
                    }
                    sName = sName + starArray[27] + " " + starArray[29];
                }

                float magnitude;
                if (!starArray[13].equals("")) {
                    magnitude = Float.parseFloat(starArray[13]);
                } else {
                    magnitude = 0;
                }

                float colorIndex;
                if (!starArray[16].equals("")) {
                    colorIndex = Float.parseFloat(starArray[16]);
                } else {
                    colorIndex = 0;
                }

                Star star = new Star(hipId, sName, EquatorialCoordinates.of(Double.parseDouble(starArray[23]), Double.parseDouble(starArray[24])), magnitude, colorIndex);
                builder.addStar(star);
            }
        }

    }
}
