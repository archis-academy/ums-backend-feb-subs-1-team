package com.archisacadeny.course;

import com.archisacadeny.config.DataBaseConnectorConfig;
import com.archisacadeny.student.Student;
import com.archisacadeny.instructor.InstructorRepository;
import com.archisacadeny.student.CourseStudentMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    public static void createCourseTable(){
        try(Statement statement = DataBaseConnectorConfig.getConnection().createStatement())
        {
            String query = """
                    DROP SEQUENCE IF EXISTS course_id_seq;
                    CREATE SEQUENCE course_id_seq INCREMENT BY 1 MINVALUE 0 MAXVALUE 2147483647 START 1;
                    CREATE TABLE IF NOT EXISTS courses(
                    "id" INTEGER DEFAULT nextval('course_id_seq') PRIMARY KEY NOT NULL,
                    "name" VARCHAR(255) NOT NULL,
                    "number" VARCHAR(255) NOT NULL,
                    "credits" INTEGER,
                    "department" VARCHAR(255),
                    "max_students" INTEGER,
                    "instructor_id" INTEGER,
                    CONSTRAINT fk_instructor_id FOREIGN KEY (instructor_id) REFERENCES "public"."instructors"(id)
                    )
            """;
            statement.execute(query);
            System.out.println("Courses table has been created in the database..");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Course save(Course course){
        String query = "INSERT INTO courses(name,number,credits,department,max_students,instructor_id) VALUES(?,?,?,?,?,?)";
        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.setString(1,course.getCourseName());
            statement.setString(2,course.getCourseNumber());
            statement.setLong(3,course.getCredits());
            statement.setString(4,course.getDepartment());
            statement.setLong(5,course.getMaxStudents());
            statement.setLong(6,course.getInstructor().getId());

            statement.execute();
            System.out.println("Course has been saved successfully with name: "+course.getCourseName());

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return course;
    }


    public void deleteCourse(long courseId) {
        String query = "DELETE FROM \"courses\"" +
                "WHERE id = '"+courseId+"'";
        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.execute();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }


    public boolean isCourseFull(long courseId) {
//       TODO ******* HOW TO REMOVE DUPLICATE VALUES ????
        // DUPLICATE VALUES IN COURSE STUDENT MAPPER RESULTS IN WRONG RESULTS.
        // IN THIS METHOD AND GETTOTALCREDITAMOUNT
        int studentCount = 0;
        int maxStudents = 0;

        String query = "SELECT \"courses\".id, name , max_students, COUNT(course_student_mapper.course_id) as n_of_students FROM \"courses\"" +
                "LEFT JOIN \"course_student_mapper\" ON  course_student_mapper.course_id = \"courses\".\"id\""+
                "WHERE courses.id = '"+courseId + "' GROUP BY courses.id  ";


        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                studentCount = rs.getInt("n_of_students");
                maxStudents = rs.getInt("max_students");
                System.out.println(studentCount + "A"+maxStudents);
            }
            printResultSet(rs);
            //PRINTLENMIYOR, rs.next() bittikten sonra.

        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        if( studentCount == maxStudents){
            return true;
        }else{
            return false;
        }
    }

    public void printResultSet(ResultSet rs) throws SQLException
    {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(" | ");
                System.out.print(rs.getString(i));
            }
            System.out.println("");
        }
    }


    public void update(String courseNumber, Course course ){
        String query = String.format(
                "UPDATE courses SET name= '%1$s'," +
                        " number = '%2$s'," +
                        " instructor_id = '%3$s' " +
                        " credits = '%4$s' " +
                        " department = '%5$s' "+
                        " max_students = '%6$s'"+

                        "WHERE number= '"+courseNumber+"'",
                course.getCourseName(),
                course.getCourseNumber(),
                course.getInstructor().getId(),
                course.getCredits(),
                course.getDepartment(),
                course.getMaxStudents()
                );
        System.out.println(query);
        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.execute();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public  double getTotalCreditAmount(long studentId) {
        double count = 0.0;
        String query = "SELECT  student_id , credits  FROM \"course_student_mapper\"" +
                "LEFT JOIN \"courses\"  ON  course_student_mapper.course_id = \"courses\".\"id\""+
                "WHERE student_id = '"+studentId + "'";

        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                count += rs.getInt("credits");
            }
            System.out.println(count);
//            printResultSet(rs);
            //PRINTLENMIYOR, rs.next() bittikten sonra.

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return count;
    }

    public  int getCourseWithMostStudents() {
        int courseId = -1;
        String query = "SELECT DISTINCT course_id , COUNT(course_id) as student_count FROM \"course_student_mapper\" " +
                "GROUP BY course_id " +
                "ORDER BY student_count DESC LIMIT 1 ";

        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                courseId = rs.getInt("course_id");
            }
//            printResultSet(rs);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return courseId;
    }


    public List<Course> getStudentEnrolledCourses(int studentId) {
        // RETURNS COURSE ID s FOR NOW
        String query = "SELECT course_id, name, instructor_id, credits, number,department,max_students FROM \"course_student_mapper\" " +
                "LEFT JOIN \"courses\" ON course_student_mapper.course_id = \"courses\".\"id\""+
                "WHERE student_id = '" + studentId + "'" ;

        ArrayList<Course> courses = new ArrayList<>();

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            statement.execute();
            ResultSet rs = statement.getResultSet();

            while (rs.next()) {
                courses.add(new Course(rs.getInt("course_id")
                        , rs.getString("name")
                        , InstructorRepository.getInstructorById( rs.getLong("instructor_id"))
                        ,rs.getLong("credits")
                        ,rs.getString("number")
                        , getCourseEnrolledStudents(rs.getInt("course_id"))
                        ,rs.getString("department")
                        ,rs.getInt("max_students")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    //BU METHODU getCouseById methodunda kullanacagimiz icin id leri degil LISt<Students> dondurmesi gerekir.
    public  List<Student> getCourseEnrolledStudents(long courseId){
        ArrayList<Student> students = new ArrayList<>();

        String query = "SELECT course_id, student_id, full_name,gender,identity_no,enrollment_date,year_of_study, total_credit_count FROM \"course_student_mapper\" " +
                "LEFT JOIN \"students\" ON course_student_mapper.student_id = \"students\".\"id\""+
                "WHERE course_id = '" + courseId + "'" ;

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            statement.execute();
            ResultSet rs = statement.getResultSet();

            while (rs.next()) {
                 students.add(new Student(rs.getInt("student_id"),
                         rs.getString("full_name"),
                         rs.getString("gender"),
                         rs.getString("identity_no"),
                         rs.getTimestamp("enrollment_date"),
                         rs.getInt("year_of_study"),
                         rs.getInt("total_credit_count")));
                 // TODO   COURSE STUDENT MAPPERDA BIR EKLEME YAPTIGIMIZDA STUDENTIN TOTAL CREDIT COUNT U GUNCELLEMEMIZ GEREKIYOR. nasil
            }
//            printResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return students;
    }

    public static Course getCourseById(long courseId){
        String query = "SELECT * FROM courses WHERE id = "+courseId;
        Course course = null;
        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                course = new Course(courseId,
                        rs.getString("name")
                        , InstructorRepository.getInstructorById( rs.getLong("instructor_id"))
                        ,rs.getLong("credits")
                        ,rs.getString("number")
                        , getCourseEnrolledStudents(courseId)
                        ,rs.getString("department")
                        ,rs.getInt("max_students"));
            }
            //printResultSet(rs);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return course;
    }

}
