package ch.epfl.rigel.astronomy;

import java.time.ZonedDateTime;

public enum Epoch {
    J2000,
    J2010;

    public double daysUntil(ZonedDateTime when)
}
