package oxforddictionaries.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.List;

/**
 * Displays information about the application
 */
public class AboutDisplayVbox {

    /**
     * Creates the vbox which displays the application name, developer name and references
     * @param appName application name
     * @param devName developer name
     * @param references references
     * @return vbox
     */
    public VBox create(String appName, String devName, List<String> references) {
        Label titleLbl = new Label("About");
        titleLbl.setWrapText(true);
        titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        VBox appNameVbox = createAppName(appName);
        VBox nameVbox = createName(devName);
        VBox referencesVbox = createReferences(references);

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(titleLbl, appNameVbox, nameVbox, referencesVbox);
        return vbox;
    }

    /**
     * Creates the application name vbox
     * @param appName application name
     * @return vbox
     */
    public VBox createAppName(String appName) {
        Label appNameHeadingLbl = new Label("Application Name");
        appNameHeadingLbl.setWrapText(true);
        appNameHeadingLbl.setFont(Font.font("Verdana", FontPosture.ITALIC, 14));

        Label appNameLbl = new Label("  " + appName);
        appNameLbl.setWrapText(true);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(appNameHeadingLbl, appNameLbl);
        return vbox;
    }

    /**
     * Creates the developer name vbox
     * @param devName developer name
     * @return vbox
     */
    public VBox createName(String devName) {
        Label nameHeadingLbl = new Label("Developer Name");
        nameHeadingLbl.setWrapText(true);
        nameHeadingLbl.setFont(Font.font("Verdana", FontPosture.ITALIC, 14));

        Label nameLbl = new Label("  " + devName);
        nameLbl.setWrapText(true);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(nameHeadingLbl, nameLbl);
        return vbox;
    }

    /**
     * Creates the references vbox
     * @param references references
     * @return vbox
     */
    public VBox createReferences(List<String> references) {
        Label referencesHeadingLbl = new Label("References");
        referencesHeadingLbl.setWrapText(true);
        referencesHeadingLbl.setFont(Font.font("Verdana", FontPosture.ITALIC, 14));

        Label referLbl = new Label("  Refer to the README for links to the sites and song.");
        referLbl.setWrapText(true);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(referencesHeadingLbl, referLbl);

        for (String reference : references) {
            Label referenceLbl = new Label("  \u2022 " + reference);
            referenceLbl.setWrapText(true);
            vbox.getChildren().add(referenceLbl);
        }
        return vbox;
    }
}
