package com.ism.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.R;
import com.ism.model.Data;
import com.ism.model.GetCitiesRequest;
import com.ism.model.GetStatesRequest;
import com.ism.model.RegisterRequest;
import com.ism.model.ResponseObject;
import com.ism.object.MyTypeFace;
import com.ism.utility.InputValidator;
import com.ism.utility.Utility;
import com.ism.ws.WebserviceWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c162 on 07/10/15.
 */
public class ProfileInformationActivity extends Activity implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = ProfileInformationActivity.class.getSimpleName();

	private Spinner spGender, spCity, spCountry,  spState;
	private TextView txtSchoolGender, txtAcademicYear, txtNameSchool, txtDistrictOfSchool, txtProgramCourse, txtClass;
	private EditText etAge, etUserName, etNewPwd, etHomeAddress, etFirstName, etLastName,
			etEmailAddress, etDob, etCurrentPwd, etContactNo,  etConfirmPwd;
	private ImageView imgDp;

	private MyTypeFace myTypeFace;
	private InputValidator inputValidator;
	private List<String> arrListGender;
	private List<String> arrListDefalt;
	private ArrayList<Data> arrListCountries;
	private ArrayList<Data> arrListStates;
	private ArrayList<Data> arrListCities;

	private String strUserId;
	private String strSchoolId;
	private String strSchoolName;
	private String strSchoolDistrict;
	private String strClassId;
	private String strClassName;
	private String strCourseId;
	private String strCourseName;
	private String strAcademicYear;
	private String strRoleId;
	private String strDpBase64;
	private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_profile_info);

        intitGlobal();

    }

    private void intitGlobal() {
        spGender = (Spinner) findViewById(R.id.sp_gender);
	    spCity = (Spinner) findViewById(R.id.sp_city);
	    spCountry = (Spinner) findViewById(R.id.sp_country);
	    spState = (Spinner) findViewById(R.id.sp_state);
	    etAge = (EditText) findViewById(R.id.et_age);
	    etContactNo = (EditText) findViewById(R.id.et_cno);
	    etConfirmPwd = (EditText) findViewById(R.id.et_confirmpwd);
	    etCurrentPwd = (EditText) findViewById(R.id.et_currentpwd);
	    etDob = (EditText) findViewById(R.id.et_dob);
	    etEmailAddress = (EditText) findViewById(R.id.et_emailAdd);
	    etFirstName = (EditText) findViewById(R.id.et_firstname);
	    etLastName = (EditText) findViewById(R.id.et_lastname);
	    etHomeAddress = (EditText) findViewById(R.id.et_homeAdd);
	    etNewPwd = (EditText) findViewById(R.id.et_newpwd);
	    etUserName = (EditText) findViewById(R.id.et_username);
	    imgDp = (ImageView) findViewById(R.id.img_dp_post_creator);
	    txtSchoolGender = (TextView) findViewById(R.id.txt_schoolgender);
	    txtAcademicYear = (TextView) findViewById(R.id.txt_academicyear);
	    txtNameSchool = (TextView) findViewById(R.id.txt_nameofschool);
	    txtDistrictOfSchool = (TextView) findViewById(R.id.txt_districtofschool);
	    txtProgramCourse = (TextView) findViewById(R.id.txt_programcourse);
	    txtClass = (TextView) findViewById(R.id.txt_class);

	    myTypeFace = new MyTypeFace(this);
	    ((TextView) findViewById(R.id.txt_youare_)).setTypeface(myTypeFace.getRalewayThin());
	    ((TextView) findViewById(R.id.txt_ifits_)).setTypeface(myTypeFace.getRalewayThin());
	    ((TextView) findViewById(R.id.txt_clickhere)).setTypeface(myTypeFace.getRalewayThin());
	    ((TextView) findViewById(R.id.txt_uploadpic)).setTypeface(myTypeFace.getRalewayRegular());
        etAge.setTypeface(myTypeFace.getRalewayRegular());
        etContactNo.setTypeface(myTypeFace.getRalewayRegular());
        etConfirmPwd.setTypeface(myTypeFace.getRalewayRegular());
        etCurrentPwd.setTypeface(myTypeFace.getRalewayRegular());
        etDob.setTypeface(myTypeFace.getRalewayRegular());
	    etEmailAddress.setTypeface(myTypeFace.getRalewayRegular());
	    etFirstName.setTypeface(myTypeFace.getRalewayRegular());
	    etLastName.setTypeface(myTypeFace.getRalewayRegular());
	    etHomeAddress.setTypeface(myTypeFace.getRalewayRegular());
	    etNewPwd.setTypeface(myTypeFace.getRalewayRegular());
	    etUserName.setTypeface(myTypeFace.getRalewayRegular());
	    txtNameSchool.setTypeface(myTypeFace.getRalewayRegular());
	    txtDistrictOfSchool.setTypeface(myTypeFace.getRalewayRegular());
	    txtSchoolGender.setTypeface(myTypeFace.getRalewayRegular());
	    txtAcademicYear.setTypeface(myTypeFace.getRalewayRegular());
	    txtProgramCourse.setTypeface(myTypeFace.getRalewayRegular());
	    txtClass.setTypeface(myTypeFace.getRalewayRegular());

	    inputValidator = new InputValidator(ProfileInformationActivity.this);

	    if (getIntent().getExtras() != null) {
		    Bundle extras = getIntent().getExtras();
		    strUserId = extras.getString(LoginActivity.ID);
		    strSchoolId = extras.getString(LoginActivity.SCHOOL_ID);
		    strSchoolName = extras.getString(LoginActivity.SCHOOL_NAME);
		    strSchoolDistrict = extras.getString(LoginActivity.SCHOOL_DISTRICT);
		    strClassId = extras.getString(LoginActivity.CLASS_ID);
		    strClassName = extras.getString(LoginActivity.CLASS_NAME);
		    strCourseId = extras.getString(LoginActivity.COURSE_ID);
		    strCourseName = extras.getString(LoginActivity.COURSE_NAME);
		    strAcademicYear = extras.getString(LoginActivity.ACADEMIC_YEAR);
		    strRoleId = extras.getString(LoginActivity.ROLE_ID);

		    txtNameSchool.setText(strSchoolName);
		    txtClass.setText(strClassName);
		    txtAcademicYear.setText(strAcademicYear);
		    txtDistrictOfSchool.setText(strSchoolDistrict);
		    txtProgramCourse.setText(strCourseName);
	    }

	    callApiGetCountries();

	    spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			    if (arrListCountries != null && position > 0) {
				    callApiGetStates(Integer.parseInt(arrListCountries.get(position - 1).getId()));
			    } else {
				    setUpSpinner(spState, arrListDefalt);
			    }
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parent) {

		    }
	    });

	    spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			    if (arrListStates != null && position > 0) {
				    callApiGetCities(Integer.parseInt(arrListStates.get(position - 1).getId()));
			    } else {
				    setUpSpinner(spCity, arrListDefalt);
			    }
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parent) {

		    }
	    });

	    arrListGender = new ArrayList<String>();
	    arrListGender.add("Gender");
	    arrListGender.add("Male");
	    arrListGender.add("Female");
        setUpSpinner(spGender, arrListGender);

	    arrListDefalt = new ArrayList<String>();
	    arrListDefalt.add(getString(R.string.select));
	    setUpSpinner(spCountry, arrListDefalt);
	    setUpSpinner(spState, arrListDefalt);
	    setUpSpinner(spCity, arrListDefalt);
    }

    private void setUpSpinner(Spinner spinner, List<String> strArr) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner, strArr) {

            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTypeface(myTypeFace.getRalewayRegular());
                if (position == 0) {
                    textView.setTextColor(getResources().getColor(R.color.color_text_hint));
                    return textView;
                } else {
                    textView.setTextColor(getResources().getColor(R.color.color_black));
                    return textView;
                }
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
	            TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
	            textView.setTypeface(myTypeFace.getRalewayRegular());
	            textView.setCompoundDrawables(null, null, null, null);

	            if (position==0) {
		            textView.setTextColor(getResources().getColorStateList(R.color.color_white));
		            textView.setBackgroundColor(getResources().getColor(R.color.color_green));
		            textView.setEnabled(false);
		            textView.setOnClickListener(null);
	            } else {
		            textView.setTextColor(getResources().getColorStateList(R.color.color_dark_gray));
		            textView.setBackgroundResource(R.color.color_white);
	            }

                return textView;
            }
        };
        adapter.setDropDownViewResource(R.layout.row_spinner);
        spinner.setAdapter(adapter);
    }

	public void onClickSubmit(View view) {
		if (isInputsValid()) {
			callApiRegisterUser();
		}

		/*Intent intentWelcome = new Intent(ProfileInformationActivity.this, WelComeActivity.class);
		startActivity(intentWelcome);
		finish();*/
	}

	public void onClickUploadImage(View v) {
		openGallary();
	}

	private void callApiRegisterUser() {
		try {
			RegisterRequest registerRequest = new RegisterRequest();
			registerRequest.setFirstname(etFirstName.getText().toString().trim());
			registerRequest.setLastname(etLastName.getText().toString().trim());
			registerRequest.setEmailAddress(etEmailAddress.getText().toString().trim());
			registerRequest.setContactNumber(etContactNo.getText().toString().trim());
			registerRequest.setGender(arrListGender.get(spGender.getSelectedItemPosition()));
			registerRequest.setBirthdate(etDob.getText().toString().trim());
			registerRequest.setHomeAddress(etHomeAddress.getText().toString().trim());
			registerRequest.setCountryId(spCountry.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListCountries.get(spCountry.getSelectedItemPosition() - 1).getId()) : 0);
			registerRequest.setStateId(spState.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListStates.get(spState.getSelectedItemPosition() - 1).getId()) : 0);
			registerRequest.setCityId(spCity.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListCities.get(spCity.getSelectedItemPosition() - 1).getId()) : 0);
			registerRequest.setUsername(etUserName.getText().toString().trim());
			registerRequest.setPassword(etNewPwd.getText().toString().trim());
			registerRequest.setDeviceToken(Utility.getDeviceTokenId(ProfileInformationActivity.this));
			Log.e(TAG, "devide token : " + registerRequest.getDeviceToken());
			registerRequest.setSchoolId(Double.parseDouble(strSchoolId));
			registerRequest.setClassroomId(Double.parseDouble(strClassId));
			registerRequest.setCourseId(Double.parseDouble(strCourseId));
			registerRequest.setAcademicYear(strAcademicYear);
			registerRequest.setRoleId(Double.parseDouble(strRoleId));
			registerRequest.setDeviceType(getString(R.string.android));
			registerRequest.setProfileImageName("image_" + System.currentTimeMillis() + ".png");
