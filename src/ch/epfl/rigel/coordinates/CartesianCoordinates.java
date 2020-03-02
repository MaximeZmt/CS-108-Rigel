package ch.epfl.rigel.coordinates;

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

    public static CartesianCoordinates of(double x, double y){
        return new CartesianCoordinates(x,y);
    }

    public double x(){
        return x;
    }

    public double y(){
        return y;
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Called hashCode from CartesianCoordinates");
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Called equals from CartesianCoordinates");
    }

    @Override
    public String toString() {
        return String.format("(x=%.4f ; y=%.4f)",x,y);
    }
}
