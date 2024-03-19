public class Patrat implements Calcul{
    private double a;

    @Override
    public double calculPerim(double a) {
        return 4*a;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public Patrat(double a) {
        this.a = a;
    }
}
