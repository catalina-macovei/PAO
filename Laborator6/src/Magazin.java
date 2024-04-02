public class Magazin {
    private int id;
    private String numeMagazin;
    private double venit;
    private double suprafata;
    private double chirie;

    private Proprietar proprietar;


    public Magazin(int id, String numeMagazin, double venit, double suprafata, double chirie, Proprietar proprietar) {
        this.id = id;
        this.numeMagazin = numeMagazin;
        this.venit = venit;
        this.suprafata = suprafata;
        this.chirie = chirie;
        this.proprietar = proprietar;
        proprietar.addMagazin(this);
    }

    public Magazin(int id, String numeMagazin, double venit, double suprafata, double chirie) {
        this.id = id;
        this.numeMagazin = numeMagazin;
        this.venit = venit;
        this.suprafata = suprafata;
        this.chirie = chirie;
    }

    public Proprietar getProprietar() {
        return proprietar;
    }

    public void setProprietar(Proprietar proprietar) {
        this.proprietar = proprietar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeMagazin() {
        return numeMagazin;
    }

    public void setNumeMagazin(String numeMagazin) {
        this.numeMagazin = numeMagazin;
    }

    public double getVenit() {
        return venit;
    }

    public void setVenit(double venit) {
        this.venit = venit;
    }

    public double getSuprafata() {
        return suprafata;
    }

    public void setSuprafata(double suprafata) {
        this.suprafata = suprafata;
    }

    public double getChirie() {
        return chirie;
    }

    public void setChirie(CalcChiria intchirie) {
        this.chirie = intchirie.calculeazaChirie(suprafata);
    }


    @Override
    public String toString() {
        return "Magazin{" +
                "id=" + id +
                ", numeMagazin='" + numeMagazin + '\'' +
                ", venit=" + venit +
                ", suprafata=" + suprafata +
                ", chirie=" + chirie +
                '}';
    }
}
