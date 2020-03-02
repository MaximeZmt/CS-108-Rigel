package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyStereographicProjectionTest {
    @Test
    void ErrorThrownOnPurpose(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.ofDeg(0,0));
        assertThrows(UnsupportedOperationException.class,()->{
            sp.hashCode();
        });
        assertThrows(UnsupportedOperationException.class,()->{
            sp.equals(null);
        });
    }

    @Test
    void applyInverseApplyBoomerang(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0,0));
        HorizontalCoordinates hc = HorizontalCoordinates.ofDeg(10,-10);
        CartesianCoordinates cc = sp.apply(hc);
        HorizontalCoordinates nhc = sp.inverseApply(cc);
        assertEquals(hc.azDeg(),nhc.azDeg(), 1e-8);
        assertEquals(hc.altDeg(),nhc.altDeg(), 1e-8);

    }



    @Test
    void ToStringTest(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.2,1.4444));
        System.out.println(sp);
    }

    @Test
    void circleCenterForParallelIsInfinity(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.2,0));
        assertEquals(Double.POSITIVE_INFINITY,sp.circleCenterForParallel(HorizontalCoordinates.ofDeg(23,0)).y());
    }

    @Test
    void circleRadiusForParallelIsInfinity(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.2,0));
        assertEquals(Double.POSITIVE_INFINITY, sp.circleRadiusForParallel(HorizontalCoordinates.ofDeg(23,0)));
    }

    @Test
    void applyToAngleIs0OnNullAngularDistance(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.2,0));
        assertEquals(0,sp.applyToAngle(0));
    }

    @Test
    void applyInverseApplyBoomerang2(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0,0));
        CartesianCoordinates cc = CartesianCoordinates.of(25,26);
        HorizontalCoordinates hc = sp.inverseApply(cc);
        CartesianCoordinates ncc = sp.apply(hc);
        assertEquals(cc.x(),ncc.x(), 1e-8);
        assertEquals(cc.y(),ncc.y(), 1e-8);
    }

    @Test
    void applyIs0OnSameCoordinateAsCenter(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(3.6,1.2));
        CartesianCoordinates cc = sp.apply(HorizontalCoordinates.of(3.6,1.2));
        assertEquals(0, cc.x());
        assertEquals(0,cc.y());
    }


}