package OxfordDictionaries.view;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ReportDialog {
    private Dialog<String> dialog;
    private ComboBox<String> privateMenu;
    private TextField nameTxt;
    private TextField userKeyTxt;
    private ComboBox<String> expireMenu;
    private TextField folderTxt;
    private Node sendBtn;

    public Dialog<String> create() {
        this.dialog = new Dialog<>();
        dialog.setTitle("Send Report");
        dialog.setHeaderText("Create a Pastebin post");

        ButtonType sendBtnType = new ButtonType("Send", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(sendBtnType);

        HBox privateHbox = createPrivate();
        HBox nameHbox = createName();
        HBox userKeyHbox = createUserKey();
        HBox expireHbox = createExpire();
        HBox folderHbox = createFolder();

        this.sendBtn = dialog.getDialogPane().lookupButton(sendBtnType);

        VBox vbox = new VBox(5);
        vbox.getChildren().addAll(privateHbox, nameHbox, userKeyHbox, expireHbox, folderHbox);

        dialog.getDialogPane().setContent(vbox);

        return dialog;
    }

    public HBox createPrivate() {
        Label privateLbl = new Label("Paste privacy: ");
        privateLbl.setWrapText(true);

        List<String> privateVals = new ArrayList<>();
        privateVals.add("public");
        privateVals.add("unlisted");
        privateVals.add("private");
        this.privateMenu = new ComboBox<>();
        privateMenu.getItems().addAll(privateVals);
        privateMenu.getSelectionModel().selectFirst();

        return new HBox(privateLbl, privateMenu);
    }

    public HBox createName() {
        Label nameLbl = new Label("Paste name: (Opt.) ");
        nameLbl.setWrapText(true);

        this.nameTxt = new TextField();

        return new HBox(nameLbl, nameTxt);
    }

    public HBox createUserKey() {
        Label userKeyLbl = new Label("User key: (Opt.) ");
        userKeyLbl.setWrapText(true);

        this.userKeyTxt = new TextField();

        return new HBox(userKeyLbl, userKeyTxt);
    }

    public HBox createExpire() {
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

        return new HBox(expireLbl, expireMenu);
    }

    public HBox createFolder() {
        Label folderLbl = new Label("Folder key: (Opt.) ");
        folderLbl.setWrapText(true);

        this.folderTxt = new TextField();

        return new HBox(folderLbl, folderTxt);
    }

    public int getPrivateVal() {
        return privateMenu.getItems().indexOf(privateMenu.getValue());
    }

    public String getNameVal() {
        if (nameTxt.getText().trim().isEmpty()) {
            return null;
        }
        return nameTxt.getText();
    }

    public String getUserKeyVal() {
        if (userKeyTxt.getText().trim().isEmpty()) {
            return null;
        }
        return userKeyTxt.getText();
    }

    public String getExpireVal() {
        return expireMenu.getValue();
    }

    public String getFolderVal() {
        if (folderTxt.getText().trim().isEmpty()) {
            return null;
        }
        return folderTxt.getText();
    }

    public Node getSendBtn() {
        return sendBtn;
    }

}
