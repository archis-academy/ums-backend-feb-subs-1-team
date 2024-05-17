package com.archisacadeny.student;


import java.util.List;

public class Student {
    private long id;
    private String fullName;
    private String email;
    private String password;
    private String department;
    private int studentNumber;
    private String gender;
    private List<String> enrolledCourse;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List getEnrolledCourse(){
        return enrolledCourse;
    }

    public void setEnrolledCourse(List<String> enrolledCourse) {
        this.enrolledCourse = enrolledCourse;
    }

    public String getDepartment(){
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }
}
