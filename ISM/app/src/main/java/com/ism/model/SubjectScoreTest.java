package com.ism.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by c161 on 16/10/15.
 */
public class SubjectScoreTest {

	private static final String TAG = SubjectScoreTest.class.getSimpleName();

	private String strSubject;
	private int intScore;

	public SubjectScoreTest() {
	}

	public SubjectScoreTest(String strSubject, int intScore) {
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

	public static ArrayList<SubjectScoreTest> getSubjectScore() {
		try {
			ArrayList<SubjectScoreTest> subjectScoreTests = new ArrayList<SubjectScoreTest>();
			for (int i = 0; i < 7; i++) {
				subjectScoreTests.add(new SubjectScoreTest("Subject " + i, i * 10));
			}
			return subjectScoreTests;
		} catch (Exception e) {
			Log.e(TAG, "getSubjectScore Exception : " + e.toString());
			return null;
		}
	}

}
