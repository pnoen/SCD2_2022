package oxforddictionaries.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * HBox that holds a label and btn.
 */
public class CustomItem extends HBox {
    private Label label;
    private Button btn;

    /**
     * Creates the custom item and inherits the super class
     */
    public CustomItem() {
        super();
    }

    /**
     * Set the label of the item
     * @param label javafx label
     */
    public void setLabel(Label label) {
        this.label = label;
        this.getChildren().add(label);
    }

    /**
     * Set the button of the item
     * @param btn javafx button
     */
    public void setBtn(Button btn) {
        this.btn = btn;
        this.getChildren().add(btn);
    }
}
