package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HorizontalCoordinatesTest {

    @Test
    void trivialHorizontalCoordinatesInRadDegCreations(){
        HorizontalCoordinates coordRad = HorizontalCoordinates.of(Angle.ofDeg(60), Angle.ofDeg(60));// create coord tuple with rad coord
        HorizontalCoordinates coordDeg = HorizontalCoordinates.ofDeg(60,60);// create coord tuple with deg coord
        assertEquals(coordRad.az(),coordDeg.az());
        assertEquals(coordRad.azDeg(),coordDeg.azDeg());
        assertEquals(coordRad.alt(),coordDeg.alt());
        assertEquals(coordRad.altDeg(),coordDeg.altDeg());
        assertEquals(60, coordRad.lonDeg(),1e-10);
        assertEquals(60, coordRad.latDeg(),1e-10);
    }

    @Test
    void throwAnErrorWithLimitValue(){
        assertThrows(IllegalArgumentException.class,()->{
            HorizontalCoordinates coordDeg = HorizontalCoordinates.ofDeg(360,50);
        });
        assertThrows(IllegalArgumentException.class,()->{
            HorizontalCoordinates coordDeg = HorizontalCoordinates.ofDeg(50,90.1);
        });
        assertThrows(IllegalArgumentException.class,()->{
            HorizontalCoordinates coordDeg = HorizontalCoordinates.ofDeg(50,-90.1);
        });
        assertDoesNotThrow(()->{
            HorizontalCoordinates coordDeg = HorizontalCoordinates.ofDeg(359,90);
        });
        assertDoesNotThrow(()->{
            HorizontalCoordinates coordDeg = HorizontalCoordinates.ofDeg(359,-90);
        });
    }

    @Test
    void correctStringBuilding(){
        assertEquals("(az=350.0000°, alt=7.2000°)",HorizontalCoordinates.ofDeg(350, 7.2).toString());
        assertEquals("(az=0.0000°, alt=0.0000°)", HorizontalCoordinates.ofDeg(0,0).toString());
    }

    @Test
    void azOctantNameVerif(){
        assertEquals("N",HorizontalCoordinates.ofDeg(22.49999999999, -54).azOctantName("N", "E", "S", "O"));
        assertEquals("N",HorizontalCoordinates.ofDeg(337.5, 10).azOctantName("N", "E", "S", "O"));
        assertEquals("NO",HorizontalCoordinates.ofDeg(335, 0).azOctantName("N", "E", "S", "O"));
        assertEquals("N",HorizontalCoordinates.ofDeg(17, 0).azOctantName("N", "E", "S", "O"));
        assertEquals("NE",HorizontalCoordinates.ofDeg(45, 0).azOctantName("N", "E", "S", "O"));
        assertEquals("E",HorizontalCoordinates.ofDeg(67.5, 0).azOctantName("N", "E", "S", "O"));
        assertEquals("SE",HorizontalCoordinates.ofDeg(112.5,0).azOctantName("N", "E", "S", "O"));
        assertEquals("S",HorizontalCoordinates.ofDeg(180, 0).azOctantName("N", "E", "S", "O"));
        assertEquals("SO",HorizontalCoordinates.ofDeg(225, 0).azOctantName("N", "E", "S", "O"));
        assertEquals("O",HorizontalCoordinates.ofDeg(292.4, 0).azOctantName("N", "E", "S", "O"));
    }

    @Test
    void angularDistanceCheck(){
        HorizontalCoordinates coord1 = HorizontalCoordinates.ofDeg(6.5682,46.5183);
        HorizontalCoordinates coord2 = HorizontalCoordinates.ofDeg(8.5476,47.3763);
        assertEquals(0.0279,coord1.angularDistanceTo(coord2),1e-4);
    }

}