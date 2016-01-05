package com.ism.model;

import android.content.Context;
import android.util.Log;

import com.ism.R;

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

    public static ArrayList<ControllerTopMenuItem> getMenuClassroom(Context context) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(context.getString(R.string.classwall), null, null));
            menu.add(new ControllerTopMenuItem(context.getString(R.string.lesson_notes), context.getString(R.string.add_notes), null));
            menu.add(new ControllerTopMenuItem(context.getString(R.string.assignment), null, null));
            menu.add(new ControllerTopMenuItem(context.getString(R.string.exam), null, null));
        } catch (Exception e) {
            Log.e(TAG, "getMenuClassRoom Exception : " + e.toString());
        }
        return menu;
    }

    public static ArrayList<ControllerTopMenuItem> getMenuAssessment(Context context) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(context.getString(R.string.ism_mock), null, null));
            menu.add(new ControllerTopMenuItem(context.getString(R.string.wassce), null, null));
            menu.add(new ControllerTopMenuItem(context.getString(R.string.end_of_term), null, null));
        } catch (Exception e) {
            Log.e(TAG, "getMenuAssessment Exception : " + e.toString());
        }
        return menu;
    }

    public static ArrayList<ControllerTopMenuItem> getMenuDesk(Context context) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(context.getString(R.string.jotter), null, null));
            ArrayList<String> favorites = new ArrayList<String>();
            favorites.add(context.getString(R.string.favorite));
            favorites.add(context.getString(R.string.notes));

            favorites.add(context.getString(R.string.books));
            favorites.add(context.getString(R.string.assignments));
            favorites.add(context.getString(R.string.exams));
            favorites.add(context.getString(R.string.links));
            favorites.add(context.getString(R.string.events));
//            favorites.add(context.getString(R.string.Authors));
            menu.add(new ControllerTopMenuItem(context.getString(R.string.favorite), context.getString(R.string.add_new_favorite), favorites));
            menu.add(new ControllerTopMenuItem(context.getString(R.string.timetable), context.getString(R.string.strAskQuestion), null));
            menu.add(new ControllerTopMenuItem(context.getString(R.string.books), context.getString(R.string.add_book), null));
//            menu.add(new ControllerTopMenuItem(context.getString(R.string.forum), context.getString(R.string.add_question), null));
        } catch (Exception e) {
            Log.e(TAG, "getMenuDesk Exception : " + e.toString());
        }
        return menu;
    }

    public static ArrayList<ControllerTopMenuItem> getMenuMyAuthor(Context context) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(context.getString(R.string.strMyAuthors), null, null));

        } catch (Exception e) {
            Log.e(TAG, "getMenuMyAuthor Exception : " + e.toString());
        }
        return menu;
    }

    public static ArrayList<ControllerTopMenuItem> getMenuMyAuthorDesk(Context context) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(context.getString(R.string.strAuthorsDesk), null, null));

        } catch (Exception e) {
            Log.e(TAG, "getMenuMyAuthorDesk Exception : " + e.toString());
        }
        return menu;
    }


    public static ArrayList<ControllerTopMenuItem> getMenuAuthorOffice(Context context) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(context.getString(R.string.gotrending), context.getString(R.string.stradd), null));
            ArrayList<String> setquiz = new ArrayList<String>();
            setquiz.add(context.getString(R.string.setquiz));
            setquiz.add(context.getString(R.string.settrial));
            menu.add(new ControllerTopMenuItem(context.getString(R.string.setquiz), context.getString(R.string.straddnew), setquiz));
            menu.add(new ControllerTopMenuItem(context.getString(R.string.progress_report), null, null));

        } catch (Exception e) {
            Log.e(TAG, "getMenuAuthorOffice Exception : " + e.toString());
        }
        return menu;

    }
}
