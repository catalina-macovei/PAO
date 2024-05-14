package service;

import daoservices.PropertyRepositoryService;
import model.*;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static utils.Constants.*;

public class PropertyService {
    private PropertyRepositoryService dbService;
    private AddressService addressService;

    public PropertyService() throws SQLException {
        this.dbService = new PropertyRepositoryService();
        this.addressService = new AddressService();
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
        dbService.getApartmentByName(name);
        //or
        System.out.println(dbService.getHouseByName(name));
    }

    public void delete(Scanner scanner) throws SQLException {
        System.out.println("Enter property name:");
        String name = scanner.nextLine();
        System.out.println("Type of property: ");
        String typeOfProperty = scanner.nextLine().toLowerCase();
        if (!typeOfPropertyValidation(typeOfProperty)) { return; }
        dbService.removeProperty(typeOfProperty, name);
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

        dbService.updateProperty((Property) house);
    }

    public void viewPropertyList(Scanner scanner) throws SQLException {
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            PropertyService propertyService = new PropertyService();

            while (true) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Create Property");
                System.out.println("2. Read Property");
                System.out.println("3. Update Property");
                System.out.println("4. Delete Property");
                System.out.println("5. View Property List");
                System.out.println("6. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        // Creating a new property
                        System.out.println("Enter landlord details:");
                        // Assuming landlord creation logic, you need to replace it with actual logic
                        Landlord landlord = new Landlord();
                        propertyService.create(scanner, landlord);
                        break;
                    case 2:
                        // Reading a property
                        propertyService.read(scanner);
                        break;
                    case 3:
                        // Updating a property
                        propertyService.update(scanner);
                        break;
                    case 4:
                        // Deleting a property
                        propertyService.delete(scanner);
                        break;
                    case 5:
                        // Viewing property list
                        propertyService.viewPropertyList(scanner);
                        break;
                    case 6:
                        // Exiting the application
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option! Please try again.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
