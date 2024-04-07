package service;

import daoservices.PaymentRepositoryService;
import model.*;

import java.util.Scanner;
public class PaymentService {
    private PaymentRepositoryService dbService;
    public PaymentService () { this.dbService = new PaymentRepositoryService(); }

    public Payment create(Scanner scanner, double requestedBookingPayment) { // maybe i need the option to pay from account balance, but maybe i'll do it in application
        Payment payment = processBookingPayment(scanner, requestedBookingPayment);
        dbService.addPayment(payment);
        return payment;
    }

    public void read(Scanner scanner) {
        System.out.println("Enter payment ID:");
        int id = scanner.nextInt();
        dbService.getPaymentById(id);
    }

    public void delete(Scanner scanner) {
        System.out.println("Enter payment ID to delete:");
        int id = scanner.nextInt();
        dbService.removePayment(dbService.getPaymentById(id));
    }

    public void update(Scanner scanner) {
        System.out.println("Enter payment ID to update:");
        int id = scanner.nextInt();
        Payment existingPayment = dbService.getPaymentById(id);
        if (existingPayment != null) {
            System.out.println("Enter new payment amount:");
            double amount = scanner.nextDouble();
            existingPayment.setAmount(amount);
        }
    }
    private Payment processBookingPayment(Scanner scanner, double totalBookingAmount)
    {
        System.out.println("You have to pay: " + totalBookingAmount + "\nIntroduce the sum:");
        double amount = scanner.nextDouble();
        Payment payment = new Payment(amount);

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
