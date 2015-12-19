package com.ism.author.fragment.assessment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.AssignmentSubmittorAdapter;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Examsubmittor;

import java.util.ArrayList;

/**
 * Created by c166 on 10/11/15.
 */
public class AssignmentsSubmittorFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = AssignmentsSubmittorFragment.class.getSimpleName();
    private View view;
    private TextView tvSubmittorTitle, tvNoDataMsg;
    private ImageView imgToggleList;
    private RecyclerView rvAssignmentSubmittorList;
    private AssignmentSubmittorAdapter assignmentSubmittorAdapter;
    private FragmentListener fragListener;
    private ArrayList<Examsubmittor> arrListExamSubmittor = new ArrayList<Examsubmittor>();

    public static AssignmentsSubmittorFragment newInstance() {
        AssignmentsSubmittorFragment assignmentsSubmittorFragment = new AssignmentsSubmittorFragment();
        return assignmentsSubmittorFragment;
    }

    public AssignmentsSubmittorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignment_submittor, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {
        tvSubmittorTitle = (TextView) view.findViewById(R.id.tv_submittor_title);
        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);
        imgToggleList = (ImageView) view.findViewById(R.id.img_toggle_list);
        rvAssignmentSubmittorList = (RecyclerView) view.findViewById(R.id.rv_assignment_submittor_list);
        assignmentSubmittorAdapter = new AssignmentSubmittorAdapter(getActivity());

        rvAssignmentSubmittorList.setHasFixedSize(true);
        rvAssignmentSubmittorList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvAssignmentSubmittorList.setAdapter(assignmentSubmittorAdapter);
        tvSubmittorTitle.setTypeface(Global.myTypeFace.getRalewayBold());
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setVisibility(View.GONE);
        tvNoDataMsg.setText(getString(R.string.no_exam_submittor));
        tvSubmittorTitle.setText(getBundleArguments().getString(ExamsAdapter.ARG_EXAM_BOOK_NAME));

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
            Utility.toastOffline(getActivity());
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
                        arrListExamSubmittor.addAll(responseHandler.getExamSubmission().get(0).getExamsubmittor());
                        assignmentSubmittorAdapter.addAll(arrListExamSubmittor);
                        assignmentSubmittorAdapter.notifyDataSetChanged();
                        tvNoDataMsg.setVisibility(View.GONE);
                    } else {
                        tvNoDataMsg.setVisibility(View.VISIBLE);
                    }

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

        getBundleArguments().remove(AssignmentSubmittorAdapter.ARG_STUDENT_ID);
        getBundleArguments().remove(AssignmentSubmittorAdapter.ARG_STUDENT_POSITION);
        getBundleArguments().remove(AssignmentSubmittorAdapter.ARG_STUDENT_PROFILE_PIC);
        getBundleArguments().remove(AssignmentSubmittorAdapter.ARG_STUDENT_NAME);

        ((AuthorHostActivity) getActivity()).handleBackClick(AppConstant.FRAGMENT_ASSIGNMENT_SUBMITTOR);
    }

    private Bundle getBundleArguments() {
        return ((AuthorHostActivity) getActivity()).getBundle();
    }
}
