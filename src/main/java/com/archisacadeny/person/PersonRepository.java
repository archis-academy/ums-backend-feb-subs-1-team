package com.archisacadeny.person;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonRepository {

    // JDBC
    public static void createPersonTable() {
        try (Statement statement = DataBaseConnectorConfig.getConnection().createStatement()) {
            // SQL query for creating persons table
            String query = "DROP SEQUENCE IF EXISTS person_id_seq;" +
                    "CREATE SEQUENCE person_id_seq INCREMENT BY 1 MINVALUE 0 MAXVALUE 2147483647 START 1;" +
                    "CREATE TABLE IF NOT EXISTS persons(" +
                    "id INTEGER DEFAULT nextval('person_id_seq') PRIMARY KEY," +
                    "full_name VARCHAR(255)," +
                    "email VARCHAR(255)," +
                    "password VARCHAR(255))";

            statement.execute(query);
            System.out.println("Persons table has been created in the database..");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  Person save(Person person) { // creates a person with the given details
        String query = "INSERT INTO persons(full_name,password,email) VALUES(?,?,?)"; // query -> sorgu
        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.setString(1, person.getFullName());
            statement.setString(2, person.getPassword());
            statement.setString(3, person.getEmail());

            statement.execute();
            System.out.println("Person saved successfully with name: "+person.getFullName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }
}
