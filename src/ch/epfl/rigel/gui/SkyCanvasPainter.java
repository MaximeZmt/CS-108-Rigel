package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.*;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;

import java.util.List;
import java.util.Set;

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
        double[] starPos = sky.starsPosition();
        //double[] newStarPos = new double[starPos.length];
        planeToCanvas.transform2DPoints(starPos, 0, starPos, 0, starList.size()); //peut être remis dans star pos


        //asterism
        Set<Asterism> asterismSet = sky.asterisms();
        ctx.setStroke(Color.BLUE);
        for (Asterism asterism : asterismSet){
            List<Integer> indiceList = sky.asterismIndices(asterism);
            double x0 = starPos[2*indiceList.get(0)];
            double y0 = starPos[2*indiceList.get(0)+1];
            ctx.beginPath();
            ctx.moveTo(x0, y0);

            int counter = 0;
            for (int indice : indiceList.subList(0,indiceList.size()-1)){
                int nextIndice = indiceList.get(counter+1);
                double x1 = starPos[2*indice];
                double y1 = starPos[2*indice+1];
                double x2 = starPos[2*nextIndice];
                double y2 = starPos[2*nextIndice+1];

                //TODO not sure if working bc test context (time and place) is not propice for check
                if (canvas.getBoundsInLocal().contains(x1,y1) || canvas.getBoundsInLocal().contains(x2,y2)){
                    ctx.lineTo(x2, y2);
                } else {
                    ctx.moveTo(x2,y2);
                }
                counter++;
            }
            ctx.stroke();
        }


        //stars
        double multiplyFactor = projection.applyToAngle(Angle.ofDeg(0.5));

        for (Star s : starList) {
            double starMagn = s.magnitude();
            double diameter = objectDiameter(starMagn, multiplyFactor); //min method
            int index = starList.indexOf(s);
            ctx.setFill(BlackBodyColor.colorForTemperature(s.colorTemperature()));
            double diam2 = planeToCanvas.deltaTransform(diameter, 0).getX(); //not sure to understand why deltaTransform and not transform
            double x = starPos[2 * index] - (0.5 * diam2);
            double y = starPos[(2 * index) + 1] - (0.5 * diam2);
            ctx.fillOval(x, y, diam2, diam2);
            if(s.name().equals("Betelgeuse")){
                System.out.println("Test");
                System.out.println("Temperature: "+s.colorTemperature());
                System.out.println("Color: "+BlackBodyColor.colorForTemperature(s.colorTemperature()));
                System.out.printf("X: %.15f \n",x);
                System.out.printf("Y: %.15f \n",y);
                System.out.printf("diam: %.15f \n",diam2);
            }
        }
    }

    public void drawPlanets(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas){
        List<Planet> planetList = sky.planets();
        double[] planetCoord = sky.planetPositions();
        //double[] newPlanetCoord = new double[planetCoord.length];
        planeToCanvas.transform2DPoints(planetCoord, 0, planetCoord, 0, planetList.size());
        double multiplyFactor = projection.applyToAngle(Angle.ofDeg(0.5));
        int counter = 0;
        for(Planet p: planetList){
            double planetMagn = p.magnitude();
            double diameter = objectDiameter(planetMagn,multiplyFactor);
            ctx.setFill(Color.LIGHTGRAY);
            double diam2 = planeToCanvas.deltaTransform(diameter,0).getX();
            double x = planetCoord[(2*counter)];
            double y = planetCoord[(2*counter)+1];
            counter++;
            //System.out.println("Planet: "+p.name()+" created");
        }
    }


    public void drawSun(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas){

        Sun sun = sky.sun();
        CartesianCoordinates sunPos = sky.sunPosition();
        Point2D planeCoord = planeToCanvas.deltaTransform(sunPos.x(),sunPos.y());
        double sunDiam = projection.applyToAngle(Angle.ofDeg(0.5));

        double sunDiamTransformed = planeToCanvas.deltaTransform(sunDiam,0).getX();
        Color c = Color.YELLOW.deriveColor(0,1,1,0.25);
        ctx.setFill(c);
        double diam1 = sunDiamTransformed *2.2;
        double x1 = planeCoord.getX() - (0.5*diam1);
        double y1 = planeCoord.getY()  - (0.5*diam1);
        //System.out.println("x:"+x1);
        //System.out.println("y:"+y1);
        ctx.fillOval(x1,y1,diam1,diam1);

        ctx.setFill(Color.YELLOW);
        double diam2 = sunDiamTransformed+2;
        double x2 = planeCoord.getX() - (0.5*diam2);
        double y2 = planeCoord.getY()  - (0.5*diam2);
        ctx.fillOval(x2,y2,diam2,diam2);

        ctx.setFill(Color.WHITE);
        double x3 = planeCoord.getX() - (0.5*sunDiamTransformed);
        double y3 = planeCoord.getY()  - (0.5*sunDiamTransformed);
        ctx.fillOval(x3,y3,sunDiamTransformed,sunDiamTransformed);

    }



    public void drawMoon(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas){
        Moon moon = sky.moon();
        CartesianCoordinates moonPos = sky.moonPosition();
        double moonDiam = projection.applyToAngle(moon.angularSize());
        double moonDiamTransformed = planeToCanvas.deltaTransform(moonDiam,0).getX();
        Point2D coordTransformed = planeToCanvas.deltaTransform(moonPos.x(),moonPos.y());
        double x = coordTransformed.getX() - (0.5*moonDiamTransformed);
        double y = coordTransformed.getY() - (0.5*moonDiamTransformed);
        //System.out.println("x:"+x);
        //System.out.println("y:"+y);
        ctx.setFill(Color.WHITE);
        ctx.fillOval(x,y,moonDiamTransformed,moonDiamTransformed);
    }

    public void drawHorizon(StereographicProjection projection, Transform planeToCanvas){

        HorizontalCoordinates parallel = HorizontalCoordinates.of(0,0);
        //System.out.println("modif (x,y)=("+centerModif.getX()+","+centerModif.getY()+")");
        CartesianCoordinates center = projection.circleCenterForParallel(parallel);
        Point2D centerTransformed = planeToCanvas.transform(center.x(), center.y());
        double diam = projection.circleRadiusForParallel(parallel);
        double diamTransformed = 2*planeToCanvas.deltaTransform(diam,0).getX();
        double x = centerTransformed.getX()-diamTransformed*0.5;
        double y =  centerTransformed.getY()-diamTransformed*0.5;
        ctx.setStroke(Color.RED);
        ctx.setLineWidth(2);
        ctx.strokeOval(x,y, diamTransformed, diamTransformed);

        //TODO check if optimal and check for +15
        HorizontalCoordinates north = HorizontalCoordinates.ofDeg(0,0);
        HorizontalCoordinates northEast = HorizontalCoordinates.ofDeg(45, 0);
        HorizontalCoordinates east = HorizontalCoordinates.ofDeg(90,0);
        HorizontalCoordinates southEast = HorizontalCoordinates.ofDeg(135,0);
        HorizontalCoordinates south = HorizontalCoordinates.ofDeg(180,0);
        HorizontalCoordinates southWest = HorizontalCoordinates.ofDeg(225,0);
        HorizontalCoordinates west = HorizontalCoordinates.ofDeg(270,0);
        HorizontalCoordinates northWest = HorizontalCoordinates.ofDeg(315,0);

        CartesianCoordinates northProjection = projection.apply(north);
        CartesianCoordinates northEastProjection = projection.apply(northEast);
        CartesianCoordinates eastProjection = projection.apply(east);
        CartesianCoordinates southEastProjection = projection.apply(southEast);
        CartesianCoordinates southProjection = projection.apply(south);
        CartesianCoordinates southWestProjection = projection.apply(southWest);
        CartesianCoordinates westProjection = projection.apply(west);
        CartesianCoordinates northWestProjection = projection.apply(northWest);

        Point2D northProjectionTransformed = planeToCanvas.transform(northProjection.x(),northProjection.y());
        Point2D northEastProjectionTransformed = planeToCanvas.transform(northEastProjection.x(),northEastProjection.y());
        Point2D eastProjectionTransformed = planeToCanvas.transform(eastProjection.x(),eastProjection.y());
        Point2D southEastProjectionTransformed = planeToCanvas.transform(southEastProjection.x(),southEastProjection.y());
        Point2D southProjectionTransformed = planeToCanvas.transform(southProjection.x(),southProjection.y());
        Point2D southWestProjectionTransformed = planeToCanvas.transform(southWestProjection.x(),southWestProjection.y());
        Point2D westProjectionTransformed = planeToCanvas.transform(westProjection.x(),westProjection.y());
        Point2D northWestProjectionTransformed = planeToCanvas.transform(northWestProjection.x(),northWestProjection.y());

        ctx.setFill(Color.RED);
        ctx.fillText(
                north.azOctantName("N","E","S","W"),
                northProjectionTransformed.getX(),
                northProjectionTransformed.getY()+15
        );
        ctx.fillText(
                northEast.azOctantName("N","E","S","W"),
                northEastProjectionTransformed.getX(),
                northEastProjectionTransformed.getY()+15
        );
        ctx.fillText(
                east.azOctantName("N","E","S","W"),
                eastProjectionTransformed.getX(),
                eastProjectionTransformed.getY()+15
        );
        ctx.fillText(
                southEast.azOctantName("N","E","S","W"),
                southEastProjectionTransformed.getX(),
                southEastProjectionTransformed.getY()+15
        );
        ctx.fillText(
                south.azOctantName("N","E","S","W"),
                southProjectionTransformed.getX(),
                southProjectionTransformed.getY()+15
        );
        ctx.fillText(
                southWest.azOctantName("N","E","S","W"),
                southWestProjectionTransformed.getX(),
                southWestProjectionTransformed.getY()+15
        );
        ctx.fillText(
                west.azOctantName("N","E","S","W"),
                westProjectionTransformed.getX(),
                westProjectionTransformed.getY()+15
        );
        ctx.fillText(
                northWest.azOctantName("N","E","S","W"),
                northWestProjectionTransformed.getX(),
                northWestProjectionTransformed.getY()+15
        );


    }

    static double objectDiameter(double magn, double multiplyFactor){ //TODO put in private at the end, now cannot cause test
        double clipMagn = MAGNITUDE_INTERVAL.clip(magn);
        double sizeFactor = (99-17*clipMagn)/140;
        double diameter = sizeFactor*multiplyFactor;
        return diameter;
    }

}
