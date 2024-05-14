package model;

public class Address {
    private static int counter;
    private int id;
    private String street;
    private String city;
    private String country;
    private String number;

    public Address() {}

    public Address(String street, String city, String country, String number) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.number = number;
        this.id = getNextId();
    }

    public int getId(){
        return id;
    }
    private static int  getNextId() {
        return counter++;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNumber() {
        return number;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
