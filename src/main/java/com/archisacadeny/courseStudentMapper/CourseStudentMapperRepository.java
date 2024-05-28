package com.archisacadeny.courseStudentMapper;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.SQLException;
import java.sql.Statement;

public class CourseStudentMapperRepository {

    public static void createCourseStudentTable(){
        try(Statement statement = DataBaseConnectorConfig.getConnection().createStatement())
        {
            // SQL query for creating persons table
            String query = "CREATE TABLE IF NOT EXISTS coursestudent(" +
                    "id INTEGER PRIMARY KEY," +
                    "course_id INTEGER," +
                    "student_id INTEGER," ;
            statement.execute(query);
            System.out.println("CourseStudent table has been created in the database..");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
