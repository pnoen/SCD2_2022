package oxforddictionaries.model;

import oxforddictionaries.model.request.responseclasses.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds the lemmas from the POJO
 */
public class LemmaProcessor {

    /**
     * Finds all the possible lemmas from in the POJO and adds it to a list.
     * @param retrieveEntry POJO
     * @return List of lemmas
     */
    public List<List<String>> createData(RetrieveEntry retrieveEntry) {
        List<List<String>> lemmas = new ArrayList<>();
        if (retrieveEntry.getResults() != null) {
            for (HeadwordEntry headwordEntry : retrieveEntry.getResults()) {
                for (LexicalEntry entry : headwordEntry.getLexicalEntries()) {
                    for (Inflection inflection : entry.getInflectionOf()) {
                        List<String> lemma = new ArrayList<>();
                        lemma.add(String.valueOf(lemmas.size() + 1));
                        lemma.add(inflection.getId());
                        lemma.add(entry.getLexicalCategory().getId());

                        if (entry.getGrammaticalFeatures() != null) {
                            for (GrammaticalFeature gramFeat : entry.getGrammaticalFeatures()) {
                                List<String> lemmaCopy = new ArrayList<>(lemma);
                                lemmaCopy.add(gramFeat.getId());
                                lemmas.add(lemmaCopy);
                            }
                            continue;
                        }
                        lemma.add("");
                        lemmas.add(lemma);
                    }
                }
            }
        }
        return lemmas;
    }
}
