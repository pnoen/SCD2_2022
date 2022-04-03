package SpaceTraders.model;

import com.google.gson.Gson;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OnlineGameEngine implements GameEngine{
    private String status;
    private Color statusIconColour;
    private Token currentToken;

    public OnlineGameEngine() {
        this.status = "Online";
        this.statusIconColour = Color.web("#01c629");
    }

    public String getStatus() {
        return this.status;
    }

    public Color getStatusIconColour() {
        return this.statusIconColour;
    }

    public List<String> register(String username) {
        // add username filtering

        List<String> msg = new ArrayList<String>();
        try {
            String uri = "https://api.spacetraders.io/users/" + username + "/claim";
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status code was: " + response.statusCode());
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + response.body());
            Gson gson = new Gson();

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                Token token = gson.fromJson(response.body(), Token.class);
                System.out.println(token);
                msg.add(token.getToken());
            }
            else if (response.statusCode() >= 400 && response.statusCode() < 500) {
                Map<String, Map<String, Object>> errorMap = gson.fromJson(response.body(), Map.class);

                String code = String.valueOf(errorMap.get("error").get("code"));
                String[] codeClean = code.split("\\.");
                msg.add(codeClean[0]);

                msg.add(String.valueOf(errorMap.get("error").get("message")));
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
        }

        return msg;
    }

    public List<String> getAccountDetails(String authToken) {
        List<String> msg = new ArrayList<String>();
        try {
            String uri = "https://api.spacetraders.io/my/account?token=" + authToken;
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status code was: " + response.statusCode());
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + response.body());
            Gson gson = new Gson();

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                Token token = gson.fromJson(response.body(), Token.class);
                token.setToken(authToken);
                this.currentToken = token;
                System.out.println(token);
            }
            else if (response.statusCode() >= 400 && response.statusCode() < 600) {
                Map<String, Map<String, Object>> errorMap = gson.fromJson(response.body(), Map.class);

                String code = String.valueOf(errorMap.get("error").get("code"));
                String[] codeClean = code.split("\\.");
                msg.add(codeClean[0]);

                msg.add(String.valueOf(errorMap.get("error").get("message")));
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
        }
        return msg;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public void logout() {
        this.currentToken = null;
    }

}
