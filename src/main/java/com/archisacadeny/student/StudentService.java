package com.archisacadeny.student;

import com.archisacadeny.course.Course;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }
    public void createStudentTable(){
        StudentRepository.createStudentTable();
    }
    public Student createStudent(Student student){
        return studentRepository.createStudent(student);
    }

    public StudentReport generateStudentAchievementReport(int studentId) {
        List<Map<String, Object>> resultSet = studentRepository.getStudentAchievementReportData(studentId);

        double totalWeightedGradePoints = 0.0;
        int totalCredits = 0;
        long studentIdFromDb = 0;
        String studentName = null;

        for (Map<String, Object> row : resultSet) {
            studentIdFromDb = (long) row.get("id");
            studentName = (String) row.get("full_name");
            long courseId = (long) row.get("course_id");
            int grade = (int) row.get("grade");

            int courseCredit = 0;
            try {
                courseCredit = studentRepository.getCourseCredit(courseId);
            } catch (SQLException e) {
                throw new RuntimeException("Error retrieving course credit", e);
            }

            totalWeightedGradePoints += grade * courseCredit;
            totalCredits += courseCredit;
        }

        double gpa = totalCredits > 0 ? totalWeightedGradePoints / totalCredits : 0.0;

        return new StudentReport(studentIdFromDb, studentName, gpa);
    }

    public void printStudentAchievementReport(int studentId) {
        StudentReport report = generateStudentAchievementReport(studentId);

        System.out.println("Student ID: " + report.getStudentId());
        System.out.println("Full Name: " + report.getFullName());
        System.out.println("GPA: " + report.getGpa());
    }

    public void listRecommendedCoursesForStudent(long studentId) {
        List<Course> recommendedCourses = studentRepository.listRecommendedCoursesForStudent(studentId);

        if (recommendedCourses.isEmpty()) {
            System.out.println("No recommended courses found for student with ID: " + studentId);
        } else {
            System.out.println("---------------");
            System.out.println("Recommended Courses for Student with ID " + studentId + ":");
            for (Course course : recommendedCourses) {
                System.out.println("Course Name: " + course.getCourseName());
                System.out.println("Department: " + course.getDepartment());
                System.out.println("Course's credit: " + course.getCredit());
            }
            System.out.println("---------------");
        }
    }
}