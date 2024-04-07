package dao;

import model.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressDao {
    private static List<Address> addresses = new ArrayList<>();

    public Address read(int id) {
        for (Address address : addresses) {
            if (address.getId() == id) {
                return address;
            }
        }
        return null; // address with given ID is not found
    }

    public void delete(Address address) {
        addresses.remove(address);
    }

    public void create(Address address) {
        addresses.add(address);
    }

}
