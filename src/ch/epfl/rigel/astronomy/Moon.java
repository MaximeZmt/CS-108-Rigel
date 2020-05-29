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
    private static final ClosedInterval PHASE_INTERVAL = ClosedInterval.of(0, 1);
    private final double phase;

    /**
     * Builds an object representing the moon with the given equatorial position, angular size, magnitude and phase
     *
     * @param equatorialPos equatorial Position
     * @param angularSize angular Size
     * @param magnitude magnitude
     * @param phase phase
     * @throws IllegalArgumentException if the phase is not in the interval [0,1]
     */
    public Moon(EquatorialCoordinates equatorialPos, float angularSize, float magnitude, float phase) {
        super("Lune", equatorialPos, angularSize, magnitude);
        Preconditions.checkInInterval(PHASE_INTERVAL, phase);
        this.phase = phase;
    }

    /**
     * @see CelestialObject#info()
     */
    @Override
    public String info() {
        return String.format(Locale.ROOT, "%s (%.1f%%)", name(), (phase*100)); //%% allow to escape String.format and print "%"
    }
}
