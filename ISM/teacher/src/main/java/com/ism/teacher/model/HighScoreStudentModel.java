package com.ism.teacher.model;

/**
 * Created by c162 on 12/10/15.
 */
public class HighScoreStudentModel {
    String strStudentName;
    String strStudentMarks;
    String strStudentSchoolYearClass;
    String strStudentDp;

    public String getStrStudentName() {
        return strStudentName;
    }

    public void setStrStudentName(String strStudentName) {
        this.strStudentName = strStudentName;
    }

    public String getStrStudentMarks() {
        return strStudentMarks;
    }

    public void setStrStudentMarks(String strStudentMarks) {
        this.strStudentMarks = strStudentMarks;
    }

    public String getStrStudentSchoolYearClass() {
        return strStudentSchoolYearClass;
    }

    public void setStrStudentSchoolYearClass(String strStudentSchoolYearClass) {
        this.strStudentSchoolYearClass = strStudentSchoolYearClass;
    }

    public String getStrStudentDp() {
        return strStudentDp;
    }

    public void setStrStudentDp(String strStudentDp) {
        this.strStudentDp = strStudentDp;
    }

    public HighScoreStudentModel(String strStudentName, String strStudentSchoolYearClass, String strStudentMarks, String strStudentDp) {

        this.strStudentName = strStudentName;
        this.strStudentMarks = strStudentMarks;
        this.strStudentSchoolYearClass = strStudentSchoolYearClass;
        this.strStudentDp = strStudentDp;
    }
}
