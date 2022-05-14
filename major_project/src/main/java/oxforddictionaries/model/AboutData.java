package oxforddictionaries.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the data for the about page
 */
public class AboutData {
    /**
     * @return application name
     */
    public String getAppName() {
        return "Oxford Dictionaries GUI";
    }

    /**
     * @return developer name
     */
    public String getDevName() {
        return "Raymond T";
    }

    /**
     * @return references
     */
    public List<String> getReferences() {
        List<String> references = new ArrayList<>();
        references.add("Input engine: Oxford Dictionaries API");
        references.add("Output engine: Pastebin API");
        references.add("Theme song: dreamy night - LilyPichu");
        references.add("Loading GIF: Loading.io");
        return references;
    }
}
