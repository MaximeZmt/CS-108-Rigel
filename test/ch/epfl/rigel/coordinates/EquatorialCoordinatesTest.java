package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquatorialCoordinatesTest{

    //constructor
    @Test
    void constructorFailsOnInvalidDec(){
        assertThrows(IllegalArgumentException.class, () -> {
            EquatorialCoordinates.of(2.3457,-91);
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
    void raWorks(){
        EquatorialCoordinates ec = EquatorialCoordinates.of(4.65444,0.56);
        assertEquals(ec.raDeg(), Angle.toDeg(ec.ra()));
    }


}