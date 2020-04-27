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

   private  final ObservableValue<GeographicCoordinates> coordinates = Bindings.createObjectBinding(()->GeographicCoordinates.ofDeg(lonDeg.get(),latDeg.get()),lonDeg,latDeg);

   public DoubleProperty lonDegProperty(){return lonDeg;};

   public DoubleProperty latDegProperty(){return latDeg};

   // pas getlong et getLat

    public ObservableValue<GeographicCoordinates> coordinatesProperty(){return coordinates;}

    public GeographicCoordinates getCoordinates(){return  coordinates.getValue();}

    public void setCoordinates(GeographicCoordinates newGeoCoordinates){
        lonDeg.setValue(newGeoCoordinates.lonDeg());
        latDeg.setValue(newGeoCoordinates.latDeg());
    }




           /*
    la longitude de la position de l'observateur, en degrés, nommée p.ex. lonDeg,
    la latitude de la position de l'observateur, en degrés, nommée p.ex. latDeg,
    les deux coordonnées précédentes, mais combinées en une instance de GeographicCoordinates, nommée p.ex. coordinates.
    La dernière de ces « propriétés » est en fait un lien créé au moyen de la méthode createObjectBinding et dont la valeur est déterminée par les deux autres propriétés.

            L'intérêt d'offrir ainsi la position de l'observateur sous deux formes, à savoir « déconstruite » (longitude et latitude séparée) et composite (longitude et latitude combinées dans une instance de GeographicCoordinates) est que cela facilite la construction de l'interface utilisateur, comme nous le verrons à l'étape suivante.
*/
}

