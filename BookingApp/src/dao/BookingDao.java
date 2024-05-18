package dao;

import daoservices.DatabaseConnection;
import model.*;
import dao.LandlordDao;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Date.valueOf;

public class BookingDao implements DaoInterface<Booking> {

    private HouseDao houseDao = HouseDao.getInstance();
    private PaymentDao paymentDao = PaymentDao.getInstance();
    private ApartmentDao apartmentDao = ApartmentDao.getInstance();
    private CustomerDao customerDao = CustomerDao.getInstance();
    private LandlordDao landlordDao = LandlordDao.getInstance();
    private static BookingDao bookingDao;
    private Connection connection = DatabaseConnection.getConnection();

    public BookingDao() throws SQLException {
    }

    public static BookingDao getInstance() throws SQLException {
        if (bookingDao == null) {
            bookingDao = new BookingDao();
        }
        return bookingDao;
    }

    public List<Booking> readAll() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM booking";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int customerFK = resultSet.getInt("customer");
                int houseFK = resultSet.getInt("house");
                int apartmentFK = resultSet.getInt("apartment");
                int paymentFK = resultSet.getInt("payment");
                LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                LocalDate endDate = resultSet.getDate("end_date").toLocalDate();
                // Create a new Booking object and set its properties
                Customer customer = customerDao.readById(customerFK);
                Payment payment = paymentDao.read(String.valueOf(paymentFK));
                // Determine whether to use house or apartment
                Property property = null;
                if (houseFK != 0) {
                    property = houseDao.readById(houseFK);
                } else if (apartmentFK != 0) {
                    property = apartmentDao.readById(apartmentFK);
                }
                Booking booking = new Booking();
                booking.setRegistrationNr(id);
                booking.setCustomer(customer);
                booking.setProperty(property);
                booking.setPayment(payment);
                booking.setStartDate(startDate);
                booking.setEndDate(endDate);

                // Add the Booking object to the list
                bookings.add(booking);
            }
        }
        return bookings;
    }

    @Override
    public Booking read(String id) throws SQLException {
        String sql = "SELECT l.id, l.customer, l.payment, l.house, l.apartment, l.start_date, l.end_date " +
                "FROM booking l " +
                "WHERE l.id = ?";
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(id));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int customerFK = resultSet.getInt("customer");
                int houseFK = resultSet.getInt("house");
                int apartmentFK = resultSet.getInt("apartment");
                int paymentFK = resultSet.getInt("payment");
                LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                LocalDate endDate = resultSet.getDate("end_date").toLocalDate();
                // Create a new Booking object and set its properties
                Customer customer = customerDao.readById(customerFK);
                Payment payment = paymentDao.read(String.valueOf(paymentFK));
                // Determine whether to use house or apartment
                Property property = null;
                if (houseFK != 0) {
                    property = houseDao.readById(houseFK);
                } else if (apartmentFK != 0) {
                    property = apartmentDao.readById(apartmentFK);
                }
                Booking booking = new Booking();
                booking.setRegistrationNr(Integer.parseInt(id));
                booking.setCustomer(customer);
                booking.setProperty(property);
                booking.setPayment(payment);
                booking.setStartDate(startDate);
                booking.setEndDate(endDate);
                return booking;
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return null;
    }


    @Override
    public void delete(Booking h) throws SQLException {
        String sql = "DELETE FROM booking.booking WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, h.getRegistrationNr());
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting booking: " + e.getMessage());
        }
    }

    @Override
    public void update(Booking h) throws SQLException {
        String sql = "UPDATE booking.booking set start_date = ? , end_date = ? where id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setDate(1, valueOf(h.getStartDate()));
            preparedStatement.setDate(2, valueOf(h.getEndDate()));
            preparedStatement.setInt(3, h.getRegistrationNr());
            System.out.println("booking updated!");
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void add(Booking h) throws SQLException {
        String sql = "INSERT INTO booking.booking(id, customer, house, apartment, start_date, end_date, payment) VALUES (?, ?, ?, ?, ?, ?, ?)";
        // Start a transaction
        connection.setAutoCommit(false);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set common fields
            statement.setInt(1, h.getRegistrationNr());
            statement.setInt(2, h.getCustomer().getId());
            Property property = h.getProperty();
            if (property instanceof House) {
                statement.setInt(3, property.getId()); // Set house id
                statement.setNull(4, java.sql.Types.INTEGER); // Set apartment to NULL
            } else if (property instanceof Apartment) {
                statement.setNull(3, java.sql.Types.INTEGER); // Set house to NULL
                statement.setInt(4, property.getId()); // Set apartment id
            }
            statement.setDate(5, valueOf(h.getStartDate()));
            statement.setDate(6, valueOf(h.getEndDate()));
            statement.setInt(7, h.getPayment().getId());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            // Roll back the transaction if any error occurs
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            // Restore auto-commit mode
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Booking> getBookingsForCustomer(String name) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        int customerId = customerDao.read(name).getId();

        String sql = "SELECT customer, id, house, apartment, payment, start_date, end_date " +
                "FROM booking.booking " +
                "WHERE customer = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int customerFK = resultSet.getInt("customer");
                int houseFK = resultSet.getInt("house");
                int apartmentFK = resultSet.getInt("apartment");
                int paymentFK = resultSet.getInt("payment");
                LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                LocalDate endDate = resultSet.getDate("end_date").toLocalDate();

                Customer customer = customerDao.readById(customerFK);
                Payment payment = paymentDao.read(String.valueOf(paymentFK));
                // Determine whether to use house or apartment
                Property property = null;
                if (houseFK != 0) {
                    property = houseDao.readById(houseFK);
                } else if (apartmentFK != 0) {
                    property = apartmentDao.readById(apartmentFK);
                }
                Booking booking = new Booking();
                booking.setRegistrationNr(id);
                booking.setCustomer(customer);
                booking.setProperty(property);
                booking.setPayment(payment);
                booking.setStartDate(startDate);
                booking.setEndDate(endDate);
                bookings.add(booking);
            }
        }
        catch (SQLException e) {
            System.out.println("Couldn't fetch customer bookings!");
        }
        return bookings;
    }

}
