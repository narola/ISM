package com.ism.fragment.TutorialGroup;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.object.Global;
import com.ism.views.CircleImageView;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by c161 on 15/12/15.
 */
public class TutorialSunFragment extends Fragment {

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

}