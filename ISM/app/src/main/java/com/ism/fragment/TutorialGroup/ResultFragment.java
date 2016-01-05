package com.ism.fragment.tutorialGroup;

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

import com.ism.R;
import com.ism.adapter.SubjectScoreAdapter;
import com.ism.constant.WebConstants;
import com.ism.model.QuestionObjectiveTest;
import com.ism.model.SubjectScoreTest;
import com.ism.object.Global;
import com.ism.utility.PreferenceData;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.FridayExamQuestion;
import com.ism.ws.model.Question;

import java.util.ArrayList;

/**
 * Created by c161 on 15/10/2015.
 */
public class ResultFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

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
	private Button btnViewEvaluationGraph;
	private ListView lvGraph;

	private static final String ARG_SHOW_GRAPH = "showGraph";
	private static final String ARG_TIME_SPENT = "timeSpent";
	private static final String ARG_EXAM_ID = "examId";
	private boolean isShowGraph;
	private int intTimeSpent;
	private String strExamId;

	private ArrayList<FridayExamQuestion> arrListQuestions;

	public static ResultFragment newInstance(ArrayList<FridayExamQuestion> questions, String examId, boolean showGraph, int timeSpent) {
		ResultFragment fragment = new ResultFragment();
		fragment.setQuestion(questions);
		Bundle args = new Bundle();
		args.putBoolean(ARG_SHOW_GRAPH, showGraph);
		args.putInt(ARG_TIME_SPENT, timeSpent);
		args.putString(ARG_EXAM_ID, examId);
		fragment.setArguments(args);
		return fragment;
	}

	public ResultFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			isShowGraph = getArguments().getBoolean(ARG_SHOW_GRAPH);
			intTimeSpent = getArguments().getInt(ARG_TIME_SPENT);
			strExamId = getArguments().getString(ARG_EXAM_ID);
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
		btnViewEvaluationGraph = (Button) view.findViewById(R.id.btn_view_evaluation_graph);
		lvGraph = (ListView) view.findViewById(R.id.lv_result_graph);

		txtScore.setText(Html.fromHtml("<font color='#323941'>" + getString(R.string.your_score_is) + "</font><font color='#1BC4A2'>" + 75 + "%</font>"));

		callApiSubmitStudentObjectiveResponse();
//		calculateScore();
		txtTotalTimeSpent.setText(intTimeSpent + " min");

		btnViewAnswers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getFragmentManager().beginTransaction().replace(R.id.fl_tutorial, com.ism.fragment.tutorialGroup.ExamEvaluationFragment.newInstance()).commit();
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

		btnViewEvaluationGraph.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		if (isShowGraph) {
			rlResultGraph.setVisibility(View.VISIBLE);
			SubjectScoreAdapter adpScore = new SubjectScoreAdapter(getActivity(), SubjectScoreTest.getSubjectScore());
			lvGraph.setAdapter(adpScore);
		}

	}

	private void callApiSubmitStudentObjectiveResponse() {
		try {
			Attribute attribute = new Attribute();
			attribute.setExamId(Integer.parseInt(strExamId));
			attribute.setUserId(Global.strUserId);

			ArrayList<Question> questions = new ArrayList<>();
			for (int i = 0; i < arrListQuestions.size(); i++) {
				Question question = new Question();
				question.setQuestionId(Integer.parseInt(arrListQuestions.get(i).getQuestionId()));

				for (int j = 0; j < arrListQuestions.get(i).getFridayExamAnswers().size(); j++) {
					if (arrListQuestions.get(i).getFridayExamAnswers().get(j).isSelected()) {
						question.setChoiceId(Integer.parseInt(arrListQuestions.get(i).getFridayExamAnswers().get(i).getId()));
						break;
					}
				}

				question.setAnswerStatus(arrListQuestions.get(i).isAnswered() ? "A"
						: arrListQuestions.get(i).isReviewLater() ? "R" : arrListQuestions.get(i).isSkipped() ? "S" : "N");
				question.setCorrectAnswerScore(1);
				question.setIsRight(arrListQuestions.get(i).isCorrect() ? 1 : 0);
				question.setResponseDuration(0);

				questions.add(question);
			}
			attribute.setQuestion(questions);

			new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(
					WebConstants.SUBMIT_STUDENT_OBJECTIVE_RESPONSE);
		} catch (Exception e) {
			Log.e(TAG, "callApiSubmitStudentObjectiveResponse Exception : " + e.toString());
		}
	}

	/*private void calculateScore() {
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
	}*/

	public void setQuestion(ArrayList<FridayExamQuestion> questions) {
		arrListQuestions = questions;
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.SUBMIT_STUDENT_OBJECTIVE_RESPONSE:
					onResponseSubmitStudentObjectiveResponse(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Excepiton : " + e.toString());
		}
	}

	private void onResponseSubmitStudentObjectiveResponse(Object object, Exception error) {
		try {
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {

				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseSubmitStudentObjectiveResponse Failed message : " + responseHandler.getMessage());
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseSubmitStudentObjectiveResponse api Excepiton : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseSubmitStudentObjectiveResponse Excepiton : " + e.toString());
		}
	}
}
