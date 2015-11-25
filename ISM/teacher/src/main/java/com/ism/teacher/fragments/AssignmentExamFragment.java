package com.ism.teacher.fragments;


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

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.adapters.ExamsAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.helper.InputValidator;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.model.FragmentArgument;
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
public class AssignmentExamFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = AssignmentExamFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    String exam_id = "78";

    Fragment mFragment;

    public static AssignmentExamFragment newInstance() {
        AssignmentExamFragment assignmentExamFragment = new AssignmentExamFragment();
        return assignmentExamFragment;
    }

    public AssignmentExamFragment() {
        // Required empty public constructor
    }

    public AssignmentExamFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    TextView tv_exam_title, tv_exam_examfor, tv_exam_examschedule, tv_exam_examinstruction, tv_exam_declareresult,
            tv_exam_negativemarking, tv_exam_randomquestion, tv_exam_usescore, tv_exam_questionscorevalue, tv_exam_addnegativemark;
    Spinner spExamClassroom, spExamSubjectname, spExamSubjecttopic, spExamPassingpercent, spExamExamname, spExamExammode,
            sp_exam_examduration;
    private ArrayList<Classrooms> arrListClassRooms;
    private ArrayList<Subjects> arrListSubject;
    private ArrayList<Topics> arrListTopic;
    List<String> arrListDefalt, arrListPassingPercent, arrListExamDuration, arrListExamMode, arrListExamName;


    ToggleButton tb_exam_selectexamfor;
    EditText etExamName, etExamStartdate, etExamEnddate, etExamQuestionscorevalue, etExamAttemptcount, etExamAddnegativemark;
    CheckBox cb_exam_startdate_notify, cb_exam_enddate_notify;
    RadioGroup radioDeclareresult, radioNegativemarking, radioExamRandomQuestion, radioExamUsescore;
    LinearLayout ll_add_questionscore, ll_add_negative_mark;

    Button btn_exam_save, btn_exam_setquestion, btn_exam_cancel;

    RichTextEditor rte_trial_exam;


    MyTypeFace myTypeFace;

    private static int PASSINGPERCENT_INTERVAL = 5, PASSINGPERCENT_STARTVALUE = 30, PASSINGPERCENT_ENDVALUE = 99;
    private static int EXAMDURATION_INTERVAL = 30, EXAMDURATION_STARTVALUE = 30, EXAMDURATION_ENDVALUE = 300;

    String examStartDate = "", examEndDate = "", strAssignmenttext = "";

    private InputValidator inputValidator;

    AddQuestionContainerFragment addQuestionContainerFragment;
    private FragmentArgument fragmentArgument = new FragmentArgument();

    private Bundle bundleArgument;

    public static AssignmentExamFragment newInstance(Bundle bundleArgument) {
        AssignmentExamFragment createExamFragment = new AssignmentExamFragment();
        createExamFragment.setArguments(bundleArgument);
        createExamFragment.bundleArgument = bundleArgument;
        return createExamFragment;
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
        sp_exam_examduration = (Spinner) view.findViewById(R.id.sp_exam_examduration);


        tb_exam_selectexamfor = (ToggleButton) view.findViewById(R.id.tb_exam_selectexamfor);

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


        btn_exam_save = (Button) view.findViewById(R.id.btn_exam_save);
        btn_exam_setquestion = (Button) view.findViewById(R.id.btn_exam_setquestion);
        btn_exam_cancel = (Button) view.findViewById(R.id.btn_exam_cancel);

        ll_add_questionscore = (LinearLayout) view.findViewById(R.id.ll_add_questionscore);
        ll_add_negative_mark = (LinearLayout) view.findViewById(R.id.ll_add_negative_mark);


        arrListDefalt = new ArrayList<String>();
        arrListDefalt.add(getString(R.string.select));


        getPassingPercentSpinnerValues();
        Adapters.setUpSpinner(getActivity(), spExamPassingpercent, arrListPassingPercent);

        getExamDurationSpinnerValues();
        Adapters.setUpSpinner(getActivity(), sp_exam_examduration, arrListExamDuration);

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
                                                        if (exam_id != null && !exam_id.equalsIgnoreCase("")) {
                                                            Log.e("examid", exam_id);

//                                                            addQuestionContainerFragment=new AddQuestionContainerFragment(AssignmentExamFragment.this, exam_id);
//                                                            mFragment.getFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, addQuestionContainerFragment).commit();

//                                                            ((TeacherHostActivity) getActivity()).loadAddQuestionFragment(TeacherHostActivity.FRAGMENT_ADDQUESTION, exam_id);

                                                            ((TeacherHostActivity) getActivity()).loadAddQuestionFragment(TeacherHostActivity.FRAGMENT_ADDQUESTION, fragmentArgument);
                                                        } else {
                                                            Log.e(TAG, "Setting question with exam id null");
                                                        }
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
                attribute.setExamDuration(arrListExamDuration.get(sp_exam_examduration.getSelectedItemPosition()));
                attribute.setExamInstruction(strAssignmenttext);
                attribute.setDeclareResults(getRadioGropuSelection(radioDeclareresult));
                attribute.setNegativeMarking(getRadioGropuSelection(radioNegativemarking));
                attribute.setRandomQuestion(getRadioGropuSelection(radioExamRandomQuestion));
                attribute.setExamStartDate(Utility.getDateInApiFormat(etExamStartdate.getText().toString()));

                attribute.setExamStartTime("5:00:00");
                attribute.setUserId("370");
                if (etExamAddnegativemark.getText().toString().isEmpty()) {
                    attribute.setNegativeMarkValue("0");
                } else {
                    attribute.setNegativeMarkValue(etExamAddnegativemark.getText().toString());
                }

                attribute.setBookId(String.valueOf(0));

                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.CREATE_EXAM);
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getActivity().getResources().getString(R.string.no_internet), getActivity());
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
        if (arrListExamDuration != null && arrListExamDuration.size() == 0 || sp_exam_examduration.getSelectedItemPosition() > 0) {
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

            switch (apiMethodName) {

                case WebConstants.GET_CLASSROOMS:
                    onResponseGetClassRooms(object);
                    break;

                case WebConstants.GET_SUBJECT:
                    onResponseGetSubject(object);
                    break;
                case WebConstants.GET_TOPICS:
                    onResponseGetTopics(object);
                    break;
                case WebConstants.CREATE_EXAM:
                    onResponseCreateExam(object);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "Assignment on response exception" + e.toString());
        }


    }

    private void onResponseGetClassRooms(Object object) {
        ResponseHandler responseHandler = (ResponseHandler) object;
        if (responseHandler.getStatus().equals(AppConstant.API_STATUS_SUCCESS) && responseHandler != null) {

            arrListClassRooms = new ArrayList<>();
            arrListClassRooms.addAll(responseHandler.getClassrooms());
            List<String> classrooms = new ArrayList<String>();
            classrooms.add(getString(R.string.strclass));
            for (Classrooms classrooms1 : arrListClassRooms) {
                classrooms.add(classrooms1.getClassName());

            }
            Adapters.setUpSpinner(getActivity(), spExamClassroom, classrooms, Adapters.ADAPTER_NORMAL);
            callApiGetSubjects();

        } else {
            Utility.showToast(responseHandler.getMessage(), getActivity());
        }
    }

    private void onResponseGetSubject(Object object) {
        ResponseHandler responseHandler = (ResponseHandler) object;
        if (responseHandler.getStatus().equals(AppConstant.API_STATUS_SUCCESS) && responseHandler != null) {

            arrListSubject = new ArrayList<>();
            arrListSubject.addAll(responseHandler.getSubjects());
            List<String> subjects = new ArrayList<String>();
            subjects.add(getString(R.string.strsubjectname));
            for (Subjects subject : arrListSubject) {
                subjects.add(subject.getSubjectName());

            }

            Adapters.setUpSpinner(getActivity(), spExamSubjectname, subjects, Adapters.ADAPTER_NORMAL);

        } else {
            Utility.showToast(responseHandler.getMessage(), getActivity());
        }
    }

    private void onResponseGetTopics(Object object) {
        ResponseHandler responseHandler = (ResponseHandler) object;
        if (responseHandler.getStatus().equals(AppConstant.API_STATUS_SUCCESS) && responseHandler != null) {

            arrListTopic = new ArrayList<>();
            arrListTopic.addAll(responseHandler.getTopics());
            List<String> topics = new ArrayList<String>();
            topics.add(getString(R.string.strtopic));
            for (Topics topic : arrListTopic) {
                topics.add(topic.getTopicName());

            }
            Adapters.setUpSpinner(getActivity(), spExamSubjecttopic, topics);
        } else {
            Adapters.setUpSpinner(getActivity(), spExamSubjecttopic, arrListDefalt);
            Utility.showToast(responseHandler.getMessage(), getActivity());
        }
    }

    private void onResponseCreateExam(Object object) {

        ResponseHandler responseHandler = (ResponseHandler) object;
        if (responseHandler.getStatus().equals(AppConstant.API_STATUS_SUCCESS) && responseHandler != null) {

            //exam_id = callCreateExamResponse.getData().get(0).getExam_id();
            Utility.showToast("Exam Created Successfully", getActivity());
            btn_exam_setquestion.setVisibility(View.VISIBLE);
            if (getArguments() == null) {
                bundleArgument = new Bundle();
            }
            bundleArgument.putString(ExamsAdapter.ARG_EXAM_ID, String.valueOf(responseHandler.getCreateExam().get(0).getExamId()));
            // fragmentArgument.getRequestObject().setExamId(callCreateExamResponse.getData().get(0).getExam_id());

        } else {

            Utility.showToast(responseHandler.getMessage(), getActivity());
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
        ((TeacherHostActivity) getActivity()).onBackPressed();
    }


}
