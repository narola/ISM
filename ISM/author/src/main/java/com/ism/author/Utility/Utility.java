package com.ism.author.utility;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ism.author.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by c161 on 12/10/15.
 */
public class Utility {

    private static final String TAG = Utility.class.getSimpleName();


    private static AlertDialog dialogOffline;
    private static AlertDialog dialogServerAlert;

    public static final SimpleDateFormat DATE_FORMAT_MY_SQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_API = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_DISPLAY = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_DDMMMYY = new SimpleDateFormat("dd MMM yy", Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_MMMDDYY_HHMMA = new SimpleDateFormat("MMM dd, yy  HH : mm aa", Locale.getDefault()); // Nov 25, 2015  7:10pm
    public static final SimpleDateFormat DATE_FORMAT_MMMDDYYYY = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_REALM = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());//   Mon Dec 21 14:21:27 EST 2015


    private static StringBuilder mFormatBuilder = new StringBuilder();
    private static Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    private static InputMethodManager inputMethod;


    /**
     * Krunal Panchal
     * Open keyboard programmatically.
     *
     * @param view
     * @param context
     */
    public static void showSoftKeyboard(View view, Context context) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * Krunal Panchal
     * Get deviceId
     *
     * @param context
     * @return
     */
    public static String getDeviceTokenId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * Krunal Panchal
     * Check if internet connection available.
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    /**
     * Krunal Panchal
     * Convert bitmap to base64 string.
     *
     * @param bitmap
     * @return
     */

    /**
     * Hide Keyboard
     *
     * @param mContext
     */
    public static void hideKeyboard(Context mContext) {

        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(((Activity) mContext).getWindow()
                .getCurrentFocus().getWindowToken(), 0);
    }

