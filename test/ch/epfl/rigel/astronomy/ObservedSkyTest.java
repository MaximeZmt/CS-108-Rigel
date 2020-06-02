package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.*;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.*;
import java.util.Optional;

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
        //CelestialObject co = os.objectClosestTo(cc,100).get();
        //System.out.println(co.name());
    }

    @Test
    void closestToTestFrama() throws IOException {

        String HYG_CATALOGUE_NAME ="/hygdata_v3.csv";
        String AST_CATALOGUE_NAME ="/asterisms.txt";
        StarCatalogue catalogue;
        ObservedSky sky;
        StereographicProjection stereo;
        GeographicCoordinates geoCoords;
        ZonedDateTime time;
        EquatorialToHorizontalConversion convEquToHor;
        EclipticToEquatorialConversion convEcltoEqu;
        StarCatalogue.Builder builder;
        try (InputStream hygStream = getClass().getResourceAsStream(HYG_CATALOGUE_NAME)) {builder = new StarCatalogue.Builder().loadFrom(hygStream, HygDatabaseLoader.INSTANCE);}
        try (InputStream astStream = getClass().getResourceAsStream(AST_CATALOGUE_NAME)) {catalogue = builder.loadFrom(astStream, AsterismLoader.INSTANCE).build();}
        time = ZonedDateTime.of(LocalDate.of(2020, Month.APRIL, 4), LocalTime.of(0, 0), ZoneOffset.UTC);
        geoCoords = GeographicCoordinates.ofDeg(30, 45);
        stereo = new StereographicProjection(HorizontalCoordinates.ofDeg(20, 22));
        convEquToHor = new EquatorialToHorizontalConversion(time, geoCoords);
        convEcltoEqu = new EclipticToEquatorialConversion(time);
        sky = new ObservedSky(time, geoCoords, stereo, catalogue);

/*
        assertEquals("Tau Phe",
                sky.objectClosestTo(stereo.apply(new EquatorialToHorizontalConversion(time,geoCoords)
                        .apply(EquatorialCoordinates.of(0.004696959812148989,-0.861893035343076))),0.1).get().name());
        // 1.3724303693276385 -0.143145630755865

        assertEquals("Rigel", //homemade Test from csv file
                sky.objectClosestTo(stereo.apply(new EquatorialToHorizontalConversion(time,geoCoords)
                        .apply(EquatorialCoordinates.of(1.3724303693276385,-0.143145630755865))),0.1).get().name());

 */


/*
        assertEquals(Optional.empty(),
                sky.objectClosestTo(stereo.apply(new EquatorialToHorizontalConversion(time,geoCoords)
                        .apply(EquatorialCoordinates.of(0.004696959812148989,-0.8618930353430763))),0.001));

 */



    }


}