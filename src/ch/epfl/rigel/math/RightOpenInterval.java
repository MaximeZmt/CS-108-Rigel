package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

import java.util.Locale;

public final class RightOpenInterval extends Interval{

    private RightOpenInterval(double a, double b) {
        super(a, b);
    }


    @Override
    public boolean contains(double v) {
        return v<high()&&v>=low();
    }


    //TODO CHECK
    public static RightOpenInterval of(double low, double high) {
        Preconditions.checkArgument(low<high);
        return new RightOpenInterval (low, high);
    }

    public static RightOpenInterval symmetric(double size) {
        Preconditions.checkArgument(size>0);
        return new RightOpenInterval(-size/2, size/2);
    }

    public double reduce(double v){
        return low()+ floorMod(v-low(),high()-low());
    }

    private double floorMod(double x, double y){
        return x- (y * Math.floor(x/y)); //TODO check if can use Math.Floor
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "[%.2f,%.2[", low(), high()); //TODO CHECK
    }


}

