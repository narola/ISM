package com.ism.fragment.tutorialGroup;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.utility.InputValidator;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.AnswerChoice;

import java.util.ArrayList;

/**
 * Created by c161 on 16/12/15.
 */
public class TutorialFriAddQuestionFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

	private static final String TAG = TutorialFriAddQuestionFragment.class.getSimpleName();

	private View view;
	private RelativeLayout rlWaiting, rlHeader, rlTutorialmateQuestion, rlCreateQuestion, rlSetOptions, rlPreviewQuestion, rlUpload;
	private LinearLayout llOptions;
	private TextView txtHelp, txtCreateQuestion, txtSetOptions, txtPreviewQuestion, txtUpload, txtQuestion;
	private EditText etQuestion, etOption1, etOption2, etOption3, etOption4;
	private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
	private Button btnUploadAndFreeze;

	private EditText[] etOptions;
	private RadioButton[] rbOptions;

	private ExamFragment.ExamListener listenerExam;
	private View.OnClickListener onClickLable;
	private InputValidator inputValidator;

	private TextView[] txtLables;
	private RelativeLayout[] viewLayouts;

	private boolean isExpanded = false;

	public static TutorialFriAddQuestionFragment newInstance(ExamFragment.ExamListener listenerExam) {
		TutorialFriAddQuestionFragment fragment = new TutorialFriAddQuestionFragment();
		fragment.setExamListener(listenerExam);
		return fragment;
	}

	private void setExamListener(ExamFragment.ExamListener examListener) {
		listenerExam = examListener;
	}

	public TutorialFriAddQuestionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_tutorial_fri_add_question, container, false);

		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		txtHelp = (TextView) view.findViewById(R.id.txt_help);
		rlWaiting = (RelativeLayout) view.findViewById(R.id.rl_waiting);
		rlHeader = (RelativeLayout) view.findViewById(R.id.rl_header);
		rlTutorialmateQuestion = (RelativeLayout) view.findViewById(R.id.rl_tutorialmate_question);
		txtCreateQuestion = (TextView) view.findViewById(R.id.txt_create_question);
		txtSetOptions = (TextView) view.findViewById(R.id.txt_set_options);
		txtPreviewQuestion = (TextView) view.findViewById(R.id.txt_preview_question);
		txtUpload = (TextView) view.findViewById(R.id.txt_upload);
		txtQuestion = (TextView) view.findViewById(R.id.txt_question);
		btnUploadAndFreeze = (Button) view.findViewById(R.id.btn_upload);
		etQuestion = (EditText) view.findViewById(R.id.et_question);
		etOption1 = (EditText) view.findViewById(R.id.et_option1);
		etOption2 = (EditText) view.findViewById(R.id.et_option2);
		etOption3 = (EditText) view.findViewById(R.id.et_option3);
		etOption4 = (EditText) view.findViewById(R.id.et_option4);
		rbOption1 = (RadioButton) view.findViewById(R.id.rb_option1);
		rbOption2 = (RadioButton) view.findViewById(R.id.rb_option2);
		rbOption3 = (RadioButton) view.findViewById(R.id.rb_option3);
		rbOption4 = (RadioButton) view.findViewById(R.id.rb_option4);
		llOptions = (LinearLayout) view.findViewById(R.id.ll_options);
		rlCreateQuestion = (RelativeLayout) view.findViewById(R.id.rl_create_question);
		rlSetOptions = (RelativeLayout) view.findViewById(R.id.rl_set_options);
		rlPreviewQuestion = (RelativeLayout) view.findViewById(R.id.rl_preview_question);
		rlUpload = (RelativeLayout) view.findViewById(R.id.rl_upload);

		etOptions = new EditText[]{etOption1, etOption2, etOption3, etOption4};
		rbOptions = new RadioButton[]{rbOption1, rbOption2, rbOption3, rbOption4};

		((TextView) view.findViewById(R.id.txt_header)).setTypeface(Global.myTypeFace.getRalewayRegular());
		txtHelp.setTypeface(Global.myTypeFace.getRalewayRegular());
		((TextView) view.findViewById(R.id.txt_title)).setTypeface(Global.myTypeFace.getRalewaySemiBold());
		txtCreateQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtSetOptions.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtPreviewQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtUpload.setTypeface(Global.myTypeFace.getRalewayRegular());
		btnUploadAndFreeze.setTypeface(Global.myTypeFace.getRalewaySemiBold());
		((TextView) view.findViewById(R.id.txt_create_question)).setTypeface(Global.myTypeFace.getRalewayRegular());
		etQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());
		((TextView) view.findViewById(R.id.txt_set_options_title)).setTypeface(Global.myTypeFace.getRalewayRegular());
		etOption1.setTypeface(Global.myTypeFace.getRalewayRegular());
		etOption2.setTypeface(Global.myTypeFace.getRalewayRegular());
		etOption3.setTypeface(Global.myTypeFace.getRalewayRegular());
		etOption4.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtQuestion.setTypeface(Global.myTypeFace.getRalewaySemiBold());
		((TextView) view.findViewById(R.id.txt_options)).setTypeface(Global.myTypeFace.getRalewaySemiBold());
		rbOption1.setTypeface(Global.myTypeFace.getRalewayRegular());
		rbOption2.setTypeface(Global.myTypeFace.getRalewayRegular());
		rbOption3.setTypeface(Global.myTypeFace.getRalewayRegular());
		rbOption4.setTypeface(Global.myTypeFace.getRalewayRegular());
		((TextView) view.findViewById(R.id.txt_message)).setTypeface(Global.myTypeFace.getRalewaySemiBold());
		((TextView) view.findViewById(R.id.txt_message2)).setTypeface(Global.myTypeFace.getRalewaySemiBold());

		txtLables = new TextView[] {txtCreateQuestion, txtSetOptions, txtPreviewQuestion, txtUpload};
		viewLayouts = new RelativeLayout[] {rlCreateQuestion, rlSetOptions, rlPreviewQuestion, rlUpload};

		inputValidator = new InputValidator(getActivity());

