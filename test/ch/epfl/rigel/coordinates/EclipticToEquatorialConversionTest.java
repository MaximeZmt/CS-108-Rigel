package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EclipticToEquatorialConversionTest {

    @Test
    void applyWorksOnNullCoordinates() {
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(ZonedDateTime.now());
        EquatorialCoordinates conv = etec.apply(EclipticCoordinates.of(Angle.ofHr(0),Angle.ofDMS(0,0,0)));
        assertEquals(0, conv.ra());
        assertEquals(0, conv.dec());
    }

    @Test
    void applyWorks() { //TODO ask for precisions/accuracy
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(ZonedDateTime.now());
        EquatorialCoordinates conv = etec.apply(EclipticCoordinates.of(Angle.ofDeg(139.6861111), Angle.ofDeg(4.875277778)));
        assertEquals(143.7225092, Angle.toDeg(conv.ra()), 1e-3);
        assertEquals(19.53569924, Angle.toDeg(conv.dec()), 1e-2);
    }


}