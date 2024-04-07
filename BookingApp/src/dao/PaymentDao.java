package dao;

import model.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentDao {
    private static List<Payment> payments = new ArrayList<>();

    public Payment read(int id) {
        for (Payment payment : payments) {
            if (payment.getId() == id) {
                return payment;
            }
        }
        return null; // payment with given ID is not found
    }

    public void create(Payment payment) {
        payments.add(payment);
    }

    public void delete(Payment payment) {
        payments.remove(payment);
    }

}
