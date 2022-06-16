package oxforddictionaries.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Builds the custom item
 */
public class CustomItemBuilder {
    private CustomItem customItem;

    /**
     * Creates the builder and initialises a custom item
     */
    public CustomItemBuilder() {
        newItem();
    }

    /**
     * Initialises a new custom item
     */
    public void newItem() {
        this.customItem = new CustomItem();
    }

    /**
     * Sets the label of the item
     * @param label javafx label
     */
    public void setLabel(Label label) {
        customItem.setLabel(label);
    }

    /**
     * Sets the pronunciation button of the item
     * @param proBtn javafx button
     */
    public void setProBtn(Button proBtn) {
        customItem.setProBtn(proBtn);
    }

    /**
     * Gets the item that it is building
     * @return custom item
     */
    public CustomItem getCustomItem() {
        return customItem;
    }

    /**
     * Sets the add to list button of the item
     * @param addBtn javafx button
     */
    public void setAddBtn(Button addBtn) {
        customItem.setAddBtn(addBtn);
    }




}
