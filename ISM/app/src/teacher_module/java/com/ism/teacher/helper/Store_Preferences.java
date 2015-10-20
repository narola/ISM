package com.ism.teacher.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Store_Preferences {

    SharedPreferences pref_master;
    SharedPreferences.Editor editor_pref_master;
    Context context;

    public Store_Preferences(Context context) {
        this.context = context;
        pref_master = context.getSharedPreferences("dava_india_pref", 0);
    }

    public void open_editor() {
        editor_pref_master = pref_master.edit();
    }

    public void set_Username(String username) {
        open_editor();
        editor_pref_master.putString("username", username);
        editor_pref_master.commit();
    }

    public String get_username() {
        return pref_master.getString("username", "");
    }


    public void set_Password(String password) {
        open_editor();
        editor_pref_master.putString("password", password);
        editor_pref_master.commit();
    }

    public String get_Password() {
        return pref_master.getString("password", "");
    }

    public void setUserLocationPincode(String pincode) {
        open_editor();
        editor_pref_master.putString("pincode", pincode);
        editor_pref_master.commit();
    }

    public String getUserLocationPincode() {
        return pref_master.getString("pincode", "");
    }

    public void setUserLocationCity(String cityFromGPS) {
        open_editor();
        editor_pref_master.putString("city_gps", cityFromGPS);
        editor_pref_master.commit();
    }

    public String getUserLocationCity() {
        return pref_master.getString("city_gps", "");
    }

    public void clear_data() {
        open_editor();
        editor_pref_master.putString("username", "");
        editor_pref_master.putString("pincode", "");
        editor_pref_master.putString("city_gps", "");
        editor_pref_master.putString("city_from_dropdown", "");
        editor_pref_master.putString("city_found_flag", "");
        editor_pref_master.commit();
    }


    /**
     * Used to save city as soon as GPS Detects..or when it founds current city from all city list after iteration.
     * We need cityfromdropdown to make it red on home screen.
     * @param cityFromDropDown
     */

    public void setCityFromDropDown(String cityFromDropDown) {
        open_editor();
        editor_pref_master.putString("city_from_dropdown", cityFromDropDown);
        editor_pref_master.commit();
    }

    public String getCityFromDropDown() {
        return pref_master.getString("city_from_dropdown", "");
    }

    /**
     * Flag set when current city reflected both in red and in spinner.
     * This flag used to call ws and detect location again.
     * @param found
     */

    public void setCurrentCityFoundFlag(String found) {
        open_editor();
        editor_pref_master.putString("city_found_flag", found);
        editor_pref_master.commit();
    }

    public String isCurrentCityFound() {
        return pref_master.getString("city_found_flag", "");

    }

    /**
     * This pref stores the username if user had selected remember checkbox while login
     * even after logout.
     * @param username
     */
    public void saveUsername(String username)
    {
        open_editor();
        editor_pref_master.putString("remember_username", username);
        editor_pref_master.commit();
    }

    public String getSavedUsername()
    {
        return pref_master.getString("remember_username", "");
    }


    /**
     * Storing the new city if user changes the city from drop down.(without affecting the cityfromGPS because we need it in painting the current city not
     * the changed city to red color on home resume.
     * Otherwise the city which is automatically detected by GPS will be retrieved in verify dialog and passed as param.
     *
     * @param newcity
     */


    public void setCityAfterChangedFromDropDown(String newcity)
    {
        open_editor();
        editor_pref_master.putString("new_city", newcity);
        editor_pref_master.commit();
    }

    public String getNewCityAfterChangedFromDropDown()
    {
        return pref_master.getString("new_city","");

    }


    /**
     * Storing the arrays after separating current city and retrieving it when when home resume and current city already found.
     * Then append currentcityfrom pref+all the elements from array which is stored in pref and separating the values and replacing the "" and "," symbols
     * @param majorcitiesexceptCurrentCity
     */

    public void setMajorCitiesAfterCurrentCityFound(String majorcitiesexceptCurrentCity)
    {
        open_editor();
        editor_pref_master.putString("saved_major_city", majorcitiesexceptCurrentCity);
        editor_pref_master.commit();
    }

    public String getSavedCitiesAfterCurrentCity()
    {
        return pref_master.getString("saved_major_city","");
    }

}
