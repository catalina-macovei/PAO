package dao;
import daoservices.DatabaseConnection;
import model.*;
import model.AccountBalance;
import model.AccountBalance;
import model.AccountBalance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class AccountBalanceDao implements DaoInterface<AccountBalance>{

    private static AccountBalanceDao accountBalanceDao;
    private Connection connection = DatabaseConnection.getConnection();

    public AccountBalanceDao() throws SQLException {
    }

    public static AccountBalanceDao getInstance() throws SQLException {
        if(accountBalanceDao == null){
            accountBalanceDao = new AccountBalanceDao();
        }
        return accountBalanceDao;
    }

    @Override
    public void add(AccountBalance accountBalance) throws SQLException {
        String sql = "INSERT INTO booking.account_balance(amount) VALUES (?);";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setDouble(1, accountBalance.getAmount());
            statement.executeUpdate();
        }
    }


    @Override
    public AccountBalance read(String id) throws SQLException {
        String sql = "SELECT * FROM booking.account_balance a WHERE a.accountNr = ?";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, id);
            rs = statement.executeQuery();

            while (rs.next()){
                AccountBalance s = new AccountBalance();
                s.setAccountNr(rs.getInt("accountNr"));
                s.setAmount(rs.getDouble("amount"));
                return  s;
            }
        }finally {
            if(rs != null) {
                rs.close();
            }
        }
        return null; // acc with given ID is not found
    }


    @Override
    public void delete(AccountBalance acc) throws SQLException {
        String sql = "DELETE FROM booking.account_balance a WHERE a.accountNr = ? ";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, acc.getAccountNr());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(AccountBalance acc) throws SQLException {
        String sql = "UPDATE booking.account_balance a set a.amount = ? where a.accountNr = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setDouble(1, acc.getAmount());
            preparedStatement.setInt(2, acc.getAccountNr());

            preparedStatement.executeUpdate();
        }
    }

    public int getAccountId(User user) throws SQLException {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        String query = null;
        if (user instanceof Customer) {
            query = "SELECT accountNr FROM customer WHERE id = ?";
        } else if (user instanceof Landlord) {
            query = "SELECT accountNr FROM landlord WHERE id = ?";
        } else {
            throw new IllegalStateException("Unknown user type: " + user.getClass().getSimpleName());
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, user.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("accountNr");
                } else {
                    throw new IllegalStateException("Account number not found for user ID: " + user.getId());
                }
            }
        }
    }

}
