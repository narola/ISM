package com.ism.teacher.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by c166 on 23/10/15.
 */
public class Utils {


    /* these is the method to check for the internet connection*/
    public static boolean isInternetConnected(Context mContext) {

        try {
            ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    /*These is the method to show toast in android
    * */
    public static void showToast(String message, Context mContext) {
        try {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void hideKeyboard(Activity activity) {

        if (activity.getCurrentFocus() != null) {

            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void showAlertDialog(Activity activity, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//    private void showLogoutDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//
//        builder.setTitle("Logout");
//
//        // Setting Dialog Message
//        builder.setMessage("Are you sure you want to Logout?");
//
//
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                store_preferences.clear_data();
//                Intent loginOptionsIntent = new Intent(MainActivity.this, LoginOptionsActivity.class);
//                startActivity(loginOptionsIntent);
//                finish();
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                dialogInterface.dismiss();
//            }
//        });
//        builder.setCancelable(true);
//        logoutDialog = builder.create();
//
//        logoutDialog.show();
//    }


}


