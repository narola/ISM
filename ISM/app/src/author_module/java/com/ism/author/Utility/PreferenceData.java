package com.ism.author.Utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by c166 on 27/10/15.
 */
public class PreferenceData {


    public static final String PREFS_ISM = "SharedPreferenceISM";
    public static final String IS_REMEMBER_ME = "isRememberMe";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";

    //this is my preference data...

    public static final String LIKE_ID_LIST = "likeIdList";
    public static final String UNLIKE_ID_LIST = "unlikeIdList";


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
        return context.getSharedPreferences(PREFS_ISM, 0).getString(prefKey, "");
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

    public static void clearPreference(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_ISM, 0).edit();
        editor.clear();
        editor.commit();
    }
}
