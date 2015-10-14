package com.ism.exam.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.exam.R;
import com.ism.exam.model.Option;
import com.ism.exam.model.QuestionObjective;

import java.util.ArrayList;

/**
 * Created by c161 on 13/10/15.
 */
public class ExamFragment extends Fragment {

	private static final String TAG = ExamFragment.class.getSimpleName();

	private View view;
	private RelativeLayout rlInstruction;
	private RelativeLayout rlQuestion;
	private TextView txtHeader;
	private TextView txtInstruct;
	private TextView txtSubject;
	private TextView txtQuestion;
	private RadioGroup rgOptions;
	private RadioButton rbOption1;
	private RadioButton rbOption2;
	private RadioButton rbOption3;
	private RadioButton rbOption4;
	private RadioButton rbOption5;
	private RadioButton rbOption6;
	private WebView wvInstructions;
	private Button btnStartTest;
	private Button btnReviewLater;
	private Button btnClearResponse;
	private Button btnSkip;
	private Button btnSaveNNext;

	private RadioButton rbOptions[];

	private ArrayList<QuestionObjective> arrListQuestions;

	private static final String ARG_PARAM1 = "param1";
	private String mParam1;
	private int intAssessmentNo;
	private String strSubject;
	private int intCurrentQuestionIndex = 0;

	public static ExamFragment newInstance(String param1) {
		ExamFragment fragment = new ExamFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		fragment.setArguments(args);
		return fragment;
	}

	public ExamFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_exam, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		rlInstruction = (RelativeLayout) view.findViewById(R.id.rl_instruction);
		rlQuestion = (RelativeLayout) view.findViewById(R.id.rl_question);
		txtHeader = (TextView) view.findViewById(R.id.txt_header);
		txtInstruct = (TextView) view.findViewById(R.id.txt_instruct);
		txtSubject = (TextView) view.findViewById(R.id.txt_subject);
		txtQuestion = (TextView) view.findViewById(R.id.txt_question);
		rgOptions = (RadioGroup) view.findViewById(R.id.rg_options);
		rbOption1 = (RadioButton) view.findViewById(R.id.rb_op1);
		rbOption2 = (RadioButton) view.findViewById(R.id.rb_op2);
		rbOption3 = (RadioButton) view.findViewById(R.id.rb_op3);
		rbOption4 = (RadioButton) view.findViewById(R.id.rb_op4);
		rbOption5 = (RadioButton) view.findViewById(R.id.rb_op5);
		rbOption6 = (RadioButton) view.findViewById(R.id.rb_op6);
		wvInstructions = (WebView) view.findViewById(R.id.wv_instruction);
		btnStartTest = (Button) view.findViewById(R.id.btn_start_test);
		btnReviewLater = (Button) view.findViewById(R.id.btn_review_later);
		btnClearResponse = (Button) view.findViewById(R.id.btn_clear_response);
		btnSkip = (Button) view.findViewById(R.id.btn_skip);
		btnSaveNNext = (Button) view.findViewById(R.id.btn_save_n_next);

		rbOptions = new RadioButton[]{rbOption1, rbOption2, rbOption3, rbOption4, rbOption5, rbOption6};

		txtHeader.setText(getString(R.string.read_instructions));
		txtInstruct.setText(getString(R.string.assessment_no) + intAssessmentNo);
		txtSubject.setText(strSubject);
		wvInstructions.loadUrl("");
		wvInstructions.getSettings().setJavaScriptEnabled(true);

		arrListQuestions = QuestionObjective.getQuestions();

		btnStartTest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rlInstruction.setVisibility(View.GONE);
				rlQuestion.setVisibility(View.VISIBLE);
				startTest();
			}
		});

		btnReviewLater.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				arrListQuestions.get(intCurrentQuestionIndex).setIsReviewLater(true);
				if (intCurrentQuestionIndex < arrListQuestions.size() - 1) {
					loadQuestion(++intCurrentQuestionIndex);
				} else {
					loadFirstFlaggedQuestion();
				}
			}
		});

		btnClearResponse.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		btnSkip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		btnSaveNNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (arrListQuestions.get(intCurrentQuestionIndex).isAnswered()
						&& intCurrentQuestionIndex < arrListQuestions.size() - 1) {
					loadQuestion(++intCurrentQuestionIndex);
				} else if (intCurrentQuestionIndex == arrListQuestions.size() - 1) {
					loadFirstFlaggedQuestion();
				}
			}
		});

	}

	private void loadFirstFlaggedQuestion() {
		for (int i = 0; i < arrListQuestions.size(); i++) {
			if (arrListQuestions.get(i).isReviewLater()) {
				loadQuestion(i);
				break;
			}
		}
	}

	private void startTest() {
		txtHeader.setText("01:00");
		txtInstruct.setText(R.string.choose_answer);
		loadQuestion(intCurrentQuestionIndex);
	}

	private void loadQuestion(int questionIndex) {
		try {
			intCurrentQuestionIndex = questionIndex;
			txtQuestion.setText(arrListQuestions.get(intCurrentQuestionIndex).getQuestion());
			ArrayList<Option> options = arrListQuestions.get(intCurrentQuestionIndex).getOptions();
			for (int i = 0; i < 6; i++) {
				if (i < options.size()) {
					rbOptions[i].setVisibility(View.VISIBLE);
					rbOptions[i].setText(options.get(i).getOption());
				} else {
					rbOptions[i].setVisibility(View.GONE);
				}
			}
			if (intCurrentQuestionIndex == arrListQuestions.size() - 1) {
				btnSaveNNext.setText(R.string.save);
			} else {
				btnSaveNNext.setText(R.string.save_n_next);
			}
		} catch (Exception e) {
			Log.e(TAG, "loadQuestion Exception : " + e.toString());
		}
	}

}
