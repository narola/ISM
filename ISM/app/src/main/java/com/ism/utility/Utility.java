package com.ism.utility;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Krunal Panchal on 12/10/15.
 */
public class Utility {

	public static void showSoftKeyboard(View view, Context context) {
		if (view.requestFocus()) {
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
		}
	}

}
