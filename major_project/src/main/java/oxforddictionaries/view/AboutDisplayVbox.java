package oxforddictionaries.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Displays information about the application
 */
public class AboutDisplayVbox {

    /**
     * Creates the vbox which displays the application name, developer name and references
     * @return vbox
     */
    public VBox create() {
        Label titleLbl = new Label("About");
        titleLbl.setWrapText(true);
        titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        VBox appNameVbox = createAppName();
        VBox nameVbox = createName();
        VBox referencesVbox = createReferences();

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(titleLbl, appNameVbox, nameVbox, referencesVbox);
        return vbox;
    }

    /**
     * Creates the application name vbox
     * @return vbox
     */
    public VBox createAppName() {
        Label appNameHeadingLbl = new Label("Application Name");
        appNameHeadingLbl.setWrapText(true);
        appNameHeadingLbl.setFont(Font.font("Verdana", FontPosture.ITALIC, 14));

        Label appNameLbl = new Label("  Oxford Dictionaries GUI");
        appNameLbl.setWrapText(true);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(appNameHeadingLbl, appNameLbl);
        return vbox;
    }

    /**
     * Creates the developer name vbox
     * @return vbox
     */
    public VBox createName() {
        Label nameHeadingLbl = new Label("Developer Name");
        nameHeadingLbl.setWrapText(true);
        nameHeadingLbl.setFont(Font.font("Verdana", FontPosture.ITALIC, 14));

        Label nameLbl = new Label("  Raymond T");
        nameLbl.setWrapText(true);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(nameHeadingLbl, nameLbl);
        return vbox;
    }

    /**
     * Creates the references vbox
     * @return vbox
     */
    public VBox createReferences() {
        Label referencesHeadingLbl = new Label("References");
        referencesHeadingLbl.setWrapText(true);
        referencesHeadingLbl.setFont(Font.font("Verdana", FontPosture.ITALIC, 14));

        Label oxfordDictLbl = new Label("  \u2022 Input engine: Oxford Dictionaries API");
        oxfordDictLbl.setWrapText(true);

        Label pastebinLbl = new Label("  \u2022 Output engine: Pastebin API");
        pastebinLbl.setWrapText(true);

        Label themeSongLbl = new Label("  \u2022 Theme song: dreamy night - LilyPichu");
        themeSongLbl.setWrapText(true);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(referencesHeadingLbl, oxfordDictLbl, pastebinLbl, themeSongLbl);
        return vbox;
    }
}
