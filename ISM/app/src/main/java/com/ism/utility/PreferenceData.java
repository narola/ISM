package com.ism.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by c161 on --/10/15.
 */
public class PreferenceData {

	public static final String PREFS_ISM = "SharedPreferenceISM";

	public static final String IS_REMEMBER_ME = "isRememberMe";

//	ProfileInfo  ============================
	public static final String IS_REMEMBER_ME_FIRST_LOGIN = "isFirstLoginRememberMe";
	public static final String PASSWORD = "password";
	public static final String CREDENTIAL_ID = "credentialId";
	public static final String SCHOOL_ID = "schoolId";
	public static final String SCHOOL_NAME = "schoolName";
	public static final String SCHOOL_DISTRICT = "schoolDistrict";
	public static final String SCHOOL_TYPE = "schoolType";
	public static final String CLASS_ID = "classId";
	public static final String CLASS_NAME = "className";
	public static final String COURSE_ID = "courseId";
	public static final String COURSE_NAME = "courseName";
	public static final String ACADEMIC_YEAR = "academicYear";
	public static final String ROLE_ID = "roleId";
//	=========================================

	public static final String SCHOOL_GRADE = "schoolGrade";
	public static final String IS_TUTORIAL_GROUP_ALLOCATED = "isTutorialGroupAllocated";
	public static final String IS_TUTORIAL_GROUP_ACCEPTED = "isTutorialGroupAccepted";
	public static final String IS_TUTORIAL_GROUP_COMPLETED = "isTutorialGroupCompleted";
	public static final String TUTORIAL_GROUP_ID = "tutorialGroupId";
	public static final String TUTORIAL_GROUP_NAME = "tutorialGroupName";
	public static final String USER_ID = "userId";
	public static final String FULL_NAME = "fullName";
	public static final String PROFILE_PIC = "profilePic";

	public static void setBooleanPrefs(String prefKey, Context context, boolean value) {
		SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_ISM, 0).edit();
		editor.putBoolean(prefKey, value);
		editor.commit();
	}

	public static boolean getBooleanPrefs(String prefKey, Context context) {
		return context.getSharedPreferences(PREFS_ISM, 0).getBoolean(prefKey, false);
	}
	
	public static boolean getBooleanPrefs(String prefKey, Context context, boolean defaultValue) {
		return context.getSharedPreferences(PREFS_ISM, 0).getBoolean(prefKey, defaultValue);
	}

	public static void setStringPrefs(String prefKey, Context context, String Value) {
		SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_ISM, 0).edit();
		editor.putString(prefKey, Value);
		editor.commit();
	}

	public static String getStringPrefs(String prefKey, Context context) {
		return context.getSharedPreferences(PREFS_ISM, 0).getString(prefKey, null);
	}

	public static String getStringPrefs(String prefKey, Context context, String defaultValue) {
		return context.getSharedPreferences(PREFS_ISM, 0).getString(prefKey, defaultValue);
	}

	public static void setIntPrefs(String prefKey, Context context, int value) {
		SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_ISM, 0).edit();
		editor.putInt(prefKey, value);
		editor.commit();
	}

	public static int getIntPrefs(String prefKey, Context context) {
		return context.getSharedPreferences(PREFS_ISM, 0).getInt(prefKey, 0);
	}

	public static int getIntPrefs(String prefKey, Context context, int defaultValue) {
		return context.getSharedPreferences(PREFS_ISM, 0).getInt(prefKey, defaultValue);
	}

	public static void setLongPrefs(String prefKey, Context context, long value) {
		SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_ISM, 0).edit();
		editor.putLong(prefKey, value);
		editor.commit();
	}

	public static long getLongPrefs(String prefKey, Context context) {
		return context.getSharedPreferences(PREFS_ISM, 0).getLong(prefKey, 0);
	}

	public static long getLongPrefs(String prefKey, Context context, long defaultValue) {
		return context.getSharedPreferences(PREFS_ISM, 0).getLong(prefKey, defaultValue);
	}

	public static void setFloatPrefs(String prefKey, Context context, float value) {
		SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_ISM, 0).edit();
		editor.putFloat(prefKey, value);
		editor.commit();
	}

	public static float getFloatPrefs(String prefKey, Context context) {
		return context.getSharedPreferences(PREFS_ISM, 0).getFloat(prefKey, 0);
	}

	public static float getFloatPrefs(String prefKey, Context context, long defaultValue) {
		return context.getSharedPreferences(PREFS_ISM, 0).getFloat(prefKey, defaultValue);
	}

	/**
	 * Clear all data in SharedPreference
	 * @param context
	 */
	public static void clearWholePreference(Context context) {
		SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_ISM, 0).edit();
	    editor.clear();
	    editor.commit();
	}

	/**
	 * Clear single key value
	 * @param prefKey
	 * @param context
	 */
	public static void remove(String prefKey, Context context) {
		SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_ISM, 0).edit();
	    editor.remove(prefKey);
	    editor.commit();
	}

}