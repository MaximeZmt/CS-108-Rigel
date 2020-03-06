package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MyEquatorialToHorizontalConversionTest {

    @Test
    void applyWorks(){

        //THIS TEST SHOULD BE USED BY CHANGING MANUALLY THE VALUE OF H ( 1.534726189 )


        EquatorialToHorizontalConversion ethc = new EquatorialToHorizontalConversion(ZonedDateTime.now(),
                GeographicCoordinates.ofDeg(0,52));
        HorizontalCoordinates coord = ethc.apply(EquatorialCoordinates.of(Angle.ofDeg(87.93333),Angle.ofDeg(23.21944)));
        assertEquals(283, coord.azDeg(),10e-3);
        assertEquals(19, coord.altDeg(),10e-3);




        /*

        ZonedDateTime tps1 = ZonedDateTime.of(
                2020,
                02,
                24,
                19,
                32,
                33,
                00,
                ZoneId.of("UTC+1"));

        ZonedDateTime tps2 =  ZonedDateTime.of(
                2020,
                02,
                25,
                19,
                28,
                37,
                00,
                ZoneId.of("UTC+1"));


        EquatorialCoordinates ecRigel = EquatorialCoordinates.of(Angle.ofHr(5.2423),Angle.ofDeg(-8.2016));
        GeographicCoordinates gcEPFL = GeographicCoordinates.ofDeg(6.5682,46.5183);
        EquatorialToHorizontalConversion ethc2 = new EquatorialToHorizontalConversion(tps1, gcEPFL);
        EquatorialToHorizontalConversion ethc3 = new EquatorialToHorizontalConversion(tps2,gcEPFL);
        //System.out.println("Altitude : "+ethc2.apply(ecRigel).altDeg());
        //System.out.println("Azimuth : "+ethc2.apply(ecRigel).azDeg());
        assertEquals(ethc2.apply(ecRigel).azDeg(),ethc3.apply(ecRigel).azDeg(), 1e-1);
        assertEquals(ethc2.apply(ecRigel).altDeg(),ethc3.apply(ecRigel).altDeg(), 1e-1);


         */


    }
}

