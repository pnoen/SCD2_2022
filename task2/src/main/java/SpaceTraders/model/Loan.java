package SpaceTraders.model;

import com.google.gson.annotations.SerializedName;

public class Loan {
    private String type;

    @SerializedName(value = "amount", alternate = "repaymentAmount")
    private int amount;
    private int rate;
    private int termInDays;
    private boolean collateralRequired;
    private String due;
    private String id;
    private String status;

    public String getType() {
        return this.type;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getRate() {
        return this.rate;
    }

    public int getTermInDays() {
        return this.termInDays;
    }

    public boolean isCollateralRequired() {
        return this.collateralRequired;
    }

    @Override
    public String toString() {
        return this.type + " " + amount;
    }
}
