package com.archisacadeny.course;


import com.archisacadeny.instructor.Instructor;
import com.archisacadeny.student.Student;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(long courseId){
        courseRepository.deleteCourse(courseId);
    }

    public Boolean isCourseFull(long courseId){
        boolean result = courseRepository.isCourseFull(courseId);
        System.out.println("Course with id:"+courseId+" is "+result);

        return result;
    }

    public void update(String courseNumber, Course course ){
        System.out.println("Course number: "+courseNumber +" id:" +course.getId()+ " is updated.");
        courseRepository.update(courseNumber,course);
    }

    public Double getTotalCreditAmount(long studentId){
        double totalCredit = courseRepository.getTotalCreditAmount(studentId);
        System.out.println("Total credit amount of student with id:"+studentId+" is "+totalCredit+" credits");
        return totalCredit;
    }

    public List<Course> getStudentEnrolledCourses(int studentId){
        List<Course> courses = courseRepository.getStudentEnrolledCourses(studentId);
        System.out.println("Student Enrolled courses:\n"+ courses);
        return courses;
    }

    public List<Student> getCourseEnrolledStudents(int studentId){
        List<Student> students = courseRepository.getCourseEnrolledStudents(studentId);
        System.out.println("Course Enrolled students:\n"+ students);
        return students;
    }

    public Course getCourseWithMostStudents(){
        Course course = courseRepository.getCourseById(courseRepository.getCourseWithMostStudents());
        System.out.println("Course with most student:\n"+ course);
        return course;
    }
    public double calculateAverageGradeForCourse(int course_id){
        Map<String, Object> values = courseRepository.calculateAverageGradeForCourse(course_id);
        double grade = (double) values.get("sum_grade");
        double num = (double) values.get("num_of_students");
        double average = Math.round((grade/num) * 100.0) / 100.0;
        System.out.println("Average grade of students in this course: " +average);
        return  average;
    }

    public CourseStatistics calculateCourseStatistics(int course_id){
        CourseStatistics values = courseRepository.calculateCourseStatistics(course_id);
        double average = values.getAverageGrade();
        double min = values.getLowestGrade();
        double max = values.getHighestGrade();
        System.out.println("Course id: "+course_id +" | Average grade: " +average+ " | Max Grade: "+max+" | Min grade "+min);
        return values;
    }

    public List<Course> getAllCourses(){
        List<Course> courses = courseRepository.getAllCourses();
        System.out.println(courses);
        return courses;
    }

    public double calculateInstructorCoursesAttendanceRate(int instructorId) throws ParseException {
        double attendancePercentage = 0;
        Map<String,Object> values = courseRepository.calculateInstructorCoursesAttendanceRate(instructorId);
        ArrayList<Integer> attendedLessons = (ArrayList<Integer>) values.get("attended_lessons");
        int courseDuration = (int) values.get("course_duration");
        for(int i =0; i<attendedLessons.size();i++){
            attendancePercentage += ( (attendedLessons.get(i) * 100.0) / (courseDuration * 2.0) );
        }
        System.out.println(attendancePercentage);
        return Math.round((attendancePercentage/attendedLessons.size()) * 100.0) / 100.0 ;
    }

    public List<Course> listPopularCourses(int topCount) {
        List<Course> a = courseRepository.listPopularCourses(topCount);
        return a;
    }


    public Map<String,Object> generateStudentAttendanceReport(int studentId, Timestamp startDate, Timestamp endDate){
        Map<String,Object> values = courseRepository.generateStudentAttendanceReport(studentId,startDate,endDate);

        ArrayList<Integer> attendedLessons = (ArrayList<Integer>) values.get("attended_lessons");
        int weekDifference = (int) values.get("week_difference");
        ArrayList<Integer> attendanceLimit = (ArrayList<Integer>) values.get("attendance_limit");

        ArrayList<Double> attendancePercentage = new ArrayList<>();
        ArrayList<Integer> missedLessons = new ArrayList<>();

        for(int i = 0; i<attendedLessons.size();i++){
            double percentage = (attendedLessons.get(i) ) * 100.0 / (weekDifference*2);
            attendancePercentage.add( Math.round( percentage * 100.0) / 100.0  );
            missedLessons.add(weekDifference*2 - (attendedLessons.get(i)));
        }

          System.out.println("Student course attendance rate:");
          System.out.println(attendancePercentage);

          System.out.println("\nLessons missed");
          System.out.println(missedLessons);

          System.out.println("\nAttendance Limit for the courses");
          System.out.println(attendanceLimit);

          System.out.print("\nFAIL or PASS \n[");
          for(int i = 0; i<missedLessons.size();i++){
              if(missedLessons.get(i)>attendanceLimit.get(i)){
                  System.out.print("FAIL,");
              }else{System.out.print("PASS,");}
          }
          System.out.println("]");

        return values;
    }
  
      public Map<String,Object> generateCourseReport(int courseId) {
        Map<String,Object> values = courseRepository.generateCourseReport(courseId);
        System.out.println("Course report generated succesfully! \n " +
                "   Course name: "+values.get("name")+
                "\n    Course number: "+values.get("number")+
                "\n    Course department: "+values.get("department")+
                "\n    Course credits: "+values.get("credits")+
                "\n    Course student count: "+  values.get("student_count")+
                "\n    Course max student count: "+  values.get("max_students")+
                "\n    Course Instructor: "+  values.get("instructor_id")+
                        "\n    Course Average: "+  ((double) values.get("total_student_score")/(double)values.get("student_count")));
        return values;
    }

    public void createCourseSchedule(long student_id) {
        ArrayList<Course> courses = (ArrayList<Course>) courseRepository.createCourseSchedule(student_id);
        // i represents days of the week, in our Uni, we had lessons max 6 days a week including labs and lessons.
        String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday","Friday","Saturday","Sunday"};
        for(int i = 0; i < 6;i++){
            System.out.println("Courses on "+weekDays[i] + "\n");
            for(int b = 0; b < 2;b++){
                if(b == 0){
                    System.out.println("   Lesson Time: 10:00 AM \n");
                }else{
                    System.out.println("   ____________________\n   Lesson Time: 1:30 PM \n");
                }
                System.out.println(
                        "   Course name: "+courses.get(i).getCourseName()+"\n"+
                        "   Course number: "+courses.get(i).getCourseNumber()+"\n"
                );
                // THIS SCHEDULE PRINTS THE SAME LESSON TWICE THE SAME DAY
                // COULD VE BEEN BETTER, TIME SCRAMBLE

            }
        }

    }

}