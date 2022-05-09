package oxforddictionarie.model.request.responseclasse;

import java.util.List;

public class HeadwordEntry {
    private String id;
    private String language;
    private List<LexicalEntry> lexicalEntries;
    private List<Pronunciation> pronunciations; // optional
    private String type; // optional
    private String word;

    public String getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public List<LexicalEntry> getLexicalEntries() {
        return lexicalEntries;
    }

    public List<Pronunciation> getPronunciations() {
        return pronunciations;
    }

    public String getType() {
        return type;
    }

    public String getWord() {
        return word;
    }
}
