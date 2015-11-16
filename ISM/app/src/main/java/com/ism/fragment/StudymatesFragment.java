package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;

/**
 * Created by c161 on 06/11/15.
 */
public class StudymatesFragment extends Fragment {

	private static final String TAG = StudymatesFragment.class.getSimpleName();

	private View view;
	private TextView txtStudymates, txtFindStudymates;

	public static StudymatesFragment newInstance() {
		StudymatesFragment fragment = new StudymatesFragment();
		return fragment;
	}

	public StudymatesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_study_mates, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		txtStudymates = (TextView) view.findViewById(R.id.txt_studymates);
		txtFindStudymates = (TextView) view.findViewById(R.id.txt_find_studymates);

		txtStudymates.setEnabled(false);

		txtStudymates.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				txtStudymates.setEnabled(false);
				txtFindStudymates.setEnabled(true);
			}
		});

		txtFindStudymates.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				txtFindStudymates.setEnabled(false);
				txtStudymates.setEnabled(true);
			}
		});

	}



	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

}
