package service;
import daoservices.AddressRepositoryService;
import model.*;
import java.util.Scanner;
public class AddressService {
    private AddressRepositoryService dbService;

    public AddressService() {
        this.dbService = new AddressRepositoryService();
    }

    public Address create(Scanner scanner) {
        Address address = setGeneralInfo(scanner);
        dbService.addAddress(address);

        return address;
    }

    public void read(Scanner scanner) {
        System.out.println("Address id: ");
        int addrid = scanner.nextInt();
        dbService.getAddressById(addrid);
    }

    public void delete(Scanner scanner) {
        System.out.println("Address id:");
        int addrid = scanner.nextInt();
        dbService.deleteAddress(dbService.getAddressById(addrid));
    }

    public void update(Scanner scanner) {
        System.out.println("Address id: ");
        int addrid = scanner.nextInt();
        Address address = dbService.getAddressById(addrid);
        if (address == null) {return;}

        Address addressGeneralInfo = setGeneralInfo(scanner);
        address.setCountry(addressGeneralInfo.getCountry());
        address.setCity(addressGeneralInfo.getCity());
        address.setStreet(addressGeneralInfo.getStreet());
        address.setNumber(addressGeneralInfo.getNumber());
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
        return new Address(street, city, country, number);
    }


}
