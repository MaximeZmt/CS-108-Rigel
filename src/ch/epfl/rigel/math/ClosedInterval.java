package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

import java.util.Locale;

public final class ClosedInterval extends Interval {


    private ClosedInterval(double a, double b) {
        super(a, b);
    }

    @Override
    public boolean contains(double v) {
        return v<=high()&&v>=low();
    }

    public static ClosedInterval of(double low, double high) {
        Preconditions.checkArgument(low<high);
        return new ClosedInterval(low, high);
    }

    public static ClosedInterval symmetric(double size) {
        Preconditions.checkArgument(size>0);
        return new ClosedInterval(-size/2, size/2);
    }

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
