package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
//implements OnStartDragListener

public class PreviewQuestionFragment extends Fragment  {


    private static final String TAG = PreviewQuestionFragment.class.getSimpleName();
    private View view;

    //    private FragmentListener fragListener;

    Fragment mFragment;

    public PreviewQuestionFragment(Fragment fragment) {
        // Required empty public constructor
        this.mFragment = fragment;
    }


    TextView tvQuestionlistTitle;
    RecyclerView rvPreviewquestionlist;
    PreviewQuestionListAdapter previewQuestionListAdapter;
    //    RecyclerListAdapter adapter;
    public ArrayList<Data> listOfPreviewQuestions = new ArrayList<Data>();
    MyTypeFace myTypeFace;


    //this is for the movable recyclerview.
    private ItemTouchHelper mItemTouchHelper;


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


//        adapter = new RecyclerListAdapter(getActivity(), this);
//        rvPreviewquestionlist.setHasFixedSize(true);
//        rvPreviewquestionlist.setAdapter(adapter);
//        rvPreviewquestionlist.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
//        callback.isItemViewSwipeEnabled();
//        mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(rvPreviewquestionlist);

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

        if (data.size() > 0) {

            listOfPreviewQuestions.addAll(data);
            previewQuestionListAdapter.addAll(listOfPreviewQuestions);

        }


    }

//    @Override
//    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
//        mItemTouchHelper.startDrag(viewHolder);
//
//    }
}
