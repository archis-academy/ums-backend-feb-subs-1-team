package com.archisacadeny.student;

public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }
    public Student createStudent(Student student){
        return studentRepository.createStudent(student);
    }
    public Student updateStudentInfo(Student student){
        return studentRepository.updateStudentInfo(student);
    }
}
