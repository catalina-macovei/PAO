package service;
import daoservices.AddressRepositoryService;
import model.*;

import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;
public class AddressService {
    private AddressRepositoryService dbService;

    public AddressService() throws SQLException {
        this.dbService = new AddressRepositoryService();
    }

    public Address create(Scanner scanner) {
        Address address = setGeneralInfo(scanner);

        try {
            dbService.addAddress(address);
        } catch (SQLException e) {
            System.err.println("Error occurred while adding the address! " );
        }

        return address;
    }

    public void read(Scanner scanner) {
        System.out.println("Address id: ");
        int addrid = scanner.nextInt();

        try {
            Address ad = dbService.getAddressById(addrid);
            System.out.println(ad);
        } catch (SQLException e) {
            System.err.println("Error occurred while reading the address!");
        }
    }


    public void delete(Scanner scanner) {
        System.out.println("Address id:");
        int addrid = scanner.nextInt();

        try {
            Address addressToDelete = dbService.getAddressById(addrid);
            if (addressToDelete != null) {
                dbService.deleteAddress(addressToDelete);
                //System.out.println("Address deleted.");
            } else {
                System.out.println("Address not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while deleting the address " );
        }
    }


    public void update(Scanner scanner) {
        System.out.println("Address id: ");
        int addrid = scanner.nextInt();

        try {
            Address address = dbService.getAddressById(addrid);
            if (address == null) {
                return;
            }
            Address addressGeneralInfo = setGeneralInfo(scanner);
            address.setCountry(addressGeneralInfo.getCountry());
            address.setCity(addressGeneralInfo.getCity());
            address.setStreet(addressGeneralInfo.getStreet());
            address.setNumber(addressGeneralInfo.getNumber());

            dbService.update(address);
            System.out.println("Address updated.");
        } catch (SQLException e) {
            System.err.println("Error occurred while updating the address");
        }
    }

    private Address setGeneralInfo(Scanner scanner) {
        System.out.println("\nAddress data: ");
        System.out.println("Enter country: ");
        String country = scanner.next();
        scanner.nextLine(); // Consume the newline character left in the buffer
        System.out.println("Enter city: ");
        String city = scanner.nextLine(); // Read entire line
        System.out.println("Enter street:");
        String street = scanner.nextLine();
        System.out.println("Enter number: ");
        String number = scanner.nextLine();
        Random random = new Random();
        int randomNumber = random.nextInt(10000); // Generates a random number between 0 and 999
        Address address = new Address(street, city, country, number);
        address.setId(randomNumber);
        return address;
    }

    /// for testing
    public static void main(String[] args) {
        try {
            AddressService addressService = new AddressService();
            Scanner scanner = new Scanner(System.in);

            // Create a new address
            System.out.println("Creating a new address...");
            Address newAddress = addressService.create(scanner);
            System.out.println("New address created: " + newAddress);

            // Update the created address
            System.out.println("Updating the address...");
            addressService.update(scanner);
            System.out.println("Address updated: " + newAddress);

            // Read an address by id
            System.out.println("Reading an address by id...");
            addressService.read(scanner);

            // Delete the created address
            System.out.println("Deleting the address...");
            addressService.delete(scanner);
            System.out.println("Address deleted.");

            // Close the scanner to prevent resource leak
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
