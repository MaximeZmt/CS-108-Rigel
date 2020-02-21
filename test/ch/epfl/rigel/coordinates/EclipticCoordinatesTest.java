package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EclipticCoordinatesTest {

    @Test
    void trivialTestEclipticCoordinatesMethodGen(){
        EclipticCoordinates coord1 = EclipticCoordinates.of()


        HorizontalCoordinates coordRad = HorizontalCoordinates.of(Angle.ofDeg(60), Angle.ofDeg(60));// create coord tuple with rad coord
        HorizontalCoordinates coordDeg = HorizontalCoordinates.ofDeg(60,60);// create coord tuple with deg coord
        assertEquals(coordRad.az(),coordDeg.az());
        assertEquals(coordRad.azDeg(),coordDeg.azDeg());
        assertEquals(coordRad.alt(),coordDeg.alt());
        assertEquals(coordRad.altDeg(),coordDeg.altDeg());
        assertEquals(60, coordRad.lonDeg(),1e-10);
        assertEquals(60, coordRad.latDeg(),1e-10);
    }

}