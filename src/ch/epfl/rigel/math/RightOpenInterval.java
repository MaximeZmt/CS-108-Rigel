package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

import java.util.Locale;

/**
 * Represents a right-open interval
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class RightOpenInterval extends Interval{
    private RightOpenInterval(double a, double b) {
        super(a, b);
    }

    @Override
    public boolean contains(double v) {
        return v<high()&&v>=low();
    }

    /**
     * Creates a right-open interval with the given bounds
     *
     * @param low the lower bound
     * @param high the upper bound
     * @return a right-open interval (RightOpenInterval)
     * @throws IllegalArgumentException if low is bigger or equal to high
     */
    public static RightOpenInterval of(double low, double high) {//TODO CHECK
        Preconditions.checkArgument(low<high);
        return new RightOpenInterval (low, high);
    }

    /**
     * Creates a symmetric right-open interval centered in 0
     *
     * @param size the size of the interval
     * @return a symmetric right-open interval (RightOpenInterval)
     * @throws IllegalArgumentException if size is smaller or equal to 0
     */
    public static RightOpenInterval symmetric(double size) {
        Preconditions.checkArgument(size>0);
        return new RightOpenInterval(-size/2, size/2);
    }

    /**
     * Reduces a value in the interval
     *
     * @param v the value to be reduced
     * @return the reduced value (double)
     */
    public double reduce(double v){
        return low()+ floorMod(v-low(),high()-low());
    }

    private double floorMod(double x, double y){
        return x- (y * Math.floor(x/y));
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "[%.2f,%.2[", low(), high());
    }


}

