package OxfordDictionaries.model;

import OxfordDictionaries.model.request.Request;
import OxfordDictionaries.model.request.responseClasses.RetrieveEntry;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OnlineInputEngine implements InputEngine {
    private Request request;
    private RetrieveEntry retrieveEntry;

    public OnlineInputEngine(Request request) {
        this.request = request;
    }

    public List<String> entrySearch(String lang, String word, String field, String gramFeat, String lexiCate,
                            String domains, String registers, String match) {
        String uri = "https://od-api.oxforddictionaries.com/api/v2/entries/" + lang + "/" + word;

        List<String> parameters = new ArrayList<>();
        parameters.add("fields=");
        parameters.add("grammaticalFeatures=");
        parameters.add("lexicalCategory=");
        parameters.add("domains=");
        parameters.add("registers=");
        parameters.add("strictMatch=");

        List<String> filters = new ArrayList<>();
        filters.add(field);
        filters.add(gramFeat);
        filters.add(lexiCate);
        filters.add(domains);
        filters.add(registers);
        filters.add(match);

        List<String> valid = new ArrayList<>();
        for (int i = 0; i < filters.size(); i++) {
            String filter = filters.get(i);
            String trim = filter.trim();
            if (!trim.equals("")) {
                valid.add(parameters.get(i) + filter);
            }
        }

        if (valid.size() >= 1) {
            String validStr = String.join("&", valid);
            uri += "?" + validStr;
        }


        List<String> response = request.getRequest(uri);

        if (response.size() == 3) {
            int statusCode = Integer.parseInt(response.get(0));
            System.out.println("Response body was:\n" + response.get(2));

            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                this.retrieveEntry = gson.fromJson(response.get(2), RetrieveEntry.class);
                response.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                response = handleErrorReq(response.get(0), response.get(2));
            }
        }
        return response;
    }

    public List<String> handleErrorReq(String code, String body) {
        Gson gson = new Gson();
        List<String> response = new ArrayList<String>();
        response.add(code);
        Map<String, String> errorMap = gson.fromJson(body, Map.class);
        String msg = errorMap.get("error");
        response.add(msg);
        return response;
    }

}
