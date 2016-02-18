package com.ism.author.fragment.assessment.subjectiveassessment;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.ExamSubmittorAdapter;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.adapter.MyStudentListAdapter;
import com.ism.author.adapter.SubjectiveQuestionListAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.fragment.assessment.objectiveassessment.ObjectiveAssignmentQuestionsFragment;
import com.ism.author.fragment.createexam.CreateExamFragment;
import com.ism.author.model.RealmDataModel;
import com.ism.author.object.Global;
import com.ism.author.object.MyTypeFace;
import com.ism.author.utility.Debug;
import com.ism.author.utility.Utility;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.ExamEvaluation;
import com.ism.author.ws.model.ExamQuestions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import realmhelper.AuthorHelper;

/**
 * Created by c166 on 16/11/15.
 */
@SuppressLint("ValidFragment")
public class SubjectiveQuestionsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {

    private static final String TAG = SubjectiveQuestionsFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;


    public SubjectiveQuestionsFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subjective_questions, container, false);
        initGlobal();
        return view;
    }

    private CircleImageView imgStudentProfilePic;
    private TextView tvStudentName, tvStudentRollNo, tvAssignmentNo, tvAssignmentTitle, tvSubjectiveScore, tvSubjectiveMarks,
            tvStudentEvalutionNo, tvNoDataMsg;
    private RecyclerView rvSubjectiveQuestionsList;
    private MyTypeFace myTypeFace;
    //    private ArrayList<Questions> arrListQuestions = new ArrayList<Questions>();
    private SubjectiveQuestionListAdapter subjectiveQuestionListAdapter;
    private ImageLoader imageLoader;
    private LinearLayoutManager mLayoutManager;
    private LinearLayout llPrevStudent, llNextStudent;
    private ImageView imgEditExam, imgCopyExam;
    private AuthorHelper authorHelper;
    private RealmDataModel realmDataModel;

    private void initGlobal() {


        authorHelper = new AuthorHelper(getActivity());
        realmDataModel = new RealmDataModel();

        myTypeFace = new MyTypeFace(getActivity());
        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

        imgEditExam = (ImageView) view.findViewById(R.id.img_edit_exam);
        imgCopyExam = (ImageView) view.findViewById(R.id.img_copy_exam);

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

        llPrevStudent.setOnClickListener(this);
        llNextStudent.setOnClickListener(this);
        imgCopyExam.setOnClickListener(this);
        imgEditExam.setOnClickListener(this);

        rvSubjectiveQuestionsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

//                    if (arrListQuestions.get(arrListQuestions.size() - 1).getIsEvaluated()) {
//
//                        if (pastVisiblesItems == 0) {
//                            loadPreviousStudentData();
//                        } else if (loading) {
//                            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
//                                loading = false;
//                                Utils.showToast("Last Item Wow !", getActivity());
//                                loadNextStudentData();
//                            }
//                        }
//                    } else {
//                        Utils.showToast(getString(R.string.msg_evaluate_question), getActivity());
//                    }
                }
            }
        });

        setEmptyView(false);
        callApiGetExamQuestions();
    }

