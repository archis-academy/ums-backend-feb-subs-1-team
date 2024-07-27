package com.archisacadeny.course;


import com.archisacadeny.student.CourseStudentMapper;
import com.archisacadeny.student.Student;

import java.text.ParseException;
import java.sql.Timestamp;
import java.util.*;

public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseStudentMapper courseStudentMapper;

    public CourseService(CourseRepository courseRepository, CourseStudentMapper courseStudentMapper) {
        this.courseRepository = courseRepository;
        this.courseStudentMapper = courseStudentMapper;
    }

    public Course createCourse(Course course) {
        System.out.println("Course "+ course.getCourseName() +" has been created");
        return courseRepository.save(course);
    }


    public void deleteCourse(long courseId){
        System.out.println("Couse has been deleted successfully");
        courseRepository.deleteCourse(courseId);
    }

    public Boolean isCourseFull(long courseId) {
        boolean result = courseRepository.isCourseFull(courseId);
        String message = "NOT full";
        if(result == true){message = "FULL";}
        System.out.println("Course with id:"+courseId+" is "+message);
      
        return result;
    }

    public void update(String courseNumber, Course course ){
        System.out.println("Course number: "+courseNumber + " is updated.");
        courseRepository.update(courseNumber,course);
    }

    public Double getTotalCreditAmount(long studentId) {
        double totalCredit = courseRepository.getTotalCreditAmount(studentId);
        System.out.println("Total credit amount of student with id:" + studentId + " is " + totalCredit + " credits");
        return totalCredit;
    }

    public List<Course> getStudentEnrolledCourses(int studentId) {
        List<Course> courses = courseRepository.getStudentEnrolledCourses(studentId);
        System.out.println("Student Enrolled courses:\n" + courses);
        return courses;
    }

    public List<Student> getCourseEnrolledStudents(int studentId) {
        List<Student> students = courseRepository.getCourseEnrolledStudents(studentId);
        System.out.println("Course Enrolled students:\n" + students);
        return students;
    }

    public Course getCourseWithMostStudents() {
        Course course = courseRepository.getCourseById(courseRepository.getCourseWithMostStudents());
        System.out.println("Course with most student:\n" + course);
        return course;
    }

    public double calculateAverageGradeForCourse(int course_id) {
        Map<String, Object> values = courseRepository.calculateAverageGradeForCourse(course_id);
        double grade = (double) values.get("sum_grade");
        double num = (double) values.get("num_of_students");
        double average = Math.round((grade/num) * 100.0) / 100.0;
        if(average == -1){System.out.println("This course has not been graded yet");}
        else{System.out.println("Average grade of students in this course: " +average);}
      
        return  average;
    }

    public CourseStatistics calculateCourseStatistics(int course_id) {
        CourseStatistics values = courseRepository.calculateCourseStatistics(course_id);
        double average = values.getAverageGrade();
        double min = values.getLowestGrade();
        double max = values.getHighestGrade();
        System.out.println("Course id: " + course_id + " | Average grade: " + average + " | Max Grade: " + max + " | Min grade " + min);
        return values;
    }

    public List<Course> getAllCourses() {
        List<Course> courses = courseRepository.getAllCourses();
        System.out.println(courses);
        return courses;
    }

    public double calculateInstructorCoursesAttendanceRate(int instructorId) throws ParseException {
        double finalAttendanceRate = 0;
        double attendancePercentage = 0;
        Map<String, Object> values = courseRepository.calculateInstructorCoursesAttendanceRate(instructorId);
        ArrayList<Integer> attendedLessons = (ArrayList<Integer>) values.get("attended_lessons");
        int courseDuration = (int) values.get("course_duration");
      
        System.out.println( "Courses attendance rate:\n");
        for(int i =0; i<attendedLessons.size();i++){
            attendancePercentage += ( (attendedLessons.get(i) * 100.0) / (courseDuration * 2.0) );
            System.out.println( "   Course number "+i+"-"+( (attendedLessons.get(i) * 100.0) / (courseDuration * 2.0) ));
        }
        finalAttendanceRate = Math.round((attendancePercentage/attendedLessons.size()) * 100.0) / 100.0;
        System.out.println("OVERALL ATTENDANCE RATE FOR INSTRUCTOR: - "+finalAttendanceRate);
        return finalAttendanceRate;
    }

    public List<Course> listPopularCourses(int topCount) {
        CourseRepository repository = new CourseRepository();
        List<Course> courses = repository.listPopularCourses(topCount);
        System.out.println("TOP "+topCount+" courses: \n"+courses);
        return courses;
    }


    public Map<String, Object> generateStudentAttendanceReport(int studentId, Timestamp startDate, Timestamp endDate) {
        Map<String, Object> values = courseRepository.generateStudentAttendanceReport(studentId, startDate, endDate);

        ArrayList<Integer> attendedLessons = (ArrayList<Integer>) values.get("attended_lessons");
        int weekDifference = (int) values.get("week_difference");
        ArrayList<Integer> attendanceLimit = (ArrayList<Integer>) values.get("attendance_limit");

        ArrayList<Double> attendancePercentage = new ArrayList<>();
        ArrayList<Integer> missedLessons = new ArrayList<>();

        for (int i = 0; i < attendedLessons.size(); i++) {
            double percentage = (attendedLessons.get(i)) * 100.0 / (weekDifference * 2);
            attendancePercentage.add(Math.round(percentage * 100.0) / 100.0);
            missedLessons.add(weekDifference * 2 - (attendedLessons.get(i)));
        }

        System.out.println("Student course attendance rate:");
        System.out.println(attendancePercentage);

        System.out.println("\nLessons missed");
        System.out.println(missedLessons);

        System.out.println("\nAttendance Limit for the courses");
        System.out.println(attendanceLimit);

        System.out.print("\nFAIL or PASS \n[");
        for (int i = 0; i < missedLessons.size(); i++) {
            if (missedLessons.get(i) > attendanceLimit.get(i)) {
                System.out.print("FAIL,");
            } else {
                System.out.print("PASS,");
            }
        }
        System.out.println("]");

        return values;
    }

    public Map<String, Object> generateCourseReport(int courseId) {
        Map<String, Object> values = courseRepository.generateCourseReport(courseId);
        System.out.println("Course report generated succesfully! \n " +
                "   Course name: " + values.get("name") +
                "\n    Course number: " + values.get("number") +
                "\n    Course department: " + values.get("department") +
                "\n    Course credits: " + values.get("credits") +
                "\n    Course student count: " + values.get("student_count") +
                "\n    Course max student count: " + values.get("max_students") +
                "\n    Course Instructor: " + values.get("instructor_id") +
                "\n    Course Average: " + ((double) values.get("total_student_score") / (double) values.get("student_count")));
        return values;
    }

    public Student findTopStudentInInstructorCourses(int instructorId){
        Student student = courseRepository.findTopStudentInInstructorCourses(instructorId);
        System.out.println("Top student in instructor id: "+instructorId+ " 's classes:\n"+student);
        return student;
    }

    public double calculateAverageSuccessGradeForInstructorCourses(int instructorId){
        Map<String,Double> values = courseRepository.calculateAverageSuccessGradeForInstructorCourses(instructorId);
        double result = Math.round((values.get("total") / values.get("courseCount"))* 100.0) / 100.0;
        System.out.println("Average success rate for instructor id "+instructorId+" is " +result);
        return result;
    }

    public String calculateLetterGradeForStudent(int studentId, int courseId) {
        double grade = courseRepository.calculateLetterGradeForStudent(studentId,courseId);
        String letterGrade;
        if (isBetween(grade, 0, 59)) {
            letterGrade = "F";
        } else if (isBetween(grade, 59, 69)) {
            letterGrade = "D";
        } else if (isBetween(grade, 69, 79)) {
            letterGrade = "C";
        } else if (isBetween(grade, 79, 89)) {
            letterGrade = "B";
        } else if (isBetween(grade, 89, 100)) {
            letterGrade = "A";
        }else{letterGrade = "NaN";};
        System.out.println("Student final grade is -  "+letterGrade + " "+grade);

        return letterGrade;
    }


    public boolean isBetween(double value, int min, int max)
    {
        return((value >= min) && (value <= max));
    }

    public List<Course> getCoursesByInstructorId(long instructorId){
        List<Course> courses = courseRepository.getCoursesByInstructorId(instructorId);
        System.out.println("Here are the instructors courses \n"+courses);
        return courses;
    }

    public int getStudentCountForCourse(long courseId) {
        int count = courseRepository.getStudentCountForCourse(courseId);
        System.out.println("Student count for course with id: "+courseId+" is - "+count);
        return count;
    }

    public void createCourseSchedule(long student_id) {
        List<Course> courses = courseRepository.createCourseSchedule(student_id);
        // i represents days of the week, in our Uni, we had lessons max 6 days a week including labs and lessons.
        String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < 6; i++) {
            System.out.println("Courses on " + weekDays[i] + "\n");
            for (int b = 0; b < 2; b++) {
                if (b == 0) {
                    System.out.println("   Lesson Time: 10:00 AM \n");
                } else {
                    System.out.println("   ____________________\n   Lesson Time: 1:30 PM \n");
                }
                System.out.println(
                        "   Course name: " + courses.get(i).getCourseName() + "\n" +
                                "   Course number: " + courses.get(i).getCourseNumber() + "\n"
                );
                // THIS SCHEDULE PRINTS THE SAME LESSON TWICE THE SAME DAY
                // COULD VE BEEN BETTER, TIME SCRAMBLE

            }
        }
    }

    public void processStudentApplication(Student student, List<Course> selectedCourses) {
        Date date = new Date();
        Timestamp startDate = new Timestamp(date.getTime());
        // START DATE IS TODAY

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 5);
        Date endDate = cal.getTime();

        Timestamp finishDate = new Timestamp(endDate.getTime());
        System.out.println(endDate);
        //END DATE IS TODAY + 5 MONTHS, DURATION OF 1 SEMESTER.

        for(int i =0; i<selectedCourses.size();i++)
        {
            Course course = selectedCourses.get(i);
            courseStudentMapper.saveToCourseStudentMapper(student.getId(),
                    course.getId(), -1, startDate ,
                    finishDate, 0);
            // attended lessons = 0, missedLessons = 0, grade = -1( not graded)
        }

    }

    public ArrayList<Boolean> checkStudentAttendance(int studentId) {
        Map<String,Object> values = courseRepository.checkStudentAttendance(studentId);

        ArrayList<Integer> attendedLessons = (ArrayList<Integer>) values.get("attended_lessons");
        int courseDuration = (int) values.get("week_difference");
        ArrayList<Integer> attendanceLimit = (ArrayList<Integer>) values.get("attendance_limit");

        ArrayList<Boolean> results = new ArrayList<>();

        System.out.println("ATTENDANCE LIMIT:     8     ");
        for(int i = 0; i<attendanceLimit.size();i++){
            System.out.print("Num of lessons  "+courseDuration*2+"  |  ");
            System.out.println(attendedLessons.get(i) + "  Num of lessons ATTENDED");
            if((courseDuration*2 - (attendedLessons.get(i))) >= attendanceLimit.get(i)){
                results.add(false);
            }else { results.add(true); }

        }
        // TRUE means student has failed, or has no chances to miss
        // FALSE means he can still miss
        return results;
    }

    public List<Course> searchAndFilter(String searchCriteria) {
        searchCriteria = "mathematics";
        List<Course> courses = courseRepository.advancedSearchAndFilters(searchCriteria);
        for (Course course : courses) {
            System.out.println("Course ID: " + course.getId());
            System.out.println("Course Name: " + course.getCourseName());
            System.out.println("Course Credit: " + course.getCredits());
            System.out.println("-------------");

        }
        return courses;
    }
  
    public List<Course> listCoursesOrderedByStudentAverageGrade() {
        List<Course> courses = courseRepository.listCoursesOrderedByStudentAverageGrade();

        for (Course course : courses) {
            System.out.println(course);
        }
        return courses;
    }

}