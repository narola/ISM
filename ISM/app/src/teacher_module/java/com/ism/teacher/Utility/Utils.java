package com.ism.teacher.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ism.utility.Utility;

import java.util.Calendar;

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


    private static DatePickerDialog datePickerDob;
    private static Calendar calDob;
    private static String strDob;
    private static long lngMaxDob;

    public static String showDatePickerDob(Context mContext, final EditText mEdittext) {
        try {
            if (calDob == null) {
                calDob = Calendar.getInstance();
                calDob.add(Calendar.YEAR, -3);
                lngMaxDob = calDob.getTimeInMillis();
            }
            datePickerDob = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calDob.set(Calendar.YEAR, year);
                    calDob.set(Calendar.MONTH, monthOfYear);
                    calDob.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    strDob = Utility.formatDateApi(calDob.getTime());
                    mEdittext.setText(Utility.formatDateDisplay(calDob.getTime()));
                }
            }, calDob.get(Calendar.YEAR), calDob.get(Calendar.MONTH), calDob.get(Calendar.DAY_OF_MONTH));
            datePickerDob.getDatePicker().setMaxDate(lngMaxDob);
            datePickerDob.show();
        } catch (Exception e) {
            Log.e("utils exception", "showDatePickerDob Exception : " + e.toString());
        }

        return strDob;
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


