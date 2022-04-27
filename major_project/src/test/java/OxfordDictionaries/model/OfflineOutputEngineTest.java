package OxfordDictionaries.model;

import OxfordDictionaries.model.request.DummyAPI;
import OxfordDictionaries.model.request.responseClasses.RetrieveEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class OfflineOutputEngineTest {
    private OfflineOutputEngine offlineOutputEngine;
    private DummyAPI dummyAPIMock;

    @BeforeEach
    public void setup() {
        this.dummyAPIMock = mock(DummyAPI.class);
        this.offlineOutputEngine = new OfflineOutputEngine(dummyAPIMock);
    }

    @Test
    public void sendReportValid() {
        String response = "https://pastebin.com/fakeLink";
        when(dummyAPIMock.getSendReportResponse()).thenReturn(response);

        RetrieveEntry retrieveEntry = new RetrieveEntry();
        List<String> actual = offlineOutputEngine.sendReport(retrieveEntry, 0, "", "", "", "");
        assertThat(actual.size(), equalTo(0));
        assertThat(offlineOutputEngine.getPastebinLink(), equalTo("https://pastebin.com/fakeLink"));

        verify(dummyAPIMock, times(1)).getSendReportResponse();
    }

    @Test
    public void sendReportNull() {
        String response = "https://pastebin.com/fakeLink";
        when(dummyAPIMock.getSendReportResponse()).thenReturn(response);

        RetrieveEntry retrieveEntry = new RetrieveEntry();
        List<String> actual = offlineOutputEngine.sendReport(retrieveEntry, 0, null, null, null, null);
        assertThat(actual.size(), equalTo(0));
        assertThat(offlineOutputEngine.getPastebinLink(), equalTo("https://pastebin.com/fakeLink"));

        verify(dummyAPIMock, times(1)).getSendReportResponse();
    }

    @Test
    public void sendReportSpaces() {
        String response = "https://pastebin.com/fakeLink";
        when(dummyAPIMock.getSendReportResponse()).thenReturn(response);

        RetrieveEntry retrieveEntry = new RetrieveEntry();
        List<String> actual = offlineOutputEngine.sendReport(retrieveEntry, 0, "", "  ", "   ", "");
        assertThat(actual.size(), equalTo(0));
        assertThat(offlineOutputEngine.getPastebinLink(), equalTo("https://pastebin.com/fakeLink"));

        verify(dummyAPIMock, times(1)).getSendReportResponse();
    }

    @Test
    public void sendReportNegativeInt() {
        String response = "https://pastebin.com/fakeLink";
        when(dummyAPIMock.getSendReportResponse()).thenReturn(response);

        RetrieveEntry retrieveEntry = new RetrieveEntry();
        List<String> actual = offlineOutputEngine.sendReport(retrieveEntry, -100, "", "", "", "");
        assertThat(actual.size(), equalTo(0));
        assertThat(offlineOutputEngine.getPastebinLink(), equalTo("https://pastebin.com/fakeLink"));

        verify(dummyAPIMock, times(1)).getSendReportResponse();
    }
}
