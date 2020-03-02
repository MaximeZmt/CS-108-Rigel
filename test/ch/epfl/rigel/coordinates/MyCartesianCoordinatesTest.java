package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class MyCartesianCoordinatesTest {

    @Test
    void constructionMethodVerification(){
        double x = 10987.99987;
        double y = -9999999.99999;
        CartesianCoordinates cc = CartesianCoordinates.of(x,y);
        assertEquals(x,cc.x());
        assertEquals(y,cc.y());
    }

    @Test
    void ErrorThrownOnPurpose(){
        CartesianCoordinates cc = CartesianCoordinates.of(0,0);
        assertThrows(UnsupportedOperationException.class,()->{
           cc.hashCode();
        });
        assertThrows(UnsupportedOperationException.class,()->{
            cc.equals(null);
        });
    }

    @Test
    void toStringWorks(){
        double x = 10987.99987;
        double y = -9999999.99999;
        CartesianCoordinates cc = CartesianCoordinates.of(x,y);
        assertEquals(String.format(Locale.ROOT,"(x=%.5f ; y=%.5f)",x,y),cc.toString());
    }
}