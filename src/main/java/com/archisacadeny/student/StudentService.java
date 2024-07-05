package com.archisacadeny.student;

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

    public Student updateStudentInfo(Student student) {
        return studentRepository.updateStudentInfo(student);
    }

    public Student viewStudentDetails(Long studentId) {

        Student savedStudent = studentRepository.viewStudentDetails(studentId);
        System.out.println(savedStudent.getFullName());
        System.out.println(savedStudent.getId());
        System.out.println(savedStudent.getGender());
        System.out.println(savedStudent.getIdentityNo());
        System.out.println(savedStudent.getYearOfStudy());
        System.out.println(savedStudent.getEnrollmentDate());


        return savedStudent;
    }

    public Student getStudentByID(long studentId) {
        return studentRepository.getStudentByID(studentId);
    }

    public List<Student> getAllStudents() {
        //list students
        List<Student> students = studentRepository.listAllStudents();
        for (Student student : students) {
            System.out.println(student.getFullName());
            System.out.println(student.getId());
            System.out.println(student.getEnrollmentDate());
            System.out.println(student.getIdentityNo());
        }

        return students;
    }
}