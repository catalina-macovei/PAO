package model;

public class Payment {
    private static int counter = 0;
    private int id;
    private double amount;
    private String status;

    public Payment(double amount) {
        this.amount = amount;
        this.id = getNextId();
    }

    public Payment() {
    }

    private static int getNextId() {
        return counter++;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}
