package com.ism.author.fragment;


import android.app.Activity;
import android.app.Fragment;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ism.author.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.InputValidator;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.adapter.Adapters;
import com.ism.author.constant.WebConstants;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.Data;
import com.ism.author.model.RequestObject;
import com.ism.author.model.ResponseObject;
import com.ism.author.ws.WebserviceWrapper;
import com.narola.kpa.richtexteditor.view.RichTextEditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by c166 on 28/10/15.
 */
public class CreateExamFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {


    private static final String TAG = CreateExamFragment.class.getSimpleName();
    private View view;

    //    private FragmentListener fragListener;
    public static CreateExamFragment newInstance() {
        CreateExamFragment createExamFragment = new CreateExamFragment();
        return createExamFragment;
    }

    public CreateExamFragment() {
        // Required empty public constructor
    }

    TextView tv_exam_title, tv_exam_examfor, tv_exam_examschedule, tv_exam_examinstruction, tv_exam_declareresult,
            tv_exam_negativemarking, tv_exam_randomquestion, tv_exam_usescore, tv_exam_questionscorevalue, tv_exam_addnegativemark;
    Spinner sp_exam_classroom, sp_exam_subjectname, sp_exam_subjecttopic, sp_exam_passingpercent, sp_exam_examname, sp_exam_exammode,
            sp_exam_examduration;
    private ArrayList<Data> arrListClassRooms, arrListSubject, arrListTopic;
    List<String> arrListDefalt, arrListPassingPercent, arrListExamDuration, arrListExamMode, arrListExamName;


    ToggleButton tb_exam_selectexamfor;
    EditText et_exam_name, et_exam_startdate, et_exam_enddate, et_exam_questionscorevalue, et_exam_attemptcount, et_exam_addnegativemark;
    CheckBox cb_exam_startdate_notify, cb_exam_enddate_notify;
    RadioGroup radio_declareresult, radio_negativemarking, radio_exam_random_question, radio_exam_usescore;
    LinearLayout ll_add_questionscore, ll_add_negative_mark;

    Button btn_exam_save, btn_exam_setquestion, btn_exam_cancel;

    RichTextEditor rte_trial_exam;


    MyTypeFace myTypeFace;

    private static int PASSINGPERCENT_INTERVAL = 5, PASSINGPERCENT_STARTVALUE = 30, PASSINGPERCENT_ENDVALUE = 99;
    private static int EXAMDURATION_INTERVAL = 30, EXAMDURATION_STARTVALUE = 30, EXAMDURATION_ENDVALUE = 300;

    String examStartDate = "", examEndDate = "", strAssignmenttext = "";

