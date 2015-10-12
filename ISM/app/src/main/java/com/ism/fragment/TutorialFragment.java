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

public class TutorialFragment extends Fragment {

    private static final String TAG = TutorialFragment.class.getSimpleName();

    private View mView;
	private TextView mTextWeekNumber;
	private TextView mTextMonday;
	private TextView mTextTuesday;
	private TextView mTextWednesday;
	private TextView mTextThursday;
	private TextView mTextFriday;
	private TextView mTextSaturday;
	private TextView mTextSunday;

	private TextView mTextWeekDays[];

    private FragmentListener mFragmentListener;
    private View.OnClickListener mOnWeekDayClickListener;
	private TutorialDiscussionFragment mTutorialDiscussionFragment;

	public static final int MON = 0;
	public static final int TUE = 1;
	public static final int WED = 2;
	public static final int THU = 3;
	public static final int FRAGMENT_DISCUSSION = 0;
	public static final int FRAGMENT_FRI = 1;
	public static final int FRAGMENT_SAT = 2;
	public static final int FRAGMENT_SUN = 3;
	private static int mCurrentFragment;
	private static int mCurrentDay;

    public static TutorialFragment newInstance() {
        TutorialFragment fragmentTutorial = new TutorialFragment();
        return fragmentTutorial;
    }

    public TutorialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tutorial, container, false);

        initGlobal();

        return mView;
    }

    private void initGlobal() {
	    mTextWeekNumber = (TextView) mView.findViewById(R.id.text_week_number);
	    mTextMonday = (TextView) mView.findViewById(R.id.text_monday);
	    mTextTuesday = (TextView) mView.findViewById(R.id.text_tuesday);
	    mTextWednesday = (TextView) mView.findViewById(R.id.text_wednesday);
	    mTextThursday = (TextView) mView.findViewById(R.id.text_thursday);
	    mTextFriday = (TextView) mView.findViewById(R.id.text_friday);
	    mTextSaturday = (TextView) mView.findViewById(R.id.text_saturday);
	    mTextSunday = (TextView) mView.findViewById(R.id.text_sunday);

	    mTextWeekDays = new TextView[]{mTextMonday, mTextTuesday, mTextWednesday, mTextThursday, mTextFriday, mTextSaturday, mTextSunday};
	    mTextWeekNumber.setText("Week 1");

	    mOnWeekDayClickListener = new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    switch (v.getId()) {
				    case R.id.text_monday:
					    setWeekDaySelection(mTextMonday);
					    mCurrentDay = MON;
						loadFragment(FRAGMENT_DISCUSSION);
					    break;
				    case R.id.text_tuesday:
					    setWeekDaySelection(mTextTuesday);
					    mCurrentDay = TUE;
					    loadFragment(FRAGMENT_DISCUSSION);
					    break;
				    case R.id.text_wednesday:
					    setWeekDaySelection(mTextWednesday);
					    mCurrentDay = WED;
					    loadFragment(FRAGMENT_DISCUSSION);
					    break;
				    case R.id.text_thursday:
					    setWeekDaySelection(mTextThursday);
					    mCurrentDay = THU;
					    loadFragment(FRAGMENT_DISCUSSION);
					    break;
				    case R.id.text_friday:
					    setWeekDaySelection(mTextFriday);
					    loadFragment(FRAGMENT_FRI);
					    break;
				    case R.id.text_saturday:
					    setWeekDaySelection(mTextSaturday);
					    loadFragment(FRAGMENT_SAT);
					    break;
				    case R.id.text_sunday:
					    setWeekDaySelection(mTextSunday);
					    loadFragment(FRAGMENT_SUN);
					    break;
			    }
		    }
	    };

	    mTextMonday.setOnClickListener(mOnWeekDayClickListener);
	    mTextTuesday.setOnClickListener(mOnWeekDayClickListener);
	    mTextWednesday.setOnClickListener(mOnWeekDayClickListener);
	    mTextThursday.setOnClickListener(mOnWeekDayClickListener);
	    mTextFriday.setOnClickListener(mOnWeekDayClickListener);
	    mTextSaturday.setOnClickListener(mOnWeekDayClickListener);
	    mTextSunday.setOnClickListener(mOnWeekDayClickListener);

	    mTextMonday.performClick();
    }

	private void setWeekDaySelection(TextView textSelectedWeekDay) {
		for (int i = 0; i < mTextWeekDays.length; i++) {
			if (textSelectedWeekDay != mTextWeekDays[i]) {
				mTextWeekDays[i].setEnabled(true);
			} else {
				mTextWeekDays[i].setEnabled(false);
			}
		}
	}

	private void loadFragment(int fragment) {
		try {
			switch (fragment) {
				case FRAGMENT_DISCUSSION:
					if (mCurrentFragment == FRAGMENT_DISCUSSION) {
						if (mTutorialDiscussionFragment != null) {
							mTutorialDiscussionFragment.changeDay(mCurrentDay);
						}
					} else {
						mTutorialDiscussionFragment = TutorialDiscussionFragment.newInstance(mCurrentDay);
						getChildFragmentManager().beginTransaction().replace(R.id.frame_tutorial, mTutorialDiscussionFragment).commit();
					}
					break;
				case FRAGMENT_FRI:
//					getChildFragmentManager().beginTransaction().replace(R.id.frame_tutorial, TutorialFragment.newInstance()).commit();
					break;
				case FRAGMENT_SAT:
//					getChildFragmentManager().beginTransaction().replace(R.id.frame_tutorial, ClassroomFragment.newInstance()).commit();
					break;
				case FRAGMENT_SUN:
//					getChildFragmentManager().beginTransaction().replace(R.id.frame_tutorial, AssessmentFragment.newInstance()).commit();
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "loadFragment Exception : " + e.toString());
		}
	}

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mFragmentListener = (FragmentListener) activity;
            if (mFragmentListener != null) {
                mFragmentListener.onFragmentAttached(HostActivity.FRAGMENT_TUTORIAL);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (mFragmentListener != null) {
                mFragmentListener.onFragmentDetached(HostActivity.FRAGMENT_TUTORIAL);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        mFragmentListener = null;
    }

}
