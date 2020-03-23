package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MyMoonModelTest {
    @Test
    void MoonModelTest(){
        CelestialObjectModel<Moon> moonO = MoonModel.MOON;
        ZonedDateTime zdt = ZonedDateTime.of(
                2003,
                9,
                1,
                0,
                0,
                0,
                0,
                ZoneId.of("UTC")
        );
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(zdt);
        Moon moon = moonO.at(Epoch.J2010.daysUntil(zdt),etec);
        EquatorialCoordinates ec = etec.apply(EclipticCoordinates.of(Angle.ofDeg(214.862515),Angle.ofDeg(1.716257)));
        assertEquals(ec.dec(),moon.equatorialPos().dec(),1e-8);
        assertEquals(ec.ra(),moon.equatorialPos().ra(),1e-8);
    }
}