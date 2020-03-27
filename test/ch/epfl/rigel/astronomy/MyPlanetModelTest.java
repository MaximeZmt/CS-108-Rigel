package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class MyPlanetModelTest {
    @Test
    void allReturnVenusAtIndex2(){
        PlanetModel venus = PlanetModel.ALL.get(1);
        assertEquals(PlanetModel.VENUS, venus);
    }

    @Test
    void atWorksOnMercuryInferiorPlanet(){
        ZonedDateTime zdt = ZonedDateTime.of(
                2003,
                11,
                22,
                0,
                0,
                0,
                0,
                ZoneId.of("UTC")
        );
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(ZonedDateTime.now());
        Planet mercury = PlanetModel.MERCURY.at(
                Epoch.J2010.daysUntil(zdt), etec);

        assertEquals("Mercure", mercury.name());
        assertEquals("Mercure", mercury.info());
        EquatorialCoordinates ec = etec.apply(EclipticCoordinates.of(Angle.ofDeg(253.929758),Angle.ofDeg((-2.044057))));
        assertEquals(ec.ra(), mercury.equatorialPos().ra(),1e-8);
        assertEquals(ec.dec(), mercury.equatorialPos().dec(),1e-8);
    }

    @Test
    void atWorksOnVenusInferiorPlanet(){
        ZonedDateTime zdt = ZonedDateTime.of(
                2025,
                6,
                27,
                0,
                0,
                0,
                0,
                ZoneId.of("UTC")
        );
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(ZonedDateTime.now());
        Planet venus = PlanetModel.VENUS.at(
                Epoch.J2010.daysUntil(zdt), etec);

        assertEquals("Vénus", venus.name());
        assertEquals("Vénus", venus.info());
        EquatorialCoordinates ec = etec.apply(EclipticCoordinates.of(Angle.ofDeg(51.64269074),Angle.ofDeg((-2.679955299))));
        assertEquals(ec.ra(), venus.equatorialPos().ra(),1e-8);
        assertEquals(ec.dec(), venus.equatorialPos().dec(),1e-8);
    }

    @Test
    void atWorksOnMarsSuperiorPlanet(){
        ZonedDateTime zdt = ZonedDateTime.of(
                2000,
                6,
                16,
                0,
                0,
                0,
                0,
                ZoneId.of("UTC")
        );
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(ZonedDateTime.now());
        Planet mars = PlanetModel.MARS.at(
                Epoch.J2010.daysUntil(zdt), etec);

        assertEquals("Mars", mars.name());
        assertEquals("Mars", mars.info());
        EquatorialCoordinates ec = etec.apply(EclipticCoordinates.of(Angle.ofDeg(89.91150061),Angle.ofDeg((0.773800175))));
        assertEquals(ec.ra(), mars.equatorialPos().ra(),1e-8);
        assertEquals(ec.dec(), mars.equatorialPos().dec(),1e-8);
    }

    @Test
    void atWorksOnJupiterSuperiorPlanet(){ // partially done
        ZonedDateTime zdt = ZonedDateTime.of(
                2003,
                11,
                22,
                0,
                0,
                0,
                0,
                ZoneId.of("UTC")
        );
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(ZonedDateTime.now());
        Planet jupiter = PlanetModel.JUPITER.at(
                Epoch.J2010.daysUntil(zdt), etec);

        assertEquals("Jupiter", jupiter.name());
        assertEquals("Jupiter", jupiter.info());
        EquatorialCoordinates ec = etec.apply(EclipticCoordinates.of(Angle.ofDeg(166.310510),Angle.ofDeg(1.036466)));
        assertEquals(ec.ra(), jupiter.equatorialPos().ra(),1e-8);
        assertEquals(ec.dec(), jupiter.equatorialPos().dec(),1e-8);
        //value checked on framapad
        assertEquals(Angle.ofDMS(0,0,35.1), jupiter.angularSize(),Angle.ofDMS(0,0,0.1)/2); //1.7022492829710245E-4 checked with framapad
        assertEquals(-1.986222784, jupiter.magnitude(),1e-2); //-1.9885659217834473 value get on Framapad, same as book but with different precision (need same decimal as book)
    }

    @Test
    void atWorksOnSaturnSuperiorPlanet(){
        ZonedDateTime zdt = ZonedDateTime.of(
                2025,
                6,
                27,
                0,
                0,
                0,
                0,
                ZoneId.of("UTC")
        );
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(ZonedDateTime.now());
        Planet saturn = PlanetModel.SATURN.at(
                Epoch.J2010.daysUntil(zdt), etec);

        assertEquals("Saturne", saturn.name());
        assertEquals("Saturne", saturn.info());
        EquatorialCoordinates ec = etec.apply(EclipticCoordinates.of(Angle.ofDeg(2.576691546),Angle.ofDeg((-2.235996483))));
        assertEquals(ec.ra(), saturn.equatorialPos().ra(),1e-8);
        assertEquals(ec.dec(), saturn.equatorialPos().dec(),1e-8);
    }

    @Test
    void atWorksOnUranusSuperiorPlanet(){
        ZonedDateTime zdt = ZonedDateTime.of(
                2030,
                6,
                27,
                0,
                0,
                0,
                0,
                ZoneId.of("UTC")
        );
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(ZonedDateTime.now());
        Planet uranus = PlanetModel.URANUS.at(
                Epoch.J2010.daysUntil(zdt), etec);

        assertEquals("Uranus", uranus.name());
        assertEquals("Uranus", uranus.info());
        EquatorialCoordinates ec = etec.apply(EclipticCoordinates.of(Angle.ofDeg(1.168783179),Angle.ofDeg((-0.752737291))));
        assertEquals(ec.ra(), uranus.equatorialPos().ra(),1e-8);
        assertEquals(ec.dec(), uranus.equatorialPos().dec(),1e-8);
    }

    @Test
    void atWorksOnNeptuneSuperiorPlanet(){
        ZonedDateTime zdt = ZonedDateTime.of(
                2030,
                6,
                27,
                0,
                0,
                0,
                0,
                ZoneId.of("UTC")
        );
        EclipticToEquatorialConversion etec = new EclipticToEquatorialConversion(ZonedDateTime.now());
        Planet neptune = PlanetModel.NEPTUNE.at(
                Epoch.J2010.daysUntil(zdt), etec);

        assertEquals("Neptune", neptune.name());
        assertEquals("Neptune", neptune.info());
        EquatorialCoordinates ec = etec.apply(EclipticCoordinates.of(Angle.ofDeg(13.04480375),Angle.ofDeg((-1.512753498))));
        assertEquals(ec.ra(), neptune.equatorialPos().ra(),1e-8);
        assertEquals(ec.dec(), neptune.equatorialPos().dec(),1e-8);
    }
}