//    if (firstVisibleItem == 0) {
//        // check if we reached the top or bottom of the list
//        View v = listview.getChildAt(0);
//        int offset = (v == null) ? 0 : v.getTop();
//        if (offset == 0) {
//            // reached the top:
//            return;
//        }
//    } else if (totalItemCount - visibleItemCount == firstVisibleItem){
//        View v =  listview.getChildAt(totalItemCount-1);
//        int offset = (v == null) ? 0 : v.getTop();
//        if (offset == 0) {
//            // reached the top:
//            return;
//        }
//    }


    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;


    private void callApiGetExamQuestions() {

        if (Utility.isConnected(getActivity())) {

            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute request = new Attribute();

                request.setExamId(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID));

                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETEXAMQUESTIONS);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }

        } else {
            Utility.alertOffline(getActivity());
        }

    }


    private void callApiGetExamEvaluation() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute request = new Attribute();

                request.setExamId(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID));
                request.setStudentId(getBaseFragment().getBundleArguments().getString(ExamSubmittorAdapter.ARG_STUDENT_ID));

                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETEXAMEVALUATIONS);
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


    private void onResponseGetAllExamQuestions(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    if (responseHandler.getExamQuestions().get(0).getQuestions().size() > 0) {

                        setEmptyView(false);
                        addExamQuestions(responseHandler.getExamQuestions().get(0));
                        setUpExamQuestionsData();

                    } else {
                        setEmptyView(true);
                    }

                    loadStudentEvaluationData();


                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllExamQuestions api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllExamQuestions Exception : " + e.toString());
        }
    }


    private void onResponseGetExamEvaluation(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {


//                    loading = true;
//
//                    subjectiveQuestionListAdapter.setEvaluationData(responseHandler.getExamEvaluation().get(0).getEvaluation());
//                    updateStatusForEvaluation();
//                    getBaseFragment().setQuestionStatusData(arrListQuestions,
//                            responseHandler.getExamEvaluation().get(0).getQuestionPalette());
//
//                    setTitleDetails();


                    if (responseHandler.getExamEvaluation().get(0).getEvaluation() != null) {
                        if (responseHandler.getExamEvaluation().get(0).getEvaluation().size() > 0) {
                            addEvaluationData(responseHandler.getExamEvaluation().get(0));
                            setUpEvaluationData();
                        }
                    } else {
                        Utility.showToast(getResources().getString(R.string.msg_no_user_evaluation), getActivity());
                        subjectiveQuestionListAdapter.setEvaluationData(null);
                    }


                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetExamEvaluation api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetExamEvaluation Exception : " + e.toString());
        }
    }


    private void addExamQuestions(ExamQuestions examQuestions) {

        if (examQuestions.getQuestions().size() > 0) {


//            authorHelper.addExamQuestions(realmDataModel.getROExamQuestions(examQuestions, authorHelper));
//            authorHelper.updateExamQuestionsData(authorHelper.getExamQuestions(Integer.valueOf(examQuestions.getId())));

            authorHelper.updateExamQuestionsData(realmDataModel.getROExamQuestions(examQuestions, authorHelper));
        }
    }

    private void setUpExamQuestionsData() {
        if (authorHelper.getExamQuestions(Integer.valueOf(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID))) != null) {
            setEmptyView(false);
            subjectiveQuestionListAdapter.addAll(authorHelper.getExamQuestions(Integer.valueOf(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID))).
                    getRoQuestions());
            Animation animation;
            if (isFromLeft) {
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_left);
            } else {
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right);
            }
            rvSubjectiveQuestionsList.setAnimation(animation);
        } else {
            setEmptyView(true);
        }

        if (!Utility.isConnected(getActivity())) {

//            setUpEvaluationData();
        }

    }


    private void addEvaluationData(ExamEvaluation examEvaluation) {


//        authorHelper.addStudentExamEvaluation(realmDataModel.getROStudentExamEvaluation(examEvaluation,
//                Integer.valueOf(getBundleArguments().getString(ExamSubmittorAdapter.ARG_STUDENT_ID)), authorHelper));
//
//        authorHelper.updateExamSubmittorData(Integer.valueOf(getBundleArguments().getString(ExamSubmittorAdapter.ARG_STUDENT_ID)),
//                authorHelper.getStudentExamEvaluation(Integer.valueOf(examEvaluation.getExamId()),
//                        Integer.valueOf(getBundleArguments().getString(ExamSubmittorAdapter.ARG_STUDENT_ID))));


        authorHelper.updateExamSubmittorData(Integer.valueOf(getBaseFragment().getBundleArguments().getString(ExamSubmittorAdapter.ARG_STUDENT_ID)),
                realmDataModel.getROStudentExamEvaluation(examEvaluation,
                        Integer.valueOf(getBaseFragment().getBundleArguments().getString(ExamSubmittorAdapter.ARG_STUDENT_ID)), authorHelper));

    }


    private void setUpEvaluationData() {

        if (authorHelper.getStudentExamEvaluation(Integer.valueOf(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID)),
                Integer.valueOf(getBaseFragment().getBundleArguments().getString(ExamSubmittorAdapter.ARG_STUDENT_ID))) != null) {

            subjectiveQuestionListAdapter.setEvaluationData(authorHelper.getStudentExamEvaluation(Integer.valueOf(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID)),
                    Integer.valueOf(getBaseFragment().getBundleArguments().getString(ExamSubmittorAdapter.ARG_STUDENT_ID))).getRoEvaluation());
        } else {

            subjectiveQuestionListAdapter.setEvaluationData(null);
            Utility.showToast(getResources().getString(R.string.msg_no_user_evaluation), getActivity());

        }

    }


    private void setStudentData(int position) {
        getBaseFragment().setBundleArgumentForStudent(position);
        loadStudentEvaluationData();
    }


    private void loadNextStudentData() {

        isFromLeft = false;
        int position = getBaseFragment().getBundleArguments().getInt(ExamSubmittorAdapter.ARG_STUDENT_POSITION);
        if (position < getBaseFragment().getBundleArguments().getInt(MyStudentListAdapter.ARG_TOTAL_NO_OF_STUDENTS) - 1) {
            position++;
            setStudentData(position);
        }
    }

    private void loadPreviousStudentData() {

        isFromLeft = true;
        int position = getBaseFragment().getBundleArguments().getInt(ExamSubmittorAdapter.ARG_STUDENT_POSITION);
        if (position >= 1) {
            position--;
            getBaseFragment().getBundleArguments().putInt(ExamSubmittorAdapter.ARG_STUDENT_POSITION, position);
            setStudentData(position);
        }
    }

    Boolean isFromLeft = false;

    private void setQuestions() {


//        if (responseObjGetAllExamQuestions.getExamQuestions().get(0).getQuestions().size() > 0) {
//
//            setEmptyView(false);
//
//            arrListQuestions.clear();
//            arrListQuestions.addAll(responseObjGetAllExamQuestions.getExamQuestions().get(0).getQuestions());
//            subjectiveQuestionListAdapter.addAll(arrListQuestions);
//            subjectiveQuestionListAdapter.notifyDataSetChanged();
//
//            Animation animation;
//            if (isFromLeft) {
//                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_left);
//            } else {
//                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right);
//            }
//
//            rvSubjectiveQuestionsList.setAnimation(animation);
//
//        } else {
//
//            setEmptyView(true);
//
//        }


    }

    public void loadStudentEvaluationData() {

        if (getBaseFragment().getBundleArguments().getString(ExamSubmittorAdapter.ARG_STUDENT_ID) != null) {

//            setQuestions();

            setUpExamQuestionsData();
            callApiGetExamEvaluation();
            scrollToSpecificQuestion(0);

        }
    }

    public void scrollToSpecificQuestion(int position) {
        rvSubjectiveQuestionsList.smoothScrollToPosition(position);
    }

    public void setTitleDetails() {

        tvStudentEvalutionNo.setText(getActivity().getResources().getString(R.string.strevaluation) + " " +
                (getBaseFragment().getBundleArguments().getInt(ExamSubmittorAdapter.ARG_STUDENT_POSITION) + 1) + " " +
                getActivity().getResources().getString(R.string.strof) + " " +
                getBaseFragment().getBundleArguments().getInt(MyStudentListAdapter.ARG_TOTAL_NO_OF_STUDENTS));


        if (getBaseFragment().getBundleArguments().getString(ExamSubmittorAdapter.ARG_STUDENT_PROFILE_PIC) != null && getBaseFragment().getBundleArguments().getString(ExamSubmittorAdapter.ARG_STUDENT_PROFILE_PIC) != "") {
            Global.imageLoader.displayImage(WebConstants.USER_IMAGES + getBaseFragment().getBundleArguments().getString(ExamSubmittorAdapter.ARG_STUDENT_PROFILE_PIC),
                    imgStudentProfilePic, ISMAuthor.options);
        } else {
            imgStudentProfilePic.setImageResource(R.drawable.userdp);
        }

        tvStudentName.setText(getBaseFragment().getBundleArguments().getString(ExamSubmittorAdapter.ARG_STUDENT_NAME));
        tvStudentRollNo.setText(getResources().getString(R.string.strrollno) + " " +
                (getBaseFragment().getBundleArguments().getInt(ExamSubmittorAdapter.ARG_STUDENT_POSITION) + 1));
        tvAssignmentNo.setText(getResources().getString(R.string.strassignmentno) + " " +
                getBaseFragment().getBundleArguments().getInt(ExamsAdapter.ARG_EXAM_NO));
        tvAssignmentTitle.setText(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_NAME));


    }


    private SubjectiveAssignmentQuestionsContainerFragment getBaseFragment() {
        return (SubjectiveAssignmentQuestionsContainerFragment) mFragment;

    }

    private void setExamQuestions() {

//        if (responseObjGetAllExamQuestions != null) {

//            getBaseFragment().getBundleArguments().putParcelableArrayList(ObjectiveAssignmentQuestionsFragment.ARG_ARR_LIST_QUESTIONS, arrListQuestions);
        getBaseFragment().getBundleArguments().putString(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_TYPE, getString(R.string.strsubjective));

        ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(
                (AuthorHostActivity.FRAGMENT_CONTAINER_CREATEEXAMASSIGNMENT));

        ((AuthorHostActivity) getActivity()).loadFragmentInRightContainer(
                (AuthorHostActivity.FRAGMENT_HIGHSCORE));

//        }

    }

    @Override
    public void onClick(View v) {

        if (v == llPrevStudent) {
            loadPreviousStudentData();
        } else if (v == llNextStudent) {
            loadNextStudentData();
        } else if (v == imgEditExam) {
            getBaseFragment().getBundleArguments().putBoolean(CreateExamFragment.ARG_IS_CREATE_EXAM, false);
            getBaseFragment().getBundleArguments().putBoolean(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY, false);
            setExamQuestions();
        } else if (v == imgCopyExam) {
            getBaseFragment().getBundleArguments().putBoolean(CreateExamFragment.ARG_IS_CREATE_EXAM, false);
            getBaseFragment().getBundleArguments().putBoolean(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY, true);
            setExamQuestions();
        }
    }

    private void updateStatusForEvaluation() {


//        for (Questions question : arrListQuestions) {
//            for (Evaluation evaluation : responseObjGetExamEvaluation.getExamEvaluation().get(0).getEvaluation()) {
//                if (question.getQuestionId().equals(evaluation.getQuestionId())) {
//                    arrListQuestions.get(arrListQuestions.indexOf(question)).setIsEvaluated(true);
//                    break;
//                }
//            }
//        }


    }

    public void updateQuestionEvaluationStatus(int position) {
//        arrListQuestions.get(position).setIsEvaluated(true);
    }


    private void setEmptyView(boolean isEnable) {

        tvNoDataMsg.setText(getResources().getString(R.string.no_exam_questions));
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvSubjectiveQuestionsList.setVisibility(isEnable ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        authorHelper.realm.close();
    }
}
