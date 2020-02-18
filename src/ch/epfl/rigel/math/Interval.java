package ch.epfl.rigel.math;

public abstract class Interval {

    private final double a;
    private final double b;

    protected Interval(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double low() {
        return a;
    }

    public double high() {
        return b;
    }

    public double size() {
        return b-a;
    }

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

