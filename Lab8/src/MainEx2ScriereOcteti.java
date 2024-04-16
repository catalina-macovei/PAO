import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainEx2ScriereOcteti {
    private static final String FILENAME = "src/persoaneOcteti.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Persoana> persoane = new ArrayList<>();

        while (true) {
            try {
                // Citire date
                System.out.println("Introduceti numele:");
                String nume = scanner.nextLine();

                System.out.println("Introduceti prenumele:");
                String prenume = scanner.nextLine();

                System.out.println("Introduceti varsta:");
                int varsta = Integer.parseInt(scanner.nextLine());

                System.out.println("Introduceti suma:");
                double suma = Double.parseDouble(scanner.nextLine());

                if (suma > 2000) {
                    throw new SumaPreaMareException("Suma introdusa este prea mare!");
                }

                System.out.println("Introduceti valuta:");
                String valuta = scanner.nextLine();

                // Creare obiect Persoana
                Persoana persoana = new Persoana(nume, prenume, varsta, suma, valuta);

                // Adăugare persoană în listă
                persoane.add(persoana);

            } catch (NumberFormatException e) {
                System.out.println("Varsta sau suma introduse nu sunt valide. Va rugam sa reintroduceti datele.");
                continue; // Reia citirea de la capat
            } catch (SumaPreaMareException e) {
                System.out.println("Eroare: " + e.getMessage());
                continue; // Reia citirea de la capat
            }

            System.out.println("Doriti sa continuati? (da/nu)");
            String raspuns = scanner.nextLine();
            if (!raspuns.equalsIgnoreCase("da")) {
                break;
            }
        }

        FileManagement.scriereObiectInFisier(FILENAME, persoane);

        for (Persoana persoana : persoane) {
            System.out.println(persoana);
        }

        scanner.close();
    }

}
