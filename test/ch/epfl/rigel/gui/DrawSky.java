package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.*;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.time.ZonedDateTime;


public final class DrawSky extends Application {
    public static void main(String[] args) { launch(args); }

    private InputStream resourceStream(String resourceName) {
        return getClass().getResourceAsStream(resourceName);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try (InputStream hs = resourceStream("/hygdata_v3.csv");
             InputStream as = resourceStream("/asterisms.txt")){
            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hs, HygDatabaseLoader.INSTANCE)
                    .loadFrom(as, AsterismLoader.INSTANCE)
                    .build();

            ZonedDateTime when =
                    ZonedDateTime.parse("2020-02-17T20:17:08+01:00");
                    //ZonedDateTime.parse("2020-02-17T20:15:00+01:00");
                    //ZonedDateTime.parse("2020-02-17T20:17:08+01:00");
                    //ZonedDateTime.parse("2020-02-17T14:15:00+01:00"); //time where we see the sun
                    //ZonedDateTime.parse("2020-02-17T09:15:00+01:00"); //time where we see the moon
            GeographicCoordinates where =
                    GeographicCoordinates.ofDeg(6.57, 46.52);
            HorizontalCoordinates projCenter =
                    HorizontalCoordinates.ofDeg(0,90);
                    //HorizontalCoordinates.ofDeg(180, 45); //WE LOOK AT 180(=South) and 45 degrée alt
            StereographicProjection projection;
            projection = new StereographicProjection(projCenter);
            ObservedSky sky =
                    new ObservedSky(when, where, projection, catalogue);

            Canvas canvas =
                    new Canvas(800, 600);
                    //new Canvas(1000, 800);
            Transform planeToCanvas =
                    //Transform.affine(1300, 0, 0, -1300, 400, 300);
                    Transform.affine(260, 0, 0, -260, 400, 300);
            SkyCanvasPainter painter =
                    new SkyCanvasPainter(canvas);

            painter.clear();
            //painter.drawStars(sky, projection, planeToCanvas);
            painter.drawSun(sky, projection, planeToCanvas);
            painter.drawMoon(sky, projection, planeToCanvas);
            painter.drawPlanets(sky,projection,planeToCanvas);
            painter.drawHorizon(projection,planeToCanvas);

            WritableImage fxImage =
                    canvas.snapshot(null, null);
            BufferedImage swingImage =
                    SwingFXUtils.fromFXImage(fxImage, null);
            ImageIO.write(swingImage, "png", new File("sky.png"));
        }
        Platform.exit();
    }
}