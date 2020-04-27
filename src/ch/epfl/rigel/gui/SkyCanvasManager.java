package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;
import ch.epfl.rigel.astronomy.StarCatalogue;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * [description text]
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class SkyCanvasManager {
    DoubleProperty mouseAzDeg = new SimpleDoubleProperty();
    DoubleProperty mouseAltDeg = new SimpleDoubleProperty();
    ObjectProperty<CelestialObject> objectUnderMouse = new SimpleObjectProperty<>();

    SkyCanvasManager(StarCatalogue catalogue,
                     DateTimeBean dateTimeBean,
                     ViewingParametersBean viewingParametersBean,
                     ObserverLocationBean observerLocationBean){

    }
}
