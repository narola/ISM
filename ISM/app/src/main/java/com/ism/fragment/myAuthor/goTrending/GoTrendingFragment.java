package com.ism.fragment.myAuthor.goTrending;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.myAuthor.GoTrendingQueAnsAuthorAdapter;
import com.ism.adapter.myAuthor.SendQuestionToAuthorAdapter;
import com.ism.constant.AppConstant;
import com.ism.constant.WebConstants;
import com.ism.fragment.MyAuthorFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.realm.RealmHandler;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.views.CirclePageIndicator;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.TrendingQuestion;
import com.ism.ws.model.TrendingQuestionDetails;

import java.util.ArrayList;

import io.realm.RealmResults;
import model.ROTrendingQuestion;

/**
 * Created by c162 on 07/01/16.
 */
public class GoTrendingFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = GoTrendingFragment.class.getSimpleName();
    private FragmentListener fragmentListener;
    private HostActivity activityHost;
    private View view;
    private ViewPager viewpager;
    private GoTrendingQueAnsAuthorAdapter trendingQueAnsAuthorAdapter;
    private CirclePageIndicator indicator;
    private RecyclerView rvQuestions;
    private SendQuestionToAuthorAdapter sendQuestionsAdapter;
    private ArrayList<TrendingQuestion> arrayListTrendingQuestions;
    private SendQuestionToAuthorAdapter getSendQuestionsAdapter;
    private ArrayList<TrendingQuestionDetails> arrayListTrendingQuestionDetails=new ArrayList<>();
    private TextView txtTrendingQuestion;
    private TextView txtTrendingQuestionFor;
    private RealmHandler realmHandler;
    private TextView txtPastQuestions;

    public static GoTrendingFragment newInstance() {
        GoTrendingFragment fragment = new GoTrendingFragment();
        return fragment;
    }

    public GoTrendingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_go_trending, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

        realmHandler=new RealmHandler(getActivity());
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        trendingQueAnsAuthorAdapter =new GoTrendingQueAnsAuthorAdapter(getActivity());
        viewpager.setAdapter(trendingQueAnsAuthorAdapter);

        indicator=(CirclePageIndicator)view.findViewById(R.id.indicator);
        indicator.setViewPager(viewpager);
       // viewpager.setAdapter(new Ima);
        rvQuestions=(RecyclerView)view.findViewById(R.id.recyclerview);
        rvQuestions.setLayoutManager(new LinearLayoutManager(getActivity()));

        txtTrendingQuestion=(TextView)view.findViewById(R.id.txt_question_tending);
        txtTrendingQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtPastQuestions=(TextView)view.findViewById(R.id.txt_past_questions);
        txtPastQuestions.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtTrendingQuestionFor=(TextView)view.findViewById(R.id.txt_question_for);
        txtTrendingQuestionFor.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtTrendingQuestionFor.setText(getResources().getString(R.string.strSendQuestionTo) + " " + activityHost.getBundle().getString(AppConstant.AUTHOR_NAME));


        callApiForGetTrending();

        txtPastQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityHost.loadFragment(MyAuthorFragment.FRAGMENT_AUTHOR_PAST_QUESTIONS,null);
            }
        });
