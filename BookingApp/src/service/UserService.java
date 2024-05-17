package service;
import daoservices.UserRepositoryService;
import model.*;
import service.*;
import utils.FileManagement;

import java.sql.SQLException;
import java.util.*;

import static utils.Constants.*;
public class UserService {
    private UserRepositoryService dbService;
    private PropertyService propertyService;
    private BookingService bookingService;
    private AccountBalanceService accountBalanceService;

    public UserService() throws SQLException {
        this.dbService = new UserRepositoryService();
        this.propertyService = new PropertyService(); // Initialize PropertyService
        this.bookingService = new BookingService();
        this.accountBalanceService = new AccountBalanceService();
    }

    public void create(Scanner scanner) throws SQLException {
        System.out.println("Enter type of user [customer/landlord]:");
        String typeOfUser = scanner.nextLine().toLowerCase();
        if(!typeOfUserValidation(typeOfUser)) { return; }
        userInit(scanner, typeOfUser);  // initiate function for user creation
    }

    public User read(Scanner scanner) throws SQLException {
        System.out.println("User name:");
        String name = scanner.nextLine();
        User user = null;

        try {
            user = dbService.getCustomerByName(name);
            //if no customer found tehn attempt to get the landlord by name
            if (user == null) {
                user = dbService.getLandlordByName(name);
            }
            FileManagement.scriereFisierChar(AUDIT_FILE, "read user: " + name);
        } catch (SQLException e) {
            System.err.println("An error occurred while reading user: " + e.getMessage());
        }
        System.out.println("read user: " + user);
        return user;
    }

    public void delete(Scanner scanner) throws SQLException {
        System.out.println("name:");
        String name = scanner.nextLine();
        System.out.println("typeOfUser:");
        String typeOfUser = scanner.nextLine();

        if (!typeOfUserValidation(typeOfUser)) {
            System.out.println("Invalid user type.");
            return;
        }
        try {
            dbService.removeUser(typeOfUser, name);
            FileManagement.scriereFisierChar(AUDIT_FILE, "removed user: " + name);
            System.out.println("User successfully deleted.");
        } catch (SQLException e) {
            System.err.println("An error occurred while attempting to delete the user: " + e.getMessage());
        }
    }

    public void update(Scanner scanner) throws SQLException {
        System.out.println("Type old credentials:");
        System.out.println("typeOfUser:");
        String typeOfUser = scanner.nextLine();

        if (!typeOfUserValidation(typeOfUser)) {
            System.out.println("Invalid user type.");
            return;
        }
        System.out.println("name:");
        String name = scanner.nextLine();
        User user = null;
        try {
            user = dbService.getUser(typeOfUser, name);
        } catch (SQLException e) {
            System.err.println("An error occurred while retrieving the user: " + e.getMessage());
        }
        if (user == null) {
            return;
        }
        User userGeneralInfo = setGeneralInfo(name, scanner);
        user.setEmail(userGeneralInfo.getEmail());
        user.setPassword(userGeneralInfo.getPassword());
        try {
            dbService.updateUser(user);
            FileManagement.scriereFisierChar(AUDIT_FILE, "updated user: " + name);
            System.out.println("User successfully updated.");
        } catch (SQLException e) {
            System.err.println("An error occurred while updating the user: " + e.getMessage());
        }
    }

    public boolean typeOfUserValidation(String typeOfUser) {
        if(! typeOfUser.equals(LANDLORD) && !typeOfUser.equals(CUSTOMER)){
            System.out.println("Wrong type");
            return false;
        }
        return true;
    }
    private User setGeneralInfo(String name, Scanner scanner) {
        System.out.println("Setting new information:");
        System.out.println("email:");
        String email = scanner.nextLine();
        System.out.println("password:");
        String password = scanner.nextLine();

        Random random = new Random();
        int randomNumber = random.nextInt(10000); // Generates a random number between 0 and 999
        AccountBalance accountBalance = new AccountBalance(0);
        accountBalance.setAccountNr(randomNumber);

        User user = new User(name, email, password, accountBalance);

        return user;
    }
    private void userInit(Scanner scanner, String typeOfUser) throws SQLException {
        System.out.println("name:");
        String name = scanner.nextLine();

        if (typeOfUser.equals(LANDLORD) && dbService.getLandlordByName(name) != null) {return;}
        if (typeOfUser.equals(CUSTOMER) && dbService.getCustomerByName(name) != null) {return;}

        User user = setGeneralInfo(name, scanner);
        if(typeOfUser.equals(LANDLORD)){
            Landlord landlord = new Landlord(user);
            user = landlord;
        } else {
            Customer customer = new Customer(user);
            user = customer;
        }

       try {
           dbService.addUser(user);
           FileManagement.scriereFisierChar(AUDIT_FILE, "created user: " + name);
        } catch (SQLException e) {
            System.out.println("Could not add the user!");
        }
        System.out.println("Created " + user);
    }

