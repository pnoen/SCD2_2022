package oxforddictionaries.model.request.responseclasses;

import java.util.List;

public class RetrieveEntry {
    private String id;
    private Metadata metadata; // optional
    private List<HeadwordEntry> results; // optional
    private String word;

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
}
