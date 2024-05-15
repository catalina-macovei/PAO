package model;

public class Apartment extends Property{
    private int floorNr;

    public Apartment() {}
    public Apartment(Property property) {
        super(property.getPrice(), property.getName(), property.getLandlord());
        property.getLandlord().addProperty(this);
    }

    public int getFloorNr() {
        return floorNr;
    }

    public void setFloorNr(int floorNr) {
        this.floorNr = floorNr;
    }

    @Override
    public String toString() {

        return "Apartment{" +
                "name= "+ getName() +
                ", address=" + getAddress() +
                ", price=" + getPrice() +
                ", floorNr=" + getFloorNr() +
                '}';
    }



}
