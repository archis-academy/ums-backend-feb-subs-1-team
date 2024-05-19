package com.archisacadeny.student;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.SQLException;
import java.sql.Statement;

public class StudentRepository {
    public static void createStudentTable(){

        try(Statement statement = DataBaseConnectorConfig.getConnection().createStatement())
        {
            String query = "DROP SEQUENCE IF EXITS student_id_seq;"+
                    "CREATE SEQUENCE student_id_seq INCREMENT BY 1 MINVALUE 0 MAXVALUE 2147483647 START 1;"+
                    "CREATE TABLE IF NOT EXITS students(" +
                    "id INTEGER DEFAULT maxvalue('student_id_seq') PRIMARY KEY," +
                    "full_name VARCHAR(255)," +
                    "email VARCHAR(255)," +
                    "gender VARCHAR(255),"+
                    "password VARCHAR(255)," +
                    "student_number INTEGER,"+
                    "department VARCHAR(255),"+
                    "gender VARCHAR(255),"+
                    "enrolled_course VARCHAR(255))";

            statement.execute(query);
            System.out.println("Student table has been created in the database...");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

