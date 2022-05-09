package oxforddictionaries.model;

import oxforddictionaries.model.request.PastebinFormatter;
import oxforddictionaries.model.request.PastebinPost;
import oxforddictionaries.model.request.PastebinPostBuilder;
import oxforddictionaries.model.request.Request;
import oxforddictionaries.model.request.responseclasses.RetrieveEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class OnlineOutputEngineTest {
    private OnlineOutputEngine onlineOutputEngine;
    private Request requestMock;
    private PastebinPostBuilder pastebinPostBuilderMock;
    private PastebinFormatter pastebinFormatterMock;

    @BeforeEach
    public void setup() {
        this.requestMock = mock(Request.class);
        this.pastebinPostBuilderMock = mock(PastebinPostBuilder.class);
        this.pastebinFormatterMock = mock(PastebinFormatter.class);
        this.onlineOutputEngine = new OnlineOutputEngine("your_key", requestMock, pastebinPostBuilderMock, pastebinFormatterMock);
    }

    @Test
    public void sendReportValid() {
        PastebinPost pastebinPost = new PastebinPost("key", "paste", "code", 0);
        when(pastebinPostBuilderMock.getPastebinPost()).thenReturn(pastebinPost);

        String postBody = "api_dev_key=key&api_option=paste&api_paste_code=code&api_paste_private=0";

        String uri = "https://pastebin.com/api/api_post.php";

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("https://pastebin.com/fakeLink");
        when(requestMock.postRequest(uri, postBody)).thenReturn(response);

        RetrieveEntry retrieveEntryMock = mock(RetrieveEntry.class);
        when(retrieveEntryMock.getId()).thenReturn("yo");

        List<String> actual = onlineOutputEngine.sendReport(retrieveEntryMock, 0, null,
                null, null, null);
        assertThat(actual.size(), equalTo(0));
        assertThat(onlineOutputEngine.getPastebinLink(),equalTo("https://pastebin.com/fakeLink"));

        verify(pastebinPostBuilderMock, times(1)).getPastebinPost();
        verify(requestMock, times(1)).postRequest(uri, postBody);
        verify(pastebinFormatterMock, times(1)).format(any(RetrieveEntry.class));
    }

    @Test
    public void sendReportFormatter() {
        String entry = "ID: donkey\nMetadata: \n\tOperation: retrieve\n\tProvider: Oxford University Press\n\tSchema: entry";
        when(pastebinFormatterMock.format(any(RetrieveEntry.class))).thenReturn(entry);

        PastebinPost pastebinPost = new PastebinPost("your_key", "paste", entry, 0);
        when(pastebinPostBuilderMock.getPastebinPost()).thenReturn(pastebinPost);

        String postBody = "api_dev_key=your_key&api_option=paste&api_paste_code=" + entry + "&api_paste_private=0";

        String uri = "https://pastebin.com/api/api_post.php";

        List<String> response = new ArrayList<>();
        response.add("200");
        response.add("https://pastebin.com/fakeLink");
        when(requestMock.postRequest(uri, postBody)).thenReturn(response);

        List<String> actual = onlineOutputEngine.sendReport(new RetrieveEntry(), 0, null,
                null, null, null);
        assertThat(actual.size(), equalTo(0));
        assertThat(onlineOutputEngine.getPastebinLink(),equalTo("https://pastebin.com/fakeLink"));

        verify(pastebinPostBuilderMock, times(1)).getPastebinPost();
        verify(requestMock, times(1)).postRequest(uri, postBody);
        verify(pastebinFormatterMock, times(1)).format(any(RetrieveEntry.class));
    }

    @Test
    public void sendReportExceptionCaused() {
        PastebinPost pastebinPost = new PastebinPost("key", "paste", "code", 0);
        when(pastebinPostBuilderMock.getPastebinPost()).thenReturn(pastebinPost);

        String postBody = "api_dev_key=key&api_option=paste&api_paste_code=code&api_paste_private=0";

        String uri = "https://pastebin.com/api/api_post.php";

        List<String> response = new ArrayList<>();
        response.add("Exception caught somewhere here");
        when(requestMock.postRequest(uri, postBody)).thenReturn(response);

        RetrieveEntry retrieveEntryMock = mock(RetrieveEntry.class);
        when(retrieveEntryMock.getId()).thenReturn("yo");

        List<String> actual = onlineOutputEngine.sendReport(retrieveEntryMock, 0, null,
                null, null, null);
        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), equalTo("Exception caught somewhere here"));

        verify(pastebinPostBuilderMock, times(1)).getPastebinPost();
        verify(requestMock, times(1)).postRequest(uri, postBody);
        verify(pastebinFormatterMock, times(1)).format(any(RetrieveEntry.class));
    }

    @Test
    public void sendReportError() {
        PastebinPost pastebinPost = new PastebinPost("key", "invalid", "code", 0);
        when(pastebinPostBuilderMock.getPastebinPost()).thenReturn(pastebinPost);

        String postBody = "api_dev_key=key&api_option=invalid&api_paste_code=code&api_paste_private=0";

        String uri = "https://pastebin.com/api/api_post.php";

        List<String> response = new ArrayList<>();
        response.add("400");
        response.add("Bad API request, invalid api_option");
        when(requestMock.postRequest(uri, postBody)).thenReturn(response);

        RetrieveEntry retrieveEntryMock = mock(RetrieveEntry.class);
        when(retrieveEntryMock.getId()).thenReturn("yo");

        List<String> actual = onlineOutputEngine.sendReport(retrieveEntryMock, 0, null,
                null, null, null);
        assertThat(actual.size(), equalTo(2));
        assertThat(actual.get(0), equalTo("400"));
        assertThat(actual.get(1), equalTo("Bad API request, invalid api_option"));

        verify(pastebinPostBuilderMock, times(1)).getPastebinPost();
        verify(requestMock, times(1)).postRequest(uri, postBody);
        verify(pastebinFormatterMock, times(1)).format(any(RetrieveEntry.class));
    }

    @Test
    public void createPastebinPost() {
        String entry = "ID: donkey\nmetadata: \n\toperation: retrieve\n\tprovider: Oxford University Press\n\tschema: entry";

        onlineOutputEngine.createPastebinPost(entry, 0, "", "", "", "");

        verify(pastebinPostBuilderMock, times(1)).newItem("your_key", "paste", entry, 0);
        verify(pastebinPostBuilderMock, times(1)).setPasteName("");
        verify(pastebinPostBuilderMock, times(1)).setUserKey("");
        verify(pastebinPostBuilderMock, times(1)).setPasteExpireDate("");
        verify(pastebinPostBuilderMock, times(1)).setFolderKey("");
        verify(pastebinPostBuilderMock, times(1)).getPastebinPost();
    }

}
