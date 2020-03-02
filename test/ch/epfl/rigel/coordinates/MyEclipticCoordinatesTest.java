package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyEclipticCoordinatesTest {

    @Test
    void trivialTestEclipticCoordinatesMethodGen(){
        EclipticCoordinates coord1 = EclipticCoordinates.of(Angle.ofDeg(180),Angle.ofDeg(-89));
        assertEquals(coord1.latDeg(),Angle.toDeg(coord1.lat()));
        assertEquals(coord1.lonDeg(),Angle.toDeg(coord1.lon()));
        assertEquals(180,coord1.lonDeg(),1e-10);
        assertEquals(-89,coord1.latDeg(),1e-10);
    }


    @Test
    void stringOutputTest(){
        assertEquals("(λ=22.5000°, β=18.0000°)",EclipticCoordinates.of(Angle.ofDeg(22.5),Angle.ofDeg(18.0)).toString());
        assertEquals("(λ=0.0000°, β=0.0000°)",EclipticCoordinates.of(0,0).toString());
    }


    @Test
    void throwAnErrorWithLimitValue(){
        assertThrows(IllegalArgumentException.class,()->{
            EclipticCoordinates coordDeg = EclipticCoordinates.of(Angle.ofDeg(360),Angle.ofDeg(0));
        });
        assertThrows(IllegalArgumentException.class,()->{
            EclipticCoordinates coordDeg = EclipticCoordinates.of(Angle.ofDeg(-1),Angle.ofDeg(0));
        });
        assertThrows(IllegalArgumentException.class,()->{
            EclipticCoordinates coordDeg = EclipticCoordinates.of(Angle.ofDeg(10),Angle.ofDeg(91));
        });
        assertThrows(IllegalArgumentException.class,()->{
            EclipticCoordinates coordDeg = EclipticCoordinates.of(Angle.ofDeg(10),Angle.ofDeg(-91));
        });
        assertDoesNotThrow(()->{
            EclipticCoordinates coordDeg = EclipticCoordinates.of(Angle.ofDeg(0),Angle.ofDeg(-90));
        });
        assertDoesNotThrow(()->{
            EclipticCoordinates coordDeg = EclipticCoordinates.of(Angle.ofDeg(359),Angle.ofDeg(90));
        });
        assertDoesNotThrow(()->{
            EclipticCoordinates coordDeg = EclipticCoordinates.of(Angle.ofDeg(10.34983749379473728929),Angle.ofDeg(90));
        });
    }



}