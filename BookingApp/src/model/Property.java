package model;

public class Property {
    private Address address;
    private Landlord landlord;
    private double price;
    private String name;

    public Property () {}
    public Property(double price, String name, Landlord landlord) {
        this.price = price;
        this.name = name;
        this.landlord = landlord;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Landlord getLandlord() {
        return landlord;
    }

    public void setLandlord(Landlord landlord) {
        this.landlord = landlord;
        landlord.addProperty(this);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Property{" +
                "address=" + address +
                '}';
    }
}
