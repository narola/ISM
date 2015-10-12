package com.ism.model;

/**
 * Created by c162 on 12/10/15.
 */
public class HighScoreStudentModel {
    String studentName;
    String studentMarks;
    String StudentSchoolYearClass;
    String StudentDp;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentMarks() {
        return studentMarks;
    }

    public void setStudentMarks(String studentMarks) {
        this.studentMarks = studentMarks;
    }

    public String getStudentSchoolYearClass() {
        return StudentSchoolYearClass;
    }

    public void setStudentSchoolYearClass(String studentSchoolYearClass) {
        StudentSchoolYearClass = studentSchoolYearClass;
    }

    public String getStudentDp() {
        return StudentDp;
    }

    public void setStudentDp(String studentDp) {
        StudentDp = studentDp;
    }

    public HighScoreStudentModel(String studentName, String studentSchoolYearClass, String studentMarks, String studentDp) {

        this.studentName = studentName;
        this.studentMarks = studentMarks;
        StudentSchoolYearClass = studentSchoolYearClass;
        StudentDp = studentDp;
    }
}
