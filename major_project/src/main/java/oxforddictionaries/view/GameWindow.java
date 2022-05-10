package oxforddictionaries.view;

import oxforddictionaries.model.request.responseclasses.RetrieveEntry;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import oxforddictionaries.model.InputEngine;
import oxforddictionaries.model.OutputEngine;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main GUI class of the application. Handles what the window will display.
 */
public class GameWindow {
    private final int width;
    private final int height;
    private InputEngine inputEngine;
    private OutputEngine outputEngine;
    private Scene scene;
    private BorderPane borderPane;
    private HBox bottomHbox;
    private VBox centerVbox;
    private VBox leftVbox;
    private ScrollPane contentScrollPane;
    private EntryInputVbox entryInputVbox;
    private EntryDisplayVbox entryDisplayVbox;
    private LemmaDisplayVbox lemmaDisplayVbox;
    private HistoryDisplayVbox historyDisplayVbox;
    private Button reportBtn;
    private ReportDialog reportDialog;

    /**
     * Creates the game window. Creates the border pane and initialises the bottom hbox, left vbox and center scroll pane.
     * @param width window width
     * @param height window height
     * @param inputEngine input engine
     * @param outputEngine output engine
     */
    public GameWindow(int width, int height, InputEngine inputEngine, OutputEngine outputEngine) {
        this.width = width;
        this.height = height;
        this.inputEngine = inputEngine;
        this.outputEngine = outputEngine;

        this.borderPane = new BorderPane();
        this.scene = new Scene(borderPane, width, height);

        this.bottomHbox = new HBox();
        this.borderPane.setBottom(this.bottomHbox);

        this.contentScrollPane = new ScrollPane();
        this.centerVbox = new VBox(this.contentScrollPane);
        this.borderPane.setCenter(this.centerVbox);

        this.leftVbox = new VBox();
        this.borderPane.setLeft(this.leftVbox);
    }

    /**
     * @return scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Setups the border pane, side bar buttons and other displays. The application starts with the entry input display.
     */
    public void draw() {
        setupBorderPane();
        sidebarBtns();

        this.entryInputVbox = new EntryInputVbox();
        this.entryDisplayVbox = new EntryDisplayVbox();
        this.lemmaDisplayVbox = new LemmaDisplayVbox();
        this.historyDisplayVbox = new HistoryDisplayVbox();
        this.reportDialog = new ReportDialog();
        entry();
    }

    /**
     * Sets the width, padding and style of the border pane children
     */
    public void setupBorderPane() {
        this.borderPane.setStyle("-fx-background-color: #d0d0d0;");

        Insets insets = new Insets(5);
        this.centerVbox.setPadding(insets);

        this.leftVbox.setPadding(insets);
        this.leftVbox.setMinWidth(130);
        this.leftVbox.setAlignment(Pos.CENTER_LEFT);

        this.bottomHbox.setPadding(insets);
        this.bottomHbox.setMinHeight(40);
        this.bottomHbox.setAlignment(Pos.CENTER_LEFT);

        this.contentScrollPane.setMinHeight(100);
        this.contentScrollPane.setFitToWidth(true);
        this.contentScrollPane.setStyle("-fx-background: #d0d0d0; -fx-background-color:transparent;");
    }

    /**
     * Creates the sidebar buttons Home, History and Create report
     */
    public void sidebarBtns() {
        double btnWidth = 90.0;

        Button entryBtn = new Button("Home");
        entryBtn.setPrefWidth(btnWidth);
        entryBtn.setOnAction((event -> {
            entry();
        }));

        Button historyBtn = new Button("History");
        historyBtn.setPrefWidth(btnWidth);
        historyBtn.setOnAction((event -> {
            history();
        }));

        this.reportBtn = new Button("Create Report");
        reportBtn.setPrefWidth(btnWidth);
        reportBtn.setOnAction((event -> {
            sendReport();
        }));
        reportBtn.setDisable(true);

        this.leftVbox.getChildren().addAll(entryBtn, historyBtn, reportBtn);
    }

    /**
     * Creates the entry input display and sets it to the scroll pane. The search button is set to request the Oxford Dictionaries Api.
     * If the word field is empty, the application should not proceed.
     */
    public void entry() {
        this.reportBtn.setDisable(true);
        VBox entryVbox = entryInputVbox.create();
        this.contentScrollPane.setContent(entryVbox);

        entryInputVbox.getSearchBtn().setOnAction((event) -> {
            if (entryInputVbox.getWord() == null) {
                List<String> error = Arrays.asList("Word was not entered.");
                handleError(error);
                return;
            }

            displayEntry(entryInputVbox.getLang(), entryInputVbox.getWord(), entryInputVbox.getField(), entryInputVbox.getGramFeat(),
                    entryInputVbox.getLexiCate(), entryInputVbox.getDomains(), entryInputVbox.getRegisters(), entryInputVbox.getMatch(),
                    true, false, false);
        });

    }

