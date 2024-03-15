package service;
import model.Student;
import model.Profesor;

import java.util.ArrayList;
import java.util.List;

public class StorageService {
    private List<Student> studenti;
    private List<Profesor> profesori;

    public StorageService() {
        this.studenti = new ArrayList<>();
        this.profesori = new ArrayList<>();
    }

    public void addStudent(Student student)
    {
        for(Student s: studenti)
        {
            if(s.getName().equals(student.getName()))
            {
                System.out.println("Already existing!");
                return;
            }
        }
        studenti.add(student);
    }

    public void show(String nume_s) // afisare profesor/student daca numele coincide
    {
        for ( Student s: studenti)
        {
            if (s.getName().equals(nume_s))
            {
                System.out.println(s);
                return;
            }
        }
        for ( Profesor p: profesori)
        {
            if (p.getName().equals(nume_s))
            {
                System.out.println(p);
                return;
            }
        }
        System.out.println("Not existing!");
    }
    public void remove(String nume_s)
    {
        for(Student s: studenti)
        {
            if(nume_s.equals(s.getName()))
            {
                studenti.remove(s);
                return;
            }
        }
        for(Profesor p: profesori)
        {
            if(nume_s.equals(p.getName()))
            {
                profesori.remove(p);
                return;
            }
        }
        System.out.println("Not existing!");
    }

    public void addProfessor(Profesor profesor)
    {
        for (Profesor prof: profesori)
        {
            if (profesor.getName().equals(prof.getName()))
            {
                System.out.println("Already existing!");
                return;
            }
        }

        profesori.add(profesor);
    }
    public int Find(String nume_s)
    {
        for (Profesor prof: profesori)
        {
            if (prof.getName().equals(nume_s))
            {
                return 1;   // ret 1 dc a gasit un prof
            }
        }
        for (Student s: studenti)
        {
            if (s.getName().equals(nume_s))
            {
                return 2; // ret 2 daca a gasit un student
            }
        }
       /// System.out.println("Not Existing!");
        return 0;  // daca nu a gasit nimic
    }
    public void updateStudent(String nume_s, Student student)
    {
        for(Student s : studenti)
        {
            if( s.getName().equals(nume_s) )    // primesc studentul cu date actualizate si actualizez datele celui din vector
            {
                s.setName(student.getName());
                s.setPhone(student.getPhone());
                s.setEmail(student.getEmail());
                s.setNumber(student.getNumber());
                s.setClassNr(student.getClassNr());
                s.setAvgMark(student.getAvgMark());

                return;
            }
        }
        System.out.println("Not existing!");
    }

    public void updateProfesor(String nume_p, Profesor prof)
    {
        for ( Profesor p : profesori)
        {
            if (p.getName().equals(nume_p))
            {
                p.setName(prof.getName());
                p.setPhone(prof.getPhone());
                p.setEmail(prof.getEmail());
                p.setCourse(prof.getCourse());
                p.setYear(prof.getYear());

                return;
            }
        }
        System.out.println("Not existing!");
    }
}

