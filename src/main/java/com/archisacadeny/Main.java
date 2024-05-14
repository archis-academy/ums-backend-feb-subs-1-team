package com.archisacadeny;

import com.archisacadeny.config.DataBaseConfig;
import com.archisacadeny.config.DataBaseConnectorConfig;
import com.archisacadeny.course.CourseRepository;
import com.archisacadeny.instructor.Instructor;
import com.archisacadeny.instructor.InstructorRepository;
import com.archisacadeny.person.PersonRepository;
import com.archisacadeny.student.StudentRepository;

public class Main {
    public static void main(String[] args) {
        DataBaseConnectorConfig.setConnection();
        PersonRepository.createPersonTable();
        CourseRepository.createCourseTable();
        InstructorRepository.createInstructorTable();
        StudentRepository.createStudentTable();
    }
}