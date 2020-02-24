package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EquatorialToHorizontalConversionTest {
    @Test
    void applyWorksOnNullCoordinates(){
        EquatorialToHorizontalConversion ethc = new EquatorialToHorizontalConversion(ZonedDateTime.now(),
                GeographicCoordinates.ofDeg(0,0));
        HorizontalCoordinates coord = ethc.apply(EquatorialCoordinates.of(0,0));
        assertEquals(0, coord.az());
        assertEquals(0, coord.alt());
    }

    @Test
    void applyWorks(){
        EquatorialToHorizontalConversion ethc = new EquatorialToHorizontalConversion(ZonedDateTime.now(),
                GeographicCoordinates.ofDeg(0,0));
        HorizontalCoordinates coord = ethc.apply(EquatorialCoordinates.of(0,0));
        assertEquals(0, coord.az());
        assertEquals(0, coord.alt());
    }
}