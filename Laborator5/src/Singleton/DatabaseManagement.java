package Singleton;
import java.util.ArrayList;
import java.util.List;
public class DatabaseManagement {
    private static DatabaseManagement databaseDebendencies;
    private List<String> dependenciesList = new ArrayList<>();

    private DatabaseManagement() {}

    public static DatabaseManagement getInstancedatabaseDebendencies(){
        if(databaseDebendencies == null){
            databaseDebendencies = new DatabaseManagement();
        }
        return databaseDebendencies;
    }

    public List<String> getDependenciesList() {
        return dependenciesList;
    }

    public void addString(String s){
        dependenciesList.add(s);
    }
}
