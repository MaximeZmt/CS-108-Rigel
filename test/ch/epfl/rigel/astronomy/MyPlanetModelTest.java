package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MyPlanetModelTest {
    @Test
    void allReturnVenusAtIndex2(){
        PlanetModel venus = PlanetModel.ALL.get(1);
        assertEquals(PlanetModel.VENUS, venus);
    }

    @Test
    void atWorksOnMercuryInferiorPlanet(){
        Planet mercury = PlanetModel.MERCURY.at(
                10, new EclipticToEquatorialConversion(ZonedDateTime.now()));

        assertEquals("Mercure", mercury.name());
        assertEquals("Mercure", mercury.info());
        //TODO implement with real values
        assertEquals(EquatorialCoordinates.of(1,1).ra(), mercury.equatorialPos().ra());
        assertEquals(EquatorialCoordinates.of(1,1).dec(), mercury.equatorialPos().dec());
        assertEquals(12, mercury.angularSize());
        assertEquals(12, mercury.magnitude());
    }

    @Test
    void atWorksOnVenusInferiorPlanet(){
        Planet venus = PlanetModel.VENUS.at(
                10, new EclipticToEquatorialConversion(ZonedDateTime.now()));

        assertEquals("Vénus", venus.name());
        assertEquals("Vénus", venus.info());
        //TODO implement with real values
        assertEquals(EquatorialCoordinates.of(1,1).ra(), venus.equatorialPos().ra());
        assertEquals(EquatorialCoordinates.of(1,1).dec(), venus.equatorialPos().dec());
        assertEquals(12, venus.angularSize());
        assertEquals(12, venus.magnitude());
    }

    @Test
    void atWorksOnEarthSuperiorPlanet(){
        Planet earth = PlanetModel.EARTH.at(
                10, new EclipticToEquatorialConversion(ZonedDateTime.now()));

        assertEquals("Terre", earth.name());
        assertEquals("Terre", earth.info());
        //TODO implement with real values
        assertEquals(EquatorialCoordinates.of(1,1).ra(), earth.equatorialPos().ra());
        assertEquals(EquatorialCoordinates.of(1,1).dec(), earth.equatorialPos().dec());
        assertEquals(12, earth.angularSize());
        assertEquals(12, earth.magnitude());
    }

    @Test
    void atWorksOnMarsSuperiorPlanet(){
        Planet mars = PlanetModel.MARS.at(
                10, new EclipticToEquatorialConversion(ZonedDateTime.now()));

        assertEquals("Mars", mars.name());
        assertEquals("Mars", mars.info());
        //TODO implement with real values
        assertEquals(EquatorialCoordinates.of(1,1).ra(), mars.equatorialPos().ra());
        assertEquals(EquatorialCoordinates.of(1,1).dec(), mars.equatorialPos().dec());
        assertEquals(12, mars.angularSize());
        assertEquals(12, mars.magnitude());
    }

    @Test
    //TODO value not in interval
    void atWorksOnJupiterSuperiorPlanet(){
        Planet jupiter = PlanetModel.JUPITER.at(
                10, new EclipticToEquatorialConversion(ZonedDateTime.now()));

        assertEquals("Jupiter", jupiter.name());
        assertEquals("Jupiter", jupiter.info());
        //TODO implement with real values
        assertEquals(EquatorialCoordinates.of(1,1).ra(), jupiter.equatorialPos().ra());
        assertEquals(EquatorialCoordinates.of(1,1).dec(), jupiter.equatorialPos().dec());
        assertEquals(12, jupiter.angularSize());
        assertEquals(12, jupiter.magnitude());
    }

    @Test
    void atWorksOnSaturnSuperiorPlanet(){
        Planet Saturn = PlanetModel.SATURN.at(
                10, new EclipticToEquatorialConversion(ZonedDateTime.now()));

        assertEquals("Saturne", Saturn.name());
        assertEquals("Saturne", Saturn.info());
        //TODO implement with real values
        assertEquals(EquatorialCoordinates.of(1,1).ra(), Saturn.equatorialPos().ra());
        assertEquals(EquatorialCoordinates.of(1,1).dec(), Saturn.equatorialPos().dec());
        assertEquals(12, Saturn.angularSize());
        assertEquals(12, Saturn.magnitude());
    }

    @Test
    void atWorksOnUranusSuperiorPlanet(){
        Planet uranus = PlanetModel.URANUS.at(
                10, new EclipticToEquatorialConversion(ZonedDateTime.now()));

        assertEquals("Uranus", uranus.name());
        assertEquals("Uranus", uranus.info());
        //TODO implement with real values
        assertEquals(EquatorialCoordinates.of(1,1).ra(), uranus.equatorialPos().ra());
        assertEquals(EquatorialCoordinates.of(1,1).dec(), uranus.equatorialPos().dec());
        assertEquals(12, uranus.angularSize());
        assertEquals(12, uranus.magnitude());
    }

    @Test
    //TODO value not in interval
    void atWorksOnNeptuneSuperiorPlanet(){
        Planet neptune = PlanetModel.NEPTUNE.at(
                10, new EclipticToEquatorialConversion(ZonedDateTime.now()));

        assertEquals("Neptune", neptune.name());
        assertEquals("Neptune", neptune.info());
        //TODO implement with real values
        assertEquals(EquatorialCoordinates.of(1,1).ra(), neptune.equatorialPos().ra());
        assertEquals(EquatorialCoordinates.of(1,1).dec(), neptune.equatorialPos().dec());
        assertEquals(12, neptune.angularSize());
        assertEquals(12, neptune.magnitude());
    }


}