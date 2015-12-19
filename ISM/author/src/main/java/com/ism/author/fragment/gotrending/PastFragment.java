package com.ism.author.fragment.gotrending;

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

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.gotrending.PastQuestionsAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.TrendingQuestion;

import java.util.ArrayList;


/**
 * Created by c166 on 21/10/15.
 */
public class PastFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = PastFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private RecyclerView rvList;
    private PastQuestionsAdapter pastQuestionsAdapter;
    private TextView txtEmpty;
    private AuthorHostActivity activityHost;

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

        rvList = (RecyclerView) view.findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));

        txtEmpty = (TextView) view.findViewById(R.id.txt_empty);
        txtEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());

        setEmptyView(true);

        callApiForGetPastQuestions();
    }

    private void setEmptyView(boolean isEnable) {
        txtEmpty.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvList.setVisibility(isEnable ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (AuthorHostActivity) activity;
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

    private void callApiForGetPastQuestions() {
        if (Utility.isConnected(getActivity())) {
            try {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setAuthorId(Global.strUserId);
                attribute.setRole(Global.role);
                attribute.setCheckSlot(Global.checkSlotNo);

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GET_TRENDING_QUESTIONS);
            } catch (Exception e) {
                Log.e(TAG, "callApiForGetPastQuestions Exception : " + e.toString());
            }
        } else {
            Utility.alertOffline(getActivity());
        }
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            activityHost.hideProgress();
            switch (apiCode) {
                case WebConstants.GET_TRENDING_QUESTIONS:
                    onResponseGetPastQuestions(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.i(TAG, "onResponse Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void onResponseGetPastQuestions(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    Debug.i(TAG, "onResponseGetPastQuestions : " + WebConstants.SUCCESS);
                    setUpData(responseHandler.getTrendingQuestions());

                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Debug.i(TAG, "onResponseGetPastQuestions : " + WebConstants.FAILED);

                }
            } else if (error != null) {
                Debug.i(TAG, "onResponseGetPastQuestions error : " + error.toString());
            }

        } catch (Exception e) {
            Debug.i(TAG, "onResponseGetPastQuestions Exceptions : " + e.getLocalizedMessage());
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
