package ch.epfl.rigel.gui;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * [description text]
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
@FunctionalInterface
public interface TimeAccelerator {
    ZonedDateTime adjust(ZonedDateTime initialSimulatedTime, long realTime); //TODO check if public or package private

    //TODO check if correct and check if .plusNanos is correct
    static TimeAccelerator continuous(int alpha){
        return (initialSimulatedTime,realTime) -> initialSimulatedTime.plusNanos(alpha*realTime);
    }

    //TODO looks shitty
    static TimeAccelerator discrete(int simulatedTimeFrequency, Duration steps){
        return (initialSimulatedTime, realTime) ->
                initialSimulatedTime.plusNanos(steps.multipliedBy((long)Math.floor(simulatedTimeFrequency*realTime)).toNanos());
    }
}

