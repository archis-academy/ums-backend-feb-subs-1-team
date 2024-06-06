package com.archisacadeny.course;

import com.archisacadeny.config.DataBaseConfig;
import com.archisacadeny.instructor.Instructor;
import com.archisacadeny.student.Student;
import java.util.List;
import java.util.ArrayList;
import com.archisacadeny.config.DataBaseConnectorConfig;
import java.sql.SQLException;
import java.sql.Statement;


public class Course {
    private long id;
    private String courseName;
    private Instructor instructor;
    private long credits;
    private long courseNumber;
    private List<Student> enrolledStudents;
    private String department;
    private int maxStudents;

    public Course(long id, String courseName, Instructor instructor, long credits, long courseNumber, List<Student> enrolledStudents, String department, int maxStudents) {
        this.id = id;
        this.courseName = courseName;
        this.instructor = instructor;
        this.credits = credits;
        this.courseNumber = courseNumber;
        this.enrolledStudents = enrolledStudents;
        this.department = department;
        this.maxStudents = maxStudents;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public long getCredits() {
        return credits;
    }

    public void setCredits(long credits) {
        this.credits = credits;
    }

    public long getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(long courseNumber) {
        this.courseNumber = courseNumber;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

}



