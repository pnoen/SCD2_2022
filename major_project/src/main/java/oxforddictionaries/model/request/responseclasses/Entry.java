package oxforddictionaries.model.request.responseclasses;

import java.util.List;

/**
 * POJO
 */
public class Entry {
    private List<String> crossReferenceMarkers; // optional
    private List<CrossReference> crossReferences; // optional
    private List<String> etymologies; // optional
    private List<GrammaticalFeature> grammaticalFeatures; // optional
    private String homographNumber; // optional
    private List<InflectedForm> inflections; // optional
    private List<CategorizedText> notes; // optional
    private List<Pronunciation> pronunciations; // optional
    private List<Sense> senses; // optional
    private List<VariantForm> variantForms; // optional

    /**
     * @return cross reference markers
     */
    public List<String> getCrossReferenceMarkers() {
        return crossReferenceMarkers;
    }

    /**
     * @return cross references
     */
    public List<CrossReference> getCrossReferences() {
        return crossReferences;
    }

    /**
     * @return etymologies
     */
    public List<String> getEtymologies() {
        return etymologies;
    }

    /**
     * @return grammatical features
     */
    public List<GrammaticalFeature> getGrammaticalFeatures() {
        return grammaticalFeatures;
    }

    /**
     * @return homograph number
     */
    public String getHomographNumber() {
        return homographNumber;
    }

    /**
     * @return inflections
     */
    public List<InflectedForm> getInflections() {
        return inflections;
    }

    /**
     * @return notes
     */
    public List<CategorizedText> getNotes() {
        return notes;
    }

    /**
     * @return pronunciations
     */
    public List<Pronunciation> getPronunciations() {
        return pronunciations;
    }

    /**
     * @return senses
     */
    public List<Sense> getSenses() {
        return senses;
    }

    /**
     * @return variant forms
     */
    public List<VariantForm> getVariantForms() {
        return variantForms;
    }
}
