package ch.epfl.rigel.gui;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.scene.paint.Color;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Get the color of a BlackBody given it's temperature
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public class BlackBodyColor {

    private final static String FILE_NAME = "/bbr_color.txt";
    //private final static Map<Integer, Color> TEMP_COLOR_MAP = fileToMap();
    private final static List<Color> LIST_COLOR = fileToMap();

    private BlackBodyColor(){}

    //TODO ask for return statement outside of try catch statement
    public static Color colorForTemperature(double temp) {
        Preconditions.checkInInterval(ClosedInterval.of(1000,40000),temp);

        temp = temp/100.;
        temp = Math.round(temp);
        int temp2 = (int)(temp-10);
        temp = temp*100;

        //return TEMP_COLOR_MAP.get((int)temp);
        return LIST_COLOR.get(temp2);
    }

    private static List<Color> fileToMap(){ //Map<Integer, Color>
        InputStream colorStream = BlackBodyColor.class.getResourceAsStream(FILE_NAME);
        InputStreamReader isr = new InputStreamReader(colorStream, StandardCharsets.US_ASCII);
        BufferedReader br = new BufferedReader(isr);

        //TODO ask if better map or list
        Map<Integer, Color> integerColorMap = new HashMap<>();
        List<Color> colorIndex2 = new ArrayList<>();
        try {
            String line;
            int temperature = 1000;
            while ((line = br.readLine())!=null) {
                if (line.charAt(0) != '#') {
                    if (line.charAt(10) == '1') { //10deg
                        if(line.substring(80).equals("#474545")){
                            System.out.println("Test");
                        }
                        integerColorMap.put(temperature, Color.web(line.substring(80))); //, 87
                        colorIndex2.add(Color.web(line.substring(80)));
                        temperature = temperature + 100;
                    }
                }
            }

        } catch (IOException e){
            throw new UncheckedIOException(e);
        }
        return colorIndex2;
    }
}
