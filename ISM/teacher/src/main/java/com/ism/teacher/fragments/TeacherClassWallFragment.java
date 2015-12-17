package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.teacher.R;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.interfaces.FragmentListener;


public class TeacherClassWallFragment extends Fragment {

	private static final String TAG = TeacherClassWallFragment.class.getSimpleName();

	private View view;
	private RecyclerView recyclerPost;
	private FragmentListener fragListener;

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

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			fragListener = (FragmentListener) activity;
			if (fragListener != null) {
			//	fragListener.onFragmentAttached(TeacherOfficeFragment.FRAGMENT_CLASSWALL);
			}
		} catch (ClassCastException e) {
			Log.e(TAG, "onAttach Exception : " + e.toString());
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			if (fragListener != null) {
				//fragListener.onFragmentDetached(TeacherOfficeFragment.FRAGMENT_CLASSWALL);
			}
		} catch (ClassCastException e) {
			Log.e(TAG, "onDetach Exception : " + e.toString());
		}
		fragListener = null;
	}

	private Bundle getBundleArguments() {
		return ((TeacherHostActivity) getActivity()).getBundle();
	}
}
