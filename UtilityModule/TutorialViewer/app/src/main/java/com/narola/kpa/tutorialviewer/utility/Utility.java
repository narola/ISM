package com.narola.kpa.tutorialviewer.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.narola.kpa.tutorialviewer.object.Global;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Krunal Panchal on 01/10/15.
 */
public class Utility {

    private static final String TAG = Utility.class.getSimpleName();

    private static StringBuilder mFormatBuilder = new StringBuilder();
    private static Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy  hh:mm");
    private static Calendar mCalendar = Calendar.getInstance();

    public static String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours   = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    public static String formatDateTime(long timeMilliseconds) {
        mCalendar.setTimeInMillis(timeMilliseconds);
        return simpleDateFormat.format(mCalendar.getTime());
    }

    public static void showSoftKeyboard(View view, Context context) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static void saveBitmapAsJPG(Bitmap bitmap, String fileName) {
        File imageDirectory = new File(Global.imagePath);
        imageDirectory.mkdirs();
        File file = new File(imageDirectory, fileName);
        if (file.exists()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e(TAG, "saveBitmapAsJPG Exception : " + e.toString());
        }
    }

}
