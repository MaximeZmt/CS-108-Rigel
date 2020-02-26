package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.SiderealTime;

import java.time.*; //TODO ask if import * or just what we need
import java.util.function.Function;

// représente un changement de systèmes de coordonnées
// depuis les coordonnées équatoriales vers les coordonnées Horizontal, à un instant et pour un lieu donnés.
public final class EquatorialToHorizontalConversion implements Function<EquatorialCoordinates, HorizontalCoordinates> {
    private final double cosH;
    private final double H;



    public EquatorialToHorizontalConversion(ZonedDateTime when, GeographicCoordinates where){
        H = SiderealTime.local(when,where)- where.lon(); // where.lon should be right ascencion
        cosH = Math.cos(H);
        //phi = where.lat() -> finish implementation


    }

    @Override
    public HorizontalCoordinates apply(EquatorialCoordinates equatorialCoordinates) {
        double phi = 0;
        double delta = 0; // declinaison
        double alpha = 0; // right asciension
        double H = 0;

        //TODO ask for atan2 remark about loosing point for that part and missing acos2 in library
        double A = Math.acos((Math.sin(delta)-Math.sin(phi)*Math.sin(alpha))/(Math.cos(phi)*Math.cos(alpha)));
        double h = Math.asin(Math.sin(delta)*Math.sin(phi)+Math.cos(delta)*Math.cos(phi)*cosH);

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


