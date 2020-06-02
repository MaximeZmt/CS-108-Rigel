package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.AsterismLoader;
import ch.epfl.rigel.astronomy.HygDatabaseLoader;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * Main class of the project
 *
 * @author Michael Freeman (313215)
 * @author Maxime Zammit (310251)
 */
public class Main extends Application {
    private StringBinding sb;
    private DatePicker datePicker;
    private TextField timeSelector;
    private ComboBox<ZoneId> zoneSelector;

    private final static double MAIN_STAGE_MIN_HEIGHT = 600;
    private final static double MAIN_STAGE_MIN_WIDTH = 800;
    private final static double OBSERVER_LONGITUDE = 6.57;
    private final static double OBSERVER_LATITUDE = 46.52;
    private final static double VIEWING_PARAMETER_CENTER_AZIMUTH = 180.000000000001;
    private final static double VIEWING_PARAMETER_CENTER_ALTITUDE = 42;
    private final static double VIEWING_PARAMETER_FIELD_OF_VIEW = 100;
    private final static double FONT_SIZE = 15;
    private final static String FILE_NAME_ASTERISM = "/asterisms.txt";
    private final static String FILE_NAME_HYGDATA = "/hygdata_v3.csv";
    private final static String FILE_NAME_FONT = "/Font Awesome 5 Free-Solid-900.otf";

    /**
     * Main method of the project
     *
     * @param args string
     */
    public static void main(String[] args) {
        launch(args);
    }

    private InputStream getResourceStream(String resource) {
        return getClass().getResourceAsStream(resource);
    }

