package com.ism.author.helper;

import android.content.Context;
import android.util.Log;

import com.ism.author.R;

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


    //trial main
    public static ArrayList<ControllerTopMenuItem> getMenuTrial(Context mContext) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
//            ArrayList<String> trial = new ArrayList<String>();
//            trial.add(mContext.getString(R.string.strTrial));
//            trial.add(mContext.getString(R.string.notes));
//            trial.add(mContext.getString(R.string.books));
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.strTrial), mContext.getString(R.string.strAddNew), null));
        } catch (Exception e) {
            Log.e(TAG, "getMenuTrial Exception : " + e.toString());
        }
        return menu;
    }

    public static ArrayList<ControllerTopMenuItem> getMenuHome(Context mContext) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(null, mContext.getString(R.string.strAddpost), null));

        } catch (Exception e) {
            Log.e(TAG, "getMenuHome Exception : " + e.toString());
        }
        return menu;
    }

    public static ArrayList<ControllerTopMenuItem> getMenuTrialSubMenu(Context mContext) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.strTrial),null, null));

        } catch (Exception e) {
            Log.e(TAG, "getMenuTrialSubMenu Exception : " + e.toString());
        }
        return menu;
    }


//    public static ArrayList<ControllerTopMenuItem> getMenuDesk(Context mContext) {
//        ArrayList<ControllerTopMenuItem> menu = null;
//        try {
//            menu = new ArrayList<ControllerTopMenuItem>();
//            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.jotter), mContext.getString(R.string.add_notes), null));
//            ArrayList<String> favorites = new ArrayList<String>();
//            favorites.add(mContext.getString(R.string.notes));
//            favorites.add(mContext.getString(R.string.books));
//            favorites.add(mContext.getString(R.string.assignments));
//            favorites.add(mContext.getString(R.string.exam));
//            favorites.add(mContext.getString(R.string.links));
//            favorites.add(mContext.getString(R.string.events));
//            favorites.add(mContext.getString(R.string.Authors));
//            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.favorite), mContext.getString(R.string.add_new_favorite), favorites));
//            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.timetable), mContext.getString(R.string.add_question), null));
//            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.books), mContext.getString(R.string.add_book), null));
//            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.forum), mContext.getString(R.string.add_question), null));
//        } catch (Exception e) {
//            Log.e(TAG, "getMenuDesk Exception : " + e.toString());
//        }
//        return menu;
//    }
//
//    public static ArrayList<ControllerTopMenuItem> getMenuReportCard(Context mContext) {
//        ArrayList<ControllerTopMenuItem> menu = null;
//        try {
//            menu = new ArrayList<ControllerTopMenuItem>();
//            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.progress_report), null, null));
//        } catch (Exception e) {
//            Log.e(TAG, "getMenuReportCard Exception : " + e.toString());
//        }
//        return menu;
//    }
//
//
//    public static ArrayList<ControllerTopMenuItem> getMenuAuthorOffice(Context mContext) {
//        ArrayList<ControllerTopMenuItem> menu = null;
//        try {
//            menu = new ArrayList<ControllerTopMenuItem>();
//            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.gotrending), mContext.getString(R.string.stradd), null));
//            ArrayList<String> setquiz = new ArrayList<String>();
//            setquiz.add(mContext.getString(R.string.setquiz));
//            setquiz.add(mContext.getString(R.string.settrial));
//            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.setquiz), mContext.getString(R.string.straddnew), setquiz));
//            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.progress_report), null, null));
//
//        } catch (Exception e) {
//            Log.e(TAG, "getMenuAuthorOffice Exception : " + e.toString());
//        }
//        return menu;
//
//    }

}
