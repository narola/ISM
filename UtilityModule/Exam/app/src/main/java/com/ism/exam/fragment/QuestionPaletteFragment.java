package com.ism.exam.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.exam.R;

/**
 * Created by c161 on 14/10/15.
 */
public class QuestionPaletteFragment extends Fragment {

	private static final String TAG = QuestionPaletteFragment.class.getSimpleName();

	private View view;

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

	}

}
