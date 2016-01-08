package com.ism.fragment.tutorialGroup;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.interfaces.FragmentListener;

public class SundayExamFragment extends Fragment {

	private static final String TAG = SundayExamFragment.class.getSimpleName();

	private View view;

	private TutorialFragment fragTutorial;
	private FragmentListener listenerFragment;

	public static SundayExamFragment newInstance() {
		SundayExamFragment fragment = new SundayExamFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_sunday_exam, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		listenerFragment = (FragmentListener) activity;
		fragTutorial = (TutorialFragment) getParentFragment();
		if (listenerFragment != null) {
			listenerFragment.onFragmentAttached(HostActivity.FRAGMENT_SUNDAY_EXAM);
		}

		if (fragTutorial != null) {
			fragTutorial.updateLayoutForSundayExam(true);
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		if (listenerFragment != null) {
			listenerFragment.onFragmentDetached(HostActivity.FRAGMENT_SUNDAY_EXAM);
		}

		if (fragTutorial != null) {
			fragTutorial.updateLayoutForSundayExam(false);
		}
	}

}