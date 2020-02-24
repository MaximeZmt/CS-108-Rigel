package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.Epoch;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.Polynomial;

import java.time.*;
import java.util.function.Function;

//représente un changement de système de coordonnées depuis les coordonnées
// écliptiques vers les coordonnées équatoriales, à un instant donné.
public final class EclipticToEquatorialConversion implements Function<EclipticCoordinates, EquatorialCoordinates> {

    private final double cosEpsilon;
    private final double sinEpsilon;

    public EclipticToEquatorialConversion(ZonedDateTime when){
        double epsilon = Polynomial.of(
                Angle.ofDMS(0,0,0.00181),
                Angle.ofDMS(0,0,-0.0006),
                Angle.ofDMS(0,0,-46.815),
                Angle.ofDMS(23,26,21.45))
                .at(Epoch.J2000.julianCenturiesUntil(when));

        cosEpsilon = Math.cos(epsilon);
        sinEpsilon = Math.sin(epsilon);
    }

    @Override
    public EquatorialCoordinates apply(EclipticCoordinates ecl) {
        double lon = ecl.lon();
        double lat = ecl.lat();
        double ra = Math.atan2(Math.sin(lon)*cosEpsilon-Math.tan(lat)*sinEpsilon,
                Math.cos(lon));
        double dec = Math.asin(Math.sin(lat)*cosEpsilon+Math.cos(lat)*sinEpsilon*Math.sin(lon));
        return EquatorialCoordinates.of(Angle.ofDeg(ra),Angle.ofDeg(dec));
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("called hashCode from EclipticToEquatorialConversion");
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("called equals from EclipticToEquatorialConversion");
    }
}
