package com.ism.fragment.tutorialGroup;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.WebConstants;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.PreferenceData;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.TutorialGroupProfile;

import java.util.Calendar;

/**
 * Created by c161 on --/10/15.
 */
public class TutorialFragment extends Fragment implements TutorialDiscussionFragment.TutorialDiscussionFragmentListener, WebserviceWrapper.WebserviceResponse {

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
	private ExamFragment.ExamListener listenerExam;
	private TutorialGroupProfile tutorialGroupProfile;
	private HostActivity activityHost;

//	public static final int MON = 0;
//	public static final int TUE = 1;
//	public static final int WED = 2;
//	public static final int THU = 3;
//	public static final int FRI = 4;
//	public static final int SAT = 5;
//	public static final int SUN = 6;
	public static final int FRAGMENT_DISCUSSION = 0;
	public static final int FRAGMENT_FRI = 1;
	public static final int FRAGMENT_SAT = 2;
	public static final int FRAGMENT_SUN = 3;
	private static int intCurrentFragment = -1;
	private static int intCurrentDay = -1;

    public static TutorialFragment newInstance(ExamFragment.ExamListener examListener) {
        TutorialFragment fragmentTutorial = new TutorialFragment();
	    fragmentTutorial.setExamListener(examListener);
        return fragmentTutorial;
    }

    public TutorialFragment() {
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

	    callApiGetGroupProfile();

	    txtWeekDays = new TextView[]{txtSunday, txtMonday, txtTuesday, txtWednesday, txtThursday, txtFriday, txtSaturday};
	    txtWeekNumber.setText("Week 1");

	    listenerOnWeekDayClick = new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    switch (v.getId()) {
				    case R.id.txt_monday:
					    intCurrentDay = Calendar.MONDAY;
						loadFragment(FRAGMENT_DISCUSSION);
					    break;
				    case R.id.txt_tuesday:
					    intCurrentDay = Calendar.TUESDAY;
					    loadFragment(FRAGMENT_DISCUSSION);
					    break;
				    case R.id.txt_wednesday:
					    intCurrentDay = Calendar.WEDNESDAY;
					    loadFragment(FRAGMENT_DISCUSSION);
					    break;
				    case R.id.txt_thursday:
					    intCurrentDay = Calendar.THURSDAY;
					    loadFragment(FRAGMENT_DISCUSSION);
					    break;
				    case R.id.txt_friday:
					    intCurrentDay = Calendar.FRIDAY;
					    loadFragment(FRAGMENT_FRI);
					    break;
				    case R.id.txt_saturday:
					    intCurrentDay = Calendar.SATURDAY;
					    loadFragment(FRAGMENT_SAT);
					    break;
				    case R.id.txt_sunday:
					    intCurrentDay = Calendar.SUNDAY;
					    loadFragment(FRAGMENT_SUN);
					    break;
			    }
			    setWeekDaySelection(intCurrentDay);
		    }
	    };

	    txtMonday.setOnClickListener(listenerOnWeekDayClick);
	    txtTuesday.setOnClickListener(listenerOnWeekDayClick);
	    txtWednesday.setOnClickListener(listenerOnWeekDayClick);
	    txtThursday.setOnClickListener(listenerOnWeekDayClick);
	    txtFriday.setOnClickListener(listenerOnWeekDayClick);
	    txtSaturday.setOnClickListener(listenerOnWeekDayClick);
	    txtSunday.setOnClickListener(listenerOnWeekDayClick);

	    Calendar cal = Calendar.getInstance();
	    switch (cal.get(Calendar.DAY_OF_WEEK)) {
		    case Calendar.MONDAY:
			    txtMonday.performClick();
			    break;
		    case Calendar.TUESDAY:
			    txtTuesday.performClick();
			    break;
		    case Calendar.WEDNESDAY:
			    txtWednesday.performClick();
			    break;
		    case Calendar.THURSDAY:
			    txtThursday.performClick();
			    break;
		    case Calendar.FRIDAY:
			    txtFriday.performClick();
			    break;
		    case Calendar.SATURDAY:
			    txtSaturday.performClick();
			    break;
		    case Calendar.SUNDAY:
			    txtSunday.performClick();
			    break;
	    }
    }

	private void callApiGetGroupProfile() {
		try {
			activityHost.showProgress();
			Attribute attribute = new Attribute();
			attribute.setGroupId(PreferenceData.getStringPrefs(PreferenceData.TUTORIAL_GROUP_ID, getActivity()));
			attribute.setUserId(Global.strUserId);

			new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
					.execute(WebConstants.GET_GROUP_PROFILE);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetGroupProfile Exception : " + e.toString());
		}
	}

	private void setWeekDaySelection(int selectedDayId) {
		/**
		 * Decrease selectedDayId by 1 because Weekday ids in Calendar starts from 1, i.e. Caledar.SUNDAY is 1
		 */
		selectedDayId--;
		for (int dayId = 0; dayId < txtWeekDays.length; dayId++) {
			if (selectedDayId != dayId) {
				txtWeekDays[dayId].setEnabled(true);
			} else {
				txtWeekDays[dayId].setEnabled(false);
			}
		}
	}

	private void loadFragment(int fragment) {
		try {
			switch (fragment) {
				case FRAGMENT_DISCUSSION:
					if (intCurrentFragment == FRAGMENT_DISCUSSION && fragTutorialDiscussion != null) {
						fragTutorialDiscussion.setDay(intCurrentDay);
					} else {
						fragTutorialDiscussion = TutorialDiscussionFragment.newInstance(intCurrentDay);
						fragTutorialDiscussion.setTutorialDiscussionListener(this);
						getChildFragmentManager().beginTransaction().replace(R.id.fl_tutorial, fragTutorialDiscussion).commit();
					}
					break;
				case FRAGMENT_FRI:
					getChildFragmentManager().beginTransaction().replace(R.id.fl_tutorial, TutorialFriAddQuestionFragment.newInstance(listenerExam)).commit();
					break;
				case FRAGMENT_SAT:
					getChildFragmentManager().beginTransaction().replace(R.id.fl_tutorial, TutorialSatFragment.newInstance()).commit();
					break;
				case FRAGMENT_SUN:
					getChildFragmentManager().beginTransaction().replace(R.id.fl_tutorial, TutorialSunFragment.newInstance()).commit();
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
	        activityHost = (HostActivity) activity;
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
	public void onDayChanged(int dayId) {
		setWeekDaySelection(dayId);
	}

	public void setExamListener(ExamFragment.ExamListener examListener) {
		listenerExam = examListener;
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.GET_GROUP_PROFILE:
					onResponseGetGroupProfile(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseGetGroupProfile(Object object, Exception error) {
		try {
			activityHost.hideProgress();
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					tutorialGroupProfile = responseHandler.getTutorialGroupProfile().get(0);
					activityHost.showTutorialGroupData(tutorialGroupProfile);
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseGetGroupProfile Failed, message : " + ((ResponseHandler) object).getMessage());
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseGetGroupProfile api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetGroupProfile Exception : " + e.toString());
		}
	}

}