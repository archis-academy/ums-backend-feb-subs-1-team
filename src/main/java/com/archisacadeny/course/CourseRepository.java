package com.archisacadeny.course;

import com.archisacadeny.config.DataBaseConnectorConfig;
import com.archisacadeny.instructor.Instructor;
import com.archisacadeny.student.Student;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

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
                    "attendance_limit" INTEGER,
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
        String query = "INSERT INTO courses(name,number,credits,department,max_students,instructor_id,attendance_limit) VALUES(?,?,?,?,?,?,?)";

        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.setString(1,course.getCourseName());
            statement.setString(2,course.getCourseNumber());
            statement.setLong(3,course.getCredits());
            statement.setString(4,course.getDepartment());
            statement.setLong(5,course.getMaxStudents());
            statement.setLong(6,course.getInstructor().getId());
            statement.setInt(7,course.getAttendanceLimit());
            statement.execute();
            System.out.println("Course " +course.getCourseName() +" has been saved successfully to the database.");

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
                System.out.println("Student count: "+studentCount + " ||| "+maxStudents + " Student limit");
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

    public void printResultSet(ResultSet rs) throws SQLException {
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
                "UPDATE courses SET name= '%1$s', " +
                        " number = '%2$s', " +
                        " instructor_id = '%3$s'," +
                        " credits = '%4$s' ," +
                        " department = '%5$s', "+
                        " max_students = '%6$s',"+
                        " attendance_limit = '%7$s' "+
                        "WHERE number= '"+courseNumber+"'",
                course.getCourseName(),
                course.getCourseNumber(),
                course.getInstructor().getId(),
                course.getCredits(),
                course.getDepartment(),
                course.getMaxStudents(),
                course.getAttendanceLimit()
        );

        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.execute();
            System.out.println("Updated Successfully");
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public double getTotalCreditAmount(long studentId) {
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

    public int getCourseWithMostStudents() {
        // RETURNS COURSE ID FOR NOW
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

    public Map<String,Object> calculateAverageGradeForCourse(long courseId) {
        Map<String, Object> values = new HashMap<>();

        String query = "SELECT SUM(grade) as sum, COUNT(grade) as num  FROM \"course_student_mapper\" " +
                "WHERE course_id = '" + courseId + "'" ;

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            statement.execute();
            ResultSet rs = statement.getResultSet();

            while (rs.next()) {
                values.put("sum_grade",rs.getDouble("sum"));
                values.put("num_of_students", (double) rs.getInt("num"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return values;
    }

    public Course getCourseById(long courseId){
        String query = "SELECT * FROM courses WHERE id = "+courseId;
        Course course = new Course();
        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                course = new Course();
                Instructor instructor = new Instructor();
                instructor.setId(rs.getLong("instructor_id"));
                course.setId(rs.getInt("id"));
                course.setCourseName(rs.getString("name"));
                course.setInstructor(instructor);
                course.setCredit(rs.getInt("credits"));
                course.setCourseNumber(rs.getString("number"));
                course.setDepartment(rs.getString("department"));
                course.setMaxStudents(rs.getInt("max_students"));
            }
            //printResultSet(rs);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return course;
    }

    public CourseStatistics calculateCourseStatistics(int courseId) {
        String query = "SELECT SUM(grade) as sum, COUNT(grade) as num, MIN(grade) as min, MAX(grade) as max  FROM \"course_student_mapper\" " +
                "WHERE course_id = '" + courseId + "'" ;
        CourseStatistics stats = new CourseStatistics();
        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
//            printResultSet(rs);
            while (rs.next()) {
                stats.setCourseId(courseId);
                stats.setAverageGrade(rs.getDouble("sum"),
                        rs.getInt("num"));
                stats.setHighestGrade(rs.getDouble("max"));
                stats.setLowestGrade(rs.getDouble("min"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stats;
    }// Service methdou serviceclassi eklendikten sonra yazilacak

    public Student findTopStudentInInstructorCourses(int instructorId) {
        String query = "SELECT student_id, grade, courses.instructor_id, " +
                "students.id,students.full_name,students.gender,students.identity_no,students.enrollment_date," +
                "students.year_of_study,students.total_credit_count FROM course_student_mapper " +
                "INNER JOIN courses ON course_student_mapper.course_id = courses.id " +
                "LEFT JOIN students  ON  course_student_mapper.student_id = \"students\".\"id\""+
                "WHERE courses.instructor_id = "+instructorId+
                " ORDER BY grade DESC LIMIT 1 ";
        Student student = new Student();
        // empty constructor*
        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                student.setId(rs.getInt("id"));
                student.setFullName(rs.getString("full_name"));
                student.setGender(rs.getString("gender"));
                student.setEnrollmentDate(rs.getTimestamp("enrollment_date"));
                student.setYearOfStudy(rs.getInt("year_of_study"));
                student.setTotalCreditCount(rs.getInt("total_credit_count"));
            }
//            printResultSet(rs);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return student;
    }//servise eklenilecek

    public Map<String, Double> calculateAverageSuccessGradeForInstructorCourses(int instructorId) {
        Map<String,Double> values = new HashMap<>();
        String query = "SELECT SUM(grade) AS total, COUNT(grade) AS courseCount, courses.instructor_id AS instructor  FROM course_student_mapper " +
                "INNER JOIN courses ON course_student_mapper.course_id = courses.id WHERE courses.instructor_id = "+instructorId+
                " GROUP BY instructor";

        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                values.put("total",rs.getDouble("total"));
                values.put("courseCount", (double) rs.getInt("courseCount"));
            }
            printResultSet(rs);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return values;
        // service eklenecek TODO
    }

    public double calculateLetterGradeForStudent(int studentId, int courseId) {
        double grade = -1;

        String query = "SELECT grade FROM course_student_mapper WHERE course_id = "+courseId+" AND student_id = "+studentId;
        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while(rs.next()) {
                grade = rs.getDouble("grade");
            }
//            printResultSet(rs);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return grade;
    }

    public List<Course> getCoursesByInstructorId(long instructorId){
        List <Course> courses = new ArrayList<>();
        String query= "SELECT * FROM courses WHERE instructor_id=?";

        try(PreparedStatement statement= DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.setLong(1, instructorId);
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    Course course = new Course();
                    course.setId(resultSet.getLong("id"));
                    course.setCourseName(resultSet.getString("name"));
                    course.setCourseNumber(resultSet.getString("number"));
                    course.setCredit(resultSet.getInt("credits"));
                    course.setDepartment(resultSet.getString("department"));
                    course.setMaxStudents(resultSet.getInt("max_students"));
                    courses.add(course);
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return courses;
    }

    public List<Course> getStudentEnrolledCourses(int studentId) {
        // RETURNS COURSE ID s FOR NOW
        String query = "SELECT course_id, student_id, name, instructor_id, credits, number,department,max_students FROM \"course_student_mapper\" " +
                "LEFT JOIN \"courses\" ON course_student_mapper.course_id = \"courses\".\"id\""+
                "WHERE student_id = '" + studentId + "'" ;

        ArrayList<Course> courses = new ArrayList<>();

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                Instructor instructor = new Instructor();
                Course course = new Course();
                instructor.setId(rs.getLong("instructor_id"));
                course.setId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("name"));
                course.setInstructor(instructor);
                course.setCredit(rs.getInt("credits"));
                course.setCourseNumber(rs.getString("number"));
                course.setDepartment(rs.getString("department"));
                course.setMaxStudents(rs.getInt("max_students"));

                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    public  List<Student> getCourseEnrolledStudents(long courseId){
        ArrayList<Student> students = new ArrayList<>();

        String query = "SELECT course_id, student_id, full_name,gender,identity_no,enrollment_date,year_of_study, total_credit_count FROM \"course_student_mapper\" " +
                "LEFT JOIN \"students\" ON course_student_mapper.student_id = \"students\".\"id\""+
                "WHERE course_id = '" + courseId + "'" ;

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            Student student;
            while (rs.next()) {
                student = new Student();
                student.setId(rs.getInt("student_id"));
                student.setFullName(rs.getString("full_name"));
                student.setGender(rs.getString("gender"));
                student.setIdentityNo(rs.getString("identity_no"));
                student.setEnrollmentDate(rs.getTimestamp("enrollment_date"));
                student.setYearOfStudy(rs.getInt("year_of_study"));
                student.setTotalCreditCount(rs.getInt("total_credit_count"));
                students.add(student);
                // TODO   COURSE STUDENT MAPPERDA BIR EKLEME YAPTIGIMIZDA STUDENTIN TOTAL CREDIT COUNT U GUNCELLEMEMIZ GEREKIYOR. nasil
            }
//            printResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return students;
    }

    public int getStudentCountForCourse(long courseId) {
        String query= "SELECT COUNT (student_id) AS student_count FROM course_student_mapper WHERE course_id = ";
        try(PreparedStatement statement= DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.setLong(1,courseId);
            try(ResultSet rs= statement.executeQuery()) {
                if(rs.next()) {
                    return rs.getInt("student_count");
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public List<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM \"courses\" ";

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            Course course;
            while (rs.next()) {
                course = new Course();
                course.setId(rs.getInt("id"));
                course.setCourseName(rs.getString("name"));
                course.setInstructor(new Instructor(rs.getLong("instructor_id")));
                course.setCredit(rs.getInt("credits"));
                course.setCourseNumber(rs.getString("number"));
                course.setDepartment(rs.getString("department"));
                course.setMaxStudents(rs.getInt("max_students"));
                courses.add(course);
                //DERSTE LOOPLARDA QUERY CALISTIRAN METHOD KULLANMAYIN DEMISTINIZ
                // FAKAT KURSA KAYITLI OGRENCILERI QUERY CALISTIRMADAN EKLEYEMEM, NASIL YAPA BILIRIM ?
                // TODO
            }
            //printResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    public List<Course> listPopularCourses(int topCount) {
        ArrayList<Course> courses = new ArrayList<>();
        String query = "SELECT courses.id,name,number,credits,department,max_students,instructor_id, COUNT(course_id) as student_count FROM courses " +
                "LEFT JOIN \"course_student_mapper\" ON course_student_mapper.course_id = \"courses\".\"id\" " +
                "GROUP BY courses.id " +
                "ORDER BY student_count DESC LIMIT " + topCount;
        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                Course course = new Course();
                Instructor instructor = new Instructor();
                instructor.setId(rs.getLong("instructor_id"));
                course.setId(rs.getInt("id"));
                course.setCourseName(rs.getString("name"));
                course.setInstructor(instructor);
                course.setCredit(rs.getInt("credits"));
                course.setCourseNumber(rs.getString("number"));
                course.setDepartment(rs.getString("department"));
                course.setMaxStudents(rs.getInt("max_students"));
                courses.add(course);
            }
//            printResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    public Map<String, Object> generateStudentAttendanceReport(int studentId, Timestamp startDate, Timestamp endDate) {
        Map<String, Object> values = new HashMap<>();

        //Mape e attendance yuzdesi arraylistini ekliyorum, ve attendance limitini. Kacirdigi dersleri
        ArrayList<Integer> attendedLessons = new ArrayList<>();
        ArrayList<Integer> attendanceLimit = new ArrayList<>();
        int weekDifference = 0;

        String query = "SELECT student_id , attended_lessons, attendance_limit, " +
                "TRUNC (DATE_PART('Day', '" + endDate + "'::TIMESTAMP - '" + startDate + "'::TIMESTAMP)/7) AS week_difference " +
                "FROM course_student_mapper " +
                "INNER JOIN courses ON course_student_mapper.course_id = courses.id WHERE student_id = " + studentId;

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                attendedLessons.add(rs.getInt("attended_lessons"));
                attendanceLimit.add(rs.getInt("attendance_limit"));
                weekDifference = rs.getInt("week_difference");
            }
            values.put("attended_lessons", attendedLessons);
            values.put("attendance_limit", attendanceLimit);
            values.put("week_difference", weekDifference);
//            printResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return values;

    }

    public Map<String,Object> generateCourseReport(int courseId) {
        Map<String,Object> values = new HashMap<>();
        // Professor ( to be added in service ?)
        String query = "SELECT name,number,instructor_id,department,credits,max_students, SUM(grade) as total_student_score,COUNT(course_id) as student_count " +
                "FROM courses LEFT JOIN \"course_student_mapper\" ON course_student_mapper.course_id = courses.id" +
                " WHERE courses.id = "+courseId+" GROUP BY courses.id ";

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                values.put("name",rs.getString("name"));
                values.put("number",rs.getString("number"));
                values.put("department",rs.getString("department"));
                values.put("credits",rs.getInt("credits"));
                values.put("student_count",rs.getInt("student_count")*1.0);
                values.put("max_students",rs.getInt("max_students"));
                values.put("instructor_id",rs.getInt("instructor_id"));
                values.put("total_student_score",rs.getDouble("total_student_score"));
            } } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return values;
    }

    public Map<String,Object> calculateInstructorCoursesAttendanceRate(int instructorId) throws ParseException {
        String query = "SELECT instructor_id AS instructor, attended_lessons, " +
                "TRUNC(DATE_PART('Day', course_end_date::TIMESTAMP - course_start_date::TIMESTAMP)/7) AS course_duration " +
                "FROM course_student_mapper " +
                "INNER JOIN courses ON course_student_mapper.course_id = courses.id WHERE courses.instructor_id = "+instructorId;
        Map<String,Object> values = new HashMap<>();
        ArrayList<Integer> attendedLessons = new ArrayList<>();

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                attendedLessons.add(rs.getInt("attended_lessons"));
                values.put("course_duration",rs.getInt("course_duration"));
                // Hangi veri turu ile ekleye bilirim ? MAP kullaninca valuelar override oluyor, hesaplamayi o yuzden burda yaptim.
            }
            values.put("attended_lessons",attendedLessons);
            // su an 2 ile boluyorum, her kurs icin haftada 2 defa attendance aliyor hoca.

    //            printResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return values;
    }

    public Map<String,Object> checkStudentAttendance(int studentId) {

        Map<String,Object> values = new HashMap<>();

        ArrayList<Integer> attendedLessons = new ArrayList<>();
        ArrayList<Integer> attendanceLimit = new ArrayList<>();
        int courseDuration = 0;
        String query = "SELECT course_id,attendance_limit, " +
                " TRUNC(DATE_PART('Day', course_end_date::TIMESTAMP - course_start_date::TIMESTAMP)/7) AS course_duration , " +
                " attended_lessons FROM courses "+
                "LEFT JOIN course_student_mapper ON course_student_mapper.course_id = courses.id WHERE student_id = "+studentId;

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                attendedLessons.add(rs.getInt("attended_lessons"));
                attendanceLimit.add(rs.getInt("attendance_limit"));
                courseDuration = rs.getInt("course_duration");
            }
            values.put("attended_lessons",attendedLessons);
            values.put("attendance_limit",attendanceLimit);
            values.put("week_difference", courseDuration);
    //            printResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return values;
    }

    public List<Course> advancedSearchAndFilters(String searchCriteria){

        List<Course> courses = new ArrayList<>();

        String query = "SELECT * FROM courses WHERE name ILIKE ? ";
        System.out.println("Genereted Query: " +query);


        try(PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){

            statement.setString(1, "%" + searchCriteria + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Course course = new Course();
                course.setId(resultSet.getLong("id"));
                course.setCourseName(resultSet.getString("name"));
                course.setCredits((int) resultSet.getLong("credits"));
                courses.add(course);

            }

        } catch (SQLException e) {
            throw new RuntimeException("Error performing advanced search and filters" ,e);
        }

        return courses;
    }

    public void enrollStudentInCourse(long studentId, long courseId) {
        String query = "INSERT INTO course_student_mapper (student_id, course_id) VALUES (?, ?)";

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {

            statement.setLong(1, studentId);
            statement.setLong(2, courseId);

            statement.executeUpdate();
            System.out.println("Student with ID: " + studentId + " has been enrolled in course with ID: " + courseId);

        } catch (SQLException e) {
            throw new RuntimeException("Error enrolling student in course", e);
        }
    }

    public void unenrollStudentFromCourse(long studentId, long courseId){

        String query = "DELETE FROM course_student_mapper WHERE student_id = ? AND course_id = ?";

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)){

            statement.setLong(1, studentId);
            statement.setLong(2, courseId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0){
                System.out.println("Student with ID:" + studentId + "has been unenrolled from course with ID:" + courseId);
            }else {
                System.out.println("No enrollment found for student with ID:" + studentId + "in course with ID" + courseId);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error unenrolling student from course", e);
        }
    }

    public List<Course> createCourseSchedule(long student_id) {
        ArrayList<Course> courses = new ArrayList<>();

        String query = "SELECT student_id, course_id," +
                " courses.name,courses.number,credits,department,max_students,instructor_id,attendance_limit " +
                "FROM course_student_mapper " +
                "LEFT JOIN courses ON course_student_mapper.course_id = courses.id WHERE student_id= "+student_id;

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                Course course = new Course();
                Instructor instructor = new Instructor();
                instructor.setId(rs.getLong("instructor_id"));
                course.setId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("name"));
                course.setInstructor(instructor);
                course.setCredit((int) rs.getLong("credits"));
                course.setCourseNumber(rs.getString("number"));
                course.setDepartment(rs.getString("department"));
                course.setMaxStudents(rs.getInt("max_students"));
                course.setAttendanceLimit(rs.getInt("attendance_limit"));
                courses.add(course);
            }
            printResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return courses;
    }

    public List<Course> listCoursesOrderedByStudentAverageGrade() {
        ArrayList<Course> courses = new ArrayList<>();

        String query = "SELECT course_id, AVG(grade):: NUMERIC(10, 2) as average, courses.name,courses.number,credits,department,max_students,instructor_id,attendance_limit " +
                "FROM course_student_mapper " +
                "LEFT JOIN courses ON course_student_mapper.course_id = courses.id GROUP BY " +
                " course_id ,courses.name, courses.number,courses.credits,courses.department,courses.max_students,courses.instructor_id,courses.attendance_limit  ORDER BY average DESC ";

        try (PreparedStatement statement = DataBaseConnectorConfig.getConnection().prepareStatement(query)) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                Course course = new Course();
                Instructor instructor = new Instructor();
                instructor.setId(rs.getLong("instructor_id"));
                course.setId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("name"));
                course.setInstructor(instructor);
                course.setCredit(rs.getInt("credits"));
                course.setCourseNumber(rs.getString("number"));
                course.setDepartment(rs.getString("department"));
                course.setMaxStudents(rs.getInt("max_students"));
                course.setAttendanceLimit(rs.getInt("attendance_limit"));
                courses.add(course);
            }
    //            printResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return courses;
    }

}
