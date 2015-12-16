package com.ism.teacher.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.teacher.ISMTeacher;
import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.AssignmentSubmitterAdapter;
import com.ism.teacher.adapters.AssignmentsAdapter;
import com.ism.teacher.adapters.MyStudentsAdapter;
import com.ism.teacher.adapters.SubjectiveQuestionListAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Evaluation;
import com.ism.teacher.ws.model.Examsubmittor;
import com.ism.teacher.ws.model.Questions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * List of subjective questions
 */
public class GetSubjectiveQuestionsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {

    private static final String TAG = GetSubjectiveQuestionsFragment.class.getSimpleName();


    private View view;
    Fragment mFragment;


    public GetSubjectiveQuestionsFragment() {
    }

    public GetSubjectiveQuestionsFragment(Fragment fragment, Bundle bundleArguments) {
        this.mFragment = fragment;
        setArguments(bundleArguments);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subjective_questions, container, false);
        initGlobal();
        return view;
    }

    private CircleImageView imgStudentProfilePic;
    private TextView tvStudentName, tvStudentRollNo, tvAssignmentNo, tvAssignmentTitle, tvSubjectiveScore, tvSubjectiveMarks,
            tvStudentEvalutionNo;
    private RecyclerView rvSubjectiveQuestionsList;
    private MyTypeFace myTypeFace;
    private ArrayList<Questions> arrListQuestions = new ArrayList<Questions>();
    private SubjectiveQuestionListAdapter subjectiveQuestionListAdapter;
    private ImageLoader imageLoader;
    private LinearLayoutManager mLayoutManager;
    private LinearLayout llPrevStudent, llNextStudent;
    private RelativeLayout rlBottomEvaluationTab;
    private ImageView imgEditExam, imgCopyExam;

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

        imgEditExam = (ImageView) view.findViewById(R.id.img_edit_exam);
        imgCopyExam = (ImageView) view.findViewById(R.id.img_copy_exam);

        imgStudentProfilePic = (CircleImageView) view.findViewById(R.id.img_student_profile_pic);

        rlBottomEvaluationTab = (RelativeLayout) view.findViewById(R.id.rl_bottom_evaluation_tab);

        tvStudentName = (TextView) view.findViewById(R.id.tv_student_name);
        tvStudentRollNo = (TextView) view.findViewById(R.id.tv_student_roll_no);
        tvAssignmentNo = (TextView) view.findViewById(R.id.tv_assignment_no);
        tvAssignmentTitle = (TextView) view.findViewById(R.id.tv_assignment_title);
        tvSubjectiveScore = (TextView) view.findViewById(R.id.tv_subjective_score);
        tvSubjectiveMarks = (TextView) view.findViewById(R.id.tv_subjective_marks);
        tvStudentEvalutionNo = (TextView) view.findViewById(R.id.tv_student_evalution_no);
        llPrevStudent = (LinearLayout) view.findViewById(R.id.ll_prev_student);
        llNextStudent = (LinearLayout) view.findViewById(R.id.ll_next_student);
        rvSubjectiveQuestionsList = (RecyclerView) view.findViewById(R.id.rv_subjective_questions_list);
        subjectiveQuestionListAdapter = new SubjectiveQuestionListAdapter(getActivity(), this);
        rvSubjectiveQuestionsList.setAdapter(subjectiveQuestionListAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvSubjectiveQuestionsList.setLayoutManager(mLayoutManager);


        tvStudentName.setTypeface(myTypeFace.getRalewayBold());
        tvStudentRollNo.setTypeface(myTypeFace.getRalewayRegular());
        tvAssignmentNo.setTypeface(myTypeFace.getRalewayRegular());
        tvAssignmentTitle.setTypeface(myTypeFace.getRalewayBold());
        tvSubjectiveScore.setTypeface(myTypeFace.getRalewayRegular());
        tvSubjectiveMarks.setTypeface(myTypeFace.getRalewayBold());
        tvStudentEvalutionNo.setTypeface(myTypeFace.getRalewayRegular());


        rvSubjectiveQuestionsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

//                    if (pastVisiblesItems == 0) {
//                        loadPreviousStudentData();
//                    } else
//                    if (loading) {
//                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
//                            loading = false;
//                            Utils.showToast("Last Item Wow !", getActivity());
//                            loadNextStudentData();
//                        }
//                    }
                }
            }
        });

        callApiGetExamQuestions();

        llPrevStudent.setOnClickListener(this);
        llNextStudent.setOnClickListener(this);
        imgCopyExam.setOnClickListener(this);
        imgEditExam.setOnClickListener(this);
    }


    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;


    private void callApiGetExamQuestions() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((TeacherHostActivity) getActivity()).showProgress();
                Attribute request = new Attribute();
