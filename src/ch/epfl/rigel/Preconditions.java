package ch.epfl.rigel;

import ch.epfl.rigel.math.Interval;

public final class Preconditions {
    private Preconditions() {}

    public static void checkArgument(boolean isTrue) {
        if(!isTrue) {
            throw new IllegalArgumentException("checkArgumentE - InvalidArgument"); //TODO checkMessage
        }
    }

    public static double checkInInterval(Interval interval, double value) {
        if (!interval.contains(value))
        {
            throw new IllegalArgumentException("CheckInIntervalE - value is not in interval");
        }
        else {
            return value;
        }
    }

}

