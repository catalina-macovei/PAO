package dao;

import model.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingDao {
    private static List<Booking> bookings = new ArrayList<>();

    public void create(Booking booking) {
        bookings.add(booking);
    }

    public Booking read(int nr) {
        if(!bookings.isEmpty()){
            for(Booking c : bookings){
                if(c.getRegistrationNr() == nr){
                    return c;
                }
            }
        }
        return null;
    }

    public void delete(Booking booking) {
        bookings.remove(booking);
    }

}
