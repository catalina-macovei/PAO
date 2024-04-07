package dao;

import model.House;
import model.Property;

import java.util.ArrayList;
import java.util.List;

public class HouseDao {
    private static List<House> houses = new ArrayList<>();

    public House read(String name) {
        for (House house : houses) {
            if (house.getName().equals(name)) {
                return house;
            }
        }
        return null; // Return null if house with the specified name is not found
    }

    public void create(House house) {
        houses.add(house);
    }

    public void delete(House house) {
        houses.remove(house);
    }

    public List<Property> readAll() {
        List<Property> properties = new ArrayList<>(houses);
        return properties;
    }

}
