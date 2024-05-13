package daoservices;

import dao.AddressDao;
import model.Address;

import java.sql.SQLException;

public class AddressRepositoryService {
    private AddressDao addressDao = AddressDao.getInstance();

    public AddressRepositoryService() throws SQLException {}

    public Address getAddressById(int id) throws SQLException {
        Address address = addressDao.read(String.valueOf(id));
        if (address == null) {
            System.out.println("No Address found for ID: " + id);
        }
        return address;
    }
    public void addAddress(Address address) throws SQLException {
        addressDao.add(address);
    }

    public void deleteAddress(Address address) throws SQLException {
        addressDao.delete(address);
        //System.out.println("Deleted Address: " + address);
    }

    public void update(Address address) throws SQLException{
        if (address != null) {
            addressDao.update(address);
        }
    }
}
