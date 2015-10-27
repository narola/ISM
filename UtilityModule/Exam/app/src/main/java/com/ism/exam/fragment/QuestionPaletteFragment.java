package com.ism.exam.fragment;

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

import com.ism.exam.R;
import com.ism.exam.adapter.QuestionPaletteAdapter;
import com.ism.exam.adapter.TutorialGroupAdapter;
import com.ism.exam.model.QuestionObjective;
import com.ism.exam.model.TutorialGroupMember;
import com.ism.exam.utility.Utility;
import com.ism.exam.view.TimerView;

import java.util.ArrayList;

/**
 * Created by c161 on 14/10/15.
 */
public class QuestionPaletteFragment extends Fragment implements ExamFragment.ExamListener {

	private static final String TAG = QuestionPaletteFragment.class.getSimpleName();

	private View view;
	private RelativeLayout rlQuestionPalette;
	private ListView lvTutorialGroup;
	private TextView txtTitle;
	private TextView txtGroupScore;
	private TextView txtActiveComments;
	private TextView txtMyScore;
	private GridView gridQuestion;
	private Button btnEndTest;
	private TimerView timerViewExam;

	private CountDownTimer timerExam;
	private ExamFragment fragExam;
	private QuestionPaletteAdapter adpQuestionPalette;
	private ArrayList<QuestionObjective> arrListQuestions;
	private TutorialGroupAdapter adpTutorialGroup;
	private ArrayList<TutorialGroupMember> arrListMembers;

	private int intTimeLeft;
	private int intExamDurationMilli;

	public static QuestionPaletteFragment newInstance() {
		QuestionPaletteFragment fragment = new QuestionPaletteFragment();
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
		txtMyScore = (TextView) view.findViewById(R.id.txt_my_score_value);
		gridQuestion = (GridView) view.findViewById(R.id.grid_question_no);
		btnEndTest = (Button) view.findViewById(R.id.btn_end_test);
		timerViewExam = (TimerView) view.findViewById(R.id.timer_exam);

		arrListMembers = TutorialGroupMember.getTutorialGroupMembers();
		adpTutorialGroup = new TutorialGroupAdapter(getActivity(), arrListMembers);
		lvTutorialGroup.setAdapter(adpTutorialGroup);

		txtGroupScore.setText(Html.fromHtml("<small><font color='#7B7B7B'>GROUP SCORE : </font></small><b><font color='#1BC4A2'>" + 5000 + "</font><b/>"));
		txtActiveComments.setText("55");
		txtMyScore.setText("2458");

		btnEndTest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				 endTest();
			}
		});

	}

	@Override
	public void startTest(ArrayList<QuestionObjective> questions, ExamFragment examFragment) {
		try {
			intExamDurationMilli = examFragment.getExamDurationMinutes() * 60 * 1000;
			timerViewExam.setTotalTimeMin(examFragment.getExamDurationMinutes());
			timerExam = new CountDownTimer( intExamDurationMilli, 1000) {
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
			fragExam = examFragment;
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
		}
	}

	private void end() {
		try {
			timerExam.cancel();
			for (int i = 0; i < arrListQuestions.size(); i++) {
				arrListQuestions.get(i).setIsReviewLater(false);
				arrListQuestions.get(i).setIsSkipped(false);
			}
			int timeSpent = (intExamDurationMilli - intTimeLeft) / 60000;
			fragExam.getFragmentManager().beginTransaction().replace(R.id.fl_exam, ResultFragment.newInstance(arrListQuestions, fragExam.isShowGraph(), timeSpent)).commit();
			getFragmentManager().beginTransaction().remove(this).commit();
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

}
