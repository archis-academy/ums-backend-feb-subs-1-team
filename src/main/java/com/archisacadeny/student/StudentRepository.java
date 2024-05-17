package com.archisacadeny.student;

import java.util.List;

public interface StudentRepository {

    List<Student> findByCourseId(int courseId);

    List<Student> findAll();

    Student save(Student student);

    Student findById(int id);

    void update(Student student);

    void delete(int id);

}