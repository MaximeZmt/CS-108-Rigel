package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeographicCoordinatesTest {

    @Test
    void ConstructorFailsOnInvalidDec(){
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates.ofDeg(2.3457,-91);
        });
    }

    @Test
    void ConstructorWorksOnTrivialCoordinates(){
        assertDoesNotThrow(()->GeographicCoordinates.ofDeg(0,0));
    }

    @Test
    void toStringWorksOnRandomCoordinates(){
        GeographicCoordinates gc = GeographicCoordinates.ofDeg(45.34567,27.654);
        assertEquals("(lon=45.3457°, lat=27.6540°)", gc.toString());
    }

}