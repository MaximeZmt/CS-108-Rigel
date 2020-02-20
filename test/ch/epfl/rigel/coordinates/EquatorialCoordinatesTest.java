package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquatorialCoordinatesTest{


    @Test
    void toStringWorksOnRandomCoordinates(){
        EquatorialCoordinates gc = EquatorialCoordinates.of(21.3457,28.654);
        assertEquals("(ra=21.3457h, dec=28.6540°)", gc.toString());
    }



}