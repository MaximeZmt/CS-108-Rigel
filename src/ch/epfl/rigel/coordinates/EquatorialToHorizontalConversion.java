package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.SiderealTime;

import java.time.*; //TODO ask if import * or just what we need
import java.util.function.Function;

// représente un changement de systèmes de coordonnées
// depuis les coordonnées équatoriales vers les coordonnées Horizontal, à un instant et pour un lieu donnés.
public final class EquatorialToHorizontalConversion implements Function<EquatorialCoordinates, HorizontalCoordinates> {
    private final double cosH;
    private final double sinH;
    private final double cosPhi;
    private final double sinPhi;

    public EquatorialToHorizontalConversion(ZonedDateTime when, GeographicCoordinates where){
        double H = SiderealTime.local(when,where)- where.lon(); // where.lon should be right ascencion
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
        //System.out.println("h: "+h);
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