//			registerRequest.setProfileImage("iVBORw0KGgoAAAANSUhEUgAAACwAAAAZCAYAAABKM8wfAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA2ZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo4NjY3QTk5MzkzNUVFNTExQTdEQ0NCQjMzODA0NDA3MyIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDoyQ0QwRjg5RjZDQzMxMUU1ODk0OERGMzI0M0FCMUJDMyIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDoyQ0QwRjg5RTZDQzMxMUU1ODk0OERGMzI0M0FCMUJDMyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ1M2IChXaW5kb3dzKSI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjVDMzk3MTlCQzE2Q0U1MTFCMDk0QTVDM0UyMTVDNDQ4IiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjg2NjdBOTkzOTM1RUU1MTFBN0RDQ0JCMzM4MDQ0MDczIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+7NygKgAAAqVJREFUeNrUl1uITVEYx/c+Zzc4bjORaHjAuEQGkYSE8sTkAeFlZhKF5FJCSh6EFF7IgxcMDyhRRCMkkchlPLjkwS2GMmmIcYn4ffU/deZ09nb2nr3P5Ktfa599zlrrv771X9/ex3NijMobDR7NQjgIe2Df22m13+Kcw41R6FrYCr1zvnoFm+AUwv90umCEWv8FsBsGB/z0JqxD9N1OE4zYyTR7YUqRXSzDDbAF4c0lE4xQy+QuWBRxzq/qH8nfbgih5ZYdWANdYrBjJH+7RR6oFbAN+jrxRyh/u/8QW6PyNNxJNizDR+Tv96EFI3S8DtRMp7TxBXbCHVgKw+T1EwUFI7SSZgfUxlWjOxBv4CJMRfDo7E1PQnvQbIQN0C3iBJ/gOXzWdXd5fiD0iTCe9ZsDH9tZArEVtI9gQMgBH8AluAZNkIYhEtcT2uADvAYrX+NgOsyGiUXs4DO4CsvgmCWUTLe4yrANtllPrXTAIE/gMJyEDMyFGbZtUB7Qz7J+D65om+1gzYc6mODTpx6BR9F2m+tJSsysfA9XyRb1ebX2nKpFk/y9HKo74E+zznE4pK1fBYuhLK9OW4bnab4zVq/9qoTZYz2s1AuNPVK3ayGZGA/WbzirZJjAC7KOxQu4bp8Rmr3npAqNwg/egR3CQRqwWlnIxFwJ0rLGLZtTos0uq2Gk5m4XXtBoDNJK00rGR5WolB2AJdl3DOZ1QgnOibJSqEXo5QK3v0cR7Bc/VCfb9JQyi/UCq+sVfpYLGZEE2xbZ6u/DQ3gML2WZoJemfqoC/dUOVa2uEl3D7qxX5FY10jSG3N5fNM2i0IJSWsAYGOEzTCaR/3RJBAuymj8251YNSTifcv6f8JyYDkWS78j79fhuiatKJBlWj5/KHqdpftr1XwEGANDhxPdASvKdAAAAAElFTkSuQmCC");
			registerRequest.setProfileImage(strDpBase64);

			new WebserviceWrapper(ProfileInformationActivity.this, registerRequest).new WebserviceCaller()
					.execute(WebserviceWrapper.REGISTER_USER);
		} catch (Exception e) {
			Log.e(TAG, "callApiRegisterUser Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiGetCountries() {
		try {
			new WebserviceWrapper(ProfileInformationActivity.this, null).new WebserviceCaller()
					.execute(WebserviceWrapper.GET_COUNTRIES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetCountries Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiGetStates(int countryId) {
		try {
			GetStatesRequest statesRequest = new GetStatesRequest();
			statesRequest.setCountryId(countryId);

			new WebserviceWrapper(ProfileInformationActivity.this, statesRequest).new WebserviceCaller()
					.execute(WebserviceWrapper.GET_STATES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetCountries Exception : " + e.getLocalizedMessage());
		}
	}

	private void callApiGetCities(int stateId) {
		try {
			GetCitiesRequest citiesRequest = new GetCitiesRequest();
			citiesRequest.setStateId(stateId);

			new WebserviceWrapper(ProfileInformationActivity.this, citiesRequest).new WebserviceCaller()
					.execute(WebserviceWrapper.GET_CITIES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetCountries Exception : " + e.getLocalizedMessage());
		}
	}

	private boolean isInputsValid() {
		return inputValidator.validateStringPresence(etFirstName) &
				inputValidator.validateStringPresence(etLastName) &
				inputValidator.validateStringPresence(etEmailAddress) &
				inputValidator.validateStringPresence(etContactNo) &
				inputValidator.validateStringPresence(etDob) &
				inputValidator.validateStringPresence(etAge) &
				inputValidator.validateStringPresence(etHomeAddress) &
				(inputValidator.validateStringPresence(etCurrentPwd) && inputValidator.validatePasswordLength(etCurrentPwd)) &
				(inputValidator.validateStringPresence(etNewPwd) && inputValidator.validatePasswordLength(etNewPwd)
					&& inputValidator.validateNewPassword(etCurrentPwd, etNewPwd)) &
				(inputValidator.validateStringPresence(etConfirmPwd) && inputValidator.validatePasswordLength(etConfirmPwd)
					&& inputValidator.validateConfirmPasswordMatch(etNewPwd, etConfirmPwd)) &
				inputValidator.validateStringPresence(etUserName);
	}

	public void openGallary() {
        Intent intent = new Intent();

        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

		// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
	            imgDp.setImageBitmap(bitmap);
	            strDpBase64 = Utility.getBase64ForImage(bitmap);
            } catch (IOException e) {
                Log.e(TAG, "onActivityResult convert Image Exception : " + e.toString());
            }
        }
    }

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			if (object != null) {
				switch (apiCode) {
					case WebserviceWrapper.GET_COUNTRIES:
						onCountriesResponse(object);
						break;
					case WebserviceWrapper.GET_STATES:
						onStatesResponse(object);
						break;
					case WebserviceWrapper.GET_CITIES:
						onCitiesResponse(object);
						break;
					case WebserviceWrapper.REGISTER_USER:
						onRegisterUserResponse(object);
						break;
				}
			} else if (error != null) {
				Log.e(TAG, "onResponse ApiCall Exception : " + error.toString() + "\nFor apiCode : " + apiCode);
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onCitiesResponse(Object object) {
		try {
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					arrListCities = new ArrayList<Data>();
					arrListCities.addAll(responseObj.getData());
					List<String> cities = new ArrayList<String>();
					cities.add(getString(R.string.select));
					for (Data city : arrListCities) {
						cities.add(city.getCityName());
					}
					setUpSpinner(spCity, cities);
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
					Log.e(TAG, "onCitiesResponse Failed");
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "onCitiesResponse Exception : " + e.toString());
		}
	}

	private void onStatesResponse(Object object) {
		try {
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					arrListStates = new ArrayList<Data>();
					boolean added = arrListStates.addAll(responseObj.getData());
					List<String> states = new ArrayList<String>();
					states.add(getString(R.string.select));
					for (Data state : arrListStates) {
						states.add(state.getStateName());
					}
					setUpSpinner(spState, states);
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
					Log.e(TAG, "onStatesResponse Failed");
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "onStatesResponse Exception : " + e.toString());
		}
	}

	private void onCountriesResponse(Object object) {
		try {
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					arrListCountries = new ArrayList<Data>();
					arrListCountries.addAll(responseObj.getData());
					List<String> countries = new ArrayList<String>();
					countries.add(getString(R.string.select));
					for (Data country : arrListCountries) {
						countries.add(country.getCountryName());
					}
					setUpSpinner(spCountry, countries);
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
					Log.e(TAG, "onCountriesResponse Failed");
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "onCountriesResponse Exception : " + e.toString());
		}
	}

	private void onRegisterUserResponse(Object object) {
		try {
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				Log.e(TAG, "onRegisterUserResponse : Message : " + responseObj.getMessage());
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					Log.e(TAG, "register successfull");
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
					Log.e(TAG, "register failed");
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "onRegisterUserResponse Exception : " + e.toString());
		}
	}

}
