package SpaceTraders.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OnlineGameEngine implements GameEngine{
    private String status;
    private String statusIconColour;
    private Token currentToken;
    private List<Loan> availableLoans;
    private List<Ship> availableShips;
    private List<Goods> goods;
    private List<Location> locations;
    private FlightPlan flightPlan;
    private Status serverStatus;

    public OnlineGameEngine() {
        this.status = "Online";
        this.statusIconColour = "#01c629";
        this.availableLoans = new ArrayList<Loan>();
        this.availableShips = new ArrayList<Ship>();
    }

    public String getStatus() {
        return this.status;
    }

    public String getStatusIconColour() {
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
//                System.out.println(token);
                msg.add(token.getToken());
            }
            else if (response.statusCode() >= 400 && response.statusCode() < 500) {
                msg = handleErrorReq(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
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
            }
            else if (response.statusCode() >= 400 && response.statusCode() < 600) {
                msg = handleErrorReq(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public void logout() {
        this.currentToken = null;
    }

    public List<String> getAvailableLoans() {
        List<String> msg = new ArrayList<String>();
        String authToken = this.currentToken.getToken();

        try {
            String uri = "https://api.spacetraders.io/types/loans?token=" + authToken;
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
                Map<String, Object> map = gson.fromJson(response.body(), Map.class);
//                System.out.println("item" + map.get("loans"));
                String json = gson.toJson(map.get("loans"));
                Loan[] loans = gson.fromJson(json, Loan[].class);
                this.availableLoans = Arrays.asList(loans);
                for (Loan loan : this.availableLoans) {
                    System.out.println(loan);
                }

            }
            else if (response.statusCode() >= 400 && response.statusCode() < 600) {
                msg = handleErrorReq(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }

    public List<Loan> getAvailableLoansList() {
        return this.availableLoans;
    }

    public List<String> takeLoan(String loanType) {
        List<String> msg = new ArrayList<String>();
        String authToken = this.currentToken.getToken();
        try {
            String uri = "https://api.spacetraders.io/my/loans?token=" + authToken + "&type=" + loanType;
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status code was: " + response.statusCode());
            System.out.println("Response body was:\n" + response.body());
            Gson gson = new Gson();

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                User user = gson.fromJson(response.body(), User.class);
                this.currentToken.setUser(user);
//                System.out.println(user.getLoan());
            }
            else if (response.statusCode() >= 400 && response.statusCode() < 600) {
                msg = handleErrorReq(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }

    public List<String> activeLoans() {
        List<String> msg = new ArrayList<String>();
        String authToken = this.currentToken.getToken();

        try {
            String uri = "https://api.spacetraders.io/my/loans?token=" + authToken;
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
                User user = gson.fromJson(response.body(), User.class);
                this.currentToken.setUser(user);
//                List<Loan> loans = this.currentToken.getUser().getLoans();
//                for (Loan loan : loans) {
//                    System.out.println(loan);
//                }
            }
            else if (response.statusCode() >= 400 && response.statusCode() < 600) {
                msg = handleErrorReq(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }

    public List<String> handleErrorReq(String body) {
        Gson gson = new Gson();
        List<String> msg = new ArrayList<String>();
        Map<String, Map<String, Object>> errorMap = gson.fromJson(body, Map.class);

        String code = String.valueOf(errorMap.get("error").get("code"));
        String[] codeClean = code.split("\\.");
        msg.add("Error code: " + codeClean[0]);

        msg.add(String.valueOf(errorMap.get("error").get("message")));
        System.out.println(errorMap.get("error"));
        if (errorMap.get("error").get("data") != null) {
            String data = gson.toJson(errorMap.get("error").get("data"));
            Map<String, List<String>> map = gson.fromJson(data, Map.class);
            if (map.get("type") != null) {
                System.out.println(map.get("type").size());
                for (String type : map.get("type")) {
                    System.out.println(type);
                    msg.add(type);
                }
            }
            if (map.get("class") != null) {
                for (String cl : map.get("class")) {
                    System.out.println(cl);
                    msg.add(cl);
                }
            }
            if (map.get("location") != null) {
                for (String location : map.get("location")) {
                    System.out.println(location);
                    msg.add(location);
                }
            }
            if (map.get("shipId") != null) {
                for (String shipId : map.get("shipId")) {
                    System.out.println(shipId);
                    msg.add(shipId);
                }
            }
            if (map.get("loadingSpeed") != null) {
                String loadingSpeed = String.valueOf(map.get("loadingSpeed"));
                String[] loadingSpeedClean = loadingSpeed.split("\\.");
                msg.add("loadingSpeed: " + loadingSpeedClean[0]);
            }
            if (map.get("good") != null) {
                for (String good : map.get("good")) {
                    System.out.println(good);
                    msg.add(good);
                }
            }
            if (map.get("quantity") != null) {
                for (String quantity : map.get("quantity")) {
                    System.out.println(quantity);
                    msg.add(quantity);
                }
            }
        }
        return msg;
    }

    public List<String> availableShips(String shipClass) {
        List<String> msg = new ArrayList<String>();
        String authToken = this.currentToken.getToken();

        try {
            String uri = "https://api.spacetraders.io/systems/OE/ship-listings?token=" + authToken;
            if (shipClass.length() != 0) {
                uri += "&class=" + shipClass;
            }
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
                Map<String, Object> map = gson.fromJson(response.body(), Map.class);
//                System.out.println(map.get("shipListings"));
                String json = gson.toJson(map.get("shipListings"));
                Ship[] ships  = gson.fromJson(json, Ship[].class);
                this.availableShips = Arrays.asList(ships);
//                for (Ship ship : this.availableShips) {
//                    System.out.println(ship);
//                }

            }
            else if (response.statusCode() >= 400 && response.statusCode() < 600) {
                msg = handleErrorReq(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }

    public List<Ship> getAvailableShips() {
        return this.availableShips;
    }

    public List<String> purchaseShip(String location, String type) {
        List<String> msg = new ArrayList<String>();
        String authToken = this.currentToken.getToken();
        try {
            String uri = "https://api.spacetraders.io/my/ships?token=" + authToken + "&location=" + location + "&type=" + type;
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status code was: " + response.statusCode());
            System.out.println("Response body was:\n" + response.body());
            Gson gson = new Gson();

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                User user = gson.fromJson(response.body(), User.class);
//                System.out.println(user.getCredits());
                this.currentToken.setUser(user);
            }
            else if (response.statusCode() >= 400 && response.statusCode() < 600) {
                msg = handleErrorReq(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }

    public List<String> getUserShips() {
        List<String> msg = new ArrayList<String>();
        String authToken = this.currentToken.getToken();

        try {
            String uri = "https://api.spacetraders.io/my/ships?token=" + authToken;
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
                User user = gson.fromJson(response.body(), User.class);
                this.currentToken.setUser(user);
            }
            else if (response.statusCode() >= 400 && response.statusCode() < 600) {
                msg = handleErrorReq(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }

    public List<String> viewMarketPlace(String location) {
        List<String> msg = new ArrayList<String>();
        String authToken = this.currentToken.getToken();

        try {
            String uri = "https://api.spacetraders.io/locations/" + location + "/marketplace?token=" + authToken;
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
                Map<String, Object> map = gson.fromJson(response.body(), Map.class);
                String json = gson.toJson(map.get("marketplace"));
                Goods[] goods = gson.fromJson(json, Goods[].class);
                this.goods = Arrays.asList(goods);
            }
            else if (response.statusCode() >= 400 && response.statusCode() < 600) {
                msg = handleErrorReq(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }

    public List<Goods> getGoods() {
        return this.goods;
    }

    public List<String> purchaseGoods(String shipId, String goods, String quantity) {
        List<String> msg = new ArrayList<String>();
        String authToken = this.currentToken.getToken();
        try {
            String uri = "https://api.spacetraders.io/my/purchase-orders?token=" + authToken + "&shipId=" +
                    shipId + "&good=" + goods + "&quantity=" + quantity;
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status code was: " + response.statusCode());
            System.out.println("Response body was:\n" + response.body());
            Gson gson = new Gson();

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                User user = gson.fromJson(response.body(), User.class);
                this.currentToken.setUser(user);
            }
            else if (response.statusCode() >= 400 && response.statusCode() < 600) {
                msg = handleErrorReq(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }

    public List<String> sellGoods(String shipId, String goods, String quantity) {
        List<String> msg = new ArrayList<String>();
        String authToken = this.currentToken.getToken();
        try {
            String uri = "https://api.spacetraders.io/my/sell-orders?token=" + authToken + "&shipId=" +
                    shipId + "&good=" + goods + "&quantity=" + quantity;
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status code was: " + response.statusCode());
            System.out.println("Response body was:\n" + response.body());
            Gson gson = new Gson();

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                User user = gson.fromJson(response.body(), User.class);
                this.currentToken.setUser(user);
            }
            else if (response.statusCode() >= 400 && response.statusCode() < 600) {
                msg = handleErrorReq(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }

    public List<String> getShipInfo(String shipId) {
        List<String> msg = new ArrayList<String>();
        String authToken = this.currentToken.getToken();

        try {
            String uri = "https://api.spacetraders.io/my/ships/" + shipId + "?token=" + authToken;
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
                User user = gson.fromJson(response.body(), User.class);
                this.currentToken.setUser(user);
            }
            else if (response.statusCode() >= 400 && response.statusCode() < 600) {
                msg = handleErrorReq(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }

    public List<String> findNearbyLocations(String type) {
        List<String> msg = new ArrayList<String>();
        String authToken = this.currentToken.getToken();

        try {
            String uri = "https://api.spacetraders.io/systems/OE/locations?token=" + authToken;
            if (type.length() != 0) {
                uri += "&type=" + type;
            }
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
                Map<String, Object> map = gson.fromJson(response.body(), Map.class);
                String json = gson.toJson(map.get("locations"));
                Location[] locations = gson.fromJson(json, Location[].class);
                this.locations = Arrays.asList(locations);
            }
            else if (response.statusCode() >= 400 && response.statusCode() < 600) {
                msg = handleErrorReq(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }

    public List<Location> getLocations() {
        return this.locations;
    }

    public List<String> createFlightPlan(String shipId, String destination) {
        List<String> msg = new ArrayList<String>();
        String authToken = this.currentToken.getToken();
        try {
            String uri = "https://api.spacetraders.io/my/flight-plans?token=" + authToken + "&shipId=" +
                    shipId + "&destination=" + destination;
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status code was: " + response.statusCode());
            System.out.println("Response body was:\n" + response.body());
            Gson gson = new Gson();

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                Map<String, Object> map = gson.fromJson(response.body(), Map.class);

                String json = gson.toJson(map.get("flightPlan"));
                FlightPlan flightPlan = gson.fromJson(json, FlightPlan.class);
                this.flightPlan = flightPlan;
            }
            else if (response.statusCode() >= 400 && response.statusCode() < 600) {
                msg = handleErrorReq(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }

    public FlightPlan getFlightPlan() {
        return this.flightPlan;
    }

    public List<String> viewFlightPlan(String flightId) {
        List<String> msg = new ArrayList<String>();
        String authToken = this.currentToken.getToken();
        try {
            String uri = "https://api.spacetraders.io/my/flight-plans/" + flightId + "?token=" + authToken;
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status code was: " + response.statusCode());
            System.out.println("Response body was:\n" + response.body());
            Gson gson = new Gson();

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                Map<String, Object> map = gson.fromJson(response.body(), Map.class);

                String json = gson.toJson(map.get("flightPlan"));
                FlightPlan flightPlan = gson.fromJson(json, FlightPlan.class);
                this.flightPlan = flightPlan;
            }
            else if (response.statusCode() >= 400 && response.statusCode() < 600) {
                msg = handleErrorReq(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }

    public List<String> checkServerStatus() {
        List<String> msg = new ArrayList<String>();
        try {
            String uri = "https://api.spacetraders.io/game/status";
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status code was: " + response.statusCode());
            System.out.println("Response body was:\n" + response.body());
            Gson gson = new Gson();

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                Status status = gson.fromJson(response.body(), Status.class);
                this.serverStatus = status;
            }
            else if (response.statusCode() >= 400 && response.statusCode() < 600) {
                msg = handleErrorReq(response.body());
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            msg.add("Something went wrong with our request!");
            msg.add(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            msg.add("Something went wrong.");
            msg.add(ignored.getMessage());
        }
        return msg;
    }

    public Status getServerStatus() {
        return this.serverStatus;
    }
}
