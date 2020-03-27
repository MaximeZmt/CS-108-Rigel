package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class MySunModelTest {

    @Test
    void atWorksOnRandomValues(){
        double a = SunModel.SUN.at(
                27 + 31,
                new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(
                        2010,  Month.FEBRUARY, 27),
                        LocalTime.of(0,0), ZoneOffset.UTC))).equatorialPos().ra();

        double b = SunModel.SUN.at(
                -2349,
                new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003, Month.JULY,
                        27),
                        LocalTime.of(0, 0, 0, 0),
                        ZoneOffset.UTC))).equatorialPos().raHr();

        double c = SunModel.SUN.at(
                -2349,
                new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003, Month.JULY,
                        27),
                        LocalTime.of(0, 0, 0, 0),
                        ZoneOffset.UTC))).equatorialPos().decDeg();


        assertEquals(5.9325494700300885, a);
        assertEquals(8.392682808297807, b);
        assertEquals(19.35288373097352, c);
    }

    @Test
    void cambridgeTestValueP105(){
        CelestialObjectModel<Sun> sunO = SunModel.SUN;
        ZonedDateTime zdt = ZonedDateTime.of(
                2003,
                07,
                27,
                0,
                0,
                0,
                0,
                ZoneId.of("UTC")
        );
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(zdt);
        Sun s = sunO.at(Epoch.J2010.daysUntil(zdt),etec);
        EquatorialCoordinates ec = etec.apply(EclipticCoordinates.of(Angle.ofDeg(123.5806048),Angle.ofDeg(0)));
        assertEquals(s.equatorialPos().ra(),ec.ra(),1e-7);
        assertEquals(s.equatorialPos().dec(),ec.dec(),1e-7);
    }

    @Test
    void cambridgeTestValueP110ForAngularSize(){
        CelestialObjectModel<Sun> sunO = SunModel.SUN;
        ZonedDateTime zdt = ZonedDateTime.of(
                1988,
                07,
                27,
                0,
                0,
                0,
                0,
                ZoneId.of("UTC")
        );
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(zdt);
        Sun s = sunO.at(Epoch.J2010.daysUntil(zdt),etec);
        assertEquals(Angle.ofDMS(0,31,30),s.angularSize(),10e-7);
    }


    @Test
    void sunValueTestedWithCambridgeSpreadsheet(){
        CelestialObjectModel<Sun> sunO = SunModel.SUN;
        ZonedDateTime zdt = ZonedDateTime.of(
                2000,
                06,
                16,
                0,
                0,
                0,
                0,
                ZoneId.of("UTC")
        );
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(zdt);
        Sun s = sunO.at(Epoch.J2010.daysUntil(zdt),etec);
        EquatorialCoordinates ec = etec.apply(EclipticCoordinates.of(Angle.ofDeg(85.18471813),0));
        assertEquals(ec.ra(),s.equatorialPos().ra(),1e-7);
        assertEquals(ec.dec(),s.equatorialPos().dec(),1e-8);
    }




}