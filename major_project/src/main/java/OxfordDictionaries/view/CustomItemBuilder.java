package oxfordDictionaries.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CustomItemBuilder {
    private CustomItem customItem;

    public CustomItemBuilder() {
        newItem();
    }

    public void newItem() {
        this.customItem = new CustomItem();
    }

    public void setLabel(Label label) {
        customItem.setLabel(label);
    }

    public void setBtn(Button btn) {
        customItem.setBtn(btn);
    }

    public CustomItem getCustomItem() {
        return customItem;
    }




}
