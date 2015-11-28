package com.ism.fragment.userprofile;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;

/**
 * Created by c161 on 10/11/15.
 */
public class FindStudymatesFragment extends Fragment {

	private static final String TAG = FindStudymatesFragment.class.getSimpleName();

	private View view;

	public static FindStudymatesFragment newInstance(Bundle bundleArguments) {
		FindStudymatesFragment fragFindStudymates = new FindStudymatesFragment();
		fragFindStudymates.setArguments(bundleArguments);
		return fragFindStudymates;
	}

	public FindStudymatesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_find_studymates, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {

	}

}
