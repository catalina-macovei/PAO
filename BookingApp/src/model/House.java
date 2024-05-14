package model;

public class House extends Property{
    private double yardsize;

    public House() {}
    public House(Property property) {
        super(property.getPrice(), property.getName(), property.getLandlord());
        property.getLandlord().addProperty(this);
    }

    public double getYardsize() {
        return yardsize;
    }

    public void setYardsize(double yardsize) {
        this.yardsize = yardsize;
    }

    @Override
    public String toString() {
        return "House{" +
                "name= "+getName() +
                ", address=" + getAddress() +
                ", price=" + getPrice() +
                ", yardsize=" + getYardsize() +
                '}';
    }

}
