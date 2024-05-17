package com.archisacadeny.course;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.SQLException;
import java.sql.Statement;

public class CourseRepository {

    public static void createCourseTable(){
        try(Statement statement = DataBaseConnectorConfig.getConnection().createStatement())
        {
            // SQL query for creating persons table
            String query = "CREATE TABLE IF NOT EXISTS courses(" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(255)," +
                    "number VARCHAR(255)," +
                    "instructor_id VARCHAR(255))";

            System.out.println(query);
            statement.execute(query);
            System.out.println("Courses table has been created in the database..");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
