package com.archisacadeny.student;

import com.archisacadeny.config.DataBaseConnectorConfig;
import com.archisacadeny.course.Course;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

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

    public List<String> getCourseNameById(long studentId) {
        List<String> courseNames = new ArrayList<>();
        String query = "SELECT c.name " +
                "FROM course_student_mapper m " +
                "JOIN courses c ON m.course_id = c.id " +
                "WHERE m.student_id = ?";

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query))
        {

            statement.setLong(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    courseNames.add(resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving recommended courses for student", e);
        }

        return courseNames;
    }

    public List<Course> listRecommendedCoursesForStudent(long studentId) {
        List<Course> recommendedCourses = new ArrayList<>();
        List<String> courseNames = getCourseNameById(studentId);

        StringJoiner joiner = new StringJoiner(",", "'", "'");
        for (String name : courseNames) {
            joiner.add(name);
        }

        String query = "SELECT * FROM courses WHERE name IN (";
        query += String.join(",", courseNames.stream().map(name -> "?").toArray(String[]::new)) + ")";

        try (Connection connection = DataBaseConnectorConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (int i = 0; i < courseNames.size(); i++) {
                statement.setString(i + 1, courseNames.get(i));
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setCourseName(resultSet.getString("name"));
                    course.setDepartment(resultSet.getString("department"));
                    course.setCredit(resultSet.getInt("credits"));
                    recommendedCourses.add(course);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving recommended courses", e);
        }

        return recommendedCourses;
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
    public Student getStudentByID(long studentId){
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

    public List<Student> listAllStudents(){
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Student student = new Student();
                student.setId(resultSet.getLong("id"));
                student.setFullName(resultSet.getString("full_name"));
                student.setGender(resultSet.getString("gender"));
                student.setIdentityNo(resultSet.getString("identity_no"));
                student.setEnrollmentDate(resultSet.getTimestamp("enrollment_date"));
                student.setTotalCreditCount(resultSet.getInt("total_credit_count"));
                student.setYearOfStudy(resultSet.getInt("year_of_study"));
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}