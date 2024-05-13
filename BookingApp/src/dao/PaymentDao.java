package dao;

import daoservices.DatabaseConnection;
import model.Payment;
import model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao implements DaoInterface<Payment> {
    
    private static PaymentDao paymentDao;
    private Connection connection = DatabaseConnection.getConnection();

    public PaymentDao() throws SQLException {
    }
    
    public static PaymentDao getInstance() throws SQLException {
        if(paymentDao == null){
            paymentDao = new PaymentDao();
        }
        return paymentDao;
    }

    @Override
    public void add(Payment payment) throws SQLException {
        String sql = "INSERT INTO booking.payment(amount, status) VALUES (? , ?);";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setDouble(1, payment.getAmount());
            statement.setString(2, payment.getStatus());
            statement.executeUpdate();
        }
    }


    @Override
    public Payment read(String id) throws SQLException {
        String sql = "SELECT * FROM booking.payment a WHERE a.id = ?";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, id);
            rs = statement.executeQuery();

            while (rs.next()){
                Payment s = new Payment();
                s.setStatus(rs.getString("status"));
                s.setAmount(rs.getDouble("amount"));
                return  s;
            }
        }finally {
            if(rs != null) {
                rs.close();
            }
        }
        return null;
    }


    @Override
    public void delete(Payment acc) throws SQLException {
        String sql = "DELETE FROM booking.payment a WHERE a.id = ? ";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, acc.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Payment acc) throws SQLException {
        String sql = "UPDATE booking.payment a set a.amount = ? , a.status = ?  where a.id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setDouble(1, acc.getAmount());
            preparedStatement.setString(2, acc.getStatus());
            preparedStatement.setInt(3, acc.getId());

            preparedStatement.executeUpdate();
        }
    }
}
