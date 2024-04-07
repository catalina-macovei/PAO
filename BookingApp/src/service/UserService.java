package service;
import daoservices.UserRepositoryService;
import model.*;
import service.*;
import java.util.*;

import static utils.Constants.*;
public class UserService {
    private UserRepositoryService dbService;
    private PropertyService propertyService;
    private BookingService bookingService;
    private AccountBalanceService accountBalanceService;
    public UserService() {
        this.dbService = new UserRepositoryService();
        this.propertyService = new PropertyService(); // Initialize PropertyService
        this.bookingService = new BookingService();
        this.accountBalanceService = new AccountBalanceService();
    }
    public void create(Scanner scanner) {
        System.out.println("Enter type of user [customer/landlord]:");
        String typeOfUser = scanner.nextLine().toLowerCase();
        if(!typeOfUserValidation(typeOfUser)) { return; }
        userInit(scanner, typeOfUser);  // initiate function for user creation
    }

    public User read(Scanner scanner) { // read having a name
        System.out.println("User name:");
        String name = scanner.nextLine();
        User user = dbService.getCustomerByName(name);
        if (user == null) {
            user = dbService.getLandlordByName(name);
        }
        return user;
    }

    public void delete(Scanner scanner) {
        System.out.println("name:");
        String name = scanner.nextLine();
        System.out.println("typeOfUser:");
        String typeOfUser = scanner.nextLine();
        if(!typeOfUserValidation(typeOfUser)) { return; }
        dbService.removeUser(typeOfUser, name);
    }

    public void update(Scanner scanner) {
        System.out.println("Type old credentials:");
        System.out.println("typeOfUser:");
        String typeOfUser = scanner.nextLine();
        if(!typeOfUserValidation(typeOfUser)) { return; }
        System.out.println("name:");
        String name = scanner.nextLine();
        User user = dbService.getUser(typeOfUser, name);
        if (user == null) { System.out.println("No user found!"); return;}

        User userGeneralInfo = setGeneralInfo(name, scanner);
        System.out.println("name:");
        String name1 = scanner.nextLine();
        user.setName(name1);
        user.setEmail(userGeneralInfo.getEmail());
        user.setPassword(userGeneralInfo.getPassword());
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
        return new User(name, email, password);
    }
    private void userInit(Scanner scanner, String typeOfUser) {
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
        dbService.addUser(user);
        System.out.println("Created " + user);
    }

    public void viewUsers(Scanner scanner) {
        System.out.println("View users: 1-landlords || 2-customers");
        System.out.println("Please select an option:");
        int choice = scanner.nextInt();
        switch(choice) {
            case 1:
                readAllLandlords(dbService.getAllLandlords());
                break;
            case 2:
                readAllCustomers(dbService.getAllCustomers());
                break;
            default:
                System.out.println("Invalid option selected.");
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

    public void readCustomerBookings(Scanner scanner) {
        System.out.println("User name:");
        String name = scanner.nextLine();
        Customer user = dbService.getCustomerByName(name);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        List<Booking> bookings = user.getBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings found for " + user.getName());
            return;
        }

        System.out.println("Bookings history for " + user.getName() + ":");
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }

    public void manageAccount(Scanner scanner) {
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

    public void userRegistration(Scanner scanner) {
        System.out.println("Register:\n");
        create(scanner);
        scanner.nextLine();
    }
    public void propertyRegistration(Scanner scanner) {  /// landlord property registration
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
    public void bookingRegistration(Scanner scanner) {
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

}
