package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.HostActivity;
import com.ism.R;
import com.ism.interfaces.FragmentListener;

/**
 * Created by c161 on --/10/15.
 */
public class TutorialFragment extends Fragment implements TutorialDiscussionFragment.TutorialDiscussionFragmentListener {

    private static final String TAG = TutorialFragment.class.getSimpleName();

    private View view;
	private TextView txtWeekNumber;
	private TextView txtMonday;
	private TextView txtTuesday;
	private TextView txtWednesday;
	private TextView txtThursday;
	private TextView txtFriday;
	private TextView txtSaturday;
	private TextView txtSunday;

	private TextView txtWeekDays[];

    private FragmentListener fragListener;
    private View.OnClickListener listenerOnWeekDayClick;
	private TutorialDiscussionFragment fragTutorialDiscussion;

	public static final int MON = 0;
	public static final int TUE = 1;
	public static final int WED = 2;
	public static final int THU = 3;
	public static final int FRAGMENT_DISCUSSION = 0;
	public static final int FRAGMENT_FRI = 1;
	public static final int FRAGMENT_SAT = 2;
	public static final int FRAGMENT_SUN = 3;
	private static int intCurrentFragment = -1;
	private static int intCurrentDay = -1;

    public static TutorialFragment newInstance() {
        TutorialFragment fragmentTutorial = new TutorialFragment();
        return fragmentTutorial;
    }

    public TutorialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    view = inflater.inflate(R.layout.fragment_tutorial, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
	    txtWeekNumber = (TextView) view.findViewById(R.id.txt_week_number);
	    txtMonday = (TextView) view.findViewById(R.id.txt_monday);
	    txtTuesday = (TextView) view.findViewById(R.id.txt_tuesday);
	    txtWednesday = (TextView) view.findViewById(R.id.txt_wednesday);
	    txtThursday = (TextView) view.findViewById(R.id.txt_thursday);
	    txtFriday = (TextView) view.findViewById(R.id.txt_friday);
	    txtSaturday = (TextView) view.findViewById(R.id.txt_saturday);
	    txtSunday = (TextView) view.findViewById(R.id.txt_sunday);

	    txtWeekDays = new TextView[]{ txtMonday, txtTuesday, txtWednesday, txtThursday, txtFriday, txtSaturday, txtSunday};
	    txtWeekNumber.setText("Week 1");

	    listenerOnWeekDayClick = new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    switch (v.getId()) {
				    case R.id.txt_monday:
					    setWeekDaySelection(txtMonday);
					    intCurrentDay = MON;
						loadFragment(FRAGMENT_DISCUSSION);
					    break;
				    case R.id.txt_tuesday:
					    setWeekDaySelection(txtTuesday);
					    intCurrentDay = TUE;
					    loadFragment(FRAGMENT_DISCUSSION);
					    break;
				    case R.id.txt_wednesday:
					    setWeekDaySelection(txtWednesday);
					    intCurrentDay = WED;
					    loadFragment(FRAGMENT_DISCUSSION);
					    break;
				    case R.id.txt_thursday:
					    setWeekDaySelection(txtThursday);
					    intCurrentDay = THU;
					    loadFragment(FRAGMENT_DISCUSSION);
					    break;
				    case R.id.txt_friday:
					    setWeekDaySelection(txtFriday);
					    loadFragment(FRAGMENT_FRI);
					    break;
				    case R.id.txt_saturday:
					    setWeekDaySelection(txtSaturday);
					    loadFragment(FRAGMENT_SAT);
					    break;
				    case R.id.txt_sunday:
					    setWeekDaySelection(txtSunday);
					    loadFragment(FRAGMENT_SUN);
					    break;
			    }
		    }
	    };

	    txtMonday.setOnClickListener(listenerOnWeekDayClick);
	    txtTuesday.setOnClickListener(listenerOnWeekDayClick);
	    txtWednesday.setOnClickListener(listenerOnWeekDayClick);
	    txtThursday.setOnClickListener(listenerOnWeekDayClick);
	    txtFriday.setOnClickListener(listenerOnWeekDayClick);
	    txtSaturday.setOnClickListener(listenerOnWeekDayClick);
	    txtSunday.setOnClickListener(listenerOnWeekDayClick);

	    txtFriday.performClick();
    }

	private void setWeekDaySelection(TextView textSelectedWeekDay) {
		for (int i = 0; i < txtWeekDays.length; i++) {
			if (textSelectedWeekDay != txtWeekDays[i]) {
				txtWeekDays[i].setEnabled(true);
			} else {
				txtWeekDays[i].setEnabled(false);
			}
		}
	}

	private void loadFragment(int fragment) {
		try {
			switch (fragment) {
				case FRAGMENT_DISCUSSION:
					if (intCurrentFragment == FRAGMENT_DISCUSSION) {
						if (fragTutorialDiscussion != null) {
							fragTutorialDiscussion.setDay(intCurrentDay);
						}
					} else {
						fragTutorialDiscussion = TutorialDiscussionFragment.newInstance(intCurrentDay);
						getChildFragmentManager().beginTransaction().replace(R.id.fl_tutorial, fragTutorialDiscussion).commit();
					}
					break;
				case FRAGMENT_FRI:
					getChildFragmentManager().beginTransaction().replace(R.id.fl_tutorial, ExamFragment.newInstance("")).commit();
					break;
				case FRAGMENT_SAT:
					getChildFragmentManager().beginTransaction().replace(R.id.fl_tutorial, ClassroomFragment.newInstance()).commit();
					break;
				case FRAGMENT_SUN:
					getChildFragmentManager().beginTransaction().replace(R.id.fl_tutorial, AssessmentFragment.newInstance()).commit();
					break;
			}
			intCurrentFragment = fragment;
		} catch (Exception e) {
			Log.e(TAG, "loadFragment Exception : " + e.toString());
		}
	}

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(HostActivity.FRAGMENT_TUTORIAL);
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
                fragListener.onFragmentDetached(HostActivity.FRAGMENT_TUTORIAL);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

	@Override
	public void onDayChanged(int day) {

	}

}
