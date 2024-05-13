package dao;

import daoservices.DatabaseConnection;
import model.Landlord;
import model.Landlord;
import model.AccountBalance;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LandlordDao implements DaoInterface<Landlord> {

    private static LandlordDao landlordDao;
    private Connection connection = DatabaseConnection.getConnection();

    public LandlordDao() throws SQLException {
    }

    public static LandlordDao getInstance() throws SQLException {
        if (landlordDao == null) {
            landlordDao = new LandlordDao();
        }
        return landlordDao;
    }

    public List<Landlord> readAll() throws SQLException {
        List<Landlord> landlords = new ArrayList<>();

        String query = "SELECT * FROM landlord"; // Assuming 'landlord' is the table name

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = ((Statement) statement).executeQuery(query)) {

            // create landlord list
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                //Create a new Landlord object and set its properties using setters
                Landlord landlord = new Landlord();
                landlord.setName(name);
                landlord.setPassword(password);
                landlord.setEmail(email);
                //Add the Landlord object to the list
                landlords.add(landlord);
            }
        }
        return landlords;
    }

    @Override
//    public Landlord read (String name) throws SQLException {
//        String sql = "SELECT l.id, l.name, l.password, l.email, a.amount " +
//                "FROM landlord l " +
//                "JOIN account_balance a ON l.accountNr = a.accountNr WHERE l.name = ?";
//        ResultSet rs = null;
//        System.out.println("called");
//        try(PreparedStatement statement = connection.prepareStatement(sql);) {
//            statement.setString(1, name);
//            rs = statement.executeQuery();
//
//            while (rs.next()){
//                Landlord s = new Landlord();
//
//                s.setName(rs.getString("name"));
//                s.setEmail(rs.getString("email"));
//                s.setPassword(rs.getString("password"));
//
//                double accountBalance = rs.getDouble("amount");
//                AccountBalance balance = new AccountBalance();
//                balance.setAmount(accountBalance);
//                s.setAccountBalance(balance);
//
//                System.out.println("s=" + s);
//                return  s;
//            }
//        }finally {
//            if(rs != null) {
//                rs.close();
//            }
//        }
//        return null;
//    }
    public Landlord read(String name) throws SQLException {
        String sql = "SELECT l.id, l.name, l.password, l.email, a.amount " +
                "FROM landlord l " +
                "JOIN account_balance a ON l.accountNr = a.accountNr " +
                "WHERE l.name = ?";
        ResultSet rs = null;
        System.out.println("called with name: " + name);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            System.out.println("Executing SQL query: " + sql);
            rs = statement.executeQuery();

            while (rs.next()) {
                Landlord s = new Landlord();
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setPassword(rs.getString("password"));

                double accountBalance = rs.getDouble("amount");
                AccountBalance balance = new AccountBalance();
                balance.setAmount(accountBalance);
                s.setAccountBalance(balance);

                System.out.println("Found Landlord: " + s);
                return s;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        System.out.println("No landlord found with name: " + name);
        return null;
    }


    @Override
    public void delete(Landlord land) throws SQLException {
        String sql = "DELETE FROM booking.landlord WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, land.getName());
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting landlord: " + e.getMessage());
            throw e; // Rethrow the exception to propagate it to the caller
        }
    }


    @Override
    public void update(Landlord land) throws SQLException {
        String sql = "UPDATE booking.landlord a set a.email = ? , a.password = ? where a.name = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, land.getEmail());
            preparedStatement.setString(2, land.getPassword());
            preparedStatement.setString(3, land.getName());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void add(Landlord land) throws SQLException {
        String sql = "INSERT INTO booking.landlord(name, password, email) VALUES (?, ?, ?);";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, land.getName());
            statement.setString(2, land.getPassword());
            statement.setString(3, land.getEmail());
            statement.executeUpdate();
        }
    }

}
