package OxfordDictionaries.view;

import OxfordDictionaries.model.request.responseClasses.HeadwordEntry;
import OxfordDictionaries.model.request.responseClasses.RetrieveEntry;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import OxfordDictionaries.model.InputEngine;
import OxfordDictionaries.model.OutputEngine;
import javafx.scene.Scene;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.List;

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
    private ScrollPane contentScrollPane;
    private EntryInputVbox entryInputVbox;
    private ContentDisplayVbox contentDisplayVbox;

    public GameWindow(int width, int height, InputEngine inputEngine, OutputEngine outputEngine) {
        this.width = width;
        this.height = height;
        this.inputEngine = inputEngine;
        this.outputEngine = outputEngine;

        this.borderPane = new BorderPane();
        this.scene = new Scene(borderPane, width, height);

        this.bottomHbox = new HBox();
        this.borderPane.setBottom(this.bottomHbox);

        this.contentScrollPane = new ScrollPane();
        this.centerVbox = new VBox(this.contentScrollPane);
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

        this.entryInputVbox = new EntryInputVbox();
        this.contentDisplayVbox = new ContentDisplayVbox();
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

        this.contentScrollPane.setMinHeight(100);
        this.contentScrollPane.setFitToWidth(true);
        this.contentScrollPane.setStyle("-fx-background: #d0d0d0; -fx-background-color:transparent;");
    }

    public void sidebarBtns() {
        double btnWidth = 90.0;

        Button entryBtn = new Button("Home");
        entryBtn.setPrefWidth(btnWidth);
        entryBtn.setOnAction((event -> {
            entry();
        }));

        Button reportBtn = new Button("Create Report");
        reportBtn.setPrefWidth(btnWidth);
        reportBtn.setOnAction((event -> {

        }));

        this.leftVbox.getChildren().addAll(entryBtn, reportBtn);
    }

    public void entry() {
        VBox entryVbox = entryInputVbox.create();
        this.contentScrollPane.setContent(entryVbox);

        Button searchBtn = new Button("Search");
        entryVbox.getChildren().add(searchBtn);
        searchBtn.setOnAction((event) -> {
            if (entryInputVbox.getWord() == null) {
                List<String> error = Arrays.asList("Word was not entered.");
                handleError(error);
                return;
            }
            List<String> error = inputEngine.entrySearch(entryInputVbox.getLang(), entryInputVbox.getWord(), entryInputVbox.getField(),
                    entryInputVbox.getGramFeat(), entryInputVbox.getLexiCate(), entryInputVbox.getDomains(), entryInputVbox.getRegisters(),
                    entryInputVbox.getMatch());
            if (error.size() > 0) {
                handleError(error);
                return;
            }
            RetrieveEntry retrieveEntry = inputEngine.getRetrieveEntry();
//            System.out.println(retrieveEntry.getId());
//            for (HeadwordEntry result : retrieveEntry.getResults()) {
//                System.out.println(result.getWord());
//            }
            VBox contentVbox = contentDisplayVbox.create(retrieveEntry);
            this.contentScrollPane.setContent(contentVbox);
        });

    }

    public void handleError(List<String> error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occurred!");
        String content = String.join("\n", error);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