    private InputValidator inputValidator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_exam, container, false);

        initGlobal();
        return view;
    }

    private void initGlobal() {


        myTypeFace = new MyTypeFace(getActivity());
        inputValidator = new InputValidator(getActivity());

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


        sp_exam_classroom = (Spinner) view.findViewById(R.id.sp_exam_classroom);
        sp_exam_subjectname = (Spinner) view.findViewById(R.id.sp_exam_subjectname);
        sp_exam_subjecttopic = (Spinner) view.findViewById(R.id.sp_exam_subjecttopic);
        sp_exam_passingpercent = (Spinner) view.findViewById(R.id.sp_exam_passingpercent);
        sp_exam_examname = (Spinner) view.findViewById(R.id.sp_exam_examname);
        sp_exam_exammode = (Spinner) view.findViewById(R.id.sp_exam_exammode);
        sp_exam_examduration = (Spinner) view.findViewById(R.id.sp_exam_examduration);


        tb_exam_selectexamfor = (ToggleButton) view.findViewById(R.id.tb_exam_selectexamfor);

        et_exam_name = (EditText) view.findViewById(R.id.et_exam_name);
        et_exam_startdate = (EditText) view.findViewById(R.id.et_exam_startdate);
        et_exam_enddate = (EditText) view.findViewById(R.id.et_exam_enddate);
        et_exam_questionscorevalue = (EditText) view.findViewById(R.id.et_exam_questionscorevalue);
        et_exam_attemptcount = (EditText) view.findViewById(R.id.et_exam_attemptcount);
        et_exam_addnegativemark = (EditText) view.findViewById(R.id.et_exam_addnegativemark);

        et_exam_name.setTypeface(myTypeFace.getRalewayRegular());
        et_exam_startdate.setTypeface(myTypeFace.getRalewayRegular());
        et_exam_enddate.setTypeface(myTypeFace.getRalewayRegular());
        et_exam_questionscorevalue.setTypeface(myTypeFace.getRalewayRegular());
        et_exam_addnegativemark.setTypeface(myTypeFace.getRalewayRegular());

        cb_exam_startdate_notify = (CheckBox) view.findViewById(R.id.cb_exam_startdate_notify);
        cb_exam_enddate_notify = (CheckBox) view.findViewById(R.id.cb_exam_enddate_notify);

        radio_declareresult = (RadioGroup) view.findViewById(R.id.radio_declareresult);
        radio_negativemarking = (RadioGroup) view.findViewById(R.id.radio_negativemarking);
        radio_exam_random_question = (RadioGroup) view.findViewById(R.id.radio_exam_random_question);
        radio_exam_usescore = (RadioGroup) view.findViewById(R.id.radio_exam_usescore);


        btn_exam_save = (Button) view.findViewById(R.id.btn_exam_save);
        btn_exam_setquestion = (Button) view.findViewById(R.id.btn_exam_setquestion);
        btn_exam_cancel = (Button) view.findViewById(R.id.btn_exam_cancel);

        btn_exam_save.setTypeface(myTypeFace.getRalewayRegular());
        btn_exam_setquestion.setTypeface(myTypeFace.getRalewayRegular());
        btn_exam_cancel.setTypeface(myTypeFace.getRalewayRegular());

        ll_add_questionscore = (LinearLayout) view.findViewById(R.id.ll_add_questionscore);
        ll_add_negative_mark = (LinearLayout) view.findViewById(R.id.ll_add_negative_mark);


        arrListDefalt = new ArrayList<String>();
        arrListDefalt.add(getString(R.string.select));


        getPassingPercentSpinnerValues();
        Adapters.setUpSpinner(getActivity(), sp_exam_passingpercent, arrListPassingPercent, Adapters.ADAPTER_NORMAL);

        getExamDurationSpinnerValues();
        Adapters.setUpSpinner(getActivity(), sp_exam_examduration, arrListExamDuration, Adapters.ADAPTER_NORMAL);

        arrListExamMode = new ArrayList<String>();
        arrListExamMode.add(getString(R.string.strexammode));
        arrListExamMode = Arrays.asList(getResources().getStringArray(R.array.exammode));
        Adapters.setUpSpinner(getActivity(), sp_exam_exammode, arrListExamMode, Adapters.ADAPTER_NORMAL);


        arrListExamName = new ArrayList<String>();
        arrListExamName.add(getString(R.string.strexamname));
        arrListExamName = Arrays.asList(getResources().getStringArray(R.array.examname));
        Adapters.setUpSpinner(getActivity(), sp_exam_examname, arrListExamName, Adapters.ADAPTER_NORMAL);

        et_exam_startdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    examStartDate = Utils.showDatePickerDob(getActivity(), et_exam_startdate);
                }
                return true;
            }
        });

        et_exam_enddate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    examEndDate = Utils.showDatePickerDob(getActivity(), et_exam_enddate);
                }
                return true;
            }
        });


        btn_exam_save.setOnClickListener(this);
        btn_exam_setquestion.setOnClickListener(this);
        btn_exam_cancel.setOnClickListener(this);


        radio_negativemarking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (((RadioButton) view.findViewById(checkedId)).getText().toString().toLowerCase().equals("yes")) {
                    ll_add_negative_mark.setVisibility(View.VISIBLE);
                } else {
                    ll_add_negative_mark.setVisibility(View.GONE);
                }


            }
        });

        radio_exam_usescore.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

        callApiGetClassrooms();
        callApiGetSubjects();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            fragListener = (FragmentListener) activity;