    public static String getBase64ForImage(Bitmap bitmap) {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteOutputStream);
        byte[] byteArrayImage = byteOutputStream.toByteArray();
        return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
    }


    /**
     * Krunal Panchal
     * Format date to pass in api.
     *
     * @param date
     * @return String : formatted date to pass in api.
     */
    public static String formatDateApi(Date date) {
        return DATE_FORMAT_API.format(date);
    }

    /**
     * Format date to display in app.
     *
     * @param date
     * @return String : formatted date for display in app.
     */
    public static String formatDateDisplay(Date date) {
        return DATE_FORMAT_DISPLAY.format(date);
    }

    public static void launchIntent(Context context, Class classToOpen) {
        Intent intent = new Intent(context, classToOpen);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static String getFormattedDate(String pattern, String date) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date1 = sdf.parse(date);
            sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
            return sdf.format(date1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SpannableString f;

    public static SpannableString getSpannableString(String spanString, Integer color) {
        f = new SpannableString(spanString);
        f.setSpan(new ForegroundColorSpan(color), 0,
                spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return f;

    }


    public static boolean containsString(String original, String tobeChecked, boolean caseSensitive) {
        if (caseSensitive) {
            return original.contains(tobeChecked);
        } else {
            return original.toLowerCase().contains(tobeChecked.toLowerCase());
        }

    }

    public static String getString(int stringId, Context mContext) {
        return mContext.getResources().getString(stringId);

    }

    /**
     * Arti Patel
     * initialization of Imageloader
     *
     * @param failed
     * @param placeholder
     */
    public static DisplayImageOptions getDisplayImageOption(int failed, int placeholder) {
        try {
            return new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .showImageOnLoading(placeholder)
                    .showImageForEmptyUri(placeholder)
                    .showImageOnFail(failed)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        } catch (Exception e) {
            Log.e(TAG, "intiImageLoader Exception : " + e.toString());
            return null;
        }


    }

    /**
     * Arti Patel
     * hide keyboard
     *
     * @param context
     * @param view
     */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethod = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Krunal Panchal
     * Toast alert when user is offline.
     *
     * @param context
     */
    public static void alertOffline(Context context) {
        if (dialogOffline == null || !dialogOffline.isShowing()) {
            dialogOffline = alert(context, context.getString(R.string.connectivity_problem), context.getString(R.string.msg_offline));
        }
    }

    /**
     * Krunal Panchal
     * Simple alert message dialog.
     *
     * @param context
     * @param title
     * @param message
     */
    public static AlertDialog alert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null) {
            builder.setTitle(title);
        }
        if (message != null) {
            builder.setMessage(message);
        }
        AlertDialog dialog = builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
        return dialog;
    }


    /**
     * Arti Patel
     * set animation on view
     *
     * @param view
     * @param fromX
     * @param toX
     * @param fromY
     * @param toY
     */
    public static void startSlideAnimation(final View view, int fromX, int toX, int fromY, int toY) {
        TranslateAnimation slideOutAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slideOutAnimation.setDuration(500);
        slideOutAnimation.setFillAfter(true);
        view.startAnimation(slideOutAnimation);
    }

    /**
     * Arti Patel
     * These is the method to show toast in android
     *
     * @param message
     * @param context
     */
    public static void showToast(Context context, String message) {
        try {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "showToast Exception : " + e.toString());
        }
    }

    /*
    * Arti Patel
    * */
    public static String getDateString() {
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        return curFormater.format(calendar.getTime());
    }


    /**
     * Krunal Panchal
     * Alert server can't connect
     *
     * @param context
     */
    public static void alertServerNotConnected(Context context) {
        if (dialogServerAlert == null || !dialogServerAlert.isShowing()) {
            dialogServerAlert = alert(context, context.getString(R.string.connectivity_problem), context.getString(R.string.msg_server_connection));
        }
    }

    /**
     * Krunal Panchal
     * Format date to pass in api with MySql format.
     *
     * @param date
     * @return
     */
    public static String formatDateMySql(Date date) {
        return DATE_FORMAT_MY_SQL.format(date);
    }

    /**
     * get the file path from the URI
     *
     * @param contentURI
     * @param mContext
     * @return
     */

    public static String getRealPathFromURI(Uri contentURI, Context mContext) {
        String result;
        Cursor cursor = mContext.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    /**
     * @param {string} covert it to html format
     * @return
     */
    public static Spanned formatHtml(String string) {
        return Html.fromHtml(string);
    }

    /**
     * Format number to alphabetical index.
     *
     * @param i
     * @return
     */

    public static String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char) (i + 'A' - 1)) : null;
    }


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

    public static String formatDate(DateFormat dateFormat, String dateText) {
        String newDate = "";
        try {
            Date date = dateFormat.parse(dateText);
            newDate = DATE_FORMAT_API.format(date);

        } catch (Exception e) {

            Log.e("Exception", "Date exception");
        }
        return newDate;

    }


    public static String getDateFromRealm(String dateText) {
        String newDate = "";
        try {
            Date date = DATE_FORMAT_REALM.parse(dateText);
            newDate = DATE_FORMAT_API.format(date);

        } catch (Exception e) {
            Log.e("Exception", "Date exception");
        }
        return newDate;

    }


    private static DatePickerDialog datePickerDob;
    private static Calendar calDob;
    private static String strDob;
    private static long lngMaxDob;

    public static String showDatePickerDob(Context mContext, final EditText mEdittext) {
        try {
            if (calDob == null) {
                calDob = Calendar.getInstance();
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
            datePickerDob.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDob.show();
        } catch (Exception e) {
            Log.e(TAG, "showDatePickerDob Exception : " + e.toString());
        }

        return strDob;
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

    public static Date getRealmDateFormat(String date) {

//        Debug.e(TAG, "The Date Is::" + date);

        if (date != null && !date.equals("")) {

            Date realmDateFormat = null;
            try {
//                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                realmDateFormat = format.parse(date);
            } catch (Exception e) {
                Log.e("Realm", "Date exception");
            }
//            Log.e(TAG, "The realmDate format is::" + realmDateFormat);
            return realmDateFormat;
        } else {
            return null;
        }
    }


}
