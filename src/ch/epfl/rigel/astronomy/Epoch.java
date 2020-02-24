package ch.epfl.rigel.astronomy;

import java.time.*;

public enum Epoch {
    J2000(LocalDate.of(200, Month.JANUARY, 1)),
    J2010(LocalDate.of(2010, Month.JANUARY, 1).minusDays(1));

    public double daysUntil(ZonedDateTime when){
        return 0;
    }

    public double julianCenturiesUntil(ZonedDateTime when){
        return 0;
    }
}
