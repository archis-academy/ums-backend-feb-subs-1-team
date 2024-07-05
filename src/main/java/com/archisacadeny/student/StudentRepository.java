package com.archisacadeny.student;

import com.archisacadeny.config.DataBaseConnectorConfig;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentRepository {

    public static void createStudentTable() {
        try (Statement statement = DataBaseConnectorConfig.getConnection().createStatement()) {
            String query = "DROP SEQUENCE IF EXISTS student_id_seq;" +
                    "CREATE SEQUENCE student_id_seq INCREMENT BY 1 MINVALUE 0 MAXVALUE 2147483647 START 1;" +
                    "CREATE TABLE IF NOT EXISTS students(" +
                    "id INTEGER DEFAULT nextval('student_id_seq') PRIMARY KEY," +
                    "full_name VARCHAR(255) NOT NULL," +
                    "email VARCHAR(255) NOT NULL," +
                    "gender VARCHAR NOT NULL," +
                    "identity_no VARCHAR(11) UNIQUE NOT NULL," +
                    "enrollment_date DATE NOT NULL," +
                    "year_of_study INTEGER NOT NULL," +
                    "total_credit_count INTEGER DEFAULT 0" +
                    ");";

            statement.execute(query);
            System.out.println("Students table has been created in the database..");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStudent(int studentId) {
        String query = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.setLong(1, studentId);
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("The student has been deleted successfully!");
            } else {
                System.out.println("Student not found!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Student createStudent(Student student) {
        if (!validateEmailDuringStudentRegistration(student.getEmail())) {
            System.out.println("Email validation failed during student creation.");
            return student;
        }

        String query = "INSERT INTO students (full_name, email, gender, identity_no, enrollment_date, year_of_study, total_credit_count) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.setString(1, student.getFullName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getGender());
            statement.setString(4, student.getIdentityNo());
            statement.setTimestamp(5, student.getEnrollmentDate());
            statement.setInt(6, student.getYearOfStudy());
            statement.setInt(7, student.getTotalCreditCount());

            statement.executeUpdate();
            System.out.println("Student saved successfully with name: " + student.getFullName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return student;
    }

    public List<Map<String, Object>> getStudentAchievementReportData(int studentId) {
        String query = """
            SELECT s.id, s.full_name, csm.course_id, csm.grade
            FROM students s
            JOIN course_student_mapper csm ON s.id = csm.student_id
            WHERE s.id = ?;
        """;

        List<Map<String, Object>> results = new ArrayList<>();

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("id", resultSet.getLong("id"));
                    row.put("full_name", resultSet.getString("full_name"));
                    row.put("course_id", resultSet.getLong("course_id"));
                    row.put("grade", resultSet.getInt("grade"));
                    results.add(row);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving student achievement report data", e);
        }

        return results;
    }

    public int getCourseCredit(long courseId) throws SQLException {
        String query = "SELECT credits FROM courses WHERE Id = ?";
        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.setLong(1, courseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("credits");
                } else {
                    throw new IllegalArgumentException("Course with ID " + courseId + " not found.");
                }
            }
        }
    }

    public boolean validateEmailDuringStudentRegistration(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        if (!email.matches(emailRegex)) {
            System.out.println("Invalid email format: " + email);
            return false;
        }

        String query = "SELECT * FROM students WHERE email = ?";

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("A record was found in the database with this email: " + email);
                    return false;
                } else {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}