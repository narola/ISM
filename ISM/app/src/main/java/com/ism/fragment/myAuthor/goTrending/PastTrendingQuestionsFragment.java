package com.ism.fragment.myAuthor.goTrending;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.myAuthor.PastQuestionsAdapter;
import com.ism.constant.AppConstant;
import com.ism.constant.WebConstants;
import com.ism.fragment.MyAuthorFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.realm.RealmHandler;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.TrendingQuestion;

import java.util.ArrayList;

/**
 * Created by c162 on 16/01/16.
 */
public class PastTrendingQuestionsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = PastTrendingQuestionsFragment.class.getSimpleName();
    private FragmentListener fragmentListener;
    private HostActivity activityHost;
    private View view;
    private ArrayList<TrendingQuestion> arrayListTrendingQuestions = new ArrayList<>();
    private RealmHandler realmHandler;
    private RecyclerView rvList;
    private TextView txtEmptyView;
    private PastQuestionsAdapter pastQuestionsAdapter;

    public static PastTrendingQuestionsFragment newInstance() {
        PastTrendingQuestionsFragment fragment = new PastTrendingQuestionsFragment();
        return fragment;
    }

    public PastTrendingQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_past_trending_questions, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

        realmHandler = new RealmHandler(getActivity());

        rvList = (RecyclerView) view.findViewById(R.id.recyclerview);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));

        txtEmptyView = (TextView) view.findViewById(R.id.txt_empty_view);

        setEmptyView(true);
        callApiForGetPastTrendingQuestions();
    }

    private void setEmptyView(boolean isEnable) {
        txtEmptyView.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEmptyView.setText(getResources().getString(R.string.no_past_trending_questions));
        txtEmptyView.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvList.setVisibility(isEnable ? View.GONE : View.VISIBLE);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragmentListener = (FragmentListener) activity;
        activityHost = (HostActivity) activity;
        if (fragmentListener != null)
            fragmentListener.onFragmentAttached(MyAuthorFragment.FRAGMENT_AUTHOR_PAST_QUESTIONS);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (fragmentListener != null)
            fragmentListener.onFragmentAttached(MyAuthorFragment.FRAGMENT_AUTHOR_PAST_QUESTIONS);
    }

    private void callApiForGetPastTrendingQuestions() {
        if (Utility.isConnected(getActivity())) {
            try {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setAuthorId(activityHost.getBundle().getString(AppConstant.AUTHOR_ID));
                attribute.setRole(Global.authorRoleID);

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GET_PAST_TRENDING_QUESTION);
            } catch (Exception e) {
                Log.e(TAG, "callApiForGetPastTrendingQuestions Exception : " + e.toString());
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
                case WebConstants.GET_PAST_TRENDING_QUESTION:
                    onResponseGetPastTrendingQuestion(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.i(TAG, "onResponse Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void onResponseGetPastTrendingQuestion(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    arrayListTrendingQuestions = responseHandler.getTrendingQuestions();
                    setUpData(arrayListTrendingQuestions);
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Debug.e(TAG, "onResponseGetPastTrendingQuestion : " + WebConstants.FAILED);

                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetPastTrendingQuestion error : " + error.toString());
            }

        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetPastTrendingQuestion Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void setUpData(ArrayList<TrendingQuestion> trendingQuestions) {
        try {
            if (trendingQuestions != null && trendingQuestions.size() > 0) {
                setEmptyView(false);
                pastQuestionsAdapter = new PastQuestionsAdapter(getActivity(), trendingQuestions);
                rvList.setAdapter(pastQuestionsAdapter);
            } else {
                setEmptyView(true);
            }

        } catch (Exception e) {
            Debug.i(TAG, "setUpData Exception : " + e.getLocalizedMessage());
        }
    }
}
