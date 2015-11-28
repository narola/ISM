package com.ism.teacher.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.helper.InputValidator;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Classrooms;
import com.ism.teacher.ws.model.Subjects;
import com.ism.teacher.ws.model.Topics;
import com.narola.kpa.richtexteditor.view.RichTextEditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by c166 on 28/10/15.
 */
public class AssignmentExamFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {


    private static final String TAG = AssignmentExamFragment.class.getSimpleName();
    //Views
    private View view;
    TextView tv_exam_title, tv_exam_examfor, tv_exam_examschedule, tv_exam_examinstruction, tv_exam_declareresult,
            tv_exam_negativemarking, tv_exam_randomquestion, tv_exam_usescore, tv_exam_questionscorevalue, tv_exam_addnegativemark;
    Spinner spExamClassroom, spExamSubjectname, spExamSubjecttopic, spExamPassingpercent, spExamExamname, spExamExammode,
            spExamExamduration;

    ToggleButton tbExamSelectexamfor;
    EditText etExamName, etExamStartdate, etExamEnddate, etExamQuestionscorevalue, etExamAttemptcount, etExamAddnegativemark;
    CheckBox cb_exam_startdate_notify, cb_exam_enddate_notify;
    RadioGroup radioDeclareresult, radioNegativemarking, radioExamRandomQuestion, radioExamUsescore;
    LinearLayout ll_add_questionscore, ll_add_negative_mark;
    Button btnExamSave, btnExamSetquestion, btnExamCancel;
    RichTextEditor rte_trial_exam;
    MyTypeFace myTypeFace;


    //Variables
    String exam_id_after_creating_exam = "78";
    private static int PASSINGPERCENT_INTERVAL = 5, PASSINGPERCENT_STARTVALUE = 30, PASSINGPERCENT_ENDVALUE = 99;
    private static int EXAMDURATION_INTERVAL = 30, EXAMDURATION_STARTVALUE = 30, EXAMDURATION_ENDVALUE = 300;
    String examStartDate = "", examEndDate = "", strAssignmenttext = "";

    //lists
    private ArrayList<Classrooms> arrListClassRooms;
    private ArrayList<Subjects> arrListSubject;
    private ArrayList<Topics> arrListTopic;
    List<String> arrListDefalt, arrListPassingPercent, arrListExamDuration, arrListExamMode, arrListExamName;

    private InputValidator inputValidator;

    private Fragment fragmentContext;
    private Context mContext;
    private ScrollView svCreateExam;


    /**
     * Fragment Args
     */
    public static String ARG_EXAM_CLASSROOM_ID = "examClassRoomId";
    public static String ARG_EXAM_SUBJECT_ID = "examSubjectId";
    public static String ARG_EXAM_TOPIC_ID = "examTopicId";
    public static String ARG_EXAM_QUESTION_SCORE = "examQuestionScore";
    public static String ARG_EXAM_BOOK_ID = "examBookId";


    public AssignmentExamFragment() {
        // Required empty public constructor
    }

    public static AssignmentExamFragment newInstance(Fragment fragment, Context mContext, Bundle bundleArgument) {
        AssignmentExamFragment assignmentExamFragment = new AssignmentExamFragment();
        assignmentExamFragment.setArguments(bundleArgument);
        assignmentExamFragment.mContext = mContext;
        assignmentExamFragment.fragmentContext = fragment;
        return assignmentExamFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignment_exam, container, false);

