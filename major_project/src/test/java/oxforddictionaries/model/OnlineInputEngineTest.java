package oxforddictionaries.model;

import oxforddictionaries.model.request.Request;
import oxforddictionaries.model.request.SqlDatabase;
import oxforddictionaries.model.request.responseclasses.RetrieveEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class OnlineInputEngineTest {
    private OnlineInputEngine onlineInputEngine;
    private Request requestMock;
    private LemmaProcessor lemmaProcessorMock;
    private SqlDatabase sqlDatabaseMock;
    private AboutData aboutDataMock;

    @BeforeEach
    public void setup() {
        this.requestMock = mock(Request.class);
        this.lemmaProcessorMock = mock(LemmaProcessor.class);
        this.sqlDatabaseMock = mock(SqlDatabase.class);
        this.aboutDataMock = mock(AboutData.class);
        this.onlineInputEngine = new OnlineInputEngine(requestMock, lemmaProcessorMock, sqlDatabaseMock, aboutDataMock);
    }

    @Test
    public void createUriFieldsValidFields() {
        String uri = "uri.test/";
        String newUri = onlineInputEngine.createUriFields(uri, "a", "b", "c,c", "d", "e", "f");
        String expected = "uri.test/?fields=a&grammaticalFeatures=b&lexicalCategory=c,c&domains=d&registers=e&strictMatch=f";
        assertThat(newUri, equalTo(expected));
    }

    @Test
    public void createUriFieldsNullFields() {
        String uri = "uri.test/";
        String newUri = onlineInputEngine.createUriFields(uri, null, null, null, null, null, null);
        assertThat(newUri, equalTo(uri));
    }

    @Test
    public void createUriFieldsEmptyFields() {
        String uri = "uri.test/";
        String newUri = onlineInputEngine.createUriFields(uri, "a", "", "   ", " ", "", "b");
        String expected = "uri.test/?fields=a&strictMatch=b";
        assertThat(newUri, equalTo(expected));
    }

    @Test
    public void createUriFieldsLeadingSpaces() {
        String uri = "uri.test/";
        String newUri = onlineInputEngine.createUriFields(uri, " a ", "", "", "", "", "");
        String expected = "uri.test/?fields= a ";
        assertThat(newUri, equalTo(expected));
    }

    @Test
    public void uriEscapePercentageSymbol() {
        String uri = "uri.test/%new";
        String newUri = onlineInputEngine.uriEscape(uri);
        String expected = "uri.test/%25new";
        assertThat(newUri, equalTo(expected));
    }

    @Test
    public void uriEscapeDollarSymbol() {
        String uri = "uri.test/$new";
        String newUri = onlineInputEngine.uriEscape(uri);
        String expected = "uri.test/%24new";
        assertThat(newUri, equalTo(expected));
    }

    @Test
    public void uriEscapeSpace() {
        String uri = "uri.test/ new";
        String newUri = onlineInputEngine.uriEscape(uri);
        String expected = "uri.test/%20new";
        assertThat(newUri, equalTo(expected));
    }

    @Test
    public void uriEscapeCombination() {
        String uri = "uri.test/% $ new";
        String newUri = onlineInputEngine.uriEscape(uri);
        String expected = "uri.test/%25%20%24%20new";
        assertThat(newUri, equalTo(expected));
    }

    @Test
    public void createHistoryEntryValid() {
        List<String> entry = onlineInputEngine.createHistoryEntry("en-gb", "noun", "", "", "",
                "", "", "", true);
        assertThat(entry.size(), equalTo(9));
        assertThat(entry.get(0), equalTo("en-gb"));
        assertThat(entry.get(1), equalTo("noun"));
    }

    @Test
    public void createHistoryEntryNullEmpty() {
        List<String> entry = onlineInputEngine.createHistoryEntry("en-gb", "noun", null, "", null,
                "", "", "", true);
        assertThat(entry.size(), equalTo(9));
        assertThat(entry.get(2), equalTo(""));
        assertThat(entry.get(3), equalTo(""));
    }

    @Test
    public void createHistoryEntrySpaces() {
        List<String> entry = onlineInputEngine.createHistoryEntry("en-gb", "noun", " ", " s ", null,
                "", "", "", true);
        assertThat(entry.size(), equalTo(9));
        assertThat(entry.get(2), equalTo(" "));
        assertThat(entry.get(3), equalTo(" s "));
    }

    @Test
    public void createHistoryEntryNewSearchTrue() {
        List<String> entry = onlineInputEngine.createHistoryEntry("en-gb", "noun", "", "", null,
                "", "", "", true);
        assertThat(entry.get(8), equalTo("Searched"));
    }

    @Test
    public void createHistoryEntryNewSearchFalse() {
        List<String> entryTrue = onlineInputEngine.createHistoryEntry("en-gb", "noun", "", "", null,
                "", "", "", true);
        onlineInputEngine.getHistory().add(entryTrue);

        List<String> entryFalse = onlineInputEngine.createHistoryEntry("en-gb", "yo", "", "", null,
                "", "", "", false);
        assertThat(entryFalse.get(8), equalTo("Synonym/Antonym of noun"));
    }

    @Test
    public void createHistoryEntrySynonymOfFirstEntry() {
        List<String> entry1 = onlineInputEngine.createHistoryEntry("en-gb", "first", "", "", null,
                "", "", "", true);
        onlineInputEngine.getHistory().add(entry1);

        List<String> entry2 = onlineInputEngine.createHistoryEntry("en-gb", "second", "", "", null,
                "", "", "", true);
        onlineInputEngine.getHistory().add(entry2);

        assertThat(onlineInputEngine.getHistory().get(0), equalTo(entry1));
        assertThat(onlineInputEngine.getHistory().get(1), equalTo(entry2));

        List<String> entry3 = onlineInputEngine.createHistoryEntry("en-gb", "yo", "", "", null,
                "", "", "", false);

        assertThat(entry3.get(8), equalTo("Synonym/Antonym of first"));
    }

    @Test
    public void handleError() {
        String code = "404";
        String body = "{\"error\": \"No entry found matching supplied source_lang, word and provided filters\"}";
        List<String> error = onlineInputEngine.handleErrorReq(code, body);
        assertThat(error.size(), equalTo(2));
        assertThat(error.get(0), equalTo("404"));
        assertThat(error.get(1), equalTo("No entry found matching supplied source_lang, word and provided filters"));
    }

    @Test
    public void handleErrorExtraBody() {
        String code = "401";
        String body = "{\"error\": \"error message\", \"extra\": \"not necessary\"}";
        List<String> error = onlineInputEngine.handleErrorReq(code, body);
        assertThat(error.size(), equalTo(2));
        assertThat(error.get(0), equalTo("401"));
        assertThat(error.get(1), equalTo("error message"));
    }

    @Test
    public void entrySearchValid() {
        List<String> sqlResponse = new ArrayList<>();
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, false, false);
        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(1));
        assertThat(onlineInputEngine.getHistory().get(0).get(1), equalTo("noun"));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getMetadata().getSchema(), equalTo("entry"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).addEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}",
                200);
    }

    @Test
    public void entrySearchValidCachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, true, true);
        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(1));
        assertThat(onlineInputEngine.getHistory().get(0).get(1), equalTo("noun"));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getMetadata().getSchema(), equalTo("entry"));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchValidCachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, true, false);
        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(1));
        assertThat(onlineInputEngine.getHistory().get(0).get(1), equalTo("noun"));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getMetadata().getSchema(), equalTo("entry"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).updateEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}",
                200);
    }

    @Test
    public void entrySearchValidCachedNotDecided() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), is(nullValue()));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchNull() {
        List<String> sqlResponse = new ArrayList<>();
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", null, "", null,
                "", "", "", true, false, false, false, false);
        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(1));
        assertThat(onlineInputEngine.getHistory().get(0).get(1), equalTo("noun"));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getMetadata().getSchema(), equalTo("entry"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).addEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}",
                200);
    }

    @Test
    public void entrySearchNullCachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", null, "", null,
                "", "", "", true, false, false, true, true);
        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(1));
        assertThat(onlineInputEngine.getHistory().get(0).get(1), equalTo("noun"));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getMetadata().getSchema(), equalTo("entry"));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchNullCachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", null, "", null,
                "", "", "", true, false, false, true, false);
        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(1));
        assertThat(onlineInputEngine.getHistory().get(0).get(1), equalTo("noun"));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getMetadata().getSchema(), equalTo("entry"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).updateEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}",
                200);
    }

    @Test
    public void entrySearchNullCachedNotDecided() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", null, "", null,
                "", "", "", true, false, false, false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), is(nullValue()));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchSpaces() {
        List<String> sqlResponse = new ArrayList<>();
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "  ", "  ", "",
                " ", "", "", true, false, false, false, false);
        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(1));
        assertThat(onlineInputEngine.getHistory().get(0).get(1), equalTo("noun"));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getMetadata().getSchema(), equalTo("entry"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).addEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}",
                200);
    }

    @Test
    public void entrySearchSpacesCachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "  ", "  ", "",
                " ", "", "", true, false, false, true, true);
        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(1));
        assertThat(onlineInputEngine.getHistory().get(0).get(1), equalTo("noun"));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getMetadata().getSchema(), equalTo("entry"));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchSpacesCachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "  ", "  ", "",
                " ", "", "", true, false, false, true, false);
        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(1));
        assertThat(onlineInputEngine.getHistory().get(0).get(1), equalTo("noun"));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getMetadata().getSchema(), equalTo("entry"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).updateEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}",
                200);
    }

    @Test
    public void entrySearchSpacesCachedNotDecided() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "  ", "  ", "",
                " ", "", "", true, false, false, false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), is(nullValue()));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchExceptionCaused() {
        List<String> sqlResponse = new ArrayList<>();
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("Caught some exception here");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), equalTo("Caught some exception here"));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(0));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchExceptionCausedCachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("Caught some exception here");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, true, true);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), equalTo("Caught some exception here"));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(0));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchExceptionCausedCachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("Caught some exception here");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, true, false);
        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(1));
        assertThat(onlineInputEngine.getHistory().get(0).get(1), equalTo("noun"));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getMetadata().getSchema(), equalTo("entry"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).updateEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}",
                200);
    }

    @Test
    public void entrySearchExceptionCausedNotDecided() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("Caught some exception here");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), is(nullValue()));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchResponseError() {
        List<String> sqlResponse = new ArrayList<>();
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("400");
        response.add("{\"error\": \"error body\"}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, false, false);
        assertThat(actual.size(), equalTo(2));
        assertThat(actual.get(0), equalTo("400"));
        assertThat(actual.get(1), equalTo("error body"));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(0));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).addEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"error\": \"error body\"}",
                400);
    }

    @Test
    public void entrySearchResponseErrorCachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("400");
        sqlResponse.add("{\"error\": \"error body\"}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, true, true);
        assertThat(actual.size(), equalTo(2));
        assertThat(actual.get(0), equalTo("400"));
        assertThat(actual.get(1), equalTo("error body"));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(0));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchResponseErrorCachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("400");
        sqlResponse.add("{\"error\": \"error body\"}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("400");
        response.add("{\"error\": \"error body\"}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, true, false);
        assertThat(actual.size(), equalTo(2));
        assertThat(actual.get(0), equalTo("400"));
        assertThat(actual.get(1), equalTo("error body"));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(0));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).updateEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"error\": \"error body\"}",
                400);
    }

    @Test
    public void entrySearchResponseErrorCachedNotDecided() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("400");
        sqlResponse.add("{\"error\": \"error body\"}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), is(nullValue()));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchNoResultsFound() {
        List<String> response = new ArrayList<>();
        response.add("404");
        response.add("{\"error\": \"no entry found\"}");
        when(requestMock.getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, false, false);
        assertThat(actual, is(nullValue()));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(0));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).addEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"error\": \"no entry found\"}",
                404);
    }

    @Test
    public void entrySearchNoResultsFoundCachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("404");
        sqlResponse.add("{\"error\": \"no entry found\"}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, true, true);
        assertThat(actual, is(nullValue()));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(0));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchNoResultsFoundCachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("404");
        sqlResponse.add("{\"error\": \"no entry found\"}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("404");
        response.add("{\"error\": \"no entry found\"}");
        when(requestMock.getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, true, false);
        assertThat(actual, is(nullValue()));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(0));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).updateEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"error\": \"no entry found\"}",
                404);
    }

    @Test
    public void entrySearchNoResultsFoundCachedNotDecided() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("404");
        sqlResponse.add("{\"error\": \"no entry found\"}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), is(nullValue()));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchNoResultsFoundLemma() {
        List<String> response = new ArrayList<>();
        response.add("404");
        response.add("{\"error\": \"no entry found\"}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, true, false, false);
        assertThat(actual.size(), equalTo(2));
        assertThat(actual.get(0), equalTo("404"));
        assertThat(actual.get(1), equalTo("no entry found"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).addEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"error\": \"no entry found\"}",
                404);
    }

    @Test
    public void entrySearchNoResultsFoundLemmaCachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("404");
        sqlResponse.add("{\"error\": \"no entry found\"}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, true, true, true);
        assertThat(actual.size(), equalTo(2));
        assertThat(actual.get(0), equalTo("404"));
        assertThat(actual.get(1), equalTo("no entry found"));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchNoResultsFoundLemmaCachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("404");
        sqlResponse.add("{\"error\": \"no entry found\"}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("404");
        response.add("{\"error\": \"no entry found\"}");
        when(requestMock.getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, true, true, false);
        assertThat(actual.size(), equalTo(2));
        assertThat(actual.get(0), equalTo("404"));
        assertThat(actual.get(1), equalTo("no entry found"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).updateEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"error\": \"no entry found\"}",
                404);
    }

    @Test
    public void entrySearchNoResultsFoundLemmaCachedNotDecided() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("404");
        sqlResponse.add("{\"error\": \"no entry found\"}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, true, false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), is(nullValue()));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchMultipleSearches() {
        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, false, false);

        response.clear();
        response.add("200");
        response.add("{\"id\": \"donkey\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);
        List<String> actual = onlineInputEngine.entrySearch("en-gb", "donkey", "", "", "",
                "", "", "", true, false, false, false, false);

        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(2));
        assertThat(onlineInputEngine.getHistory().get(1).get(1), equalTo("donkey"));
        assertThat(onlineInputEngine.getCurrentPageInd(), equalTo(1));

        verify(requestMock, times(2)).getRequest(anyString());
        verify(sqlDatabaseMock, times(2)).getEntry(anyString());
        verify(sqlDatabaseMock, times(2)).addEntry(anyString(),
                anyString(),
                anyInt());
    }

    @Test
    public void entrySearchMultipleSearchesCachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry(anyString())).thenReturn(sqlResponse);

        onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, true, true);

        sqlResponse.clear();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"donkey\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry(anyString())).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "donkey", "", "", "",
                "", "", "", true, false, false, true, true);

        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(2));
        assertThat(onlineInputEngine.getHistory().get(1).get(1), equalTo("donkey"));
        assertThat(onlineInputEngine.getCurrentPageInd(), equalTo(1));

        verify(requestMock, times(0)).getRequest(anyString());
        verify(sqlDatabaseMock, times(2)).getEntry(anyString());
    }

    @Test
    public void entrySearchMultipleSearchesCachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry(anyString())).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, true, false);

        sqlResponse.clear();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"donkey\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry(anyString())).thenReturn(sqlResponse);

        response.clear();
        response.add("200");
        response.add("{\"id\": \"donkey\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "donkey", "", "", "",
                "", "", "", true, false, false, true, false);

        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(2));
        assertThat(onlineInputEngine.getHistory().get(1).get(1), equalTo("donkey"));
        assertThat(onlineInputEngine.getCurrentPageInd(), equalTo(1));

        verify(requestMock, times(2)).getRequest(anyString());
        verify(sqlDatabaseMock, times(2)).getEntry(anyString());
        verify(sqlDatabaseMock, times(2)).updateEntry(anyString(),
                anyString(),
                anyInt());
    }

    @Test
    public void entrySearchHistorySearchNewSearch() {
        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, true, false, false, false);

        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(0));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).addEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}",
                200);
    }

    @Test
    public void entrySearchHistorySearchNewSearchCachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, true, false, true, true);
        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(0));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchHistorySearchNewSearchCachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, true, false, true, false);
        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(0));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).updateEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}",
                200);
    }

    @Test
    public void entrySearchHistorySearchNewSearchCachedNotDecided() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, true, false, false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), is(nullValue()));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchHistorySearchNotNewSearch() {
        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", false, true, false, false, false);

        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(0));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).addEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}",
                200);
    }

    @Test
    public void entrySearchHistorySearchNotNewSearchCachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", false, true, false, true, true);

        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(0));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchHistorySearchNotNewSearchCachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", false, true, false, true, false);

        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(0));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).updateEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun",
                "{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}",
                200);
    }

    @Test
    public void entrySearchHistorySearchNotNewSearchCachedNotDecided() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", false, true, false, false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), is(nullValue()));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
        verify(sqlDatabaseMock, times(1)).getEntry("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/noun");
    }

    @Test
    public void entrySearchNotNewSearch() {
        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, false, false);

        response.clear();
        response.add("200");
        response.add("{\"id\": \"donkey\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        onlineInputEngine.entrySearch("en-gb", "donkey", "", "", "",
                "", "", "", true, false, false, false, false);

        assertThat(onlineInputEngine.getHistory().get(1).get(1), equalTo("donkey"));
        assertThat(onlineInputEngine.getCurrentPageInd(), equalTo(1));
        onlineInputEngine.setCurrentPageInd(0);

        response.clear();
        response.add("200");
        response.add("{\"id\": \"cow\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "cow", "", "", "",
                "", "", "", false, false, false, false, false);

        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(3));
        assertThat(onlineInputEngine.getHistory().get(0).get(1), equalTo("donkey"));
        assertThat(onlineInputEngine.getHistory().get(1).get(1), equalTo("noun"));
        assertThat(onlineInputEngine.getCurrentPageInd(), equalTo(2));

        verify(requestMock, times(3)).getRequest(anyString());
        verify(sqlDatabaseMock, times(3)).getEntry(anyString());
        verify(sqlDatabaseMock, times(3)).addEntry(anyString(),
                anyString(),
                anyInt());
    }

    @Test
    public void entrySearchNotNewSearchCachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry(anyString())).thenReturn(sqlResponse);

        onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, true, true);

        sqlResponse.clear();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"donkey\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry(anyString())).thenReturn(sqlResponse);

        onlineInputEngine.entrySearch("en-gb", "donkey", "", "", "",
                "", "", "", true, false, false, true, true);

        assertThat(onlineInputEngine.getHistory().get(1).get(1), equalTo("donkey"));
        assertThat(onlineInputEngine.getCurrentPageInd(), equalTo(1));
        onlineInputEngine.setCurrentPageInd(0);

        sqlResponse.clear();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"cow\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry(anyString())).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "cow", "", "", "",
                "", "", "", false, false, false, true, true);

        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(3));
        assertThat(onlineInputEngine.getHistory().get(0).get(1), equalTo("donkey"));
        assertThat(onlineInputEngine.getHistory().get(1).get(1), equalTo("noun"));
        assertThat(onlineInputEngine.getCurrentPageInd(), equalTo(2));

        verify(requestMock, times(0)).getRequest(anyString());
        verify(sqlDatabaseMock, times(3)).getEntry(anyString());
    }

    @Test
    public void entrySearchNotNewSearchCachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry(anyString())).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"id\": \"noun\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        onlineInputEngine.entrySearch("en-gb", "noun", "", "", "",
                "", "", "", true, false, false, true, false);

        sqlResponse.clear();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"donkey\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry(anyString())).thenReturn(sqlResponse);

        response.clear();
        response.add("200");
        response.add("{\"id\": \"donkey\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        onlineInputEngine.entrySearch("en-gb", "donkey", "", "", "",
                "", "", "", true, false, false, true, false);

        assertThat(onlineInputEngine.getHistory().get(1).get(1), equalTo("donkey"));
        assertThat(onlineInputEngine.getCurrentPageInd(), equalTo(1));
        onlineInputEngine.setCurrentPageInd(0);

        sqlResponse.clear();
        sqlResponse.add("200");
        sqlResponse.add("{\"id\": \"cow\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(sqlDatabaseMock.getEntry(anyString())).thenReturn(sqlResponse);

        response.clear();
        response.add("200");
        response.add("{\"id\": \"cow\",\"metadata\": {\"operation\": \"retrieve\",\"provider\": \"Oxford University Press\",\"schema\": \"entry\"}}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.entrySearch("en-gb", "cow", "", "", "",
                "", "", "", false, false, false, true, false);

        assertThat(actual.size(), equalTo(0));
        assertThat(onlineInputEngine.getHistory().size(), equalTo(3));
        assertThat(onlineInputEngine.getHistory().get(0).get(1), equalTo("donkey"));
        assertThat(onlineInputEngine.getHistory().get(1).get(1), equalTo("noun"));
        assertThat(onlineInputEngine.getCurrentPageInd(), equalTo(2));

        verify(requestMock, times(3)).getRequest(anyString());
        verify(sqlDatabaseMock, times(3)).getEntry(anyString());
        verify(sqlDatabaseMock, times(3)).updateEntry(anyString(),
                anyString(),
                anyInt());
    }

    @Test
    public void lemmaSearchValid() {
        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}");
        when(requestMock.getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(response);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "", "", false, false);
        assertThat(actual.size(), equalTo(0));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getResults().get(0).getId(), equalTo("forehead"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchValidCachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "", "", true, true);
        assertThat(actual.size(), equalTo(0));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getResults().get(0).getId(), equalTo("forehead"));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchValidCachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}");
        when(requestMock.getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(response);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "", "", true, false);
        assertThat(actual.size(), equalTo(0));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getResults().get(0).getId(), equalTo("forehead"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).updateLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead",
                "{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}",
                200);
    }

    @Test
    public void lemmaSearchValidCachedNotDecided() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "", "", false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), is(nullValue()));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchNull() {
        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", null, null, false, false);
        assertThat(actual.size(), equalTo(0));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getResults().get(0).getId(), equalTo("forehead"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchNullCachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", null, null, true, true);
        assertThat(actual.size(), equalTo(0));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getResults().get(0).getId(), equalTo("forehead"));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchNullCachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}");
        when(requestMock.getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(response);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", null, null, true, false);
        assertThat(actual.size(), equalTo(0));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getResults().get(0).getId(), equalTo("forehead"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).updateLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead",
                "{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}",
                200);
    }

    @Test
    public void lemmaSearchNullCachedNotDecided() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", null, null, false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), is(nullValue()));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchSpaces() {
        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "  ", " ", false, false);
        assertThat(actual.size(), equalTo(0));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getResults().get(0).getId(), equalTo("forehead"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchSpacesCachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "  ", " ", true, true);
        assertThat(actual.size(), equalTo(0));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getResults().get(0).getId(), equalTo("forehead"));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchSpacesCachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}");
        when(requestMock.getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(response);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "  ", " ", true, false);
        assertThat(actual.size(), equalTo(0));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getResults().get(0).getId(), equalTo("forehead"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).updateLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead",
                "{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}",
                200);
    }

    @Test
    public void lemmaSearchSpacesCachedNotDecided() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("200");
        sqlResponse.add("{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "  ", " ", false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), is(nullValue()));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchExceptionCaused() {
        List<String> response = new ArrayList<>();
        response.add("Exception caught somewhere here");
        when(requestMock.getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(response);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "", "", false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), equalTo("Exception caught somewhere here"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchExceptionCausedCachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("Exception caught somewhere here");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "", "", true, true);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), equalTo("Exception caught somewhere here"));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchExceptionCausedCachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("Exception caught somewhere here");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "", "", true, false);
        assertThat(actual.size(), equalTo(0));

        RetrieveEntry retrieveEntry = onlineInputEngine.getRetrieveEntry();
        assertThat(retrieveEntry.getResults().get(0).getId(), equalTo("forehead"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).updateLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead",
                "{\"metadata\": {\"provider\": \"Oxford\"},\"results\": [{\"id\": \"forehead\",\"language\": \"en\"}]}",
                200);
    }

    @Test
    public void lemmaSearchExceptionCausedCachedNotDecided() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("Exception caught somewhere here");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "", "", false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), is(nullValue()));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchErrorCode() {
        List<String> response = new ArrayList<>();
        response.add("400");
        response.add("{\"error\": \"no lemmas found\"}");
        when(requestMock.getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(response);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "", "", false, false);
        assertThat(actual.size(), equalTo(2));
        assertThat(actual.get(0), equalTo("400"));
        assertThat(actual.get(1), equalTo("no lemmas found"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchErrorCodeCachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("400");
        sqlResponse.add("{\"error\": \"no lemmas found\"}");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "", "", true, true);
        assertThat(actual.size(), equalTo(2));
        assertThat(actual.get(0), equalTo("400"));
        assertThat(actual.get(1), equalTo("no lemmas found"));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchErrorCodeCachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("400");
        sqlResponse.add("{\"error\": \"no lemmas found\"}");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("400");
        response.add("{\"error\": \"no lemmas found\"}");
        when(requestMock.getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(response);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "", "", true, false);
        assertThat(actual.size(), equalTo(2));
        assertThat(actual.get(0), equalTo("400"));
        assertThat(actual.get(1), equalTo("no lemmas found"));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).updateLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead",
                "{\"error\": \"no lemmas found\"}",
                400);
    }

    @Test
    public void lemmaSearchErrorCodeCachedNotDecided() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("400");
        sqlResponse.add("{\"error\": \"no lemmas found\"}");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "", "", false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), is(nullValue()));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchErrorCode404() {
        List<String> response = new ArrayList<>();
        response.add("404");
        response.add("{\"error\": \"no lemmas found\"}");
        when(requestMock.getRequest(anyString())).thenReturn(response);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "", "", false, false);
        assertThat(actual, is(nullValue()));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchErrorCode404CachedUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("404");
        sqlResponse.add("{\"error\": \"no lemmas found\"}");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "", "", true, true);
        assertThat(actual, is(nullValue()));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void lemmaSearchErrorCode404CachedNoUseCache() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("404");
        sqlResponse.add("{\"error\": \"no lemmas found\"}");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> response = new ArrayList<>();
        response.add("404");
        response.add("{\"error\": \"no lemmas found\"}");
        when(requestMock.getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(response);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "", "", true, false);
        assertThat(actual, is(nullValue()));

        verify(requestMock, times(1)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).updateLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead",
                "{\"error\": \"no lemmas found\"}",
                404);
    }

    @Test
    public void lemmaSearchErrorCode404CachedNotDecided() {
        List<String> sqlResponse = new ArrayList<>();
        sqlResponse.add("404");
        sqlResponse.add("{\"error\": \"no lemmas found\"}");
        when(sqlDatabaseMock.getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead")).thenReturn(sqlResponse);

        List<String> actual = onlineInputEngine.lemmaSearch("en", "forehead", "", "", false, false);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), is(nullValue()));

        verify(requestMock, times(0)).getRequest("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
        verify(sqlDatabaseMock, times(1)).getLemma("https://od-api.oxforddictionaries.com/api/v2/lemmas/en/forehead");
    }

    @Test
    public void addPronunciation() {
        boolean actual = onlineInputEngine.addPronunciation("noun", "https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3");
        assertThat(actual, is(Boolean.TRUE));

        List<List<String>> pronunciations = onlineInputEngine.getPronunciations();
        assertThat(pronunciations.size(), equalTo(1));
        assertThat(pronunciations.get(0).get(0), equalTo("noun"));
        assertThat(pronunciations.get(0).get(1), equalTo("https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3"));
    }

    @Test
    public void addPronunciationNullId() {
        boolean actual = onlineInputEngine.addPronunciation(null, "https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3");
        assertThat(actual, is(Boolean.TRUE));

        List<List<String>> pronunciations = onlineInputEngine.getPronunciations();
        assertThat(pronunciations.size(), equalTo(1));
        assertThat(pronunciations.get(0).get(0), equalTo("-"));
        assertThat(pronunciations.get(0).get(1), equalTo("https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3"));
    }

    @Test
    public void addPronunciationEmptyId() {
        boolean actual = onlineInputEngine.addPronunciation("", "https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3");
        assertThat(actual, is(Boolean.TRUE));

        List<List<String>> pronunciations = onlineInputEngine.getPronunciations();
        assertThat(pronunciations.size(), equalTo(1));
        assertThat(pronunciations.get(0).get(0), equalTo(""));
        assertThat(pronunciations.get(0).get(1), equalTo("https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3"));
    }

    @Test
    public void addPronunciationEmptyPronunciation() {
        boolean actual = onlineInputEngine.addPronunciation("noun", "");
        assertThat(actual, is(Boolean.TRUE));

        List<List<String>> pronunciations = onlineInputEngine.getPronunciations();
        assertThat(pronunciations.size(), equalTo(1));
        assertThat(pronunciations.get(0).get(0), equalTo("noun"));
        assertThat(pronunciations.get(0).get(1), equalTo(""));
    }

    @Test
    public void addPronunciationAddTwice() {
        onlineInputEngine.addPronunciation("noun", "https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3");
        boolean actual = onlineInputEngine.addPronunciation("noun", "https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3");
        assertThat(actual, is(Boolean.FALSE));

        List<List<String>> pronunciations = onlineInputEngine.getPronunciations();
        assertThat(pronunciations.size(), equalTo(1));
        assertThat(pronunciations.get(0).get(0), equalTo("noun"));
        assertThat(pronunciations.get(0).get(1), equalTo("https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3"));
    }

    @Test
    public void addPronunciationAddTwiceDiffId() {
        onlineInputEngine.addPronunciation("noun", "https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3");
        boolean actual = onlineInputEngine.addPronunciation("new", "https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3");
        assertThat(actual, is(Boolean.FALSE));

        List<List<String>> pronunciations = onlineInputEngine.getPronunciations();
        assertThat(pronunciations.size(), equalTo(1));
        assertThat(pronunciations.get(0).get(0), equalTo("noun"));
        assertThat(pronunciations.get(0).get(1), equalTo("https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3"));
    }

    @Test
    public void addPronunciationAddTwiceDiffPronunciation() {
        onlineInputEngine.addPronunciation("noun", "https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3");
        boolean actual = onlineInputEngine.addPronunciation("noun", "https://audio.oxforddictionaries.com/en/mp3/diff_gb_1.mp3");
        assertThat(actual, is(Boolean.TRUE));

        List<List<String>> pronunciations = onlineInputEngine.getPronunciations();
        assertThat(pronunciations.size(), equalTo(2));
        assertThat(pronunciations.get(0).get(0), equalTo("noun"));
        assertThat(pronunciations.get(0).get(1), equalTo("https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3"));

        assertThat(pronunciations.get(1).get(0), equalTo("noun"));
        assertThat(pronunciations.get(1).get(1), equalTo("https://audio.oxforddictionaries.com/en/mp3/diff_gb_1.mp3"));
    }

    @Test
    public void removePronunciation() {
        onlineInputEngine.addPronunciation("noun", "https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3");
        boolean actual = onlineInputEngine.removePronunciation("https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3");
        assertThat(actual, is(Boolean.TRUE));

        List<List<String>> pronunciations = onlineInputEngine.getPronunciations();
        assertThat(pronunciations.size(), equalTo(0));
    }

    @Test
    public void removePronunciationDoesntExist() {
        onlineInputEngine.addPronunciation("noun", "https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3");
        boolean actual = onlineInputEngine.removePronunciation("Doesn't exist");
        assertThat(actual, is(Boolean.FALSE));

        List<List<String>> pronunciations = onlineInputEngine.getPronunciations();
        assertThat(pronunciations.size(), equalTo(1));
        assertThat(pronunciations.get(0).get(0), equalTo("noun"));
        assertThat(pronunciations.get(0).get(1), equalTo("https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3"));
    }

    @Test
    public void removePronunciationNullPronunciation() {
        onlineInputEngine.addPronunciation("noun", "https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3");
        boolean actual = onlineInputEngine.removePronunciation(null);
        assertThat(actual, is(Boolean.FALSE));

        List<List<String>> pronunciations = onlineInputEngine.getPronunciations();
        assertThat(pronunciations.size(), equalTo(1));
        assertThat(pronunciations.get(0).get(0), equalTo("noun"));
        assertThat(pronunciations.get(0).get(1), equalTo("https://audio.oxforddictionaries.com/en/mp3/noun_gb_1.mp3"));
    }


}
