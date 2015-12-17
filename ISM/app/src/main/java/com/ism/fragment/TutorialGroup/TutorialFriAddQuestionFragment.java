package com.ism.fragment.tutorialGroup;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ism.R;

/**
 * Created by c161 on 16/12/15.
 */
public class TutorialFriAddQuestionFragment extends Fragment {

	private static final String TAG = TutorialFriAddQuestionFragment.class.getSimpleName();

	private View view;
	private Button btnUploadAndFreeze;

	public static TutorialFriAddQuestionFragment newInstance() {
		TutorialFriAddQuestionFragment fragment = new TutorialFriAddQuestionFragment();
		return fragment;
	}

	public TutorialFriAddQuestionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_tutorial_fri_add_question, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		btnUploadAndFreeze = (Button) view.findViewById(R.id.btn_upload);

		btnUploadAndFreeze.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getChildFragmentManager().beginTransaction().replace(R.id.fl_tutorial, ExamFragment.newInstance(null, 120, false)).commit();
			}
		});

	}

}
