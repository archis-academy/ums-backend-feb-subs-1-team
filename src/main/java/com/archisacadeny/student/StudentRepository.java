package com.archisacadeny.student;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.SQLException;
import java.sql.Statement;

public class StudentRepository {
    public static void sutendtTable(){

        try(Statement statement = DataBaseConnectorConfig.getConnection().createStatement())
        {
            String query = "CREATE TABLE IF NOT EXITS students" +
                    "id INTEGER PRIMARY KEY," +
                    "full_name VARCHAR(255)," +
                    "email VARCHAR(255)," +
                    "password VARCHAR(255)," +
                    "enrolled_course VARCHAR(255)";

            statement.execute(query);
            System.out.println("Student table has been created in the database...");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

