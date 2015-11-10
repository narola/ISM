package com.ism.teacher.fragments;

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

import com.ism.teacher.R;
import com.ism.teacher.activity.TeacherHomeActivity;
import com.ism.teacher.helper.InputValidator;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.ws.WebserviceWrapper;


/**
 * Created by c162 on 04/11/15.
 */
public class ExamObjectiveDetailFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = ExamObjectiveDetailFragment.class.getSimpleName();
    private View view;
    private MyTypeFace myTypeFace;
    private InputValidator inputValidator;
    public static RecyclerView rvList;
    public static TextView txtExamType, txtBookNameValue, txtExamTypeName, txtBookName, txtClassName, txtClass, txtEamName, txtEamTypeName, txtExamDateValue, txtExamName, txtExamDate;
    ImageView imgCopy, imgEdit;
    private FragmentListener fragListener;

    //private ResponseObject responseObject;
    //public static ResponseObject responseObjQuestions;
    // public static TrialExamDetailsAdapter trialExamDetailsAdapter;
    //public static String questionsID[];

    public static ExamObjectiveDetailFragment newInstance() {
        ExamObjectiveDetailFragment examObjectiveDetailFragment = new ExamObjectiveDetailFragment();
        return examObjectiveDetailFragment;
    }

    public ExamObjectiveDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exam_objective_details, container, false);

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
        ((TeacherHomeActivity) getActivity()).loadFragmentInRightContainer(TeacherHomeActivity.FRAGMENT_STUDENT_ATTEMPTED);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHomeActivity.FRAGMENT_EXAM_OBJECTIVE_DETAILS);
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
                fragListener.onFragmentDetached(TeacherHomeActivity.FRAGMENT_EXAM_OBJECTIVE_DETAILS);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    @Override
    public void onResponse(int API_METHOD, Object object, Exception error) {
        //((TeacherHomeActivity) getActivity()).stopProgress();
        try {
            //responseObject = (ResponseObject) object;
//           if (API_METHOD == WebConstants.GETEXAMQUESTIONS) {
//                if (responseObject.getStatus().equals(ResponseObject.SUCCESS)) {
//                    ((AuthorHostActivity) getActivity()).stopProgress();
//                    if (responseObject.getData().size() != 0) {
//                        responseObjQuestions = responseObject;
//                        // Debug.i(TAG, "Arraylist of Questions  ::" + responseObject.getData().get(0).getEvaluations());
//
//                        trialExamDetailsAdapter = new TrialExamDetailsAdapter(responseObjQuestions, getActivity(), this, null);
//                        ExamObjectiveDetailFragment.rvList.setAdapter(trialExamDetailsAdapter);
//                        ExamObjectiveDetailFragment.rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
//                        ExamObjectiveDetailFragment.txtBookNameValue.setText(responseObjQuestions.getData().get(0).getBookName());
//                        ExamObjectiveDetailFragment.txtExamTypeName.setText(responseObjQuestions.getData().get(0).getExam_type());
//                        ExamObjectiveDetailFragment.txtClassName.setText(responseObjQuestions.getData().get(0).getClassName());
//                        String examDate[] = responseObjQuestions.getData().get(0).getCreatedDate().split(" ");
//                        ExamObjectiveDetailFragment.txtExamDateValue.setText(examDate[0]);
//                        ExamObjectiveDetailFragment.txtExamName.setText(responseObjQuestions.getData().get(0).getExamName());
//                        questionsID=null;
//                        for(int i=0;i<responseObjQuestions.getData().get(0).getQuestions().size();i++){
//                            questionsID[i]=responseObjQuestions.getData().get(0).getQuestions().get(0).getQuestionId();
//                        }
//
//
//                    }
//
//                } else if (responseObject.getStatus().equals(ResponseObject.FAILED)) {
//                    Toast.makeText(getActivity(), "Please try again!", Toast.LENGTH_LONG).show();
//                }
//            }


        } catch (Exception e) {
            Log.i(TAG, "Response Exceptions :" + e.toString());
        }
    }


}


