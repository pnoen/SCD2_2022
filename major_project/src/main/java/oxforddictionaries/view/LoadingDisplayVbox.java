package oxforddictionaries.view;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;

/**
 * Displays the spinning loading gif
 */
public class LoadingDisplayVbox {

    /**
     *
     * @return vbox
     */
    public VBox start() {
        String[] path = {"src", "main", "resources", "loading.gif"};
        String filename = String.join(File.separator, path);
        File file = new File(filename);
        String fileUri = file.toURI().toString();
        Image img = new Image(fileUri);
        ImageView imgView = new ImageView();
        imgView.setImage(img);
        VBox vbox = new VBox(imgView);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }
}
