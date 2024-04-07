package model;

public class AccountBalance {
    private static int counter = 0;
    private int accountNr;
    private double amount;

    public AccountBalance(double amount) {
        this.amount = amount;
        this.accountNr = getNextId();
    }

    private static int getNextId() {
        return counter++;
    }
    public int getAccountNr() {
        return accountNr;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return amount + "\n";
    }
}
