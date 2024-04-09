import java.util.HashSet;
import java.util.Set;

public class Application {

    public static void main(String[] args) throws CloneNotSupportedException {
        Set<String> tari = new HashSet<>();
        Set<Tranzactie> tranzactiiPerUser = new HashSet<>();

        User user = new User("Macovei", "Catalina", "IBAN12345");
        User user2 = new User("Tudos", "Mihail", "IBAN34567");

        Currency ron = Currency.RON;
        Currency usd = Currency.USD;

        Payment payment = new Payment(5000, ron, user, "Plata chirie");
        tari.add(payment.getCurrency().getCountryName()); // adaug in lista tara

        Payment payment1 = new Payment(2000, usd, user2, "Travel");
        tari.add(payment1.getCurrency().getCountryName());

        Tranzactie tranzactie = new Tranzactie(1, payment);
        Tranzactie tranzactie1 = new Tranzactie(2, payment1);
        tranzactiiPerUser.add(tranzactie);
        tranzactiiPerUser.add(tranzactie1);
        /// Facem mai multe plati
        Payment payment2 = new Payment(1000, usd, user,  "Shopping");
        tari.add(payment2.getCurrency().getCountryName());

        Tranzactie tranzactie2 = new Tranzactie(3, payment2);
        tranzactiiPerUser.add(tranzactie2);

        System.out.println("TRANZACTIE initiala: " + tranzactie);

        // stornare
        Tranzactie tranzactieClona = (Tranzactie)tranzactie.clone();
        double amountNou =  - tranzactieClona.getPayment().getAmount();
        tranzactieClona.getPayment().setAmount(amountNou);

        System.out.println("TRANZACTIE Clona: " + tranzactieClona);

        System.out.println("TARI:");
        for (String tara: tari) {
            System.out.println(tara);
        }

        System.out.println("Primul user:"); // tranz pentru primul user
        for (Tranzactie tranz : tranzactiiPerUser) {
            if (tranz.getPayment().getUser() == user)
                System.out.println(tranz);  // cu fiecare tranzactie afiseaza amount
        }
    }
}
