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
                1980,
                04,
                22,
                14,
                36,
                51,
                27,
                ZoneId.of("UTC"));

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(30,45);
        assertEquals(1.74570958832716,SiderealTime.local(tps, gc), 1e-4);//TODO check if precise enough¬
    }
}