package oxforddictionaries.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import oxforddictionaries.model.InputEngine;

import java.util.List;

/**
 * This is the display pane for the pronunciation list
 */
public class PronunciationListVbox {
    private InputEngine inputEngine;

    /**
     * Creates the pronunciation list vbox
     * @param inputEngine Input Engine
     */
    public PronunciationListVbox(InputEngine inputEngine) {
        this.inputEngine = inputEngine;
    }

    /**
     * Creates the grid pane to list the pronunciations.
     * @param pronunciations pronunciation list
     * @return vbox
     */
    public VBox create(List<List<String>> pronunciations) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label titleLbl = new Label("Pronunciations List");
        titleLbl.setWrapText(true);
        titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        for (int i = 0; i < pronunciations.size(); i++) {
            gridPane = createPlayer(gridPane, i, pronunciations.get(i));
        }

        VBox vbox = new VBox(5);
        vbox.getChildren().addAll(titleLbl, gridPane);
        return vbox;
    }

    /**
     * Creates the individual row for the grid pane. Creates a button to play the pronunciation and
     * a button to remove it from the list. If it fails to remove, display a message.
     * @param gridPane javafx grid pane
     * @param id row number
     * @param pronunciation pronunciation
     * @return javafx grid pane
     */
    public GridPane createPlayer(GridPane gridPane, int id, List<String> pronunciation) {
        Label idLbl = new Label(pronunciation.get(0));
        idLbl.setWrapText(true);

        Media proMedia = new Media(pronunciation.get(1));
        MediaPlayer proPlayer = new MediaPlayer(proMedia);
        proPlayer.setOnEndOfMedia(() -> {
            proPlayer.stop();
        });

        Button playBtn = new Button("Play");
        playBtn.setOnAction((event) -> {
            proPlayer.play();
        });

        Button removeBtn = new Button("Remove");
        removeBtn.setOnAction((event) -> {
            removeBtn.setDisable(true);
            boolean removed = inputEngine.removePronunciation(pronunciation.get(1));
            if (!removed) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error has occurred!");
                alert.setContentText("Pronunciation doesn't exist in the list.");
                alert.showAndWait();
            }
        });

        gridPane.add(idLbl, 0, id);
        gridPane.add(playBtn, 1, id);
        gridPane.add(removeBtn, 2, id);

        return gridPane;
    }
}
