package com.archisacadeny.student;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentRepository {

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
}
