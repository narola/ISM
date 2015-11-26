package com.ism.teacher.Utility;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;
import com.ism.teacher.R;
import com.ism.teacher.activity.TeacherHostActivity;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

	public static void launchIntent(Context context, Class classToOpen) {
		Intent intent = new Intent(context, classToOpen);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}


	public static String getCharForNumber(int i) {
		return i > 0 && i < 27 ? String.valueOf((char) (i + 'A' - 1)) : null;
	}
	public static Spanned formatHtml(String string) {
		return Html.fromHtml(string);
	}

	public static String getDate() {
		SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		return curFormater.format(calendar.getTime());
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


	/**
	 * For showing calendar without year limitation
	 *
	 */

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
			//datePickerDob.getDatePicker().setMaxDate(lngMaxDob);
			datePickerDob.show();
		} catch (Exception e) {
			Log.e("utils exception", "showDatePickerDob Exception : " + e.toString());
		}

		return strDob;
	}


	/**
	 * Show generic Toast message
	 */

	/*These is the method to show toast in android
    * */
	public static void showToast(String message, Context mContext) {
		try {
			Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * For Internet connection
	 */

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


	public static String getFormattedDate(String pattern, String date){

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

			Date date1 = sdf.parse(date);
			sdf  = new SimpleDateFormat(pattern, Locale.ENGLISH);

			return sdf.format(date1);
		}
		catch (Exception e) {

			e.printStackTrace();
			return null;
		}
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

	public static void showProgressBar(TeacherHostActivity activity) {
		activity.startProgress();
	}

	public static void hideProgressBar(TeacherHostActivity activity) {
		activity.stopProgress();
	}



	public static SpannableString f;

	public static SpannableString getSpannableString(String spanString, Integer color) {

		f = new SpannableString(spanString);
		f.setSpan(new ForegroundColorSpan(color), 0,
				spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return f;

	}

}
