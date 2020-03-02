package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;

import java.util.Locale;

/**
 * Represents The Moon at a certain moment
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */


public final class Moon extends CelestialObject {

    private final double phase;

    public Moon(EquatorialCoordinates equatorialPos, float angularSize, float magnitude, float phase) {
        super("Lune", equatorialPos, angularSize, magnitude);
        ClosedInterval phaseInterval = ClosedInterval.of(0,1);
        Preconditions.checkInInterval(phaseInterval,phase);
        this.phase = phase;
    }

    @Override
    public String info() {
        return String.format(Locale.ROOT, "%s (%.1f%%)",name(),(phase*100)); //%% allow to escape String.format and print "%"
    }
}
