package com.archisacadeny.course;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

public class CourseRepository {

    public static void addCourse(String course_name,String course_number,String course_instructor,String enrolled_students){
        //change enrolled students type
        try(Statement statement = DataBaseConnectorConfig.getConnection().createStatement())
        {
            // SQL query for creating persons table
            //String values = MessageFormat.format(" VALUES ( ''{0}'', ''{1}'', ''{2}'', ''{3}'')", course_name, course_number,course_instructor,enrolled_students);

//            System.out.println(values);
            String query = "INSERT INTO courses " +
                    "(course_name,course_number,course_instructor,enrolled_students)"+
                    " VALUES ( '"+course_name+"', '"+course_number+"', '"+course_instructor+"', '"+enrolled_students+"')";

            System.out.println(query);
            statement.execute(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createCourseTable(){
        try(Statement statement = DataBaseConnectorConfig.getConnection().createStatement())
        {
            // SQL query for creating persons table
            String query = "CREATE TABLE IF NOT EXISTS courses(" +
                    "course_id SERIAL PRIMARY KEY," + // id SERIAL ?
                    "course_name VARCHAR(255)," +
                    "course_number VARCHAR(255)," +
                    "course_instructor VARCHAR(255)," +
                    "enrolled_students  VARCHAR(255))";
                    // enrolled students array mi olacak ?

            //change enrolled students type
            System.out.println(query);


            statement.execute(query);
            System.out.println("Courses table has been created in the database..");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
