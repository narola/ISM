package com.ism.fragment.tutorialGroup;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.Global;

/**
 * Created by c161 on 15/12/15.
 */
public class TutorialSunFragment extends Fragment {

	private static final String TAG = TutorialSunFragment.class.getSimpleName();

	private View view;

	private ImageView imgTeacherDp;
	private TextView txtTeacherName, txtTeacherSchool, txtTeacherExperience;

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
		imgTeacherDp = (ImageView) view.findViewById(R.id.img_dp_teacher);
		txtTeacherName = (TextView) view.findViewById(R.id.txt_teacher_name);
		txtTeacherSchool = (TextView) view.findViewById(R.id.txt_teacher_school);
		txtTeacherExperience = (TextView) view.findViewById(R.id.txt_teacher_experience);

		txtTeacherName.setTypeface(Global.myTypeFace.getRalewaySemiBold());
		txtTeacherSchool.setTypeface(Global.myTypeFace.getRalewayBold());
		txtTeacherExperience.setTypeface(Global.myTypeFace.getRalewayRegular());

		txtTeacherName.setText("Daniel finch");
		txtTeacherSchool.setText("St. Xaviers School");
		txtTeacherExperience.setText("Teaching since 9 yrs");
	}

}