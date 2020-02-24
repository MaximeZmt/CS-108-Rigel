package ch.epfl.rigel.astronomy;

import java.time.*;
import java.time.temporal.ChronoUnit;

public enum Epoch {
    J2000(ZonedDateTime.of(
            LocalDateTime.of(
                    LocalDate.of(2000, Month.JANUARY, 1),
                    LocalTime.of(12, 0)),
            ZoneId.of("UTC"))),
    J2010(ZonedDateTime.of(
            LocalDateTime.of(
                    LocalDate.of(2010, Month.JANUARY, 1).minusDays(1),
                    LocalTime.of(0, 0)),
            ZoneId.of("UTC")));

    private ZonedDateTime date;

    Epoch(ZonedDateTime date){
        this.date = date;
    }

    public double daysUntil(ZonedDateTime when){
        return date.until(when, ChronoUnit.MILLIS)/86400000;
    }

    public double julianCenturiesUntil(ZonedDateTime when){
        return date.until(when, ChronoUnit.MILLIS)/(86400000*36525);
    }
}
