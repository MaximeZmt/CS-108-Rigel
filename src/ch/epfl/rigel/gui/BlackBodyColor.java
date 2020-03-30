package ch.epfl.rigel.gui;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.scene.paint.Color;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * [fillTxt]
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public class BlackBodyColor {

    private BlackBodyColor(){}

    //TODO ask for return statement outside of try catch statement
    //temp in kelvin
    public static Color colorForTemperature(double temp) {
        Preconditions.checkInInterval(ClosedInterval.of(1000,40000),temp);

        temp = temp/100.;
        temp = Math.round(temp);
        temp = temp*100;
        String tempString;
        if (temp<10000){
            tempString = " "+temp;
        } else {
            tempString = Double.toString(temp);
        }

        try {
        //TODO check if correct
        InputStream colorStream = BlackBodyColor.class.getResourceAsStream("/bbr_color.txt");
        InputStreamReader isr = new InputStreamReader(colorStream, StandardCharsets.US_ASCII);
        BufferedReader br = new BufferedReader(isr);

        String line;
        while ((line = br.readLine())!=null) {
            if (line.substring(1, 5).equals(tempString)){
                if (line.charAt(10) == '1'){
                    return Color.web(line.substring(80,86));
                }
            }
        }

        } catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }
}
