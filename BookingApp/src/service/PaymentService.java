package service;

import daoservices.PaymentRepositoryService;
import model.*;

import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;
public class PaymentService {
    private PaymentRepositoryService dbService;
    public PaymentService () throws SQLException { this.dbService = new PaymentRepositoryService(); }

    public Payment create(Scanner scanner, double requestedBookingPayment) throws SQLException { // maybe i need the option to pay from account balance, but maybe i'll do it in application
        Payment payment = processBookingPayment(scanner, requestedBookingPayment);
        payment.setStatus("success");
        System.out.println("payment amount="+payment+" id="+payment.getId());
        dbService.addPayment(payment);
        return payment;
    }

    public void read(Scanner scanner) throws SQLException {
        System.out.println("Enter payment ID:");
        int id = scanner.nextInt();
        dbService.getPaymentById(id);
    }

    public void delete(Scanner scanner) throws SQLException {
        System.out.println("Enter payment ID to delete:");
        int id = scanner.nextInt();
        Payment p = dbService.getPaymentById(id);
        dbService.removePayment(p);
    }

    public void update(Scanner scanner, Payment existingPayment, double amount) throws SQLException {
        System.out.println("You have to pay " + amount);

        if (existingPayment != null) {
            double amountPaid;
            boolean isValidAmount = false;

            do {
                System.out.println("Enter amount:");
                amountPaid = scanner.nextDouble();

                if (amountPaid >= amount) {
                    isValidAmount = true;
                } else {
                    System.out.println("Invalid amount. Please enter an amount greater than or equal to " + amount);
                }
            } while (!isValidAmount);

            existingPayment.setAmount(amountPaid);
            dbService.updatePayment(existingPayment);
        }
    }

    private Payment processBookingPayment(Scanner scanner, double totalBookingAmount) throws SQLException {
        System.out.println("You have to pay: " + totalBookingAmount + "\nIntroduce the sum:");
        double amount = scanner.nextDouble();
        Payment payment = new Payment(amount);
        Random random = new Random();
        int randomNumber = random.nextInt(100000); // Generates a random number between 0 and 99999
        payment.setId(randomNumber);

        if (amount >= totalBookingAmount) {
            payment.setStatus("success");
            System.out.println("Payment success!");
        } else {
            payment.setStatus("failed");
            System.out.println("Payment failed!");
        }
        return payment;
    }
}
