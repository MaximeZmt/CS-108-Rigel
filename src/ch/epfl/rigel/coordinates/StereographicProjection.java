package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;

import java.util.Locale;
import java.util.function.Function;

/**
 * Represents a stereographic projection of horizontal coordinates
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class StereographicProjection implements Function<HorizontalCoordinates, CartesianCoordinates> {
    private final HorizontalCoordinates center;
    private final double cosPhi1;
    private final double sinPhi1;
    private final double lambda0;

    /**
     * Builds a stereographic projection centered in the given center
     *
     * @param center horizontal position of the center
     */
    public StereographicProjection(HorizontalCoordinates center){
        this.center = center;
        double phi = center.alt();
        cosPhi1 = Math.cos(phi);
        sinPhi1 = Math.sin(phi);
        lambda0 = center.az();
    }

    /**
     * Computes the coordinates of the center of the circle corresponding to the parallel
     * going through the given horizontal coordinate
     *
     * @param hor horizontal coordinate
     * @return coordinates of the center of the circle (CartesianCoordinates)
     */
    public CartesianCoordinates circleCenterForParallel(HorizontalCoordinates hor){
        double phi = hor.alt();
        double y = cosPhi1/(Math.sin(phi)+sinPhi1);
        return CartesianCoordinates.of(0,y);
    }

    /**
     * Computes the radius of the circle corresponding to the projection of the parallel
     *
     * @param parallel horizontal coordinate of the parallel
     * @return radius of the circle (double)
     */
    public double circleRadiusForParallel(HorizontalCoordinates parallel){
        double phi = parallel.alt();
        return Math.cos(phi)/(Math.sin(phi)+sinPhi1);
    }

    /**
     * Computes the projected diameter of a sphere with the given angular size
     * centered on the center of projection, supposing that it is on the horizon
     *
     * @param rad angular size
     * @return the projected diameter (double)
     */
    public double applyToAngle(double rad){
        return 2*Math.tan(rad/4);
    }

    /**
     * Computes the horizontal coordinates of a point that has for projection the given cartesian coordinate
     *
     * @param xy cartesian coordinate
     * @return inverse of the projection (HorizontalCoordinates)
     */
    public HorizontalCoordinates inverseApply(CartesianCoordinates xy){
        double x = xy.x();
        double y = xy.y();
        double rho = Math.sqrt(x*x+y*y);
        double sinc = 2*rho/(rho*rho+1);
        double cosc = (1-rho*rho)/(rho*rho+1);
        double lambda = Math.atan2(x*sinc,rho*cosPhi1*cosc-y*sinPhi1*sinc)+lambda0;
        double phi = Math.asin(cosc*sinPhi1+(y*sinc*cosPhi1)/rho);
        return HorizontalCoordinates.of(Angle.normalizePositive(lambda),phi);
    }

    //TODO check genericity with javadoc
    /**
     * @see Function<HorizontalCoordinates, CartesianCoordinates>{@link #apply(HorizontalCoordinates)}
     */
    @Override
    public CartesianCoordinates apply(HorizontalCoordinates horizontalCoordinates) {
        double phi = horizontalCoordinates.alt();
        double sinPhi = Math.sin(phi);
        double cosPhi = Math.cos(phi);
        double deltaLambda = horizontalCoordinates.az()-lambda0;
        double sinDeltaLambda = Math.sin(deltaLambda);
        double cosDeltaLambda = Math.cos(deltaLambda);
        double d = 1/(1+sinPhi*sinPhi1+cosPhi*cosPhi1*cosDeltaLambda);
        double x = d*cosPhi*sinDeltaLambda;
        double y = d*(sinPhi*cosPhi1-cosPhi*sinPhi1*cosDeltaLambda);
        return CartesianCoordinates.of(x,y);
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Called hashCode from StereographicProjection");
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Called equals from StereographicProjection");
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT,"StereographicProjection, center : (az=%.4f ; alt=%.4f)",
                center.az(),
                center.alt());
    }
}
