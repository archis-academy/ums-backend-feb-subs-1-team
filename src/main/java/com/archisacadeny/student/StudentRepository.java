package com.archisacadeny.student;

import com.archisacadeny.config.DataBaseConnectorConfig;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentRepository {

    public static void createStudentTable() {
        try (Statement statement = DataBaseConnectorConfig.getConnection().createStatement()) {
            String query = "DROP SEQUENCE IF EXISTS student_id_seq;" +
                    "CREATE SEQUENCE student_id_seq INCREMENT BY 1 MINVALUE 0 MAXVALUE 2147483647 START 1;" +
                    "CREATE TABLE IF NOT EXISTS students(" +
                    "id INTEGER DEFAULT nextval('student_id_seq') PRIMARY KEY," +
                    "full_name VARCHAR(255) NOT NULL," +
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
        String query = "INSERT INTO students (full_name, gender, identity_no, enrollment_date, year_of_study, total_credit_count) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.setString(1, student.getFullName());
            statement.setString(2, student.getGender());
            statement.setString(3, student.getIdentityNo());
            statement.setTimestamp(4, student.getEnrollmentDate());
            statement.setInt(5, student.getYearOfStudy());
            statement.setInt(6, student.getTotalCreditCount());

            statement.executeUpdate();
            System.out.println("Student saved successfully with name: " + student.getFullName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return student;
    }

    public StudentReport generateStudentAchievementReport(int studentId) {
        String query = """
        SELECT s.id, s.full_name, csm.course_id, csm.grade
        FROM students s
        JOIN course_student_mapper csm ON s.id = csm.student_id
        WHERE s.id = ?;
    """;

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new IllegalArgumentException("Student with ID " + studentId + " not found.");
                }

                long studentIdFromDb = resultSet.getLong("id");
                String studentName = resultSet.getString("full_name");
                double totalWeightedGradePoints = 0.0;
                int totalCredits = 0;

                do {
                    long courseId = resultSet.getLong("course_id");
                    int grade = resultSet.getInt("grade");
                    int courseCredit = getCourseCredit(courseId);
                    totalWeightedGradePoints += grade * courseCredit;
                    totalCredits += courseCredit;
                } while (resultSet.next());

                double gpa = totalCredits > 0 ? totalWeightedGradePoints / totalCredits : 0.0;

                return new StudentReport(studentIdFromDb, studentName, gpa);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error generating student achievement report", e);
        }
    }

    private int getCourseCredit(long courseId) throws SQLException {
        String query = "SELECT Credits FROM courses WHERE Id = ?";
        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.setLong(1, courseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("Credits");
                } else {
                    throw new IllegalArgumentException("Course with ID " + courseId + " not found.");
                }
            }
        }
    }

}