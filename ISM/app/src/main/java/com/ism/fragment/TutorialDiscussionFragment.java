package com.ism.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;

/**
 * Created by c161 on 12/10/15.
 */
public class TutorialDiscussionFragment extends Fragment {

	private static final String TAG = TutorialDiscussionFragment.class.getSimpleName();

	private View view;
	private TextView txtDay;

	private TutorialDiscussionFragmentListener listenerTutorialDiscussion;

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
		public void onDayChanged(int dayId);
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
		txtDay = (TextView) view.findViewById(R.id.txt_day);

		setDay(intWeekDay);
	}

	public void setTutorialDiscussionListener(TutorialDiscussionFragmentListener listenerTutorialDiscussion) {
		this.listenerTutorialDiscussion = listenerTutorialDiscussion;
	}

	public void setDay(int day) {
		intWeekDay = day;
		switch (intWeekDay) {
			case TutorialFragment.MON:
				txtDay.setText("Monday");
				break;
			case TutorialFragment.TUE:
				txtDay.setText("Tuesday");
				break;
			case TutorialFragment.WED:
				txtDay.setText("Wednesday");
				break;
			case TutorialFragment.THU:
				txtDay.setText("Thursday");
				break;
			case TutorialFragment.FRI:
				txtDay.setText("Friday");
				break;
			case TutorialFragment.SAT:
				txtDay.setText("Saturday");
				break;
			case TutorialFragment.SUN:
				txtDay.setText("Sunday");
				break;
		}
	}

}
