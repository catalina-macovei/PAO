package daoservices;

import model.Property;
import dao.ApartmentDao;
import dao.HouseDao;
import model.Apartment;
import model.House;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.Constants.*;

public class PropertyRepositoryService {
    private ApartmentDao apartmentDao;
    private HouseDao houseDao =  HouseDao.getInstance();

    public PropertyRepositoryService() throws SQLException {
        this.apartmentDao = new ApartmentDao();
    }

    public Apartment getApartmentByName(String name) {
        Apartment apartment = apartmentDao.read(name);
        return apartment;
    }

    public House getHouseByName(String name) throws SQLException {
        House house = houseDao.read(name);
        return house;
    }

    public void removeProperty(String typeOfProperty, String name) throws SQLException {
        Property property = getProperty(typeOfProperty, name);
        if (property == null) return;

        switch (typeOfProperty) {
            case APARTMENT -> apartmentDao.delete((Apartment) property);
            case HOUSE -> houseDao.delete((House) property);
            default -> throw new IllegalArgumentException("Unsupported property type: " + typeOfProperty);
        }

        System.out.println("Removed " + property);
    }

    public void addProperty(Property property) throws SQLException {
        if (property != null) {
            switch (property.getClass().getSimpleName()) {
                case "Apartment" -> apartmentDao.create((Apartment) property);
                case "House" -> houseDao.add((House) property);
                default ->
                        throw new IllegalArgumentException("Unsupported property type: " + property.getClass().getSimpleName());
            }
        }
    }

    public void updateProperty(Property property) throws SQLException {
        if (property != null) {
            System.out.println("update casa");
            switch (property.getClass().getSimpleName()) {
                //case "Apartment" -> apartmentDao.update((Apartment) property);
                case "House" -> houseDao.update((House) property);
                default ->
                        throw new IllegalArgumentException("Unsupported property type: " + property.getClass().getSimpleName());
            }
        }
    }

    public Property getProperty(String typeOfProperty, String name) throws SQLException {
        Property property;
        switch (typeOfProperty) {
            case APARTMENT:
                property = getApartmentByName(name);
                break;
            case HOUSE:
                property = getHouseByName(name);
                break;
            default:
                System.out.println("Unsupported property type: " + typeOfProperty);
                return null;
        }

        if (property == null) {
            System.out.println("No property having name " + name);
        }
        return property;
    }

    public List<Property> getAllProperties() throws SQLException {
        List<Property> properties = new ArrayList<>();
        properties.addAll(apartmentDao.readAll());
        properties.addAll(houseDao.readAll());
        return properties;
    }

    public List<Property> getAllApartments() {
        return apartmentDao.readAll();
    }
    public List<Property> getAllHouses() throws SQLException {
        return houseDao.readAll();
    }

}