//import Calcul;
public class Cerc implements Calcul {
    private double a;

    @Override
    public double calculPerim(double a) {
        return 2*3.14*a;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public Cerc(double a) {
        this.a = a;
    }
}
