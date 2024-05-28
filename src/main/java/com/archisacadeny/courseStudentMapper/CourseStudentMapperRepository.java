package com.archisacadeny.courseStudentMapper;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.SQLException;
import java.sql.Statement;

public class CourseStudentMapperRepository {

    public static void createCourseStudentTable() {
        try (Statement statement = DataBaseConnectorConfig.getConnection().createStatement()) {
            // SQL query for creating courseStudent table
            String query = """
                     DROP SEQUENCE IF EXISTS s_c_mapper_id;
                     CREATE SEQUENCE s_c_mapper_id INCREMENT BY 1 MINVALUE 0 MAXVALUE 2147483647 START 1;
                     CREATE TABLE IF NOT EXISTS course_student_mapper (
                            id INTEGER DEFAULT nextval('s_c_mapper_id') PRIMARY KEY NOT NULL,
                            course_id INTEGER NOT NULL,
                            student_id INTEGER NOT NULL,
                    CONSTRAINT fk_course_id FOREIGN KEY (course_id) REFERENCES "public"."courses"(id),
                    CONSTRAINT fk_student_id FOREIGN KEY (student_id) REFERENCES "public"."students"(id)
                    );
                    """;
            statement.execute(query);
            System.out.println("CourseStudent table has been created in the database..");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
