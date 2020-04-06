package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ObservedSkyTest {
    private static final String HYG_CATALOGUE_NAME = "/hygdata_v3.csv";
    private static final String ASTERISM = "/asterisms.txt";

    @Test
    void trivialTest() throws IOException {
        InputStream hygStream = getClass().getResourceAsStream(HYG_CATALOGUE_NAME);
        InputStream astStream = getClass().getResourceAsStream(ASTERISM);
        StarCatalogue catalogue = new StarCatalogue.Builder().loadFrom(hygStream, HygDatabaseLoader.INSTANCE).loadFrom(astStream,AsterismLoader.INSTANCE).build();
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
        GeographicCoordinates gc = GeographicCoordinates.ofDeg(51.473771,0);
        StereographicProjection stereoPrj = new StereographicProjection(HorizontalCoordinates.of(Angle.ofDMS(174,36,37),Angle.ofDMS(19,45,04)));
        ObservedSky os = new ObservedSky(zdt,gc,stereoPrj,catalogue);
        CartesianCoordinates cc = CartesianCoordinates.of(0,0);
        CelestialObject co = os.objectClosestTo(cc,100);
        System.out.println(co.name());
    }


}