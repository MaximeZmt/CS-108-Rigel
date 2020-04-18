package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.Star;
import ch.epfl.rigel.astronomy.Sun;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.geometry.Point2D;
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
public class SkyCanvasPainter { //classe instanciable //TODO Instanciable = Finale:check in project, same as static = CAPS

    private final Canvas canvas;
    private final GraphicsContext ctx;
    private final static ClosedInterval MAGNITUDE_INTERVAL = ClosedInterval.of(-2,5);

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
        double multiplyFacor = projection.applyToAngle(Angle.ofDeg(0.5));
        double[] starPos = sky.starsPosition();
        double[] newStarPos = new double[starPos.length];
        planeToCanvas.transform2DPoints(starPos, 0, newStarPos, 0, (starPos.length / 2));
        for (Star s : starList) {
            double starMagn = s.magnitude();
            double diameter = ObjectDiameter(starMagn, multiplyFacor);
            int index = starList.indexOf(s);
            ctx.setFill(BlackBodyColor.colorForTemperature(s.colorTemperature()));
            double diam2 = planeToCanvas.deltaTransform(diameter, 0).getX(); //not sure to understand why deltaTransform and not transform
            double x = newStarPos[2 * index] - (0.5 * diam2);
            double y = newStarPos[(2 * index) + 1] - (0.5 * diam2);
            ctx.fillOval(x, y, diam2, diam2);
        }
    }

    public void drawPlanets(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas){


    }


    public void drawSun(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas){

        Sun sun = sky.sun();
        CartesianCoordinates sunPos = sky.sunPosition();
        Point2D planeCoord = planeToCanvas.deltaTransform(sunPos.x(),sunPos.y());
        double sunDiam = projection.applyToAngle(Angle.ofDeg(0.5)); //TODO why not angular size

        double sunDiamTransformed = planeToCanvas.deltaTransform(sunDiam,0).getX();
        Color c = Color.YELLOW.deriveColor(0,0,0,0.25);
        ctx.setFill(c);
        double diam1 = sunDiamTransformed *2.2;
        double x1 = planeCoord.getX() - (0.5*diam1);
        double y1 = planeCoord.getY()  - (0.5*diam1);
        System.out.println("x:"+x1);
        System.out.println("y:"+y1);
        ctx.fillOval(x1,y1,diam1,diam1);

        ctx.setGlobalAlpha(1);
        double diam2 = sunDiamTransformed+2;
        double x2 = planeCoord.getX() - (0.5*diam2);
        double y2 = planeCoord.getY()  - (0.5*diam2);
        ctx.fillOval(x2,y2,diam2,diam2);

        ctx.setFill(Color.WHITE);
        double x3 = planeCoord.getX() - (0.5*sunDiamTransformed);
        double y3 = planeCoord.getY()  - (0.5*sunDiamTransformed);
        ctx.fillOval(x3,y3,sunDiamTransformed,sunDiamTransformed);



    /*
    un disque dont le diamètre est celui du Soleil et la couleur Color.WHITE,
un disque dont le diamètre est celui du Soleil plus 2 et la couleur Color.YELLOW,
un disque dont le diamètre est celui du Soleil multiplié par 2.2 et dont la couleur est Color.YELLOW
mais avec une opacité de 25% seulement, représentant un halo autour du Soleil.
     */


    }



    public void drawMoon(){}
    public void drawHorizon(){}

    static double ObjectDiameter(double magn, double multiplyFactor){
        double clipMagn = MAGNITUDE_INTERVAL.clip(magn);
        double sizeFactor = (99-17*clipMagn)/140;
        double diameter = sizeFactor*multiplyFactor;
        return diameter;
    }

}
