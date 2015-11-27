package com.ism.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.ws.model.Notice;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Created by c161 on 12/10/15.
 */
public class Utility {

    private static final String TAG = Utility.class.getSimpleName();

    private static AlertDialog dialogOffline;
    private static AlertDialog dialogServerAlert;

    public static final SimpleDateFormat DATE_FORMAT_PHP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_API = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_DISPLAY = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_DMY = new SimpleDateFormat("dd MMM yy", Locale.getDefault());
    private static InputMethodManager inputMethod;

    /**
     * Krunal Panchale
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
     * Check if internet connection available and network access.
     *
     * @param context
     * @return returns whether connection to network can be made or not.
     */
    public static boolean isConnected(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * Krunal Panchal
     * Check if server connection possible
     *
     * @return
     */
    public static boolean isServerConnected() {
//      Check if we can get access from the network.
        URL url = null;
        try {
            url = new URL(WebConstants.HOST_147);
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(2000); // Timeout 2 seconds.
            urlc.connect();
            return urlc.getResponseCode() == 200; //Successful response.
        } catch (MalformedURLException e) {
            Log.e(TAG, "isServerConnected MalformedURLException : " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "isServerConnected IOException : " + e.toString());
        }
        return false;
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
     * Convert bitmap to base64 string.
     *
     * @param bitmap
     * @return
     */
    public static String getBase64ForImage(Bitmap bitmap) {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteOutputStream);
        byte[] byteArrayImage = byteOutputStream.toByteArray();
        return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppDialogTheme);
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

    /**
     * Krunal Panchal
     *
     * @param strDate
     * @return String : fromatted date. eg. : 6 jul 15
     */
    public static String formatPHPDateToDMY(String strDate) {
        try {
            return DATE_FORMAT_DMY.format(DATE_FORMAT_PHP.parse(strDate));
        } catch (ParseException e) {
            Log.e(TAG, "formatPHPDateToDMY Exception : " + e.toString());
            return null;
        }
    }

    /**
     * Arti
     * Launch any activity
     *
     * @param context
     * @param classToOpen
     */
    public static void launchActivity(Context context, Class classToOpen) {
        Intent intent = new Intent(context, classToOpen);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Krunal Panchal
     * Sort ArrayList of Data by PostedBy date in ascending order
     *
     * @param arrListData
     */
    public static void sortPostedOnAsc(ArrayList<Notice> arrListData) {
        Collections.sort(arrListData, new Comparator<Notice>() {
            @Override
            public int compare(Notice lData, Notice rData) {
                int compare = lData.getPostedOn().compareTo(rData.getPostedOn());
                return compare;
            }
        });
    }

    /**
     * Krunal Panchal
     * Sort ArrayList of Data by PostedBy date in descending order
     *
     * @param arrListData
     */
    public static void sortPostedOnDesc(ArrayList<Notice> arrListData) {
        Collections.sort(arrListData, new Comparator<Notice>() {
            @Override
            public int compare(Notice lData, Notice rData) {
                int compare = rData.getPostedOn().compareTo(lData.getPostedOn());
                return compare;
            }
        });
    }

    /**
     * Krunal Panchal
     * Sort ArrayList of Data by NoticeTitle date in ascending order
     *
     * @param arrListData
     */
    public static void sortNoticeTitleAsc(ArrayList<Notice> arrListData) {
        Collections.sort(arrListData, new Comparator<Notice>() {
            @Override
            public int compare(Notice lData, Notice rData) {
                return lData.getNoticeTitle().compareToIgnoreCase(rData.getNoticeTitle());

            }
        });
    }

    /**
     * Krunal Panchal
     * Sort ArrayList of Data by NoticeTitle date in descending order
     *
     * @param arrListData
     */
    public static void sortNoticeTitleDesc(ArrayList<Notice> arrListData) {
        Collections.sort(arrListData, new Comparator<Notice>() {
            @Override
            public int compare(Notice lData, Notice rData) {
                return rData.getNoticeTitle().compareToIgnoreCase(lData.getNoticeTitle());

            }
        });
    }

    /**
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
                    .showImageOnLoading(R.drawable.ic_classmates_active)
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
        inputMethod = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
     * get Image path from uri
     * @param uri
     * @param context
     * @return
     */
    public static String getImagePath(Uri uri, Context context) {
        String wholeID = DocumentsContract.getDocumentId(uri);
        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);
        String filePath = "";

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

}
