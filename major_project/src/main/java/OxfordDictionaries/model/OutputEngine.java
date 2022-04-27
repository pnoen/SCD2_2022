package oxfordDictionaries.model;

import oxfordDictionaries.model.request.responseClasses.RetrieveEntry;

import java.util.List;

public interface OutputEngine {
    List<String> sendReport(RetrieveEntry retrieveEntry, int apiPastePrivate, String apiPasteName, String userApiKey,
                            String apiPasteExpireDate, String apiFolderKey);

    String getPastebinLink();
}
