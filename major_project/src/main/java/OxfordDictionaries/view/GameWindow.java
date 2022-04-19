package OxfordDictionaries.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import OxfordDictionaries.model.InputEngine;
import OxfordDictionaries.model.OutputEngine;
import javafx.scene.Scene;

public class GameWindow {
    private final int width;
    private final int height;
    private InputEngine inputEngine;
    private OutputEngine outputEngine;
    private Scene scene;
    private BorderPane borderPane;
    private HBox bottomHbox;
    private VBox centerVbox;
    private VBox leftVbox;
    private VBox contentVbox;
    private EntryInputVbox entryInputVbox;

    public GameWindow(int width, int height, InputEngine inputEngine, OutputEngine outputEngine) {
        this.width = width;
        this.height = height;
        this.inputEngine = inputEngine;
        this.outputEngine = outputEngine;

        this.borderPane = new BorderPane();
        this.scene = new Scene(borderPane, width, height);

        this.bottomHbox = new HBox();
        this.borderPane.setBottom(this.bottomHbox);

        this.centerVbox = new VBox();
        this.borderPane.setCenter(this.centerVbox);

        this.leftVbox = new VBox();
        this.borderPane.setLeft(this.leftVbox);
    }

    public Scene getScene() {
        return scene;
    }

    public void draw() {
        setupBorderPane();
        sidebarBtns();
        entry();
    }

    public void setupBorderPane() {
        this.borderPane.setStyle("-fx-background-color: #d0d0d0;");

        Insets insets = new Insets(5);
        this.centerVbox.setPadding(insets);

        this.leftVbox.setPadding(insets);
        this.leftVbox.setMinWidth(130);
        this.leftVbox.setAlignment(Pos.CENTER_LEFT);

        this.bottomHbox.setPadding(insets);
        this.bottomHbox.setMinHeight(40);
        this.bottomHbox.setAlignment(Pos.CENTER_LEFT);
    }

    public void sidebarBtns() {
        Button entryBtn = new Button("Home");
        entryBtn.setOnAction((event -> {

        }));
        this.leftVbox.getChildren().add(entryBtn);
    }

    public void entry() {
        this.contentVbox = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this.contentVbox);
        scrollPane.setMinHeight(100);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #d0d0d0; -fx-background-color:transparent;");

        Pane spacer = new Pane();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(1, 10);

        this.entryInputVbox = new EntryInputVbox();
        VBox entryVbox = entryInputVbox.create();

        ScrollPane entryScrollPane = new ScrollPane();
        entryScrollPane.setContent(entryVbox);
        entryScrollPane.setFitToWidth(true);
        entryScrollPane.setStyle("-fx-background: #d0d0d0; -fx-background-color:transparent;");

        Button searchBtn = new Button("Search");
        entryVbox.getChildren().add(searchBtn);
        searchBtn.setOnAction((event) -> {

        });

        this.centerVbox.getChildren().addAll(scrollPane, spacer, entryScrollPane);
    }
}
