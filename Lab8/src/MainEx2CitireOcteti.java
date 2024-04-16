import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainEx2CitireOcteti {
    private static final String FILENAME = "src/persoaneOcteti.txt";

    public static void main(String[] args) {
        List<Object> obiecte = Arrays.stream(FileManagement.citireObiectDinFisier(FILENAME))
                .toList();

        // Afisare lista de obiecte
        System.out.println("Lista de obiecte:");
        for (Object obiect : obiecte) {
            System.out.println(obiect);
            
        }
    }
}
