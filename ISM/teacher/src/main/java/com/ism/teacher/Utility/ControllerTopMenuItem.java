package com.ism.teacher.Utility;

import android.content.Context;
import android.util.Log;

import com.ism.teacher.R;

import java.util.ArrayList;

/**
 * Created by c161 on 09/10/15.
 */
public class ControllerTopMenuItem {

    private static final String TAG = ControllerTopMenuItem.class.getSimpleName();

    private String strMenuItemTitle;
    private String strMenuItemAction;
    private ArrayList<String> arrListSubMenu;
    private boolean isActive;

    public ControllerTopMenuItem() {

    }

    public ControllerTopMenuItem(String strMenuItemTitle, String strMenuItemAction, ArrayList<String> arrListSubMenu) {
        this.strMenuItemTitle = strMenuItemTitle;
        this.strMenuItemAction = strMenuItemAction;
        this.arrListSubMenu = arrListSubMenu;
    }

    public String getMenuItemTitle() {
        return strMenuItemTitle;
    }

    public void setMenuItemTitle(String strMenuItemTitle) {
        this.strMenuItemTitle = strMenuItemTitle;
    }

    public String getMenuItemAction() {
        return strMenuItemAction;
    }

    public void setMenuItemAction(String strMenuItemAction) {
        this.strMenuItemAction = strMenuItemAction;
    }

    public ArrayList<String> getSubMenu() {
        return arrListSubMenu;
    }

    public void setSubMenu(ArrayList<String> arrListSubMenu) {
        this.arrListSubMenu = arrListSubMenu;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public static ArrayList<ControllerTopMenuItem> getMenuClassroom(Context mContext) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.classwall), null, null));
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.lesson_notes), mContext.getString(R.string.add_notes), null));
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.assignment), null, null));
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.exam), null, null));
        } catch (Exception e) {
            Log.e(TAG, "getMenuClassRoom Exception : " + e.toString());
        }
        return menu;
    }

    public static ArrayList<ControllerTopMenuItem> getMenuAssessment(Context mContext) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.ism_mock), null, null));
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.wassce), null, null));
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.end_of_term), null, null));
        } catch (Exception e) {
            Log.e(TAG, "getMenuAssessment Exception : " + e.toString());
        }
        return menu;
    }

    public static ArrayList<ControllerTopMenuItem> getMenuDesk(Context mContext) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.jotter), mContext.getString(R.string.add_notes), null));
            ArrayList<String> favorites = new ArrayList<String>();
            favorites.add(mContext.getString(R.string.notes));
            favorites.add(mContext.getString(R.string.books));
            favorites.add(mContext.getString(R.string.assignments));
            favorites.add(mContext.getString(R.string.exam));
            favorites.add(mContext.getString(R.string.links));
            favorites.add(mContext.getString(R.string.events));
            favorites.add(mContext.getString(R.string.Authors));
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.favorite), mContext.getString(R.string.add_new_favorite), favorites));
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.timetable), mContext.getString(R.string.add_question), null));
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.books), mContext.getString(R.string.add_book), null));
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.forum), mContext.getString(R.string.add_question), null));
        } catch (Exception e) {
            Log.e(TAG, "getMenuDesk Exception : " + e.toString());
        }
        return menu;
    }

    public static ArrayList<ControllerTopMenuItem> getMenuReportCard(Context mContext) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.progress_report), null, null));
        } catch (Exception e) {
            Log.e(TAG, "getMenuReportCard Exception : " + e.toString());
        }
        return menu;
    }


    /**
     * For loading  teacher office main menu at top
     *
     * @param mContext
     * @return
     */

    public static ArrayList<ControllerTopMenuItem> getMenuTeacherOffice(Context mContext) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.str_classwall), null, null));

            ArrayList<String> notes_sub_sections = new ArrayList<>();
            notes_sub_sections.add(mContext.getString(R.string.notes));
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.str_notes), mContext.getString(R.string.add_notes), notes_sub_sections));

            ArrayList<String> quiz_sub_sections = new ArrayList<String>();
            quiz_sub_sections.add(mContext.getString(R.string.assignments));
//            quiz_sub_sections.add(mContext.getString(R.string.assignments));
//            quiz_sub_sections.add(mContext.getString(R.string.assignments));
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.str_quiz), mContext.getString(R.string.str_add), quiz_sub_sections));

            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.str_markscript), null, null));
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.str_results), null, null));
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.str_progressReport), null, null));
        } catch (Exception e) {
            Log.e(TAG, "getMenuClassRoom Exception : " + e.toString());
        }
        return menu;
    }


    public static ArrayList<ControllerTopMenuItem> getMenuTutorialGroup(Context mContext) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.str_tutorial), mContext.getString(R.string.str_past), null));
        } catch (Exception e) {
            Log.e(TAG, "tutorial top menu exception : " + e.toString());
        }
        return menu;
    }

}