//            if (fragListener != null) {
//                fragListener.onFragmentAttached(CreateExamAssignmentContainerFragment.FRAGMENT_TRIAL_EXAM);
//            }
//        } catch (ClassCastException e) {
//            Log.i(TAG, "onAttach Exception : " + e.toString());
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        try {
//            if (fragListener != null) {
//                fragListener.onFragmentDetached(CreateExamAssignmentContainerFragment.FRAGMENT_TRIAL_EXAM);
//            }
//        } catch (ClassCastException e) {
//            Log.i(TAG, "onDetach Exception : " + e.toString());
//        }
//        fragListener = null;
    }

    private void callApiGetClassrooms() {

        if (Utils.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETCLASSROOMS);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }

    private void callApiGetSubjects() {

        if (Utils.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETSUBJECT);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }


    private void callApiGetTopics(int subject_id) {

        if (Utils.isInternetConnected(getActivity())) {
            try {
                RequestObject requestObject = new RequestObject();
                requestObject.setSubjectId(subject_id);
                new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETTOPICS);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }


    private void callApiCreateExam() {

        if (Utils.isInternetConnected(getActivity())) {
            try {
                RequestObject requestObject = new RequestObject();

                requestObject.setExamName(et_exam_name.getText().toString());
                requestObject.setClassroomId(sp_exam_classroom.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListClassRooms.
                        get(sp_exam_classroom.getSelectedItemPosition() - 1).getId()) : 0);
                requestObject.setSubjectId(sp_exam_subjectname.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListSubject.
                        get(sp_exam_subjectname.getSelectedItemPosition() - 1).getId()) : 0);
                requestObject.setAttemptCount(Integer.valueOf(et_exam_attemptcount.getText().toString()));
                requestObject.setExamType(getExamType());
                requestObject.setExamCategory(arrListExamName.get(sp_exam_examname.getSelectedItemPosition()));
                requestObject.setExamMode(arrListExamMode.get(sp_exam_exammode.getSelectedItemPosition()));
                requestObject.setPassingPercent(arrListPassingPercent.get(sp_exam_passingpercent.getSelectedItemPosition()));
                requestObject.setExamDuration(arrListExamDuration.get(sp_exam_examduration.getSelectedItemPosition()));
                requestObject.setExamInstruction(strAssignmenttext);
                requestObject.setDeclareResults(getRadioGropuSelection(radio_declareresult));
                requestObject.setNegativeMarking(getRadioGropuSelection(radio_negativemarking));
                requestObject.setRandomQuestion(getRadioGropuSelection(radio_exam_random_question));
                requestObject.setExamStartDate(Utils.getDateInApiFormat(et_exam_startdate.getText().toString()));

                requestObject.setExamStartTime("5:00:00");
                requestObject.setUserId("370");
                if (et_exam_addnegativemark.getText().toString().isEmpty()) {
                    requestObject.setNegativeMarkValue("0");
                } else {
                    requestObject.setNegativeMarkValue(et_exam_addnegativemark.getText().toString());
                }

                requestObject.setBookId(0);

                new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.CREATEEXAM);
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getActivity().getResources().getString(R.string.strnetissue), getActivity());
        }

    }


    //local field validations
    private String strValidationMsg;

    private boolean isInputsValid() {
        return inputValidator.validateStringPresence(et_exam_name) & inputValidator.validateStringPresence(et_exam_attemptcount)
                & inputValidator.validateStringPresence(et_exam_startdate) & inputValidator.validateStringPresence(et_exam_enddate)
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
        if (radio_exam_usescore.getCheckedRadioButtonId() == -1) {
            strValidationMsg += getString(R.string.msg_validation_use_score_from_questions);
            return false;
        } else {
            return true;
        }
    }

    private boolean isRandomQuestionOption() {
        if (radio_exam_random_question.getCheckedRadioButtonId() == -1) {
            strValidationMsg += getString(R.string.msg_validation_random_question);
            return false;
        } else {
            return true;
        }
    }

    private boolean isNegativeMarkingOption() {
        if (radio_negativemarking.getCheckedRadioButtonId() == -1) {
            strValidationMsg += getString(R.string.msg_validation_negative_marking);
            return false;
        } else {
            return true;
        }
    }

    private boolean isDeclareResultOption() {
        if (radio_declareresult.getCheckedRadioButtonId() == -1) {
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
        if (arrListExamDuration != null && arrListExamDuration.size() == 0 || sp_exam_examduration.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_set_exam_duration);
            return false;
        }
    }

    private boolean isExamModeSet() {
        if (sp_exam_exammode.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_set_exam_mode);
            return false;
        }
    }

    private boolean isExamNameSet() {
        if (sp_exam_examname.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_set_exam_name);
            return false;
        }
    }

    private boolean isPassingPercentSet() {
        if (arrListPassingPercent != null && arrListPassingPercent.size() == 0 || sp_exam_passingpercent.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_set_passingpercent);
            return false;
        }
    }

    private boolean isSubjectSet() {
        if (arrListSubject != null && arrListSubject.size() == 0 || sp_exam_subjectname.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_set_subject);
            return false;
        }
    }

    private boolean isClassroomSet() {
        if (arrListClassRooms != null && arrListClassRooms.size() == 0 || sp_exam_classroom.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getActivity().getResources().getString(R.string.msg_validation_set_classroom);
            return false;
        }
    }


    private String getExamType() {
        if (tb_exam_selectexamfor.isChecked()) {
            return "subject";
        } else {
            return "topic";
        }

    }


    private String getRadioGropuSelection(RadioGroup radioGroup) {

        int selectedId = radioGroup.getCheckedRadioButtonId();
        return ((RadioButton) view.findViewById(selectedId)).getText().toString().toLowerCase();


    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {

        try {
            switch (apiCode) {
                case WebConstants.GETCLASSROOMS:
                    onResponseGetClassrooms(object, error);
                    break;
                case WebConstants.GETSUBJECT:
                    onResponseGetSubjects(object, error);
                    break;
                case WebConstants.GETTOPICS:
                    onResponseGetTopics(object, error);
                    break;
                case WebConstants.CREATEEXAM:
                    onResponseCreateExam(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }

    }


    private void onResponseGetClassrooms(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseObject responseObj = (ResponseObject) object;
                if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {

                    arrListClassRooms = new ArrayList<Data>();
                    arrListClassRooms.addAll(responseObj.getData());
                    List<String> classrooms = new ArrayList<String>();
                    classrooms.add(getString(R.string.strclass));
                    for (Data course : arrListClassRooms) {
                        classrooms.add(course.getClassName());

                    }
                    Adapters.setUpSpinner(getActivity(), sp_exam_classroom, classrooms, Adapters.ADAPTER_NORMAL);

                } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                    Utils.showToast(responseObj.getMessage(), getActivity());
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
            if (object != null) {
                ResponseObject responseObj = (ResponseObject) object;
                if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {

                    arrListSubject = new ArrayList<Data>();
                    arrListSubject.addAll(responseObj.getData());
                    List<String> subjects = new ArrayList<String>();
                    subjects.add(getString(R.string.strsubjectname));
                    for (Data subject : arrListSubject) {
                        subjects.add(subject.getSubjectName());

                    }
                    Adapters.setUpSpinner(getActivity(), sp_exam_subjectname, subjects, Adapters.ADAPTER_NORMAL);
                } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                    Utils.showToast(responseObj.getMessage(), getActivity());
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
            if (object != null) {
                ResponseObject responseObj = (ResponseObject) object;
                if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                    arrListTopic = new ArrayList<Data>();
                    arrListTopic.addAll(responseObj.getData());
                    List<String> topics = new ArrayList<String>();
                    topics.add(getString(R.string.strtopic));
                    for (Data topic : arrListTopic) {
                        topics.add(topic.getTopicName());

                    }
                    Adapters.setUpSpinner(getActivity(), sp_exam_subjecttopic, topics, Adapters.ADAPTER_NORMAL);
                } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                    Adapters.setUpSpinner(getActivity(), sp_exam_subjecttopic, arrListDefalt, Adapters.ADAPTER_NORMAL);
                    Utils.showToast(responseObj.getMessage(), getActivity());
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
            if (object != null) {
                ResponseObject responseObj = (ResponseObject) object;
                if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                    Utils.showToast(getActivity().getString(R.string.msg_success_createexam), getActivity());
                    btn_exam_setquestion.setVisibility(View.VISIBLE);
                } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                    Utils.showToast(responseObj.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseCreateExam api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseCreateExam Exception : " + e.toString());
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

    private void backToTrialScreen() {
        ((AuthorHostActivity) getActivity()).onBackPressed();
    }

    @Override
    public void onClick(View v) {

        if (v == btn_exam_save) {

            if (isInputsValid()) {
                callApiCreateExam();
            }

        } else if (v == btn_exam_setquestion) {

            ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_ADDQUESTION);

        } else if (v == btn_exam_cancel) {

            backToTrialScreen();

        }

    }
}
