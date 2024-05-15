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

        while (true) { // looping infinit pana apare comanda return (care iese din metoda main)
            menu();

            String command = scanner.nextLine().toLowerCase();
           // scanner.nextLine(); // consume next line character
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
                    userService.propertyRegistration(scanner);
                    break;
                case "6":
                    propertyService.update(scanner);
                    scanner.nextLine();
                    break;
                case "7":
                    propertyService.delete(scanner);
                    break;
                case "8":
                    propertyService.viewPropertyList(scanner);
                    scanner.nextLine();
                    break;
                case "9":
                    userService.bookingRegistration(scanner);
                    scanner.nextLine();
                    break;
                case "10":
                    userService.readCustomerBookings(scanner);
                    break;
                case "11":
                    bookingService.delete(scanner);
                    scanner.nextLine();
                    break;
                case "12":
                    bookingService.update(scanner);
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
        System.out.println("1.User registration");
        System.out.println("2.Account management");
        System.out.println("3.View users");
        System.out.println("4.Delete user");
        System.out.println("5.Add property");
        System.out.println("6.Update property");
        System.out.println("7.Delete property");
        System.out.println("8.View properties");
        System.out.println("9.Add booking");
        System.out.println("10.See my bookings");
        System.out.println("11.Delete booking\nHint: See your bookings list to check for registration number.");
        System.out.println("12.Update booking");
        System.out.println("quit");
        System.out.println("\nEnter corresponding functionality number:");
    }
}
