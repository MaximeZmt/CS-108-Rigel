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

    /**
     * @see Interval#contains(double)
     */
    @Override
    public boolean contains(double v) {
        return low()<=v&&v<=high();
    }

    /**
     * Generates a closed interval
     * <p>
     * Checks if low<high
     *
     * @param low lowest number
     * @param high highest number
     * @return a closed interval (ClosedInterval)
     * @throws IllegalArgumentException if low is bigger or equal to high
     */
    public static ClosedInterval of(double low, double high) {
        Preconditions.checkArgument(low<high);
        return new ClosedInterval(low, high);
    }

    /**
     * Builds a symmetric closed interval centered in 0
     *
     * @param size size of the interval
     * @return a closed interval (ClosedInterval)
     * @throws IllegalArgumentException if size is smaller or equal to 0
     */
    public static ClosedInterval symmetric(double size) {
        Preconditions.checkArgument(size>0);
        return new ClosedInterval(-size/2, size/2);
    }

    /**
     * Clips a value into the interval
     *
     * @param v value to be clipped
     * @return the clipped value (double)
     */
    public double clip(double v) {
        if(v<= low()) {
            return low();
        }
        else return Math.min(v, high());
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "[%.2f,%.2f]", low(), high());
    }

}
