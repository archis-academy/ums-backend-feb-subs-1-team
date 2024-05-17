package com.archisacadeny.instructor;

import com.archisacadeny.course.Course;

import java.util.ArrayList;
import java.util.List;

public class Instructor {

    private long id;
    private String name;
    private String number;
    private String email;
    private String password;
    private List<Course> courses;

    public Instructor() {this.courses = new ArrayList<>();}

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getNumber() {return number;}
    public void setNumber(String number) {this.number = number;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public List<Course> getCourses() {return courses;}
    public void setCourses(List<Course> courses) {this.courses = courses;}
}
