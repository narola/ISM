package com.ism.author.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.adapter.QuestionPaletteAdapter;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.model.Evaluation;

import java.util.ArrayList;

/**
 * Created by c166 on 16/11/15.
 */
public class QuestionPaletteFragment extends Fragment {


    private static final String TAG = QuestionPaletteFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;

    public QuestionPaletteFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    private TextView tvQuestionPaletteTitle, tvLegend, tvQuesPaletteAssessed, tvQuesPaletteNotAttempted,
            tvQuesPaletteUnassessed;
    private RecyclerView rvQuestionpaletteList;
    private MyTypeFace myTypeFace;
    private ArrayList<Evaluation> arrListEvaluation = new ArrayList<Evaluation>();
    private QuestionPaletteAdapter questionPaletteAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question_palette, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());

        tvQuestionPaletteTitle = (TextView) view.findViewById(R.id.tv_question_palette_title);
        tvLegend = (TextView) view.findViewById(R.id.tv_legend);
        tvQuesPaletteAssessed = (TextView) view.findViewById(R.id.tv_ques_palette_assessed);
        tvQuesPaletteNotAttempted = (TextView) view.findViewById(R.id.tv_ques_palette_not_attempted);
        tvQuesPaletteUnassessed = (TextView) view.findViewById(R.id.tv_ques_palette_unassessed);
        rvQuestionpaletteList = (RecyclerView) view.findViewById(R.id.rv_questionpalette_list);
        questionPaletteAdapter = new QuestionPaletteAdapter(getActivity(), mFragment);
        rvQuestionpaletteList.setAdapter(questionPaletteAdapter);
        rvQuestionpaletteList.setLayoutManager(new GridLayoutManager(getActivity(), 5));


        tvQuestionPaletteTitle.setTypeface(myTypeFace.getRalewayRegular());
        tvLegend.setTypeface(myTypeFace.getRalewayBold());
        tvQuesPaletteAssessed.setTypeface(myTypeFace.getRalewayRegular());
        tvQuesPaletteNotAttempted.setTypeface(myTypeFace.getRalewayRegular());
        tvQuesPaletteUnassessed.setTypeface(myTypeFace.getRalewayRegular());


    }


    private Bundle getBundleArgument() {
        return ((GetSubjectiveAssignmentQuestionsFragment) mFragment).getBundleArgument();

    }

    public void setQuestionStatusData(ArrayList<Evaluation> evaluations) {
        arrListEvaluation.clear();
        arrListEvaluation.addAll(evaluations);
        questionPaletteAdapter.addAll(arrListEvaluation);
        questionPaletteAdapter.notifyDataSetChanged();
    }


}
