package com.ism.exam.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.exam.R;
import com.ism.exam.model.QuestionObjective;

import java.util.ArrayList;

/**
 * Created by c161 on 15/10/2015.
 */
public class ResultFragment extends Fragment {

	private static final String TAG = ResultFragment.class.getSimpleName();

	private View view;

	private TextView txtScore;

	private ArrayList<QuestionObjective> arrListQuestions;

	public static ResultFragment newInstance(ArrayList<QuestionObjective> questions) {
		ResultFragment fragment = new ResultFragment();
		fragment.setQuestion(questions);
		return fragment;
	}

	public ResultFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_result, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		txtScore = (TextView) view.findViewById(R.id.txt_score);

		txtScore.setText(Html.fromHtml("<font color='#323941'>YOUR SCORE IS : </font><font color='#1BC4A2'>" + 75 + "%</font>"));

	}

	public void setQuestion(ArrayList<QuestionObjective> questions) {
		arrListQuestions = questions;
	}

}
