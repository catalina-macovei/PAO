package dao;

import daoservices.DatabaseConnection;
import model.*;
import dao.LandlordDao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HouseDao implements DaoInterface<House> {

    private LandlordDao landlordDao = LandlordDao.getInstance();
    private AddressDao addressDao = AddressDao.getInstance();
    private static HouseDao houseDao;
    private Connection connection = DatabaseConnection.getConnection();

    public HouseDao() throws SQLException {
    }

    public static HouseDao getInstance() throws SQLException {
        if (houseDao == null) {
            houseDao = new HouseDao();
        }
        return houseDao;
    }

    public List<Property> readAll() throws SQLException {
        List<Property> houses = new ArrayList<>();

        String query = "SELECT * FROM house"; // Assuming 'house' is the table name

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = ((Statement) statement).executeQuery(query)) {

            // create house list
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                Double yardSize = resultSet.getDouble("yardSize");
                int landlordFK = resultSet.getInt("landlord");
                int addressFK = resultSet.getInt("address");
                //Create a new House object and set its properties using setters
                Landlord landlord = landlordDao.readById(landlordFK);
                Address address = addressDao.read(String.valueOf(addressFK));

                House house = new House();
                house.setName(name);
                house.setPrice(price);
                house.setYardsize(yardSize);
                house.setLandlord(landlord);
                house.setAddress(address);
                //Add the House object to the list
                houses.add((Property) house);
            }
        }
        return houses;
    }

    public House read(String name) throws SQLException {
        String sql = "SELECT l.id, l.name, l.address, l.landlord, l.price, l.yardSize " +
                "FROM house l " +
                "WHERE l.name = ?";
        ResultSet rs = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                Double price = rs.getDouble("price");
                Double yardSize = rs.getDouble("yardSize");
                int landlordFK = rs.getInt("landlord");
                int addressFK = rs.getInt("address");
                //Create a new House object and set its properties using setters
                Landlord landlord = landlordDao.readById(landlordFK);
                Address address = addressDao.read(String.valueOf(addressFK));

                House house = new House();
                house.setName(name);
                house.setPrice(price);
                house.setYardsize(yardSize);
                house.setLandlord(landlord);
                house.setAddress(address);

                return house;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return null;
    }


    @Override
    public void delete(House h) throws SQLException {
        String sql = "DELETE FROM booking.house WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, h.getName());
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting house: " + e.getMessage());
        }
    }


    @Override
    public void update(House h) throws SQLException {
        String sql = "UPDATE booking.house a set a.price = ? , a.yardsize = ? where a.name = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setDouble(1, h.getPrice());
            preparedStatement.setDouble(2, h.getYardsize());
            preparedStatement.setString(3, h.getName());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void add(House h) throws SQLException {
        String sql = "INSERT INTO booking.house(name, address, landlord, price, yardSize) VALUES (?, ?, ?, ?, ?)";

        // Start a transaction
        connection.setAutoCommit(false);

        try (PreparedStatement statement1 = connection.prepareStatement(sql)) {
            statement1.setString(1, h.getName());
            statement1.setInt(2, h.getAddress().getId());
            statement1.setInt(3, h.getLandlord().getId());
            statement1.setDouble(4, h.getPrice());
            statement1.setDouble(5, h.getYardsize());
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
