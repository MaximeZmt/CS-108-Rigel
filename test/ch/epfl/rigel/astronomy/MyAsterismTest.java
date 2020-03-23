package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyAsterismTest {

    @Test
    void constructorOnEmptyList(){
        assertThrows(IllegalArgumentException.class,()->{
            Asterism a1 = new Asterism(new ArrayList<Star>());
            Asterism a2 = new Asterism(null);
        });
    }

    @Test
    void trivialTest(){
        Star s = new Star(0,"test", EquatorialCoordinates.of(0,0),0,0);
        Asterism a = new Asterism(new ArrayList<>(List.of(s)));
        assertEquals(s,a.stars().get(0));
    }

}