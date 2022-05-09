package oxforddictionaries.model.request.responseclasses;

import java.util.List;

/**
 * POJO
 */
public class HeadwordEntry {
    private String id;
    private String language;
    private List<LexicalEntry> lexicalEntries;
    private List<Pronunciation> pronunciations; // optional
    private String type; // optional
    private String word;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @return lexical entries
     */
    public List<LexicalEntry> getLexicalEntries() {
        return lexicalEntries;
    }

    /**
     * @return pronunciations
     */
    public List<Pronunciation> getPronunciations() {
        return pronunciations;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @return word
     */
    public String getWord() {
        return word;
    }
}
