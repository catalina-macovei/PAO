package model;


import java.util.HashSet;
import java.util.Set;

public class Landlord extends User{
    private Set<Property> properties = new HashSet<>();
    public Landlord(User user) {
        super(user.getName(), user.getEmail(), user.getPassword(), user.getAccountBalance());
    }

    public Landlord(String name, String email, String password, AccountBalance accountBalance, Set<Property> properties) {
        super(name, email, password, accountBalance);
        this.properties = properties;
    }

    public Landlord() {

    }
    public Set<Property> getProperties() {
        return properties;
    }


    public void addProperty(Property p) {
        properties.add(p);
    }


    @Override
    public String toString() {
        return "Landlord{" +
                "name='" + getName() +   " account balance =" + getAccountBalance() +'}';
    }

}
