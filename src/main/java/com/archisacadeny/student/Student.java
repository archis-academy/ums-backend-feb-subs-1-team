package com.archisacadeny.student;

import java.time.LocalDate;

public class Student {
    private int id;
    private final String fullName;
    private final String gender;
    private final int courseId;
    private final String identityNo;
    private final LocalDate unvEnrollmentDate;
    private final int yearOfStudy;
    private final int totalCreditCount;

    public Student(int id, String fullName, String gender, int courseId, String identityNo, LocalDate unvEnrollmentDate, int yearOfStudy, int totalCreditCount) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.courseId = courseId;
        this.identityNo = identityNo;
        this.unvEnrollmentDate = unvEnrollmentDate;
        this.yearOfStudy = yearOfStudy;
        this.totalCreditCount = totalCreditCount;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public LocalDate getUnvEnrollmentDate() {
        return unvEnrollmentDate;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public int getTotalCreditCount() {
        return totalCreditCount;
    }

    public int getId() {
        return id;
    }
}
