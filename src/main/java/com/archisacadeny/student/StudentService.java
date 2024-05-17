package com.archisacadeny.student;
import java.util.List;

public class StudentService {
    private final StudentRepositoryImpl studentRepository;

    public StudentService(StudentRepositoryImpl studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getCourseEnrolledStudents(int courseId) {
        return studentRepository.findByCourseId(courseId);
    }
}
