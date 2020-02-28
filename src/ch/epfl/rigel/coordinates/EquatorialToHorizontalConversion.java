package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.SiderealTime;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.time.*; //TODO ask if import * or just what we need 2nd
import java.util.function.Function;

// représente un changement de systèmes de coordonnées
// depuis les coordonnées équatoriales vers les coordonnées Horizontal, à un instant et pour un lieu donnés.

/**
 * Represents a coordinate system transformation from ecliptic to equatorial at a given moment and location
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class EquatorialToHorizontalConversion implements Function<EquatorialCoordinates, HorizontalCoordinates> {
    private final double cosH;
    private final double sinH;
    private final double cosPhi;
    private final double sinPhi;

    /**
     * Builds a coordinate system transformation between equatorial and
     * horizontal coordinates for the given date, time, zone and location
     *
     * @param when date, time and zone
     * @param where geographic location
     */
    public EquatorialToHorizontalConversion(ZonedDateTime when, GeographicCoordinates where){ //TODO put back to normal
        double H = 1.534726189;//SiderealTime.local(when,where)- where.lon(); // where.lon should be right ascencion
        double phi = where.lat(); //-> finish implementation
        cosH = Math.cos(H);
        sinH = Math.sin(H);
        cosPhi = Math.cos(phi);
        sinPhi = Math.sin(phi);

    }

    @Override
    public HorizontalCoordinates apply(EquatorialCoordinates equatorialCoordinates) {
        double delta = equatorialCoordinates.dec(); // declinaison
        double h = Math.asin(Math.sin(delta)*sinPhi+Math.cos(delta)*cosPhi*cosH);
        double A = Math.atan2((-Math.cos(delta)*cosPhi*sinH),(Math.sin(delta)-(sinPhi*Math.sin(h))));

        System.out.println("A: "+A);
        System.out.println("h: "+h);

        //TODO Check ce bordel avec value Cambridge, attention val H, dep temps sideral et time zone, modif main (clip)
        //should we clip
        RightOpenInterval ROI = RightOpenInterval.of(0,360);
        double r = ROI.reduce(Angle.toDeg(A));
        ClosedInterval CI = ClosedInterval.of(-90,90);
        double rp = CI.clip(Angle.toDeg(h));


        return HorizontalCoordinates.ofDeg(r,rp);
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