//                request.setExamId(getBaseFragment().getArguments().getString(ExamsAdapter.ARG_EXAM_ID));
                request.setExamId(WebConstants.EXAM_ID_11_SUBJECTIVE);
                Debug.e(TAG, "The exam ID is::" + getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_ID));
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_EXAM_QUESTIONS);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    private void callAPiGetExamEvaluation() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((TeacherHostActivity) getActivity()).showProgress();
                Attribute request = new Attribute();
                request.setExamId(getBundleArguments().getString(AssignmentSubmitterAdapter.ARG_STUDENT_ID));
                request.setExamId(WebConstants.EXAM_ID_11_SUBJECTIVE);
//                request.setStudentId("1");
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_EXAM_EVALUATIONS);
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
                case WebConstants.GET_EXAM_QUESTIONS:
                    onResponseGetAllExamQuestions(object, error);
                    break;
                case WebConstants.GET_EXAM_EVALUATIONS:
                    onResponseGetExamEvaluation(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
        }

    }

    ResponseHandler responseObjGetAllExamQuestions;

    private void onResponseGetAllExamQuestions(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                responseObjGetAllExamQuestions = (ResponseHandler) object;
                if (responseObjGetAllExamQuestions.getStatus().equals(ResponseHandler.SUCCESS)) {

                    /**
                     * condition to call evaluation based on flag
                     */

                    if (getBundleArguments().getBoolean(AssignmentsAdapter.ARG_ISLOAD_FRAGMENTFOREVALUATION)) {
                        loadStudentEvaluationData();
                        // getBaseFragment().showGetStudentsandPalleteFragment();
                        rlBottomEvaluationTab.setVisibility(View.VISIBLE);
                    } else {
                        setQuestions();
                        scrollToSpecificQuestion(0);
                        //getBaseFragment().hideGetStudentsandPalleteFragment();
                        rlBottomEvaluationTab.setVisibility(View.GONE);

                    }
                } else if (responseObjGetAllExamQuestions.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseObjGetAllExamQuestions.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllExamQuestions api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllExamQuestions Exception : " + e.toString());
        }
    }

    ResponseHandler responseObjGetExamEvaluation;

    private void onResponseGetExamEvaluation(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                responseObjGetExamEvaluation = (ResponseHandler) object;
                if (responseObjGetExamEvaluation.getStatus().equals(ResponseHandler.SUCCESS)) {
                    loading = true;

                    subjectiveQuestionListAdapter.setEvaluationData(responseObjGetExamEvaluation.getExamEvaluation().get(0).getEvaluation());

                    updateStatusForEvaluation();
                    setTitleDetails();
                    getBaseFragment().setQuestionStatusData(arrListQuestions,
                            responseObjGetExamEvaluation.getExamEvaluation().get(0).getQuestionPalette());


                } else if (responseObjGetExamEvaluation.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseObjGetExamEvaluation.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetExamEvaluation api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetExamEvaluation Exception : " + e.toString());
        }
    }

    private void updateStatusForEvaluation() {
        for (Questions question : arrListQuestions) {
            for (Evaluation evaluation : responseObjGetExamEvaluation.getExamEvaluation().get(0).getEvaluation()) {
                if (question.getQuestionId().equals(evaluation.getQuestionId())) {
                    arrListQuestions.get(arrListQuestions.indexOf(question)).setIsEvaluated(true);
                    break;
                }
            }
        }
    }

    private void setStudentData(int position) {
        getBaseFragment().setBundleArgumentForStudent(position);
        loadStudentEvaluationData();
    }


    ArrayList<Examsubmittor> arrListExamSubmittor;

    private void loadNextStudentData() {
        isFromLeft = false;
        int position = getBundleArguments().getInt(AssignmentSubmitterAdapter.ARG_STUDENT_POSITION);
        if (position < arrListExamSubmittor.size() - 1) {
            position++;
            setStudentData(position);
        }
    }

    private void loadPreviousStudentData() {
        isFromLeft = true;
        int position = getBundleArguments().getInt(AssignmentSubmitterAdapter.ARG_STUDENT_POSITION);
        if (position >= 1) {
            position--;
            getBundleArguments().putInt(AssignmentSubmitterAdapter.ARG_STUDENT_POSITION, position);
            setStudentData(position);
        }
    }

    Boolean isFromLeft = false;

    private void setQuestions() {

        arrListQuestions.clear();
        arrListQuestions.addAll(responseObjGetAllExamQuestions.getExamQuestions().get(0).getQuestions());
        subjectiveQuestionListAdapter.addAll(arrListQuestions);
        subjectiveQuestionListAdapter.notifyDataSetChanged();

        Animation animation;
        if (isFromLeft) {
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_left);
        } else {
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right);
        }

        rvSubjectiveQuestionsList.setAnimation(animation);

    }

    public void loadStudentEvaluationData() {
        if (getBundleArguments().getString(AssignmentSubmitterAdapter.ARG_STUDENT_ID) != null) {
            setQuestions();
            callAPiGetExamEvaluation();
            scrollToSpecificQuestion(0);

        }
    }

    public void scrollToSpecificQuestion(int position) {
        rvSubjectiveQuestionsList.smoothScrollToPosition(position);
    }

    private void setTitleDetails() {

        arrListExamSubmittor = getBundleArguments().getParcelableArrayList(MyStudentsAdapter.ARG_ARR_LIST_STUDENTS);
        tvStudentEvalutionNo.setText(getActivity().getResources().getString(R.string.strevaluation) + " " +
                (getBundleArguments().getInt(AssignmentSubmitterAdapter.ARG_STUDENT_POSITION) + 1) + " " +
                getActivity().getResources().getString(R.string.strof) + " " +
                arrListExamSubmittor.size());

        imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png",
                imgStudentProfilePic, ISMTeacher.options);

        tvStudentName.setText(getBundleArguments().getString(AssignmentSubmitterAdapter.ARG_STUDENT_NAME));
        tvStudentRollNo.setText(getResources().getString(R.string.strrollno) + " " +
                (getBundleArguments().getInt(AssignmentSubmitterAdapter.ARG_STUDENT_POSITION) + 1));
        tvAssignmentNo.setText(getResources().getString(R.string.strassignmentno) + " " +
                getBundleArguments().getInt(AssignmentsAdapter.ARG_ASSIGNMENT_NO));
        tvAssignmentTitle.setText(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_NAME));


    }


    private GetSubjectiveAssignmentQuestionsFragment getBaseFragment() {
        return (GetSubjectiveAssignmentQuestionsFragment) mFragment;

    }

    @Override
    public void onClick(View v) {
        if (v == llPrevStudent) {

            loadPreviousStudentData();

        } else if (v == llNextStudent) {
            loadNextStudentData();

        } else if (v == imgEditExam || v == imgCopyExam) {
            setExamQuestions();
        }
    }

    private void setExamQuestions() {

        if (responseObjGetAllExamQuestions != null) {

            getBundleArguments().putParcelableArrayList(GetObjectiveAssignmentQuestionsFragment.ARG_ARR_LIST_QUESTIONS, arrListQuestions);
            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_TYPE, getString(R.string.strsubjective));

//            ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(
//                    (AuthorHostActivity.FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT), getArguments());
//
//            ((AuthorHostActivity) getActivity()).loadFragmentInRightContainer(
//                    (AuthorHostActivity.FRAGMENT_HIGHSCORE), null);

            mFragment.getFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home,
                    CreateExamAssignmentContainerFragment.newInstance(getBundleArguments())).commit();

        }

    }
    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }
}
