package com.archisacadeny.course;

import com.archisacadeny.config.DataBaseConfig;
import com.archisacadeny.instructor.Instructor;
import com.archisacadeny.student.Student;
import java.util.List;
import java.util.ArrayList;

public class Course {
    private long id;
    private String courseName;
    private Instructor instructor;
    private long credit;
    private String courseNumber;
    private List<Student> enrolledStudents;
    private String department;
    private int maxStudents;


    public Course(long id, String courseName, Instructor instructor, long credits, String courseNumber, List<Student> enrolledStudents, java.lang.String department, int maxStudents) {
        this.id = id;
        this.courseName = courseName;
        this.instructor = instructor;
        this.credit = credits;
        this.courseNumber = courseNumber;
        this.enrolledStudents = enrolledStudents;
        this.department = department;
        this.maxStudents = maxStudents;
    }

    @Override
    public String toString(){
        return  "ID:" + this.getId() +"\n"+
                " Course Name: " + this.getCourseName()+"\n"+
                " Course Number: " + this.getCourseNumber()+"\n"+
                " Course Department: " + this.getDepartment()+"\n"+
                " Course Instructor: " + this.getInstructor().getFullName()+"\n"+
                " Course Credit: " + this.getCredit()+"\n"+
                " Course Max Student Limit: " + this.getMaxStudents()+"\n";
    }

    public Course() {

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

    public long getCredits() {
        return credit;
    }

    public void setCredits(long credit) {
        this.credit = credit;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
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

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public long getCredit() {
        return credit;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }
}

