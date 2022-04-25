package OxfordDictionaries.model;

import OxfordDictionaries.model.request.DummyAPI;
import OxfordDictionaries.model.request.responseClasses.RetrieveEntry;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class OfflineInputEngine implements InputEngine {
    private DummyAPI dummyAPI;
    private RetrieveEntry retrieveEntry;
    private List<List<String>> history;
    private int currentPageInd;

    public OfflineInputEngine(DummyAPI dummyAPI) {
        this.dummyAPI = dummyAPI;
        this.history = new ArrayList<>();
        setupHistory();
    }

    public List<String> entrySearch(String lang, String word, String field, String gramFeat, String lexiCate,
                                    String domains, String registers, String match, boolean newSearch, boolean historyEntry) {
        String json = dummyAPI.getentrySearchJSON();
        Gson gson = new Gson();
        this.retrieveEntry = gson.fromJson(json, RetrieveEntry.class);
        return new ArrayList<>();
    }

    public RetrieveEntry getRetrieveEntry() {
        return retrieveEntry;
    }

    public List<String> lemmaSearch(String lang, String word, String gramFeat, String lexiCate) {
        String json = dummyAPI.getLemmaSearchJSON();
        Gson gson = new Gson();
        this.retrieveEntry = gson.fromJson(json, RetrieveEntry.class);
        return new ArrayList<>();
    }

    public List<List<String>> getHistory() {
        return history;
    }

    public void setCurrentPageInd(int ind) {
        this.currentPageInd = ind;
    }

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
}
