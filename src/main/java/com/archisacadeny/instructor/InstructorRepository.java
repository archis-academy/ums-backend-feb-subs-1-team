package com.archisacadeny.instructor;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.SQLException;
import java.sql.Statement;

public class InstructorRepository {
    public static void createInstructorTable(){
        try(Statement statement = DataBaseConnectorConfig.getConnection().createStatement())
        {
            // SQL query for creating persons table
            String query = "CREATE TABLE IF NOT EXISTS instructors(" +
                    "instructorId INTEGER PRIMARY KEY," +
                    "firstName VARCHAR(255)," +
                    "secondName VARCHAR(255)," +
                    "gender VARCHAR(255) " +
                    "courseId INTEGER," +
                    "department VARCHAR(255),)";

            statement.execute(query);
            System.out.println("Persons table has been created in the database..");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
