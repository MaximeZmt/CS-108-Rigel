package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;
import java.util.Locale;

/**
 * Represents a closed interval [a,b] where a<b
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class ClosedInterval extends Interval {
    private ClosedInterval(double a, double b) {
        super(a, b);
    }

    @Override
    public boolean contains(double v) {
        return v<=high()&&v>=low();
    }

    /**
     * Builds a closed interval
     * checks if low<high
     *
     * @param low  lowest number
     * @param high  highest number
     * @return a closed interval
     */
    public static ClosedInterval of(double low, double high) {
        Preconditions.checkArgument(low<high);
        return new ClosedInterval(low, high);
    }

    /**
     * Builds a symmetric closed interval centered in 0
     *
     * @param size  size of the interval
     * @return a closed interval
     */
    public static ClosedInterval symmetric(double size) {
        Preconditions.checkArgument(size>0);
        return new ClosedInterval(-size/2, size/2);
    }

    /**
     * Clips a value into the interval
     *
     * @param v  value to be clipped
     * @return the clipped value
     */
    public double clip(double v) { //TODO check access right and done: Ecretage
        if(v<= low()) {
            return low();
        }
        else if(v>=high()) {
            return high();
        }
        else {
            return v;
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "[%.2f,%.2f]", low(), high()); //TODO CHECK
    }

}
