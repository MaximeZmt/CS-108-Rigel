package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkyCanvasPainterTest {
    @Test
    void testObjectDiameter(){
        StereographicProjection stereoProj = new StereographicProjection(HorizontalCoordinates.of(0,0));
        double val = SkyCanvasPainter.objectDiameter(0.18, stereoProj.applyToAngle(Angle.ofDeg(0.5)));
        System.out.println(val);
        assertEquals(2.99*1e-3,val,1e-6);
    }

}