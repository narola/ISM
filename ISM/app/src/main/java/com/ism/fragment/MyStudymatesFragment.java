package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;

/**
 * Created by c161 on 10/11/15.
 */
public class MyStudymatesFragment extends Fragment {

	private static final String TAG = MyStudymatesFragment.class.getSimpleName();

	private View view;

	public static MyStudymatesFragment newInstance() {
		MyStudymatesFragment fragment = new MyStudymatesFragment();
		return fragment;
	}

	public MyStudymatesFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_my_studymates, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {

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
