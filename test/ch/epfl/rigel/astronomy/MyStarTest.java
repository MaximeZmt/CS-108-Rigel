package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyStarTest {

    @Test
    void trivialErrorsThrows(){
        assertThrows(IllegalArgumentException.class,()->{
            new Star(-1,"Test", EquatorialCoordinates.of(0,0),0,0);
        });
        assertThrows(IllegalArgumentException.class,()->{
            new Star(-1,"Test", EquatorialCoordinates.of(0,0),0,-0.6f);
        });
        assertThrows(IllegalArgumentException.class,()->{
            new Star(-1,"Test", EquatorialCoordinates.of(0,0),0,5.6f);
        });
    }

    @Test
    void trivialTest(){
        Star s = new Star(0,"TestStar",EquatorialCoordinates.of(0,0),0,5.5f);
        assertEquals(0,s.hipparcosId());
    }

    @Test
    void colorTempTest(){
        Star rigelTestCol = new Star(0,"TestRigel",EquatorialCoordinates.of(0,0),0,-0.03f);
        Star betelgeuseTestCol = new Star(0,"TestBetelgeuse",EquatorialCoordinates.of(0,0),0,1.50f);
        assertEquals(10500,rigelTestCol.colorTemperature(),20); //obtain 10515
        assertEquals(3800, betelgeuseTestCol.colorTemperature(),20); //obtain 3793
    }

}