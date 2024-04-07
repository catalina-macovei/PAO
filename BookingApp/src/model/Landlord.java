package model;


import java.util.HashSet;
import java.util.Set;

public class Landlord extends User{
    private Set<Property> properties = new HashSet<>();

    public Landlord(String name, String email, String password, AccountBalance accountBalance, Set<Property> properties) {
        super(name, email, password);
        this.properties = properties;
    }

    public Landlord(User user) {
        super(user.getName(), user.getEmail(), user.getPassword());
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
                "name='" + getName() + '\'' +
                ", accountBalance=" + (getAccountBalance() != null ? getAccountBalance() : 0) +
                ", propertiesNr=" + getProperties().size() +
                '}';
    }

}
