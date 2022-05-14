package oxforddictionaries.model.request;

/**
 * Acts as a mock of the Oxford Dictionaries api by providing a set json string.
 */
public class DummyAPI {

    /**
     * Creates a fake JSON string for entry searches
     * @return json string
     */
    public String getEntrySearchJSON() {
        String json = "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"RetrieveEntry\"" +
                "},\"results\": [{\"id\": \"noun\",\"language\": \"en-gb\",\"lexicalEntries\": [{\"derivatives\": [{\"id\": \"nounal\",\"text\": \"nounal\"" +
                "}],\"entries\": [{\"etymologies\": [\"late Middle English: from Anglo-Norman French, from Latin nomen ‘name’\"],\"pronunciations\": [{" +
                "\"audioFile\": \"https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3\",\"dialects\": [\"British English\"],\"phoneticNotation\": " +
                "\"IPA\",\"phoneticSpelling\": \"naʊn\"}],\"senses\": [{\"definitions\": [\"a word (other than a pronoun) used to identify any of a class " +
                "of people, places, or things (common noun), or to name a particular one of these (proper noun)\"],\"domainClasses\": [{\"id\": \"grammar\"," +
                "\"text\": \"Grammar\"}],\"domains\": [{\"id\": \"grammar\",\"text\": \"Grammar\"}],\"examples\": [{\"text\": \"converting adjectives to " +
                "nouns has always been common in English\"},{\"text\": \"you must say el gato because gato, which means cat, is a masculine noun\"},{" +
                "\"text\": \"lucidity is a noun\"}],\"id\": \"m_en_gbus0702940.005\",\"semanticClasses\": [{\"id\": \"part_of_speech\",\"text\": " +
                "\"Part_Of_Speech\"}],\"shortDefinitions\": [\"word used to identify people, places, or things\"], \"synonyms\": [{\"language\": \"en\"," +
                "\"text\": \"criterion\"}]}]}],\"language\": \"en-gb\",\"lexicalCategory\": {\"id\": \"noun\",\"text\": \"Noun\"},\"text\": \"noun\"}]," +
                "\"type\": \"headword\",\"word\": \"noun\"}],\"word\": \"noun\"}";
        return json;
    }

    /**
     * Creates a fake JSON string for lemma searches
     * @return json string
     */
    public String getLemmaSearchJSON() {
        String json = "{\"metadata\": {\"provider\": \"Oxford University Press\"},\"results\": [{\"id\": \"aces\",\"language\": \"en\",\"lexicalEntries\": [{" +
                "\"inflectionOf\": [{\"id\": \"ace\",\"text\": \"ace\"}],\"language\": \"en\",\"lexicalCategory\": {\"id\": \"noun\",\"text\": \"Noun\"}," +
                "\"text\": \"aces\"},{\"inflectionOf\": [{\"id\": \"ace\",\"text\": \"ace\"}],\"language\": \"en\",\"lexicalCategory\": {\"id\": \"verb\"," +
                "\"text\": \"Verb\"},\"text\": \"aces\"}],\"word\": \"aces\"}]}";
        return json;
    }

    /**
     * Creates a fake URL for sending reports
     * @return url
     */
    public String getSendReportResponse() {
        String response = "https://pastebin.com/DummyAPI";
        return response;
    }
}
