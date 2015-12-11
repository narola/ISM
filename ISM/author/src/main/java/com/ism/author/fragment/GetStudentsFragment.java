package com.ism.author.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.adapter.MyStudentListAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Examsubmittor;

import java.util.ArrayList;

/**
 * Created by c166 on 16/11/15.
 */
public class GetStudentsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = GetStudentsFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;

    public GetStudentsFragment(Fragment fragment, Bundle bundleArguments) {
        this.mFragment = fragment;
        setArguments(bundleArguments);

    }

    private ImageView imgSearchMystudents;
    private EditText etSearchMystudents;
    private TextView tvTitleMystudents;
    private RecyclerView rvMystudentList;
    private MyStudentListAdapter myStudentListAdapter;
    private MyTypeFace myTypeFace;
    private ArrayList<Examsubmittor> arrListExamSubmittor = new ArrayList<Examsubmittor>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_get_students, container, false);
        initGlobal();

        return view;
    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());

        imgSearchMystudents = (ImageView) view.findViewById(R.id.img_search_mystudents);
        etSearchMystudents = (EditText) view.findViewById(R.id.et_search_mystudents);
        tvTitleMystudents = (TextView) view.findViewById(R.id.tv_title_mystudents);
        rvMystudentList = (RecyclerView) view.findViewById(R.id.rv_mystudent_list);
        myStudentListAdapter = new MyStudentListAdapter(getActivity(), mFragment);
        rvMystudentList.setAdapter(myStudentListAdapter);
        rvMystudentList.setLayoutManager(new LinearLayoutManager(getActivity()));

        etSearchMystudents.setTypeface(myTypeFace.getRalewayRegular());
        tvTitleMystudents.setTypeface(myTypeFace.getRalewayRegular());

        callApiGetExamSubmission();

        imgSearchMystudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etSearchMystudents.getVisibility() == View.VISIBLE) {
//                    startSlideAnimation(etSearchMystudents, 0, etSearchMystudents.getWidth(), 0, 0);
//                    startSlideAnimation(imgSearchMystudents, -imgSearchMystudents.getWidth(), 0, 0, 0);
                    etSearchMystudents.setVisibility(View.GONE);
                    myStudentListAdapter.filter("");
                    etSearchMystudents.setText("");

                } else {
                    startSlideAnimation(etSearchMystudents, etSearchMystudents.getWidth(), 0, 0, 0);
                    startSlideAnimation(imgSearchMystudents, etSearchMystudents.getWidth(), 0, 0, 0);
                    etSearchMystudents.setVisibility(View.VISIBLE);
                    Utility.showSoftKeyboard(etSearchMystudents, getActivity());
                }
            }
        });

        etSearchMystudents.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myStudentListAdapter.filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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


    private void callApiGetExamSubmission() {
        if (Utility.isConnected(getActivity())) {

            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute request = new Attribute();
                request.setExamId(getArguments().getString(ExamsAdapter.ARG_EXAM_ID));
                request.setUserId(Global.strUserId);
                request.setRole(Global.role);


                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETEXAMSUBMISSION);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GETEXAMSUBMISSION:
                    onResponseGetAllExamSubmission(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
        }
    }


    private void onResponseGetAllExamSubmission(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    arrListExamSubmittor.addAll(responseHandler.getExamSubmission().get(0).getExamsubmittor());
                    myStudentListAdapter.addAll(arrListExamSubmittor);
                    myStudentListAdapter.notifyDataSetChanged();

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllExamSubmission api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllExamSubmission Exception : " + e.toString());
        }
    }


    /*this is to refresh adapter for student navigation button in subjective questions fragment*/

    public void setBundleArgument(int position) {
        myStudentListAdapter.setBundleArgument(position);

    }

    private GetSubjectiveAssignmentQuestionsFragment getBaseFragment() {
        return (GetSubjectiveAssignmentQuestionsFragment) mFragment;

    }

}
