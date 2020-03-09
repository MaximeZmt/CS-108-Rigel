package ch.epfl.rigel.astronomy;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class EpochTest {

    @Test
    void daysUntilWorksOnTrivialEpoch(){
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2000, Month.JANUARY, 3),
                LocalTime.of(18, 0),
                ZoneOffset.UTC);

        assertEquals(2.25,Epoch.J2000.daysUntil(d));
    }

    @Test
    void daysUntilWorksOnNegativeYear(){
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(-1, Month.JANUARY, 3),
                LocalTime.of(18, 0),
                ZoneOffset.UTC);
        assertEquals(-730847.75, Epoch.J2000.daysUntil(d));

    }

    @Test
    void daysUntilWorksOnTimeZero(){
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(0, Month.JANUARY, 1),
                LocalTime.of(0, 0),
                ZoneOffset.UTC);
        assertEquals(-730485.5, Epoch.J2000.daysUntil(d));

    }

    @Test
    void julianCenturiesWorksOnTrivialValues(){
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(0, Month.JANUARY, 1),
                LocalTime.of(12, 0),
                ZoneOffset.UTC);

        assertEquals(-20,Epoch.J2000.julianCenturiesUntil(d), 1e-3);
    }

    @Test
    void centuriesUntilWorksOnMinusCenturies(){
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(-1, Month.DECEMBER, 17),
                LocalTime.of(12, 00),
                ZoneOffset.UTC);
        assertEquals(-20, Epoch.J2000.julianCenturiesUntil(d));

    }

}