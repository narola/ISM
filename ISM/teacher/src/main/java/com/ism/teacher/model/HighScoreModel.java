package com.ism.teacher.model;

import java.util.ArrayList;

/**
 * Created by c162 on 12/10/15.
 */
public class HighScoreModel {


    String strSubjectName;
    ArrayList<HighScoreStudentModel> arrayListStudent = new ArrayList<HighScoreStudentModel>();

    public String getStrSubjectName() {
        return strSubjectName;
    }

    public void setStrSubjectName(String strSubjectName) {
        this.strSubjectName = strSubjectName;
    }

    public ArrayList<HighScoreStudentModel> getArrayListStudent() {
        return arrayListStudent;
    }

    public void setArrayListStudent(ArrayList<HighScoreStudentModel> arrayListStudent) {
        this.arrayListStudent = arrayListStudent;
    }

    public HighScoreModel(String strSubjectName, ArrayList<HighScoreStudentModel> arrayListStudent) {
        this.strSubjectName = strSubjectName;

        this.arrayListStudent = arrayListStudent;
    }
}
