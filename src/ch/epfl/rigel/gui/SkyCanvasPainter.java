package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.coordinates.StereographicProjection;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;

/**
 * [fillTxt]
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public class SkyCanvasPainter { //classe instanciable

    private final Canvas canvas;
    private final GraphicsContext ctx;

    public SkyCanvasPainter(Canvas canvas){ //suppose public cause instanciable
        this.canvas = canvas;
        ctx = canvas.getGraphicsContext2D();
    }

    /*
    pour les étoiles : la couleur obtenue via BlackBodyColor,
    pour les astérismes : Color.BLUE,
    pour les planètes : Color.LIGHTGRAY,
    pour le Soleil : voir ci-dessous,
    pour la Lune : Color.WHITE,
    pour l'horizon et les points intercardinaux : Color.RED.

    --
     le Soleil est représenté au moyen des trois disques concentriques suivants :

        un disque dont le diamètre est celui du Soleil et la couleur Color.WHITE,
        un disque dont le diamètre est celui du Soleil plus 2 et la couleur Color.YELLOW,
        un disque dont le diamètre est celui du Soleil multiplié par 2.2 et dont la couleur est Color.YELLOW mais avec une opacité de 25% seulement, représentant un halo autour du Soleil.
    Ces trois disques doivent bien entendu être dessinés du plus grand au plus petit.

     */

    public void clear(){
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
    }
    public void drawStars(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas){}
    public void drawPlanets(){}
    public void drawSun(){}
    public void drawMoon(){}
    public void drawHorizon(){}



}
