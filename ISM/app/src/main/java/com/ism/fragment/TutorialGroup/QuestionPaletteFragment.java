package com.ism.fragment.tutorialGroup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.QuestionPaletteAdapter;
import com.ism.adapter.TutorialGroupAdapter;
import com.ism.views.TimerView;
import com.ism.ws.model.FridayExamQuestion;
import com.ism.ws.model.TutorialGroupMember;
import com.ism.ws.model.TutorialGroupProfile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import model.ROAdminConfig;
import realmhelper.StudentHelper;

/**
 * Created by c161 on 14/10/15.
 */
public class QuestionPaletteFragment extends Fragment implements com.ism.fragment.tutorialGroup.ExamFragment.ExamListener, HostActivity.HostListenerQuestionPalette {

	private static final String TAG = QuestionPaletteFragment.class.getSimpleName();

	private View view;
	private RelativeLayout rlQuestionPalette;
	private ListView lvTutorialGroup;
	private TextView txtTitle;
	private TextView txtGroupScore;
	private TextView txtActiveComments;
	private TextView txtGroupRank;
	private GridView gridQuestion;
	private Button btnEndTest;
	private TimerView timerViewExam;

	private CountDownTimer timerExam;
//	private ExamFragment fragExam;
	private QuestionPaletteAdapter adpQuestionPalette;
	private ArrayList<FridayExamQuestion> arrListQuestions;
	private TutorialGroupAdapter adpTutorialGroup;
	private ArrayList<TutorialGroupMember> arrListGroupMembers;
	private StudentHelper studentHelper;
	private HostActivity activityHost;

	private String strExamId;
	private int intTimeLeft;
	private long longExamDurationMilli;
	private boolean setActiveHours;

	public static QuestionPaletteFragment newInstance(boolean setActiveHours) {
		QuestionPaletteFragment fragment = new QuestionPaletteFragment();
		fragment.setActiveHours = setActiveHours;
		return fragment;
	}

	public QuestionPaletteFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_question_palette, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		rlQuestionPalette = (RelativeLayout) view.findViewById(R.id.rl_question_palette);
		lvTutorialGroup = (ListView) view.findViewById(R.id.lv_tutorial_group);
		txtTitle = (TextView) view.findViewById(R.id.txt_title);
		txtGroupScore = (TextView) view.findViewById(R.id.txt_group_score);
		txtActiveComments = (TextView) view.findViewById(R.id.txt_active_comments_value);
		txtGroupRank = (TextView) view.findViewById(R.id.txt_group_rank_value);
		gridQuestion = (GridView) view.findViewById(R.id.grid_question_no);
		btnEndTest = (Button) view.findViewById(R.id.btn_end_test);
		timerViewExam = (TimerView) view.findViewById(R.id.timer_exam);

		studentHelper = new StudentHelper(getActivity());

