package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utils;
import com.ism.teacher.interfaces.FragmentListener;

/**
 * Created by c166 on 31/10/15.
 */
public class QuestionAddEditFragment extends Fragment {


    private static final String TAG = QuestionAddEditFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private static final String ARG_FRAGMENT = "fragment";

//    public static QuestionAddEditFragment newInstance() {
//        QuestionAddEditFragment questionAddEditFragment = new QuestionAddEditFragment();
//        return questionAddEditFragment;
//    }


    Fragment mFragment;

    public QuestionAddEditFragment(Fragment fragment) {
        // Required empty public constructor

        this.mFragment = fragment;
    }


    Button flip;

    public QuestionAddEditFragment() {

    }

    public static QuestionAddEditFragment newInstance(int fragment) {
        QuestionAddEditFragment questionAddEditFragment = new QuestionAddEditFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_FRAGMENT, fragment);
        questionAddEditFragment.setArguments(args);
        return questionAddEditFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question_addedit, container, false);

        Utils.showToast("THE QUESTION ADD EDIT FRAGMENT CALLED", getActivity());

        initGlobal();
        return view;
    }

    private void initGlobal() {

        flip = (Button) view.findViewById(R.id.flip);
        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddQuestionFragmentTeacher) mFragment).flipCard();

            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AddQuestionFragmentTeacher.FRAGMENT_QUESTIONADDEDIT);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AddQuestionFragmentTeacher.FRAGMENT_QUESTIONADDEDIT);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }
}
