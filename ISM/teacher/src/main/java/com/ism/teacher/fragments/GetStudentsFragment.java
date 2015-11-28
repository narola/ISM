package com.ism.teacher.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;

import com.ism.teacher.R;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.MyStudentsAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Examsubmittor;

import java.util.ArrayList;

/**
 * Created by c75 on 10/11/15.
 */
public class GetStudentsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = GetStudentsFragment.class.getSimpleName();

    //views
    View rootview;
    RecyclerView rvMystudentslist;
    MyStudentsAdapter myStudentsAdapter;
    EditText etSearchMystudents;
    private ImageView imgSearchMystudents;


    //new
    private Fragment mFragment;
    private ArrayList<Examsubmittor> arrListExamSubmittor = new ArrayList<Examsubmittor>();

    public static GetStudentsFragment newInstance() {
        GetStudentsFragment getStudentsFragment = new GetStudentsFragment();
        return getStudentsFragment;
    }

    public GetStudentsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public GetStudentsFragment(Fragment fragment) {
        this.mFragment = fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_mystudents, container, false);

        initGlobal(rootview);

        return rootview;
    }

    private void initGlobal(View rootview) {
        rvMystudentslist = (RecyclerView) rootview.findViewById(R.id.rv_mystudentslist);
        etSearchMystudents = (EditText) rootview.findViewById(R.id.et_search_mystudents);
        imgSearchMystudents = (ImageView) rootview.findViewById(R.id.img_search_mystudents);

        myStudentsAdapter = new MyStudentsAdapter(getActivity(), mFragment);
        rvMystudentslist.setAdapter(myStudentsAdapter);
        rvMystudentslist.setLayoutManager(new LinearLayoutManager(getActivity()));


        callGetExamSubmissionApi();

//        imgSearchMystudents.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (etSearchMystudents.getVisibility() == View.VISIBLE) {
//                    etSearchMystudents.setVisibility(View.GONE);
//                    myStudentsAdapter.filter("");
//                    etSearchMystudents.setText("");
//
//                } else {
//                    startSlideAnimation(etSearchMystudents, etSearchMystudents.getWidth(), 0, 0, 0);
//                    startSlideAnimation(imgSearchMystudents, etSearchMystudents.getWidth(), 0, 0, 0);
//                    etSearchMystudents.setVisibility(View.VISIBLE);
//                    Utility.showSoftKeyboard(etSearchMystudents, getActivity());
//                }
//            }
//        });
//
//        etSearchMystudents.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                myStudentsAdapter.filter(s);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

    }

    private void startSlideAnimation(final View view, int fromX, int toX, int fromY, int toY) {
        TranslateAnimation slideOutAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slideOutAnimation.setDuration(500);
        slideOutAnimation.setFillAfter(true);
        view.startAnimation(slideOutAnimation);
    }


    private void callGetExamSubmissionApi() {
        try {
            Attribute attribute = new Attribute();
            attribute.setExamId(WebConstants.EXAM_ID_9_OBJECTIVE);
            attribute.setUserId(WebConstants.USER_ID_340);
            attribute.setRole(WebConstants.TEACHER_ROLE_ID);

            ((TeacherHostActivity) getActivity()).startProgress();
            new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                    .execute(WebConstants.GET_ALL_EXAM_SUBMISSION);


        } catch (Exception e) {
            Log.e("error", e.getLocalizedMessage());
        }
    }


    @Override
    public void onResponse(int api_code, Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).stopProgress();
            switch (api_code) {
                case WebConstants.GET_ALL_EXAM_SUBMISSION:
                    onResponseMyStudents(object);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseMyStudents(Object object) {

        ResponseHandler responseHandler = (ResponseHandler) object;
        if (responseHandler.getStatus().equalsIgnoreCase(AppConstant.API_STATUS_SUCCESS)) {

            arrListExamSubmittor.addAll(responseHandler.getExamSubmission().get(0).getExamsubmittor());
            myStudentsAdapter.addAll(arrListExamSubmittor);
            myStudentsAdapter.notifyDataSetChanged();

        }
    }



 /*this is to refresh adapter for student navigation button in subjective questions fragment*/

    public void setBundleArgument(int position) {
        myStudentsAdapter.setBundleArgument(position);

    }

    private GetSubjectiveAssignmentQuestionsFragment getBaseFragment() {
        return (GetSubjectiveAssignmentQuestionsFragment) mFragment;

    }


}
