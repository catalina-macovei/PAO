package service;
import daoservices.AddressRepositoryService;
import model.*;
import utils.FileManagement;

import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

import static utils.Constants.AUDIT_FILE;

public class AddressService {
    private AddressRepositoryService dbService;

    public AddressService() throws SQLException {
        this.dbService = new AddressRepositoryService();
    }

    public Address create(Scanner scanner) {
        Address address = setGeneralInfo(scanner);

        try {
            dbService.addAddress(address);
            FileManagement.scriereFisierChar(AUDIT_FILE, "created address: " + address);
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
            FileManagement.scriereFisierChar(AUDIT_FILE, "read address: " + ad);
            System.out.println(ad);
        } catch (SQLException e) {
            System.err.println("Error occurred while reading the address!");
        }
    }


    public void delete(Scanner scanner, int addrid) {

        try {
            Address addressToDelete = dbService.getAddressById(addrid);
            if (addressToDelete != null) {
                dbService.deleteAddress(addressToDelete);
                FileManagement.scriereFisierChar(AUDIT_FILE, "deleted address: " + addressToDelete);
                //System.out.println("Address deleted.");
            } else {
                System.out.println("Address not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while deleting the address " );
        }
    }


    public void update(Scanner scanner, int addrid) {
        try {
            Address address = dbService.getAddressById(addrid);
            FileManagement.scriereFisierChar(AUDIT_FILE, "read address: " + address);
            if (address == null) {
                return;
            }
            Address addressGeneralInfo = setGeneralInfo(scanner);
            address.setCountry(addressGeneralInfo.getCountry());
            address.setCity(addressGeneralInfo.getCity());
            address.setStreet(addressGeneralInfo.getStreet());
            address.setNumber(addressGeneralInfo.getNumber());
            System.out.println("address id: " + address.getId());
            dbService.update(address);
            FileManagement.scriereFisierChar(AUDIT_FILE, "update address: " + address);
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

}
