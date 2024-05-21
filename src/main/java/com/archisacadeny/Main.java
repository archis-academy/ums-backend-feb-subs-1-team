package com.archisacadeny;

import com.archisacadeny.config.DataBaseConfig;
import com.archisacadeny.config.DataBaseConnectorConfig;
import com.archisacadeny.course.Course;
import com.archisacadeny.course.CourseRepository;
import com.archisacadeny.person.Person;
import com.archisacadeny.person.PersonRepository;
import com.archisacadeny.person.PersonService;
import com.archisacadeny.student.StudentRepository;

public class Main {
    public static void main(String[] args) {
        DataBaseConnectorConfig.setConnection();

        StudentRepository.createStudentCourseMapperTable();

    }
}
