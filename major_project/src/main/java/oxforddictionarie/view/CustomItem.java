package oxforddictionarie.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CustomItem extends HBox {
    private Label label;
    private Button btn;

    public CustomItem() {
        super();
    }

    public void setLabel(Label label) {
        this.label = label;
        this.getChildren().add(label);
    }

    public void setBtn(Button btn) {
        this.btn = btn;
        this.getChildren().add(btn);
    }
}
