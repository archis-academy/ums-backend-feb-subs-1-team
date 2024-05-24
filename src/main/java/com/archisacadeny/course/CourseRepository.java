package com.archisacadeny.course;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CourseRepository {
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
                "UPDATE courses SET name= '%1$s', number = '%2$s', instructor_id = '%3$s' WHERE number= '"+courseNumber+"'",
                course.getCourseName(),course.getCourseNumber(),course.getInstructorId());
        System.out.println(query);
        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.execute();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

}