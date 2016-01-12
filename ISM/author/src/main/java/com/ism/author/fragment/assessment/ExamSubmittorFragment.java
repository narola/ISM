package com.ism.author.fragment.assessment;

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
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.ExamSubmittorAdapter;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.model.RealmDataModel;
import com.ism.author.object.Global;
import com.ism.author.utility.Debug;
import com.ism.author.utility.Utility;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.ExamSubmission;

import realmhelper.AuthorHelper;

/**
 * Created by c166 on 10/11/15.
 */
public class ExamSubmittorFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = ExamSubmittorFragment.class.getSimpleName();
    private View view;
    private TextView tvSubmittorTitle, tvNoDataMsg;
    private RecyclerView rvExamSubmittorList;
    private ExamSubmittorAdapter examSubmittorAdapter;
    private FragmentListener fragListener;
    private AuthorHelper authorHelper;
    private RealmDataModel realmDataModel;

    public static ExamSubmittorFragment newInstance() {
        ExamSubmittorFragment examSubmittorFragment = new ExamSubmittorFragment();
        return examSubmittorFragment;
    }

    public ExamSubmittorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exam_submittor, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {

        authorHelper = new AuthorHelper(getActivity());
        realmDataModel = new RealmDataModel();

        tvSubmittorTitle = (TextView) view.findViewById(R.id.tv_submittor_title);
        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);
        rvExamSubmittorList = (RecyclerView) view.findViewById(R.id.rv_exam_submittor_list);
        examSubmittorAdapter = new ExamSubmittorAdapter(getActivity());

        rvExamSubmittorList.setHasFixedSize(true);
        rvExamSubmittorList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvExamSubmittorList.setAdapter(examSubmittorAdapter);
        tvSubmittorTitle.setTypeface(Global.myTypeFace.getRalewayBold());

        tvSubmittorTitle.setText(getBundleArguments().getString(ExamsAdapter.ARG_EXAM_BOOK_NAME));

        setEmptyView(false);
        callApiGetExamSubmission();


    }

    private void callApiGetExamSubmission() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();

                Attribute request = new Attribute();
                request.setExamId(getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID));
                request.setUserId(Global.strUserId);
                request.setRole(Global.role);

                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETEXAMSUBMISSION);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            setUpData();
        }
    }


    @Override
    public void onResponse(int apicode, Object object, Exception error) {
        try {
            switch (apicode) {
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

                    if (responseHandler.getExamSubmission().size() > 0) {
                        setEmptyView(false);
                        addExamSubmission(responseHandler.getExamSubmission().get(0));
                        setUpData();
                    } else {
                        setEmptyView(true);
                    }


                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllExamSubmission api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllExamSubmission Exception : " + e.toString());
        }
    }


    private void addExamSubmission(ExamSubmission examSubmission) {

        if (examSubmission.getExamsubmittor().size() > 0) {

            authorHelper.addExamSubmission(realmDataModel.getROExamSubmission(examSubmission, authorHelper));
            /**
             * here we update the examsubmission data in exams table.
             */
            authorHelper.updateExamSubmissionData(authorHelper.getExamSubmission(Integer.valueOf(examSubmission.getExamId())));
        }
    }

    private void setUpData() {

        if (authorHelper.getExamSubmission(Integer.valueOf(getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID))) != null) {
            if (authorHelper.getExamSubmission(Integer.valueOf(getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID))).getRoExamSubmittors().size() > 0) {
                setEmptyView(false);
                examSubmittorAdapter.addAll(authorHelper.getExamSubmission(Integer.valueOf(getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID))).
                        getRoExamSubmittors());
            }
        } else {
            setEmptyView(true);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_ASSIGNMENT_SUBMITTOR);
                Debug.i(TAG, "attach");
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_ASSIGNMENT_SUBMITTOR);
                Debug.i(TAG, "detach");
                authorHelper.realm.close();
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    /**
     * Created by c162 on 26/10/15.
     */
    public static class BooksFragment extends Fragment {

        private static final String TAG = BooksFragment.class.getSimpleName();
        private View view;
        private FragmentListener fragListener;

        public static BooksFragment newInstance() {
            BooksFragment fragBooks = new BooksFragment();
            return fragBooks;
        }

        public BooksFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_my_books, container, false);

            initGlobal();

            return view;
        }

        private void initGlobal() {

        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            try {
                fragListener = (FragmentListener) activity;
                if (fragListener != null) {
                    fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_ASSIGNMENT_SUBMITTOR);
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
                    fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_ASSIGNMENT_SUBMITTOR);
                }
            } catch (ClassCastException e) {
                Debug.e(TAG, "onDetach Exception : " + e.toString());
            }
            fragListener = null;
        }
    }

    public void onBackClick() {

        getBundleArguments().remove(ExamSubmittorAdapter.ARG_STUDENT_ID);
        getBundleArguments().remove(ExamSubmittorAdapter.ARG_STUDENT_POSITION);
        getBundleArguments().remove(ExamSubmittorAdapter.ARG_STUDENT_PROFILE_PIC);
        getBundleArguments().remove(ExamSubmittorAdapter.ARG_STUDENT_NAME);

        ((AuthorHostActivity) getActivity()).handleBackClick(AppConstant.FRAGMENT_ASSIGNMENT_SUBMITTOR);
    }

    private Bundle getBundleArguments() {
        return ((AuthorHostActivity) getActivity()).getBundle();
    }

    private void setEmptyView(boolean isEnable) {

        tvNoDataMsg.setText(getResources().getString(R.string.no_exam_submittor));
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvExamSubmittorList.setVisibility(isEnable ? View.GONE : View.VISIBLE);
    }
}
