package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EclipticToEquatorialConversionTest {

    @Test
    void EclipticToEquatorialConversion() {


        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(ZonedDateTime.now());
        EquatorialCoordinates ec = etec.apply(EclipticCoordinates.of(Angle.ofHr(2.7625), Angle.ofDMS(34, 54, 66.334765)));
        assertEquals(0.414118,ec.ra());
    }

}