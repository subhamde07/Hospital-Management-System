package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
    private final Connection connection;
    Doctor(Connection connection){
        this.connection = connection;
    }

    public void viewDoctors(){
        String query = "SELECT * FROM doctors";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            // Formation Table Output.
            System.out.println("Doctors Details: ");
            System.out.println("+---------+----------------------+--------------+-------------------------+");
            System.out.println("| Id      | Name                 | Gender       | Specialization          |");
            System.out.println("+---------+----------------------+--------------+-------------------------+");
            while ( resultSet.next() ){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-7s | %-20s | %-12s | %-23s |\n", id, name, gender, specialization);
                System.out.println("+---------+----------------------+--------------+-------------------------+");
            }

        }catch (SQLException e){
            e.printStackTrace(System.out);
        }
    }

    public boolean getDoctorStatus(int id){
        String query = "SELECT * FROM doctors WHERE id = ?";
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
