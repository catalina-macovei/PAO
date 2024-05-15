package dao;

import daoservices.DatabaseConnection;
import model.Customer;
import model.Customer;
import model.AccountBalance;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao implements DaoInterface<Customer> {

    private static CustomerDao customerDao;
    private Connection connection = DatabaseConnection.getConnection();

    public CustomerDao() throws SQLException {
    }

    public static CustomerDao getInstance() throws SQLException {
        if (customerDao == null) {
            customerDao = new CustomerDao();
        }
        return customerDao;
    }

    public List<Customer> readAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();

        String query = "SELECT * FROM customer"; // Assuming 'customer' is the table name

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = ((Statement) statement).executeQuery(query)) {

            // create customer list
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                //Create a new Customer object and set its properties using setters
                Customer customer = new Customer();
                customer.setName(name);
                customer.setPassword(password);
                customer.setEmail(email);
                //Add the Customer object to the list
                customers.add(customer);
            }
        }
        return customers;
    }

    public Customer read(String name) throws SQLException {
        String sql = "SELECT l.id, l.name, l.password, l.email, a.amount " +
                "FROM customer l " +
                "JOIN account_balance a ON l.accountNr = a.accountNr " +
                "WHERE l.name = ?";
        ResultSet rs = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            rs = statement.executeQuery();

            while (rs.next()) {
                Customer s = new Customer();
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setPassword(rs.getString("password"));
                s.setId(rs.getInt("id"));

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

    public Customer readById(int id) throws SQLException {
        String sql = "SELECT l.id, l.name, l.password, l.email, a.amount " +
                "FROM customer l " +
                "JOIN account_balance a ON l.accountNr = a.accountNr " +
                "WHERE l.id = ?";
        ResultSet rs = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            rs = statement.executeQuery();

            while (rs.next()) {
                Customer s = new Customer();
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setPassword(rs.getString("password"));
                s.setId(id);
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
    public void delete(Customer cust) throws SQLException {
        String sql = "DELETE FROM booking.customer WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cust.getName());
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " rows deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
            throw e; // Rethrow the exception to propagate it to the caller
        }
    }


    @Override
    public void update(Customer cust) throws SQLException {
        String sql = "UPDATE booking.customer a set a.email = ? , a.password = ? where a.name = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, cust.getEmail());
            preparedStatement.setString(2, cust.getPassword());
            preparedStatement.setString(3, cust.getName());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void add(Customer cust) throws SQLException {
        String sql1 = "INSERT INTO booking.account_balance(accountNr, amount) VALUES(?, ?)";
        String sql2 = "INSERT INTO booking.customer(name, password, email, accountNr) VALUES (?, ?, ?, ?)";

        // Start a transaction
        connection.setAutoCommit(false);

        try (
                PreparedStatement statement1 = connection.prepareStatement(sql1);
                PreparedStatement statement2 = connection.prepareStatement(sql2);
        ) {
            // Insert into account_balance table
            AccountBalance account = cust.getAccountBalance();
            statement1.setInt(1, account.getAccountNr());
            statement1.setDouble(2, account.getAmount());
            statement1.executeUpdate();

            // Insert into customer table
            statement2.setString(1, cust.getName());
            statement2.setString(2, cust.getPassword());
            statement2.setString(3, cust.getEmail());
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
