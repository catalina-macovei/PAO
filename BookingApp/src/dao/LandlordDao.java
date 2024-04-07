package dao;

import model.Landlord;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class LandlordDao {
    private static List<Landlord> landlords = new ArrayList<>();

    public void create(Landlord landlord) {
        landlords.add(landlord);
    }

    public Landlord read(String name) {
        if(!landlords.isEmpty()){
            for(Landlord c : landlords){
                if(c.getName().equals(name)){
                    return c;
                }
            }
        }
        return null;
    }

    public void delete(User User) {
        landlords.remove(User);
    }

    public List<Landlord> readAll() {
        return landlords;
    }

}
