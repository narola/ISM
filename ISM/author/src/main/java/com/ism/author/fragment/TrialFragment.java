package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.utility.Debug;
import com.ism.author.utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.adapter.TrialAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Exams;

import java.util.ArrayList;

/**
 * Created by c166 on 11/12/15.
 */
/*This fragment will use for both trial section and my30 section*/
public class TrialFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = TrialFragment.class.getSimpleName();
    private View view;


    public static TrialFragment newInstance() {
        TrialFragment trialFragment = new TrialFragment();
        return trialFragment;
    }

    public TrialFragment() {
        // Required empty public constructor
    }

    private RecyclerView rvMythirtyList;
    private TrialAdapter trialAdapter;
    private ArrayList<Exams> arrListExams = new ArrayList<Exams>();
    private TextView tvNoDataMsg;
    private FragmentListener fragListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trial, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {

        rvMythirtyList = (RecyclerView) view.findViewById(R.id.rv_mythirty_list);
        trialAdapter = new TrialAdapter(getActivity());

        rvMythirtyList.setHasFixedSize(true);
        rvMythirtyList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvMythirtyList.setAdapter(trialAdapter);

        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);
        setEmptyView(false);


        callApiGetAllExams();
    }

    private void callApiGetAllExams() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute request = new Attribute();
                request.setUserId(Global.strUserId);
                request.setRole(Global.role);

                if (OfficeFragment.CURRENT_OFFICE_FRAGMENT == OfficeFragment.FRAGMENT_TRIAL) {
                    request.setExamCategory(getString(R.string.strtrial));
                } else if (OfficeFragment.CURRENT_OFFICE_FRAGMENT == OfficeFragment.FRAGMENT_MYTHIRTY) {
                    request.setExamCategory(getString(R.string.strmy30th));
                }
//                request.setExamCategory("");
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETALLEXAMS);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.alertOffline(getActivity());
        }
    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {

        try {
            switch (apiCode) {
                case WebConstants.GETALLEXAMS:
                    onResponseGetAllExams(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }

    }

    private void onResponseGetAllExams(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    if (responseHandler.getExams().size() > 0) {
                        arrListExams.addAll(responseHandler.getExams());
                        trialAdapter.addAll(arrListExams);

                        setEmptyView(false);
                    } else {

                        setEmptyView(true);
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());

                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllExams api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllExams Exception : " + e.toString());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_TRIAL);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_TRIAL);
                Debug.i(TAG, "detach");
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    public void onBackClick() {

        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_ID);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_NAME);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_CLASSROOM_ID);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_CLASSROOM_NAME);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_BOOK_ID);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_BOOK_NAME);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_CATEGORY);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_MODE);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_PASS_PERCENTAGE);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_DURATION);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_ATTEMPT_COUNT);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_INSTRUCTIONS);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_IS_RANDOM_QUESTION);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_IS_NEGATIVE_MARKING);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_NEGATIVE_MARK_VALUE);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_IS_DECLARE_RESULTS);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_ASSESSOR);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_START_DATE);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_START_TIME);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_CREATED_DATE);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_NO);
        getBundleArguments().remove(ExamsAdapter.ARG_FRAGMENT_TYPE);
        getBundleArguments().remove(ExamsAdapter.ARG_ISLOAD_FRAGMENTFOREVALUATION);

        ((AuthorHostActivity) getActivity()).handleBackClick(AppConstant.FRAGMENT_TRIAL);
    }

    private Bundle getBundleArguments() {
        return ((AuthorHostActivity) getActivity()).getBundle();
    }


    private void setEmptyView(boolean isEnable) {

        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setText(getString(R.string.no_exams));
        tvNoDataMsg.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvMythirtyList.setVisibility(isEnable ? View.GONE : View.VISIBLE);
    }
}
