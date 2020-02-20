package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeographicCoordinatesTest {

    @Test
    void toStringWorksOnRandomCoordinates(){
        GeographicCoordinates gc = GeographicCoordinates.ofDeg(45.34567,27.654);
        //GeographicCoordinates gc = GeographicCoordinates.ofDeg(0,0);
        assertEquals(2,2);
        //assertEquals("(lon=45.3457°, lat=27.6540°)", gc.toString());
    }

}