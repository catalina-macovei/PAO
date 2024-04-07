package daoservices;

import model.User;
import model.Customer;
import model.Landlord;
import dao.CustomerDao;
import dao.LandlordDao;

import java.util.List;

import static utils.Constants.LANDLORD;

public class UserRepositoryService {

    private LandlordDao landlordDao;
    private CustomerDao customerDao;

    public UserRepositoryService() {
        this.customerDao = new CustomerDao();
        this.landlordDao = new LandlordDao();
    }

    public Customer getCustomerByName(String name) {
        Customer customer = customerDao.read(name);
        return customer;
    }

    public Landlord getLandlordByName(String name) {
        Landlord landlord = landlordDao.read(name);
        return landlord;
    }

    public void removeUser(String typeOfUser, String name) {
        User user = getUser(typeOfUser, name);
        if (user == null) return;

        switch (user) {
            case Landlord landlord -> landlordDao.delete(landlord);
            case Customer customer -> customerDao.delete(customer);
            default -> throw new IllegalStateException("Unexpected value: " + user);
        }

        System.out.println("Removed " + user);
    }

    public void addUser(User user) {
        if (user != null) {
            switch (user) {
                case Landlord landlord -> landlordDao.create(landlord);
                case Customer customer -> customerDao.create(customer);
                default -> throw new IllegalStateException("Unexpected value: " + user);
            }
        }
    }

    public User getUser(String typeOfUser, String name) {
        User user;
        if (typeOfUser.equals(LANDLORD)) {
            user = getLandlordByName(name);
        } else {
            user = getCustomerByName(name);
        }
        return user;
    }

    public List<Landlord> getAllLandlords() {
        return landlordDao.readAll();
    }

    public List<Customer> getAllCustomers() {
        return customerDao.readAll();
    }
}
