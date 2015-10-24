package com.ism.utility;

import android.content.Context;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by c161 on 12/10/15.
 */
public class Utility {

	public static void showSoftKeyboard(View view, Context context) {
		if (view.requestFocus()) {
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
		}
	}

	public static String getDeviceTokenId(Context context) {
		return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
	}

}
