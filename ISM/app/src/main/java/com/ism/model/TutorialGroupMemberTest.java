package com.ism.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by c161 on 15/10/15.
 */
public class TutorialGroupMemberTest {

	private static final String TAG = TutorialGroupMemberTest.class.getSimpleName();

	private String strDpPath;
	private String strName;
	private String strSchoolName;

	public TutorialGroupMemberTest(String strDpPath, String strName, String strSchoolName) {
		this.strDpPath = strDpPath;
		this.strName = strName;
		this.strSchoolName = strSchoolName;
	}

	public String getDpPath() {
		return strDpPath;
	}

	public void setDpPath(String strDpPath) {
		this.strDpPath = strDpPath;
	}

	public String getName() {
		return strName;
	}

	public void setName(String strName) {
		this.strName = strName;
	}

	public String getSchoolName() {
		return strSchoolName;
	}

	public void setSchoolName(String strSchoolName) {
		this.strSchoolName = strSchoolName;
	}

	public static ArrayList<TutorialGroupMemberTest> getTutorialGroupMembers() {
		try {
			ArrayList<TutorialGroupMemberTest> members = new ArrayList<TutorialGroupMemberTest>();
			members.add(new TutorialGroupMemberTest("Path", "Adam", "St. Xavier"));
			members.add(new TutorialGroupMemberTest("Path", "Adam", "St. Xavier"));
			members.add(new TutorialGroupMemberTest("Path", "Adam", "St. Xavier"));
			members.add(new TutorialGroupMemberTest("Path", "Adam", "St. Xavier"));
			members.add(new TutorialGroupMemberTest("Path", "Adam", "St. Xavier"));
			return members;
		} catch (Exception e) {
			Log.e(TAG, "getTutorialGroupMembers Exception : " + e.toString());
			return null;
		}
	}

}