        initGlobal();
        return view;
    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());
        inputValidator = new InputValidator(getActivity());

        svCreateExam = (ScrollView) view.findViewById(R.id.sv_create_exam);
        tv_exam_title = (TextView) view.findViewById(R.id.tv_exam_title);

        tv_exam_examfor = (TextView) view.findViewById(R.id.tv_exam_examfor);
        tv_exam_examschedule = (TextView) view.findViewById(R.id.tv_exam_examschedule);
        tv_exam_examinstruction = (TextView) view.findViewById(R.id.tv_exam_examinstruction);
        tv_exam_declareresult = (TextView) view.findViewById(R.id.tv_exam_declareresult);
        tv_exam_negativemarking = (TextView) view.findViewById(R.id.tv_exam_negativemarking);
        tv_exam_randomquestion = (TextView) view.findViewById(R.id.tv_exam_randomquestion);
        tv_exam_usescore = (TextView) view.findViewById(R.id.tv_exam_usescore);
        tv_exam_questionscorevalue = (TextView) view.findViewById(R.id.tv_exam_questionscorevalue);
        tv_exam_addnegativemark = (TextView) view.findViewById(R.id.tv_exam_addnegativemark);

        tv_exam_title.setTypeface(myTypeFace.getRalewayRegular());
        tv_exam_examfor.setTypeface(myTypeFace.getRalewayRegular());
        tv_exam_examschedule.setTypeface(myTypeFace.getRalewayRegular());
        tv_exam_examinstruction.setTypeface(myTypeFace.getRalewayRegular());
        tv_exam_declareresult.setTypeface(myTypeFace.getRalewayRegular());
        tv_exam_negativemarking.setTypeface(myTypeFace.getRalewayRegular());
        tv_exam_randomquestion.setTypeface(myTypeFace.getRalewayRegular());
        tv_exam_usescore.setTypeface(myTypeFace.getRalewayRegular());
        tv_exam_questionscorevalue.setTypeface(myTypeFace.getRalewayRegular());
        tv_exam_addnegativemark.setTypeface(myTypeFace.getRalewayRegular());


        spExamClassroom = (Spinner) view.findViewById(R.id.sp_exam_classroom);
        spExamSubjectname = (Spinner) view.findViewById(R.id.sp_exam_subjectname);
        spExamSubjecttopic = (Spinner) view.findViewById(R.id.sp_exam_subjecttopic);
        spExamPassingpercent = (Spinner) view.findViewById(R.id.sp_exam_passingpercent);
        spExamExamname = (Spinner) view.findViewById(R.id.sp_exam_examname);
        spExamExammode = (Spinner) view.findViewById(R.id.sp_exam_exammode);
        spExamExamduration = (Spinner) view.findViewById(R.id.sp_exam_examduration);


        tbExamSelectexamfor = (ToggleButton) view.findViewById(R.id.tb_exam_selectexamfor);

        etExamName = (EditText) view.findViewById(R.id.et_exam_name);
        etExamStartdate = (EditText) view.findViewById(R.id.et_exam_startdate);
        etExamEnddate = (EditText) view.findViewById(R.id.et_exam_enddate);
        etExamQuestionscorevalue = (EditText) view.findViewById(R.id.et_exam_questionscorevalue);
        etExamAttemptcount = (EditText) view.findViewById(R.id.et_exam_attemptcount);
        etExamAddnegativemark = (EditText) view.findViewById(R.id.et_exam_addnegativemark);

        etExamName.setTypeface(myTypeFace.getRalewayRegular());
        etExamStartdate.setTypeface(myTypeFace.getRalewayRegular());
        etExamEnddate.setTypeface(myTypeFace.getRalewayRegular());
        etExamQuestionscorevalue.setTypeface(myTypeFace.getRalewayRegular());
        etExamAddnegativemark.setTypeface(myTypeFace.getRalewayRegular());

        cb_exam_startdate_notify = (CheckBox) view.findViewById(R.id.cb_exam_startdate_notify);
        cb_exam_enddate_notify = (CheckBox) view.findViewById(R.id.cb_exam_enddate_notify);

        radioDeclareresult = (RadioGroup) view.findViewById(R.id.radio_declareresult);
        radioNegativemarking = (RadioGroup) view.findViewById(R.id.radio_negativemarking);
        radioExamRandomQuestion = (RadioGroup) view.findViewById(R.id.radio_exam_random_question);
        radioExamUsescore = (RadioGroup) view.findViewById(R.id.radio_exam_usescore);


        btnExamSave = (Button) view.findViewById(R.id.btn_exam_save);
        btnExamSetquestion = (Button) view.findViewById(R.id.btn_exam_setquestion);
        btnExamCancel = (Button) view.findViewById(R.id.btn_exam_cancel);

        btnExamSave.setOnClickListener(this);
        btnExamSetquestion.setOnClickListener(this);
        btnExamCancel.setOnClickListener(this);

        ll_add_questionscore = (LinearLayout) view.findViewById(R.id.ll_add_questionscore);
        ll_add_negative_mark = (LinearLayout) view.findViewById(R.id.ll_add_negative_mark);


        arrListDefalt = new ArrayList<String>();
        arrListDefalt.add(getString(R.string.select));


        getPassingPercentSpinnerValues();
        Adapters.setUpSpinner(getActivity(), spExamPassingpercent, arrListPassingPercent);

        getExamDurationSpinnerValues();
        Adapters.setUpSpinner(getActivity(), spExamExamduration, arrListExamDuration);

        arrListExamMode = new ArrayList<String>();
        arrListExamMode.add(getString(R.string.strexammode));
        arrListExamMode = Arrays.asList(getResources().getStringArray(R.array.exammode));
        Adapters.setUpSpinner(getActivity(), spExamExammode, arrListExamMode);


        arrListExamName = new ArrayList<String>();
        arrListExamName.add(getString(R.string.strexamname));
        arrListExamName = Arrays.asList(getResources().getStringArray(R.array.examname));
        Adapters.setUpSpinner(getActivity(), spExamExamname, arrListExamName);

