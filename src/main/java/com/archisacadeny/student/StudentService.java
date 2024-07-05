package com.archisacadeny.student;

import java.sql.SQLException;
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
        if (!validateEmailDuringStudentRegistration(student.getEmail())){
            return student;
        }
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

    public boolean validateEmailDuringStudentRegistration(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        if (!email.matches(emailRegex)) {
            System.out.println("Invalid email format: " + email);
            return false;
        }

        return studentRepository.validateEmailDuringStudentRegistration(email);
    }
}