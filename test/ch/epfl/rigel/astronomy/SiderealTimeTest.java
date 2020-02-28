package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class SiderealTimeTest {
    @Test
    void dataTest(){
        ZonedDateTime tps = ZonedDateTime.of(
                1980,
                04,
                22,
                14,
                36,
                51,
                67,
                ZoneId.of("UTC"));
        assertEquals(1.2220619247737088,SiderealTime.greenwich(tps));

        tps = ZonedDateTime.of(
                2001,
                1,
                27,
                12,
                0,
                0,
                0,
                ZoneId.of("UTC"));
        assertEquals(5.355270290366605,SiderealTime.greenwich(tps));

        tps = ZonedDateTime.of(
                2004,
                9,
                23,
                11,
                0,
                0,
                0,
                ZoneId.of("UTC"));
        assertEquals(2.9257399567031235,SiderealTime.greenwich(tps));

        tps = ZonedDateTime.of(
                2001,
                9,
                11,
                8,
                14,
                0,
                0,
                ZoneId.of("UTC"));
        assertEquals(1.9883078130455532,SiderealTime.greenwich(tps),1e-10);





        tps = ZonedDateTime.of(
                1980,
                04,
                22,
                14,
                36,
                51,
                27,
                ZoneId.of("UTC"));

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(30,45);
        assertEquals(1.74570958832716,SiderealTime.local(tps, gc), 1e-4);

        ZonedDateTime tps1 = ZonedDateTime.of(
                2020,
                02,
                24,
                19,
                32,
                33,
                00,
                ZoneId.of("UTC+1"));

        GeographicCoordinates gcEPFL = GeographicCoordinates.ofDeg(6.5682,46.5183);

        ZonedDateTime tps2 =  ZonedDateTime.of(
                2020,
                02,
                25,
                19,
                28,
                37,
                00,
                ZoneId.of("UTC+1"));
        Double st = Angle.toDeg(SiderealTime.local(tps1,gcEPFL));
        System.out.println("SiderealTime: "+st);
    assertEquals(SiderealTime.local(tps1,gcEPFL), SiderealTime.local(tps2,gcEPFL),1e-5);
    }
}