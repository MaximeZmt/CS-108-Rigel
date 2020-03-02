package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyMoonTest {

    @Test
    void testOnPrint(){
        Moon m = new Moon(EquatorialCoordinates.of(0,0),0,0,0.3752f);
        System.out.println(m.toString());
    }
}