package model;

import java.time.LocalDate;
import java.util.Date;

public class Booking {
    private static int counter = 0;
    private int registrationNr;
    private Customer customer;
    private Property property;
    private LocalDate startDate;
    private LocalDate endDate;
    private Payment payment;

    public Booking () {}
    public Booking(Customer customer, Property property, LocalDate startDate, LocalDate endDate, Payment payment) {
        this.customer = customer;
        this.property = property;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payment = payment;
        this.registrationNr = counter++;
        customer.addBooking(this);
    }

    public int getRegistrationNr() {
        return registrationNr;
    }

    public void setRegistrationNr(int registrationNr) {
        this.registrationNr = registrationNr;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.addBooking(this);
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "Reg. number=" + getRegistrationNr() + '\'' +
                ", customerName='" + getCustomer().getName() + '\'' +
                ", propertyPricePerMonth=" + getProperty().getPrice() +
                ", landlord='" + getProperty().getLandlord().getName() + '\'' +
                ", startDate=" + getStartDate() +
                ", endDate=" + getEndDate() +
                ", paymentAmount=" + getPayment().getAmount() +
                '}';
    }
}
