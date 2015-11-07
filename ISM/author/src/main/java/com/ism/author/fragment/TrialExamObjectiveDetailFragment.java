package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.Utility.InputValidator;
import com.ism.author.constant.WebConstants;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.ws.WebserviceWrapper;

/**
 * Created by c162 on 04/11/15.
 */
public class TrialExamObjectiveDetailFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = TrialExamObjectiveDetailFragment.class.getSimpleName();
    private View view;
    private FragmentListener fragListener;
    private MyTypeFace myTypeFace;
    private InputValidator inputValidator;
    public static RecyclerView rvList;
    public static TextView txtExamType, txtBookNameValue,txtExamTypeName, txtBookName, txtClassName, txtClass, txtEamName, txtEamTypeName, txtExamDateValue, txtExamName, txtExamDate;
    ImageView imgCopy,imgEdit;

    public static TrialExamObjectiveDetailFragment newInstance() {
        TrialExamObjectiveDetailFragment trialExamObjectiveDetailFragment = new TrialExamObjectiveDetailFragment();
        return trialExamObjectiveDetailFragment;
    }

    public TrialExamObjectiveDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trial_exam_details, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());
        inputValidator = new InputValidator(getActivity());
        rvList = (RecyclerView) view.findViewById(R.id.rv_list);
        txtExamType = (TextView) view.findViewById(R.id.txt_exam_type);
        txtExamName = (TextView) view.findViewById(R.id.txt_exam_name);
        imgCopy = (ImageView) view.findViewById(R.id.img_copy);
        imgEdit = (ImageView) view.findViewById(R.id.img_edit);
        txtExamTypeName = (TextView) view.findViewById(R.id.txt_exam_type_name);

        txtExamDate = (TextView) view.findViewById(R.id.txt_exam_date);
        txtExamDateValue = (TextView) view.findViewById(R.id.txt_exam_date_value);
        txtEamTypeName = (TextView) view.findViewById(R.id.txt_exam_type_name);
        txtClass = (TextView) view.findViewById(R.id.txt_class);
        txtClassName = (TextView) view.findViewById(R.id.txt_class_name);
        txtBookName = (TextView) view.findViewById(R.id.txt_book_name);
        txtBookNameValue = (TextView) view.findViewById(R.id.txt_book_name_value);
        txtBookName.setTypeface(myTypeFace.getRalewayRegular());
        txtBookNameValue.setTypeface(myTypeFace.getRalewayRegular());
        txtClass.setTypeface(myTypeFace.getRalewayRegular());
        txtClassName.setTypeface(myTypeFace.getRalewayRegular());
        txtExamType.setTypeface(myTypeFace.getRalewayRegular());
        txtExamName.setTypeface(myTypeFace.getRalewayRegular());
        txtExamDateValue.setTypeface(myTypeFace.getRalewayRegular());
        txtExamDate.setTypeface(myTypeFace.getRalewayRegular());
        txtBookName.setTypeface(myTypeFace.getRalewayRegular());
        txtBookNameValue.setTypeface(myTypeFace.getRalewayRegular());
        txtExamTypeName.setTypeface(myTypeFace.getRalewayRegular());
        txtEamTypeName.setTypeface(myTypeFace.getRalewayRegular());
        ((AuthorHostActivity) getActivity()).loadFragmentInRightContainer(AuthorHostActivity.FRAGMENT_STUDENT_ATTEMPTED);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_TRIAL_EXAM_OBJECTIVE_DETAILS);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_TRIAL_EXAM_OBJECTIVE_DETAILS);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    @Override
    public void onResponse(int apiMethodName, Object object, Exception error) {
        ((AuthorHostActivity) getActivity()).stopProgress();
        try {
            if (apiMethodName == WebConstants.GETEXAMEVALUATIONS) {
//                ResponseObject responseObject = (ResponseObject) object;
//                Debug.i(TAG, "Response of student attempted  ::" + responseObject.getMessage());
//                Debug.i(TAG, "Response of student attempted  ::" + responseObject.getStatus());
//                Debug.i(TAG, "Response of student attempted  ::" + responseObject.getQuestionData());
//                if (responseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && responseObject != null) {
//
//
//                } else {
//                    Utils.showToast(responseObject.getMessage(), getActivity());
//                }
//
            }


        } catch (Exception e) {
            Log.i(TAG, "Response Exceptions :" + e.toString());
        }
    }


}


