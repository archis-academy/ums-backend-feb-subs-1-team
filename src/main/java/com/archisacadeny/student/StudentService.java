package com.archisacadeny.student;

public class StudentService {
    private final StudentRepository studentRepository;

    public void viewStudentDetails(Student student){

        System.out.println("Full Name:" + student.getFullName());
        System.out.println("Gender:" + student.getGender());
        System.out.println("Identity No:" + student.getIdentityNo());
        System.out.println("Enrollment Date:" + student.getEnrollmentDate());
        System.out.println("Years of study:" + student.getYearOfStudy());
        System.out.println("Total Credits:" + student.getTotalCreditCount());
    }
    public StudentService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }
    public Student createStudent(Student student){

        return studentRepository.createStudent(student);
    }
    public void updateStudentInfo(Student student){

        System.out.println("Full Name:" + student.getFullName());
        System.out.println("Gender:" + student.getGender());
        System.out.println("Identity No:" + student.getIdentityNo());
        System.out.println("Enrollment Date:" + student.getEnrollmentDate());

    }
}
