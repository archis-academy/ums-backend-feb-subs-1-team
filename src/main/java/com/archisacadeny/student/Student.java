package com.archisacadeny.student;

import com.archisacadeny.course.Course;

import java.sql.Timestamp;
import java.util.List;

public class Student {
    private long id;
    private String fullName;
    private String gender;
    private String identityNo;
    private Timestamp enrollmentDate;
    private List<Course> enrolledCourses;
    private int yearOfStudy;
    private int totalCreditCount;

    public Student(long id, String fullName, String gender, String identityNo, Timestamp enrollmentDate, int yearOfStudy, int totalCreditCount) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.identityNo = identityNo;
        this.enrollmentDate = enrollmentDate;
        this.yearOfStudy = yearOfStudy;
        this.totalCreditCount = totalCreditCount;
        this.enrolledCourses = enrolledCourses;
    }

    public Student(){

    }
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public List<Course> getEnrolledCourses() { return enrolledCourses; }

    public void setEnrolledCourses(List<Course> enrolledCourses) { this.enrolledCourses = enrolledCourses; }

    public String getIdentityNo() { return identityNo; }
    public void setIdentityNo(String identityNo) { this.identityNo = identityNo; }

    public Timestamp getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(Timestamp enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public int getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }

    public int getTotalCreditCount() { return totalCreditCount; }
    public void setTotalCreditCount(int totalCreditCount) { this.totalCreditCount = totalCreditCount; }

}