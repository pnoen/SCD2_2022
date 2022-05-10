package oxforddictionaries.model;

import oxforddictionaries.model.request.responseclasses.RetrieveEntry;

import java.util.List;

/**
 * The application has the option to use an online or offline engine for the GET requests.
 */
public interface InputEngine {

    /**
     * Creates the uri and performs a GET request.
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
     * @return list of error messages
     */
    List<String> entrySearch(String lang, String word, String field, String gramFeat, String lexiCate,
                             String domains, String registers, String match, boolean newSearch, boolean historyEntry, boolean lemma);

    /**
     * Gets the POJO
     * @return entry
     */
    RetrieveEntry getRetrieveEntry();

    /**
     * Creates the uri and performs a GET request
     * @param lang language
     * @param word word
     * @param gramFeat grammatical features
     * @param lexiCate lexical categories
     * @return list of error messages
     */
    List<String> lemmaSearch(String lang, String word, String gramFeat, String lexiCate);

    /**
     * Contains a list of list with entry information
     * @return history
     */
    List<List<String>> getHistory();

    /**
     * Updates the current page index
     * @param ind page index
     */
    void setCurrentPageInd(int ind);

    /**
     * Finds the lemmas from the POJO
     * @return List of lemmas
     */
    List<List<String>> findLemmas();
}
