package oxforddictionaries.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * HBox that holds a label and btn.
 */
public class CustomItem extends HBox {
    private Label label;
    private Button proBtn;
    private Button addBtn;

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
     * Set the pronunciation button of the item
     * @param proBtn javafx button
     */
    public void setProBtn(Button proBtn) {
        this.proBtn = proBtn;
        this.getChildren().add(proBtn);
    }

    /**
     * Set the add to list button of the item
     * @param addBtn javafx button
     */
    public void setAddBtn(Button addBtn) {
        this.addBtn = addBtn;
        this.getChildren().add(addBtn);
    }
}
