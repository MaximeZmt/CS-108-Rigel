package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyEquatorialCoordinatesTest{

    //constructor
    @Test
    void constructorFailsOnInvalidDec(){
        assertThrows(IllegalArgumentException.class, () -> {
            EquatorialCoordinates.of(2.3457,-91);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            EquatorialCoordinates.of(Angle.ofDeg(360),Angle.ofDeg(10));
        });
    }

    @Test
    void constructorWorksOnTrivialCoordinates(){
        assertDoesNotThrow(()->EquatorialCoordinates.of(0,0));
    }

    @Test
    void toStringWorksOnRandomCoordinates(){
        EquatorialCoordinates ec = EquatorialCoordinates.of(4.65444,0.56);
        assertEquals("(ra=17.7787h, dec=32.0856°)", ec.toString());
    }

    @Test
    void raAndHrWorks(){
        EquatorialCoordinates ec = EquatorialCoordinates.of(4.65444,0.56);
        assertEquals(ec.raDeg(), Angle.toDeg(ec.ra()));
        EquatorialCoordinates ec2 = EquatorialCoordinates.of(Angle.ofDeg(244.059),Angle.ofDeg(27.1606));
        assertEquals(16.2706,ec2.raHr(),1e-10);
        assertEquals(244.059,ec2.raDeg());
        assertEquals(Angle.ofDeg(244.059),ec2.ra());
        assertEquals(27.1606,ec2.decDeg());
        assertEquals(Angle.ofDeg(27.1606),ec2.dec());
    }


}