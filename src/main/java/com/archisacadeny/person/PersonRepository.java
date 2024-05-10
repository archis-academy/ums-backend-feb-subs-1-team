package com.archisacadeny.person;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.SQLException;
import java.sql.Statement;

public class PersonRepository {

    // JDBC
    public static void createPersonTable(){
        try(Statement statement = DataBaseConnectorConfig.getConnection().createStatement())
        {
            // SQL query for creating persons table
            String query = "CREATE TABLE IF NOT EXISTS persons(" +
                    "id INTEGER PRIMARY KEY," +
                    "full_name VARCHAR(255)," +
                    "email VARCHAR(255)," +
                    "password VARCHAR(255))";

            statement.execute(query);
            System.out.println("Persons table has been created in the database..");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
