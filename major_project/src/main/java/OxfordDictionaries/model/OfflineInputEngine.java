package OxfordDictionaries.model;

import OxfordDictionaries.model.request.responseClasses.RetrieveEntry;

import java.util.List;

public class OfflineInputEngine implements InputEngine {
    private RetrieveEntry retrieveEntry;
    private List<List<String>> history;
    private int currentPageInd;

    public List<String> entrySearch(String lang, String word, String field, String gramFeat, String lexiCate,
                                    String domains, String registers, String match, boolean newSearch, boolean historyEntry) {
        return null;
    }

    public RetrieveEntry getRetrieveEntry() {
        return retrieveEntry;
    }

    public List<String> lemmaSearch(String lang, String word, String gramFeat, String lexiCate) {
        return null;
    }

    public List<List<String>> getHistory() {
        return history;
    }

    public void setCurrentPageInd(int ind) {
        this.currentPageInd = ind;
    }
}
