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
    private long creditAmount;
    private long totalStudent;
    private List<Student> enrolledStudent;
    private String department;

    public Course(long id, String courseName, Instructor instructor, long creditAmount, long totalStudent, List<Student> enrolledStudent, String department) {
        this.id = id;
        this.courseName = courseName;
        this.instructor = instructor;
        this.creditAmount = creditAmount;
        this.totalStudent = totalStudent;
        this.enrolledStudent = enrolledStudent;
        this.department = department;
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

    public long getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(long creditAmount) {
        this.creditAmount = creditAmount;
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
}
