package daoservices;

import model.Property;
import dao.ApartmentDao;
import dao.HouseDao;
import model.Apartment;
import model.House;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.Constants.*;

public class PropertyRepositoryService {
    private ApartmentDao apartmentDao;
    private HouseDao houseDao;

    public PropertyRepositoryService() {
        this.apartmentDao = new ApartmentDao();
        this.houseDao = new HouseDao();
    }

    public Apartment getApartmentByName(String name) {
        Apartment apartment = apartmentDao.read(name);
        return apartment;
    }

    public House getHouseByName(String name) {
        House house = houseDao.read(name);
        return house;
    }

    public void removeProperty(String typeOfProperty, String name) {
        Property property = getProperty(typeOfProperty, name);
        if (property == null) return;

        switch (typeOfProperty) {
            case APARTMENT -> apartmentDao.delete((Apartment) property);
            case HOUSE -> houseDao.delete((House) property);
            default -> throw new IllegalArgumentException("Unsupported property type: " + typeOfProperty);
        }

        System.out.println("Removed " + property);
    }

    public void addProperty(Property property) {
        if (property != null) {
            switch (property.getClass().getSimpleName()) {
                case "Apartment" -> apartmentDao.create((Apartment) property);
                case "House" -> houseDao.create((House) property);
                default ->
                        throw new IllegalArgumentException("Unsupported property type: " + property.getClass().getSimpleName());
            }
        }
    }

    public Property getProperty(String typeOfProperty, String name) {
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

    public List<Property> getAllProperties() {
        List<Property> properties = new ArrayList<>();
        properties.addAll(apartmentDao.readAll());
        properties.addAll(houseDao.readAll());
        return properties;
    }

    public List<Property> getAllApartments() {
        return apartmentDao.readAll();
    }
    public List<Property> getAllHouses() {
        return houseDao.readAll();
    }

}