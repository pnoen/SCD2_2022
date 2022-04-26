package OxfordDictionaries.view;

import OxfordDictionaries.model.request.responseClasses.RetrieveEntry;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestLemmaDisplayVbox {
    private LemmaDisplayVbox lemmaDisplayVbox;

    @BeforeEach
    public void setup() {
        this.lemmaDisplayVbox = new LemmaDisplayVbox();
    }

    @Test
    public void createDataGramFeat() {
        String json = "{\"metadata\": {\"provider\": \"Oxford University Press\"},\"results\": [{\"id\": \"FG\",\"language\": \"en\",\"lexicalEntries\": [" +
                "{\"grammaticalFeatures\": [{\"id\": \"abbreviation\",\"text\": \"Abbreviation\",\"type\": \"Residual\"}],\"inflectionOf\": [{\"id\": \"FG\"," +
                "\"text\": \"FG\"}],\"language\": \"en\",\"lexicalCategory\": {\"id\": \"residual\",\"text\": \"Residual\"},\"text\": \"FG\"}],\"word\": " +
                "\"FG\"}]}";
        Gson gson = new Gson();
        RetrieveEntry retrieveEntry = gson.fromJson(json, RetrieveEntry.class);
        List<List<String>> actual = lemmaDisplayVbox.createData(retrieveEntry);

        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0).size(), equalTo(4));
        assertThat(actual.get(0).get(1), equalTo("FG"));
        assertThat(actual.get(0).get(2), equalTo("residual"));
        assertThat(actual.get(0).get(3), equalTo("abbreviation"));
    }

    @Test
    public void createDataNoGramFeat() {
        String json = "{\"metadata\": {\"provider\": \"Oxford University Press\"},\"results\": [{\"id\": \"aces\",\"language\": \"en\",\"lexicalEntries\"" +
                ": [{\"inflectionOf\": [{\"id\": \"ace\",\"text\": \"ace\"}],\"language\": \"en\",\"lexicalCategory\": {\"id\": \"noun\",\"text\": \"Noun\"" +
                "},\"text\": \"aces\"},{\"inflectionOf\": [{\"id\": \"ace\",\"text\": \"ace\"}],\"language\": \"en\",\"lexicalCategory\": {\"id\": \"verb\"," +
                "\"text\": \"Verb\"},\"text\": \"aces\"}],\"word\": \"aces\"}]}";
        Gson gson = new Gson();
        RetrieveEntry retrieveEntry = gson.fromJson(json, RetrieveEntry.class);
        List<List<String>> actual = lemmaDisplayVbox.createData(retrieveEntry);

        assertThat(actual.size(), equalTo(2));
        assertThat(actual.get(0).size(), equalTo(4));
        assertThat(actual.get(0).get(1), equalTo("ace"));
        assertThat(actual.get(0).get(2), equalTo("noun"));
        assertThat(actual.get(0).get(3), equalTo(""));

        assertThat(actual.get(1).size(), equalTo(4));
        assertThat(actual.get(1).get(1), equalTo("ace"));
        assertThat(actual.get(1).get(2), equalTo("verb"));
        assertThat(actual.get(1).get(3), equalTo(""));
    }

    @Test
    public void createDataEmpty() {
        String json = "{\"metadata\": {\"provider\": \"Oxford University Press\"},\"results\": [{\"id\": \"aces\",\"language\": \"en\",\"lexicalEntries\"" +
                ": [{\"inflectionOf\": [],\"language\": \"en\",\"lexicalCategory\": {\"id\": \"noun\",\"text\": \"Noun\"" +
                "},\"text\": \"aces\"}],\"word\": \"aces\"}]}";
        Gson gson = new Gson();
        RetrieveEntry retrieveEntry = gson.fromJson(json, RetrieveEntry.class);
        List<List<String>> actual = lemmaDisplayVbox.createData(retrieveEntry);

        assertThat(actual.size(), equalTo(0));
    }
}