//        spExamSubjectname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (arrListSubject != null && position > 0) {
//
//                    if (Utility.isOnline(getActivity())) {
//
//                        callApiGetTopics(Integer.parseInt(arrListSubject.get(position - 1).getId()));
//                    } else {
//                        Utility.toastOffline(getActivity());
//                    }
//                } else {
//                    com.ism.adapter.Adapters.setUpSpinner(getActivity(), spExamSubjecttopic, arrListDefalt);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        etExamStartdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    examStartDate = Utility.showDatePickerDob(getActivity(), etExamStartdate);
                }
                return true;
            }
        });

        etExamEnddate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    examEndDate = Utility.showDatePickerDob(getActivity(), etExamEnddate);
                }
                return true;
            }
        });


        radioNegativemarking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // checkedId is the RadioButton selected


                if (((RadioButton) view.findViewById(checkedId)).getText().toString().toLowerCase().equals("yes")) {

                    ll_add_negative_mark.setVisibility(View.VISIBLE);

                } else {

                    ll_add_negative_mark.setVisibility(View.GONE);
                }


            }
        });

        radioExamUsescore.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // checkedId is the RadioButton selected


                if (((RadioButton) view.findViewById(checkedId)).getText().toString().toLowerCase().equals("yes")) {

                    ll_add_questionscore.setVisibility(View.VISIBLE);

                } else {

                    ll_add_questionscore.setVisibility(View.GONE);
                }


            }
        });


        rte_trial_exam = (RichTextEditor) view.findViewById(R.id.rte_trial_exam);


        rte_trial_exam.getRichEditor().setEditorFontSize(25);

        rte_trial_exam.getRichEditor().setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {

                strAssignmenttext = text;

            }
        });

        if (getArguments() != null) {
            setExamDetails();
            btnExamSetquestion.setVisibility(View.VISIBLE);
        } else {
//            btnExamSetquestion.setVisibility(View.GONE);
        }
        callApiGetClassRooms();
        callApiGetSubjects();

    }

    private void setExamDetails() {

        etExamName.setText(getArguments().getString(AppConstant.ARG_EXAM_NAME));
        spExamPassingpercent.setSelection(arrListPassingPercent.indexOf(getArguments().getString(AppConstant.ARG_PASS_PERCENTAGE)));
        setExamType(getArguments().getString(AppConstant.ARG_EXAM_TYPE));
        spExamExamname.setSelection(arrListExamName.indexOf(getArguments().getString(AppConstant.ARG_EXAM_CATEGORY)));
        spExamExammode.setSelection(arrListExamMode.indexOf(getArguments().getString(AppConstant.ARG_EXAM_MODE)));
        spExamExamduration.setSelection(arrListExamDuration.indexOf(getArguments().getString(AppConstant.ARG_EXAM_DURATION)));

    }

    private void setExamType(String value) {
        if (value.equals("subject")) {
            tbExamSelectexamfor.setChecked(true);
        } else if (value.equals("topic")) {
            tbExamSelectexamfor.setChecked(false);
        }

    }


    private void callApiGetClassRooms() {

        if (Utility.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_CLASSROOMS);
            } catch (Exception e) {
                //Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getActivity().getResources().getString(R.string.no_internet), getActivity());
        }

    }

    private void callApiGetSubjects() {

        if (Utility.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_SUBJECT);
            } catch (Exception e) {
                //Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getActivity().getResources().getString(R.string.no_internet), getActivity());
        }

    }


    private void callApiGetTopics(int subject_id) {

        if (Utility.isInternetConnected(getActivity())) {
            try {
                Attribute attribute = new Attribute();
                attribute.setSubjectId(String.valueOf(subject_id));
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_TOPICS);
            } catch (Exception e) {
                //Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getActivity().getResources().getString(R.string.no_internet), getActivity());
        }

    }


    private void callApiCreateExam() {


        if (Utility.isInternetConnected(getActivity())) {
            try {
                Attribute attribute = new Attribute();

                attribute.setExamName(etExamName.getText().toString());
                attribute.setClassroomId(String.valueOf(spExamClassroom.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListClassRooms.
                        get(spExamClassroom.getSelectedItemPosition() - 1).getId()) : 0));
                attribute.setSubjectId(String.valueOf(spExamSubjectname.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListSubject.
                        get(spExamSubjectname.getSelectedItemPosition() - 1).getId()) : 0));
                attribute.setAttemptCount(String.valueOf(Integer.valueOf(etExamAttemptcount.getText().toString())));
                attribute.setExamType(getExamType());
                attribute.setExamCategory(arrListExamName.get(spExamExamname.getSelectedItemPosition()));
                attribute.setExamMode(arrListExamMode.get(spExamExammode.getSelectedItemPosition()));
                attribute.setPassingPercent(arrListPassingPercent.get(spExamPassingpercent.getSelectedItemPosition()));
                attribute.setExamDuration(arrListExamDuration.get(spExamExamduration.getSelectedItemPosition()));
                attribute.setExamInstruction(strAssignmenttext);
                attribute.setDeclareResults(getRadioGroupSelection(radioDeclareresult));
                attribute.setNegativeMarking(getRadioGroupSelection(radioNegativemarking));
                attribute.setRandomQuestion(getRadioGroupSelection(radioExamRandomQuestion));
                attribute.setExamStartDate(Utility.getDateInApiFormat(etExamStartdate.getText().toString()));

                attribute.setExamStartTime("5:00:00");
                attribute.setUserId(WebConstants.USER_ID_370);
                if (etExamAddnegativemark.getText().toString().isEmpty()) {
                    attribute.setNegativeMarkValue("0");
                } else {
                    attribute.setNegativeMarkValue(etExamAddnegativemark.getText().toString());
                }

                attribute.setBookId("0");

                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.CREATE_EXAM);
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getActivity().getResources().getString(R.string.no_internet), getActivity());
        }

    }


    @Override
    public void onResponse(int apiMethodName, Object object, Exception error) {

        try {

            switch (apiMethodName) {

                case WebConstants.GET_CLASSROOMS:
                    onResponseGetClassrooms(object, error);
                    break;

                case WebConstants.GET_SUBJECT:
                    onResponseGetSubjects(object, error);
                    break;
                case WebConstants.GET_TOPICS:
                    onResponseGetTopics(object, error);
                    break;
                case WebConstants.CREATE_EXAM:
                    onResponseCreateExam(object, error);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "Assignment on response exception" + e.toString());
        }


    }

    private void onResponseGetClassrooms(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).stopProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    arrListClassRooms = new ArrayList<Classrooms>();
                    arrListClassRooms.addAll(responseHandler.getClassrooms());
                    List<String> classrooms = new ArrayList<String>();
                    classrooms.add(Utility.getString(R.string.strclass, mContext));
                    for (Classrooms classroom : arrListClassRooms) {
                        classrooms.add(classroom.getClassName());

                    }
                    Adapters.setUpSpinner(mContext, spExamClassroom, classrooms, Adapters.ADAPTER_NORMAL);
                    if (getArguments() != null) {
                        spExamClassroom.setSelection(classrooms.indexOf(getArguments().getString(AppConstant.ARG_CLASSROOM_NAME)));
                    }


                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), mContext);
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetClassrooms api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetClassrooms Exception : " + e.toString());
        }
    }

    private void onResponseGetSubjects(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).stopProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    arrListSubject = new ArrayList<Subjects>();
                    arrListSubject.addAll(responseHandler.getSubjects());
                    List<String> subjects = new ArrayList<String>();
                    subjects.add(Utility.getString(R.string.strsubjectname, mContext));
                    for (Subjects subject : arrListSubject) {
                        subjects.add(subject.getSubjectName());
                    }
                    Adapters.setUpSpinner(mContext, spExamSubjectname, subjects, Adapters.ADAPTER_NORMAL);

                    if (getArguments() != null) {
                        spExamSubjectname.setSelection(subjects.indexOf(getArguments().getString(AppConstant.ARG_SUBJECT_NAME)));
                    }
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), mContext);
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetSubjects api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetSubjects Exception : " + e.toString());
        }
    }

    private void onResponseGetTopics(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).stopProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListTopic = new ArrayList<Topics>();
                    arrListTopic.addAll(responseHandler.getTopics());
                    List<String> topics = new ArrayList<String>();
                    topics.add(Utility.getString(R.string.strtopic, mContext));
                    for (Topics topic : arrListTopic) {
                        topics.add(topic.getTopicName());
                    }
                    Adapters.setUpSpinner(mContext, spExamSubjecttopic, topics, Adapters.ADAPTER_NORMAL);
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Adapters.setUpSpinner(mContext, spExamSubjecttopic, arrListDefalt, Adapters.ADAPTER_NORMAL);
                    Utility.showToast(responseHandler.getMessage(), mContext);
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetTopics api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetTopics Exception : " + e.toString());
        }
    }

    private void onResponseCreateExam(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).stopProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    Utility.showToast(Utility.getString(R.string.msg_success_createexam, mContext), mContext);
                    btnExamSetquestion.setVisibility(View.VISIBLE);

                    if (getArguments() != null) {
                        getArguments().putString(AppConstant.ARG_EXAM_ID, responseHandler.getCreateExam().get(0).getExamId());
                    } else {
                        Bundle bundleExamDetails = new Bundle();
                        bundleExamDetails.putString(AppConstant.ARG_EXAM_ID, responseHandler.getCreateExam().get(0).getExamId());
                        setArguments(bundleExamDetails);
                    }

                    setBundleArguments();

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), mContext);
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseCreateExam api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseCreateExam Exception : " + e.toString());
        }
    }

    private void setBundleArguments() {

        try {
            getArguments().putString(ARG_EXAM_CLASSROOM_ID, String.valueOf(spExamClassroom.getSelectedItemPosition() > 0 ?
                    Integer.parseInt(arrListClassRooms.get(spExamClassroom.getSelectedItemPosition() - 1).getId()) : 0));
            getArguments().putString(ARG_EXAM_SUBJECT_ID, String.valueOf(spExamSubjectname.getSelectedItemPosition() > 0 ?
                    Integer.parseInt(arrListSubject.get(spExamSubjectname.getSelectedItemPosition() - 1).getId()) : 0));
            getArguments().putString(ARG_EXAM_TOPIC_ID, "5");
            getArguments().putString(ARG_EXAM_BOOK_ID, "3");
            getArguments().putString(ARG_EXAM_QUESTION_SCORE, etExamQuestionscorevalue.getText().toString().equals("") ?
                    "0" : etExamQuestionscorevalue.getText().toString());
            getArguments().putString(AppConstant.ARG_SUBJECT_NAME, arrListSubject.get(spExamSubjectname.getSelectedItemPosition() - 1).getSubjectName());
        } catch (Exception e) {
            Debug.e(TAG, "SetBundleArgumentsException : " + e.toString());
        }
    }


    private void getPassingPercentSpinnerValues() {

        arrListPassingPercent = new ArrayList<String>();
        arrListPassingPercent.add(getString(R.string.strpassingpercent));
        for (int i = PASSINGPERCENT_STARTVALUE; i < PASSINGPERCENT_ENDVALUE; i += PASSINGPERCENT_INTERVAL) {
            arrListPassingPercent.add(String.valueOf(i));

        }


    }

    private void getExamDurationSpinnerValues() {

        arrListExamDuration = new ArrayList<String>();
        arrListExamDuration.add(getString(R.string.strexamduration));
        for (int i = EXAMDURATION_STARTVALUE; i < EXAMDURATION_ENDVALUE; i += EXAMDURATION_INTERVAL) {
            arrListExamDuration.add(String.valueOf(i));

        }

    }

    @Override
    public void onClick(View v) {
        if (v == btnExamSave) {
            if (isInputsValid()) {
                callApiCreateExam();
            } else {
                svCreateExam.fullScroll(ScrollView.FOCUS_UP);
            }

        } else if (v == btnExamSetquestion) {

            if (getArguments() != null) {
                setBundleArguments();
            }

            ((CreateExamAssignmentContainerFragment) fragmentContext).hideTopBar();
            getFragmentManager().beginTransaction().
                    replace(R.id.fl_fragment_assignment_container, AddQuestionContainerFragment.newInstance(getArguments()))
                    .commit();

        } else if (v == btnExamCancel) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((TeacherHostActivity) getActivity()).flFragmentContainerRight.setVisibility(View.VISIBLE);
    }


    /**
     * Local Field validations
     */
    private String strValidationMsg;

    private boolean isInputsValid() {
        return inputValidator.validateStringPresence(etExamName) & inputValidator.validateStringPresence(etExamAttemptcount)
                & inputValidator.validateStringPresence(etExamStartdate) & inputValidator.validateStringPresence(etExamEnddate)
                && checkOtherInputs() && checkRadioButtonInputs();
    }

    private boolean checkRadioButtonInputs() {
        strValidationMsg = "";

        if (isDeclareResultOption() & isNegativeMarkingOption() & isRandomQuestionOption() & isUseScoreFromQuestion()) {
            return true;
        } else {
            Utility.alert(getActivity(), null, strValidationMsg);
            return false;
        }

    }

    private boolean isUseScoreFromQuestion() {
        if (radioExamUsescore.getCheckedRadioButtonId() == -1) {
            strValidationMsg += getString(R.string.msg_validation_use_score_from_questions);
            return false;
        } else {
            return true;
        }
    }

    private boolean isRandomQuestionOption() {
        if (radioExamRandomQuestion.getCheckedRadioButtonId() == -1) {
            strValidationMsg += getString(R.string.msg_validation_random_question);
            return false;
        } else {
            return true;
        }
    }

    private boolean isNegativeMarkingOption() {
        if (radioNegativemarking.getCheckedRadioButtonId() == -1) {
            strValidationMsg += getString(R.string.msg_validation_negative_marking);
            return false;
        } else {
            return true;
        }
    }

    private boolean isDeclareResultOption() {
        if (radioDeclareresult.getCheckedRadioButtonId() == -1) {
            strValidationMsg += getString(R.string.msg_validation_declare_results);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkOtherInputs() {

        strValidationMsg = "";
        if (isClassroomSet() & isSubjectSet() & isPassingPercentSet() & isExamNameSet() & isExamModeSet() & isExamDurationSet() & isTextSetInRichTextEditor()) {
            return true;
        } else {
            Utility.alert(getActivity(), null, strValidationMsg);
            return false;
        }
    }

    private boolean isTextSetInRichTextEditor() {

        if (strAssignmenttext.trim().length() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_add_text_rich_editor);
            return false;
        }

    }

    private boolean isExamDurationSet() {
        if (arrListExamDuration != null && arrListExamDuration.size() == 0 || spExamExamduration.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_set_exam_duration);
            return false;
        }
    }

    private boolean isExamModeSet() {
        if (spExamExammode.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_set_exam_mode);
            return false;
        }
    }

    private boolean isExamNameSet() {
        if (spExamExamname.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_set_exam_name);
            return false;
        }
    }

    private boolean isPassingPercentSet() {
        if (arrListPassingPercent != null && arrListPassingPercent.size() == 0 || spExamPassingpercent.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_set_passingpercent);
            return false;
        }
    }

    private boolean isSubjectSet() {
        if (arrListSubject != null && arrListSubject.size() == 0 || spExamSubjectname.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_set_subject);
            return false;
        }
    }

    private boolean isClassroomSet() {
        if (arrListClassRooms != null && arrListClassRooms.size() == 0 || spExamClassroom.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getActivity().getResources().getString(R.string.msg_validation_set_classroom);
            return false;
        }
    }


    private String getExamType() {
        if (tbExamSelectexamfor.isChecked()) {
            return "subject";
        } else {
            return "topic";
        }

    }


    private String getRadioGroupSelection(RadioGroup radioGroup) {

        int selectedId = radioGroup.getCheckedRadioButtonId();
        return ((RadioButton) view.findViewById(selectedId)).getText().toString().toLowerCase();


    }


}
