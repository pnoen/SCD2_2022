package oxforddictionaries.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

/**
 * This is the display pane for the history
 */
public class HistoryDisplayVbox {
    private VBox vbox;
    private Button goBtn;
    private ToggleGroup historyRadioBtns;

    /**
     * Displays the history and allows the user to select an entry to revisit.
     * @param history list of history
     * @return vbox
     */
    public VBox create(List<List<String>> history) {
        this.vbox = new VBox(5);

        Label titleLbl = new Label("History");
        titleLbl.setWrapText(true);
        titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        vbox.getChildren().add(titleLbl);

        this.historyRadioBtns = new ToggleGroup();
        for (List<String> entry : history) {
            RadioButton radioBtn = new RadioButton(entry.get(1) + " - " + entry.get(8));
            radioBtn.setToggleGroup(historyRadioBtns);
            vbox.getChildren().add(radioBtn);
        }

        this.goBtn = new Button("Go to");
        vbox.getChildren().add(goBtn);

        return vbox;
    }

    /**
     * @return revisit button
     */
    public Button getGoBtn() {
        return goBtn;
    }

    /**
     * Gets the index of the selected entry on the history page. If no entry has been selected, return -1.
     * @return index of selected entry
     */
    public int getSelectedBtnVal() {
        if (historyRadioBtns.getSelectedToggle() == null) {
            return -1;
        }
        return historyRadioBtns.getToggles().indexOf(historyRadioBtns.getSelectedToggle());
    }
}
