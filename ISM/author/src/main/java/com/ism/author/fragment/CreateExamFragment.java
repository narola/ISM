package com.ism.author.fragment;


import android.app.Fragment;
import android.os.Bundle;
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

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.InputValidator;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.Adapters;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Classrooms;
import com.ism.author.ws.model.Subjects;
import com.ism.author.ws.model.Topics;
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
    public static CreateExamFragment newInstance(Bundle bundleArgument) {
        CreateExamFragment createExamFragment = new CreateExamFragment();
        createExamFragment.setArguments(bundleArgument);
        return createExamFragment;
    }

    public CreateExamFragment() {
        // Required empty public constructor
    }

    private TextView tvExamTitle, tvExamExamfor, tvExamExamschedule, tvExamExaminstruction, tvExamDeclareresult,
            tvExamNegativemarking, tvExamRandomquestion, tvExamUsescore, tvExamQuestionscorevalue, tvExamAddnegativemark;
    private Spinner spExamClassroom, spExamSubjectname, spExamSubjecttopic, spExamPassingpercent, spExamExamCategory, spExamExammode,
            spExamExamduration;
    private ArrayList<Topics> arrListTopic;
    private ArrayList<Classrooms> arrListClassRooms;
    private ArrayList<Subjects> arrListSubject;
    private List<String> arrListDefalt, arrListPassingPercent, arrListExamDuration, arrListExamMode, arrListExamCategory;
    private ToggleButton tbExamSelectexamfor;
    private EditText etExamName, etExamStartdate, etExamEnddate, etExamQuestionscorevalue, etExamAttemptcount, etExamAddnegativemark;
    private CheckBox cbExamStartdateNotify, cbExamEnddateNotify;
    private RadioGroup radioDeclareresult, radioNegativemarking, radioExamRandomQuestion, radioExamUsescore;
    private LinearLayout llAddQuestionscore, llAddNegativeMark;

    private Button btnExamSave, btnExamSetquestion, btnExamCancel;
    private RichTextEditor rteTrialExam;


    private MyTypeFace myTypeFace;

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

        tvExamTitle = (TextView) view.findViewById(R.id.tv_exam_title);

        tvExamExamfor = (TextView) view.findViewById(R.id.tv_exam_examfor);
        tvExamExamschedule = (TextView) view.findViewById(R.id.tv_exam_examschedule);
        tvExamExaminstruction = (TextView) view.findViewById(R.id.tv_exam_examinstruction);
        tvExamDeclareresult = (TextView) view.findViewById(R.id.tv_exam_declareresult);
        tvExamNegativemarking = (TextView) view.findViewById(R.id.tv_exam_negativemarking);
        tvExamRandomquestion = (TextView) view.findViewById(R.id.tv_exam_randomquestion);
        tvExamUsescore = (TextView) view.findViewById(R.id.tv_exam_usescore);
        tvExamQuestionscorevalue = (TextView) view.findViewById(R.id.tv_exam_questionscorevalue);
        tvExamAddnegativemark = (TextView) view.findViewById(R.id.tv_exam_addnegativemark);

        tvExamTitle.setTypeface(myTypeFace.getRalewayRegular());
        tvExamExamfor.setTypeface(myTypeFace.getRalewayRegular());
        tvExamExamschedule.setTypeface(myTypeFace.getRalewayRegular());
        tvExamExaminstruction.setTypeface(myTypeFace.getRalewayRegular());
        tvExamDeclareresult.setTypeface(myTypeFace.getRalewayRegular());
        tvExamNegativemarking.setTypeface(myTypeFace.getRalewayRegular());
        tvExamRandomquestion.setTypeface(myTypeFace.getRalewayRegular());
        tvExamUsescore.setTypeface(myTypeFace.getRalewayRegular());
        tvExamQuestionscorevalue.setTypeface(myTypeFace.getRalewayRegular());
        tvExamAddnegativemark.setTypeface(myTypeFace.getRalewayRegular());


        spExamClassroom = (Spinner) view.findViewById(R.id.sp_exam_classroom);
        spExamSubjectname = (Spinner) view.findViewById(R.id.sp_exam_subjectname);
        spExamSubjecttopic = (Spinner) view.findViewById(R.id.sp_exam_subjecttopic);
        spExamPassingpercent = (Spinner) view.findViewById(R.id.sp_exam_passingpercent);
        spExamExamCategory = (Spinner) view.findViewById(R.id.sp_exam_examcategory);
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

        cbExamStartdateNotify = (CheckBox) view.findViewById(R.id.cb_exam_startdate_notify);
        cbExamEnddateNotify = (CheckBox) view.findViewById(R.id.cb_exam_enddate_notify);

        radioDeclareresult = (RadioGroup) view.findViewById(R.id.radio_declareresult);
        radioNegativemarking = (RadioGroup) view.findViewById(R.id.radio_negativemarking);
        radioExamRandomQuestion = (RadioGroup) view.findViewById(R.id.radio_exam_random_question);
        radioExamUsescore = (RadioGroup) view.findViewById(R.id.radio_exam_usescore);


        btnExamSave = (Button) view.findViewById(R.id.btn_exam_save);
        btnExamSetquestion = (Button) view.findViewById(R.id.btn_exam_setquestion);
        btnExamCancel = (Button) view.findViewById(R.id.btn_exam_cancel);

        btnExamSave.setTypeface(myTypeFace.getRalewayRegular());
        btnExamSetquestion.setTypeface(myTypeFace.getRalewayRegular());
        btnExamCancel.setTypeface(myTypeFace.getRalewayRegular());

        llAddQuestionscore = (LinearLayout) view.findViewById(R.id.ll_add_questionscore);
        llAddNegativeMark = (LinearLayout) view.findViewById(R.id.ll_add_negative_mark);


        arrListDefalt = new ArrayList<String>();
        arrListDefalt.add(getString(R.string.select));


        getPassingPercentSpinnerValues();
        Adapters.setUpSpinner(getActivity(), spExamPassingpercent, arrListPassingPercent, Adapters.ADAPTER_NORMAL);

        getExamDurationSpinnerValues();
        Adapters.setUpSpinner(getActivity(), spExamExamduration, arrListExamDuration, Adapters.ADAPTER_NORMAL);

        arrListExamMode = new ArrayList<String>();
        arrListExamMode = Arrays.asList(getResources().getStringArray(R.array.exammode));
        Adapters.setUpSpinner(getActivity(), spExamExammode, arrListExamMode, Adapters.ADAPTER_NORMAL);


        arrListExamCategory = new ArrayList<String>();
        arrListExamCategory = Arrays.asList(getResources().getStringArray(R.array.examname));
        Adapters.setUpSpinner(getActivity(), spExamExamCategory, arrListExamCategory, Adapters.ADAPTER_NORMAL);

        etExamStartdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    examStartDate = Utils.showDatePickerDob(getActivity(), etExamStartdate);
                }
                return true;
            }
        });

        etExamEnddate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    examEndDate = Utils.showDatePickerDob(getActivity(), etExamEnddate);
                }
                return true;
            }
        });


        btnExamSave.setOnClickListener(this);
        btnExamSetquestion.setOnClickListener(this);
        btnExamCancel.setOnClickListener(this);


        radioNegativemarking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (((RadioButton) view.findViewById(checkedId)).getText().toString().toLowerCase().equals("yes")) {
                    llAddNegativeMark.setVisibility(View.VISIBLE);
                } else {
                    llAddNegativeMark.setVisibility(View.GONE);
                }
            }
        });

        radioExamUsescore.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if (((RadioButton) view.findViewById(checkedId)).getText().toString().toLowerCase().equals("yes")) {
                    llAddQuestionscore.setVisibility(View.VISIBLE);
                } else {
                    llAddQuestionscore.setVisibility(View.GONE);
                }
            }
        });


        rteTrialExam = (RichTextEditor) view.findViewById(R.id.rte_trial_exam);
        rteTrialExam.getRichEditor().setEditorFontSize(25);
        rteTrialExam.getRichEditor().setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                strAssignmenttext = text;
            }
        });

        if (getArguments() != null) {
            setExamDetails();
        }
        callApiGetClassrooms();
        callApiGetSubjects();

    }


    private void setExamDetails() {

        etExamName.setText(getArguments().getString(ExamsAdapter.ARG_EXAM_NAME));
        spExamPassingpercent.setSelection(arrListPassingPercent.indexOf(getArguments().getString(ExamsAdapter.ARG_PASS_PERCENTAGE)));
        setExamType(getArguments().getString(ExamsAdapter.ARG_EXAM_TYPE));
        spExamExamCategory.setSelection(arrListExamCategory.indexOf(getArguments().getString(ExamsAdapter.ARG_EXAM_CATEGORY)));
        spExamExammode.setSelection(arrListExamMode.indexOf(getArguments().getString(ExamsAdapter.ARG_EXAM_MODE)));
        spExamExamduration.setSelection(arrListExamDuration.indexOf(getArguments().getString(ExamsAdapter.ARG_EXAM_DURATION)));

    }

    private void callApiGetClassrooms() {
        if (Utility.isOnline(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETCLASSROOMS);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }

    private void callApiGetSubjects() {
        if (Utility.isOnline(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETSUBJECT);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    private void callApiGetTopics(int subject_id) {
        if (Utility.isOnline(getActivity())) {
            try {
                Attribute attribute = new Attribute();
                attribute.setSubjectId(String.valueOf(subject_id));
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETTOPICS);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    private void callApiCreateExam() {

        if (Utility.isOnline(getActivity())) {
            try {
                Attribute attribute = new Attribute();

                attribute.setExamName(etExamName.getText().toString());
                attribute.setClassroomId(String.valueOf(spExamClassroom.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListClassRooms.
                        get(spExamClassroom.getSelectedItemPosition() - 1).getId()) : 0));
                attribute.setSubjectId(String.valueOf(spExamSubjectname.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListSubject.
                        get(spExamSubjectname.getSelectedItemPosition() - 1).getId()) : 0));
                attribute.setAttemptCount(String.valueOf(Integer.valueOf(etExamAttemptcount.getText().toString())));
                attribute.setExamType(getExamType());
                attribute.setExamCategory(arrListExamCategory.get(spExamExamCategory.getSelectedItemPosition()));
                attribute.setExamMode(arrListExamMode.get(spExamExammode.getSelectedItemPosition()));
                attribute.setPassingPercent(arrListPassingPercent.get(spExamPassingpercent.getSelectedItemPosition()));
                attribute.setExamDuration(arrListExamDuration.get(spExamExamduration.getSelectedItemPosition()));
                attribute.setExamInstruction(strAssignmenttext);
                attribute.setDeclareResults(getRadioGropuSelection(radioDeclareresult));
                attribute.setNegativeMarking(getRadioGropuSelection(radioNegativemarking));
                attribute.setRandomQuestion(getRadioGropuSelection(radioExamRandomQuestion));
                attribute.setExamStartDate(Utils.getDateInApiFormat(etExamStartdate.getText().toString()));

                attribute.setExamStartTime("5:00:00");
                attribute.setUserId("370");
                if (etExamAddnegativemark.getText().toString().isEmpty()) {
                    attribute.setNegativeMarkValue("0");
                } else {
                    attribute.setNegativeMarkValue(etExamAddnegativemark.getText().toString());
                }

                attribute.setBookId(String.valueOf(0));

                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.CREATEEXAM);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    //local field validations
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
        if (spExamExamCategory.getSelectedItemPosition() > 0) {
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

    private void setExamType(String value) {
        if (value.equals("subject")) {
            tbExamSelectexamfor.setChecked(true);
        } else if (value.equals("topic")) {
            tbExamSelectexamfor.setChecked(false);
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
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    arrListClassRooms = new ArrayList<Classrooms>();
                    arrListClassRooms.addAll(responseHandler.getClassrooms());
                    List<String> classrooms = new ArrayList<String>();
                    classrooms.add(getActivity().getString(R.string.strclass));
                    for (Classrooms classroom : arrListClassRooms) {
                        classrooms.add(classroom.getClassName());

                    }
                    Adapters.setUpSpinner(getActivity(), spExamClassroom, classrooms, Adapters.ADAPTER_NORMAL);
                    if (getArguments() != null) {
                        Debug.e(TAG, "The index for subject is:::" + arrListClassRooms.indexOf(getArguments().getString(ExamsAdapter.ARG_CLASSROOM_NAME)));
                        spExamClassroom.setSelection(arrListClassRooms.indexOf(getArguments().getString(ExamsAdapter.ARG_CLASSROOM_NAME)) + 1);
                    }


                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
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
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    arrListSubject = new ArrayList<Subjects>();
                    arrListSubject.addAll(responseHandler.getSubjects());
                    List<String> subjects = new ArrayList<String>();
                    subjects.add(getString(R.string.strsubjectname));
                    for (Subjects subject : arrListSubject) {
                        subjects.add(subject.getSubjectName());
                    }
                    Adapters.setUpSpinner(getActivity(), spExamSubjectname, subjects, Adapters.ADAPTER_NORMAL);

                    if (getArguments() != null) {
                        Debug.e(TAG, "The index for subject is:::" + arrListSubject.indexOf(getArguments().getString(ExamsAdapter.ARG_SUBJECT_NAME)));
                        spExamSubjectname.setSelection(arrListSubject.indexOf(getArguments().getString(ExamsAdapter.ARG_SUBJECT_NAME)) + 1);
                    }
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
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
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListTopic = new ArrayList<Topics>();
                    arrListTopic.addAll(responseHandler.getTopics());
                    List<String> topics = new ArrayList<String>();
                    topics.add(getString(R.string.strtopic));
                    for (Topics topic : arrListTopic) {
                        topics.add(topic.getTopicName());
                    }
                    Adapters.setUpSpinner(getActivity(), spExamSubjecttopic, topics, Adapters.ADAPTER_NORMAL);
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Adapters.setUpSpinner(getActivity(), spExamSubjecttopic, arrListDefalt, Adapters.ADAPTER_NORMAL);
                    Utils.showToast(responseHandler.getMessage(), getActivity());
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
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    Utils.showToast(getActivity().getString(R.string.msg_success_createexam), getActivity());
                    btnExamSetquestion.setVisibility(View.VISIBLE);
//                    if (getArguments() == null) {
//                        bundleArgument = new Bundle();
//                    }
//                    bundleArgument.putString(ExamsAdapter.ARG_EXAM_ID, String.valueOf(responseHandler.getCreateExam().get(0).getExamId()));
//                    fragmentArgument.getFragmentArgumentObject().setExamId(String.valueOf(responseHandler.getCreateExam().get(0).getExamId()));
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
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
        if (v == btnExamSave) {
            if (isInputsValid()) {
                callApiCreateExam();
            }
        } else if (v == btnExamSetquestion) {
            ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_ADDQUESTION_CONTAINER, null);
        } else if (v == btnExamCancel) {
            backToTrialScreen();
        }

    }


}
