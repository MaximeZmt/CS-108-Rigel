package ch.epfl.rigel.astronomy;

import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * Represents an astronomic epoch
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
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

    //TODO ask if javadoc
    Epoch(ZonedDateTime date){
        this.date = date;
    }

    /**
     * Calculates the days separating the epoch and the given date and time
     *
     * @param when date, time and zone
     * @return number of days (can be decimal) (double)
     */
    public double daysUntil(ZonedDateTime when){
        return date.until(when, ChronoUnit.MILLIS)/86400000.;
    }

    /**
     * Calculates the julian centuries separating the epoch and the given date and time
     *
     * @param when date, time and zone
     * @return number of julian centuries (can be decimal) (double)
     */
    public double julianCenturiesUntil(ZonedDateTime when){
        return date.until(when, ChronoUnit.MILLIS)/(86400000.*36525);
    }
}
