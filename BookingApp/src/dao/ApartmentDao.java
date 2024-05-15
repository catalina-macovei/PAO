package dao;

import daoservices.DatabaseConnection;
import model.*;
import dao.LandlordDao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApartmentDao implements DaoInterface<Apartment> {

    private LandlordDao landlordDao = LandlordDao.getInstance();
    private AddressDao addressDao = AddressDao.getInstance();
    private static ApartmentDao apartmentDao;
    private Connection connection = DatabaseConnection.getConnection();

    public ApartmentDao() throws SQLException {
    }

    public static ApartmentDao getInstance() throws SQLException {
        if (apartmentDao == null) {
            apartmentDao = new ApartmentDao();
        }
        return apartmentDao;
    }

    public List<Property> readAll() throws SQLException {
        List<Property> apartments = new ArrayList<>();

        String query = "SELECT * FROM apartment"; // Assuming 'apartment' is the table name

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = ((Statement) statement).executeQuery(query)) {

            // create apartment list
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                int floorNr = resultSet.getInt("floorNr");
                int landlordFK = resultSet.getInt("landlord");
                int addressFK = resultSet.getInt("address");
                //Create a new Apartment object and set its properties using setters
                Landlord landlord = landlordDao.readById(landlordFK);
                Address address = addressDao.read(String.valueOf(addressFK));

                Apartment apartment = new Apartment();
                apartment.setName(name);
                apartment.setPrice(price);
                apartment.setFloorNr(floorNr);
                apartment.setLandlord(landlord);
                apartment.setAddress(address);
                //Add the Apartment object to the list
                apartments.add((Property) apartment);
            }
        }
        return apartments;
    }

    public Apartment read(String name) throws SQLException {
        String sql = "SELECT l.id, l.name, l.address, l.landlord, l.price, l.floorNr " +
                "FROM apartment l " +
                "WHERE l.name = ?";
        ResultSet rs = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                Double price = rs.getDouble("price");
                int floorNr = rs.getInt("floorNr");
                int landlordFK = rs.getInt("landlord");
                int addressFK = rs.getInt("address");
                //Create a new Apartment object and set its properties using setters
                Landlord landlord = landlordDao.readById(landlordFK);
                Address address = addressDao.read(String.valueOf(addressFK));

                Apartment apartment = new Apartment();
                apartment.setName(name);
                apartment.setPrice(price);
                apartment.setFloorNr(floorNr);
                apartment.setLandlord(landlord);
                apartment.setAddress(address);

                return apartment;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return null;
    }


    @Override
    public void delete(Apartment h) throws SQLException {
        String sql = "DELETE FROM booking.apartment WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, h.getName());
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting apartment: " + e.getMessage());
        }
    }


    @Override
    public void update(Apartment h) throws SQLException {
        String sql = "UPDATE booking.apartment a set a.price = ? , a.floorNr = ? where a.name = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setDouble(1, h.getPrice());
            preparedStatement.setDouble(2, h.getFloorNr());
            preparedStatement.setString(3, h.getName());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void add(Apartment h) throws SQLException {
        String sql = "INSERT INTO booking.apartment(name, address, landlord, price, floorNr) VALUES (?, ?, ?, ?, ?)";

        // Start a transaction
        connection.setAutoCommit(false);

        try (PreparedStatement statement1 = connection.prepareStatement(sql)) {
            statement1.setString(1, h.getName());
            statement1.setInt(2, h.getAddress().getId());
            statement1.setInt(3, h.getLandlord().getId());
            statement1.setDouble(4, h.getPrice());
            statement1.setInt(5, h.getFloorNr());
            statement1.executeUpdate();
            // Commit transaction
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

}
