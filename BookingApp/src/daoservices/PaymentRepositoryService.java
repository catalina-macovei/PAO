package daoservices;

import dao.PaymentDao;
import model.Payment;

public class PaymentRepositoryService {
    private PaymentDao paymentDao;

    public PaymentRepositoryService() {
        this.paymentDao = new PaymentDao(); // Initialize your Payment DAO here
    }

    public Payment getPaymentById(int id) {
        Payment payment = paymentDao.read(id);
        if (payment == null) {
            System.out.println("No Payment found for ID: " + id);
        }
        return payment;
    }
    public void addPayment(Payment payment) {
        paymentDao.create(payment);
    }
    public void removePayment(Payment payment) {
        paymentDao.delete(payment);
        System.out.println("Removed Payment: " + payment);
    }
}
