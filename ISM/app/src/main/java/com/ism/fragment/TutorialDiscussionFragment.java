package com.ism.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;

public class TutorialDiscussionFragment extends Fragment {

	private static final String TAG = TutorialDiscussionFragment.class.getSimpleName();

	private View mView;

	private static final String ARG_WEEK_DAY = "weekDay";
	private int mWeekDay;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param weekDay Parameter 1.
	 * @return A new instance of fragment TutorialDiscussionFragment.
	 */
	// TODO: Rename and change types and number of parameters
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mWeekDay = getArguments().getInt(ARG_WEEK_DAY);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.e(TAG, "onCreateView");
		mView = inflater.inflate(R.layout.fragment_tutorial_discussion, container, false);

		initGlobal();

		return mView;
	}

	private void initGlobal() {

	}

	public void changeDay(int day) {
		Log.e(TAG, "dayChanged : " + day);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		Log.e(TAG, "onAttach to : " + context.getPackageName());
	}
}
