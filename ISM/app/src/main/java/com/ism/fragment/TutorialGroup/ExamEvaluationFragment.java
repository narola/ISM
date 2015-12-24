package com.ism.fragment.TutorialGroup;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.Global;
import com.ism.views.CircleImageView;

/**
 * Created by c161 on 21/12/15.
 */
public class ExamEvaluationFragment extends Fragment {

	private static final String TAG = ExamEvaluationFragment.class.getSimpleName();

	private View view;
	private TextView txtHeaderQuestion, txtQuestion, txtYourAnswer, txtCorrectAnswer, txtSolutionContent, txtTutorialmateName1,
			txtAnsweredOption1, txtTutorialmateName2, txtAnsweredOption2, txtTutorialmateName3, txtAnsweredOption3,
			txtTutorialmateName4, txtAnsweredOption4, txtTutorialmateName5, txtAnsweredOption5;
	private CircleImageView imgTutorialmateDp1, imgTutorialmateDp2, imgTutorialmateDp3, imgTutorialmateDp4, imgTutorialmateDp5;
	private ImageView imgPrevious, imgNext;

	public static ExamEvaluationFragment newInstance() {
		ExamEvaluationFragment fragment = new ExamEvaluationFragment();
		return fragment;
	}

	public ExamEvaluationFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_exam_evaluation, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		txtHeaderQuestion = (TextView) view.findViewById(R.id.txt_header_question);
		txtQuestion = (TextView) view.findViewById(R.id.txt_question);
		txtYourAnswer = (TextView) view.findViewById(R.id.txt_your_answer);
		txtCorrectAnswer = (TextView) view.findViewById(R.id.txt_correct_answer);
		txtSolutionContent = (TextView) view.findViewById(R.id.txt_solution_content);
		txtTutorialmateName1 = (TextView) view.findViewById(R.id.txt_tutorialmate_name1);
		txtAnsweredOption1 = (TextView) view.findViewById(R.id.txt_answered_option1);
		txtTutorialmateName2 = (TextView) view.findViewById(R.id.txt_tutorialmate_name2);
		txtAnsweredOption2 = (TextView) view.findViewById(R.id.txt_answered_option2);
		txtTutorialmateName3 = (TextView) view.findViewById(R.id.txt_tutorialmate_name3);
		txtAnsweredOption3 = (TextView) view.findViewById(R.id.txt_answered_option3);
		txtTutorialmateName4 = (TextView) view.findViewById(R.id.txt_tutorialmate_name4);
		txtAnsweredOption4 = (TextView) view.findViewById(R.id.txt_answered_option4);
		txtTutorialmateName5 = (TextView) view.findViewById(R.id.txt_tutorialmate_name5);
		txtAnsweredOption5 = (TextView) view.findViewById(R.id.txt_answered_option5);
		imgTutorialmateDp1 = (CircleImageView) view.findViewById(R.id.img_tutorialmate_dp1);
		imgTutorialmateDp2 = (CircleImageView) view.findViewById(R.id.img_tutorialmate_dp2);
		imgTutorialmateDp3 = (CircleImageView) view.findViewById(R.id.img_tutorialmate_dp3);
		imgTutorialmateDp4 = (CircleImageView) view.findViewById(R.id.img_tutorialmate_dp4);
		imgTutorialmateDp5 = (CircleImageView) view.findViewById(R.id.img_tutorialmate_dp5);
		imgPrevious = (ImageView) view.findViewById(R.id.img_previous);
		imgNext = (ImageView) view.findViewById(R.id.img_next);

		txtHeaderQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtYourAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtCorrectAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtSolutionContent.setTypeface(Global.myTypeFace.getRalewayRegular());
		((TextView) view.findViewById(R.id.txt_solution)).setTypeface(Global.myTypeFace.getRalewaySemiBold());
		txtTutorialmateName1.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtAnsweredOption1.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtTutorialmateName2.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtAnsweredOption2.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtTutorialmateName3.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtAnsweredOption3.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtTutorialmateName4.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtAnsweredOption4.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtTutorialmateName5.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtAnsweredOption5.setTypeface(Global.myTypeFace.getRalewayRegular());

	}

}