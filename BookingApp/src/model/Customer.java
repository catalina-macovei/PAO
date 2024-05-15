package model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User{
    private List<Booking> bookings = new ArrayList<>();

    public Customer() {}
    public Customer(User user) {
        super(user.getName(), user.getEmail(), user.getPassword(), user.getAccountBalance());
    }

    public Customer(String name, String email, String password, AccountBalance accountBalance, List<Booking> bookings) {
        super(name, email, password, accountBalance);
        this.bookings = bookings;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addBooking (Booking b) {
        bookings.add(b);
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + getName() + '\'' +
                ", accountBalance=" + (getAccountBalance() != null ? getAccountBalance() : 0) +
                ", bookingsNr=" + getBookings().size() +
                '}';
    }

}
