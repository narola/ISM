package com.ism.exam.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.exam.R;
import com.ism.exam.model.QuestionObjective;

import java.util.ArrayList;

/**
 * Created by c161 on 15/10/2015.
 */
public class ResultFragment extends Fragment {

	private static final String TAG = ResultFragment.class.getSimpleName();

	private View view;

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

	}

	public void setQuestion(ArrayList<QuestionObjective> questions) {
		arrListQuestions = questions;
	}

}
