package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MySunTest {
    @Test
    void sunFailsOnNullEclipticPosition(){
        assertThrows(NullPointerException.class,()->{
            Sun s = new Sun(null, EquatorialCoordinates.of(3.4,1), 34f, 45);
        });
    }

    @Test
    void nameIsSoleil(){
        Sun s = new Sun(EclipticCoordinates.of(0.4,0.5), EquatorialCoordinates.of(3.4,1), 34f, 45);
        assertEquals("Soleil", s.name());
    }

    @Test
    void magnitudeIsCorrect(){
        Sun s = new Sun(EclipticCoordinates.of(0.4,0.5), EquatorialCoordinates.of(3.4,1), 34f, 45);
        assertEquals(-26.7f, s.magnitude());
    }
}