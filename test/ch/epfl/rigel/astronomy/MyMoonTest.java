package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyMoonTest {

    @Test
    void testOnPrint(){
        Moon m = new Moon(EquatorialCoordinates.of(0,0),0,0,0.3752f);
        System.out.println(m.toString());
    }

    @Test
    void celestialObjectExceptionTestForMoon(){
        assertThrows(IllegalArgumentException.class,()->{
            Moon m = new Moon(EquatorialCoordinates.of(0,0),-21,0,0.3752f);
        });
        assertThrows(NullPointerException.class,()->{
            Moon m = new Moon(null,0,0,0.3752f);
        });
    }

    @Test
    void moonFailsOnInvalidPhase(){
        assertThrows(IllegalArgumentException.class,()->{
            Moon m = new Moon(EquatorialCoordinates.of(0,0),0,0,-1);
        });
    }

    @Test
    void nameIsLune(){
        Moon m = new Moon(EquatorialCoordinates.of(0,0),0,0,0);
        assertEquals("Lune", m.name());
    }

    @Test
    void moonWorksOnRandomValues(){
        Moon m = new Moon(EquatorialCoordinates.of(0,0),152.2628f,0,0.5f);
        assertEquals(152.2628f,m.angularSize());
    }

}