//        sendQuestionsAdapter=new SendQuestionToAuthorAdapter(this,getActivity());
//        rvQuestions.setAdapter(sendQuestionsAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragmentListener = (FragmentListener) activity;
        activityHost = (HostActivity) activity;
        if (fragmentListener != null)
            fragmentListener.onFragmentAttached(MyAuthorFragment.FRAGMENT_GO_TRENDING);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (fragmentListener != null)
            fragmentListener.onFragmentAttached(MyAuthorFragment.FRAGMENT_GO_TRENDING);
    }
    private void callApiForGetTrending() {
        if (Utility.isConnected(getActivity())) {
            try {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setAuthorId(activityHost.getBundle().getString(AppConstant.AUTHOR_ID));
                attribute.setRole(Global.authorRoleID);
                attribute.setCheckSlot(Global.checkSlotNo);

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GET_TRENDING_QUESTIONS);
            } catch (Exception e) {
                Log.e(TAG, "callApiForGetTrending Exception : " + e.toString());
            }
        } else {
            Utility.alertOffline(getActivity());
        }
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        try {
            activityHost.hideProgress();
            switch (apiCode) {
                case WebConstants.GET_TRENDING_QUESTIONS:
                    onResponseGetTrending(object, error);
                    break;
                case WebConstants.GET_TRENDING_QUESTION_DETAIL:
                    onResponseTrendingQuestionDetail(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.i(TAG, "onResponse Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void onResponseTrendingQuestionDetail(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    arrayListTrendingQuestionDetails.add(responseHandler.getTrendingQuestionDetails().get(0));
                   // realmHandler.updateTrendingQuestion(responseHandler.getTrendingQuestionDetails().get(0));

                   trendingQueAnsAuthorAdapter.addAll(arrayListTrendingQuestionDetails);
//                   trendingQueAnsAuthorAdapter.addAll(realmHandler.getTrendingQuestions(activityHost.getBundle().getString(AppConstant.AUTHOR_ID)));
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Debug.i(TAG, "onResponseTrendingQuestionDetail : " + WebConstants.FAILED);

                }
            } else if (error != null) {
                Debug.i(TAG, "onResponseTrendingQuestionDetail error : " + error.toString());
            }

        } catch (Exception e) {
            Debug.i(TAG, "onResponseTrendingQuestionDetail Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void onResponseGetTrending(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    arrayListTrendingQuestions = responseHandler.getTrendingQuestions();
//                    setUpData(arrayListTrendingQuestions);
                    realmHandler.saveTrendingQuestion(arrayListTrendingQuestions,activityHost.getBundle().getString(AppConstant.AUTHOR_ID));
                    setUpData(realmHandler.getTrendingQuestions(activityHost.getBundle().getString(AppConstant.AUTHOR_ID)));
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Debug.i(TAG, "onResponseGetTrending : " + WebConstants.FAILED);

                }
            } else if (error != null) {
                Debug.i(TAG, "onResponseGetTrending error : " + error.toString());
            }

        } catch (Exception e) {
            Debug.i(TAG, "onResponseGetTrending Exceptions : " + e.getLocalizedMessage());
        }
    }
    private void setUpData(RealmResults<ROTrendingQuestion> realmResults) {
        try {
            if (realmResults != null && realmResults.size() > 0) {
               // setEmptyView(false);

                sendQuestionsAdapter = new SendQuestionToAuthorAdapter(this,getActivity(), realmResults);
                rvQuestions.setAdapter(sendQuestionsAdapter);
                for(ROTrendingQuestion roTrendingQuestion : realmResults){
                    callApiForGetTrendingQuestionDetails(roTrendingQuestion.getTrendingId());
                }
//                trendingQueAnsAuthorAdapter =new GoTrendingQueAnsAuthorAdapter(getActivity());
//                trendingQueAnsAuthorAdapter.addAll(realmHandler.getTrendingQuestions(activityHost.getBundle().getString(AppConstant.AUTHOR_ID)));
//                viewpager.setAdapter(trendingQueAnsAuthorAdapter);
//                indicator.setViewPager(viewpager);

            } else {
              //  setEmptyView(true);
            }

        } catch (Exception e) {
            Debug.i(TAG, "setUpData Exception : " + e.getLocalizedMessage());
        }
    }

    private void callApiForGetTrendingQuestionDetails(int trendingId) {
        if (Utility.isConnected(getActivity())) {
            try {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setQuestionId(String.valueOf(trendingId));

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GET_TRENDING_QUESTION_DETAIL);
            } catch (Exception e) {
                Log.e(TAG, "callApiForGetTrendingQuestionDetails Exception : " + e.toString());
            }
        } else {
            Utility.alertOffline(getActivity());
        }
    }
}
