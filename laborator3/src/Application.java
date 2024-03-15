import model.Student;
import model.Profesor;
import service.StorageService;
import java.util.Scanner;

public class Application {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        //instantiem clasa
        StorageService storageService = new StorageService();

        while (run) {
            System.out.println("Available commands:\ncreate\nread\nupdate\ndelete\nquit\nEnter command:");
            String operationType = scanner.nextLine();

            switch (operationType.toLowerCase()) {
                case "create":
                    System.out.println("Command received: create");
                    System.out.println("Enter type of person:\n");
                    String personType = scanner.nextLine();
                    System.out.println("Command received: " + personType);

                    System.out.println("Name:\n");
                    String Name = scanner.nextLine();

                    // verific daca exista obiect - student/profesor cu acelasi nume
                    int find_rez1 = storageService.Find(Name);
                    if (find_rez1 < 1)
                    {
                        System.out.print("phone number: ");
                        String Phone = scanner.nextLine();
                        System.out.print("email: ");
                        String Email = scanner.nextLine();

                        if ("student".equalsIgnoreCase(personType))
                        {
                            System.out.print("student number: ");
                            int studentNumber = scanner.nextInt();
                            System.out.print("average mark: ");
                            double avgMark = scanner.nextDouble();
                            System.out.print("class number: ");
                            int classNr = scanner.nextInt();
                            scanner.nextLine();

                            // Creare stduent
                            Student newStudent = new Student(Name, Phone, Email, studentNumber, avgMark, classNr);

                            storageService.addStudent(newStudent);

                        }
                        else if ("profesor".equalsIgnoreCase(personType))
                        {
                            System.out.print("course: ");
                            String profCourse = scanner.nextLine();
                            System.out.print("year: ");
                            int year = scanner.nextInt();
                            scanner.nextLine();
                            Profesor newProfesor = new Profesor(Name, Phone, Email, profCourse, year);

                            storageService.addProfessor(newProfesor);
                        }
                    }
                    else
                    {
                        System.out.println("Already existing!");
                    }
                    break;

                case "read":
                    System.out.print("Nume: ");
                    String nume1 = scanner.nextLine();
                    storageService.show(nume1);

                    break;

                case "update":
                    System.out.print("Nume: ");
                    String nume2 = scanner.nextLine();
                    int find_rez = storageService.Find(nume2);  // in functie de care pers am gasit, fac update

                    if(find_rez > 0)
                    {
                        // campuri comune
                        System.out.println("Name update:\n");
                        String Name1 = scanner.nextLine();
                        System.out.print("phone number update: ");
                        String Phone1 = scanner.nextLine();
                        System.out.print("email update: ");
                        String Email1 = scanner.nextLine();

                        if (find_rez == 2)
                        {
                            System.out.print("student number: ");
                            int studentNumber1 = scanner.nextInt();
                            System.out.print("average mark: ");
                            double avgMark1 = scanner.nextDouble();
                            System.out.print("class number: ");
                            int classNr1 = scanner.nextInt();
                            scanner.nextLine();
                            // Creare student nou cu valorile schimbate
                            Student newStudent1 = new Student(Name1, Phone1, Email1, studentNumber1, avgMark1, classNr1);
                            storageService.updateStudent(nume2, newStudent1);
                        }
                        if (find_rez == 1)
                        {
                            System.out.print("course update: ");
                            String profCourse1 = scanner.nextLine();
                            System.out.print("year update: ");
                            int year1 = scanner.nextInt();
                            scanner.nextLine();

                            Profesor newProfesor1 = new Profesor(Name1, Phone1, Email1, profCourse1, year1);
                            storageService.updateProfesor(nume2, newProfesor1);
                        }
                    }
                    else
                    {
                        System.out.println("Not existing!");
                    }

                    break;

                case "delete":
                    System.out.print("Nume: ");
                    String nume3 = scanner.nextLine();
                    storageService.remove(nume3);
                    break;

                case "quit":
                    System.out.println("Quitting application. Goodbye!");
                    run = false;
                    break;

                default:
                    System.out.println("Invalid operation type. Please try again.");
                    break;
            }
        }

        scanner.close();
    }
}
