package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * [fillTxt]
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public class Main extends Application {

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage mainStage) throws Exception {

        //WINDOWS
        mainStage.setTitle("Rigel");
        mainStage.setMinHeight(600);
        mainStage.setMinWidth(800);
        //END WINDOWS

        //MAIN BP
        BorderPane borderPane = new BorderPane();
        /*borderPane.setTop(toolbar);
        borderPane.setCenter(appContent);
        borderPane.setBottom(statusbar);
        //END MAIN BP

         */

        //

        borderPane.setTop(controlBar());


        mainStage.setScene(new Scene(borderPane));

        mainStage.show();
    }

    private HBox controlBar() throws  IOException{
        HBox mainControlBar = new HBox();
        mainControlBar.setStyle("-fx-spacing: 4; -fx-padding: 4;");

        Separator sepVert = new Separator();
        Separator sepVert2 = new Separator();
        sepVert.setOrientation(Orientation.VERTICAL);
        sepVert2.setOrientation(Orientation.VERTICAL);

        HBox child1 = observerPos();
        HBox child2 = observInstant();
        HBox child3 = timeManager();

        mainControlBar.getChildren().addAll(child1,sepVert,child2,sepVert2,child3);

        return mainControlBar;
    }

    private HBox observerPos(){
        HBox observerPosBox = new HBox();
        observerPosBox.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");

        Label longiLabel = new Label("Longitude (°) :");
        Label latiLabel = new Label("Latitude (°) :");

        TextField inpLongi = new TextField();
        inpLongi.setTextFormatter(lonTextFormatter);
        TextField inpLati = new TextField();
        inpLati.setTextFormatter(null);

        inpLongi.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");
        inpLati.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");

        observerPosBox.getChildren().setAll(longiLabel,inpLongi,latiLabel,inpLati);

        return  observerPosBox;
    }

    private HBox observInstant(){
        HBox observInstantBox = new HBox();
        observInstantBox.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");

        Label dateLabel = new Label("Date :");
        Label timeLabel = new Label("Heure :");

        DatePicker datePicker = new DatePicker();
        datePicker.setStyle("-fx-pref-width: 120;");
        TextField timeSelector = new TextField();
        timeSelector.setStyle("-fx-pref-width: 75; -fx-alignment: baseline-right;");
        ComboBox zoneSelector = new ComboBox();
        //zoneSelector.setItems();
        zoneSelector.setStyle("-fx-pref-width: 180;");

        observInstantBox.getChildren().addAll(dateLabel,datePicker,timeLabel, timeSelector,zoneSelector);

        return observInstantBox;
    }

    private HBox timeManager() throws IOException {

        InputStream fontStream = getClass()
                .getResourceAsStream("/Font Awesome 5 Free-Solid-900.otf");
        Font fontAwesome = Font.loadFont(fontStream, 15);

        Button resetButton = new Button("\uf0e2");
        resetButton.setFont(fontAwesome);
        Button playButton = new Button("\uf04b");
        playButton.setFont(fontAwesome);
        Button pauseButton = new Button("\uf04c");
        pauseButton.setFont(fontAwesome);


        fontStream.close();

        HBox timeManagerbox = new HBox();
        timeManagerbox.setStyle("-fx-spacing: inherit;");

        ChoiceBox acceleratorSelector = new ChoiceBox();
        acceleratorSelector.setItems(FXCollections.observableList(List.of(NamedTimeAccelerator.values())));

        timeManagerbox.getChildren().addAll(acceleratorSelector,resetButton,playButton,pauseButton);

        return timeManagerbox;
    }


    NumberStringConverter stringConverter =
            new NumberStringConverter("#0.00");

    UnaryOperator<TextFormatter.Change> lonFilter = (change -> {
        try {
            String newText =
                    change.getControlNewText();
            double newLonDeg =
                    stringConverter.fromString(newText).doubleValue();
            return GeographicCoordinates.isValidLonDeg(newLonDeg)
                    ? change
                    : null;
        } catch (Exception e) {
            return null;
        }
    });

    TextFormatter<Number> lonTextFormatter =
            new TextFormatter<>(stringConverter, 0, lonFilter);



}
