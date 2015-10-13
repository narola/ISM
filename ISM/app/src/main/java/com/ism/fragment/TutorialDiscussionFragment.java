package com.ism.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;

/**
 * Created by c161 on 12/10/15.
 */
public class TutorialDiscussionFragment extends Fragment {

	private static final String TAG = TutorialDiscussionFragment.class.getSimpleName();

	private View view;

	private static final String ARG_WEEK_DAY = "weekDay";
	private int intWeekDay;

	public static TutorialDiscussionFragment newInstance(int weekDay) {
		TutorialDiscussionFragment fragment = new TutorialDiscussionFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_WEEK_DAY, weekDay);
		fragment.setArguments(args);
		return fragment;
	}

	public TutorialDiscussionFragment() {
		// Required empty public constructor
	}

	public interface TutorialDiscussionFragmentListener {
		public void onDayChanged(int day);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			intWeekDay = getArguments().getInt(ARG_WEEK_DAY);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_tutorial_discussion, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		setDay(intWeekDay);
	}

	public void setDay(int day) {
		intWeekDay = day;
	}

}
