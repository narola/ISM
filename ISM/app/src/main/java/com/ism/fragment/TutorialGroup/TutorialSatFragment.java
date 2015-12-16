package com.ism.fragment.tutorialGroup;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;

/**
 * Created by c161 on 12/10/15.
 */
public class TutorialSatFragment extends Fragment {

	private static final String TAG = TutorialSatFragment.class.getSimpleName();

	private View view;

	public static TutorialSatFragment newInstance() {
		TutorialSatFragment fragment = new TutorialSatFragment();
		return fragment;
	}

	public TutorialSatFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_tutorial_sat, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {

	}

}