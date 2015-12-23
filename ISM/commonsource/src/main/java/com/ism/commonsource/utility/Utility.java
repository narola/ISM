package com.ism.commonsource.utility;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by c161 on 09/11/15.
 */
public class Utility {

    private static final String TAG = Utility.class.getSimpleName();

    private static SimpleDateFormat formatPhp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getTimeDuration(String dateInPhpDefaultFormat) {
        try {
            return getTimeDuration(formatPhp.parse(dateInPhpDefaultFormat));
        } catch (ParseException e) {
            Log.e(TAG, "getTimeDuration ParseException : " + e.toString());
            return "";
        }
    }

    public static String getTimeDuration(Date date) {
        try {
            Date currentDate = Calendar.getInstance().getTime();
            int deltaSeconds = (int) ((currentDate.getTime() - date.getTime()) / 1000);
            double deltaMinutes = deltaSeconds / 60.0f;
            int minutes;
            if (deltaSeconds < 5) {
                return "Just now";
            } else if (deltaSeconds < 60) {
                return deltaSeconds + " seconds ago";
            } else if (deltaSeconds < 120) {
                return "A minute ago";
            } else if (deltaMinutes < 60) {
                return (int) deltaMinutes + " minutes ago";
            } else if (deltaMinutes < 120) {
                return "An hour ago";
            } else if (deltaMinutes < (24 * 60)) {
                minutes = (int) (deltaMinutes / 60);
                return minutes + " hours ago";
            } else if (deltaMinutes < (24 * 60 * 2)) {
                return "Yesterday";
            } else if (deltaMinutes < (24 * 60 * 7)) {
                minutes = (int) (deltaMinutes / (60 * 24));
                return minutes + " days ago";
            } else if (deltaMinutes < (24 * 60 * 14)) {
                return "Last week";
            } else if (deltaMinutes < (24 * 60 * 31)) {
                minutes = (int) (deltaMinutes / (60 * 24 * 7));
                return minutes + " weeks ago";
            } else if (deltaMinutes < (24 * 60 * 61)) {
                return "Last month";
            } else if (deltaMinutes < (24 * 60 * 365.25)) {
                minutes = (int) (deltaMinutes / (60 * 24 * 30));
                return minutes + " months ago";
            } else if (deltaMinutes < (24 * 60 * 731)) {
                return "Last year";
            }
            minutes = (int) (deltaMinutes / (60 * 24 * 365));
            return minutes + " years ago";

        } catch (Exception e) {
            Log.i(TAG, "getTimeDuration Exception : " + e.getLocalizedMessage());
            return null;
        }

    }

    //used for changed date format in 14th May 2015
    public static String DateFormat(String birthdate) {
        try {
            String strDob = birthdate;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            Date convertedDate = new Date();
            convertedDate = dateFormat.parse(birthdate);
            System.out.println(convertedDate);
            SimpleDateFormat format = new SimpleDateFormat("dd");
            String date = format.format(convertedDate);

            // Log.i(TAG, "Date :" + date);

            if (date.endsWith("1") && !date.endsWith("11"))
                format = new SimpleDateFormat("dd'st' MMMM yyyy");
            else if (date.endsWith("2") && !date.endsWith("12"))
                format = new SimpleDateFormat("dd'nd' MMMM yyyy");
            else if (date.endsWith("3") && !date.endsWith("13"))
                format = new SimpleDateFormat("dd'rd' MMMM yyyy");
            else
                format = new SimpleDateFormat("dd'th' MMMM yyyy");
            // Log.i(TAG, "Date Formated:" + format.format(convertedDate));
            return format.format(convertedDate);
        } catch (ParseException e) {
            Log.i(TAG, "dateFormat ParseException : " + e.getLocalizedMessage());
            return null;
        } catch (Exception e) {
            Log.i(TAG, "dateFormat Exceptions : " + e.getLocalizedMessage());
            return null;
        }
    }

    //used for changed date format in 14th May 2015
    public static String DateFormatDb(String birthdate) {
        try {
            String strDob = birthdate;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            Date convertedDate = new Date();
            convertedDate = dateFormat.parse(birthdate);
            System.out.println(convertedDate);
            SimpleDateFormat format = new SimpleDateFormat("dd");
            String date = format.format(convertedDate);

            // Log.i(TAG, "Date :" + date);

            if (date.endsWith("1") && !date.endsWith("11"))
                format = new SimpleDateFormat("dd'st' MMMM yyyy");
            else if (date.endsWith("2") && !date.endsWith("12"))
                format = new SimpleDateFormat("dd'nd' MMMM yyyy");
            else if (date.endsWith("3") && !date.endsWith("13"))
                format = new SimpleDateFormat("dd'rd' MMMM yyyy");
            else
                format = new SimpleDateFormat("dd'th' MMMM yyyy");
             Log.i(TAG, "Date Formated:" + format.format(convertedDate));
            return format.format(convertedDate);
        } catch (ParseException e) {
            Log.i(TAG, "dateFormat ParseException : " + e.getLocalizedMessage());
            return null;
        } catch (Exception e) {
            Log.i(TAG, "dateFormat Exceptions : " + e.getLocalizedMessage());
            return null;
        }
    }

    /*
   * Arti Patel
   * return date
   * */
    public static Date getDateFormate(String date, String pattern) {
        SimpleDateFormat curFormater = new SimpleDateFormat(pattern);//"yyyy-MM-dd"
        try {
            Log.i(TAG, "getDateFormate : " + curFormater.parse(date));
            return curFormater.parse(date);
        } catch (ParseException e) {
            Log.i(TAG, "getDateFormate ParseException : " + e.getLocalizedMessage());
        }
        return null;
    }

}
