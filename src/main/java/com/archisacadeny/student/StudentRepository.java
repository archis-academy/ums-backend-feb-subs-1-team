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

    public Student viewStudentDetails(long studentid) {
        String query = "SELECT * FROM students WHERE id = ?";
        Student student = new Student();
        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.setLong(1, studentid);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                student.setFullName(resultSet.getString("full_name"));
                student.setGender(resultSet.getString("gender"));
                student.setIdentityNo(resultSet.getString("identity_no"));
                student.setEnrollmentDate(resultSet.getTimestamp("enrollment_date"));
                student.setYearOfStudy(resultSet.getInt("year_of_study"));
                student.setTotalCreditCount(resultSet.getInt("total_credit_count"));
            }
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }
    public Student getStudentByID(Long studentId){
        String query = "SELECT * FROM students WHERE id = ?";
        Student student = new Student();

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.setLong(1,studentId);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    student.setId(resultSet.getLong("id"));
                    student.setFullName(resultSet.getString("full_name"));
                    student.setGender(resultSet.getString("gender"));
                    student.setEnrollmentDate(resultSet.getTimestamp("enrollment_date"));
                    student.setIdentityNo(resultSet.getString("identity_no"));
                    student.setYearOfStudy(resultSet.getInt("year_of_study"));
                    student.setTotalCreditCount(resultSet.getInt("total_credit_count"));
                }else {
                    throw new RuntimeException("Student can not be found!!" + studentId);
                }


            }


        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return student;
    }
    public Student updateStudentInfo(Student student) {
        String query = "UPDATE students SET full_name = ?, gender = ?, identity_no = ?, enrollment_date = ? WHERE id = ?";
        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {

            statement.setString(1, student.getFullName());
            statement.setString(2, student.getGender());
            statement.setString(3, student.getIdentityNo());
            statement.setTimestamp(4, student.getEnrollmentDate());
            statement.setLong(5, student.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("The Student you have reach by id has been update" + student.getId());
            } else {
                throw new RuntimeException("Update has failed!! The Student has not been found!!" + student.getId());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return student;


    }

    public List<Student> listAllStudents(Student student){
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                student.setId(resultSet.getLong("id"));
                student.setFullName(resultSet.getString("full_name"));
                student.setGender(resultSet.getString("gender"));
                student.setIdentityNo(resultSet.getString("identity_no"));
                student.setEnrollmentDate(resultSet.getTimestamp("enrollment_date"));
                student.setTotalCreditCount(resultSet.getInt("total_credit_count"));
                student.setYearOfStudy(resultSet.getInt("year_of_study"));
                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

}