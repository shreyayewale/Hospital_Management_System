package com.hms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagement {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {

            while (true) {
                System.out.println("****Hospital Management System****");
                System.out.println("1. Add Patient");
                System.out.println("2. Add Doctor");
                System.out.println("3. View Patients");
                System.out.println("4. View Doctors");
                System.out.println("5. Book Appointment");
                System.out.println("6. Update Patient");
                System.out.println("7. Update Doctor");
                System.out.println("8. Delete Patient");
                System.out.println("9. Delete Doctor");
                System.out.println("10. Exit");

                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        addPatient(connection);
                        break;
                    case 2:
                        addDoctor(connection);
                        break;
                    case 3:
                        viewPatients(connection);
                        break;
                    case 4:
                        viewDoctors(connection);
                        break;
                    case 5:
                        bookAppointment(connection);
                        break;
                    case 6:
                        updatePatient(connection);
                        break;
                    case 7:
                        updateDoctor(connection);
                        break;
                    case 8:
                        deletePatient(connection);
                        break;
                    case 9:
                        deleteDoctor(connection);
                        break;
                    case 10:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Please enter a valid choice.");
                        break;
                }
            }

        } finally {
            sc.close();
        }
    }

    private static void addPatient(Connection connection) throws SQLException {
        System.out.println("Enter patient details:");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("DOB (YYYY-MM-DD): ");
        String dob = sc.nextLine();
        System.out.print("Gender: ");
        String gender = sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();
        System.out.print("Contact Number: ");
        String contact = sc.nextLine();

        String sql = "INSERT INTO patients (name, dob, gender, address, contact) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, dob);
            stmt.setString(3, gender);
            stmt.setString(4, address);
            stmt.setString(5, contact);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient added successfully.\n");
            } else {
                System.out.println("Failed to add patient.");
            }
        }
    }

    private static void addDoctor(Connection connection) throws SQLException {
        System.out.println("Enter doctor details:");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Specialization: ");
        String specialization = sc.nextLine();
        System.out.print("Contact Number: ");
        String contact = sc.nextLine();

        String sql = "INSERT INTO doctors (name, specialization, contact) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, specialization);
            stmt.setString(3, contact);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Doctor added successfully.\n");
            } else {
                System.out.println("Failed to add doctor.");
            }
        }
    }

    private static void viewPatients(Connection connection) throws SQLException {
        String sql = "SELECT * FROM patients";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Patients:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String dob = rs.getString("dob");
                String gender = rs.getString("gender");
                String address = rs.getString("address");
                String contact = rs.getString("contact");

                System.out.println("ID: " + id + ", Name: " + name + ", DOB: " + dob + ", Gender: " + gender
                        + ", Address: " + address + ", Contact: " + contact);
            }
        }
    }

    private static void viewDoctors(Connection connection) throws SQLException {
        String sql = "SELECT * FROM doctors";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Doctors:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                String contact = rs.getString("contact");

                System.out.println("ID: " + id + ", Name: " + name + ", Specialization: " + specialization
                        + ", Contact: " + contact);
            }
        }
    }

    private static void bookAppointment(Connection connection) throws SQLException {
        System.out.println("Enter appointment details:");
        System.out.print("Patient ID: ");
        int patientId = sc.nextInt();
        sc.nextLine(); // Consume newline character
        System.out.print("Doctor ID: ");
        int doctorId = sc.nextInt();
        sc.nextLine(); // Consume newline character
        System.out.print("Appointment Date (YYYY-MM-DD): ");
        String appointmentDate = sc.nextLine();

        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            stmt.setInt(2, doctorId);
            stmt.setString(3, appointmentDate);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Appointment booked successfully.");
            } else {
                System.out.println("Failed to book appointment.");
            }
        }
    }

    private static void updatePatient(Connection connection) throws SQLException {
        System.out.println("Enter the ID of the patient to update:");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline character

        System.out.println("Enter new details for the patient:");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("DOB (YYYY-MM-DD): ");
        String dob = sc.nextLine();
        System.out.print("Gender: ");
        String gender = sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();
        System.out.print("Contact Number: ");
        String contact = sc.nextLine();

        String sql = "UPDATE patients SET name = ?, dob = ?, gender = ?, address = ?, contact = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, dob);
            stmt.setString(3, gender);
            stmt.setString(4, address);
            stmt.setString(5, contact);
            stmt.setInt(6, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient updated successfully.\n");
            } else {
                System.out.println("Failed to update patient.");
            }
        }
    }

    private static void updateDoctor(Connection connection) throws SQLException {
        System.out.println("Enter the ID of the doctor to update:");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline character

        System.out.println("Enter new details for the doctor:");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Specialization: ");
        String specialization = sc.nextLine();
        System.out.print("Contact Number: ");
        String contact = sc.nextLine();

        String sql = "UPDATE doctors SET name = ?, specialization = ?, contact = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, specialization);
            stmt.setString(3, contact);
            stmt.setInt(4, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Doctor updated successfully.\n");
            } else {
                System.out.println("Failed to update doctor.");
            }
        }
    }

    private static void deletePatient(Connection connection) throws SQLException {
        System.out.println("Enter the ID of the patient to delete:");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline character

        String sql = "DELETE FROM patients WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient deleted successfully.\n");
            } else {
                System.out.println("Failed to delete patient.");
            }
        }
    }

    private static void deleteDoctor(Connection connection) throws SQLException {
        System.out.println("Enter the ID of the doctor to delete:");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline character

        String sql = "DELETE FROM doctors WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Doctor deleted successfully.\n");
            } else {
                System.out.println("Failed to delete doctor.");
            }
        }
    }
}
