package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MyEclipticToEquatorialConversionTest {

    @Test
    void applyWorksOnNullCoordinates() {
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(ZonedDateTime.now());
        EquatorialCoordinates conv = etec.apply(EclipticCoordinates.of(Angle.ofHr(0),Angle.ofDMS(0,0,0)));
        assertEquals(0, conv.ra());
        assertEquals(0, conv.dec());
    }

    @Test
    void applyWorks() {
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(ZonedDateTime.of(
                2009,
                7,
                6,
                0,
                0,
                0,
                0,
                ZoneId.of("UTC"))

        );

        EquatorialCoordinates conv = etec.apply(
                EclipticCoordinates.of(Angle.ofDMS(139,41,10), Angle.ofDMS(4,52,31)));
        assertEquals(9.581478170200256, conv.raHr());
        assertEquals(0.34095012064184566, conv.dec());
    }


}