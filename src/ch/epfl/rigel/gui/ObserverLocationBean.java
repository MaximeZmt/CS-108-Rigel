package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.beans.binding.Bindings;
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
   private final ObservableValue<GeographicCoordinates> coordinates = Bindings.createObjectBinding(()->
           GeographicCoordinates.ofDeg(lonDeg.get(),latDeg.get()),lonDeg,latDeg);

   public DoubleProperty lonDegProperty(){
       return lonDeg;
   }
   public DoubleProperty latDegProperty(){
       return latDeg;
   }

   //TODO pas getlong et getLat ?
   public ObservableValue<GeographicCoordinates> coordinatesProperty(){
       return coordinates;
   }
   public GeographicCoordinates getCoordinates(){
       return  coordinates.getValue();
   }

   public void setCoordinates(GeographicCoordinates newGeoCoordinates){
       lonDeg.setValue(newGeoCoordinates.lonDeg());
       latDeg.setValue(newGeoCoordinates.latDeg());
   }
}

