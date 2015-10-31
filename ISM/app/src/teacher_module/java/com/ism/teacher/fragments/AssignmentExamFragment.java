package com.ism.teacher.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ism.R;

import com.ism.interfaces.FragmentListener;
import com.ism.object.MyTypeFace;
import com.ism.teacher.Utility.Utils;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.login.TeacherHomeActivity;
import com.ism.teacher.model.CreateExamRequest;
import com.ism.teacher.model.Data;
import com.ism.teacher.model.GetTopicsRequest;
import com.ism.teacher.model.ResponseObject;
import com.ism.teacher.ws.WebserviceWrapper;
import com.ism.utility.Debug;
import com.ism.utility.InputValidator;
import com.ism.utility.Utility;
import com.narola.kpa.richtexteditor.view.RichTextEditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by c166 on 28/10/15.
 */
public class AssignmentExamFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = AssignmentExamFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;

    public static AssignmentExamFragment newInstance() {
        AssignmentExamFragment assignmentExamFragment = new AssignmentExamFragment();
        return assignmentExamFragment;
    }

    public AssignmentExamFragment() {
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
        view = inflater.inflate(R.layout.fragment_assignment_exam, container, false);

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

        ll_add_questionscore = (LinearLayout) view.findViewById(R.id.ll_add_questionscore);
        ll_add_negative_mark = (LinearLayout) view.findViewById(R.id.ll_add_negative_mark);


        arrListDefalt = new ArrayList<String>();
        arrListDefalt.add(getString(R.string.select));


        getPassingPercentSpinnerValues();
        Adapters.setUpSpinner(getActivity(), sp_exam_passingpercent, arrListPassingPercent);

        getExamDurationSpinnerValues();
        Adapters.setUpSpinner(getActivity(), sp_exam_examduration, arrListExamDuration);

        arrListExamMode = new ArrayList<String>();
        arrListExamMode.add(getString(R.string.strexammode));
        arrListExamMode = Arrays.asList(getResources().getStringArray(R.array.exammode));
        Adapters.setUpSpinner(getActivity(), sp_exam_exammode, arrListExamMode);


        arrListExamName = new ArrayList<String>();
        arrListExamName.add(getString(R.string.strexamname));
        arrListExamName = Arrays.asList(getResources().getStringArray(R.array.examname));
        Adapters.setUpSpinner(getActivity(), sp_exam_examname, arrListExamName);

//        sp_exam_subjectname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
//                    com.ism.adapter.Adapters.setUpSpinner(getActivity(), sp_exam_subjecttopic, arrListDefalt);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


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


        btn_exam_save.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 if (isInputsValid()) {
                                                     callApiCreateExam();
                                                 }


                                             }
                                         }
        );

        btn_exam_setquestion.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                    }
                                                }
        );


        btn_exam_cancel.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   backToTrialScreen();
                                               }
                                           }
        );


        radio_negativemarking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

        callApiGetClassRooms();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                //fragListener.onFragmentAttached(TrialAddNewFragment.FRAGMENT_TRIAL_EXAM);
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
                //fragListener.onFragmentDetached(TrialAddNewFragment.FRAGMENT_TRIAL_EXAM);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    private void callApiGetClassRooms() {

        if (Utils.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETCLASSROOMS);
            } catch (Exception e) {
                //Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getActivity().getResources().getString(R.string.no_internet), getActivity());
        }

    }

    private void callApiGetSubjects() {

        if (Utils.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETSUBJECT);
            } catch (Exception e) {
                //Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getActivity().getResources().getString(R.string.no_internet), getActivity());
        }

    }


    private void callApiGetTopics(int subject_id) {

        if (Utils.isInternetConnected(getActivity())) {
            try {
                GetTopicsRequest getTopicsRequest = new GetTopicsRequest();
                getTopicsRequest.setSubject_id(subject_id);
                new WebserviceWrapper(getActivity(), getTopicsRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETTOPICS);
            } catch (Exception e) {
                //Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getActivity().getResources().getString(R.string.no_internet), getActivity());
        }

    }


    private void callApiCreateExam() {


        if (Utils.isInternetConnected(getActivity())) {
            try {
                CreateExamRequest createExamRequest = new CreateExamRequest();

                createExamRequest.setExam_name(et_exam_name.getText().toString());
                createExamRequest.setClassroom_id(sp_exam_classroom.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListClassRooms.
                        get(sp_exam_classroom.getSelectedItemPosition() - 1).getId()) : 0);
                createExamRequest.setSubject_id(sp_exam_subjectname.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListSubject.
                        get(sp_exam_subjectname.getSelectedItemPosition() - 1).getId()) : 0);
                createExamRequest.setAttempt_count(Integer.valueOf(et_exam_attemptcount.getText().toString()));
                createExamRequest.setExam_type(getExamType());
                createExamRequest.setExam_category(arrListExamName.get(sp_exam_examname.getSelectedItemPosition()));
                createExamRequest.setExam_mode(arrListExamMode.get(sp_exam_exammode.getSelectedItemPosition()));
                createExamRequest.setPassing_percent(arrListPassingPercent.get(sp_exam_passingpercent.getSelectedItemPosition()));
                createExamRequest.setExam_duration(arrListExamDuration.get(sp_exam_examduration.getSelectedItemPosition()));
                createExamRequest.setExam_instruction(strAssignmenttext);
                createExamRequest.setDeclare_results(getRadioGropuSelection(radio_declareresult));
                createExamRequest.setNegative_marking(getRadioGropuSelection(radio_negativemarking));
                createExamRequest.setRandom_question(getRadioGropuSelection(radio_exam_random_question));

//                createExamRequest.setExam_start_date(et_exam_startdate.getText().toString());
                createExamRequest.setExam_start_date(Utils.getDateInApiFormat(et_exam_startdate.getText().toString()));
                Log.e("date", "" + Utils.getDateInApiFormat(et_exam_startdate.getText().toString()));

                createExamRequest.setExam_start_time("5:00:00");
                createExamRequest.setUser_id("370");
                if (et_exam_addnegativemark.getText().toString().equalsIgnoreCase("")) {
                    createExamRequest.setNegative_mark_value("0");
                } else {
                    createExamRequest.setNegative_mark_value(et_exam_addnegativemark.getText().toString());
                }

                createExamRequest.setBook_id(0);

                new WebserviceWrapper(getActivity(), createExamRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.CREATEEXAM);
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getActivity().getResources().getString(R.string.no_internet), getActivity());
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
    public void onResponse(int apiMethodName, Object object, Exception error) {

        try {
            if (apiMethodName == WebserviceWrapper.GETCLASSROOMS) {

                ResponseObject callGetClassRoomsResponse = (ResponseObject) object;
                if (callGetClassRoomsResponse.getStatus().equals(AppConstant.API_STATUS_SUCCESS) && callGetClassRoomsResponse != null) {

                    arrListClassRooms = new ArrayList<Data>();
                    arrListClassRooms.addAll(callGetClassRoomsResponse.getData());
                    List<String> classrooms = new ArrayList<String>();
                    classrooms.add(getString(R.string.strclass));
                    for (Data course : arrListClassRooms) {
                        classrooms.add(course.getClass_name());

                    }
                    Adapters.setUpSpinner(getActivity(), sp_exam_classroom, classrooms);
                    callApiGetSubjects();

                } else {
                    Utils.showToast(callGetClassRoomsResponse.getMessage(), getActivity());
                }

            } else if (apiMethodName == WebserviceWrapper.GETSUBJECT) {

                ResponseObject callGetSubjectResponseObject = (ResponseObject) object;
                if (callGetSubjectResponseObject.getStatus().equals(AppConstant.API_STATUS_SUCCESS) && callGetSubjectResponseObject != null) {

                    arrListSubject = new ArrayList<Data>();
                    arrListSubject.addAll(callGetSubjectResponseObject.getData());
                    List<String> subjects = new ArrayList<String>();
                    subjects.add(getString(R.string.strsubjectname));
                    for (Data subject : arrListSubject) {
                        subjects.add(subject.getSubject_name());

                    }

                    Adapters.setUpSpinner(getActivity(), sp_exam_subjectname, subjects);

                } else {
                    Utils.showToast(callGetSubjectResponseObject.getMessage(), getActivity());
                }

            } else if (apiMethodName == WebserviceWrapper.GETTOPICS) {

                ResponseObject callGetTopicsResponseObject = (ResponseObject) object;
                if (callGetTopicsResponseObject.getStatus().equals(AppConstant.API_STATUS_SUCCESS) && callGetTopicsResponseObject != null) {

                    arrListTopic = new ArrayList<Data>();
                    arrListTopic.addAll(callGetTopicsResponseObject.getData());
                    List<String> topics = new ArrayList<String>();
                    topics.add(getString(R.string.strtopic));
                    for (Data topic : arrListTopic) {
                        topics.add(topic.getTopic_name());

                    }
                    Adapters.setUpSpinner(getActivity(), sp_exam_subjecttopic, topics);
                } else {
                    Adapters.setUpSpinner(getActivity(), sp_exam_subjecttopic, arrListDefalt);
                    Utils.showToast(callGetTopicsResponseObject.getMessage(), getActivity());
                }

            } else if (apiMethodName == WebserviceWrapper.CREATEEXAM) {

                ResponseObject callCreateExamResponse = (ResponseObject) object;
                if (callCreateExamResponse.getStatus().equals(AppConstant.API_STATUS_SUCCESS) && callCreateExamResponse != null) {

                    Utils.showToast("Exam Created Successfully", getActivity());

                } else {

                    Utils.showToast(callCreateExamResponse.getMessage(), getActivity());
                }

            }
        } catch (Exception e) {
            Log.e(TAG, "Assignment on response exception" + e.toString());
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
        ((TeacherHomeActivity) getActivity()).onBackPressed();
    }


}
