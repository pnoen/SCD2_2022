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
    private AboutData aboutData;
    private List<List<String>> pronunciations;

    /**
     * Creates the offline input engine
     * @param dummyAPI dummy api
     * @param lemmaProcessor lemma processor
     * @param aboutData about information
     */
    public OfflineInputEngine(DummyAPI dummyAPI, LemmaProcessor lemmaProcessor, AboutData aboutData) {
        this.dummyAPI = dummyAPI;
        this.lemmaProcessor = lemmaProcessor;
        this.aboutData = aboutData;
        this.history = new ArrayList<>();
        this.pronunciations = new ArrayList<>();
        setupHistory();
        setupPronunciations();
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
     * @param cacheDecided notified user
     * @param useCache cache or request new data
     * @return empty list
     */
    public List<String> entrySearch(String lang, String word, String field, String gramFeat, String lexiCate,
                                    String domains, String registers, String match, boolean newSearch, boolean historyEntry, boolean lemma,
                                    boolean cacheDecided, boolean useCache) {
        String json = dummyAPI.getEntrySearchJSON();
        Gson gson = new Gson();
        this.retrieveEntry = gson.fromJson(json, RetrieveEntry.class);
        List<String> error = new ArrayList<>();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            error.add(e.getMessage());
        }
        return error;
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
     * @param cacheDecided cached been decided
     * @param useCache cache or request new data
     * @return empty list
     */
    public List<String> lemmaSearch(String lang, String word, String gramFeat, String lexiCate, boolean cacheDecided, boolean useCache) {
        String json = dummyAPI.getLemmaSearchJSON();
        Gson gson = new Gson();
        this.retrieveEntry = gson.fromJson(json, RetrieveEntry.class);
        List<String> error = new ArrayList<>();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            error.add(e.getMessage());
        }
        return error;
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

    /**
     * Gets the application name from the about data
     * @return application name
     */
    public String getAboutAppName() {
        return aboutData.getAppName();
    }

    /**
     * Gets the developer name from the about data
     * @return developer name
     */
    public String getAboutDevName() {
        return aboutData.getDevName();
    }

    /**
     * Gets the references from the about data
     * @return references
     */
    public List<String> getAboutReferences() {
        return aboutData.getReferences();
    }

    /**
     * Adds pronunciation to the list of pronunciations
     * @param entryId Entry ID
     * @param pronunciation Pronunciation URI
     * @return added or not
     */
    public boolean addPronunciation(String entryId, String pronunciation) {
        return true;
    }

    /**
     * Gets the list of pronunciations
     * @return pronunciations
     */
    public List<List<String>> getPronunciations() {
        return pronunciations;
    }

    /**
     * Removes pronunciation from the list of pronunciations
     * @param pronunciation Pronunciation URI
     * @return removed or not
     */
    public boolean removePronunciation(String pronunciation) {
        return true;
    }

    /**
     * Creates fake pronunciation list
     */
    public void setupPronunciations() {
        List<String> pronunciation = new ArrayList<>();
        pronunciation.add("noun");
        pronunciation.add("https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3");
        pronunciations.add(pronunciation);
    }
}
