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

    public Landlord read(String name) throws SQLException {
        String sql = "SELECT l.id, l.name, l.password, l.email, a.amount " +
                "FROM landlord l " +
                "JOIN account_balance a ON l.accountNr = a.accountNr " +
                "WHERE l.name = ?";
        ResultSet rs = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
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

                return s;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
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
        String sql1 = "INSERT INTO booking.account_balance(accountNr, amount) VALUES(?, ?)";
        String sql2 = "INSERT INTO booking.landlord(name, password, email, accountNr) VALUES (?, ?, ?, ?)";

        // Start a transaction
        connection.setAutoCommit(false);

        try (
                PreparedStatement statement1 = connection.prepareStatement(sql1);
                PreparedStatement statement2 = connection.prepareStatement(sql2);
        ) {
            // Insert into account_balance table
            AccountBalance account = land.getAccountBalance();
            statement1.setInt(1, account.getAccountNr());
            statement1.setDouble(2, account.getAmount());
            statement1.executeUpdate();

            // Insert into landlord table
            statement2.setString(1, land.getName());
            statement2.setString(2, land.getPassword());
            statement2.setString(3, land.getEmail());
            statement2.setInt(4, account.getAccountNr());
            statement2.executeUpdate();

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
