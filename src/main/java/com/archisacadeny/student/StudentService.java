package com.archisacadeny.student;

import java.util.List;

public class StudentService {
    private final StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {

        return studentRepository.createStudent(student);
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
        return studentRepository.listAllStudents();
    }

    public void enrollStudentToCourse(long studentId, long courseId) {

    }
}