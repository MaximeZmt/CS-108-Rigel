package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;

abstract class SphericalCoordinates {

    private double longitude;
    private double latitude;

    SphericalCoordinates(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    double lon(){
        return longitude;
    }

    double lonDeg(){
        return Angle.toDeg(longitude);
    }

    double lat(){
        return latitude;
    }

    double latDeg(){
        return Angle.toDeg(latitude);
    }

    @Override
    public final int hashCode() {
        throw new UnsupportedOperationException("Try to call hashCode in SphericalCoordinates class");
    }

    @Override
    public final boolean equals(Object obj) {
        throw new UnsupportedOperationException("Try to call equals in SphericalCoordinates class");
    }
}
