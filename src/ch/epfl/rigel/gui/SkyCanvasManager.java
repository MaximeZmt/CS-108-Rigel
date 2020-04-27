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
    private final DoubleProperty mouseAzDeg = new SimpleDoubleProperty();
    private final DoubleProperty mouseAltDeg = new SimpleDoubleProperty();
    private final ObjectProperty<CelestialObject> objectUnderMouse = new SimpleObjectProperty<>();

    public SkyCanvasManager(StarCatalogue catalogue,
                     DateTimeBean dateTimeBean,
                     ObserverLocationBean observerLocationBean,
                     ViewingParametersBean viewingParametersBean
                     ){
/*
        crée un certain nombre de propriétés et liens décrits plus bas,
                installe un auditeur (listener) pour être informé des mouvements du curseur de la souris, et stocker sa position dans une propriété,
        installe un auditeur pour détecter les clics de la souris sur le canevas et en faire alors le destinataire des événements clavier,
        installe un auditeur pour réagir aux mouvements de la molette de la souris et/ou du trackpad et changer le champ de vue en fonction,
                installe un auditeur pour réagir aux pressions sur les touches du curseur et changer le centre de projection en fonction,
                installe des auditeurs pour être informé des changements des liens et propriétés ayant un impact sur le dessin du ciel, et demander dans ce cas au peintre de le redessiner.

        
 */

    }

    /*
    créer le canevas sur lequel le ciel est dessiné,
créer le peintre chargé du dessin du ciel et lui faire redessiner le ciel chaque fois que cela est nécessaire,
changer le champ de vue lorsque l'utilisateur manipule la molette de la souris et/ou le trackpad,
changer le centre de projection lorsque l'utilisateur appuie sur les touches du curseur,
suivre les mouvements de la souris et exporter, via des propriétés, la position de son curseur dans le système de coordonnées horizontal, et l'objet céleste le plus proche de ce curseur.
     */
}
