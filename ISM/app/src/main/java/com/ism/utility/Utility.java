package com.ism.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ism.R;
import com.ism.ws.model.Data;

import java.io.ByteArrayOutputStream;
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

	public static final SimpleDateFormat DATE_FORMAT_API = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
	public static final SimpleDateFormat DATE_FORMAT_DISPLAY = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

	/**
	 * Krunal Panchale
	 * Open keyboard programmatically.
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
	 * @param context
	 * @return
	 */
	public static String getDeviceTokenId(Context context) {
		return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
	}

	/**
	 * Krunal Panchal
	 * Check if internet connection available and network access.
	 * @param context
	 * @return returns whether connection to network can be made or not.
	 */
	public static boolean isConnected(Context context) {
		NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}

	/**
	 * Krunal Panchal
	 * Toast alert when user is offline.
	 * @param context
	 */
	public static void toastOffline(Context context) {
		Toast.makeText(context, R.string.msg_offline, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Krunal Panchal
	 * Convert bitmap to base64 string.
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
	 * @param context
	 * @param title
	 * @param message
	 */
	public static void alert(Context context, String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (title != null) {
			builder.setTitle(title);
		}
		if (message != null) {
			builder.setMessage(message);
		}
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).create().show();
	}

	/**
	 * Krunal Panchal
	 * Format date to pass in api.
	 * @param date
	 * @return String : formatted date to pass in api.
	 */
	public static String formatDateApi(Date date) {
		return DATE_FORMAT_API.format(date);
	}

	/**
	 * Format date to display in app.
	 * @param date
	 * @return String : formatted date for display in app.
	 */
	public static String formatDateDisplay(Date date) {
		return DATE_FORMAT_DISPLAY.format(date);
	}

	/**
	 * Arti
	 * Launch any activity
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
	 * @param arrListData
	 */
	public static void sortPostedOnAsc(ArrayList<Data> arrListData) {
		Collections.sort(arrListData, new Comparator<Data>() {
			@Override
			public int compare(Data lData, Data rData) {
				int compare = lData.getPostedOn().compareTo(rData.getPostedOn());
				return compare;
			}
		});
	}

	/**
	 * Krunal Panchal
	 * Sort ArrayList of Data by PostedBy date in descending order
	 * @param arrListData
	 */
	public static void sortPostedOnDesc(ArrayList<Data> arrListData) {
		Collections.sort(arrListData, new Comparator<Data>() {
			@Override
			public int compare(Data lData, Data rData) {
				int compare = rData.getPostedOn().compareTo(lData.getPostedOn());
				return compare;
			}
		});
	}

	/**
	 * Krunal Panchal
	 * Sort ArrayList of Data by NoticeTitle date in ascending order
	 * @param arrListData
	 */
	public static void sortNoticeTitleAsc(ArrayList<Data> arrListData) {
		Collections.sort(arrListData, new Comparator<Data>() {
			@Override
			public int compare(Data lData, Data rData) {
				return lData.getNoticeTitle().compareToIgnoreCase(rData.getNoticeTitle());

			}
		});
	}

	/**
	 * Krunal Panchal
	 * Sort ArrayList of Data by NoticeTitle date in descending order
	 * @param arrListData
	 */
	public static void sortNoticeTitleDesc(ArrayList<Data> arrListData) {
		Collections.sort(arrListData, new Comparator<Data>() {
			@Override
			public int compare(Data lData, Data rData) {
				return rData.getNoticeTitle().compareToIgnoreCase(lData.getNoticeTitle());

			}
		});
	}

	/**
	 * These is the method to show toast in android
	 * @param message
	 * @param context
	 */
	public static void showToast(String message, Context context) {
		try {
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Log.e(TAG, "showToast Exception : " + e.toString());
		}
	}

}
