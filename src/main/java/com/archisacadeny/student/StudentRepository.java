package com.archisacadeny.student;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.*;

public class StudentRepository {

    public static void createStudentTable() {
        try (Statement statement = DataBaseConnectorConfig.getConnection().createStatement()) {
            String query = "DROP SEQUENCE IF EXISTS student_id_seq;" +
                    "CREATE SEQUENCE student_id_seq INCREMENT BY 1 MINVALUE 0 MAXVALUE 2147483647 START 1;" +
                    "CREATE TABLE IF NOT EXISTS students(" +
                    "Id INTEGER DEFAULT nextval('student_id_seq') PRIMARY KEY," +
                    "FullName VARCHAR(255) NOT NULL," +
                    "Gender CHAR NOT NULL," +
                    "IdentityNo VARCHAR(11) UNIQUE NOT NULL," +
                    "EnrollmentDate DATE NOT NULL," +
                    "YearOfStudy INTEGER NOT NULL," +
                    "EnrolledCourses INTEGER[]," +
                    "TotalCreditCount INTEGER DEFAULT 0" +
                    ");";

            statement.execute(query);
            System.out.println("Students table has been created in the database..");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStudent(int studentId){
        String query = "DELETE FROM students WHERE Id = ?";
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
}
