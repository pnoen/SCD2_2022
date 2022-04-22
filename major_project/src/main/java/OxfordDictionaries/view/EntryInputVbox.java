package OxfordDictionaries.view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class EntryInputVbox {
    private VBox vbox;
    private ComboBox<String> langsMenu;
    private TextField wordTxt;
    private ComboBox<String> fieldMenu;
    private TextField gramFeatTxt;
    private TextField lexiCateTxt;
    private TextField domainsTxt;
    private TextField registersTxt;
    private ComboBox<String> matchMenu;
    private Button searchBtn;

    public VBox create() {
        Label titleLbl = new Label("Search for an entry");
        titleLbl.setWrapText(true);
        titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        HBox langHbox = createLangHbox();
        HBox wordHbox = createWordHbox();
        HBox fieldHbox = createFieldHBox();
        HBox gramFeatHbox = createGramFeatHbox();
        HBox lexiCateHbox = createLexiCateHbox();
        HBox domainsHbox = createDomainsHbox();
        HBox registersHbox = createRegistersHbox();
        HBox matchHbox = createMatchHbox();

        this.searchBtn = new Button("Search");

        this.vbox = new VBox(5);
        vbox.getChildren().addAll(titleLbl, langHbox, wordHbox, fieldHbox, gramFeatHbox,
                lexiCateHbox, domainsHbox, registersHbox, matchHbox, searchBtn);

        return vbox;
    }

    public HBox createLangHbox() {
        Label langLbl = new Label("Language: ");
        langLbl.setWrapText(true);
        List<String> langs = new ArrayList<>();
        langs.add("en-gb");
        langs.add("en-us");
        this.langsMenu = new ComboBox<>();
        langsMenu.getItems().addAll(langs);
        langsMenu.getSelectionModel().selectFirst();
        HBox langHbox = new HBox(langLbl, langsMenu);
        return langHbox;
    }

    public HBox createWordHbox() {
        Label wordLbl = new Label("Word: ");
        wordLbl.setWrapText(true);
        this.wordTxt = new TextField();
        HBox wordHbox = new HBox(wordLbl, wordTxt);
        return wordHbox;
    }

    public HBox createFieldHBox() {
        Label fieldLbl = new Label("Field: ");
        fieldLbl.setWrapText(true);
        List<String> fields = new ArrayList<>();
        fields.add("");
        fields.add("definitions");
        fields.add("domains");
        fields.add("etymologies");
        fields.add("examples");
        fields.add("pronunciations");
        fields.add("regions");
        fields.add("registers");
        fields.add("variantForms");
        this.fieldMenu = new ComboBox<>();
        fieldMenu.getItems().addAll(fields);
        fieldMenu.getSelectionModel().selectFirst();
        HBox fieldHbox = new HBox(fieldLbl, fieldMenu);
        return fieldHbox;
    }

    public HBox createGramFeatHbox() {
        Label gramFeatLbl = new Label("Grammatical Feature: ");
        gramFeatLbl.setWrapText(true);
        this.gramFeatTxt = new TextField();
        HBox gramFeatHbox = new HBox(gramFeatLbl, gramFeatTxt);
        return gramFeatHbox;
    }

    public HBox createLexiCateHbox() {
        Label lexiCateLbl = new Label("Lexical Category: ");
        lexiCateLbl.setWrapText(true);
        this.lexiCateTxt = new TextField();
        HBox lexiCateHbox = new HBox(lexiCateLbl, lexiCateTxt);
        return lexiCateHbox;
    }

    public HBox createDomainsHbox() {
        Label domainsLbl = new Label("Domains: ");
        domainsLbl.setWrapText(true);
        this.domainsTxt = new TextField();
        HBox domainsHbox = new HBox(domainsLbl, domainsTxt);
        return domainsHbox;
    }

    public HBox createRegistersHbox() {
        Label registersLbl = new Label("Registers: ");
        registersLbl.setWrapText(true);
        this.registersTxt = new TextField();
        HBox registersHbox = new HBox(registersLbl, registersTxt);
        return registersHbox;
    }

    public HBox createMatchHbox() {
        Label matchLbl = new Label("Strict Match: ");
        matchLbl.setWrapText(true);
        List<String> matches = new ArrayList<>();
        matches.add("");
        matches.add("false");
        matches.add("true");
        this.matchMenu = new ComboBox<>();
        matchMenu.getItems().addAll(matches);
        matchMenu.getSelectionModel().selectFirst();
        HBox matchHbox = new HBox(matchLbl, matchMenu);
        return matchHbox;
    }

    public String getLang() {
        return langsMenu.getValue();
    }

    public String getWord() {
        if (wordTxt.getText().trim().equals("")) {
            return null;
        }
        return wordTxt.getText();
    }

    public String getField() {
        return fieldMenu.getValue();
    }

    public String getGramFeat() {
        return gramFeatTxt.getText();
    }

    public String getLexiCate() {
        return lexiCateTxt.getText();
    }

    public String getDomains() {
        return domainsTxt.getText();
    }

    public String getRegisters() {
        return registersTxt.getText();
    }

    public String getMatch() {
        return matchMenu.getValue();
    }

    public Button getSearchBtn() {
        return searchBtn;
    }
}
