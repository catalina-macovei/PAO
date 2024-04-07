package daoservices;

import dao.AddressDao;
import model.Address;

public class AddressRepositoryService {
    private AddressDao addressDao;

    public AddressRepositoryService() {
        this.addressDao = new AddressDao();
    }

    public Address getAddressById(int id) {
        Address address = addressDao.read(id);
        if (address == null) {
            System.out.println("No Address found for ID: " + id);
        }
        return address;
    }
    public void addAddress(Address address) {
        addressDao.create(address);
    }

    public void deleteAddress(Address address) {
        addressDao.delete(address);
        System.out.println("Deleted Address: " + address);
    }
}
