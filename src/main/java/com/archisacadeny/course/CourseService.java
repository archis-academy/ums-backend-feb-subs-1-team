package com.archisacadeny.course;


import com.archisacadeny.instructor.Instructor;
import com.archisacadeny.student.Student;

import java.util.ArrayList;
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

//    public Course getCourseWithMostStudents(){
//        Course course = courseRepository.getCourseById(courseRepository.getCourseWithMostStudents());
//        System.out.println("Course with most student:\n"+ course);
//        return course;
//    }
//    public double calculateAverageGradeForCourse(int course_id){
//        Map<String,Double> values = courseRepository.calculateAverageGradeForCourse(course_id);
//        double grade = values.get("sum_grade");
//        double num = values.get("num_of_students");
//        double average = Math.round((grade/num) * 100.0) / 100.0;
//        System.out.println("Average grade of students in this course: " +average);
//        return  average;
//    }
//
//    public CourseStatistics calculateCourseStatistics(int course_id){
//        Map<String,Double> values = courseRepository.calculateCourseStatistics(course_id);
//        double grade = values.get("sum_grade");
//        double num = values.get("num_of_students");
//        double average = Math.round((grade/num) * 100.0) / 100.0;
//        double min = values.get("min_grade");
//        double max = values.get("max_grade");
//        System.out.println("Course id: "+course_id +" | Average grade: " +average+ " | Max Grade: "+max+" | Min grade "+min);
//        return new CourseStatistics(course_id,average,max,min);
//    }
//
//    public List<Course> getAllCourses(){
//        ArrayList<Course> courses = courseRepository.getAllCourses();
//        System.out.println(courses);
//        return courses;
//    }

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
}