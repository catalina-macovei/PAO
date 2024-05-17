package service;

import daoservices.AddressRepositoryService;
import daoservices.PropertyRepositoryService;
import daoservices.UserRepositoryService;
import model.*;
import utils.FileManagement;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static utils.Constants.*;

public class PropertyService {
    private PropertyRepositoryService dbService;
    private AddressRepositoryService addressRepository;
    private AddressService addressService;


    public PropertyService() throws SQLException {
        this.dbService = new PropertyRepositoryService();
        this.addressService = new AddressService();
        this.addressRepository = new AddressRepositoryService();
    }

    public void create(Scanner scanner, Landlord landlord) throws SQLException {
        System.out.println("Enter type of property [apartment/house]:");
        String typeOfProperty = scanner.nextLine().toLowerCase();

        if (!typeOfPropertyValidation(typeOfProperty)) { return; }
        propertyInit(scanner, typeOfProperty, landlord);
    }

    public void read(Scanner scanner) throws SQLException {
        System.out.println("Enter property name:");
        String name = scanner.nextLine();
        Apartment apartment = dbService.getApartmentByName(name);
        House house = dbService.getHouseByName(name);

        if (apartment != null) {
            System.out.println(apartment);
            FileManagement.scriereFisierChar(AUDIT_FILE, "read apartment: " + name);
        }

        if (house != null) {
            System.out.println(house);
            FileManagement.scriereFisierChar(AUDIT_FILE, "read house: " + name);
        }
    }


    public void delete(Scanner scanner) throws SQLException {
        System.out.println("Enter property name:");
        String name = scanner.nextLine();
        System.out.println("Type of property: ");
        String typeOfProperty = scanner.nextLine().toLowerCase();
        if (!typeOfPropertyValidation(typeOfProperty)) { return; }
        Property property = dbService.getProperty(typeOfProperty, name);
        dbService.removeProperty(typeOfProperty, name);
        FileManagement.scriereFisierChar(AUDIT_FILE, "removed property: " + name);
        addressService.delete(scanner, property.getAddress().getId());
    }

    public void update(Scanner scanner) throws SQLException {
        System.out.println("Enter property name:");
        String name = scanner.nextLine();
        System.out.println("Type of property: ");
        String typeOfProperty = scanner.nextLine().toLowerCase();
        if(checkIfExists(name, typeOfProperty)) {
            Property property = dbService.getProperty(typeOfProperty, name);
            System.out.println("New price");
            Double p = scanner.nextDouble();
            property.setPrice(p);

            if (typeOfProperty.equals(HOUSE)) {
                houseInit(scanner, (House) property);
            } else {
                apartmentInit(scanner, (Apartment) property);
            }
            dbService.updateProperty(property);
            FileManagement.scriereFisierChar(AUDIT_FILE, "updated property: " + name);
            addressService.update(scanner, property.getAddress().getId());
        }
    }

    private boolean typeOfPropertyValidation(String typeOfProperty) {
        if (!typeOfProperty.equals(APARTMENT) && !typeOfProperty.equals(HOUSE)) {
            System.out.println("Invalid property type!");
            return false;
        }
        return true;
    }

    private void propertyInit(Scanner scanner, String typeOfProperty, Landlord landlord) throws SQLException {
        System.out.println("Enter property name:");
        String name = scanner.nextLine();
        if(checkIfExists(name, typeOfProperty)) {
            System.out.println("already exists!");
            return;
        }
        System.out.println("Enter price:");
        double price = scanner.nextDouble();
        Property property = new Property(price, name, landlord); // Landlord not provided in the example

        System.out.println("landlord: " + property.getLandlord().getId());
        if (typeOfProperty.equals(HOUSE)) {
            House house = new House(property);
            houseInit(scanner, house);
            property = house;
        } else {
            Apartment apartment = new Apartment(property);
            apartmentInit(scanner, apartment);
            property = apartment;
        }
        Address address = addressService.create(scanner);
        property.setAddress(address);
        dbService.addProperty(property);
        FileManagement.scriereFisierChar(AUDIT_FILE, "created property: " + name);
    }

    private boolean checkIfExists(String name, String typeOfProperty) throws SQLException {
        if (typeOfProperty.equals(APARTMENT) && dbService.getApartmentByName(name) != null) {return true;}
        if (typeOfProperty.equals(HOUSE) && dbService.getHouseByName(name) != null) {return true;}
        return false;
    }

    private void apartmentInit(Scanner scanner, Apartment apartment) {
        System.out.println("Apatment floor number: ");
        int fnr = scanner.nextInt();
        apartment.setFloorNr(fnr);
    }

    private void houseInit(Scanner scanner, House house) throws SQLException {
        System.out.println("House yardSize: ");
        double ys = scanner.nextDouble();
        house.setYardsize(ys);
    }

    public void viewPropertyList(Scanner scanner) throws SQLException {
        System.out.println("\nView available properties: 1-apartment || 2-house || 3-all");
        System.out.println("Please select an option:");
        int choice = scanner.nextInt();
        switch(choice) {
            case 1:
                readAllApartments(dbService.getAllApartments());
                FileManagement.scriereFisierChar(AUDIT_FILE, "read all apartments");
                break;
            case 2:
                readAllHouses(dbService.getAllHouses());
                FileManagement.scriereFisierChar(AUDIT_FILE, "read all houses");
                break;
            case 3:
                readAllProperties(dbService.getAllProperties());
                FileManagement.scriereFisierChar(AUDIT_FILE, "read all properties");
                break;
            default:
                System.out.println("Invalid option selected.");
        }
    }


    private static void readAllApartments(List<Property> apartments) {
        if (apartments.size() > 0) {
            System.out.println("Apartments sorted by price: ");
            apartments.sort(Comparator.comparingDouble(Property::getPrice));
        } else {
            System.out.println("No apartments in list!");
        }
        for (Property apartment : apartments) {
            System.out.println(apartment);
        }
    }

    private static void readAllHouses(List<Property> houses) {
        if (houses.size() > 0) {
            System.out.println("Houses sorted by price: ");
            houses.sort(Comparator.comparingDouble(Property::getPrice));
        } else {
            System.out.println("No houses in list!");
        }
        for (Property apartment : houses) {
            System.out.println(apartment);
        }
    }

    private static void readAllProperties(List<Property> properties) {
        System.out.println("All properties sorted by name: ");
        properties.sort(Comparator.comparing(Property::getName));
        for (Property property : properties) {
            System.out.println(property);
        }
    }

}
