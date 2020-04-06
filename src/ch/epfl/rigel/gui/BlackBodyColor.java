package ch.epfl.rigel.gui;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.scene.paint.Color;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


/**
 * [fillTxt]
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public class BlackBodyColor {

    private final static String FILE_NAME = "/bbr_color.txt";
    private final static Map<Integer, Color> TEMP_COLOR_MAP = fileToMap();

    private BlackBodyColor(){}

    //TODO ask for return statement outside of try catch statement
    public static Color colorForTemperature(double temp) {
        Preconditions.checkInInterval(ClosedInterval.of(1000,40000),temp);

        temp = temp/100.;
        temp = Math.round(temp);
        temp = temp*100;

        return TEMP_COLOR_MAP.get((int)temp);
    }

    private static Map<Integer, Color> fileToMap(){
        InputStream colorStream = BlackBodyColor.class.getResourceAsStream(FILE_NAME);
        InputStreamReader isr = new InputStreamReader(colorStream, StandardCharsets.US_ASCII);
        BufferedReader br = new BufferedReader(isr);

        //TODO ask if better map or list
        Map<Integer, Color> integerColorMap = new HashMap<>();
        try {
            String line;
            int temperature = 1000;
            while ((line = br.readLine())!=null) {
                if ((line.charAt(10) == '1') && (line.charAt(0) != '#')){
                    integerColorMap.put(temperature, Color.web(line.substring(80,86)));
                    temperature = temperature+100;
                }
            }

        } catch (IOException e){
            throw new UncheckedIOException(e);
        }
        return integerColorMap;
    }
}
