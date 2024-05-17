package com.archisacadeny.student;

import java.util.List;

public interface StudentRepository {

    List<Student> findByCourseId(int courseId);
}