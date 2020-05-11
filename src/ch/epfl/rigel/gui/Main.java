package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.AsterismLoader;
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
import javafx.beans.property.Property;
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
import javafx.util.converter.LocalTimeStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
            viewingParametersBean.setFieldOfViewDeg(100);
            //horizontalCoordProperty = viewingParametersBean.centerProperty();
            //fovProperty = viewingParametersBean.fieldOfViewDegProperty();

       //






        SkyCanvasManager skyCanvas = new SkyCanvasManager(catalogue,dateTimeBean,observerLocationBean,viewingParametersBean);

        Canvas sky = skyCanvas.canvas();
        Pane skyPane = new Pane(sky);

        borderPane.setTop(controlBar(skyCanvas));
        borderPane.setCenter(skyPane);
        borderPane.setBottom(informationBar(skyCanvas));

        sky.widthProperty().bind(skyPane.widthProperty());
        sky.heightProperty().bind(skyPane.heightProperty());


        mainStage.setScene(new Scene(borderPane));

        mainStage.show();

        sky.requestFocus();

        }
    }

    private HBox controlBar(SkyCanvasManager skyCanvas) throws  IOException{
        HBox mainControlBar = new HBox();
        mainControlBar.setStyle("-fx-spacing: 4; -fx-padding: 4;");

        Separator sepVert = new Separator();
        Separator sepVert2 = new Separator();
        sepVert.setOrientation(Orientation.VERTICAL);
        sepVert2.setOrientation(Orientation.VERTICAL);

        HBox child1 = observerPos(skyCanvas.observerLonDegProperty(), skyCanvas.observerLatDegProperty());
        HBox child2 = observInstant(skyCanvas);
        HBox child3 = timeManager();

        mainControlBar.getChildren().addAll(child1,sepVert,child2,sepVert2,child3);

        return mainControlBar;
    }

    private HBox observerPos(DoubleProperty observerLonDegProperty, DoubleProperty observerLatDegProperty){
        HBox observerPosBox = new HBox();
        observerPosBox.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");

        Label longiLabel = new Label("Longitude (°) :");
        Label latiLabel = new Label("Latitude (°) :");

        TextField inpLongi = new TextField();
        TextFormatter<Number> formatterLon = formatter("lon");
        inpLongi.setTextFormatter(formatterLon);
        Bindings.bindBidirectional(formatterLon.valueProperty(),observerLonDegProperty);


        //TODO USE WITH BINDBIDIRECTIONAL

        TextField inpLati = new TextField();
        TextFormatter<Number> formatterLat = formatter("lat");
        inpLati.setTextFormatter(formatterLat);
        Bindings.bindBidirectional(formatterLat.valueProperty(),observerLatDegProperty);

        inpLongi.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");
        inpLati.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");

        observerPosBox.getChildren().setAll(longiLabel,inpLongi,latiLabel,inpLati);

        return  observerPosBox;
    }

    private HBox observInstant(SkyCanvasManager skyCanvas){
        HBox observInstantBox = new HBox();
        observInstantBox.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");

        Label dateLabel = new Label("Date :");
        Label timeLabel = new Label("Heure :");

        DateTimeFormatter hmsFormatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTimeStringConverter stringConverter =
                new LocalTimeStringConverter(hmsFormatter, hmsFormatter);
        TextFormatter<LocalTime> timeFormatter =
                new TextFormatter<>(stringConverter);

        DatePicker datePicker = new DatePicker();
        datePicker.setStyle("-fx-pref-width: 120;");

        Bindings.bindBidirectional(datePicker.valueProperty(),skyCanvas.dateProperty());

        TextField timeSelector = new TextField();
        timeSelector.setTextFormatter(timeFormatter); //TODO why doesnt work

        Bindings.bindBidirectional(timeFormatter.valueProperty(),skyCanvas.timeProperty());

        timeSelector.setStyle("-fx-pref-width: 75; -fx-alignment: baseline-right;");
        ComboBox zoneSelector = new ComboBox();
        //List.of(ZoneId.getAvailableZoneIds()).sort();
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

        Text leftText = new Text(String.format("Champ de vue : %.1f°",skyCanvas.getFieldOfViewDeg()));
        Text centerText = new Text();
        Text rightText = new Text(String.format("Azimut : %.2f°, hauteur : %.2f°",skyCanvas.getMouseAzDeg(),skyCanvas.getMouseAltDeg()));

        skyCanvas.objectUnderMouseProperty().addListener((p, o, n) -> {
            if (n != null){
                centerText.setText(n.info());
            }else{
                centerText.setText("NULL");
            }
        }); //TODO CHECK THAT -- see when null PROBLEM VALUE STAY SAME EVEN IF SHOULD BE NULL

        sb = Bindings.createStringBinding(()->String.format("Azimut : %.2f°, hauteur : %.2f°",skyCanvas.getMouseAzDeg(),skyCanvas.getMouseAltDeg()),skyCanvas.mouseAltDegProperty(),skyCanvas.mouseAzDegProperty());



        sb.addListener((p, o, n)->{rightText.setText(sb.getValue());});
        skyCanvas.fieldOfViewDegProperty().addListener((p, o, n)->{leftText.setText(String.format("Champ de vue : %.1f°",skyCanvas.getFieldOfViewDeg()));});

        informationPane.setLeft(leftText);
        informationPane.setCenter(centerText);
        informationPane.setRight(rightText);

        return informationPane;
    }



 //TODO create method

    private TextFormatter<Number> formatter(String type){
        NumberStringConverter stringConverter =
                new NumberStringConverter("#0.00"); //TODO CHANGE FOR ,. see on internet genre LOCALE

        UnaryOperator<TextFormatter.Change> lonLatFilter = (change -> {
            try {
                String newText =
                        change.getControlNewText();
                double newLonLatDeg =
                        stringConverter.fromString(newText).doubleValue();
                if(type.equals("lon")){
                    System.out.println("0");
                    return GeographicCoordinates.isValidLonDeg(newLonLatDeg)
                            ? change
                            : null;
                }else if(type.equals("lat")){
                    System.out.println("1");
                    return GeographicCoordinates.isValidLatDeg(newLonLatDeg)
                            ? change
                            : null;
                }else{
                    return  null;
                }
            } catch (Exception e) {
                return null;
            }
        });

        TextFormatter<Number> lonLatTextFormatter =
                new TextFormatter<>(stringConverter, 0, lonLatFilter);
        return lonLatTextFormatter;
    }




}
