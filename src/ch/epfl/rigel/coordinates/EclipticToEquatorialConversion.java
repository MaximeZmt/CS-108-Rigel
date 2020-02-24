package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.Epoch;
import ch.epfl.rigel.math.Polynomial;

import java.time.*;
import java.util.function.Function;

//représente un changement de système de coordonnées depuis les coordonnées
// écliptiques vers les coordonnées équatoriales, à un instant donné.
public final class EclipticToEquatorialConversion implements Function<EclipticCoordinates, EquatorialCoordinates> {

    private double epsilon;

    public EclipticToEquatorialConversion(ZonedDateTime when){
        epsilon = Polynomial.of().at(Epoch.J2000.julianCenturiesUntil(ZonedDateTime.now()));
    }

    @Override
    public EquatorialCoordinates apply(EclipticCoordinates eclipticCoordinates) {
        return null;
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
