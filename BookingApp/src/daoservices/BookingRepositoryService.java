package daoservices;

import dao.BookingDao;
import model.Booking;

public class BookingRepositoryService {
    private BookingDao bookingDao;

    public BookingRepositoryService() {
        this.bookingDao = new BookingDao(); // Initialize your Booking DAO here
    }

    public Booking getByRegistrationNr(int nr) {
        Booking booking = bookingDao.read(nr);
        if (booking == null) {
            System.out.println("Booking with registration number " + nr + " not found.");
        }
        return booking;
    }

    public void addBooking(Booking booking) {
        bookingDao.create(booking);
    }

    public void removeBooking(Booking booking) {
        bookingDao.delete(booking);
        System.out.println("Removed Booking: " + booking);
    }
}
