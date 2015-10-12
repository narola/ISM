package com.ism.model;

import java.util.ArrayList;

/**
 * Created by c162 on 12/10/15.
 */
public class HighScoreModel {
    String subjectName;
    ArrayList<HighScoreStudentModel> arrayListStudent=new ArrayList<HighScoreStudentModel>();

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public ArrayList<HighScoreStudentModel> getArrayListStudent() {
        return arrayListStudent;
    }

    public void setArrayListStudent(ArrayList<HighScoreStudentModel> arrayListStudent) {
        this.arrayListStudent = arrayListStudent;
    }

    public HighScoreModel(String subjectName, ArrayList<HighScoreStudentModel> arrayListStudent) {
        this.subjectName = subjectName;

        this.arrayListStudent = arrayListStudent;
    }
}
