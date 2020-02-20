package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquatorialCoordinatesTest{


    @Test
    void toStringWorksOnRandomCoordinates(){
        EquatorialCoordinates gc = EquatorialCoordinates.of(45.34567,27.654);
        assertEquals("(ra=45.3457°, dec=27.6540°)", gc.toString());
    }



}