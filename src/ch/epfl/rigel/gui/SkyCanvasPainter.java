package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.Star;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;

import java.util.List;

/**
 * [fillTxt]
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public class SkyCanvasPainter { //classe instanciable

    private final Canvas canvas;
    private final GraphicsContext ctx;
    private final static ClosedInterval MAGNITUDE_INTERVAL = ClosedInterval.of(-2,5);
    private final static double FIXED_TAN_FACTOR = Math.tan(Angle.ofDeg(0.5)/4);

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

    public void drawStars(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas){
        List<Star> starList = sky.stars();
        double[] starPos = sky.starsPosition();
        double[] newStarPos = new double[starPos.length];
        planeToCanvas.transform2DPoints(starPos,0,newStarPos,0,(starPos.length/2));
        int count = 0;
        for(Star s:starList){
            count++;
            if (count < 1000) {
                double starMagn = s.magnitude();
                double diameter = ObjectDiameter(starMagn);
                System.out.println(diameter);
                int index = starList.indexOf(s);
                ctx.setFill(Color.WHITE);
                double x = newStarPos[2 * index] - (0.5 * diameter);
                double y = newStarPos[(2 * index) + 1] - (0.5 * diameter);
                ctx.fillOval(x, y, x + diameter, y + diameter);
            }
        }
        //max size 95% of 0.5 degrees
        // min size 10% of diam of 0.5 degrees
        //MAGNITUDE_INTERVAL.clip(value)
    }

    public void drawPlanets(){}
    public void drawSun(){}
    public void drawMoon(){}
    public void drawHorizon(){}

    static double ObjectDiameter(double magn){
        double clipMagn = MAGNITUDE_INTERVAL.clip(magn);
        //System.out.println(clipMagn);
        double sizeFactor = (99-17*clipMagn)/140;
        //System.out.println(sizeFactor);
        double diameter = sizeFactor*2*FIXED_TAN_FACTOR;
        return diameter;
    }

}
