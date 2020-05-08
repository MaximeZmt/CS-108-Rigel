package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.AsterismLoader;
import ch.epfl.rigel.astronomy.CelestialObject;
import ch.epfl.rigel.astronomy.HygDatabaseLoader;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    private StringBinding sb;


    //private ObjectProperty<> horizontalCoordProperty; //TODO --
    //private DoubleProperty fovProperty;

    public static void main(String[] args) { launch(args); }

    private InputStream getResourceStream(String resource) {
        return getClass().getResourceAsStream(resource);
    }


    @Override
    public void start(Stage mainStage) throws Exception {

        //WINDOWS
        mainStage.setTitle("Rigel");
        mainStage.setMinHeight(600);
        mainStage.setMinWidth(800);
        //END WINDOWS

        //MAIN BP
        BorderPane borderPane = new BorderPane();
        //

        try (InputStream hs = getResourceStream("/hygdata_v3.csv");
             InputStream hs2 = getResourceStream("/asterisms.txt")) {
            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hs, HygDatabaseLoader.INSTANCE)
                    .loadFrom(hs2, AsterismLoader.INSTANCE)
                    .build();

            ZonedDateTime when =
                    ZonedDateTime.parse("2020-02-17T20:15:00+01:00");
            DateTimeBean dateTimeBean = new DateTimeBean();
            dateTimeBean.setZonedDateTime(when);

            ObserverLocationBean observerLocationBean =
                    new ObserverLocationBean();
            observerLocationBean.setCoordinates(
                    GeographicCoordinates.ofDeg(6.57, 46.52));

            ViewingParametersBean viewingParametersBean=
                    new ViewingParametersBean();
            viewingParametersBean.setCenter(
                    HorizontalCoordinates.ofDeg(180.000000000001, 42));
            viewingParametersBean.setFieldOfViewDeg(70);
            //horizontalCoordProperty = viewingParametersBean.centerProperty();
            //fovProperty = viewingParametersBean.fieldOfViewDegProperty();

       //






        SkyCanvasManager skyCanvas = new SkyCanvasManager(catalogue,dateTimeBean,observerLocationBean,viewingParametersBean);

        Canvas sky = skyCanvas.canvas();
        Pane skyPane = new Pane(sky);

        borderPane.setTop(controlBar());
        borderPane.setCenter(skyPane);
        borderPane.setBottom(informationBar(skyCanvas));

        sky.widthProperty().bind(skyPane.widthProperty());
        sky.heightProperty().bind(skyPane.heightProperty());


        mainStage.setScene(new Scene(borderPane));

        mainStage.show();

        sky.requestFocus();

        }
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
        Button playPauseButton = new Button("\uf04b"); // "\uf04c" change play to pause
        playPauseButton.setFont(fontAwesome);


        fontStream.close();

        HBox timeManagerbox = new HBox();
        timeManagerbox.setStyle("-fx-spacing: inherit;");

        ChoiceBox acceleratorSelector = new ChoiceBox();
        acceleratorSelector.setItems(FXCollections.observableList(List.of(NamedTimeAccelerator.values())));

        timeManagerbox.getChildren().addAll(acceleratorSelector,resetButton,playPauseButton);

        return timeManagerbox;
    }

    private BorderPane informationBar(SkyCanvasManager skyCanvas){
        BorderPane informationPane = new BorderPane();
        informationPane.setStyle("-fx-padding: 4; -fx-background-color: white;");

        Text leftText = new Text("Champ de vue : <fov>°");
        Text centerText = new Text();
        Text rightText = new Text("Azimut : <az>°, hauteur : <alt>°");

        skyCanvas.objectUnderMouseProperty().addListener((p, o, n) -> {
            if (n != null){
                centerText.setText(n.info());
            }else{
                centerText.setText("NULL");
            }
        }); //TODO CHECK THAT -- see when null PROBLEM VALUE STAY SAME EVEN IF SHOULD BE NULL

        sb = Bindings.createStringBinding(()->String.format("Azimut : %.2f°, hauteur : %.2f°",skyCanvas.getMouseAzDeg(),skyCanvas.getMouseAltDeg()),skyCanvas.mouseAltDegProperty(),skyCanvas.mouseAzDegProperty());
        sb.addListener((p, o, n)->{rightText.setText(sb.getValue());});
       // horizontalCoordProperty.addListener((p, o, n) -> {if (n != null) rightText.setText(String.format("Azimut : %s°, hauteur : %s°",((HorizontalCoordinates)horizontalCoordProperty.get()).az(),((HorizontalCoordinates)horizontalCoordProperty.get()).alt()))}); //TODO CHECK THAT

        informationPane.setLeft(leftText);
        informationPane.setCenter(centerText);
        informationPane.setRight(rightText);

        return informationPane;
    }


    NumberStringConverter stringConverter =
            new NumberStringConverter("#0.00");
 //TODO create method
    UnaryOperator<TextFormatter.Change> lonFilter = (change -> {
        try {
            String newText =
                    change.getControlNewText();
            double newLonDeg =
                    stringConverter.fromString(newText).doubleValue();
            return GeographicCoordinates.isValidLonDeg(newLonDeg) //TODO is valid lat deg ?
                    ? change
                    : null;
        } catch (Exception e) {
            return null;
        }
    });

    TextFormatter<Number> lonTextFormatter =
            new TextFormatter<>(stringConverter, 0, lonFilter);



}
