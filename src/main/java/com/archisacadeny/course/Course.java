package com.archisacadeny.course;

import com.archisacadeny.config.DataBaseConfig;
import com.archisacadeny.student.Student;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private long id;
    private String courseName;
    private long instructorId;

    private ArrayList<Student> enrolledStudents;

    public void displayEnrolledStudents() {
        System.out.println("Enrolled students in " + courseName + ":");
        for (Student student : enrolledStudents) {
            System.out.println(student.getFullName() + " (ID: " + student.getId() + ")");
        }
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

    public long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(long instructorId) {
        this.instructorId = instructorId;
    }

    public void setEnrolledStudents(ArrayList<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = (ArrayList<Student>) enrolledStudents;
    }
}
