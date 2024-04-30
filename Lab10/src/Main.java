import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

public class Main {
    public static void main(String[] args) {
        solveEx1();
        solveEx2();
        solveEx3();
    }

    private static void solveEx1() {
        Integer[] numere = new Integer[]{100, 2, 3, 20, 10, 15};
        Arrays.sort(numere, (Object o1, Object o2) -> ((Integer) o2).compareTo((Integer) o1));
        System.out.println("Sortare:" + Arrays.toString(numere));
        // suma reduce
        int suma = Arrays.stream(numere).mapToInt(Integer::intValue).reduce(0, (a, b) -> a + b);
        System.out.println("Suma 1: " + suma);
        // summingInt
        suma = Arrays.stream(numere).collect(summingInt(Integer::intValue));
        System.out.println("Suma 2: " + suma);
        //averagingInt
        double media = Arrays.stream(numere).collect(Collectors.averagingInt(Integer::intValue));
        System.out.println("Media: " + media);

        int min = Arrays.stream(numere).min(Integer::compareTo).orElseThrow(); /// dc array e gol
        System.out.println("min: " + min);
        int max = Arrays.stream(numere).max(Integer::compareTo).orElseThrow();
        System.out.println("min: " + max);

        Integer val = 10;
        Stream<Integer> nrSortate = Arrays.stream(numere).filter(nr -> nr > val);
        System.out.println("Nr mai mari decat " + val + ": " + Arrays.toString(nrSortate.toArray()));
    }

    private static void solveEx2() {
        int[] ar = {5, 3, 8, 2, 1};

        Arrays.sort(ar);
        System.out.println("crescator: " +Arrays.toString(ar));

        int[] arDesc = Arrays.stream(ar).boxed().sorted(java.util.Comparator.reverseOrder()).mapToInt(Integer::intValue).toArray();
        System.out.println("descrescator: " + Arrays.toString(arDesc));

        int[] ar2 = {2, 3, 4, 5, 6};
        var listaPatrate = Arrays.stream(ar2).map(x -> x * x).boxed().collect(Collectors.toList());
        System.out.println("squared: " + listaPatrate);
    }

    private static void solveEx3() {
        List<Magazin> magazine =  new ArrayList<>();
        magazine.add(new Magazin("Mag 1", 100));
        magazine.add(new Magazin("Mag 2", 80));
        magazine.add(new Magazin("Mag 3", 100));
        magazine.add(new Magazin("Mag 4", 90));

        Map<Integer, List<Magazin>> mapGroupByAng = magazine.stream().collect(groupingBy(Magazin::getNumarAngajati));
        System.out.println("GroupBy nr ang: " + mapGroupByAng);

        int sumaAng = magazine.stream().collect(summingInt(Magazin::getNumarAngajati));
        System.out.println("Suma nr ang: " + sumaAng);
    }
}
