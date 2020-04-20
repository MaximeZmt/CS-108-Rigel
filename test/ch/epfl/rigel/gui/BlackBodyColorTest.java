package ch.epfl.rigel.gui;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class BlackBodyColorTest {

    @Test
    void colorForTemperatureWorksForRandomTemperature(){
        System.out.println(Color.web("#ff3800"));
        System.out.println(BlackBodyColor.colorForTemperature(1050));
        assertEquals(Color.web("#a3c2ff"), BlackBodyColor.colorForTemperature(24100));
        assertEquals(Color.web("#ff932c"), BlackBodyColor.colorForTemperature(2190.546));

    }

    @Test
    void onOutIntervalValue(){
        assertThrows(IllegalArgumentException.class, ()->{
            BlackBodyColor.colorForTemperature(999);
        });
        assertThrows(IllegalArgumentException.class, ()->{
            BlackBodyColor.colorForTemperature(40001);
        });
        assertDoesNotThrow(()->{
            BlackBodyColor.colorForTemperature(1000);
            BlackBodyColor.colorForTemperature(40000);
        });
    }
}
