package SpaceTraders.model;

public class Token {
    private String token;
    private User user;

    public String getToken() {
        return this.token;
    }

    public User getUser() {
        return this.user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return token + " " + user.toString();
    }
}
