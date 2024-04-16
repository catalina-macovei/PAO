import java.io.*;

public class MainEx1ScriereChar {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                try {
                    // Citire date
                    System.out.println("Introduceti numele:");
                    String nume = reader.readLine();
                    System.out.println("Introduceti prenumele:");
                    String prenume = reader.readLine();
                    System.out.println("Introduceti varsta:");
                    String varsta = reader.readLine();
                    System.out.println("Introduceti suma:");
                    double suma = Double.parseDouble(reader.readLine());
                    if (suma > 2000) {
                        throw new SumaPreaMareException("Suma introdusa este prea mare!");
                    }
                    System.out.println("Introduceti valuta:");
                    String valuta = reader.readLine();

                    // Scriere date in fisier
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/persoaneChar.txt", true))) {
                        writer.write(nume + ";" + prenume + ";" + varsta + ";" + suma + ";" + valuta + "\n");
                    } catch (IOException e) {
                        System.err.println("Eroare la scrierea in fisier: " + e.getMessage());
                    }
                } catch (SumaPreaMareException e) {
                    System.err.println(e.getMessage());
                    continue; // Reia citirea de la capat
                }

                System.out.println("Doriti sa continuati? (da/nu)");
                String raspuns = reader.readLine();
                if (!raspuns.equalsIgnoreCase("da")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la citirea de la intrare: " + e.getMessage());
        }
    }
}
