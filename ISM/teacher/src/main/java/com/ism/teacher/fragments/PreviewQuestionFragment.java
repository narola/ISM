package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.interfaces.FragmentListener;
import com.ism.object.MyTypeFace;
import com.ism.teacher.adapters.PreviewQuestionListAdapter;
import com.ism.teacher.model.Data;
import com.ism.utility.Debug;

import java.util.ArrayList;

/**
 * Created by c166 on 31/10/15.
 */
public class PreviewQuestionFragment extends Fragment {


    private static final String TAG = PreviewQuestionFragment.class.getSimpleName();
    private View view;

    TextView tvQuestionlistTitle;
    RecyclerView rvPreviewquestionlist;
    PreviewQuestionListAdapter previewQuestionListAdapter;
    //    RecyclerListAdapter adapter;
    public ArrayList<Data> listOfPreviewQuestions = new ArrayList<Data>();
    MyTypeFace myTypeFace;
    Fragment mFragment;

    //this is for the movable recyclerview.
//    private ItemTouchHelper mItemTouchHelper;

    public PreviewQuestionFragment(Fragment fragment) {
        // Required empty public constructor
        this.mFragment = fragment;
    }

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
        previewQuestionListAdapter = new PreviewQuestionListAdapter(getActivity(), mFragment);
        rvPreviewquestionlist.setAdapter(previewQuestionListAdapter);
        rvPreviewquestionlist.setLayoutManager(new LinearLayoutManager(getActivity()));

/*        adapter = new RecyclerListAdapter(getActivity(), this);
//        rvPreviewquestionlist.setHasFixedSize(true);
        rvPreviewquestionlist.setAdapter(adapter);
        rvPreviewquestionlist.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        callback.isItemViewSwipeEnabled();
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rvPreviewquestionlist);*/

    }


    public void addQuestionsToPreviewFragment() {
        previewQuestionListAdapter.addAll(listOfPreviewQuestions);

    }


}
