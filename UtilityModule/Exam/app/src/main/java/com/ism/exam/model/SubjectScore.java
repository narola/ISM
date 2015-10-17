package com.ism.exam.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by c161 on 16/10/15.
 */
public class SubjectScore {

	private static final String TAG = SubjectScore.class.getSimpleName();

	private String strSubject;
	private int intScore;

	public SubjectScore() {
	}

	public SubjectScore(String strSubject, int intScore) {
		this.strSubject = strSubject;
		this.intScore = intScore;
	}

	public String getSubject() {
		return strSubject;
	}

	public void setSubject(String strSubject) {
		this.strSubject = strSubject;
	}

	public int getScore() {
		return intScore;
	}

	public void setScore(int intScore) {
		this.intScore = intScore;
	}

	public static ArrayList<SubjectScore> getSubjectScore() {
		try {
			ArrayList<SubjectScore> subjectScores = new ArrayList<SubjectScore>();
			for (int i = 0; i < 7; i++) {
				subjectScores.add(new SubjectScore("Subject " + i, i * 10));
			}
			return subjectScores;
		} catch (Exception e) {
			Log.e(TAG, "getSubjectScore Exception : " + e.toString());
			return null;
		}
	}

}
