package service;

import daoservices.PropertyRepositoryService;
import model.*;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static utils.Constants.*;

public class PropertyService {
    private PropertyRepositoryService dbService;
    private AddressService addressService;

    public PropertyService() {
        this.dbService = new PropertyRepositoryService();
        this.addressService = new AddressService();
    }

    public void create(Scanner scanner, Landlord landlord) {
        System.out.println("Enter type of property [apartment/house]:");
        String typeOfProperty = scanner.nextLine().toLowerCase();

        if (!typeOfPropertyValidation(typeOfProperty)) { return; }
        propertyInit(scanner, typeOfProperty, landlord);
        // set the address
    }

    public void read(Scanner scanner){
        System.out.println("Enter property name:");
        String name = scanner.nextLine();
        dbService.getApartmentByName(name);
        //or
        dbService.getHouseByName(name);
    }

    public void delete(Scanner scanner) {
        System.out.println("Enter property name:");
        String name = scanner.nextLine();
        System.out.println("Type of property: ");
        String typeOfProperty = scanner.nextLine().toLowerCase();
        if (!typeOfPropertyValidation(typeOfProperty)) { return; }
        dbService.removeProperty(typeOfProperty, name);
    }

    public void update(Scanner scanner) {
        System.out.println("Enter property name:");
        String name = scanner.nextLine();
        System.out.println("Type of property: ");
        String typeOfProperty = scanner.nextLine().toLowerCase();
        if(checkIfExists(name, typeOfProperty)) {
            Property property = dbService.getProperty(typeOfProperty, name);
            System.out.println("New name: ");
            String n = scanner.nextLine();
            property.setName(n);
            System.out.println("New price");
            Double p = scanner.nextDouble();
            property.setPrice(p);
            // apelare fct din setare adresa (creare)
            if (typeOfProperty.equals(HOUSE)) {
                houseInit(scanner, (House) property);
            } else {
                apartmentInit(scanner, (Apartment) property);
            }
        }
    }

    private boolean typeOfPropertyValidation(String typeOfProperty) {
        if (!typeOfProperty.equals(APARTMENT) && !typeOfProperty.equals(HOUSE)) {
            System.out.println("Invalid property type!");
            return false;
        }
        return true;
    }

    private void propertyInit(Scanner scanner, String typeOfProperty, Landlord landlord) {
        System.out.println("Enter property name:");
        String name = scanner.nextLine();
        if(checkIfExists(name, typeOfProperty)) {
            System.out.println("already exists!");
            return;
        }
        System.out.println("Enter price:");
        double price = scanner.nextDouble();
        Property property = new Property(price, name, landlord); // Landlord not provided in the example

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
    }

    private boolean checkIfExists(String name, String typeOfProperty) {
        if (typeOfProperty.equals(APARTMENT) && dbService.getApartmentByName(name) != null) {return true;}
        if (typeOfProperty.equals(HOUSE) && dbService.getHouseByName(name) != null) {return true;}
        return false;
    }

    private void apartmentInit(Scanner scanner, Apartment apartment) {
        System.out.println("Apatment floor number: ");
        int fnr = scanner.nextInt();
        apartment.setFloorNr(fnr);
    }

    private void houseInit(Scanner scanner, House house) {
        System.out.println("House yardSize: ");
        double ys = scanner.nextDouble();
        house.setYardsize(ys);
    }

    public void viewPropertyList(Scanner scanner) {
        System.out.println("\nView available properties: 1-apartment || 2-house || 3-all");
        System.out.println("Please select an option:");
        int choice = scanner.nextInt();
        switch(choice) {
            case 1:
                readAllApartments(dbService.getAllApartments());
                break;
            case 2:
                readAllHouses(dbService.getAllHouses());
                break;
            case 3:
                readAllProperties(dbService.getAllProperties());
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
