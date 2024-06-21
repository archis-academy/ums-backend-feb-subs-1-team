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
        int[] courseIds = courseRepository.getStudentEnrolledCourses(studentId);
        ArrayList<Course> courses = new ArrayList<>();
        for(int i = 0;i< courseIds.length;i++){
            courses.add(courseRepository.getCourseById(courseIds[i]));
        }
        return courses;
    }

    public List<Student> getCourseEnrolledStudents(int studentId){
        return courseRepository.getCourseEnrolledStudents(studentId);
    }

    public Course getCourseWithMostStudents(){
        return courseRepository.getCourseById(CourseRepository.getCourseWithMostStudents());
    }
    public double calculateAverageGradeForCourse(int course_id){
        Map<String,Double> values = courseRepository.calculateAverageGradeForCourse(course_id);
        double grade = values.get("sum_grade");
        double num = values.get("num_of_students");
        double average = Math.round((grade/num) * 100.0) / 100.0;
        System.out.println("Average grade of students in this course: " +average);
        return  average;
    }
}





