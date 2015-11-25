package com.ism.author.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.AssignmentSubmittorAdapter;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.adapter.MyStudentListAdapter;
import com.ism.author.adapter.SubjectiveQuestionListAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.MyTypeFace;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Examsubmittor;
import com.ism.author.ws.model.Questions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c166 on 16/11/15.
 */
public class GetSubjectiveQuestionsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = GetSubjectiveQuestionsFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;


    public GetSubjectiveQuestionsFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_getsubjective_questions, container, false);
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

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

        imgStudentProfilePic = (CircleImageView) view.findViewById(R.id.img_student_profile_pic);

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


        llPrevStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPreviousStudentData();
            }
        });

        llNextStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextStudentData();

            }
        });

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
    }


    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;


    private void callApiGetExamQuestions() {
        if (Utility.isOnline(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).startProgress();
                Attribute request = new Attribute();
//                request.setExamId(getBaseFragment().getArguments().getString(ExamsAdapter.ARG_EXAM_ID));
                request.setExamId("11");
                Debug.e(TAG, "The exam ID is::" + getBaseFragment().getArguments().getString(ExamsAdapter.ARG_EXAM_ID));
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETEXAMQUESTIONS);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    private void callAPiGetExamEvaluation() {
        if (Utility.isOnline(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).startProgress();
                Attribute request = new Attribute();
//                request.setExamId(getBaseFragment().getArguments().getString(ExamsAdapter.ARG_EXAM_ID));
                request.setExamId(getBaseFragment().getArguments().getString(AssignmentSubmittorAdapter.ARG_STUDENT_ID));
                request.setExamId("11");
//                request.setStudentId("1");
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETEXAMEVALUATIONS);
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
                case WebConstants.GETEXAMQUESTIONS:
                    onResponseGetAllExamQuestions(object, error);
                    break;
                case WebConstants.GETEXAMEVALUATIONS:
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
            ((AuthorHostActivity) getActivity()).stopProgress();
            if (object != null) {
                responseObjGetAllExamQuestions = (ResponseHandler) object;
                if (responseObjGetAllExamQuestions.getStatus().equals(ResponseHandler.SUCCESS)) {
                    loadStudentEvaluationData();
                } else if (responseObjGetAllExamQuestions.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseObjGetAllExamQuestions.getMessage(), getActivity());
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
            ((AuthorHostActivity) getActivity()).stopProgress();
            if (object != null) {
                responseObjGetExamEvaluation = (ResponseHandler) object;
                if (responseObjGetExamEvaluation.getStatus().equals(ResponseHandler.SUCCESS)) {
                    loading = true;
                    subjectiveQuestionListAdapter.setEvaluationData(responseObjGetExamEvaluation.getExamEvaluation().get(0).getEvaluation());

                    setTitleDetails();
                    getBaseFragment().setQuestionStatusData(responseObjGetExamEvaluation.getExamEvaluation().get(0).getEvaluation());

                } else if (responseObjGetExamEvaluation.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseObjGetExamEvaluation.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetExamEvaluation api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetExamEvaluation Exception : " + e.toString());
        }
    }


    private void setStudentData(int position) {
        getBaseFragment().setBundleArgumentForStudent(position);
        loadStudentEvaluationData();
    }


    ArrayList<Examsubmittor> arrListExamSubmittor;

    private void loadNextStudentData() {
        isFromLeft = false;
        int position = getBaseFragment().getArguments().getInt(AssignmentSubmittorAdapter.ARG_STUDENT_POSITION);
        if (position < arrListExamSubmittor.size() - 1) {
            position++;
            setStudentData(position);
        }
    }

    private void loadPreviousStudentData() {
        isFromLeft = true;
        int position = getBaseFragment().getArguments().getInt(AssignmentSubmittorAdapter.ARG_STUDENT_POSITION);
        if (position >= 1) {
            position--;
            getBaseFragment().getArguments().putInt(AssignmentSubmittorAdapter.ARG_STUDENT_POSITION, position);
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
        if (getBaseFragment().getArguments().getString(AssignmentSubmittorAdapter.ARG_STUDENT_ID) != null) {
            setQuestions();
            callAPiGetExamEvaluation();
            scrollToSpecificQuestion(0);

        }
    }

    public void scrollToSpecificQuestion(int position) {
        rvSubjectiveQuestionsList.smoothScrollToPosition(position);
    }

    private void setTitleDetails() {

        arrListExamSubmittor = getBaseFragment().getArguments().getParcelableArrayList(MyStudentListAdapter.ARG_ARR_LIST_STUDENTS);
        tvStudentEvalutionNo.setText(getActivity().getResources().getString(R.string.strevaluation) + " " +
                (getBaseFragment().getArguments().getInt(AssignmentSubmittorAdapter.ARG_STUDENT_POSITION) + 1) + " " +
                getActivity().getResources().getString(R.string.strof) + " " +
                arrListExamSubmittor.size());

        imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png",
                imgStudentProfilePic, ISMAuthor.options);

        tvStudentName.setText(getBaseFragment().getArguments().getString(AssignmentSubmittorAdapter.ARG_STUDENT_NAME));
        tvStudentRollNo.setText(getResources().getString(R.string.strrollno) + " " +
                (getBaseFragment().getArguments().getInt(AssignmentSubmittorAdapter.ARG_STUDENT_POSITION) + 1));
        tvAssignmentNo.setText(getResources().getString(R.string.strassignmentno) + " " +
                getBaseFragment().getArguments().getInt(ExamsAdapter.ARG_ASSIGNMENT_NO));
        tvAssignmentTitle.setText(getBaseFragment().getArguments().getString(ExamsAdapter.ARG_EXAM_NAME));


    }


    private GetSubjectiveAssignmentQuestionsFragment getBaseFragment() {
        return (GetSubjectiveAssignmentQuestionsFragment) mFragment;

    }


}
