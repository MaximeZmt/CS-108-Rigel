package ch.epfl.rigel.coordinates;

import java.util.Locale;

/**
 * Represents cartesian coordinates
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class CartesianCoordinates {
    private final double x;
    private final double y;

    private CartesianCoordinates(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a cartesian coordinate of abscissa x and ordinate y
     *
     * @param x abscissa
     * @param y ordinate
     * @return a cartesian coordinate with the given x and y (CartesianCoordinates)
     */
    public static CartesianCoordinates of(double x, double y){
        return new CartesianCoordinates(x, y);
    }

    /**
     * Getter for the x coordinate
     *
     * @return x (double)
     */
    public double x(){
        return x;
    }

    /**
     * Getter for the y coordinate
     *
     * @return y (double)
     */
    public double y(){
        return y;
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Called hashCode from CartesianCoordinates");
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Called equals from CartesianCoordinates");
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT,"(x=%.4f ; y=%.4f)", x, y);
    }
}
