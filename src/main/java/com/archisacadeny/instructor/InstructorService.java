package com.archisacadeny.instructor;

import com.archisacadeny.config.DataBaseConnectorConfig;
import com.archisacadeny.course.Course;
import com.archisacadeny.course.CourseRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;

    public InstructorService(InstructorRepository instructorRepository, CourseRepository courseRepository) {
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
    }

    public Instructor createInstructor(Instructor instructor) {
        if(instructor== null){
            throw new RuntimeException("instructor is null");
        }
        return instructorRepository.save(instructor);
    }

    public List<Instructor> listAllInstructors() {
        return instructorRepository.listAllInstructors();
    }

    public Instructor getInstructorById(long instructorId){
        return instructorRepository.getInstructorById(instructorId);
    }

    public Instructor updateInstructor(Instructor instructor) {
        if(instructor == null){
            throw new RuntimeException("instructor is null");
        }
        return instructorRepository.updateInstructor(instructor);
    }

    public void deleteInstructor(long instructorId) {
        instructorRepository.deleteInstructor(instructorId);
    }

    public Instructor createInstructor(long id, String instructorNumber, String fullName, String email, String password){

        Instructor instructor = new Instructor();
        instructor.setId(id);
        instructor.setNumber(instructorNumber);
        instructor.setFullName(fullName);
        instructor.setEmail(email);
        instructor.setPassword(password);

        return instructorRepository.save(instructor);
    }

    public void viewInstructorTaughtCourses(long instructorId) {
        Instructor instructor = null ;
        List<Course> courses = courseRepository.getCoursesByInstructorId(instructorId);
        printCourseDetails(courses);

    }

    public int calculateTotalStudentsForInstructor(long instructorId) {
        List<Course> courses = courseRepository.getCoursesByInstructorId(instructorId);
        int totalStudents =0;

        for(Course course : courses) {
            totalStudents += courseRepository.getStudentCountForCourse(course.getId());

        }
        System.out.println("Total students for instructor with ID " + instructorId + ": " + totalStudents);
        return totalStudents;
    }

    public double calculateAverageSuccessGradeForInstructorCourses(long instructorId) {
        double totalGradeSum=0.0;
        int totalStudents=0;

        List<Course> courses= courseRepository.getCoursesByInstructorId(instructorId);
        for(Course course : courses) {

            Map<String,Object> gradeInfo = courseRepository.calculateAverageGradeForCourse(course.getId());
            double sumGrade = ((Number) gradeInfo.get("sum_grade")).doubleValue();
            int numStudents = ((Number) gradeInfo.get("num_of_students")).intValue();

            totalGradeSum += sumGrade;
            totalStudents += numStudents;
        }

        double averageGrade;
        if(totalStudents > 0){
            averageGrade= totalGradeSum / totalStudents;
        }else{
            averageGrade= 0.0;
        }
        System.out.println("Instructor ID: " + instructorId + "\nAverage grade: " + averageGrade);
        return averageGrade;

    }
    public void listMostRecommendedCoursesForInstructor(long instructorId, int topCount) {
        List<Course> recommendedCourses = instructorRepository.getMostRecommendedCoursesForInstructor(instructorId, topCount);
        printCourseDetails(recommendedCourses);

    }

    private void printCourseDetails(List<Course> courses) {

        for (Course course : courses) {
            System.out.println("Course Name: " + course.getCourseName());
            System.out.println("Course Number: " + course.getCourseNumber());
            System.out.println("Credits: " + course.getCredits());
            System.out.println("Department: " + course.getDepartment());
            System.out.println("Max Students: " + course.getMaxStudents());
            System.out.println("Instructor: " + course.getInstructor().getFullName());
            System.out.println();
        }
    }
}
