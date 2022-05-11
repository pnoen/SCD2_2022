package oxforddictionaries.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Displays a window which allows the user to select which data to use.
 */
public class CacheConfirmation {
    private Alert alert;
    private ButtonType yesBtnType;
    private ButtonType noBtnType;

    /**
     * Presents the user with the option to select whether to use the cached data or not.
     * @param entry is entry
     * @return alert box
     */
    public Alert create(boolean entry) {
        this.alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        String header = "found in cache";
        if (entry) {
            header = "Entry " + header;
        }
        else {
            header = "Lemma " + header;
        }
        alert.setHeaderText(header);
        alert.setContentText("Would you like to use the cache data?");

        this.yesBtnType = new ButtonType("Yes");
        this.noBtnType = new ButtonType("No");

        alert.getButtonTypes().setAll(yesBtnType, noBtnType);

        return alert;
    }

    /**
     * @return yes button
     */
    public ButtonType getYesBtnType() {
        return yesBtnType;
    }

    /**
     * @return no button
     */
    public ButtonType getNoBtnType() {
        return noBtnType;
    }
}
