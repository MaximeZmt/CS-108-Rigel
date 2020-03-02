package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyGeographicCoordinatesTest {

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

    @Test
    void isValidLongDeg(){
        assertEquals(true, GeographicCoordinates.isValidLonDeg(-180));
        assertEquals(false, GeographicCoordinates.isValidLonDeg(-181));
        assertEquals(false, GeographicCoordinates.isValidLonDeg(180));
        assertEquals(true, GeographicCoordinates.isValidLonDeg(179));
        assertEquals(true, GeographicCoordinates.isValidLonDeg(0));

    }

    @Test
    void isValidLatDeg() {
        assertEquals(true, GeographicCoordinates.isValidLatDeg(-90));
        assertEquals(true, GeographicCoordinates.isValidLatDeg(90));
        assertEquals(false, GeographicCoordinates.isValidLatDeg(91));
        assertEquals(false, GeographicCoordinates.isValidLatDeg(-91));
        assertEquals(true, GeographicCoordinates.isValidLatDeg(0));
    }

}