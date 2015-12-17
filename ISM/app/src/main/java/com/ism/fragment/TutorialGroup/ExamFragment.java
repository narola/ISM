package com.ism.fragment.tutorialGroup;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;

/**
 * Created by c161 on 13/10/15.
 */
public class ExamFragment extends Fragment {

	private static final String TAG = ExamFragment.class.getSimpleName();

	private View view;

	private static final String ARG_PARAM1 = "param1";
	private String mParam1;

	public static ExamFragment newInstance(String param1) {
		ExamFragment fragment = new ExamFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		fragment.setArguments(args);
		return fragment;
	}

	public ExamFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_exam, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {

	}

}
