import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainEx1CitireChar {
    public static void main(String[] args) {
        List<List<String>> listOfLists = readDataFromFile("src/persoaneChar.txt");
        List<List<String>> duplicates = findDuplicates(listOfLists);

        // Print details of duplicate persons
        for (int k = 0; k < duplicates.size(); k += 2) {
            List<String> person1 = duplicates.get(k);
            List<String> person2 = duplicates.get(k + 1);
            System.out.println("Duplicates found:");
            System.out.println("Person 1: " + person1);
            System.out.println("Person 2: " + person2);
            System.out.println();
        }
    }

    public static List<List<String>> readDataFromFile(String filename) {
        List<List<String>> listOfLists = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> person = new ArrayList<>();
                String[] fields = line.split(";");

                //// verific validitatea datelor
                if (fields.length >= 2) {
                    for (int i = 0; i < fields.length; i++) {
                        person.add(fields[i]);
                    }

                    listOfLists.add(person);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfLists;
    }

    public static List<List<String>> findDuplicates(List<List<String>> listOfLists) {
        List<List<String>> duplicates = new ArrayList<>();
        for (int i = 0; i < listOfLists.size(); i++) {
            List<String> person1 = listOfLists.get(i);
            for (int j = i + 1; j < listOfLists.size(); j++) {
                List<String> person2 = listOfLists.get(j);

                // verific nume prenume
                if (person1.get(0).equals(person2.get(0)) && person1.get(1).equals(person2.get(1))) {
                    duplicates.add(person1);
                    duplicates.add(person2);
                    break;
                }
            }
        }
        return duplicates;
    }
}
