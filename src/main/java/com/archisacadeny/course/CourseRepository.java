package com.archisacadeny.course;

import com.archisacadeny.config.DataBaseConnectorConfig;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CourseRepository {
    public static void deleteCourse(long courseId) {
        String query = "DELETE FROM \"courses\"" +
                "WHERE id = '"+courseId+"'";
        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.execute();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static boolean isCourseFull(long courseId) {

        boolean result = false;
        int count = 0;

        String query = "SELECT COUNT(*) FROM \"course_student_mapper\"" +
                "WHERE course_id = '" + courseId +"'";
        // kac ogrenci bu kursda



//        String query2 = "SELECT max_students FROM \"courses\"" +
//                "WHERE course_id = '" + courseId +"'";
//        // ogrenci limiti kac ?


        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.execute();
            ResultSet rs = statement.getResultSet();
            System.out.println(rs);
            while (rs.next()) {
                System.out.println(rs.getString("count"));
                count = Integer.parseInt(rs.getString("count"));
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        // 2 query calistira bilirmiyim ?
        // course table indan courseID ile course un max_student i na nasil erisirim ?

        return result;
    }
}
