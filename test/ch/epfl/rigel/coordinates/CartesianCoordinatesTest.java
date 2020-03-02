package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartesianCoordinatesTest {
    @Test
    void toStringWorks(){
        CartesianCoordinates cc = CartesianCoordinates.of(23.45372974,0.00002637);
        System.out.println(cc);
    }
}