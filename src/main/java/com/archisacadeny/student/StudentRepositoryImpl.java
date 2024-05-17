package com.archisacadeny.student;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class StudentRepositoryImpl implements StudentRepository {

    @Override
    public List<Student> findByCourseId(int courseId) {
        List<Student> students = new ArrayList<>();

        String sql = """
                SELECT Id, FullName, Gender, CourseID, IdentityNo, UnvEnrollmentDate, YearOfStudy, TotalCreditCount
                FROM Students
                JOIN Enrollments ON Students.Id = Enrollments.StudentID
                WHERE Enrollments.CourseID = ?
                """;

        try (Connection connection = DataBaseConnectorConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, courseId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Student student = new Student(
                            resultSet.getInt("Id"),
                            resultSet.getString("FullName"),
                            resultSet.getString("Gender"),
                            resultSet.getInt("CourseID"),
                            resultSet.getString("IdentityNo"),
                            resultSet.getDate("UnvEnrollmentDate").toLocalDate(),
                            resultSet.getInt("YearOfStudy"),
                            resultSet.getInt("TotalCreditCount")
                    );
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }
}