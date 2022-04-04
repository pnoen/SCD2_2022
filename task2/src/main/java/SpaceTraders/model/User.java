package SpaceTraders.model;

public class User {
    private int credits;
    private String joinedAt;
    private int shipCount;
    private int structureCount;
    private String username;
    private String[] ships;
    private Loan[] loans;
    private Loan loan;

    public User(int credits, String joinedAt, int shipCount, int structureCount, String username) {
        this.credits = credits;
        this.joinedAt = joinedAt;
        this.shipCount = shipCount;
        this.structureCount = structureCount;
        this.username = username;

        this.ships = new String[0];
        this.loans = new Loan[0];
    }

    public int getCredits() {
        return credits;
    }

    public String getJoinedAt() {
        return joinedAt;
    }

    public int getShipCount() {
        return shipCount;
    }

    public int getStructureCount() {
        return structureCount;
    }

    public String getUsername() {
        return username;
    }

    public Loan getLoan() {
        return loan;
    }

    @Override
    public String toString() {
        return username + " " + credits;
    }
}
