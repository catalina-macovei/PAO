package daoservices;

import model.User;
import model.Customer;
import model.Landlord;
import dao.CustomerDao;
import dao.LandlordDao;

import java.sql.SQLException;
import java.util.List;

import static utils.Constants.LANDLORD;

public class UserRepositoryService {

    private LandlordDao landlordDao = LandlordDao.getInstance();
    private CustomerDao customerDao;

    public UserRepositoryService() throws SQLException {
    }

    public Customer getCustomerByName(String name) {
        Customer customer = customerDao.read(name);
        return customer;
    }

    public Landlord getLandlordByName(String name) throws SQLException {
        Landlord landlord = landlordDao.read(name);
        System.out.println("getLandlordByName " + landlord);
        return landlord;
    }

    public void removeUser(String typeOfUser, String name) throws SQLException {
        User user = getUser(typeOfUser, name);
        if (user == null) {
            System.out.println("user not found");
            return;
        }

        switch (user) {
            case Landlord landlord -> landlordDao.delete(landlord);
            case Customer customer -> customerDao.delete(customer);
            default -> throw new IllegalStateException("Unexpected value: " + user);
        }

        System.out.println("Removed " + user);
    }

    public void addUser(User user) throws SQLException {
        if (user != null) {
            switch (user) {
                case Landlord landlord -> landlordDao.add(landlord);
                case Customer customer -> customerDao.create(customer);
                default -> throw new IllegalStateException("Unexpected value: " + user);
            }
        }
    }

    public User getUser(String typeOfUser, String name) throws SQLException {
        User user;
        if (typeOfUser.equals(LANDLORD)) {
            user = getLandlordByName(name);
            System.out.println("User "+ name);
            System.out.println(user);
        } else {
            user = getCustomerByName(name);
        }
        return user;
    }

    public List<Landlord> getAllLandlords() throws SQLException {
        return landlordDao.readAll();
    }

    public List<Customer> getAllCustomers() {
        return customerDao.readAll();
    }
}
