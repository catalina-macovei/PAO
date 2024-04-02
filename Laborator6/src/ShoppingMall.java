import java.util.ArrayList;
import java.util.List;

public class ShoppingMall {
    List<Magazin> magazine = new ArrayList<>();


    public List<Magazin> getMagazine() {
        return magazine;
    }

    public void addMagazin(Magazin magazin) {
        magazine.add(magazin);
    }
}
