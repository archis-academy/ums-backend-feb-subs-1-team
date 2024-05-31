package com.archisacadeny.course;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.*;

public class CourseRepository {
    public static void createCourseTable(){
        try(Statement statement = DataBaseConnectorConfig.getConnection().createStatement())
        {
            String query = """
                    DROP SEQUENCE IF EXISTS course_id_seq;
                    CREATE SEQUENCE course_id_seq INCREMENT BY 1 MINVALUE 0 MAXVALUE 2147483647 START 1;
                    CREATE TABLE IF NOT EXISTS courses(
                    "id" INTEGER DEFAULT nextval('course_id_seq') PRIMARY KEY NOT NULL,
                    "name" VARCHAR(255) NOT NULL,
                    "number" VARCHAR(255) NOT NULL,
                    "credits" INTEGER,
                    "department" VARCHAR(255),
                    "max_students" INTEGER,
                    "instructor_id" INTEGER,
                    CONSTRAINT fk_instructor_id FOREIGN KEY (instructor_id) REFERENCES "public"."instructors"(id)
                    )
            """;
            statement.execute(query);
            System.out.println("Courses table has been created in the database..");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Course save(Course course){
        String query = "INSERT INTO courses(name,number,credits,department,max_students,instructor_id) VALUES(?,?,?,?,?,?)";
        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.setString(1,course.getCourseName());
            statement.setString(2,course.getCourseNumber());
            statement.setInt(3,course.getCredits());
            statement.setString(4,course.getDepartment());
            statement.setLong(5,course.getMaxStudents());
            statement.setLong(6,course.getInstructor().getId());

            statement.execute();
            System.out.println("Course has been saved successfully with name: "+course.getCourseName());

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return course;
    }

    public static void deleteCourse(long courseId) {
        String query = "DELETE FROM \"courses\"" +
                "WHERE id = '"+courseId+"'";
        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.execute();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void update(String courseNumber, Course course ){
        String query = String.format(
                "UPDATE courses SET name= '%1$s'," +
                        " number = '%2$s'," +
                        " instructor_id = '%3$s' " +
                        " credits = '%4$s' " +
                        " department = '%5$s' "+
                        " max_students = '%6$s'"+

                        "WHERE number= '"+courseNumber+"'",
                course.getCourseName(),
                course.getCourseNumber(),
                course.getInstructor().getId(),
                course.getCreditHours(),
                course.getDepartment(),
                course.getMaxStudents()
                );
        System.out.println(query);
        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.execute();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

}
