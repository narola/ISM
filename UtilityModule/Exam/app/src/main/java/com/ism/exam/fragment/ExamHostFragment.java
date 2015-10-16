package com.ism.exam.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.exam.R;

/**
 * Created by c161 on 14/10/15.
 */
public class ExamHostFragment extends Fragment {

    private static final String TAG = ExamHostFragment.class.getSimpleName();

    private View view;

    public static ExamHostFragment newInstance() {
        ExamHostFragment fragmentTutorial = new ExamHostFragment();
        return fragmentTutorial;
    }

    public ExamHostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    view = inflater.inflate(R.layout.fragment_exam_host, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
	    QuestionPaletteFragment fragmentQuestionsPalette = QuestionPaletteFragment.newInstance();
	    getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_right, fragmentQuestionsPalette).commit();
	    getChildFragmentManager().beginTransaction().replace(R.id.fl_exam, ExamFragment.newInstance(fragmentQuestionsPalette, 60)).commit();
//	    getChildFragmentManager().beginTransaction().replace(R.id.fl_exam, ResultFragment.newInstance(null, true)).commit();
    }

}
