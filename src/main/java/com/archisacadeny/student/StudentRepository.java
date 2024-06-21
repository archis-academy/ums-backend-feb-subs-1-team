package com.archisacadeny.student;

import com.archisacadeny.config.DataBaseConnectorConfig;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        SELECT s.Id, s.FullName, c.Id AS CourseId, c.CourseName, e.Grade
        FROM students s
        JOIN enrolled_courses e ON s.Id = e.studentId
        JOIN courses c ON e.courseId = c.Id
        WHERE s.Id = ?
        """;

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new IllegalArgumentException("Student with ID " + studentId + " not found.");
                }

                long studentIdFromDb = resultSet.getLong("Id");
                String studentName = resultSet.getString("FullName");

                List<CourseGrade> courseGrades = new ArrayList<>();
                int totalGradePoints = 0;
                int totalCredits = 0;
                double gpa = 0.0;

                do {
                    long courseId = resultSet.getLong("CourseId");
                    String courseName = resultSet.getString("CourseName");
                    int grade = resultSet.getInt("Grade");
                    int courseCredit = getCourseCredit(courseId);

                    courseGrades.add(new CourseGrade(courseId, courseName, grade));
                    totalGradePoints += grade * courseCredit;
                    totalCredits += courseCredit;

                } while (resultSet.next());
                gpa = totalCredits > 0 ? (double) totalGradePoints / totalCredits : 0.0;

                return new StudentReport(studentIdFromDb, studentName,  gpa, courseGrades);

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