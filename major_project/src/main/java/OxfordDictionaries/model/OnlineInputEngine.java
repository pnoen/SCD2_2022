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
    private List<List<String>> history;

    public OnlineInputEngine(Request request) {
        this.request = request;
        this.history = new ArrayList<>();
    }

    public List<String> entrySearch(String lang, String word, String field, String gramFeat, String lexiCate,
                            String domains, String registers, String match, boolean newSearch) {
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

        if (response.size() == 2) {
            int statusCode = Integer.parseInt(response.get(0));
//            System.out.println("Response body was:\n" + response.get(1));

            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                this.retrieveEntry = gson.fromJson(response.get(1), RetrieveEntry.class);
                response.clear();

                List<String> search = new ArrayList<>();
                search.add(lang);
                search.add(word);
                search.add(field);
                search.add(gramFeat);
                search.add(lexiCate);
                search.add(domains);
                search.add(registers);
                search.add(match);
                String newEntry = "Searched";
                if (!newSearch) {
                    newEntry = "Synonym/Antonym of " + history.get(history.size()-1).get(1);
                }
                search.add(newEntry);
                history.add(search);
            }
            else if (statusCode >= 400 && statusCode < 500) {
                response = handleErrorReq(response.get(0), response.get(1));
                if (response.get(0).equals("404")) {
                    response = null;
                }
            }
        }
        return response;
    }

    public RetrieveEntry getRetrieveEntry() {
        return retrieveEntry;
    }

    public List<String> lemmaSearch(String lang, String word, String gramFeat, String lexiCate) {
        String uri = "https://od-api.oxforddictionaries.com/api/v2/lemmas/" + lang + "/" + word;

        List<String> parameters = new ArrayList<>();
        parameters.add("grammaticalFeatures=");
        parameters.add("lexicalCategory=");

        List<String> filters = new ArrayList<>();
        filters.add(gramFeat);
        filters.add(lexiCate);

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

//        System.out.println(uri);

        List<String> response = request.getRequest(uri);

        if (response.size() == 2) {
            int statusCode = Integer.parseInt(response.get(0));
//            System.out.println("Response body was:\n" + response.get(1));

            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                this.retrieveEntry = gson.fromJson(response.get(1), RetrieveEntry.class);
                response.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                response = handleErrorReq(response.get(0), response.get(1));
                if (response.get(0).equals("404")) {
                    response = null;
                }
            }
        }
        return response;
    }

    public List<String> handleErrorReq(String code, String body) {
        Gson gson = new Gson();
        List<String> response = new ArrayList<>();
        response.add(code);
        Map<String, String> errorMap = gson.fromJson(body, Map.class);
        String msg = errorMap.get("error");
        response.add(msg);
        return response;
    }

    public List<List<String>> getHistory() {
        return history;
    }

}
