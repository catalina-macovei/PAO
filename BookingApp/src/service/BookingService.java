package service;
import daoservices.BookingRepositoryService;
import daoservices.PropertyRepositoryService;
import model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
public class BookingService {
    private BookingRepositoryService dbService;
    private PropertyRepositoryService propertyService;
    private PaymentService paymentService;

    public BookingService () throws SQLException {
        this.dbService = new BookingRepositoryService();
        this.propertyService = new PropertyRepositoryService();
        this.paymentService = new PaymentService();
    }

    // for create booking we must have a customer first
    public void create (Scanner scanner, Customer customer) throws SQLException {
        Booking booking = setGeneralInfo(scanner, customer);
        dbService.addBooking(booking);
    }

    public void read (Scanner scanner) {
        System.out.println("Enter registration number of Booking: ");
        int regNr = scanner.nextInt();
        Booking booking = dbService.getByRegistrationNr(regNr);
        System.out.println("Booking: " + booking);
    }

    public void delete (Scanner scanner) {
        System.out.println("Enter registration number of Booking: ");
        int regNr = scanner.nextInt();
        Booking booking = dbService.getByRegistrationNr(regNr);

        if (booking == null) {
            return; // Exit
        }
        ///remove booking from customer's bookings list
        Customer customer = booking.getCustomer();
        if (customer != null) {
            customer.getBookings().remove(booking);
        }
        dbService.removeBooking(booking);
    }

    public void update (Scanner scanner) throws SQLException {
        System.out.println("Enter registration number of Booking: ");
        int regNr = scanner.nextInt();
        Booking booking = dbService.getByRegistrationNr(regNr);

        if(booking != null) {
            Customer customer = booking.getCustomer();
            System.out.println("\nBooking update:");
            booking = setGeneralInfo(scanner, customer);
        }
    }

    private void updateAccountBalanceOnBooking(Scanner scanner, Booking booking) {  // the update is for landlord
        Property property = booking.getProperty();
        if (property != null) {
            Landlord landlord = property.getLandlord();
            AccountBalance accountBalance =  landlord.getAccountBalance();
            if (accountBalance != null) {
                double amount = accountBalance.getAmount();
                accountBalance.setAmount(amount + booking.getPayment().getAmount());
                landlord.setAccountBalance(accountBalance);
            } else {
                AccountBalance newaccountB = new AccountBalance(booking.getPayment().getAmount());
                landlord.setAccountBalance(newaccountB);
            }
        }
    }
    private Booking setGeneralInfo(Scanner scanner, Customer customer) throws SQLException {
        scanner.nextLine();
        System.out.println("Enter property name: ");
        String propName = scanner.nextLine();
        System.out.println("Enter property type: ");
        String propType = scanner.nextLine();
        Property property = propertyService.getProperty(propType, propName);

        if (property != null) {
            System.out.println("Set booking start date (yyyy-MM-dd): ");
            LocalDate startDate = readDate(scanner);
            System.out.println("Set booking end date (yyyy-MM-dd): ");
            LocalDate endDate = readDate(scanner);
            double totalBookingAmount = totalAmountBooking(startDate, endDate, property.getPrice());
            Payment payment = paymentService.create(scanner, totalBookingAmount);

            if (payment.getStatus().equals("success")) {
                Booking booking = new Booking(customer, property, startDate, endDate, payment);
                updateAccountBalanceOnBooking(scanner, booking);
                return booking;
            }
        }
        System.out.println("Maybe you try next time!");
        return null;
    }

    private static LocalDate readDate(Scanner scanner) {
        LocalDate date = null;
        boolean validDate = false;

        while (!validDate) {
            try {
                String input = scanner.nextLine();
                date = LocalDate.parse(input);

                if (date.isBefore(LocalDate.now())) {
                    System.out.println("Error: Booking date cannot be in the past.");
                } else {
                    validDate = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format. Please enter date in yyyy-MM-dd format.");
            }
        }

        return date;
    }
    private long calculateDays(LocalDate startDate, LocalDate endDate) {
        return endDate.toEpochDay() - startDate.toEpochDay();
    }

    // total sum for an indicated period
    // price is standard for a month
    private double totalAmountBooking(LocalDate startDate, LocalDate endDate, double pricePerMonth) {
        // Calculate the number of days in the booking period
        long numOfDays = calculateDays(startDate, endDate);

        //nr of days in a month
        int daysInMonth = YearMonth.from(startDate).lengthOfMonth();

        ///daily rate from the monthly price
        double dailyRate = pricePerMonth / daysInMonth;

        //total amount for the booking period
        double totalAmount = Math.round(numOfDays * dailyRate);

        return totalAmount;
    }


}
