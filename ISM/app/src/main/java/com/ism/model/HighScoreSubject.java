package com.ism.model;

import com.ism.ws.model.Data;

import java.util.ArrayList;

/**
 * Created by c162 on 12/10/15
 * Updated by c161 on 17/11/15
 */
public class HighScoreSubject {

	private String subjectName;
	private ArrayList<Data> arrListStudent;

	public String getSubjectName() {
        return subjectName;
    }

	public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

	public ArrayList<Data> getArrListStudent() {
		return arrListStudent;
	}

	public void setArrListStudent(ArrayList<Data> arrListStudent) {
		this.arrListStudent = arrListStudent;
	}

}
