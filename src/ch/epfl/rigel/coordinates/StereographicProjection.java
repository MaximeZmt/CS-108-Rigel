package ch.epfl.rigel.coordinates;

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
    private double lambda0;

    public StereographicProjection(HorizontalCoordinates center){
        this.center = center;
        double phi = center.alt();
        cosPhi1 = Math.cos(phi);
        sinPhi1 = Math.sin(phi);
        lambda0 = center.az();
    }

    public CartesianCoordinates circleCenterForParallel(HorizontalCoordinates hor){
        double phi = hor.alt();
        double y = cosPhi1/(Math.sin(phi)+sinPhi1);
        return CartesianCoordinates.of(0,y);
    }

    public double circleRadiusForParallel(HorizontalCoordinates parallel){
        double phi = parallel.alt();
        double rho = Math.cos(phi)/(Math.sin(phi)+sinPhi1);
        return rho;
    }

    public double applyToAngle(double rad){
        return 2*Math.tan(rad/4);
    }

    public HorizontalCoordinates inverseApply(CartesianCoordinates xy){
        double x = xy.x();
        double y = xy.y();
        double rho = Math.sqrt(x*x+y*y);
        double sinc = 2*rho/(rho*rho+1);
        double cosc = (1-rho*rho)/(rho*rho+1);
        double lambda = Math.atan2(x*sinc,rho*cosPhi1*cosc-y*sinPhi1*sinc)+lambda0;
        double phi = Math.asin(cosc*sinPhi1+(y*sinc*cosPhi1)/rho);
        return HorizontalCoordinates.of(lambda,phi);
    }

    @Override
    public CartesianCoordinates apply(HorizontalCoordinates horizontalCoordinates) {
        double phi = horizontalCoordinates.alt();
        double sinPhi = Math.sin(phi);
        double cosPhi = Math.cos(phi);
        double deltaLambda = horizontalCoordinates.az()-lambda0;
        double sinDeltaLambda = Math.sin(deltaLambda);
        double cosDeltaLambda = Math.cos(deltaLambda);
        double d = 1/1+sinPhi*sinPhi1+cosPhi*cosPhi1*cosDeltaLambda;
        double x = d*cosPhi*sinDeltaLambda;
        double y = d*(sinPhi*cosPhi1-cosPhi*sinPhi1*cosDeltaLambda);
        return CartesianCoordinates.of(x,y);
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Called hashCode from StereographicProjection");
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Called equals from StereographicProjection");
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT,"StereographicProjection, center : (az=%.4f ; alt=%.4f)",
                center.az(),
                center.alt());
    }
}
