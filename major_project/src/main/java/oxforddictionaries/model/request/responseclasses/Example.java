package oxforddictionaries.model.request.responseclasses;

import java.util.List;

/**
 * POJO
 */
// Inline model 12
public class Example {
    private List<String> definitions; // optional
    private List<Domain> domains; // optional
    private List<CategorizedText> notes; // optional
    private List<Region> regions; // optional
    private List<Register> registers; // optional
    private List<String> senseIds; // optional
    private String text;

    /**
     * @return definitions
     */
    public List<String> getDefinitions() {
        return definitions;
    }

    /**
     * @return domains
     */
    public List<Domain> getDomains() {
        return domains;
    }

    /**
     * @return notes
     */
    public List<CategorizedText> getNotes() {
        return notes;
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
     * @return sense ids
     */
    public List<String> getSenseIds() {
        return senseIds;
    }

    /**
     * @return text
     */
    public String getText() {
        return text;
    }
}
