package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
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
        assertEquals(0,sp.inverseApply(CartesianCoordinates.of(0,0)).alt());
        assertEquals(0,sp.inverseApply(CartesianCoordinates.of(0,0)).az());
    }



    @Test
    void ToStringTest(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.3,1.4454));
        assertEquals("StereographicProjection, center : (az=17.1887°, alt=82.8153°)",sp.toString());
    }

    @Test
    void circleCenterForParallelIsInfinity(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.2,0));
        assertEquals(Double.POSITIVE_INFINITY,sp.circleCenterForParallel(HorizontalCoordinates.ofDeg(23,0)).y());
    }

    @Test
    void circleCenterWorksOnRandomValues(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.ofDeg(45,45));
        assertEquals(0.6089987401, sp.circleCenterForParallel(HorizontalCoordinates.ofDeg(0,27)).y(),1e-9);
    }

    @Test
    void circleRadiusForParallelIsInfinity(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.2,0));
        assertEquals(Double.POSITIVE_INFINITY, sp.circleRadiusForParallel(HorizontalCoordinates.ofDeg(23,0)));
    }

    @Test
    void circleRadiusWorksOnRandomValue(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.ofDeg(45,45));
        assertEquals(0.7673831804, sp.circleRadiusForParallel(HorizontalCoordinates.ofDeg(0,27)), 1e-9);
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

    @Test
    void ApplyTest(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.ofDeg(0 ,0 ));

        CartesianCoordinates cc = sp.apply(HorizontalCoordinates.ofDeg(40 ,90 ));
        assertEquals(0.00000,cc.x()*2,1e-5);
        assertEquals(2.00000,cc.y()*2,1e-5);

        cc = sp.apply(HorizontalCoordinates.ofDeg(10 ,70 ));
        assertEquals(0.08885,cc.x()*2,1e-5);
        assertEquals(1.40586,cc.y()*2,1e-5);

         cc = sp.apply(HorizontalCoordinates.ofDeg(60 ,80 ));
        assertEquals(0.27674,cc.x()*2,1e-5);
        assertEquals(1.81227,cc.y()*2,1e-5);
    }

    @Test
    void ParallelTest(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.ofDeg(0 ,0));
        CartesianCoordinates cc = sp.circleCenterForParallel(HorizontalCoordinates.ofDeg(0,0));
        assertEquals(Double.POSITIVE_INFINITY, cc.y());
        double cc2 = sp.circleRadiusForParallel(HorizontalCoordinates.ofDeg(0,0));
        assertEquals(Double.POSITIVE_INFINITY, cc2);

    }

    @Test
    void applyToAngleWorksOnRandomValues(){
        StereographicProjection sp = new StereographicProjection (HorizontalCoordinates.ofDeg(23, 45));
        assertEquals(4.363330053e-3, sp.applyToAngle(Angle.ofDeg(1/2.0)), 1e-11);
    }

    @Test
    void inverseApplyWorksOnRandomValues(){
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.ofDeg(45,20));
        assertEquals(-0.2691084761522857, sp.inverseApply(CartesianCoordinates.of(0,25)).alt());
        assertEquals(3.9269908169872414, sp.inverseApply(CartesianCoordinates.of(0,25)).az());

        StereographicProjection sp2 = new StereographicProjection(HorizontalCoordinates.ofDeg(45,45));
        assertEquals(3.648704525474978, sp2.inverseApply(CartesianCoordinates.of(10,0)).az(),1e-6);
    }

    @Test
    void applyWorksOnRandomValues(){
        StereographicProjection sp = new StereographicProjection (HorizontalCoordinates.ofDeg(45,45));
        assertEquals(-0.1316524976, sp.apply(HorizontalCoordinates.ofDeg(45,30)).y(),1e-9);
    }

     /*
     source: some test data from
     Snyder, J. P. Map Projections--A Working Manual. U. S. Geological Survey Professional Paper 1395. Washington,
     DC: U. S. Government Printing Office, pp. 154-163, 1987.
      */

}