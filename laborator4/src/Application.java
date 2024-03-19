public class Application {
    public static void main(String[] args) {

        Cerc c1 = new Cerc(2);

        Patrat p1 = new Patrat(2);

       // Calcul c2 = new Cerc(5);

        System.out.println("perimetru cerc:");
        System.out.println(c1.calculPerim(c1.getA()));

        System.out.println("perimetru patrat:");
        System.out.println(p1.calculPerim(p1.getA()));
    }
}
