package oxforddictionaries.view;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the dialog window for send report
 */
public class ReportDialog {
    private Dialog<String> dialog;
    private ComboBox<String> privateMenu;
    private TextField nameTxt;
    private TextField userKeyTxt;
    private ComboBox<String> expireMenu;
    private TextField folderTxt;
    private Node sendBtn;
    private GridPane gridPane;

    /**
     * Creates the window and fields for the user to enter privacy, name, user key, expiration length and folder key
     * @return dialog
     */
    public Dialog<String> create() {
        this.gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        this.dialog = new Dialog<>();
        dialog.setTitle("Send Report");
        dialog.setHeaderText("Create a Pastebin post");

        ButtonType sendBtnType = new ButtonType("Send", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(sendBtnType);

        createPrivate();
        createName();
        createUserKey();
        createExpire();
        createFolder();

        this.sendBtn = dialog.getDialogPane().lookupButton(sendBtnType);

        VBox vbox = new VBox(5);
        vbox.getChildren().addAll(gridPane);

        dialog.getDialogPane().setContent(vbox);

        return dialog;
    }

    /**
     * Creates the dropdown for privacy
     */
    public void createPrivate() {
        Label privateLbl = new Label("Paste privacy: ");
        privateLbl.setWrapText(true);

        List<String> privateVals = new ArrayList<>();
        privateVals.add("public");
        privateVals.add("unlisted");
        privateVals.add("private");
        this.privateMenu = new ComboBox<>();
        privateMenu.getItems().addAll(privateVals);
        privateMenu.getSelectionModel().selectFirst();

        gridPane.add(privateLbl, 0, 0);
        gridPane.add(privateMenu, 1, 0);
    }

    /**
     * Creates the text field for post name
     */
    public void createName() {
        Label nameLbl = new Label("Paste name: (Opt.) ");
        nameLbl.setWrapText(true);

        this.nameTxt = new TextField();

        gridPane.add(nameLbl, 0, 1);
        gridPane.add(nameTxt, 1, 1);
    }

    /**
     * Creates the text field for the user key
     */
    public void createUserKey() {
        Label userKeyLbl = new Label("User key: (Opt.) ");
        userKeyLbl.setWrapText(true);

        this.userKeyTxt = new TextField();

        gridPane.add(userKeyLbl, 0, 2);
        gridPane.add(userKeyTxt, 1, 2);
    }

    /**
     * Creates the dropdown for the expiration length
     */
    public void createExpire() {
        Label expireLbl = new Label("Expiration date: ");
        expireLbl.setWrapText(true);

        List<String> expireVals = new ArrayList<>();
        expireVals.add("N");
        expireVals.add("10M");
        expireVals.add("1H");
        expireVals.add("1D");
        expireVals.add("1W");
        expireVals.add("2W");
        expireVals.add("1M");
        expireVals.add("6M");
        expireVals.add("1Y");
        this.expireMenu = new ComboBox<>();
        expireMenu.getItems().addAll(expireVals);
        expireMenu.getSelectionModel().selectFirst();

        gridPane.add(expireLbl, 0, 3);
        gridPane.add(expireMenu, 1, 3);
    }

    /**
     * Creates the text field for the folder key
     */
    public void createFolder() {
        Label folderLbl = new Label("Folder key: (Opt.) ");
        folderLbl.setWrapText(true);

        this.folderTxt = new TextField();

        gridPane.add(folderLbl, 0, 4);
        gridPane.add(folderTxt, 1, 4);
    }

    /**
     * Gets the index of the inputted privacy
     * @return index
     */
    public int getPrivateVal() {
        return privateMenu.getItems().indexOf(privateMenu.getValue());
    }

    /**
     * Gets the post name from the text field. If it is empty, return null
     * @return post name
     */
    public String getNameVal() {
        if (nameTxt.getText().trim().isEmpty()) {
            return null;
        }
        return nameTxt.getText();
    }

    /**
     * Gets the user key from the text field. If it is empty, return null
     * @return user key
     */
    public String getUserKeyVal() {
        if (userKeyTxt.getText().trim().isEmpty()) {
            return null;
        }
        return userKeyTxt.getText();
    }

    /**
     * Gets the expiration length from the dropdown
     * @return expiration length
     */
    public String getExpireVal() {
        return expireMenu.getValue();
    }

    /**
     * Gets the folder key from the text field. If it is empty, return null
     * @return folder key
     */
    public String getFolderVal() {
        if (folderTxt.getText().trim().isEmpty()) {
            return null;
        }
        return folderTxt.getText();
    }

    /**
     * @return send button
     */
    public Node getSendBtn() {
        return sendBtn;
    }

}
