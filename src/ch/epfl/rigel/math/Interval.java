package ch.epfl.rigel.math;

/**
 * Represents a basic interval
 */
public abstract class Interval {

    private final double a;
    private final double b;

    /**
     * Builds a basic interval
     *
     * @param a  the lower bound
     * @param b  the upper bound
     */
    protected Interval(double a, double b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Getter for the lower bound
     *
     * @return the lowest number
     */
    public double low() {
        return a;
    }

    /**
     * Getter for the upper bound
     *
     * @return the highest number
     */
    public double high() {
        return b;
    }

    /**
     * Getter for the size of the interval
     *
     * @return the size of the interval
     */
    public double size() {
        return b-a;
    }

    /**
     * Checks if a value is contained in the interval
     *
     * @param v  the value to be checked
     * @return a boolean
     */
    public abstract boolean contains (double v);

    @Override
    public final int hashCode() {
        throw new UnsupportedOperationException("Try to call hashCode in Interval class");
    }

    @Override
    public final boolean equals(Object obj) {
        throw new UnsupportedOperationException("Try to call equals in Interval Class");
    }




}

