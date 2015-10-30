package com.ism.teacher.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;

public class TeacherClassWallFragment extends Fragment {

	private static final String TAG = TeacherClassWallFragment.class.getSimpleName();

	private View view;
	private RecyclerView recyclerPost;

	public static TeacherClassWallFragment newInstance() {
		TeacherClassWallFragment fragment = new TeacherClassWallFragment();
		return fragment;
	}

	public TeacherClassWallFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_teacher_class_wall, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {

	}

}
