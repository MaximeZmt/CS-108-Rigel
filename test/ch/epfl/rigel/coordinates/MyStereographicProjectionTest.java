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
        assertEquals(hc.azDeg(),nhc.azDeg());
        assertEquals(hc.altDeg(),nhc.altDeg());

    }



    @Test
    void ToStringTest(){

    }

}