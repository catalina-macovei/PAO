package model;

public class Profesor extends Person {
    private String course;
    private int year;

    public Profesor(String name, String phone, String email, String course, int year) {
        super(name, phone, email);

        this.course = course;
        this.year = year;
    }
    public String getCourse() {
        return course;
    }

    public int getYear() {
        return year;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "\nname:\n " + getName() +
                "\nphone number:\n " + getPhone() +
                "\nemail:\n " + getEmail() +
                "\nyear:\n" + getYear() +
                "\ncourse:\n" + getCourse();
    }
}
