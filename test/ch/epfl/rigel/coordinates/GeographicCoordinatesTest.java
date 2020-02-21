package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeographicCoordinatesTest {

    @Test
    void constructorFailsOnInvalidDec(){
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates.ofDeg(10,-91);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates.ofDeg(10,91);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates.ofDeg(-181,0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates.ofDeg(180,0);
        });
        assertDoesNotThrow(()->{
            GeographicCoordinates.ofDeg(-180,0);
        });
        assertDoesNotThrow(()->{
            GeographicCoordinates.ofDeg(-90,90);
        });
    }

    @Test
    void constructorWorksOnTrivialCoordinates(){
        assertDoesNotThrow(()->GeographicCoordinates.ofDeg(0,0));
    }

    @Test
    void toStringWorksOnRandomCoordinates(){
        GeographicCoordinates gc = GeographicCoordinates.ofDeg(45.34567,27.654);
        assertEquals("(lon=45.3457°, lat=27.6540°)", gc.toString());
    }

}