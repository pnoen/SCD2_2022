package oxforddictionarie.model.request;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private final String INPUT_APP_KEY;
    private final String INPUT_API_APP_ID;

    public Request(String INPUT_API_APP_ID, String INPUT_APP_KEY) {
        this.INPUT_API_APP_ID = INPUT_API_APP_ID;
        this.INPUT_APP_KEY = INPUT_APP_KEY;
    }

    public List<String> getRequest(String uri) {
        List<String> msg = new ArrayList<String>();
//        System.out.println(INPUT_API_APP_ID + " " + INPUT_APP_KEY);
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .GET()
                    .header("Accept", "application/json")
                    .header("app_id", INPUT_API_APP_ID)
                    .header("app_key", INPUT_APP_KEY)
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            msg.add(String.valueOf(response.statusCode()));
//            msg.add(String.valueOf(response.headers()));
            msg.add(response.body());

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
//            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
//            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }

    public List<String> postRequest(String uri, String postBody) {
        List<String> msg = new ArrayList<String>();
        try {
            byte[] bytes = postBody.getBytes(StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .POST(HttpRequest.BodyPublishers.ofByteArray(bytes))
                    .header("Content-type", "application/x-www-form-urlencoded")
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            msg.add(String.valueOf(response.statusCode()));
//            msg.add(String.valueOf(response.headers()));
            msg.add(response.body());
//            System.out.println(response.body());

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
//            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
//            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }
}
