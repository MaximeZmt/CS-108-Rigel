package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.*;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.transform.Transform;

import java.util.List;
import java.util.Set;

/**
 * Instantiable class that represent a sky painter,
 * an object that is able to paint the sky
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public final class SkyCanvasPainter {
    private final static double NORTH_AZIMUTH = 0;
    private final static double NORTH_EAST_AZIMUTH = 45;
    private final static double EAST_AZIMUTH = 90;
    private final static double SOUTH_EAST_AZIMUTH = 135;
    private final static double SOUTH_AZIMUTH = 180;
    private final static double SOUTH_WEST_AZIMUTH = 225;
    private final static double WEST_AZIMUTH = 270;
    private final static double NORTH_WEST_AZIMUTH = 315;
    private final static double LETTER_ALTITUDE = -0.5;

    private final Canvas canvas;
    private final GraphicsContext ctx;
    private final static ClosedInterval MAGNITUDE_INTERVAL = ClosedInterval.of(-2, 5);

    /**
     * Public constructor to create a painter
     *
     * @param canvas Canvas that will be edited.
     */
    public SkyCanvasPainter(Canvas canvas){
        this.canvas = canvas;
        ctx = canvas.getGraphicsContext2D();
    }

    /**
     * Clear the Canvas of the painter
     */
    public void clear(){
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Draw on the canvas of the painter: Asterism and Stars
     *
     * @param sky The sky which the painter will paint
     * @param projection The projection from real world onto a plane
     * @param planeToCanvas Change from projection coordinate system to Canvas
     */
    public void drawStars(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas){
        List<Star> starList = sky.stars();
        double[] starPos = sky.starsPosition();
        planeToCanvas.transform2DPoints(starPos, 0, starPos, 0, starList.size());

        //asterism
        Set<Asterism> asterismSet = sky.getAsterism();
        ctx.setStroke(Color.BLUE);
        for (Asterism asterism : asterismSet){
            List<Integer> indiceList = sky.getAsterismIndices(asterism);
            double x0 = starPos[2 * indiceList.get(0)];
            double y0 = starPos[2 * indiceList.get(0) + 1];
            ctx.beginPath();
            ctx.moveTo(x0, y0);

            int counter = 0;
            for (int indice : indiceList.subList(0, indiceList.size() - 1)){
                int nextIndice = indiceList.get(counter + 1);
                double x1 = starPos[2 * indice];
                double y1 = starPos[2 * indice + 1];
                double x2 = starPos[2 * nextIndice];
                double y2 = starPos[2 * nextIndice + 1];

                if (canvas.getBoundsInLocal().contains(x1, y1) || canvas.getBoundsInLocal().contains(x2, y2)){
                    ctx.lineTo(x2, y2);
                } else {
                    ctx.moveTo(x2, y2);
                }
                counter++;
            }
            ctx.stroke();
        }

        //stars
        double multiplyFactor = projection.applyToAngle(Angle.ofDeg(0.5));

        for (Star s : starList) {
            double starMagn = s.magnitude();
            double diameter = objectDiameter(starMagn, multiplyFactor);
            int index = starList.indexOf(s);
            ctx.setFill(BlackBodyColor.colorForTemperature(s.colorTemperature()));
            double diam2 = planeToCanvas.deltaTransform(diameter, 0).getX();
            double x = starPos[2 * index] - (0.5 * diam2);
            double y = starPos[(2 * index) + 1] - (0.5 * diam2);
            ctx.fillOval(x, y, diam2, diam2);
        }
    }

    /**
     * Draw on the canvas of the painter: planets
     *
     * @param sky The sky which the painter will paint
     * @param projection The projection from real world onto a plane
     * @param planeToCanvas Change from projection coordinate system to Canvas
     */
    public void drawPlanets(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas){
        List<Planet> planetList = sky.planets();
        double[] planetCoord = sky.planetPositions();
        planeToCanvas.transform2DPoints(planetCoord, 0, planetCoord, 0, planetList.size());
        double multiplyFactor = projection.applyToAngle(Angle.ofDeg(0.5));
        int counter = 0;
        for(Planet p : planetList){
            double planetMagn = p.magnitude();
            double diameter = objectDiameter(planetMagn, multiplyFactor);
            ctx.setFill(Color.LIGHTGRAY);
            double diam2 = planeToCanvas.deltaTransform(diameter, 0).getX();
            double x = planetCoord[(2 * counter)] - 0.5 * diam2;
            double y = planetCoord[(2 * counter) + 1] - 0.5 * diam2;
            ctx.fillOval(x, y, diam2, diam2);
            counter++;
        }
    }

    /**
     * Draw on the canvas of the painter: The sun
     *
     * @param sky The sky which the painter will paint
     * @param projection The projection from real world onto a plane
     * @param planeToCanvas Change from projection coordinate system to Canvas
     */
    public void drawSun(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas){

        CartesianCoordinates sunPos = sky.sunPosition();
        Point2D planeCoord = planeToCanvas.transform(sunPos.x(), sunPos.y());
        double sunDiam = projection.applyToAngle(Angle.ofDeg(0.5));

        double sunDiamTransformed = planeToCanvas.deltaTransform(sunDiam, 0).getX();
        Color c = Color.YELLOW.deriveColor(0, 1, 1, 0.25);
        ctx.setFill(c);
        double diam1 = sunDiamTransformed * 2.2;
        double x1 = planeCoord.getX() - (0.5 * diam1);
        double y1 = planeCoord.getY() - (0.5 * diam1);
        ctx.fillOval(x1, y1, diam1, diam1);

        ctx.setFill(Color.YELLOW);
        double diam2 = sunDiamTransformed + 2;
        double x2 = planeCoord.getX() - (0.5 * diam2);
        double y2 = planeCoord.getY() - (0.5 * diam2);
        ctx.fillOval(x2, y2, diam2, diam2);

        ctx.setFill(Color.WHITE);
        double x3 = planeCoord.getX() - (0.5 * sunDiamTransformed);
        double y3 = planeCoord.getY() - (0.5 * sunDiamTransformed);
        ctx.fillOval(x3, y3, sunDiamTransformed, sunDiamTransformed);
    }

    /**
     * Draw on the canvas of the painter: The moon
     *
     * @param sky The sky which the painter will paint
     * @param projection The projection from real world onto a plane
     * @param planeToCanvas Change from projection coordinate system to Canvas
     */
    public void drawMoon(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas){
        Moon moon = sky.moon();

        CartesianCoordinates moonPos = sky.moonPosition();
        double moonDiam = projection.applyToAngle(moon.angularSize());
        double moonDiamTransformed = planeToCanvas.deltaTransform(moonDiam,0).getX();
        Point2D coordTransformed = planeToCanvas.transform(moonPos.x(), moonPos.y());
        double x = coordTransformed.getX() - (0.5 * moonDiamTransformed);
        double y = coordTransformed.getY() - (0.5 * moonDiamTransformed);


        double phase = moon.getPhase();

        //TODO do with rightopeninterval

        ctx.setFill(Color.BLACK);
        ctx.fillOval(x, y, moonDiamTransformed, moonDiamTransformed);
        if(phase >= 0.20 && phase < 0.40){
            // crescent
            ctx.setFill(Color.WHITE);
            ctx.fillArc(x, y, moonDiamTransformed, moonDiamTransformed,90,180, ArcType.ROUND);
            ctx.setFill(Color.BLACK);
            ctx.fillArc(x + (moonDiamTransformed / 3), y, (moonDiamTransformed / 2), moonDiamTransformed,90,180, ArcType.ROUND);
        }else if(phase >= 0.40 && phase < 0.60){
            // half moon
            ctx.setFill(Color.WHITE);
            ctx.fillArc(x, y, moonDiamTransformed, moonDiamTransformed,90,180, ArcType.ROUND);
        }else if (phase >= 0.60 && phase < 0.80){
            // gibbous moon
            ctx.setFill(Color.WHITE);
            ctx.fillArc(x, y, moonDiamTransformed, moonDiamTransformed,90,180, ArcType.ROUND);
        }else if (phase >= 0.80){
            ctx.setFill(Color.WHITE);
            ctx.fillOval(x, y, moonDiamTransformed, moonDiamTransformed);
            ctx.fillArc(x + (moonDiamTransformed / 5), y, (moonDiamTransformed / 2), moonDiamTransformed,270,180, ArcType.ROUND);
        }
    }

    /**
     * Draw on the canvas of the painter: The Horizon and the octant name
     *
     * @param projection The projection from real world onto a plane
     * @param planeToCanvas Change from projection coordinate system to Canvas
     */
    public void drawHorizon(StereographicProjection projection, Transform planeToCanvas){
        HorizontalCoordinates parallel = HorizontalCoordinates.of(0, 0);
        CartesianCoordinates center = projection.circleCenterForParallel(parallel);
        Point2D centerTransformed = planeToCanvas.transform(center.x(), center.y());
        double radius = projection.circleRadiusForParallel(parallel);
        double diamTransformed = 2 * planeToCanvas.deltaTransform(radius, 0).getX();
        double x = centerTransformed.getX() - diamTransformed * 0.5;
        double y =  centerTransformed.getY() - diamTransformed * 0.5;
        ctx.setStroke(Color.RED);
        ctx.setLineWidth(2);
        ctx.strokeOval(x, y, diamTransformed, diamTransformed);
        ctx.setTextBaseline(VPos.TOP);

        HorizontalCoordinates north = HorizontalCoordinates.ofDeg(NORTH_AZIMUTH, LETTER_ALTITUDE);
        HorizontalCoordinates northEast = HorizontalCoordinates.ofDeg(NORTH_EAST_AZIMUTH, LETTER_ALTITUDE);
        HorizontalCoordinates east = HorizontalCoordinates.ofDeg(EAST_AZIMUTH, LETTER_ALTITUDE);
        HorizontalCoordinates southEast = HorizontalCoordinates.ofDeg(SOUTH_EAST_AZIMUTH, LETTER_ALTITUDE);
        HorizontalCoordinates south = HorizontalCoordinates.ofDeg(SOUTH_AZIMUTH, LETTER_ALTITUDE);
        HorizontalCoordinates southWest = HorizontalCoordinates.ofDeg(SOUTH_WEST_AZIMUTH, LETTER_ALTITUDE);
        HorizontalCoordinates west = HorizontalCoordinates.ofDeg(WEST_AZIMUTH, LETTER_ALTITUDE);
        HorizontalCoordinates northWest = HorizontalCoordinates.ofDeg(NORTH_WEST_AZIMUTH, LETTER_ALTITUDE);

        HorizontalCoordinates[] cardinalList = {north, northEast, east, southEast, south, southWest, west, northWest};

        for (HorizontalCoordinates cardinalPoint : cardinalList){
            CartesianCoordinates cardinalProjection = projection.apply(cardinalPoint);
            Point2D cardinalProjectionTransformed = planeToCanvas.transform(cardinalProjection.x(), cardinalProjection.y());
            ctx.setFill(Color.RED);
            ctx.fillText(
                    cardinalPoint.azOctantName("N", "E", "S", "O"),
                    cardinalProjectionTransformed.getX(),
                    cardinalProjectionTransformed.getY()
            );
        }
    }

    private static double objectDiameter(double magn, double multiplyFactor){
        double clipMagn = MAGNITUDE_INTERVAL.clip(magn);
        double sizeFactor = (99 - 17 * clipMagn) / 140;
        return sizeFactor * multiplyFactor;
    }

}
