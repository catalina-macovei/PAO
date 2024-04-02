import java.util.ArrayList;
import java.util.List;

public class Proprietar {
    private String nume;
    private String prenume;
    private int varsta;
    private List<Magazin> magazine = new ArrayList<>();

    public Proprietar(String nume, String prenume, int varsta, List<Magazin> magazine) {
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
        this.magazine = magazine;
    }

    public Proprietar(String nume, String prenume, int varsta) {
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public List<Magazin> getMagazine() {
        return magazine;
    }

    public void addMagazin(Magazin magazin) {
        magazine.add(magazin);
    }
}
