import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Proprietar proprietar1;
        proprietar1 = new Proprietar("NumeP1", "PrenumeP1", 50);
        Proprietar proprietar2;
        proprietar2 = new Proprietar("NumeP2", "PrenumeP2", 52);

        ShoppingMall shoppingMall = new ShoppingMall();

        List<Magazin> magazineProprietar = new ArrayList<>();
        Magazin m1 = new Magazin(11, "D magazin 1",600, 204, 500, proprietar1);
        shoppingMall.addMagazin(m1);
        Magazin m2 = new Magazin(12, "B magazin 2",900, 209, 5000, proprietar1);
        shoppingMall.addMagazin(m2);
        Magazin m3 = new Magazin(13, "C magazin 3",700, 300, 800, proprietar2);
        shoppingMall.addMagazin(m3);

        CalcChiria calcChiria1 = new MagazinAlimentar();
        m1.setChirie(calcChiria1);

        CalcChiria calcChiria2 = new MagazinChimicale();
        m2.setChirie(calcChiria2);

        CalcChiria calcChiria3 = new MagazinHaine();
        m3.setChirie(calcChiria3);

        System.out.println("Lista de magazine din shopping mall:");
        Collections.sort(shoppingMall.getMagazine(),
                Comparator.comparing(Magazin::getProprietar, Comparator.comparing(Proprietar::getNume))
                        .thenComparing(Magazin::getProprietar, Comparator.comparing(Proprietar::getPrenume))
                                .thenComparing(Magazin::getChirie));

        System.out.println(shoppingMall.getMagazine());

        // Alegem un prorpietar:
        System.out.println("Lista de magazine a unui proprietar:");

        Collections.sort(proprietar1.getMagazine(),
                Comparator.comparing(Magazin::getNumeMagazin)
                        .thenComparing(Magazin::getVenit)
                        .thenComparing(Magazin::getChirie)
                        .thenComparing(Magazin::getSuprafata)
                        );
        System.out.println(proprietar1.getMagazine());

        System.out.println("Venitul total al proprietarului: ");
        double sumV = 0.0;
        for (Magazin m : proprietar1.getMagazine()) {
            sumV += m.getVenit();
        }
        System.out.println("Venit total: " + sumV);

        System.out.println("Chiria totala: ");
        double sumC = 0.0;
        for (Magazin m: proprietar1.getMagazine())
        {
            sumC += m.getChirie();
        }
        System.out.println("Chirie totala: " + sumC);
    }
}