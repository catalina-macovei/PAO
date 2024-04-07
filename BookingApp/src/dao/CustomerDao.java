package dao;

import model.Customer;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class CustomerDao {

    private static List<Customer> customers = new ArrayList<>();

    public void create(Customer customer) {
        customers.add(customer);
    }

    public Customer read(String name) {
        if(!customers.isEmpty()){
            for(Customer c : customers){
                if(c.getName().equals(name)){
                    return c;
                }
            }
        }
        return null;
    }

    public void delete(User User) {
        customers.remove(User);
    }

    public List<Customer> readAll() {
        return customers;
    }
}
