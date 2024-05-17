import java.sql.SQLException;
import java.util.Scanner;

import service.BookingService;
import service.PropertyService;
import service.UserService;

public class Application {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        PropertyService propertyService = new PropertyService();
        UserService userService = new UserService();
        BookingService bookingService = new BookingService();

        while (true) { // looping infinitely until the return command is issued (which exits the main method)
            menu();

            String command = scanner.nextLine().toLowerCase();
            System.out.println("Command received: " + command);
            switch (command) {
                case "1":
                    userService.userRegistration(scanner);
                    break;
                case "2":
                    userService.manageAccount(scanner);
                    break;
                case "3":
                    userService.viewUsers(scanner);
                    scanner.nextLine();
                    break;
                case "4":
                    userService.delete(scanner);
                    break;
                case "5":
                    userService.read(scanner);
                    break;
                case "6":
                    userService.propertyRegistration(scanner);
                    break;
                case "7":
                    propertyService.update(scanner);
                    scanner.nextLine();
                    break;
                case "8":
                    propertyService.delete(scanner);
                    break;
                case "9":
                    propertyService.read(scanner);
                    break;
                case "10":
                    propertyService.viewPropertyList(scanner);
                    scanner.nextLine();
                    break;
                case "11":
                    userService.bookingRegistration(scanner);
                    scanner.nextLine();
                    break;
                case "12":
                    userService.readCustomerBookings(scanner);
                    break;
                case "13":
                    bookingService.delete(scanner);
                    scanner.nextLine();
                    break;
                case "14":
                    bookingService.update(scanner);
                    scanner.nextLine();
                    break;
                case "15":
                    bookingService.read(scanner);
                    scanner.nextLine();
                    break;
                case "quit":
                    System.out.println("Exiting");
                    return;
                default:
                    System.out.println("Wrong command");
            }
        }
    }

    private static void menu() {
        System.out.println("\nAvailable functionalities:");
        System.out.println("1. User registration (create user/account balance)");
        System.out.println("2. Account management (update user/account balance)");
        System.out.println("3. View users");
        System.out.println("4. Delete user");
        System.out.println("5. Read user");
        System.out.println("6. Create property (automatically create address)");
        System.out.println("7. Update property");
        System.out.println("8. Delete property");
        System.out.println("9. Read property");
        System.out.println("10. View properties");
        System.out.println("11. Add booking");
        System.out.println("12. See my bookings");
        System.out.println("13. Delete booking\nHint: See your bookings list to check for registration number.");
        System.out.println("14. Update booking");
        System.out.println("15. Read booking");
        System.out.println("quit");
        System.out.println("\nEnter corresponding functionality number:");
    }
}
