package com.archisacadeny.instructor;

import com.archisacadeny.course.Course;
import com.archisacadeny.person.Person;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person {

    private String number;
    private List<Course> courses;

    public Instructor() {this.courses = new ArrayList<>();}

    public String getNumber() {return number;}
    public void setNumber(String number) {this.number = number;}

    public List<Course> getCourses() {return courses;}
    public void setCourses(List<Course> courses) {this.courses = courses;}
}
