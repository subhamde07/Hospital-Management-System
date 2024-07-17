package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private final Connection connection;
    private final Scanner scanner;
    Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }
    public void addPatients(){
        System.out.print("Enter Patient Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Enter Patient Age: ");
        int age = scanner.nextInt();
        System.out.print("Enter Patient Gender: ");
        String gender = scanner.next();

        try {
            String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);

            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0) {
                System.out.print("Patient added successfully.");
            } else{
                System.out.print("Failed to add patient.");
            }

        } catch (SQLException e){
            e.printStackTrace(System.out);
        }
    }

    public void viewPatients(){
        String query = "SELECT * FROM patients";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            // Formation Table Output.
            System.out.println("Patients Details: ");
            System.out.println("+---------+----------------------+-----------+--------------+");
            System.out.println("| Id      | Name                 | Age       | Gender       |");
            System.out.println("+---------+----------------------+-----------+--------------+");
            while ( resultSet.next() ){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-7s | %-20s | %-9s | %-12s |\n", id, name, age, gender);
                System.out.println("+---------+----------------------+-----------+--------------+");
            }

        }catch (SQLException e){
            e.printStackTrace(System.out);
        }
    }

    public boolean getPatientStatus(int id){
        String query = "SELECT * FROM patients WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet resultSet = ps.executeQuery();

            return resultSet.next();

        }catch (SQLException e){
            e.printStackTrace(System.out);
        }
        return false;
    }
}
