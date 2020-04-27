package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;

/**
 * [fillTxt]
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class ObserverLocationBean {
    private final DoubleProperty lonDeg = new SimpleDoubleProperty(0);
    private final DoubleProperty latDeg = new SimpleDoubleProperty(0);
    private final ObjectBinding<GeographicCoordinates> coordinates = Bindings.createObjectBinding(()->
            GeographicCoordinates.ofDeg(lonDeg.get(),latDeg.get()),lonDeg,latDeg);

    public double getLonDeg() {
        return lonDeg.get();
    }
    public DoubleProperty lonDegProperty() {
        return lonDeg;
    }
    public void setLonDeg(double lonDeg) {
        this.lonDeg.set(lonDeg);
    }

    public double getLatDeg() {
        return latDeg.get();
    }
    public DoubleProperty latDegProperty() {
        return latDeg;
    }
    public void setLatDeg(double latDeg) {
        this.latDeg.set(latDeg);
    }

    public GeographicCoordinates getCoordinates() {
        return coordinates.get();
    }
    public ObjectBinding<GeographicCoordinates> coordinatesProperty() {
        return coordinates;
    }
    public void setCoordinates(GeographicCoordinates geographicCoordinates){
        setLonDeg(geographicCoordinates.lonDeg());
        setLatDeg(geographicCoordinates.latDeg());
    }

    //TODO pas getlong et getLat ?

}

