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
                    LocalTime.NOON),
            ZoneOffset.UTC)),
    J2010(ZonedDateTime.of(
            LocalDateTime.of(
                    LocalDate.of(2010, Month.JANUARY, 1).minusDays(1),
                    LocalTime.MIDNIGHT),
            ZoneOffset.UTC));

    private ZonedDateTime date;
    private final static double  MILLISEC_TO_DAY_FACTOR = 1000 * 60 * 60 * 24;
    private final static double  NUMBER_OF_DAYS_PER_CENTURY = 365.25*100;

    /**
     * Basic constructor for Epoch
     *
     * @param date zone, date and time
     */
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
        return date.until(when, ChronoUnit.MILLIS)/MILLISEC_TO_DAY_FACTOR;
    }

    /**
     * Calculates the julian centuries separating the epoch and the given date and time
     *
     * @param when date, time and zone
     * @return number of julian centuries (can be decimal) (double)
     */
    public double julianCenturiesUntil(ZonedDateTime when){
        return date.until(when, ChronoUnit.MILLIS)/(MILLISEC_TO_DAY_FACTOR*NUMBER_OF_DAYS_PER_CENTURY);
    }
}
