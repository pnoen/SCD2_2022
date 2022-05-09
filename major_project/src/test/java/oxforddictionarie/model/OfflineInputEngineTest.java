package oxforddictionarie.model;

import oxforddictionarie.model.request.DummyAPI;
import oxforddictionarie.model.request.responseclasse.RetrieveEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class OfflineInputEngineTest {
    private OfflineInputEngine offlineInputEngine;
    private DummyAPI dummyAPIMock;

    @BeforeEach
    public void setup() {
        this.dummyAPIMock = mock(DummyAPI.class);
        this.offlineInputEngine = new OfflineInputEngine(dummyAPIMock);
    }

    @Test
    public void entrySearchValid() {
        String response = "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}";
        when(dummyAPIMock.getentrySearchJSON()).thenReturn(response);

        List<String> actual = offlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false);
        assertThat(actual.size(), equalTo(0));

        verify(dummyAPIMock, times(1)).getentrySearchJSON();
    }

    @Test
    public void entrySearchNull() {
        String response = "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}";
        when(dummyAPIMock.getentrySearchJSON()).thenReturn(response);

        List<String> actual = offlineInputEngine.entrySearch("en-gb", "noun", null, null, null,
                null, "", "", true, false, false);
        assertThat(actual.size(), equalTo(0));

        verify(dummyAPIMock, times(1)).getentrySearchJSON();
    }

    @Test
    public void entrySearchSpaces() {
        String response = "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}";
        when(dummyAPIMock.getentrySearchJSON()).thenReturn(response);

        List<String> actual = offlineInputEngine.entrySearch("en-gb", "noun", "", "  ", " ",
                "", "", "", true, false, false);
        assertThat(actual.size(), equalTo(0));

        verify(dummyAPIMock, times(1)).getentrySearchJSON();
    }

    @Test
    public void entrySearchEmptyWord() {
        String response = "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}";
        when(dummyAPIMock.getentrySearchJSON()).thenReturn(response);

        List<String> actual = offlineInputEngine.entrySearch("en-gb", "", "", "", "",
                "", "", "", true, false, false);
        assertThat(actual.size(), equalTo(0));

        verify(dummyAPIMock, times(1)).getentrySearchJSON();
    }

    @Test
    public void entrySearchHistorySearch() {
        String response = "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}";
        when(dummyAPIMock.getentrySearchJSON()).thenReturn(response);

        List<String> actual = offlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, true, false);
        assertThat(actual.size(), equalTo(0));

        verify(dummyAPIMock, times(1)).getentrySearchJSON();
    }

    @Test
    public void entrySearchNotNewSearch() {
        String response = "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}";
        when(dummyAPIMock.getentrySearchJSON()).thenReturn(response);

        List<String> actual = offlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", false, false, false);
        assertThat(actual.size(), equalTo(0));

        verify(dummyAPIMock, times(1)).getentrySearchJSON();
    }

    @Test
    public void setupHistory() {
        assertThat(offlineInputEngine.getHistory().size(), equalTo(2));
        assertThat(offlineInputEngine.getHistory().get(0).get(1), equalTo("ace"));
        assertThat(offlineInputEngine.getHistory().get(1).get(1), equalTo("cool"));
    }

    @Test
    public void lemmaSearchValid() {
        String response = "{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}";
        when(dummyAPIMock.getLemmaSearchJSON()).thenReturn(response);

        List<String> actual = offlineInputEngine.lemmaSearch("en", "forehead", "", "");
        assertThat(actual.size(), equalTo(0));
        RetrieveEntry retrieveEntry = offlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry, is(notNullValue()));
        assertThat(retrieveEntry.getResults().get(0).getId(), equalTo("forehead"));

        verify(dummyAPIMock, times(1)).getLemmaSearchJSON();
    }

    @Test
    public void lemmaSearchNull() {
        String response = "{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}";
        when(dummyAPIMock.getLemmaSearchJSON()).thenReturn(response);

        List<String> actual = offlineInputEngine.lemmaSearch("en", "forehead", null, null);
        assertThat(actual.size(), equalTo(0));
        RetrieveEntry retrieveEntry = offlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry, is(notNullValue()));
        assertThat(retrieveEntry.getResults().get(0).getId(), equalTo("forehead"));

        verify(dummyAPIMock, times(1)).getLemmaSearchJSON();
    }

    @Test
    public void lemmaSearchSpaces() {
        String response = "{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}";
        when(dummyAPIMock.getLemmaSearchJSON()).thenReturn(response);

        List<String> actual = offlineInputEngine.lemmaSearch("en", "forehead", " ", "   ");
        assertThat(actual.size(), equalTo(0));
        RetrieveEntry retrieveEntry = offlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry, is(notNullValue()));
        assertThat(retrieveEntry.getResults().get(0).getId(), equalTo("forehead"));

        verify(dummyAPIMock, times(1)).getLemmaSearchJSON();
    }
}
