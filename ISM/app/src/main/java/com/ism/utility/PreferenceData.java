package com.ism.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by c161 on --/10/15.
 */
public class PreferenceData {

    public static final String PREFS_ISM = "SharedPreferenceISM";

    public static final String IS_REMEMBER_ME = "isRememberMe";

//  ============================  ProfileInfo  ============================
    public static final String IS_REMEMBER_ME_FIRST_LOGIN = "isFirstLoginRememberMe";
    public static final String USER_PASSWORD = "userPassword";
    public static final String USER_CREDENTIAL_ID = "userCredentialId";
    public static final String USER_SCHOOL_ID = "userSchoolId";
    public static final String USER_SCHOOL_NAME = "userSchoolName";
    public static final String USER_SCHOOL_DISTRICT = "userSchoolDistrict";
    public static final String USER_SCHOOL_TYPE = "userSchoolType";
    public static final String USER_CLASS_ID = "userClassId";
    public static final String USER_CLASS_NAME = "userClassName";
    public static final String USER_COURSE_ID = "userCourseId";
    public static final String USER_COURSE_NAME = "userCourseName";
    public static final String USER_ACADEMIC_YEAR = "userAcademicYear";
    public static final String USER_ROLE_ID = "userRoleId";
//	=======================================================================

    public static final String USER_SCHOOL_GRADE = "userSchoolGrade";
    public static final String IS_TUTORIAL_GROUP_ALLOCATED = "isTutorialGroupAllocated";
    public static final String IS_TUTORIAL_GROUP_ACCEPTED = "isTutorialGroupAccepted";
    public static final String IS_TUTORIAL_GROUP_COMPLETED = "isTutorialGroupCompleted";
    public static final String TUTORIAL_GROUP_ID = "tutorialGroupId";
    public static final String TUTORIAL_GROUP_NAME = "tutorialGroupName";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String USER_FULL_NAME = "userFullName";
    public static final String USER_PROFILE_PIC = "userprofilePic";
    public static final String BADGE_COUNT_NOTIFICATION = "badgeCountNotification";
    public static final String BADGE_COUNT_MESSAGE = "badgeCountMessage";
    public static final String BADGE_COUNT_REQUEST = "badgeCountRequest";
    public static final String SYNC_DATE_ADMIN_CONFIG = "syncDateAdminConfig";
    public static final String ACCESS_KEY = "accessKey";
    public static final String SECRET_KEY = "secretKey";

    //====================================================================
    //PS=> PRIVACYSETTING
    public static final String PS_VIWERS_VIEW_CONTACT = "ViewersViewContact";
    public static final String PS_VIWERS_VIEW_BIRTHDATE = "ViewersViewBirthdate";
    public static final String PS_VIWERS_VIEW_EXAMSCORE = "ViewersViewExamScore";

    //SA=> SMSAlerts
    public static final String SA_ALERT_NEW_ASSIGNMENT = "SMSAlertNewAssignment";
    public static final String SA_ALERT_EVALUATION_READY = "SMSAlertEvaluationReady";
    public static final String SA_ALERT_CONFERENCE_SCHECDULE = "SMSAlertConferenceSchedule";

    //NOTI=> NOTIFICATION
    public static final String NOTI_LIKE = "LikeNotification";
    public static final String NOTI_COMMENT = "CommentNotification";
    public static final String NOTI_FOLLOWED_AUTHOR_POST= "FollowedAuthorPostNotification";
    public static final String NOTI_TAGGED = "TaggedNotification";
    public static final String NOTI_NOTIFICATION_NOTICEBOARD = "NoticeboardNotiication";

    //=> Block user

    public static final String LAST_MODIFIED_DATE = "lastModifieddate";


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
     *
     * @param context
     */
    public static void clearWholePreference(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_ISM, 0).edit();
        editor.clear();
        editor.commit();
    }

    /**
     * Clear single key value
     *
     * @param prefKey
     * @param context
     */
    public static void remove(String prefKey, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_ISM, 0).edit();
        editor.remove(prefKey);
        editor.commit();
    }

}