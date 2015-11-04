package com.ism.author.Utility;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.Patterns;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.ism.author.R;


/**
 * Created by c161 on 22/10/2015.
 */
public class InputValidator {

	private static final String tag = InputValidator.class.getSimpleName();

	private Context context;

	public InputValidator(Context context) {
		this.context = context;
	}

	public boolean validateStringPresence(EditText editText) {
		if (editText.getText() == null
				|| editText.getText().toString().trim().length() == 0) {
			setError(editText, context.getString(R.string.required));
			return false;
		} else {
			editText.setError(null);
			return true;
		}
	}

	public boolean validateStringPresence(AutoCompleteTextView autoCompleteTextView) {
		if (autoCompleteTextView.getText() == null
				|| autoCompleteTextView.getText().toString().trim().length() == 0) {
			setError(autoCompleteTextView, context.getString(R.string.required));
			return false;
		} else {
			autoCompleteTextView.setError(null);
			return true;
		}
	}

	public boolean validatePasswordLength(EditText editText) {
		if (editText.getText().toString().trim().length() < 8) {
			setError(editText, context.getString(R.string.error_password_length));
			return false;
		} else {
			editText.setError(null);
			return true;
		}
	}

	public boolean validatePasswordLength(EditText editText, int passwordLengthMin, String errorMessage) {
		if (editText.getText().toString().trim().length() < passwordLengthMin) {
			setError(editText, errorMessage);
			return false;
		} else {
			editText.setError(null);
			return true;
		}
	}

	public boolean validateConfirmPasswordMatch(EditText editTextPassword, EditText editTextConfirmPassword) {
		if (!editTextConfirmPassword.getText().toString().trim().equals(editTextPassword.getText().toString().trim())) {
			setError(editTextConfirmPassword, context.getString(R.string.error_confirm_password_mismatch));
			return false;
		} else {
			editTextConfirmPassword.setError(null);
			return true;
		}
	}

	public boolean validateNewPassword(EditText editTextOldPassword, EditText editTextNewPassword) {
		if (editTextOldPassword.getText().toString().trim().equals(editTextNewPassword.getText().toString().trim())) {
			setError(editTextNewPassword, context.getString(R.string.error_same_old_new_password));
			return false;
		} else {
			editTextNewPassword.setError(null);
			return true;
		}
	}

	public boolean validateEmail(EditText editText) {
		if (!Patterns.EMAIL_ADDRESS.matcher(
				editText.getText()).matches()) {
			setError(editText, context.getString(R.string.error_invalid_email));
			return false;
		} else {
			editText.setError(null);
			return true;
		}
	}

	public boolean validateUrl(EditText editText) {
		if (!Patterns.WEB_URL.matcher(
				editText.getText()).matches()) {
			setError(editText, context.getString(R.string.invalid_url));
			return false;
		} else {
			editText.setError(null);
			return true;
		}
	}


	public boolean validatePhoneNumberLength(EditText editText)
	{
		if (editText.getText().toString().trim().length() < 10) {
			setError(editText,"Minimum 10 characters are required");
			return false;
		} else {
			editText.setError(null);
			return true;
		}
	}

	/**
	 *
	 *  ********  General Validation methods  *********
	 *
	 */

	/**
	 * All default validations checks for email
	 * @param editText
	 * @return
	 */
	public boolean validateAllConstraintsEmail(EditText editText) {
		return validateStringPresence(editText) && validateEmail(editText);
	}

	/**
	 * All default validation checks for password and confirmPassword
	 * @param editTextPassword
	 * @param editTextConfirmPassword
	 * @return
	 */
	public boolean validateAllConstraintsPassword(EditText editTextPassword, EditText editTextConfirmPassword) {
		return (validateStringPresence(editTextPassword) && validatePasswordLength(editTextPassword))
				& (validateStringPresence(editTextConfirmPassword) && validateConfirmPasswordMatch(editTextPassword, editTextConfirmPassword));
	}

	/**
	 * All default validation checks for password and confirmPassword with custome password lenght and error message
	 * @param editTextPassword
	 * @param editTextConfirmPassword
	 * @param passwordLengthMin
	 * @param errorMessage
	 * @return
	 */
	public boolean validateAllConstraintsPassword(EditText editTextPassword, EditText editTextConfirmPassword, int passwordLengthMin, String errorMessage) {
		return (validateStringPresence(editTextPassword) && validatePasswordLength(editTextPassword, passwordLengthMin, errorMessage))
				& (validateStringPresence(editTextConfirmPassword) && validateConfirmPasswordMatch(editTextPassword, editTextConfirmPassword));
	}

	// For setting error message using custom string on any EditText.
	public static void setError(EditText editText, String errorMessage) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			editText.setError(errorMessage);
		} else{
			// There is no theme available for ErrorMessageWindow for Light theme for Gingerbread in AppCompat.
			// To display message properly, need to change color from white to black. No inbuilt functionality available for this.
			editText.setError(Html.fromHtml("<font color='black'>" + errorMessage + "</font>"));
		}
	}

	// For setting error message using custom string on any AutoCompleteTextView.
	public static void setError(AutoCompleteTextView autoCompleteTextView, String errorMessage) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			autoCompleteTextView.setError(errorMessage);
		} else{
			// There is no theme available for ErrorMessageWindow for Light theme for Gingerbread in AppCompat.
			// To display message properly, need to change color from white to black. No inbuilt functionality available for this.
			autoCompleteTextView.setError(Html.fromHtml("<font color='black'>" + errorMessage + "</font>"));
		}
	}

}