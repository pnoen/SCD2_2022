package OxfordDictionaries.model.request.responseClasses;

import java.util.List;

public class RetrieveEntry {
    private String id;
    private Metadata metadata; // optional
    private List<HeadwordEntry> results; // optional
    private String word;
    private String error;

    public String getId() {
        return id;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public List<HeadwordEntry> getResults() {
        return results;
    }

    public String getWord() {
        return word;
    }

    public String getError() {
        return error;
    }
}
