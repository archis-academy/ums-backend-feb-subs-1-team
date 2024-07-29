package com.archisacadeny.student;

import com.archisacadeny.course.Course;

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

    public void deleteStudent(int studentId) {
        studentRepository.deleteStudent(studentId);
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

    public boolean validateEmailDuringStudentRegistration(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        if (!email.matches(emailRegex)) {
            System.out.println("Invalid email format: " + email);
            return false;
        }

        return studentRepository.validateEmailDuringStudentRegistration(email);

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