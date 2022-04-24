package OxfordDictionaries.model;

import OxfordDictionaries.model.request.responseClasses.RetrieveEntry;

import java.util.List;

public class OfflineOutputEngine implements OutputEngine {
    private String pastebinLink;

    public List<String> sendReport(RetrieveEntry retrieveEntry, int apiPastePrivate, String apiPasteName, String userApiKey,
                                   String apiPasteExpireDate, String apiFolderKey) {
        return null;
    }

    public String getPastebinLink() {
        return pastebinLink;
    }
}
