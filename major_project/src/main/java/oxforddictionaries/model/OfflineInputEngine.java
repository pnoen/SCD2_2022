package oxforddictionaries.model;

import oxforddictionaries.model.request.DummyAPI;
import oxforddictionaries.model.request.responseclasses.RetrieveEntry;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Calls from the DummyApi instead of the real api to test the GUI
 */
public class OfflineInputEngine implements InputEngine {
    private DummyAPI dummyAPI;
    private RetrieveEntry retrieveEntry;
    private List<List<String>> history;
    private int currentPageInd;
    private LemmaProcessor lemmaProcessor;

    /**
     * Creates the offline input engine
     * @param dummyAPI dummy api
     * @param lemmaProcessor lemma processor
     */
    public OfflineInputEngine(DummyAPI dummyAPI, LemmaProcessor lemmaProcessor) {
        this.dummyAPI = dummyAPI;
        this.lemmaProcessor = lemmaProcessor;
        this.history = new ArrayList<>();
        setupHistory();
    }

    /**
     * Calls from the dummy api and creates the POJO. Return an empty list.
     * @param lang language
     * @param word word
     * @param field field
     * @param gramFeat grammatical features
     * @param lexiCate lexical categories
     * @param domains domains
     * @param registers registers
     * @param match match
     * @param newSearch new search
     * @param historyEntry history search
     * @param lemma lemma search
     * @return empty list
     */
    public List<String> entrySearch(String lang, String word, String field, String gramFeat, String lexiCate,
                                    String domains, String registers, String match, boolean newSearch, boolean historyEntry, boolean lemma) {
        String json = dummyAPI.getEntrySearchJSON();
        Gson gson = new Gson();
        this.retrieveEntry = gson.fromJson(json, RetrieveEntry.class);
        return new ArrayList<>();
    }

    /**
     * Gets the POJO
     * @return entry
     */
    public RetrieveEntry getRetrieveEntry() {
        return retrieveEntry;
    }

    /**
     * Calls from the dummy api and creates the POJO. Return an empty list.
     * @param lang language
     * @param word word
     * @param gramFeat grammatical features
     * @param lexiCate lexical categories
     * @return empty list
     */
    public List<String> lemmaSearch(String lang, String word, String gramFeat, String lexiCate) {
        String json = dummyAPI.getLemmaSearchJSON();
        Gson gson = new Gson();
        this.retrieveEntry = gson.fromJson(json, RetrieveEntry.class);
        return new ArrayList<>();
    }

    /**
     * @return history
     */
    public List<List<String>> getHistory() {
        return history;
    }

    /**
     * Updates the current page index
     * @param ind page index
     */
    public void setCurrentPageInd(int ind) {
        this.currentPageInd = ind;
    }

    /**
     * Creates fake history
     */
    public void setupHistory() {
        List<String> entry = new ArrayList<>();
        entry.add("en_gb");
        entry.add("ace");
        entry.add("");
        entry.add("");
        entry.add("");
        entry.add("");
        entry.add("");
        entry.add("");
        entry.add("Searched");
        history.add(entry);

        entry = new ArrayList<>();
        entry.add("en_gb");
        entry.add("cool");
        entry.add("");
        entry.add("");
        entry.add("");
        entry.add("");
        entry.add("");
        entry.add("");
        entry.add("Searched");
        history.add(entry);
    }

    /**
     * Finds the lemmas from the POJO
     * @return List of lemmas
     */
    public List<List<String>> findLemmas() {
        return lemmaProcessor.createData(retrieveEntry);
    }

    /**
     * Clears the database tables.
     * @return null
     */
    public String clearCache() {
        return null;
    }
}
