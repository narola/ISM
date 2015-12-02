package com.ism.author.Utility;


import android.app.Activity;
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
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ism.author.R;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by c161 on 12/10/15.
 */
public class Utility {

    public static final SimpleDateFormat DATE_FORMAT_API = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_DISPLAY = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private static final String TAG = Utility.class.getSimpleName();
    private static AlertDialog dialogOffline;

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
     * Toast alert when user is offline.
     *
     * @param context
     */
    public static void toastOffline(Context context) {
        Toast.makeText(context, R.string.msg_offline, Toast.LENGTH_SHORT).show();
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


    public static ProgressGenerator progressGenerator = new ProgressGenerator();

    public static void showSpinnerProgress(ActionProcessButton actionProcessButton) {
        actionProcessButton.setProgress(1);
        actionProcessButton.setVisibility(View.VISIBLE);
        progressGenerator.start(actionProcessButton);

    }

    public static void hideSpinnerProgress(ActionProcessButton actionProgressButton) {
        actionProgressButton.setProgress(100);
        actionProgressButton.setVisibility(View.INVISIBLE);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppTheme);
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
     * get Image path from uri
     *
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


}
