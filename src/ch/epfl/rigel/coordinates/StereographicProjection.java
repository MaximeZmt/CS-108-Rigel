package ch.epfl.rigel.coordinates;

import java.util.function.Function;

public final class StereographicProjection implements Function<HorizontalCoordinates, CartesianCoordinates> {

    private final HorizontalCoordinates center;

    public StereographicProjection(HorizontalCoordinates center){
        this.center = center;
    }

    //FIXME
    CartesianCoordinates circleCenterForParallel(HorizontalCoordinates hor){
        return null;
    }

    double circleRadiusForParallel(HorizontalCoordinates parallel){
        return 0;
    }

    double applyToAngle(double rad){
        return 0;
    }

    HorizontalCoordinates inverseApply(CartesianCoordinates xy){
        return null;
    }

    @Override
    public CartesianCoordinates apply(HorizontalCoordinates horizontalCoordinates) {
        return null;
    }
}
