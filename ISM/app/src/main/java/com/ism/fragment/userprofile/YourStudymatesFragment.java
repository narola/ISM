package com.ism.fragment.userprofile;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;

public class YourStudymatesFragment extends Fragment {

	public static final String TAG = YourStudymatesFragment.class.getSimpleName();

	private View view;

	public static YourStudymatesFragment newInstance(Bundle bundleArguments) {
		YourStudymatesFragment fragment = new YourStudymatesFragment();
		fragment.setArguments(bundleArguments);
		return fragment;
	}

	public YourStudymatesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_your_studymates, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {

	}

}