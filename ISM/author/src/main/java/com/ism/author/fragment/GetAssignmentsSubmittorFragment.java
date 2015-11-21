package com.ism.author.fragment;

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
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.model.FragmentArgument;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Examsubmittor;

import java.util.ArrayList;

/**
 * Created by c166 on 10/11/15.
 */
public class GetAssignmentsSubmittorFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = GetAssignmentsSubmittorFragment.class.getSimpleName();
    private View view;
    private TextView tvSubmittorTitle;
    private ImageView imgToggleList;
    private RecyclerView rvAssignmentSubmittorList;
    private AssignmentSubmittorAdapter assignmentSubmittorAdapter;
    private MyTypeFace myTypeFace;
    private FragmentListener fragListener;
    private ArrayList<Examsubmittor> listOfStudents = new ArrayList<Examsubmittor>();
    private FragmentArgument fragmentArgument;

    public static GetAssignmentsSubmittorFragment newInstance(FragmentArgument fragmentArgument) {
        GetAssignmentsSubmittorFragment getAssignmentsSubmittorFragment = new GetAssignmentsSubmittorFragment();
        getAssignmentsSubmittorFragment.fragmentArgument = fragmentArgument;
        return getAssignmentsSubmittorFragment;
    }

    public GetAssignmentsSubmittorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_get_assignment_submittor, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {
        myTypeFace = new MyTypeFace(getActivity());

        tvSubmittorTitle = (TextView) view.findViewById(R.id.tv_submittor_title);
        imgToggleList = (ImageView) view.findViewById(R.id.img_toggle_list);
        rvAssignmentSubmittorList = (RecyclerView) view.findViewById(R.id.rv_assignment_submittor_list);
        assignmentSubmittorAdapter = new AssignmentSubmittorAdapter(getActivity(), fragmentArgument);

        rvAssignmentSubmittorList.setHasFixedSize(true);
        rvAssignmentSubmittorList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvAssignmentSubmittorList.setAdapter(assignmentSubmittorAdapter);
        tvSubmittorTitle.setTypeface(myTypeFace.getRalewayBold());

        callApiGetExamSubmission();


    }

    private void callApiGetExamSubmission() {
        if (Utility.isOnline(getActivity())) {

            try {
                ((AuthorHostActivity) getActivity()).startProgress();
                Attribute request = new Attribute();
//                request.setExamId(fragmentArgument.getRequestObject().getExamId());
                request.setExamId("9");
                request.setUserId("340");
                request.setRole(String.valueOf(AppConstant.AUTHOR_ROLE_ID));

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
            ((AuthorHostActivity) getActivity()).stopProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    listOfStudents.addAll(responseHandler.getExamSubmission().get(0).getExamsubmittor());
                    assignmentSubmittorAdapter.addAll(listOfStudents);
                    assignmentSubmittorAdapter.notifyDataSetChanged();
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


}
