package com.ism.exam.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.exam.R;
import com.ism.exam.adapter.SubjectScoreAdapter;
import com.ism.exam.model.QuestionObjective;
import com.ism.exam.model.SubjectScore;

import java.util.ArrayList;

/**
 * Created by c161 on 15/10/2015.
 */
public class ResultFragment extends Fragment {

	private static final String TAG = ResultFragment.class.getSimpleName();

	private View view;

	private RelativeLayout rlResultGraph;
	private TextView txtScore;
	private TextView txtCorrectAnswers;
	private TextView txtIncorrectAnswers;
	private TextView txtUnattemptedAnswers;
	private TextView txtTotalTimeSpent;
	private Button btnViewAnswers;
	private Button btnYes;
	private Button btnNo;
	private Button btnViewEvaluation;
	private Button btnViewEvaluationGraph;
	private ListView lvGraph;

	private static final String SHOW_GRAPH = "showGraph";
	private boolean isShowGraph;

	private ArrayList<QuestionObjective> arrListQuestions;

	public static ResultFragment newInstance(ArrayList<QuestionObjective> questions, boolean showGraph) {
		ResultFragment fragment = new ResultFragment();
		fragment.setQuestion(questions);
		Bundle args = new Bundle();
		args.putBoolean(SHOW_GRAPH, showGraph);
		fragment.setArguments(args);
		return fragment;
	}

	public ResultFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			isShowGraph = getArguments().getBoolean(SHOW_GRAPH);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_result, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		rlResultGraph = (RelativeLayout) view.findViewById(R.id.rl_graph);
		txtScore = (TextView) view.findViewById(R.id.txt_score);
		txtCorrectAnswers = (TextView) view.findViewById(R.id.txt_correct_value);
		txtIncorrectAnswers = (TextView) view.findViewById(R.id.txt_incorrect_value);
		txtUnattemptedAnswers = (TextView) view.findViewById(R.id.txt_unattempted_value);
		txtTotalTimeSpent = (TextView) view.findViewById(R.id.txt_time_spent_value);
		btnViewAnswers = (Button) view.findViewById(R.id.btn_answers);
		btnYes = (Button) view.findViewById(R.id.btn_yes);
		btnNo = (Button) view.findViewById(R.id.btn_no);
		btnViewEvaluation = (Button) view.findViewById(R.id.btn_view_evaluation);
		btnViewEvaluationGraph = (Button) view.findViewById(R.id.btn_view_evaluation_graph);
		lvGraph = (ListView) view.findViewById(R.id.lv_result_graph);

		txtScore.setText(Html.fromHtml("<font color='#323941'>YOUR SCORE IS : </font><font color='#1BC4A2'>" + 75 + "%</font>"));

		calculateScore();

		btnViewAnswers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		btnYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		btnNo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		btnViewEvaluation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		btnViewEvaluationGraph.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		if (isShowGraph) {
			rlResultGraph.setVisibility(View.VISIBLE);
			SubjectScoreAdapter adpScore = new SubjectScoreAdapter(getActivity(), SubjectScore.getSubjectScore());
			lvGraph.setAdapter(adpScore);
		}

	}

	private void calculateScore() {
		try {
			int correct = 0;
			int incorrect = 0;
			int unattempted = 0;
			if (arrListQuestions != null) {
				for (int i = 0; i < arrListQuestions.size(); i++) {
					if (arrListQuestions.get(i).isAnswered()) {
						if (arrListQuestions.get(i).isCorrect()) {
							correct++;
						} else {
							incorrect++;
						}
					} else {
						unattempted++;
					}
				}
			}
			txtCorrectAnswers.setText(correct + "");
			txtIncorrectAnswers.setText(incorrect + "");
			txtUnattemptedAnswers.setText(unattempted + "");
		} catch (Exception e) {
			Log.e(TAG, "calculateScore Exception : " + e.toString());
		}
	}

	public void setQuestion(ArrayList<QuestionObjective> questions) {
		arrListQuestions = questions;
	}

}
