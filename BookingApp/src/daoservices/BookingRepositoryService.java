package daoservices;

import dao.BookingDao;
import model.Booking;
import model.Customer;

import java.sql.SQLException;
import java.util.List;

public class BookingRepositoryService {
    private BookingDao bookingDao;

    public BookingRepositoryService() throws SQLException {
        this.bookingDao = new BookingDao(); // Initialize your Booking DAO here
    }

    public Booking getByRegistrationNr(int nr) throws SQLException {
        Booking booking = bookingDao.read(String.valueOf(nr));
        if (booking == null) {
            System.out.println("Booking with registration number " + nr + " not found.");
        }
        return booking;
    }

    public List<Booking> getBookingsForCustomer(String name) throws SQLException {
        return bookingDao.getBookingsForCustomer(name);
    }

    public void addBooking(Booking booking) throws SQLException {
        bookingDao.add(booking);
    }

    public void updateBooking(Booking booking) throws SQLException {
        bookingDao.update(booking);
    }


    public void removeBooking(Booking booking) throws SQLException {
        bookingDao.delete(booking);
        System.out.println("Removed Booking: " + booking);
    }
}
