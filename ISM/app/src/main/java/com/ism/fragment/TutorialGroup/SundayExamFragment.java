package com.ism.fragment.tutorialGroup;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.WebConstants;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.PreferenceData;
import com.ism.views.CircleImageView;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.Exam;
import com.ism.ws.model.ExamAnswer;

import java.util.ArrayList;

public class SundayExamFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

	private static final String TAG = SundayExamFragment.class.getSimpleName();

	private View view, viewCover1, viewCover2;
	private RecyclerView recyclerDiscussion;
	private CircleImageView imgDpMember1, imgDpMember2, imgDpMember3, imgDpMember4, imgDpMember5;
	private ImageView imgAttachFile, imgAttachImage, imgAttachAudio, imgAttachLink;
	private EditText etMessage;
	private Button btnSend, btnAgree, btnDisagree, btnFinalizeAnswer;
	private TextView txtQuestionTitle, txtQuestion, txtInstruct, txtNameMember1, txtNameMember2, txtNameMember3, txtNameMember4, txtNameMember5;
	private RadioGroup rgOptions;
	private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;

	private TutorialFragment fragTutorial;
	private FragmentListener listenerFragment;
	private Exam exam;

	private int intCurrentQuestionIndex = 0;
	private boolean isOptionsLoading = false;

	public static SundayExamFragment newInstance() {
		SundayExamFragment fragment = new SundayExamFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_sunday_exam, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		viewCover1 = view.findViewById(R.id.view_cover1);
		viewCover2 = view.findViewById(R.id.view_cover2);
		recyclerDiscussion = (RecyclerView) view.findViewById(R.id.recycler_discussion);
		imgDpMember1 = (CircleImageView) view.findViewById(R.id.img_dp_member1);
		imgDpMember2 = (CircleImageView) view.findViewById(R.id.img_dp_member2);
		imgDpMember3 = (CircleImageView) view.findViewById(R.id.img_dp_member3);
		imgDpMember4 = (CircleImageView) view.findViewById(R.id.img_dp_member4);
		imgDpMember5 = (CircleImageView) view.findViewById(R.id.img_dp_member5);
		imgAttachFile = (ImageView) view.findViewById(R.id.img_attach_file);
		imgAttachImage = (ImageView) view.findViewById(R.id.img_attach_image);
		imgAttachAudio = (ImageView) view.findViewById(R.id.img_attach_audio);
		imgAttachLink = (ImageView) view.findViewById(R.id.img_attach_link);
		etMessage = (EditText) view.findViewById(R.id.et_message);
		btnSend = (Button) view.findViewById(R.id.btn_send);
		btnAgree = (Button) view.findViewById(R.id.btn_agree);
		btnDisagree = (Button) view.findViewById(R.id.btn_disagree);
		btnFinalizeAnswer = (Button) view.findViewById(R.id.btn_finalize_answer);
		txtQuestionTitle = (TextView) view.findViewById(R.id.txt_question_title);
		txtQuestion = (TextView) view.findViewById(R.id.txt_question);
		txtInstruct = (TextView) view.findViewById(R.id.txt_msg);
		txtNameMember1 = (TextView) view.findViewById(R.id.txt_name_member1);
		txtNameMember2 = (TextView) view.findViewById(R.id.txt_name_member2);
		txtNameMember3 = (TextView) view.findViewById(R.id.txt_name_member3);
		txtNameMember4 = (TextView) view.findViewById(R.id.txt_name_member4);
		txtNameMember5 = (TextView) view.findViewById(R.id.txt_name_member5);
		rgOptions = (RadioGroup) view.findViewById(R.id.rg_options);
		rbOption1 = (RadioButton) view.findViewById(R.id.rb_op1);
		rbOption2 = (RadioButton) view.findViewById(R.id.rb_op2);
		rbOption3 = (RadioButton) view.findViewById(R.id.rb_op3);
		rbOption4 = (RadioButton) view.findViewById(R.id.rb_op4);

		enableDiscussion(false);
		enableExam(true);

		etMessage.setTypeface(Global.myTypeFace.getRalewayRegular());
		btnSend.setTypeface(Global.myTypeFace.getRalewayRegular());
		btnAgree.setTypeface(Global.myTypeFace.getRalewayRegular());
		btnDisagree.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtQuestionTitle.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtInstruct.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtNameMember1.setTypeface(Global.myTypeFace.getRalewaySemiBold());
		txtNameMember2.setTypeface(Global.myTypeFace.getRalewaySemiBold());
		txtNameMember3.setTypeface(Global.myTypeFace.getRalewaySemiBold());
		txtNameMember4.setTypeface(Global.myTypeFace.getRalewaySemiBold());
		txtNameMember5.setTypeface(Global.myTypeFace.getRalewaySemiBold());
		rbOption1.setTypeface(Global.myTypeFace.getRalewayRegular());
		rbOption2.setTypeface(Global.myTypeFace.getRalewayRegular());
		rbOption3.setTypeface(Global.myTypeFace.getRalewayRegular());
		rbOption4.setTypeface(Global.myTypeFace.getRalewayRegular());
		((TextView) view.findViewById(R.id.txt_options)).setTypeface(Global.myTypeFace.getRalewaySemiBold());
		((TextView) view.findViewById(R.id.txt_header)).setTypeface(Global.myTypeFace.getRalewayRegular());
		((TextView) view.findViewById(R.id.txt_discussion_title)).setTypeface(Global.myTypeFace.getRalewayRegular());

		callApiGetSundayExamQuestion();

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
					}
					exam.getQuestions().get(intCurrentQuestionIndex).setIsAnswered(isChecked);
					if (intCurrentQuestionIndex < exam.getQuestions().size() - 1) {
						setQuestion(++intCurrentQuestionIndex);
					}
				}
			}
		});

		btnSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enableDiscussion(false);
				enableExam(true);
			}
		});

		btnFinalizeAnswer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enableDiscussion(true);
				enableExam(false);
			}
		});

	}

	private void enableDiscussion(boolean enable) {
		viewCover1.setVisibility(enable ? View.GONE : View.VISIBLE);
		etMessage.setEnabled(enable);
		btnSend.setEnabled(enable);
		imgAttachAudio.setEnabled(enable);
		imgAttachFile.setEnabled(enable);
		imgAttachImage.setEnabled(enable);
		imgAttachLink.setEnabled(enable);
	}

	private void enableExam(boolean enable) {
		viewCover2.setVisibility(enable ? View.GONE : View.VISIBLE);
		for (int i = 0; i < rgOptions.getChildCount(); i++) {
			rgOptions.getChildAt(i).setEnabled(enable);
		}
		btnAgree.setEnabled(enable);
		btnDisagree.setEnabled(enable);
		btnFinalizeAnswer.setEnabled(enable);
	}

	private void saveAnswer(int optionPosition, boolean isChecked) {
		Log.e(TAG, "saveAnswer questionIndex : " + intCurrentQuestionIndex);
		exam.getQuestions().get(intCurrentQuestionIndex).getExamAnswers().get(optionPosition).setIsSelected(isChecked);
		exam.getQuestions().get(intCurrentQuestionIndex).setIsCorrect(
				exam.getQuestions().get(intCurrentQuestionIndex).getExamAnswers().get(optionPosition).isAnswer()
						&& exam.getQuestions().get(intCurrentQuestionIndex).getExamAnswers().get(optionPosition).isSelected());
	}

	private void callApiGetSundayExamQuestion() {
		try {
			Attribute attribute = new Attribute();
			attribute.setExamId(135);

			new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.GET_SUNDAY_EXAM_QUESTION);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetSundayExamQuestion Exception : " + e.toString());
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		listenerFragment = (FragmentListener) activity;
		fragTutorial = (TutorialFragment) getParentFragment();
		if (listenerFragment != null) {
			listenerFragment.onFragmentAttached(HostActivity.FRAGMENT_SUNDAY_EXAM);
		}

		if (fragTutorial != null) {
			fragTutorial.updateLayoutForSundayExam(true);
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		if (listenerFragment != null) {
			listenerFragment.onFragmentDetached(HostActivity.FRAGMENT_SUNDAY_EXAM);
		}

		if (fragTutorial != null) {
			fragTutorial.updateLayoutForSundayExam(false);
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.GET_SUNDAY_EXAM_QUESTION:
					onResponseGetSundayExamQuestion(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseGetSundayExamQuestion(Object object, Exception error) {
		try {
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					exam = responseHandler.getExam().get(0);
					setQuestion(intCurrentQuestionIndex);
				} else if(responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseGetSundayExamQuestion failed message : " + responseHandler.getMessage());
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseGetSundayExamQuestion api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetSundayExamQuestion Exception : " + e.toString());
		}
	}

	private void setQuestion(int questionIndex) {
		try {
			txtQuestionTitle.setText(getString(R.string.question) + (questionIndex + 1));
			txtQuestion.setText(exam.getQuestions().get(questionIndex).getQuestionText());
			ArrayList<ExamAnswer> options = exam.getQuestions().get(questionIndex).getExamAnswers();
			isOptionsLoading = true;
			rgOptions.clearCheck();
			for (int i = 0; i < 4; i++) {
				if (i < options.size()) {
					rgOptions.getChildAt(i).setVisibility(View.VISIBLE);
					((RadioButton) rgOptions.getChildAt(i)).setText(options.get(i).getChoiceText());
				} else {
					rgOptions.getChildAt(i).setVisibility(View.GONE);
				}
			}
			isOptionsLoading = false;
			Log.e(TAG, "isOptionLoading : " + isOptionsLoading);
		} catch (Exception e) {
			Log.e(TAG, "setQuestion Exception : " + e.toString());
		}
	}

}