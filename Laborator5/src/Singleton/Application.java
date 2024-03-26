package Singleton;

public class Application {
    public static void main(String[] args) {
        DatabaseManagement databaseManagement = DatabaseManagement.getInstancedatabaseDebendencies();

        databaseManagement.addString("Mysql");
        databaseManagement.addString("Postgres");

        System.out.println(databaseManagement.getDependenciesList());
    }
}
