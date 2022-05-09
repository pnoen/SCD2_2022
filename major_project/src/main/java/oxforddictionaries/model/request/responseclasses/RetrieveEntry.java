package oxforddictionaries.model.request.responseclasses;

import java.util.List;

/**
 * POJO
 */
public class RetrieveEntry {
    private String id;
    private Metadata metadata; // optional
    private List<HeadwordEntry> results; // optional
    private String word;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @return metadata
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * @return results
     */
    public List<HeadwordEntry> getResults() {
        return results;
    }

    /**
     * @return word
     */
    public String getWord() {
        return word;
    }
}
