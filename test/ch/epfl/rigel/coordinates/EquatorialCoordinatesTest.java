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
        EquatorialCoordinates ec = EquatorialCoordinates.of(21.3457,28.654);
        assertEquals("(ra=21.3457h, dec=28.6540°)", ec.toString());
    }

    @Test
    void raWorks(){
        EquatorialCoordinates ec = EquatorialCoordinates.of(21.3457,28.654);
        assertEquals(ec.raDeg(), Angle.toDeg(ec.ra()));
    }


}