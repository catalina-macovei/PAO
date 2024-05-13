package daoservices;

import dao.PaymentDao;
import model.Payment;

import java.sql.SQLException;

public class PaymentRepositoryService {
    private PaymentDao paymentDao = PaymentDao.getInstance();

    public PaymentRepositoryService() throws SQLException {
    }

    public Payment getPaymentById(int id) throws SQLException {
        Payment payment = paymentDao.read(String.valueOf(id));
        if (payment == null) {
            System.out.println("No Payment found for ID: " + id);
        }
        return payment;
    }
    public void addPayment(Payment payment) throws SQLException {
        paymentDao.add(payment);
    }
    public void removePayment(Payment payment) throws SQLException {
        paymentDao.delete(payment);
        System.out.println("Removed Payment: " + payment);
    }
}
