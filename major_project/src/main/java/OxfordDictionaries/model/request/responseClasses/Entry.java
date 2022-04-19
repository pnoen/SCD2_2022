package OxfordDictionaries.model.request.responseClasses;

import java.util.List;

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

    public List<String> getCrossReferenceMarkers() {
        return crossReferenceMarkers;
    }

    public List<CrossReference> getCrossReferences() {
        return crossReferences;
    }

    public List<String> getEtymologies() {
        return etymologies;
    }

    public List<GrammaticalFeature> getGrammaticalFeatures() {
        return grammaticalFeatures;
    }

    public String getHomographNumber() {
        return homographNumber;
    }

    public List<InflectedForm> getInflections() {
        return inflections;
    }

    public List<CategorizedText> getNotes() {
        return notes;
    }

    public List<Pronunciation> getPronunciations() {
        return pronunciations;
    }

    public List<Sense> getSenses() {
        return senses;
    }

    public List<VariantForm> getVariantForms() {
        return variantForms;
    }
}