    /**
     * @see Application#start(Stage)
     */
    @Override
    public void start(Stage mainStage) throws Exception {
        //WINDOWS
        mainStage.setTitle("Rigel");
        mainStage.setMinHeight(MAIN_STAGE_MIN_HEIGHT);
        mainStage.setMinWidth(MAIN_STAGE_MIN_WIDTH);

        //MAIN BP
        BorderPane borderPane = new BorderPane();

        try (InputStream hs = getResourceStream(FILE_NAME_HYGDATA);
             InputStream hs2 = getResourceStream(FILE_NAME_ASTERISM)) {

            //star catalogue
            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hs, HygDatabaseLoader.INSTANCE)
                    .loadFrom(hs2, AsterismLoader.INSTANCE)
                    .build();

            //zone date time
            DateTimeBean dateTimeBean = new DateTimeBean();
            dateTimeBean.setZonedDateTime(ZonedDateTime.now());

            //observer location
            ObserverLocationBean observerLocationBean =
                    new ObserverLocationBean();
            observerLocationBean.setCoordinates(
                    GeographicCoordinates.ofDeg(OBSERVER_LONGITUDE, OBSERVER_LATITUDE));

            //viewing parameters
            ViewingParametersBean viewingParametersBean=
                    new ViewingParametersBean();
            viewingParametersBean.setCenter(
                    HorizontalCoordinates.ofDeg(VIEWING_PARAMETER_CENTER_AZIMUTH, VIEWING_PARAMETER_CENTER_ALTITUDE));
            viewingParametersBean.setFieldOfViewDeg(VIEWING_PARAMETER_FIELD_OF_VIEW);

            TimeAnimator timeAnimator = new TimeAnimator(dateTimeBean);
            SkyCanvasManager skyCanvas = new SkyCanvasManager(catalogue, dateTimeBean, observerLocationBean, viewingParametersBean);

            Canvas sky = skyCanvas.canvas();
            Pane skyPane = new Pane(sky);

            borderPane.setTop(controlBar(skyCanvas, timeAnimator));
            borderPane.setCenter(skyPane);
            borderPane.setBottom(informationBar(skyCanvas));


            sky.widthProperty().bind(skyPane.widthProperty());
            sky.heightProperty().bind(skyPane.heightProperty());

            Scene mainScene = new Scene(borderPane);
            mainScene.getStylesheets().add("/main.css");
            mainStage.setScene(mainScene);

            mainStage.show();

            sky.requestFocus();
        }
    }

    private HBox controlBar(SkyCanvasManager skyCanvas, TimeAnimator timeAnimator) throws IOException {
        HBox mainControlBar = new HBox();
        mainControlBar.setStyle("-fx-spacing: 4; -fx-padding: 4;");



        /*
            ATTEMPT
            */
        Menu cm = new Menu("Options");
        cm.getStyleClass().add("cmMenu");


        CheckMenuItem cb1 = new CheckMenuItem("Show Asterisms");
        CheckMenuItem cb2 = new CheckMenuItem("Show Stars");
        CheckMenuItem cb3 = new CheckMenuItem("Show Sun");
        CheckMenuItem cb4 = new CheckMenuItem("Show Moon");
        CheckMenuItem cb5 = new CheckMenuItem("Show Planet");
        CheckMenuItem cb6 = new CheckMenuItem("Show Horizon");

        cm.getItems().addAll(cb1, cb2, cb3, cb4, cb5, cb6);
        Separator sepVert3 = new Separator();
        sepVert3.setOrientation(Orientation.VERTICAL);
        MenuBar mb = new MenuBar();
        mb.getStyleClass().add("mbMenuBar");

        BooleanProperty bp1 = cb1.selectedProperty();
        BooleanProperty bp2 = cb2.selectedProperty();
        BooleanProperty bp3 = cb3.selectedProperty();
        BooleanProperty bp4 = cb4.selectedProperty();
        BooleanProperty bp5 = cb5.selectedProperty();
        BooleanProperty bp6 = cb6.selectedProperty();

        Bindings.bindBidirectional(bp1, skyCanvas.enableAsterismDrawingProperty());
        Bindings.bindBidirectional(bp2, skyCanvas.enableStarsDrawingProperty());
        Bindings.bindBidirectional(bp3, skyCanvas.enableSunDrawingProperty());
        Bindings.bindBidirectional(bp4, skyCanvas.enableMoonDrawingProperty());
        Bindings.bindBidirectional(bp5, skyCanvas.enablePlanetsDrawingProperty());
        Bindings.bindBidirectional(bp6, skyCanvas.enableHorizonDrawingProperty());

        cm.setOnShowing(e->{
            mb.setStyle("-fx-background-color: #0096C9;");
        });
        cm.setOnHiding(e-> mb.setStyle(""));
        mb.getMenus().add(cm);


        /*
         END OF IT
        */






        Separator sepVert = new Separator();
        Separator sepVert2 = new Separator();

        sepVert.setOrientation(Orientation.VERTICAL);
        sepVert2.setOrientation(Orientation.VERTICAL);

        HBox child1 = observerPos(skyCanvas.observerLonDegProperty(), skyCanvas.observerLatDegProperty());
        HBox child2 = observInstant(skyCanvas);
        HBox child3 = timeManager(skyCanvas, timeAnimator);

        mainControlBar.getChildren().addAll(child1, sepVert, child2, sepVert2, child3, sepVert3, mb);

        return mainControlBar;
    }

    private HBox observerPos(DoubleProperty observerLonDegProperty, DoubleProperty observerLatDegProperty) {
        HBox observerPosBox = new HBox();
        observerPosBox.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");

        Label longiLabel = new Label("Longitude (°) :");
        Label latiLabel = new Label("Latitude (°) :");

        TextField inpLongi = new TextField();
        TextFormatter<Number> formatterLon = formatter("lon");
        inpLongi.setTextFormatter(formatterLon);
        Bindings.bindBidirectional(formatterLon.valueProperty(), observerLonDegProperty);

        TextField inpLati = new TextField();
        TextFormatter<Number> formatterLat = formatter("lat");
        inpLati.setTextFormatter(formatterLat);
        Bindings.bindBidirectional(formatterLat.valueProperty(), observerLatDegProperty);

        inpLongi.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");
        inpLati.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");

        observerPosBox.getChildren().setAll(longiLabel, inpLongi, latiLabel, inpLati);

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

        datePicker = new DatePicker();
        datePicker.setStyle("-fx-pref-width: 120;");

        Bindings.bindBidirectional(datePicker.valueProperty(), skyCanvas.dateProperty());

        timeSelector = new TextField();
        timeSelector.setTextFormatter(timeFormatter);

        Bindings.bindBidirectional(timeFormatter.valueProperty(), skyCanvas.timeProperty());

        timeSelector.setStyle("-fx-pref-width: 75; -fx-alignment: baseline-right;");
        zoneSelector = new ComboBox<>();

        List<ZoneId> zoneIdList = new ArrayList<>();
        for(String s : ZoneId.getAvailableZoneIds()){
            zoneIdList.add(ZoneId.of(s));
        }

        zoneSelector.setItems(FXCollections.observableList(zoneIdList).sorted());

        Bindings.bindBidirectional(zoneSelector.valueProperty(), skyCanvas.zoneProperty());

        zoneSelector.setStyle("-fx-pref-width: 180;");
        observInstantBox.getChildren().addAll(dateLabel, datePicker, timeLabel, timeSelector, zoneSelector);

        return observInstantBox;
    }

    private HBox timeManager(SkyCanvasManager skyCanvas, TimeAnimator timeAnimator) throws IOException {

        InputStream fontStream = getClass()
                .getResourceAsStream(FILE_NAME_FONT);
        Font fontAwesome = Font.loadFont(fontStream, FONT_SIZE);

        Button resetButton = new Button("\uf0e2");
        resetButton.setFont(fontAwesome);
        Button playPauseButton = new Button("\uf04b"); // "\uf04c" change play to pause
        playPauseButton.setFont(fontAwesome);

        fontStream.close();

        HBox timeManagerbox = new HBox();
        timeManagerbox.setStyle("-fx-spacing: inherit;");

        ChoiceBox<NamedTimeAccelerator> acceleratorSelector = new ChoiceBox<>();
        acceleratorSelector.setItems(FXCollections.observableList(List.of(NamedTimeAccelerator.values())));
        acceleratorSelector.setValue(NamedTimeAccelerator.TIMES_300);
        timeAnimator.acceleratorProperty().bind(Bindings.select(acceleratorSelector.valueProperty(),"Accelerator"));

        playPauseButton.setOnAction(e -> {
            if(timeAnimator.isRunning()){
                timeAnimator.stop();
                playPauseButton.setText("\uf04b");

                //unblock
                resetButton.setDisable(false);
                acceleratorSelector.setDisable(false);
                datePicker.setDisable(false);
                timeSelector.setDisable(false);
                zoneSelector.setDisable(false);
            } else {
                timeAnimator.start();
                playPauseButton.setText("\uf04c");

                //block
                resetButton.setDisable(true);
                acceleratorSelector.setDisable(true);
                datePicker.setDisable(true);
                timeSelector.setDisable(true);
                zoneSelector.setDisable(true);
            }
        });

        resetButton.setOnAction(e -> {
            ZonedDateTime currentTime = ZonedDateTime.now();
            skyCanvas.setTime(currentTime.toLocalTime());
            skyCanvas.setDate(currentTime.toLocalDate());
            skyCanvas.setZone(currentTime.getZone());
        });

        timeManagerbox.getChildren().addAll(acceleratorSelector, resetButton, playPauseButton);

        return timeManagerbox;
    }

    private BorderPane informationBar(SkyCanvasManager skyCanvas){
        BorderPane informationPane = new BorderPane();
        informationPane.setStyle("-fx-padding: 4; -fx-background-color: white;");

        Text leftText = new Text(String.format("Champ de vue : %.1f°", skyCanvas.getFieldOfViewDeg()));
        Text centerText = new Text();
        Text rightText = new Text(String.format("Azimut : %.2f°, hauteur : %.2f°", skyCanvas.getMouseAzDeg(), skyCanvas.getMouseAltDeg()));

        skyCanvas.objectUnderMouseProperty().addListener((p, o, n) -> {
            if (n != null){
                centerText.setText(n.info());
            } else {
                centerText.setText("");
            }
        });

        sb = Bindings.createStringBinding(() ->
                String.format("Azimut : %.2f°, hauteur : %.2f°",
                        skyCanvas.getMouseAzDeg(),
                        skyCanvas.getMouseAltDeg()),
                skyCanvas.mouseAltDegProperty(),
                skyCanvas.mouseAzDegProperty());
        sb.addListener((p, o, n) -> rightText.setText(sb.getValue()));
        skyCanvas.fieldOfViewDegProperty().addListener((p, o, n) ->
                leftText.setText(String.format("Champ de vue : %.1f°",skyCanvas.getFieldOfViewDeg())));

        informationPane.setLeft(leftText);
        informationPane.setCenter(centerText);
        informationPane.setRight(rightText);

        return informationPane;
    }

    private TextFormatter<Number> formatter(String type){
        NumberStringConverter stringConverter =
                new NumberStringConverter("#0.00");

        UnaryOperator<TextFormatter.Change> lonLatFilter = (change -> {
            try {
                String newText =
                        change.getControlNewText();
                double newLonLatDeg =
                        stringConverter.fromString(newText).doubleValue();
                if(type.equals("lon")){
                    return GeographicCoordinates.isValidLonDeg(newLonLatDeg)
                            ? change
                            : null;
                } else if(type.equals("lat")){
                    return GeographicCoordinates.isValidLatDeg(newLonLatDeg)
                            ? change
                            : null;
                } else {
                    return  null;
                }
            } catch (Exception e) {
                return null;
            }
        });

        return new TextFormatter<>(stringConverter, 0, lonLatFilter);
    }
}
