package com.ism.author.Utility;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ism.author.fragment.TrialActivityFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by c166 on 23/10/15.
 */
public class Utils {


    private static final String TAG = TrialActivityFragment.class.getSimpleName();


    /* these is the method to check for the internet connection*/
    public static boolean isInternetConnected(Context mContext) {

        try {
            ConnectivityManager connect = null;
            connect = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connect != null) {
                NetworkInfo resultMobile = connect
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                NetworkInfo resultWifi = connect
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if ((resultMobile != null && resultMobile
                        .isConnectedOrConnecting())
                        || (resultWifi != null && resultWifi
                        .isConnectedOrConnecting())) {
                    return true;
                } else {
                    return false;
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
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getDate() {
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        return curFormater.format(calendar.getTime());
    }

    private static DatePickerDialog datePickerDob;
    private static Calendar calDob;
    private static String strDob;
    private static long lngMaxDob;

    public static String showDatePickerDob(Context mContext, final EditText mEdittext) {
        try {
            if (calDob == null) {
                calDob = Calendar.getInstance();
//                calDob.add(Calendar.YEAR, 100);
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
//            datePickerDob.getDatePicker().setMaxDate(lngMaxDob);
            datePickerDob.show();
        } catch (Exception e) {
            Log.e(TAG, "showDatePickerDob Exception : " + e.toString());
        }

        return strDob;
    }


    public static final SimpleDateFormat DATE_FORMAT_API = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public static String getDateInApiFormat(String dateText) {
        String newDate = "";
        try {
            Date date = DATE_FORMAT_API.parse(dateText);
            newDate = DATE_FORMAT_API.format(date);

        } catch (Exception e) {

            Log.e("Exception", "Date exception");
        }
        return newDate;

    }

    public static String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char) (i + 'A' - 1)) : null;
    }


}


