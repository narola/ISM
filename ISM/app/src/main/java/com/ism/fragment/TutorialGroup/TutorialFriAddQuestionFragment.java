package com.ism.fragment.tutorialGroup;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.Global;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import realmhelper.StudentHelper;

/**
 * Created by c161 on 16/12/15.
 */
public class TutorialFriAddQuestionFragment extends Fragment {

	private static final String TAG = TutorialFriAddQuestionFragment.class.getSimpleName();

	private View view;
	private RelativeLayout rlWaiting, rlHeader, rlTutorialmateQuestion, rlCreateQuestion, rlSetOptions, rlPreviewQuestion, rlUpload;
	private LinearLayout llOptions;
	private TextView txtHelp, txtCreateQuestion, txtSetOptions, txtPreviewQuestion, txtUpload, txtQuestion;
	private EditText etQuestion, etOption1, etOption2, etOption3, etOption4;
	private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
	private Button btnUploadAndFreeze;

	private ExamFragment.ExamListener listenerExam;
	private View.OnClickListener onClickLable;

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

		txtLables = new TextView[] {txtCreateQuestion, txtSetOptions, txtPreviewQuestion, txtUpload};
		viewLayouts = new RelativeLayout[] {rlCreateQuestion, rlSetOptions, rlPreviewQuestion, rlUpload};

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
				getFragmentManager().beginTransaction().replace(R.id.fl_tutorial, ExamFragment.newInstance(listenerExam, 0, false)).commit();
			}
		});

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

}
