package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.adapter.PreviewQuestionListAdapter;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.Data;

import java.util.ArrayList;

/**
 * Created by c166 on 31/10/15.
 */
public class PreviewQuestionFragment extends Fragment {


    private static final String TAG = PreviewQuestionFragment.class.getSimpleName();
    private View view;

//    private FragmentListener fragListener;

    public static PreviewQuestionFragment newInstance() {
        PreviewQuestionFragment previewQuestionFragment = new PreviewQuestionFragment();
        return previewQuestionFragment;
    }

    public PreviewQuestionFragment() {
        // Required empty public constructor
    }

    TextView tvQuestionlistTitle;
    RecyclerView rvPreviewquestionlist;
    PreviewQuestionListAdapter previewQuestionListAdapter;
    ArrayList<Data> listOfPreviewQuestions = new ArrayList<Data>();
    MyTypeFace myTypeFace;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_preview_question, container, false);

        initGlobal();
        return view;
    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());

        tvQuestionlistTitle = (TextView) view.findViewById(R.id.tv_questionlist_title);
        tvQuestionlistTitle.setTypeface(myTypeFace.getRalewayRegular());

        rvPreviewquestionlist = (RecyclerView) view.findViewById(R.id.rv_previewquestionlist);
        previewQuestionListAdapter = new PreviewQuestionListAdapter(getActivity());
        rvPreviewquestionlist.setAdapter(previewQuestionListAdapter);
        rvPreviewquestionlist.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            fragListener = (FragmentListener) activity;
//            if (fragListener != null) {
//                fragListener.onFragmentAttached(AddQuestionFragment.FRAGMENT_PREVIEWQUESTION);
//            }
//        } catch (ClassCastException e) {
//            Debug.e(TAG, "onAttach Exception : " + e.toString());
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        try {
//            if (fragListener != null) {
//                fragListener.onFragmentDetached(AddQuestionFragment.FRAGMENT_PREVIEWQUESTION);
//            }
//        } catch (ClassCastException e) {
//            Debug.e(TAG, "onDetach Exception : " + e.toString());
//        }
//        fragListener = null;
    }


    public void addQuestionsToPreviewFragment(ArrayList<Data> data) {

        previewQuestionListAdapter.addAll(data);


    }
}
