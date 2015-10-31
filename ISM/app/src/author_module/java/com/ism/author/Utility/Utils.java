package com.ism.author.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by c166 on 23/10/15.
 */
public class Utils {


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


}


