package com.ism.author.fragment.gotrading;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.gotrading.PastQuestionsAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;


/**
 * Created by c166 on 21/10/15.
 */
public class PastFragment extends Fragment {

    private static final String TAG = PastFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private TextView txtYourAnswer;
    private EditText etAnswer;
    private TextView txtSave;
    private TextView txt_Creator_Name;
    private TextView txtQuestion;
    private TextView txtDate;
    private TextView txtFollowers;
    private TextView txtQuestionMostFollower;
    private RecyclerView rvList;
    private PastQuestionsAdapter pastQuestionsAdapter;
    private TextView txtEmpty;

    public static PastFragment newInstance() {
        PastFragment fragGoTrending = new PastFragment();
        return fragGoTrending;
    }

    public PastFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_past, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
//        txtYourAnswer=(TextView)view.findViewById(R.id.txt_your_answer);
//        txtYourAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());
//
//        txtDate=(TextView)view.findViewById(R.id.txt_date);
//        txtDate.setTypeface(Global.myTypeFace.getRalewayRegular());
//
//        txtFollowers=(TextView)view.findViewById(R.id.txt_total_followers);
//        txtFollowers.setTypeface(Global.myTypeFace.getRalewayRegular());
//
//        txtQuestion=(TextView)view.findViewById(R.id.txt_question);
//        txtQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());
//
//        txt_Creator_Name=(TextView)view.findViewById(R.id.txt_creator_name);
//        txt_Creator_Name.setTypeface(Global.myTypeFace.getRalewayRegular());
//
//        txtSave=(TextView)view.findViewById(R.id.txt_save);
//        txtSave.setTypeface(Global.myTypeFace.getRalewayRegular());
//
//        txtQuestionMostFollower=(TextView)view.findViewById(R.id.txt_questions_most_follower);
//        txtQuestionMostFollower.setTypeface(Global.myTypeFace.getRalewayRegular());
//
//        etAnswer=(EditText)view.findViewById(R.id.et_answer);
//        etAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());

        rvList=(RecyclerView)view.findViewById(R.id.rv_list);

        pastQuestionsAdapter = new PastQuestionsAdapter(getActivity(),null);
        rvList.setAdapter(pastQuestionsAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));

        txtEmpty=(TextView)view.findViewById(R.id.txt_empty);
        txtEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_PAST);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_PAST);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    public void onBackClick() {
            ((AuthorHostActivity) getActivity()).handleBackClick(AppConstant.FRAGMENT_PAST);


    }
}
