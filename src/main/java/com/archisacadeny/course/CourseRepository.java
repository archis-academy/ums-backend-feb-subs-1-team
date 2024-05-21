package com.archisacadeny.course;

import com.archisacadeny.config.DataBaseConnectorConfig;
import com.archisacadeny.person.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CourseRepository {

    public static void createCourseTable(){
        try(Statement statement = DataBaseConnectorConfig.getConnection().createStatement()) {
            String query = """
                    DROP SEQUENCE IF EXISTS course_id_seq;
                    CREATE SEQUENCE course_id_seq INCREMENT BY 1 MINVALUE 0 MAXVALUE 2147483647 START 1;
                    
                    CREATE TABLE IF NOT EXISTS courses(
                        "id" INTEGER DEFAULT nextval('course_id_seq') PRIMARY KEY NOT NULL,
                        "title" VARCHAR(255) NOT NULL,
                        "instructor_id" INTEGER,
                         CONSTRAINT fk_person_id FOREIGN KEY (instructor_id) REFERENCES "public"."persons"(id)
                    )
                    """;
            statement.execute(query);
            System.out.println("Course table created successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Course save(Course course) { // creates a person with the given details
        String query = "INSERT INTO courses(title,instructor_id) VALUES(?,?)"; // query -> sorgu
        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.setString(1, course.getTitle());
            statement.setLong(2, course.getInstructor().getId());

            statement.execute();
            System.out.println("Course saved successfully with name: "+course.getTitle());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return course;
    }
}
