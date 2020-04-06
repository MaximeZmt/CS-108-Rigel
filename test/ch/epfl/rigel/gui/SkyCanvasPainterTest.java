package ch.epfl.rigel.gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkyCanvasPainterTest {
    @Test
    void testObjectDiameter(){
        double val = SkyCanvasPainter.ObjectDiameter(0.18);
        System.out.println(val);
        assertEquals(2.99*1e-3,val,1e-6);
    }

}