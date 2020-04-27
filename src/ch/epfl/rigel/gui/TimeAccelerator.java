package ch.epfl.rigel.gui;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Represents a time Accelerator, it's compute a simulate time
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
@FunctionalInterface
public interface TimeAccelerator {
    /**
     * Compute simulate time T
     * @param t0  Initial simulate time
     * @param deltaT Represents (T-T0), the difference of time in nanoseconds
     * @return new ZoneDateTime with the simulate Time T
     */
    ZonedDateTime adjust(ZonedDateTime t0, long deltaT);

    static TimeAccelerator continuous(int alpha){
        return (t0,deltaT) -> t0.plusNanos(alpha*deltaT);
    }
    
    static TimeAccelerator discrete(long simulatedTimeFrequency, Duration steps){
        return (t0, deltaT) ->
                t0.plus((steps.multipliedBy(simulatedTimeFrequency*deltaT/1_000_000_000)));
    }
}

