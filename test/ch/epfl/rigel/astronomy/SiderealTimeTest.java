package ch.epfl.rigel.astronomy;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class SiderealTimeTest {
    @Test
    void dataTest(){
        ZonedDateTime tps = ZonedDateTime.of(1980,04,22,14,36,51,67,ZoneId.of("UTC"));
        assertEquals(0,SiderealTime.greenwich(tps));


    }

}