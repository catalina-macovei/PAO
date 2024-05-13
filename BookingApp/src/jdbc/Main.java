package jdbc;

import utils.FileManagement;

import java.sql.*;

// pentru testare

public class Main {
    public static void main(String[] args) {

        String sql = "SELECT * FROM test";
        int id;
        try(Connection conn =
                    DriverManager.getConnection("jdbc:mysql://localhost/booking",
                            "root","root");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        ) {

            while (resultSet.next()) {
                id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                System.out.println("id = " + id
                        + " nume = " + nume);
                String persoana = id + ";" + nume ;
                FileManagement.scriereFisierChar("persoane.csv",
                        persoana);
            }


        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
        }
    }
}