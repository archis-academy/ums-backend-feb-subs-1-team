package com.archisacadeny.course;

import com.archisacadeny.config.DataBaseConfig;
import com.archisacadeny.person.Person;
import com.archisacadeny.student.Student;

import java.util.List;

public class Course {
    private long id; // primary key
    private String title;
    // other fields
    private Person instructor; // instructor_id
    List<Student> courseStudents;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Person getInstructor() {
        return instructor;
    }

    public void setInstructor(Person instructor) {
        this.instructor = instructor;
    }
}
