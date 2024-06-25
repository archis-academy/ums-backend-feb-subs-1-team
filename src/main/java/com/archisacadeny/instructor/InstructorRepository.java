package com.archisacadeny.instructor;

import com.archisacadeny.config.DataBaseConnectorConfig;
import com.archisacadeny.course.Course;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InstructorRepository {

    public static void createInstructorTable(){
        try(Statement statement= DataBaseConnectorConfig.getConnection().createStatement()){
            //SQL query for creating instructors table
            String InstructorTableQuery= "DROP SEQUENCE IF EXISTS instructor_id_seq;" +
                    "CREATE SEQUENCE instructor_id_seq INCREMENT BY 1 MINVALUE 0 MAXVALUE 2147483647 START 1;"+
                    "CREATE TABLE IF NOT EXISTS instructors(" +
                    "id INTEGER DEFAULT nextval('instructor_id_seq') PRIMARY KEY," +
                    "full_name VARCHAR(255),"+
                    "number VARCHAR(255),"+
                    "email VARCHAR(255),"+
                    "password VARCHAR(255))";


            statement.execute(InstructorTableQuery);
            System.out.println("Instructor table has been created in the database..");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Instructor save (Instructor instructor){
        String query = "INSERT INTO instructors (full_name,number,email,password) VALUES(?,?,?,?)";
       try(PreparedStatement statement= DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
           statement.setString(1, instructor.getFullName());
           statement.setString(2, instructor.getNumber());
           statement.setString(3, instructor.getEmail());
           statement.setString(4, instructor.getPassword());

           statement.execute();
           System.out.println("Instructor saved successfully with name: " + instructor.getFullName());
       }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return instructor;
    }


    public List<Instructor> listAllInstructors(){
        List<Instructor> instructors = new ArrayList<>();
        String query = "SELECT * FROM instructors";
        try(Statement statement= DataBaseConnectorConfig.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            while(resultSet.next()) {
                Instructor instructor = new Instructor();
                instructor.setId(resultSet.getLong("id"));
                instructor.setFullName(resultSet.getString("full_name"));
                instructor.setNumber(resultSet.getString("number"));
                instructor.setEmail(resultSet.getString("email"));
                instructor.setPassword(resultSet.getString("password"));
                instructors.add(instructor);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return instructors;
    }

    public List<Course> getCoursesByInstructorId(long instructorId){
        List <Course> courses = new ArrayList<>();
        String query= "SELECT * FROM courses WHERE instructor_id=?";

        try(PreparedStatement statement= DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.setLong(1, instructorId);
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    Course course = new Course();
                    course.setId(resultSet.getLong("id"));
                    course.setCourseName(resultSet.getString("name"));
                    course.setCourseNumber(resultSet.getString("number"));
                    course.setCredit(resultSet.getInt("credits"));
                    course.setDepartment(resultSet.getString("department"));
                    course.setMaxStudents(resultSet.getInt("max_students"));
                    course.setInstructor(InstructorRepository.getInstructorById(instructorId));
                    courses.add(course);
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return courses;
    }

}
