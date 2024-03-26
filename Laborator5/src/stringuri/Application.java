package stringuri;
import java.util.regex.*;
public class Application {
    public static void main(String[] args) {
        String strReg = "b";

        System.out.println(strReg.matches("[abc]"));

        strReg = "f";
        System.out.println(strReg.matches("[^abc]"));

        strReg = "d";
        System.out.println(strReg.matches("[a-z]"));

        strReg = "A";
        System.out.println(strReg.matches("[a-zA-Z]"));

        strReg = "abac";
        System.out.println(strReg.matches("[abc]+"));

        strReg = "abacb";
        System.out.println(strReg.matches("[abc]{5}"));

        strReg = "abaccb";
        System.out.println(strReg.matches("[abc]{5,}"));

        strReg = "abaccbbb";
        System.out.println(strReg.matches("[abc]{5,10}"));


        // Exercitiul 2:

        String str = "Odata creat un sir de caractere cu continutul sau nu mai poate fi modificat.";

        // Afiseaza lungimea
        System.out.println("1. Lungimea stringului: " + str.length());

        // Verifica daca stringul contine doar litere mici sau doar litere mari
        System.out.println("2. Contine doar litere mici sau doar litere mari: " + str.matches("[a-z]*|[A-Z]*"));

        //Imparte textul in cuvinte si afiseaza cuvintele de la mijloc
        String[] words = str.split("\\s+"); // \s+ face match pe spatii goale

        int middleIndex = words.length / 2;

        System.out.println("3. Cuvintele de la mijloc:");

        if (words.length % 2 == 0) {
            System.out.println(words[middleIndex - 1]);
            System.out.println(words[middleIndex]);
        } else {
            System.out.println(words[middleIndex]);
        }

        //Afiseaza stringul inversat
        StringBuilder reversedStr = new StringBuilder(str).reverse();
        System.out.println("4. Stringul inversat: " + reversedStr);
    }
}
