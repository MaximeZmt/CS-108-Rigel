package ch.epfl.rigel.gui;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * [description text]
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
class BlackBodyColorTest {

    @Test
    void colorForTemperatureWorksForRandomTemperature(){
        assertEquals(Color.web("#a3c2ff"), BlackBodyColor.colorForTemperature(24100));
        assertEquals(Color.web("#ff932c"), BlackBodyColor.colorForTemperature(2190.546));
    }
}
