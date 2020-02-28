package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EquatorialToHorizontalConversionTest {

    @Test
    void applyWorks(){
        /*
                ZonedDateTime tps = ZonedDateTime.of(

        );

         */
        EquatorialToHorizontalConversion ethc = new EquatorialToHorizontalConversion(ZonedDateTime.now(),
                GeographicCoordinates.ofDeg(0,52));
        HorizontalCoordinates coord = ethc.apply(EquatorialCoordinates.of(Angle.ofDeg(87.93333),Angle.ofDeg(23.21944)));
        assertEquals(Angle.ofDeg(283), Angle.toDeg(coord.az()),10e-3);
        assertEquals(Angle.ofDeg(19), Angle.toDeg(coord.alt()),10e-3);
    }
}

