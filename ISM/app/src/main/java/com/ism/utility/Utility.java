package com.ism.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.ism.R;

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

	/**
	 * Krunal Panchal
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
	 * Check if internet connection available.
	 * @param context
	 * @return
	 */
	public static boolean isOnline(Context context) {
		NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnectedOrConnecting();
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
	 * @return
	 */
	public static String formatDateApi(Date date) {
		return DATE_FORMAT_API.format(date);
	}

	/**
	 * Format date to display in app.
	 * @param date
	 * @return
	 */
	public static String formatDateDisplay(Date date) {
		return DATE_FORMAT_DISPLAY.format(date);
	}

	public static void launchIntent(Context context, Class classToOpen) {
		Intent intent = new Intent(context, classToOpen);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

}