		if (setActiveHours) {
			Calendar cal = Calendar.getInstance();
//			cal.set(Calendar.HOUR, 11);
//			cal.set(Calendar.MINUTE, 10);
//			cal.set(Calendar.AM_PM, Calendar.AM);
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
				try {
					ROAdminConfig configStartTime = studentHelper.getActiveHoursStartTime();
					SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
					Calendar calStartTime = Calendar.getInstance();
					String startTime = configStartTime.getConfigValue() + " " + configStartTime.getValueUnit();
//					String startTime = "11:21 am";
					Log.e(TAG, "start time : " + startTime);
					Date date = DATE_FORMAT_TIME.parse(startTime);
					calStartTime.set(Calendar.HOUR_OF_DAY, date.getHours());
					calStartTime.set(Calendar.MINUTE, date.getMinutes());

					if (cal.after(calStartTime)) {
						ROAdminConfig configEndTime = studentHelper.getActiveHoursEndTime();
						String endTime = configEndTime.getConfigValue() + " " + configEndTime.getValueUnit();
//						String endTime = "04:15 pm";
						Log.e(TAG, "end time : " + endTime);
						Date endDate = DATE_FORMAT_TIME.parse(endTime);

						Calendar calEndTime = Calendar.getInstance();
						calEndTime.set(Calendar.HOUR_OF_DAY, endDate.getHours());
						calEndTime.set(Calendar.MINUTE, endDate.getMinutes());

						if (cal.before(calEndTime)) {
							longExamDurationMilli = calEndTime.getTimeInMillis() - cal.getTimeInMillis();
							timerViewExam.setTotalTimeMilli(calEndTime.getTimeInMillis() - calStartTime.getTimeInMillis());
							timerExam = new CountDownTimer(longExamDurationMilli, 1000) {

								@Override
								public void onTick(long millisUntilFinished) {
									intTimeLeft = (int) millisUntilFinished;
									timerViewExam.setTimeMilli(intTimeLeft);
								}

								@Override
								public void onFinish() {
									timerViewExam.setTimeMilli(0);
									end();
								}
							};
							timerExam.start();
						}
					}
				} catch (ParseException e) {
					Log.e(TAG, "date parsing Exception : " + e.toString());
				}
			}
		}

		btnEndTest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				 endTest();
			}
		});

	}

	@Override
	public void startTest(ArrayList<FridayExamQuestion> questions, String examId, com.ism.fragment.tutorialGroup.ExamFragment examFragment) {
		try {
			/*longExamDurationMilli = examFragment.getExamDurationMinutes() * 60 * 1000;
			timerViewExam.setTotalTimeMin(examFragment.getExamDurationMinutes());
			timerExam = new CountDownTimer(longExamDurationMilli, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {
					intTimeLeft = (int) millisUntilFinished;
					timerViewExam.setTimeMilli(intTimeLeft);
				}

				@Override
				public void onFinish() {
					timerViewExam.setTimeMilli(0);
					end();
				}
			};
			timerExam.start();*/

//			fragExam = examFragment;
			strExamId = examId;
			lvTutorialGroup.setVisibility(View.GONE);
			rlQuestionPalette.setVisibility(View.VISIBLE);
			arrListQuestions = questions;
			adpQuestionPalette = new QuestionPaletteAdapter(getActivity(), arrListQuestions, examFragment);
			gridQuestion.setAdapter(adpQuestionPalette);
			txtTitle.setText(R.string.question_palette);
		} catch (Exception e) {
			Log.e(TAG, "startTest Exception : " + e.toString());
		}
	}

	@Override
	public void onQuestionSet(int position) {
		if (position == -1) {
			endTest();
		} else {
			if (adpQuestionPalette != null) {
				adpQuestionPalette.setQuestion(position);
			}
			gridQuestion.smoothScrollToPosition(position);
		}
	}

	private void end() {
		try {
			if (timerExam != null) {
				timerExam.cancel();
			}
			for (int i = 0; i < arrListQuestions.size(); i++) {
				arrListQuestions.get(i).setIsReviewLater(false);
				arrListQuestions.get(i).setIsSkipped(false);
			}
			int timeSpent = (int) ((longExamDurationMilli - intTimeLeft) / 60000);

			lvTutorialGroup.setVisibility(View.VISIBLE);
			rlQuestionPalette.setVisibility(View.GONE);
			getFragmentManager().beginTransaction().replace(R.id.fl_tutorial, com.ism.fragment.tutorialGroup.ResultFragment.newInstance(arrListQuestions,
					strExamId, false, timeSpent)).commit();
//			getFragmentManager().beginTransaction().remove(this).commit();
		} catch (Exception e) {
			Log.e(TAG, "end Exception : " + e.toString());
		}
	}

	public void endTest() {
		boolean isUncomplete = false;
		for (int i = 0; i < arrListQuestions.size(); i++) {
			if (arrListQuestions.get(i).isReviewLater() || arrListQuestions.get(i).isSkipped() || !arrListQuestions.get(i).isAnswered()) {
				isUncomplete = true;
				break;
			}
		}
		String msg = isUncomplete ? getResources().getString(R.string.msg_end_test_unanswered) : getResources().getString(R.string.msg_end_test);
		String negativeText = isUncomplete ? getResources().getString(R.string.attend) : getResources().getString(R.string.cancel);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(msg)
				.setPositiveButton(R.string.end_test, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						end();
					}
				}).setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.setCancelable(false);
		builder.create().show();
	}

	@Override
	public void showTutorialGroupData(TutorialGroupProfile tutorialGroupProfile) {
		if (tutorialGroupProfile != null) {
			arrListGroupMembers = tutorialGroupProfile.getGroupMembers();
			adpTutorialGroup = new TutorialGroupAdapter(getActivity(), arrListGroupMembers);
			lvTutorialGroup.setAdapter(adpTutorialGroup);

			txtGroupScore.setText(Html.fromHtml("<small><font color='#7B7B7B'>GROUP SCORE : </font></small><b><font color='#1BC4A2'>"
				+ (tutorialGroupProfile.getGroupScore() != null ? tutorialGroupProfile.getGroupScore() : "") + "</font><b/>"));
			txtActiveComments.setText(tutorialGroupProfile.getTotalActiveComments() != null ? tutorialGroupProfile.getTotalActiveComments() : "");
			txtGroupRank.setText(tutorialGroupProfile.getGroupRank() != null ? tutorialGroupProfile.getGroupRank() : "");
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		activityHost = (HostActivity) activity;
		activityHost.setListenerQuestionPalette(this);
	}

}
