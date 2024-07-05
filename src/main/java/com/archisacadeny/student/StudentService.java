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
    public void createStudent(Student student){
        Student newStudent = studentRepository.createStudent(student);

        System.out.println("The student has been created with ID: " + newStudent.getId());
        System.out.println("Full Name: " + newStudent.getFullName());
        System.out.println("Email: " + newStudent.getEmail());
        System.out.println("Gender: " + newStudent.getGender());
        System.out.println("Identity No: " + newStudent.getGender());
        System.out.println("Enrollment Date: " + newStudent.getEnrollmentDate());
        System.out.println("Year of Study: " + newStudent.getYearOfStudy());
        System.out.println("Total credit: " + newStudent.getTotalCreditCount());
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

    public void updateStudentInfo(Student student) {
        Student newStudent = studentRepository.updateStudentInfo(student);

        System.out.println("The student has been updated with ID: " + student.getId());
        System.out.println("New Full Name: " + newStudent.getFullName());
        System.out.println("New Gender: " + newStudent.getGender());
        System.out.println("New Identity Number: " + newStudent.getIdentityNo());
        System.out.println("New Enrollment Date: " + newStudent.getEnrollmentDate());
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