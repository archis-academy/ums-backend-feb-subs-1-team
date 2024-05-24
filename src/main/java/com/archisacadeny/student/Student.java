package com.archisacadeny.student;

import com.archisacadeny.course.Course;

import java.time.LocalDate;
import java.util.List;

public class Student {
    private long id;
    private String fullName;
    private String gender;
    private String identityNo;
    private LocalDate enrollmentDate;
    private List<Course> enrolledCourses;
    private int yearOfStudy;
    private int totalCreditCount;

    public Student(long id, String fullName, String gender, String identityNo, LocalDate enrollmentDate, int yearOfStudy, int totalCreditCount) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.identityNo = identityNo;
        this.enrollmentDate = enrollmentDate;
        this.yearOfStudy = yearOfStudy;
        this.totalCreditCount = totalCreditCount;
        this.enrolledCourses = enrolledCourses;
    }
    public long getId() { return id; }

    public String getFullName() { return fullName; }

    public String getGender() { return gender; }

    public List<Course> getEnrolledCourses(int i) { return enrolledCourses; }

    public String getIdentityNo() { return identityNo; }

    public LocalDate getEnrollmentDate() { return enrollmentDate; }

    public int getYearOfStudy() { return yearOfStudy; }

    public int getTotalCreditCount() { return totalCreditCount; }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
    }
}