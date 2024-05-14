package com.archisacadeny.student;


public class Student {
    private long id;
    private String fullName;
    private String email;
    private String password;
    private String enrolledCourse;


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

    public String getEnrolledCourse(){return enrolledCourse;}

    public void setEnrolledCourse(String enrolledCourse){this.enrolledCourse = enrolledCourse;}


}
