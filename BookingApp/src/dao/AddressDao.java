package dao;

import daoservices.DatabaseConnection;
import model.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDao implements DaoInterface <Address>{
    private static AddressDao addressDao;
    private Connection connection = DatabaseConnection.getConnection();

    public AddressDao() throws SQLException {
    }
    public static AddressDao getInstance() throws SQLException {
        if(addressDao == null){
            addressDao = new AddressDao();
        }
        return addressDao;
    }

    @Override
    public void add(Address address) throws SQLException {
        String sql = "INSERT INTO booking.address( country, city, street, number, id) VALUES (?, ?, ?, ?, ?);";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1, address.getCountry());
            statement.setString(2, address.getCity());
            statement.setString(3, address.getStreet());
            statement.setString(4, address.getNumber());
            statement.setInt(5, address.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public Address read(String id) throws SQLException {
        String sql = "SELECT * FROM booking.address a WHERE a.id = ?";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, id);
            rs = statement.executeQuery();

            while (rs.next()){
                Address s = new Address();

                s.setId(rs.getInt("id"));
                s.setCountry(rs.getString("country"));
                s.setCity(rs.getString("city"));
                s.setStreet(rs.getString("street"));
                s.setNumber(rs.getString("number"));
                return  s;
            }
        }finally {
            if(rs != null) {
                rs.close();
            }
        }
        return null; // address with given ID is not found
    }

    @Override
    public void delete(Address address) throws SQLException {
        String sql = "DELETE FROM booking.address a WHERE a.id = ? ";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, address.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Address address) throws SQLException {
        String sql = "UPDATE booking.address a set a.country = ? , a.city = ? , a.street = ? , a.number = ? where a.id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, address.getCountry());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setString(4, address.getNumber());
            preparedStatement.setInt(5, address.getId());

            preparedStatement.executeUpdate();
        }
    }

}
