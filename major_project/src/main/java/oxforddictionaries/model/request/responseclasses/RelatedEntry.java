package oxforddictionaries.model.request.responseclasses;

import java.util.List;

/**
 * POJO
 */
// Inline model 2
public class RelatedEntry {
    private List<Domain> domains; // optional
    private String id;
    private String language; // optional
    private List<Region> regions; // optional
    private List<Register> registers; // optional
    private String text;

    /**
     * @return domains
     */
    public List<Domain> getDomains() {
        return domains;
    }

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
     * @return regions
     */
    public List<Region> getRegions() {
        return regions;
    }

    /**
     * @return registers
     */
    public List<Register> getRegisters() {
        return registers;
    }

    /**
     * @return text
     */
    public String getText() {
        return text;
    }
}
