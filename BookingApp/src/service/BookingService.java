package service;

import daoservices.AccountBalanceRepositoryService;
import daoservices.BookingRepositoryService;
import daoservices.PropertyRepositoryService;
import model.*;
import utils.FileManagement;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.SortedMap;

import static utils.Constants.AUDIT_FILE;

public class BookingService {
    private BookingRepositoryService dbService;
    private PropertyRepositoryService propertyService;
    private PaymentService paymentService;
    private AccountBalanceRepositoryService acountBalanceService;

    public BookingService() throws SQLException {
        // Initialize the dependencies within the constructor
        this.dbService = new BookingRepositoryService();
        this.propertyService = new PropertyRepositoryService();
        this.paymentService = new PaymentService();
        this.acountBalanceService = new AccountBalanceRepositoryService();
    }


    // for create booking we must have a customer first
    public void create(Scanner scanner, Customer customer) throws SQLException {
        Booking booking = setGeneralInfo(scanner, customer);
        try {
            dbService.addBooking(booking);
        } catch (SQLException e) {
            System.out.println("error creating booking!");
        }
        FileManagement.scriereFisierChar(AUDIT_FILE, "created booking for customer: " + customer.getName() + " bookingRegNr="+booking.getRegistrationNr());
    }

    public void read(Scanner scanner) throws SQLException {
        System.out.println("Enter registration number of Booking: ");
        int regNr = scanner.nextInt();
        try {
            Booking booking = dbService.getByRegistrationNr(regNr);
            FileManagement.scriereFisierChar(AUDIT_FILE, "read booking: regNr=" + booking.getRegistrationNr());
            System.out.println("Booking: " + booking);
        } catch (SQLException e ) {
            System.out.println("Error redaing booking!");
        }
    }

    public void delete(Scanner scanner) throws SQLException {
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
        try {
            dbService.removeBooking(booking);
        } catch (SQLException e) {
            System.out.println("error removing booking!");
        }
        FileManagement.scriereFisierChar(AUDIT_FILE, "delete booking: regNr" + booking.getRegistrationNr());
    }

    public void update(Scanner scanner) throws SQLException {
        System.out.println("Enter registration number of Booking: ");
        int regNr = scanner.nextInt();
        Booking booking = dbService.getByRegistrationNr(regNr);

        if (booking != null) {
            Customer customer = booking.getCustomer();
            System.out.println("\nBooking update:");

            System.out.println("Set booking start date (yyyy-MM-dd): ");
            LocalDate startDate = readDate(scanner);
            System.out.println("Set booking end date (yyyy-MM-dd): ");
            LocalDate endDate = readDate(scanner);
            Property property = booking.getProperty();
            double totalBookingAmount = totalAmountBooking(startDate, endDate, property.getPrice());
            Payment payment = booking.getPayment();
            paymentService.update(scanner, payment, totalBookingAmount);
            System.out.println("check payment success: ");
            if (payment.getStatus().equals("success")) {
                System.out.println("now updating booking...");
                booking.setStartDate(startDate);
                booking.setEndDate(endDate);
                updateAccountBalanceOnBooking(scanner, booking);
                try {
                    dbService.updateBooking(booking);
                } catch (SQLException e) {
                    System.out.println("error updating booking!");
                }
                FileManagement.scriereFisierChar(AUDIT_FILE, "update booking: regNr=" + booking.getRegistrationNr());
            }
        } else {
            System.out.println("Couldn't update booking!");
        }
    }

    public List<Booking> getBookingsForCustomer(String name) throws SQLException {
        FileManagement.scriereFisierChar(AUDIT_FILE, "read all bookings for customer: " + name);
        return (List<Booking>) dbService.getBookingsForCustomer(name);
    }

    private void updateAccountBalanceOnBooking(Scanner scanner, Booking booking) throws SQLException {  // the update is for landlord
        Property property = booking.getProperty();
        if (property != null) {
            Landlord landlord = property.getLandlord();
            AccountBalance accountBalance = landlord.getAccountBalance();
            if (accountBalance != null) {
                double amount = accountBalance.getAmount();
                accountBalance.setAmount(amount + booking.getPayment().getAmount());
                acountBalanceService.update(accountBalance);
                landlord.setAccountBalance(accountBalance);
            } else {
                AccountBalance newaccountB = new AccountBalance(booking.getPayment().getAmount());
                Random random = new Random();
                int randomNumber = random.nextInt(10000); // Generates a random number between 0 and 999
                accountBalance.setAccountNr(randomNumber);
                acountBalanceService.createAccountBalance(accountBalance);
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
                Random random = new Random();
                int randomNumber = random.nextInt(100000); // Generates a random number between 0 and 99999
                booking.setRegistrationNr(randomNumber);
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
