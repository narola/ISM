package com.ism.fragment.tutorialGroup;

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

import com.ism.R;
import com.ism.adapter.QuestionPaletteAdapter;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.FridayExam;
import com.ism.ws.model.FridayExamAnswer;
import com.ism.ws.model.FridayExamQuestion;

import java.util.ArrayList;

/**
 * Created by c161 on 13/10/15.
 */
public class ExamFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

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

	private ExamListener listenerExam;
	private QuestionPaletteAdapter adpQuestionPalette;
//	private ArrayList<QuestionObjectiveTest> arrListQuestions;
	private FridayExam fridayExam;
	private ArrayList<FridayExamQuestion> arrListQuestions;

	private int intAssessmentNo;
	private String strSubject;
	private int intCurrentQuestionIndex = 0;
	private boolean isOptionsLoading = false;

	public interface ExamListener {
		public void startTest(ArrayList<FridayExamQuestion> questions, String examId, ExamFragment examFragment);
		public void onQuestionSet(int position);
	}

	public static ExamFragment newInstance(ExamListener examListener) {
		ExamFragment fragment = new ExamFragment();
		fragment.setListenerExam(examListener);
		return fragment;
	}

	public ExamFragment() {
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

		callApiGetFridayExamQuestions();

		txtHeader.setText(getString(R.string.read_instructions));
		txtInstruct.setText(getString(R.string.assessment_no) + intAssessmentNo);
		txtSubject.setText(strSubject);
		wvInstructions.loadUrl("");
		wvInstructions.getSettings().setJavaScriptEnabled(true);

//		arrListQuestions = QuestionObjectiveTest.getQuestions();

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
				arrListQuestions.get(intCurrentQuestionIndex).setIsSkipped(false);
				if (intCurrentQuestionIndex < arrListQuestions.size() - 1) {
					setQuestion(++intCurrentQuestionIndex);
				} else {
					if (listenerExam != null) {
						listenerExam.onQuestionSet(-1);
					}
				}
			}
		});

		btnClearResponse.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rgOptions.clearCheck();
			}
		});

		btnSkip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				arrListQuestions.get(intCurrentQuestionIndex).setIsSkipped(true);
				arrListQuestions.get(intCurrentQuestionIndex).setIsReviewLater(false);
				if (intCurrentQuestionIndex < arrListQuestions.size() - 1) {
					setQuestion(++intCurrentQuestionIndex);
				} else {
					if (listenerExam != null) {
						listenerExam.onQuestionSet(-1);
					}
				}
			}
		});

		btnSaveNNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				arrListQuestions.get(intCurrentQuestionIndex).setIsSkipped(false);
				arrListQuestions.get(intCurrentQuestionIndex).setIsReviewLater(false);
				if (intCurrentQuestionIndex < arrListQuestions.size() - 1) {
					setQuestion(++intCurrentQuestionIndex);
				} else if (intCurrentQuestionIndex == arrListQuestions.size() - 1) {
					if (listenerExam != null) {
						listenerExam.onQuestionSet(-1);
					}
				}
			}
		});

		rgOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (!isOptionsLoading) {
					boolean isChecked = false;
					switch (checkedId) {
						case R.id.rb_op1:
							isChecked = rbOption1.isChecked();
							saveAnswer(0, isChecked);
							break;
						case R.id.rb_op2:
							isChecked = rbOption2.isChecked();
							saveAnswer(1, isChecked);
							break;
						case R.id.rb_op3:
							isChecked = rbOption3.isChecked();
							saveAnswer(2, isChecked);
							break;
						case R.id.rb_op4:
							isChecked = rbOption4.isChecked();
							saveAnswer(3, isChecked);
							break;
						case R.id.rb_op5:
							isChecked = rbOption5.isChecked();
							saveAnswer(4, isChecked);
							break;
						case R.id.rb_op6:
							isChecked = rbOption6.isChecked();
							saveAnswer(5, isChecked);
							break;
					}
					arrListQuestions.get(intCurrentQuestionIndex).setIsAnswered(isChecked);
				}
			}
		});

	}

	private void callApiGetFridayExamQuestions() {
		try {
			Attribute attribute = new Attribute();
			attribute.setTutorialTopicId(PreferenceData.getStringPrefs(PreferenceData.TUTORIAL_TOPIC_ID, getActivity()));
			attribute.setGroupId(Global.strTutorialGroupId);

			new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.GET_FRIDAY_EXAM_QUESTIONS);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetFridayExamQuestions Exception : " + e.toString());
		}
	}

	private void saveAnswer(int optionPosition, boolean isChecked) {
		arrListQuestions.get(intCurrentQuestionIndex).getFridayExamAnswers().get(optionPosition).setIsSelected(isChecked);
		arrListQuestions.get(intCurrentQuestionIndex).setIsCorrect(
				arrListQuestions.get(intCurrentQuestionIndex).getFridayExamAnswers().get(optionPosition).isAnswer()
						&& arrListQuestions.get(intCurrentQuestionIndex).getFridayExamAnswers().get(optionPosition).isSelected());
	}

	private void startTest() {
//		txtHeader.setText(Utility.stringForTime(intExamDurationMinutes * 60 * 1000));
		txtHeader.setText(fridayExam.getExamName() + " - " + fridayExam.getId());
		txtInstruct.setText(R.string.choose_answer);
		if (listenerExam != null) {
			listenerExam.startTest(arrListQuestions, fridayExam.getId(), this);
		}
		setQuestion(intCurrentQuestionIndex);
	}

	public void setQuestion(int questionIndex) {
		try {
			intCurrentQuestionIndex = questionIndex;
			txtQuestion.setText(arrListQuestions.get(intCurrentQuestionIndex).getQuestionText());
			ArrayList<FridayExamAnswer> options = arrListQuestions.get(intCurrentQuestionIndex).getFridayExamAnswers();
			isOptionsLoading = true;
			rgOptions.clearCheck();
			for (int i = 0; i < 6; i++) {
				if (i < options.size()) {
					rbOptions[i].setVisibility(View.VISIBLE);
					rbOptions[i].setText(options.get(i).getChoiceText());
					rbOptions[i].setChecked(options.get(i).isSelected());
				} else {
					rbOptions[i].setVisibility(View.GONE);
				}
			}
			isOptionsLoading = false;
			if (intCurrentQuestionIndex == arrListQuestions.size() - 1) {
				btnSaveNNext.setText(R.string.save);
			} else {
				btnSaveNNext.setText(R.string.save_n_next);
			}
			if (listenerExam != null) {
				listenerExam.onQuestionSet(questionIndex);
			}
		} catch (Exception e) {
			Log.e(TAG, "setQuestion Exception : " + e.toString());
		}
	}

	public void setListenerExam(ExamListener examListener) {
		listenerExam = examListener;
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.GET_FRIDAY_EXAM_QUESTIONS:
					onResponseGetFridayExamQuestions(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Excepiton : " + e.toString());
		}
	}

	private void onResponseGetFridayExamQuestions(Object object, Exception error) {
		try {
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					fridayExam = responseHandler.getFridayExam().get(0);
					arrListQuestions = responseHandler.getFridayExam().get(0).getQuestions();
					btnStartTest.setEnabled(true);
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseGetFridayExamQuestions Failed message : " + responseHandler.getMessage());
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseGetFridayExamQuestions api Excepiton : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetFridayExamQuestions Excepiton : " + e.toString());
		}
	}

}
