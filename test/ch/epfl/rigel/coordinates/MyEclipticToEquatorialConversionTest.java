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
                2020,
                3,
                6,
                11,
                35,
                0,
                0,
                ZoneId.of("UTC"))

        );

        EquatorialCoordinates conv = etec.apply(
                EclipticCoordinates.of(Angle.ofDMS(139,41,10), Angle.ofDMS(4,52,31)));

        assertEquals(Angle.ofDMS(19,32,3.5),conv.dec(), 1e-5);
        assertEquals(Angle.ofHr(9.581452778), conv.ra(),1e-6);
    }

    @Test
    void applyWorks2WCamb(){
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

        EquatorialCoordinates ec = etec.apply(EclipticCoordinates.of(Angle.ofDMS(139,41,10),Angle.ofDMS(4,52,31)));
        assertEquals(143.722173,ec.raDeg(),1e-6);
        assertEquals(19.535003,ec.decDeg(),1e-6);


    }
    @Test
    void ecEqualsThrowsUOE() {
        ZonedDateTime tps = ZonedDateTime.of(
                1980,
                04,
                22,
                14,
                36,
                51,
                67,
                ZoneId.of("UTC"));
        assertThrows(UnsupportedOperationException.class, () -> {
            EclipticToEquatorialConversion etc = new EclipticToEquatorialConversion(tps);
            etc.equals(null);
        });
    }

    @Test
    void ecHashCodeThrowsUOE() {
        ZonedDateTime tps = ZonedDateTime.of(
                1980,
                04,
                22,
                14,
                36,
                51,
                67,
                ZoneId.of("UTC"));

        assertThrows(UnsupportedOperationException.class, () -> {
            EclipticToEquatorialConversion etc = new EclipticToEquatorialConversion(tps);
            etc.hashCode();
        });
    }

}