package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MySunModelTest {

    @Test
    void cambridgeTestValueP105(){ //TODO see the delta
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
        assertEquals(Angle.ofDeg(201.159131),Angle.normalizePositive(s.meanAnomaly()),1e-6); //TODO ask why 201.159131 -> 0.000001/2 doesn't work ->related to explanation of monday

        assertEquals(123.580601,s.eclipticPos().lonDeg(),1e-6);
        assertEquals(0,s.eclipticPos().latDeg());

        assertEquals(Angle.ofHr(8+(23./60)+(34./3600)),s.equatorialPos().ra(),1e-4);
        assertEquals(Angle.ofDMS(19,21,10),s.equatorialPos().dec(),1e-4);
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

}