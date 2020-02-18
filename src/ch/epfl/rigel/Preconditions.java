package ch.epfl.rigel;

import ch.epfl.rigel.math.Interval;

/**
 * Allows to check conditions
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class Preconditions {
    private Preconditions() {}

    /**
     * Checks if the given argument is correct in it's context
     *
     * @param isTrue  the argument to be checked
     * @throws IllegalArgumentException if the argument is not correct
     */
    public static void checkArgument(boolean isTrue) {
        if(!isTrue) {
            throw new IllegalArgumentException("checkArgumentE - InvalidArgument"); //TODO checkMessage
        }
    }

    /**
     * Checks if the given value is inside the given interval
     *
     * @param interval  the given interval
     * @param value  the given value
     * @return a boolean
     */
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

