package com.ism.author.object;

import android.content.Context;

import com.ism.author.R;
import com.ism.author.Utility.Debug;

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
            Debug.e(TAG, "getMenuTrial Exception : " + e.toString());
        }
        return menu;
    }

    public static ArrayList<ControllerTopMenuItem> getMenuAssessment(Context mContext) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.strassessment), mContext.getString(R.string.strAddNew), null));
        } catch (Exception e) {
            Debug.e(TAG, "getMenuAssessment Exception : " + e.toString());
        }
        return menu;
    }

    public static ArrayList<ControllerTopMenuItem> getMenuHome(Context mContext) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(null, mContext.getString(R.string.strAddpost), null));

        } catch (Exception e) {
            Debug.e(TAG, "getMenuHome Exception : " + e.toString());
        }
        return menu;
    }

    public static ArrayList<ControllerTopMenuItem> getMenuTrialSubMenu(Context mContext) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem(mContext.getString(R.string.strTrial), null, null));

        } catch (Exception e) {
            Debug.e(TAG, "getMenuTrialSubMenu Exception : " + e.toString());
        }
        return menu;
    }
    public static ArrayList<ControllerTopMenuItem> getMenuMyDesk(Context mContext) {
        ArrayList<ControllerTopMenuItem> menu = null;
        try {
            menu = new ArrayList<ControllerTopMenuItem>();
            menu.add(new ControllerTopMenuItem((mContext.getString(R.string.strMyDesk)), null, null));

        } catch (Exception e) {
            Debug.e(TAG, "getMenuMyDesk Exception : " + e.toString());
        }
        return menu;
    }
}
