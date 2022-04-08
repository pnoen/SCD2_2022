package SpaceTraders.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int credits;
    private String joinedAt;
    private int shipCount;
    private int structureCount;
    private String username;
    private List<Ship> ships;
    private List<Loan> loans;
    private Loan loan;
    private Ship ship;
    private Order order;

    public User(int credits, String joinedAt, int shipCount, int structureCount, String username) {
        this.credits = credits;
        this.joinedAt = joinedAt;
        this.shipCount = shipCount;
        this.structureCount = structureCount;
        this.username = username;

        this.ships = new ArrayList<Ship>();
        this.loans = new ArrayList<Loan>();
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

    public List<Loan> getLoans() {
        return loans;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public Ship getShip() {
        return ship;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return username + " " + credits;
    }
}
