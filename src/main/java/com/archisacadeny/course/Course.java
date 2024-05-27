package com.archisacadeny.course;

import com.archisacadeny.config.DataBaseConfig;
import com.archisacadeny.instructor.Instructor;
import com.archisacadeny.student.Student;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private long id;
    private String courseName;
    private Instructor instructor;
    private long creditHours;
    private long totalStudent;
    private List<Student> enrolledStudent;
    private String department;
    private int maxStudents;

    public Course(long id, String courseName, Instructor instructor, long creditHours, long totalStudent, List<Student> enrolledStudent, String department, int maxStudents) {
        this.id = id;
        this.courseName = courseName;
        this.instructor = instructor;
        this.creditHours = creditHours;
        this.totalStudent = totalStudent;
        this.enrolledStudent = enrolledStudent;
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

    public long getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(long creditHours) {
        this.creditHours = creditHours;
    }

    public long getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(long totalStudent) {
        this.totalStudent = totalStudent;
    }

    public List<Student> getEnrolledStudent() {
        return enrolledStudent;
    }

    public void setEnrolledStudent(List<Student> enrolledStudent) {
        this.enrolledStudent = enrolledStudent;
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
