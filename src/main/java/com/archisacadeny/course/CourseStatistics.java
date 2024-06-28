package com.archisacadeny.course;

public class CourseStatistics {
    private long courseId;
    private double averageGrade;
    private double highestGrade;
    private double lowestGrade;

    public CourseStatistics(long id, double averageGrade, double highestGrade, double lowestGrade) {
        this.courseId = id;
        this.averageGrade = averageGrade;
        this.highestGrade = highestGrade;
        this.lowestGrade = lowestGrade;
    }

    public CourseStatistics(){

    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long id) {
        this.courseId = id;
    }

    public double getAverageGrade() {
        return averageGrade;
    }


    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public void setAverageGrade(double sum,int num) {
        this.averageGrade = Math.round((sum/num) * 100.0) / 100.0;;
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
