package com.ism.exam.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ism.exam.R;
import com.ism.exam.adapter.QuestionPaletteAdapter;
import com.ism.exam.model.QuestionObjective;

import java.util.ArrayList;

/**
 * Created by c161 on 14/10/15.
 */
public class QuestionPaletteFragment extends Fragment implements ExamFragment.ExamListener {

	private static final String TAG = QuestionPaletteFragment.class.getSimpleName();

	private View view;
	private GridView gridQuestion;

	private QuestionPaletteAdapter adpQuestionPalette;
	private ArrayList<QuestionObjective> arrListQuestions;

	public static QuestionPaletteFragment newInstance() {
		QuestionPaletteFragment fragment = new QuestionPaletteFragment();
		return fragment;
	}

	public QuestionPaletteFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_question_palette, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		gridQuestion = (GridView) view.findViewById(R.id.grid_question_no);
	}

	@Override
	public void questionsFetched(ArrayList<QuestionObjective> questions, ExamFragment examFragment) {
		arrListQuestions = questions;
		adpQuestionPalette = new QuestionPaletteAdapter(getActivity(), arrListQuestions, examFragment);
		gridQuestion.setAdapter(adpQuestionPalette);
	}

	@Override
	public void onQuestionSet(int position) {
		if (adpQuestionPalette != null) {
			adpQuestionPalette.setQuestion(position);
		}
	}

}
