package com.archisacadeny;

import com.archisacadeny.config.DataBaseConfig;
import com.archisacadeny.config.DataBaseConnectorConfig;
import com.archisacadeny.course.Course;
import com.archisacadeny.course.CourseService;
import com.archisacadeny.course.CourseRepository;
import com.archisacadeny.instructor.Instructor;
import com.archisacadeny.instructor.InstructorRepository;
import com.archisacadeny.instructor.InstructorService;
import com.archisacadeny.person.Person;
import com.archisacadeny.person.PersonRepository;
import com.archisacadeny.person.PersonService;
import com.archisacadeny.student.CourseStudentMapper;
import com.archisacadeny.student.Student;
import com.archisacadeny.student.StudentRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataBaseConnectorConfig.setConnection();
    }
}