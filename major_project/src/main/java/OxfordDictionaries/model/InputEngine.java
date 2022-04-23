package OxfordDictionaries.model;

import OxfordDictionaries.model.request.responseClasses.RetrieveEntry;

import java.util.List;

public interface InputEngine {

    List<String> entrySearch(String lang, String word, String field, String gramFeat, String lexiCate,
                             String domains, String registers, String match, boolean newSearch);

    RetrieveEntry getRetrieveEntry();

    List<String> lemmaSearch(String lang, String word, String gramFeat, String lexiCate);

    List<List<String>> getHistory();
}
