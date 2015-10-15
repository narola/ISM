package com.ism.exam.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.exam.R;
import com.ism.exam.adapter.QuestionPaletteAdapter;
import com.ism.exam.adapter.TutorialGroupAdapter;
import com.ism.exam.model.QuestionObjective;
import com.ism.exam.model.TutorialGroupMember;

import java.util.ArrayList;

/**
 * Created by c161 on 14/10/15.
 */
public class QuestionPaletteFragment extends Fragment implements ExamFragment.ExamListener {

	private static final String TAG = QuestionPaletteFragment.class.getSimpleName();

	private View view;
	private RelativeLayout rlQuestionPalette;
	private ListView lvTutorialGroup;
	private TextView txtTitle;
	private GridView gridQuestion;
	private Button btnEndTest;

	private ExamFragment fragExam;
	private QuestionPaletteAdapter adpQuestionPalette;
	private ArrayList<QuestionObjective> arrListQuestions;
	private TutorialGroupAdapter adpTutorialGroup;
	private ArrayList<TutorialGroupMember> arrListMembers;

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
		rlQuestionPalette = (RelativeLayout) view.findViewById(R.id.rl_question_palette);
		lvTutorialGroup = (ListView) view.findViewById(R.id.lv_tutorial_group);
		txtTitle = (TextView) view.findViewById(R.id.txt_title);
		gridQuestion = (GridView) view.findViewById(R.id.grid_question_no);
		btnEndTest = (Button) view.findViewById(R.id.btn_end_test);

		arrListMembers = TutorialGroupMember.getTutorialGroupMembers();
		adpTutorialGroup = new TutorialGroupAdapter(getActivity(), arrListMembers);
		lvTutorialGroup.setAdapter(adpTutorialGroup);

		btnEndTest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				fragExam.endTest();
			}
		});

	}

	@Override
	public void startTest(ArrayList<QuestionObjective> questions, ExamFragment examFragment) {
		try {
			fragExam = examFragment;
			lvTutorialGroup.setVisibility(View.GONE);
			rlQuestionPalette.setVisibility(View.VISIBLE);
			arrListQuestions = questions;
			adpQuestionPalette = new QuestionPaletteAdapter(getActivity(), arrListQuestions, examFragment);
			gridQuestion.setAdapter(adpQuestionPalette);
			txtTitle.setText(R.string.question_palette);
		} catch (Exception e) {
			Log.e(TAG, "startTest Exception : " + e.toString());
		}
	}

	@Override
	public void onQuestionSet(int position) {
		if (adpQuestionPalette != null) {
			adpQuestionPalette.setQuestion(position);
		}
	}

}
