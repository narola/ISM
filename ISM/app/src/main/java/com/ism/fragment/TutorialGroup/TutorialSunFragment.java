package com.ism.fragment.tutorialGroup;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.utility.PreferenceData;
import com.ism.views.CircleImageView;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by c161 on 15/12/15.
 */
public class TutorialSunFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

	private static final String TAG = TutorialSunFragment.class.getSimpleName();

	private View view;

	private RoundedImageView imgDpTeacher;
	private CircleImageView imgDpMember1, imgDpMember2, imgDpMember3, imgDpMember4, imgDpMember5;
	private TextView txtTeacherName, txtTeacherSchool, txtTeacherExperience, txtMessage,
			txtNameMember1, txtNameMember2, txtNameMember3, txtNameMember4, txtNameMember5,
			txtSchoolMember1, txtSchoolMember2, txtSchoolMember3, txtSchoolMember4, txtSchoolMember5;

	public static TutorialSunFragment newInstance() {
		TutorialSunFragment fragment = new TutorialSunFragment();
		return fragment;
	}

	public TutorialSunFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_tutorial_sun, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		imgDpTeacher = (RoundedImageView) view.findViewById(R.id.img_dp_teacher);
		txtTeacherName = (TextView) view.findViewById(R.id.txt_teacher_name);
		txtTeacherSchool = (TextView) view.findViewById(R.id.txt_teacher_school);
		txtTeacherExperience = (TextView) view.findViewById(R.id.txt_teacher_experience);
		txtMessage = (TextView) view.findViewById(R.id.txt_message);
		imgDpMember1 = (CircleImageView) view.findViewById(R.id.img_dp_member1);
		imgDpMember2 = (CircleImageView) view.findViewById(R.id.img_dp_member2);
		imgDpMember3 = (CircleImageView) view.findViewById(R.id.img_dp_member3);
		imgDpMember4 = (CircleImageView) view.findViewById(R.id.img_dp_member4);
		imgDpMember5 = (CircleImageView) view.findViewById(R.id.img_dp_member5);
		txtNameMember1 = (TextView) view.findViewById(R.id.txt_name_member1);
		txtNameMember2 = (TextView) view.findViewById(R.id.txt_name_member2);
		txtNameMember3 = (TextView) view.findViewById(R.id.txt_name_member3);
		txtNameMember4 = (TextView) view.findViewById(R.id.txt_name_member4);
		txtNameMember5 = (TextView) view.findViewById(R.id.txt_name_member5);
		txtSchoolMember1 = (TextView) view.findViewById(R.id.txt_school_member1);
		txtSchoolMember2 = (TextView) view.findViewById(R.id.txt_school_member2);
		txtSchoolMember3 = (TextView) view.findViewById(R.id.txt_school_member3);
		txtSchoolMember4 = (TextView) view.findViewById(R.id.txt_school_member4);
		txtSchoolMember5 = (TextView) view.findViewById(R.id.txt_school_member5);

		txtTeacherName.setTypeface(Global.myTypeFace.getRalewaySemiBold());
		txtTeacherSchool.setTypeface(Global.myTypeFace.getRalewayBold());
		txtTeacherExperience.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtMessage.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtNameMember1.setTypeface(Global.myTypeFace.getRalewaySemiBold());
		txtNameMember2.setTypeface(Global.myTypeFace.getRalewaySemiBold());
		txtNameMember3.setTypeface(Global.myTypeFace.getRalewaySemiBold());
		txtNameMember4.setTypeface(Global.myTypeFace.getRalewaySemiBold());
		txtNameMember5.setTypeface(Global.myTypeFace.getRalewaySemiBold());
		txtSchoolMember1.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtSchoolMember2.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtSchoolMember3.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtSchoolMember4.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtSchoolMember5.setTypeface(Global.myTypeFace.getRalewayRegular());

		callApiAllocateTeacherToGroup();

		txtTeacherName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getFragmentManager().beginTransaction().replace(R.id.fl_tutorial, SundayExamFragment.newInstance()).commit();
			}
		});

		txtTeacherName.setText("Daniel finch");
		txtTeacherSchool.setText("St. Xaviers School");
		txtTeacherExperience.setText("Teaching since 9 yrs");
		txtMessage.setText(Global.strFullName + " is preparing question for you. Be ready to take his challenge.");
		txtNameMember1.setText("Smith");
		txtNameMember2.setText("Smith");
		txtNameMember3.setText("Smith");
		txtNameMember4.setText("Smith");
		txtNameMember5.setText("Smith");
		txtSchoolMember1.setText("St. Xavier");
		txtSchoolMember2.setText("St. Xavier");
		txtSchoolMember3.setText("St. Xavier");
		txtSchoolMember4.setText("St. Xavier");
		txtSchoolMember5.setText("St. Xavier");
		Global.imageLoader.displayImage(Global.strProfilePic, imgDpTeacher, ISMStudent.options);
		Global.imageLoader.displayImage(Global.strProfilePic, imgDpMember1, ISMStudent.options);
		Global.imageLoader.displayImage(Global.strProfilePic, imgDpMember2, ISMStudent.options);
		Global.imageLoader.displayImage(Global.strProfilePic, imgDpMember3, ISMStudent.options);
		Global.imageLoader.displayImage(Global.strProfilePic, imgDpMember4, ISMStudent.options);
		Global.imageLoader.displayImage(Global.strProfilePic, imgDpMember5, ISMStudent.options);

	}

	private void callApiAllocateTeacherToGroup() {
		try {
			Attribute attribute = new Attribute();
			attribute.setGroupId(Global.strTutorialGroupId);
			attribute.setTutorialTopicId(PreferenceData.getStringPrefs(PreferenceData.TUTORIAL_TOPIC_ID, getActivity()));

			new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
					.execute(WebConstants.ALLOCATE_TEACHER_TO_GROUP);
		} catch (Exception e) {
			Log.e(TAG, "callApiAllocateTeacherToGroup Excepiton : " + e.toString());
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.ALLOCATE_TEACHER_TO_GROUP:
					onResponseAllocateTeacherToGroup(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Excepiton : " + e.toString());
		}
	}

	private void onResponseAllocateTeacherToGroup(Object object, Exception error) {
		try {
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {

				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseAllocateTeacherToGroup Failed message : " + responseHandler.getMessage());
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseAllocateTeacherToGroup api Excepiton : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseAllocateTeacherToGroup Excepiton : " + e.toString());
		}
	}
}