package com.archisacadeny.student;

public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){

        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student){
        return  studentRepository.save(student);
    }
}