//		rlHeader.setVisibility(View.GONE);
//		rlTutorialmateQuestion.setVisibility(View.GONE);
//		rlWaiting.setVisibility(View.VISIBLE);

		onClickLable = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.txt_create_question:
						showForm(rlCreateQuestion);
						highlightLable(txtCreateQuestion);
						break;
					case R.id.txt_set_options:
						showForm(rlSetOptions);
						highlightLable(txtSetOptions);
						break;
					case R.id.txt_preview_question:
						showForm(rlPreviewQuestion);
						highlightLable(txtPreviewQuestion);
						break;
					case R.id.txt_upload:
						showForm(rlUpload);
						highlightLable(txtUpload);
						break;
				}
			}
		};

		txtHelp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		txtCreateQuestion.setOnClickListener(onClickLable);
		txtSetOptions.setOnClickListener(onClickLable);
		txtPreviewQuestion.setOnClickListener(onClickLable);
		txtUpload.setOnClickListener(onClickLable);

		txtCreateQuestion.setText(Html.fromHtml(getString(R.string.step1) + getString(R.string.create_question)));
		txtSetOptions.setText(Html.fromHtml(getString(R.string.step2) + getString(R.string.set_options)));
		txtPreviewQuestion.setText(Html.fromHtml(getString(R.string.step3) + getString(R.string.preview_question)));
		txtUpload.setText(Html.fromHtml(getString(R.string.step4) + getString(R.string.upload)));

		btnUploadAndFreeze.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (inputsValid()) {
					callApiSubmitQuestionForFriday();
					getFragmentManager().beginTransaction().replace(R.id.fl_tutorial, ExamFragment.newInstance(listenerExam, 0, false)).commit();
				}
			}
		});

	}

	private void callApiSubmitQuestionForFriday() {
		try {

			/*
			"group_id":59,
			"user_id":1,
			"tutorial_topic_id":4,
			"question_text":"Test Virtual Java Question",
			"answer_choices":[
								{
									"choice_text" :"java1",
										"is_right":1
								},
								{
									"choice_text" : "java3",
										"is_right":0
								}
							]
							*/

			Attribute attribute = new Attribute();
			attribute.setGroupId(Global.strTutorialGroupId);
			attribute.setUserId(Global.strUserId);
			attribute.setTutorialTopicId("");
			attribute.setQuestionText(etQuestion.getText().toString().trim());
			ArrayList<AnswerChoice> answerChoices = new ArrayList<>();
			for (int i = 0; i < 4; i++) {
				AnswerChoice answerChoice = new AnswerChoice();
				answerChoice.setChoiceText(etOptions[i].getText().toString().trim());
				answerChoice.setIsRight(rbOptions[i].isChecked() ? 1 : 0);
				answerChoices.add(answerChoice);
			}
			attribute.setAnswerChoices(answerChoices);

			new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
					.execute(WebConstants.SUBMIT_QUESTION_FOR_FRIDAY);
		} catch (Exception e) {
			Log.e(TAG, "callApiSubmitQuestionForFriday Exception : " + e.toString());
		}
	}

	private boolean inputsValid() {
		return inputValidator.validateStringPresence(etQuestion)
				& inputValidator.validateStringPresence(etOption1)
				& inputValidator.validateStringPresence(etOption2)
				& inputValidator.validateStringPresence(etOption3)
				& inputValidator.validateStringPresence(etOption4)
				& isAnswerSelected();
	}

	private boolean isAnswerSelected() {
		boolean valid = rbOption1.isChecked() || rbOption2.isChecked() || rbOption3.isChecked() || rbOption4.isChecked();
		if (!valid) {
			Utility.alert(getActivity(), getString(R.string.title_select_answer), getString(R.string.msg_select_answer));
		}
		return valid;
	}

	private void showForm(RelativeLayout layoutForm) {
		if (!isExpanded && layoutForm == viewLayouts[0]) {
			rlTutorialmateQuestion.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
			rlTutorialmateQuestion.getLayoutParams().width = RelativeLayout.LayoutParams.MATCH_PARENT;
			llOptions.getLayoutParams().width = 250;

			llOptions.bringToFront();
			viewLayouts[0].startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right));
			viewLayouts[0].setVisibility(View.VISIBLE);

			isExpanded = true;
		} else if (isExpanded) {
			for (int i = 0; i < viewLayouts.length; i++) {
				if (viewLayouts[i] == layoutForm) {
					viewLayouts[i].setVisibility(View.VISIBLE);
				} else {
					viewLayouts[i].setVisibility(View.GONE);
				}
			}
		}
	}

	private void highlightLable(TextView textView) {
		if (isExpanded) {
			for (int i = 0; i < txtLables.length; i++) {
				if (txtLables[i] == textView) {
					txtLables[i].setBackgroundResource(R.color.border_gray);
				} else {
					txtLables[i].setBackgroundResource(R.drawable.bg_white_border_gray_bottom);
				}
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.SUBMIT_QUESTION_FOR_FRIDAY:
					onResponseSubmitQuestionForFriday(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseSubmitQuestionForFriday(Object object, Exception error) {
		try {
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus() == WebConstants.SUCCESS) {
					Log.e(TAG, "question added successfully.");
				} else if (responseHandler.getStatus() == WebConstants.FAILED) {
					Log.e(TAG, "question failed to be added.");
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseSubmitQuestionForFriday api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseSubmitQuestionForFriday Exception : " + e.toString());
		}
	}

}