    public void viewUsers(Scanner scanner) throws SQLException {
        System.out.println("View users: 1-landlords || 2-customers");
        System.out.println("Please select an option:");

        int choice = -1;
        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
        } else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear invalid input
            return;
        }

        try {
            switch (choice) {
                case 1:
                    readAllLandlords(dbService.getAllLandlords());
                    FileManagement.scriereFisierChar(AUDIT_FILE, "read all landlords");
                    break;
                case 2:
                    readAllCustomers(dbService.getAllCustomers());
                    FileManagement.scriereFisierChar(AUDIT_FILE, "read all customers" );
                    break;
                default:
                    System.out.println("Invalid option selected.");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while retrieving the users: " + e.getMessage());
            throw e; // Rethrow the exception to ensure the caller is aware of the failure
        }
    }

    private static void readAllLandlords(List<Landlord> landlords) {
        if (landlords.size() > 0) {
            System.out.println("Landlords ranked by properties number: ");
            // sorting the list of landlords using a Comparator
            landlords.sort(Comparator.comparingInt(landlord -> landlord.getProperties().size()));

            for (Landlord land : landlords) {
                System.out.println(land);
            }
        } else {
            System.out.println("No landlords in list!");
        }
    }
    private static void readAllCustomers(List<Customer> customers) {
        if (customers.size() > 0)
        {
            System.out.println("Customers ranked by bookings number: ");
            // Sorting the list of customers using a Comparator
            customers.sort(Comparator.comparingInt(customer -> customer.getBookings().size()));

            for (Customer cust : customers) {
                System.out.println(cust);
            }
        } else {
            System.out.println("No customers in list!");
        }
    }

    public void readCustomerBookings(Scanner scanner) throws SQLException {
        System.out.println("User name:");
        String name = scanner.nextLine();
        Customer user = dbService.getCustomerByName(name);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        List<Booking> bookings = bookingService.getBookingsForCustomer(name);
        user.setBookings(bookings);
        if (bookings.isEmpty()) {
            System.out.println("No bookings found for " + user.getName());
            return;
        }

        System.out.println("Bookings history for " + user.getName() + ":");
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }

    public void manageAccount(Scanner scanner) throws SQLException {
        User user = read(scanner);
        if (user == null)
            return;
        if (!userLogin(scanner, user)) {
            return;
        }
        System.out.println("1.Edit user details || 2.Manage account balance");
        System.out.println("Introduce number of your choice:");
        String option = scanner.nextLine();

        if (option.equals("1")) {
            update(scanner);
        } else if (option.equals("2")) {
            accountBalanceService.manageAccountBalance(scanner, user);
            scanner.nextLine();
        } else {
            System.out.println("WRONG OPTION");
        }
    }

    private static boolean userLogin(Scanner scanner, User user) {
        System.out.println("Introduce your password: ");
        String password  = scanner.nextLine();
        if(!user.getPassword().equals(password)){
            System.out.println("WRONG PASSWORD");
            return false;
        }
        return true;
    }

    public void userRegistration(Scanner scanner) throws SQLException {
        System.out.println("Register:\n");
        create(scanner);
        scanner.nextLine();
    }
    public void propertyRegistration(Scanner scanner) throws SQLException {  /// landlord property registration
        User user = read(scanner);
        Landlord landlord;
        if (user instanceof Landlord) {
            landlord = (Landlord) user;
        } else {
            System.out.println("User is not a Landlord.");
            return;
        }
        System.out.print("Enter the number of properties: ");
        int numOfProperties = scanner.nextInt();
        scanner.nextLine();                              /// consume newline character
        for (int i = 0; i < numOfProperties; i++) {
            propertyService.create(scanner, landlord);
        }
    }
    public void bookingRegistration(Scanner scanner) throws SQLException {
        System.out.println("Booking process: ");
        Object obj = read(scanner); // Read the object from the scanner

        if (!(obj instanceof Customer)) {
            System.out.println("The user is not a customer.");
            return;
        }

        Customer customer = (Customer) obj; // Cast the object to Customer
        if (customer == null)
            return;

        bookingService.create(scanner, customer); // Process the booking with the customer
    }

    public static void main(String[] args) {
        try {
            UserService userService = new UserService();
            Scanner scanner = new Scanner(System.in);
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
