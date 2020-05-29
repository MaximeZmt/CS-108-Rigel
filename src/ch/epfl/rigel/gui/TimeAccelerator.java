package ch.epfl.rigel.gui;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Represents a time Accelerator, computes a simulated time
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
@FunctionalInterface
public interface TimeAccelerator {
    /**
     * Compute simulate time T
     *
     * @param t0  Initial simulate time
     * @param deltaT Represents (T-T0), the difference of time in nanoseconds
     * @return new ZoneDateTime with the simulate Time T
     */
    ZonedDateTime adjust(ZonedDateTime t0, long deltaT);

    /**
     * Return a continuous time accelerator given an acceleration factor (Integer)
     *
     * @param alpha The acceleration factor
     * @return a continuous time accelerator
     */
    static TimeAccelerator continuous(int alpha){
        return (t0,deltaT) -> t0.plusNanos(alpha*deltaT);
    }

    /**
     * Return a discrete time accelerator given a frequency (long)
     *
     * @param simulatedTimeFrequency Frequency of step
     * @param steps Duration of each step
     * @return a discrete time accelerator
     */
    static TimeAccelerator discrete(long simulatedTimeFrequency, Duration steps){
        return (t0, deltaT) ->
                t0.plus((steps.multipliedBy(simulatedTimeFrequency*deltaT/1_000_000_000)));
    }
}

