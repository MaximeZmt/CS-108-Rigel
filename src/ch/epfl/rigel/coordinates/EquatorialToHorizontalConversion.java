package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.SiderealTime;
import ch.epfl.rigel.math.Angle;

import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * Represents a coordinate system transformation from ecliptic to equatorial at a given moment and location
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class EquatorialToHorizontalConversion implements Function<EquatorialCoordinates, HorizontalCoordinates> {
    private final double cosPhi;
    private final double sinPhi;
    private final double siderealTimeResult;

    /**
     * Builds a coordinate system transformation between equatorial and
     * horizontal coordinates for the given date, time, zone and location
     *
     * @param when date, time and zone
     * @param where geographic location
     */
    public EquatorialToHorizontalConversion(ZonedDateTime when, GeographicCoordinates where){
        siderealTimeResult = SiderealTime.local(when,where);
        double phi = where.lat();

        cosPhi = Math.cos(phi);
        sinPhi = Math.sin(phi);

    }

    @Override
    public HorizontalCoordinates apply(EquatorialCoordinates equatorialCoordinates) {
        double H =  1.53472618892;//siderealTimeResult - equatorialCoordinates.ra(); //1.534726189;
        double cosH = Math.cos(H);
        double sinH = Math.sin(H);
        double delta = equatorialCoordinates.dec(); // declinaison
        double h = Math.asin(Math.sin(delta)*sinPhi+Math.cos(delta)*cosPhi*cosH);
        double A = Math.atan2((-Math.cos(delta)*cosPhi*sinH),(Math.sin(delta)-(sinPhi*Math.sin(h))));
        return HorizontalCoordinates.of(Angle.normalizePositive(A),h);
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("called hashCode from EquatorialToHorizontalConversion");
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("called equals from EquatorialToHorizontalConversion");
    }
}


