package com.ism.author.fragment.gotrending;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.constant.WebConstants;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.TrendingQuestion;

import java.util.ArrayList;

/**
 * Created by c166 on 25/12/15.
 */
public class QuestionCommentsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = QuestionCommentsFragment.class.getSimpleName();
    private View view;

    private ImageView imgUserDp;
    private TextView txtCreatorName, txtQuestion, txtDate, txtTotalFollowers, txtTotalComments, tvNoDataMsg;
    private RecyclerView rvQuestionCommentsList;


    public static QuestionCommentsFragment newInstance() {
        QuestionCommentsFragment questionCommentsFragment = new QuestionCommentsFragment();
        return questionCommentsFragment;
    }

    public QuestionCommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question_comments, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

        imgUserDp = (ImageView) view.findViewById(R.id.img_user_dp);
        txtCreatorName = (TextView) view.findViewById(R.id.txt_creator_name);
        txtQuestion = (TextView) view.findViewById(R.id.txt_question);
        txtDate = (TextView) view.findViewById(R.id.txt_date);
        txtTotalFollowers = (TextView) view.findViewById(R.id.txt_total_followers);
        txtTotalComments = (TextView) view.findViewById(R.id.txt_total_comments);
        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);

        rvQuestionCommentsList = (RecyclerView) view.findViewById(R.id.rv_question_comments_list);

        setEmptyView(true);

        callApiGetTrendingQuestionDetail();
    }


    private void setEmptyView(boolean isEnable) {
        tvNoDataMsg.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvQuestionCommentsList.setVisibility(isEnable ? View.GONE : View.VISIBLE);
    }

    private void callApiGetTrendingQuestionDetail() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setQuestionid("2");

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GET_TRENDING_QUESTION_DETAIL);
            } catch (Exception e) {
                Log.e(TAG, "callApiGetTrendingQuestionDetail Exception : " + e.toString());
            }
        } else {
            Utility.alertOffline(getActivity());
        }
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {


        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            switch (apiCode) {
                case WebConstants.GET_TRENDING_QUESTION_DETAIL:
                    onResponseGetTrendingQuestionDetail(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.i(TAG, "onResponse Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void onResponseGetTrendingQuestionDetail(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    setUpData(responseHandler.getTrendingQuestions());

                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Debug.i(TAG, "onResponseGetTrendingQuestionDetail : " + WebConstants.FAILED);

                }
            } else if (error != null) {
                Debug.i(TAG, "onResponseGetTrendingQuestionDetail error : " + error.toString());
            }

        } catch (Exception e) {
            Debug.i(TAG, "onResponseGetTrendingQuestionDetail Exceptions : " + e.getLocalizedMessage());
        }
    }


    private void setUpData(ArrayList<TrendingQuestion> trendingQuestions) {
//        try {
//            if (trendingQuestions != null && trendingQuestions.size() > 0) {
//                setEmptyView(false);
//                pastQuestionsAdapter = new PastQuestionsAdapter(getActivity(), trendingQuestions);
//                rvList.setAdapter(pastQuestionsAdapter);
//            } else {
//                setEmptyView(true);
//            }
//
//        } catch (Exception e) {
//            Debug.i(TAG, "setUpData Exception : " + e.getLocalizedMessage());
//        }
    }

}
