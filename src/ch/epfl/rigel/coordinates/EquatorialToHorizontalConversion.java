package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.SiderealTime;

import java.time.*; //TODO ask if import * or just what we need
import java.util.function.Function;

// représente un changement de systèmes de coordonnées
// depuis les coordonnées équatoriales vers les coordonnées Horizontal, à un instant et pour un lieu donnés.
public final class EquatorialToHorizontalConversion implements Function<EquatorialCoordinates, HorizontalCoordinates> {
    private final double cosH;
    private final double H;
    private final double phi;



    public EquatorialToHorizontalConversion(ZonedDateTime when, GeographicCoordinates where){
        H = SiderealTime.local(when,where)- where.lon(); // where.lon should be right ascencion
        cosH = Math.cos(H);
        phi = where.lat(); //-> finish implementation


    }

    @Override
    public HorizontalCoordinates apply(EquatorialCoordinates equatorialCoordinates) {
        double delta = equatorialCoordinates.dec(); // declinaison
        double alpha = equatorialCoordinates.ra(); // right asciension
        double sinDelta = Math.sin(delta);
        double sinPhi = Math.sin(phi);
        double sinAlpha = Math.sin(alpha);
        double cosPhi = Math.cos(phi);
        double cosAlpha = Math.cos(alpha);

        //TODO ask for atan2 remark about loosing point for that part and missing acos2 in library
        double A = Math.acos((sinDelta-(sinPhi*sinAlpha))/(cosPhi*cosAlpha));
        double h = Math.asin(Math.sin(delta)*Math.sin(phi)+Math.cos(delta)*Math.cos(phi)*cosH);
        System.out.println("A: "+A);
        System.out.println("h: "+h);
        return HorizontalCoordinates.of(A,h);
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


