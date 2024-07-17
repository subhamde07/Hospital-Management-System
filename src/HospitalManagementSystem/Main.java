package HospitalManagementSystem;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "root";

    public static boolean checkDoctorAvailability(int doctorId, String date, Connection connection){
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, doctorId);
            ps.setString(2, date);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                return count == 0;
            }
        } catch (SQLException e){
            e.printStackTrace(System.out);
        }
        return false;
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner sc){
        System.out.println("Enter Patient Id: ");
        int patientId = sc.nextInt();
        System.out.println("Enter Doctor Id: ");
        int doctorId = sc.nextInt();
        System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
        String date = sc.next();

        if(patient.getPatientStatus(patientId) && doctor.getDoctorStatus(doctorId)){
            if(checkDoctorAvailability(doctorId, date, connection)){
                String query = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
                try{
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, patientId);
                    ps.setInt(2, doctorId);
                    ps.setString(3, date);
                    int affectedRow = ps.executeUpdate();
                    if(affectedRow > 0){
                        System.out.println("Appointment Booked successfully.");
                    } else {
                        System.out.println("Appointment Booked Failed!!.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace(System.out);
                }
            } else {
                System.out.println("Doctor is not available!!. Please choose other Date.");
            }
        } else {
            System.out.println("Doctor or Patient not Exist!!.");
        }
    }

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(System.out);
        }

        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);

            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient.");
                System.out.println("2. View Patient.");
                System.out.println("3. View Doctor.");
                System.out.println("4. Book Appointment.");
                System.out.println("5. Exit.");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        patient.addPatients();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break;
                    case 5:
                        System.out.println("Thank You!!");
                        return;
                    default:
                        System.out.println("Enter a valid choice.");
                        System.out.println();
                        break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
