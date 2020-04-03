package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(Angle.ofDeg(0.546822),moon.angularSize(),1e-7);
        String str = String.format(Locale.ROOT,"%s (%.1f%%)","Lune",22.5);
        assertEquals(str,moon.toString());
    }

    @Test
    //frama test
    void atRaHrWorksOnRandomValues(){
        double moon = MoonModel.MOON.at(
                -2313,
                new EclipticToEquatorialConversion(
                        ZonedDateTime.of(
                                LocalDate.of(2003,  Month.SEPTEMBER, 1),
                                LocalTime.of(0,0), ZoneOffset.UTC
                        )
                )
        ).equatorialPos().raHr();

        assertEquals(14.211456457835897, moon);
    }

    @Test
    //frama test
    void atDecWorksOnRandomValues(){
        double moon = MoonModel.MOON.at(
                -2313,
                new EclipticToEquatorialConversion(
                        ZonedDateTime.of(
                                LocalDate.of(2003,  Month.SEPTEMBER, 1),
                                LocalTime.of(0,0), ZoneOffset.UTC
                        )
                )
        ).equatorialPos().dec();

        assertEquals(-0.20114171346014934, moon);
    }

    @Test
        //frama test
    void atAngularSizeWorksOnRandomValues(){
        double moon = MoonModel.MOON.at(Epoch.J2010.daysUntil(ZonedDateTime.of(LocalDate.of(1979, 9, 1),LocalTime.of(0, 0),
                ZoneOffset.UTC)), new EclipticToEquatorialConversion(ZonedDateTime.of(
                LocalDate.of(1979, 9, 1),LocalTime.of(0, 0),ZoneOffset.UTC))).
                angularSize();

        assertEquals(0.009225908666849136, moon);
    }
}