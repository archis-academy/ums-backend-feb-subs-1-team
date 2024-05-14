package com.archisacadeny.course;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.SQLException;
import java.sql.Statement;

public class CourseRepository {
    public static void createCourseTable(){
        try(Statement statement = DataBaseConnectorConfig.getConnection().createStatement())
        {
            // SQL query for creating courses table
            String query = "CREATE TABLE IF NOT EXISTS courses(" +
                    "courseId INTEGER PRIMARY KEY," +
                    "courseTitle VARCHAR(255)," +
                    "creditHours INTEGER," +
                    "gradeScale INTEGER," +
                    "department VARCHAR(255)," +
                    "instructorId LONG,)";

            statement.execute(query);
            System.out.println("Courses table has been created in the database..");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
