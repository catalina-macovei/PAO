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

    public void update(Scanner scanner) throws SQLException {
        System.out.println("Enter payment ID to update:");
        int id = scanner.nextInt();
        Payment existingPayment = dbService.getPaymentById(id);
        if (existingPayment != null) {
            System.out.println("Enter new payment amount:");
            double amount = scanner.nextDouble();
            existingPayment.setAmount(amount);
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

    public static void main(String[] args) {
        try {
            PaymentService paymentService = new PaymentService();
            Scanner scanner = new Scanner(System.in);

            // Test create
            System.out.println("Testing create operation...");
            System.out.println("Enter requested booking payment:");
            double requestedBookingPayment = scanner.nextDouble();
            Payment createdPayment = paymentService.create(scanner, requestedBookingPayment);
            System.out.println("Payment created: " + createdPayment);

            // Test read
            System.out.println("Testing read operation...");
            paymentService.read(scanner);

            // Test update
            System.out.println("Testing update operation...");
            paymentService.update(scanner);

            // Test delete
            System.out.println("Testing delete operation...");
            paymentService.delete(scanner);

            // Close the scanner to prevent resource leak
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
