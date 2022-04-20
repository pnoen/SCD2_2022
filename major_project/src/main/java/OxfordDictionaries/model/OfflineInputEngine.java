package OxfordDictionaries.model;

import OxfordDictionaries.model.request.responseClasses.RetrieveEntry;

import java.util.List;

public class OfflineInputEngine implements InputEngine {
    private RetrieveEntry retrieveEntry;

    public List<String> entrySearch(String lang, String word, String field, String gramFeat, String lexiCate,
                                    String domains, String registers, String match) {
        return null;
    }

    public RetrieveEntry getRetrieveEntry() {
        return retrieveEntry;
    }
}
