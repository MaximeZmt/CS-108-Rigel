package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.transform.Transform;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * [description text]
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class SkyCanvasManager {
    public final DoubleProperty mouseAzDeg = new SimpleDoubleProperty();
    public final DoubleProperty mouseAltDeg = new SimpleDoubleProperty(); //TODO check if public as online
    public final ObjectProperty<CelestialObject> objectUnderMouse = new SimpleObjectProperty<>();

    private final ObjectProperty<HorizontalCoordinates> center;
    private final ObjectProperty<LocalDate> date;
    private final ObjectProperty<LocalTime> time;
    private final ObjectProperty<ZoneId> zone;
    private final ObservableValue<GeographicCoordinates> observCoordinates;




    private final ObservableValue<StereographicProjection> projetion;
    private final ObservableValue<Transform> planeToCanvas;
    private final ObservableValue<ObservedSky> sky;

    public SkyCanvasManager(StarCatalogue catalogue,
                     DateTimeBean dateTimeBean,
                     ObserverLocationBean observerLocationBean,
                     ViewingParametersBean viewingParametersBean
                     ){

        Canvas canvas = new Canvas(1000, 800);
        center = viewingParametersBean.centerProperty(); //TODO should we create getCenter and setCenter ? don't think so
        date = dateTimeBean.dateProperty();
        time = dateTimeBean.timeProperty();
        zone = dateTimeBean.zoneProperty();
        observCoordinates = observerLocationBean.coordinatesProperty();



        // Transform planeToCanvas =
        //                    Transform.affine(1300, 0, 0, -1300, 400, 300);

        projetion = Bindings.createObjectBinding(()->new StereographicProjection(center.get()),center);
        sky = Bindings.createObjectBinding(()->new ObservedSky(ZonedDateTime.of(date.get(),time.get(),zone.get()),observCoordinates.getValue(),projetion.getValue(),catalogue),date,time,zone,observCoordinates,projetion);
        //planeToCanvas = Bindings.createObjectBinding(()->Transform.affine())

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
