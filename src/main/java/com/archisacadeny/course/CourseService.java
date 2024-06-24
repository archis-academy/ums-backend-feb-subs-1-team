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
        return courseRepository.isCourseFull(courseId);
    }

    public void update(String courseNumber, Course course ){
        courseRepository.update(courseNumber,course);
    }

    public Double getTotalCreditAmount(long studentId){
        return courseRepository.getTotalCreditAmount(studentId);
    }

    public List<Course> getStudentEnrolledCourses(int studentId){
       return courseRepository.getStudentEnrolledCourses(studentId);
    }

    public List<Student> getCourseEnrolledStudents(int studentId){
        return courseRepository.getCourseEnrolledStudents(studentId);
    }

    public Course getCourseWithMostStudents(){
        return courseRepository.getCourseById(courseRepository.getCourseWithMostStudents());
        // tek id donduren methodlarda getCourseById() kullanmam dogru mu TODO
    }
    public double calculateAverageGradeForCourse(int course_id){
        Map<String,Double> values = courseRepository.calculateAverageGradeForCourse(course_id);
        double grade = values.get("sum_grade");
        double num = values.get("num_of_students");
        double average = Math.round((grade/num) * 100.0) / 100.0;
        System.out.println("Average grade of students in this course: " +average);
        return  average;
    }

    public CourseStatistics calculateCourseStatistics(int course_id){
        Map<String,Double> values = courseRepository.calculateCourseStatistics(course_id);
        double grade = values.get("sum_grade");
        double num = values.get("num_of_students");
        double average = Math.round((grade/num) * 100.0) / 100.0;
        double min = values.get("min_grade");
        double max = values.get("max_grade");
        System.out.println("Course id: "+course_id +" | Average grade: " +average+ " | Max Grade: "+max+" | Min grade "+min);
        return new CourseStatistics(course_id,average,max,min);
    }

    public List<Course> getAllCourses(){
        return courseRepository.getAllCourses();
    }
}





