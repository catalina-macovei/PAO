package dao;

import model.Apartment;
import model.Property;

import java.util.ArrayList;
import java.util.List;

public class ApartmentDao {
    private static List<Apartment> apartments = new ArrayList<>();

    public Apartment read(String name) {
        for (Apartment apartment : apartments) {
            if (apartment.getName().equals(name)) {
                return apartment;
            }
        }
        return null; //apartment with the specified name is not found
    }

    public void create(Apartment apartment) {
        apartments.add(apartment);
    }

    public void delete(Apartment apartment) {
        apartments.remove(apartment);
    }

    public List<Property> readAll() {
        List<Property> properties = new ArrayList<>(apartments);
        return properties;
    }
}
