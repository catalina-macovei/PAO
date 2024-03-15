package model;

public class Student extends Person {

    private int number;
    private double avgMark;
    private int classNr;

    public Student(String name, String phone, String email, int number, double avgMark, int classNr) {
        super(name, phone, email);

        this.number = number;
        this.avgMark = avgMark;
        this.classNr = classNr;
    }

    public int getNumber() {
        return number;
    }

    public double getAvgMark() {
        return avgMark;
    }

    public int getClassNr() {
        return classNr;
    }

    public void setAvgMark(double avgMark) {
        this.avgMark = avgMark;
    }

    public void setClassNr(int classNr) {
        this.classNr = classNr;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "\nname:\n " + getName() +
                "\nphone number:\n " + getPhone() +
                "\nemail:\n " + getEmail() +
                "\nstudent number:\n" + getNumber() +
                "\naverage mark:\n" + getAvgMark() +
                "\nclass:\n" + getClassNr();
    }
}