    /**
     * Requests the Oxford Dictionaries Api. If the response is null, request the api for the lemma. If the response list size is greater
     * than 0, display the error message.
     * @param lang language
     * @param word word
     * @param field field
     * @param gramFeat grammatical features
     * @param lexiCate lexical categories
     * @param domain domains
     * @param register registers
     * @param match match
     * @param newSearch new search
     * @param historySearch history search
     * @param lemma lemma
     */
    public void displayEntry(String lang, String word, String field, String gramFeat, String lexiCate,
                             String domain, String register, String match, boolean newSearch, boolean historySearch, boolean lemma) {
        List<String> error = inputEngine.entrySearch(lang, word, field, gramFeat, lexiCate, domain, register, match, newSearch, historySearch, lemma);
//        System.out.println(error);
        if (error == null) {
//            System.out.println("No entry");
            lemma(word, gramFeat, lexiCate, newSearch);
            return;
        }
        if (error.size() > 0) {
            handleError(error);
            if (lemma) {
                entry();
            }
            return;
        }
        reportBtn.setDisable(false);
        RetrieveEntry retrieveEntry = inputEngine.getRetrieveEntry();
        VBox contentVbox = entryDisplayVbox.create(retrieveEntry);
        contentScrollPane.setVvalue(0);
        contentScrollPane.setContent(contentVbox);

        List<HBox> synAntHboxes = entryDisplayVbox.getSynAntHboxes();
        for (int i = 0; i < synAntHboxes.size(); i++) {
            int ind = i;
            synAntHboxes.get(ind).setOnMouseClicked((event) -> {
                String text = entryDisplayVbox.getSynAntText(ind);
                displayEntry(lang, text, "", "", "", "", "", "", false, false, false);
            });
        }
    }

    /**
     * Creates an alert box and displays the error messages
     * @param error list of error messages
     */
    public void handleError(List<String> error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occurred!");
        String content = String.join("\n", error);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Requests the api for the lemma of the word. If the response is null, display that there are no lemmas.
     * If the response list size is greater than 0, display the error. Otherwise, find the lemmas in the POJO.
     * The user will select the lemma to pick to search. If there is 1 lemma, then it should search for the entry.
     * @param word word
     * @param gramFeat grammatical features
     * @param lexiCate lexical categories
     * @param newSearch new search
     */
    public void lemma(String word, String gramFeat, String lexiCate, boolean newSearch) {
        List<String> error = inputEngine.lemmaSearch("en", word, gramFeat, lexiCate);
        if (error == null) {
            List<String> errorMsg = Arrays.asList("No lemma was found for the entry.");
            handleError(errorMsg);
            return;
        }
        if (error.size() > 0) {
            handleError(error);
            return;
        }
        List<List<String>> lemmas = inputEngine.findLemmas();
        VBox lemmaVbox = lemmaDisplayVbox.create(lemmas);
        this.contentScrollPane.setContent(lemmaVbox);

        lemmaDisplayVbox.getSelectBtn().setOnAction((event) -> {
            int id = lemmaDisplayVbox.getLemmaId();
            List<String> lemma = lemmaDisplayVbox.getLemma(id - 1);
            displayEntry(entryInputVbox.getLang(), lemma.get(1), "", lemma.get(3), lemma.get(2), "", "", "true",
                    newSearch, false, true);
        });

        if (lemmaDisplayVbox.getLemmaSize() == 1) {
            List<String> lemma = lemmaDisplayVbox.getLemma(0);
            displayEntry(entryInputVbox.getLang(), lemma.get(1), "", lemma.get(3), lemma.get(2), "", "", "true",
                    newSearch, false, true);
            return;
        }
    }

    /**
     * Creates the history page. The user can select a page to search.
     */
    public void history() {
        reportBtn.setDisable(true);
        List<List<String>> history = inputEngine.getHistory();
        VBox historyVbox = historyDisplayVbox.create(history);
        this.contentScrollPane.setContent(historyVbox);
        historyDisplayVbox.getGoBtn().setOnAction((event) -> {
            int ind = historyDisplayVbox.getSelectedBtnVal();
            if (ind == -1) {
                List<String> error = new ArrayList<>();
                error.add("No radio button was selected");
                handleError(error);
                return;
            }
            List<String> entry = history.get(ind);
            displayEntry(entry.get(0), entry.get(1), entry.get(2), entry.get(3), entry.get(4), entry.get(5),
                    entry.get(6), entry.get(7), true, true, false);
            inputEngine.setCurrentPageInd(ind);
        });
    }

    /**
     * Creates the report dialog window. The user enters the pastebin information and sends a POST request to the Pastebin Api
     */
    public void sendReport() {
        Dialog<String> dialog = reportDialog.create();
        reportDialog.getSendBtn().addEventFilter(ActionEvent.ACTION, event -> {
            List<String> error = outputEngine.sendReport(inputEngine.getRetrieveEntry(), reportDialog.getPrivateVal(), reportDialog.getNameVal(),
                    reportDialog.getUserKeyVal(), reportDialog.getExpireVal(), reportDialog.getFolderVal());
            if (error.size() > 0) {
                handleError(error);
                return;
            }
            TextInputDialog linkDialog = new TextInputDialog(outputEngine.getPastebinLink());
            linkDialog.setTitle("Send Report");
            linkDialog.setHeaderText("Link to paste:");
            linkDialog.showAndWait();
        });
        dialog.showAndWait();
    }
}
