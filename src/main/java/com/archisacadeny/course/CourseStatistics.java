package com.archisacadeny.course;

public class CourseStatistics {
    private long id;
    private double averageGrade;
    private double highestGrade;
    private double lowestGrade;

    public CourseStatistics(long id, double averageGrade, double highestGrade, double lowestGrade) {
        this.id = id;
        this.averageGrade = averageGrade;
        this.highestGrade = highestGrade;
        this.lowestGrade = lowestGrade;
    }

    public CourseStatistics(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public double getHighestGrade() {
        return highestGrade;
    }

    public void setHighestGrade(double highestGrade) {
        this.highestGrade = highestGrade;
    }

    public double getLowestGrade() {
        return lowestGrade;
    }

    public void setLowestGrade(double lowestGrade) {
        this.lowestGrade = lowestGrade;
    }
}
