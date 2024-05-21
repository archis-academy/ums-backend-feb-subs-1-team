package com.archisacadeny.student;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.SQLException;
import java.sql.Statement;

public class StudentRepository {

    public static void createStudentTable(){
        try(Statement statement = DataBaseConnectorConfig.getConnection().createStatement()) {
            String query = """
                    DROP SEQUENCE IF EXISTS student_id_seq;
                    CREATE SEQUENCE student_id_seq INCREMENT BY 1 MINVALUE 0 MAXVALUE 2147483647 START 1;
                    
                    CREATE TABLE IF NOT EXISTS students(
                        "id" INTEGER DEFAULT nextval('student_id_seq') PRIMARY KEY NOT NULL,
                        "full_name" VARCHAR(255) NOT NULL,
                        "email" VARCHAR(255),
                        "password" VARCHAR(255)
                    )
                    """;
            statement.execute(query);
            System.out.println("Student table created successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createStudentCourseMapperTable(){
        try(Statement statement = DataBaseConnectorConfig.getConnection().createStatement()){
            String query = """
                    DROP SEQUENCE IF EXISTS s_c_mapper_id;
                    CREATE SEQUENCE s_c_mapper_id INCREMENT BY 1 MINVALUE 0 MAXVALUE 2147483647 START 1;
                    
                    CREATE TABLE IF NOT EXISTS course_student_mappers(
                        "id" INTEGER DEFAULT nextval('s_c_mapper_id') PRIMARY KEY NOT NULL,
                        "course_id" INTEGER NOT NULL,
                        "student_id" INTEGER NOT NULL,
                        CONSTRAINT fk_course_id FOREIGN KEY (course_id) REFERENCES "public"."courses"(id),
                        CONSTRAINT fk_student_id FOREIGN KEY (course_id) REFERENCES "public"."students"(id)
                    )
                    """;
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
