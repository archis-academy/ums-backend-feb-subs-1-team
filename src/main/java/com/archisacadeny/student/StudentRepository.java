package com.archisacadeny.student;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.SQLException;
import java.sql.Statement;

public class StudentRepository {

    public static void createStudentTable(){
        try(Statement statement = DataBaseConnectorConfig.getConnection().createStatement())
        {
            // SQL query for creating students table
            String query = "CREATE TABLE IF NOT EXISTS students(" +
                    "studentId INTEGER PRIMARY KEY," +
                    "firstName VARCHAR(255)," +
                    "secondName VARCHAR(255)," +
                    "gender  VARCHAR(255),"+
                    "courseId  LONG," +
                    "identityNo VARCHAR(255),"+
                    "unvEnrollmentDate VARCHAR(255),"+
                    "yearOfStudy LONG,"+
                    "totalCreditCount INTEGER)";

            statement.execute(query);
            System.out.println("Students table has been created in the database..");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
