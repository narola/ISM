package com.ism.teacher.fragments;


import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.adapters.AssignmentsAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.helper.InputValidator;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Classrooms;
import com.ism.teacher.ws.model.Subjects;
import com.ism.teacher.ws.model.Topics;
import com.narola.kpa.richtexteditor.view.RichTextEditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AssignmentExamFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {

    private View view;
    private Context mContext;
    private Fragment fragmentContext;

    private static final String TAG = AssignmentExamFragment.class.getSimpleName();
    private TextView tvExamTitle, tvExamExamfor, tvExamExaminstruction, tvExamDeclareresult,
            tvExamNegativemarking, tvExamRandomquestion, tvExamUsescore, tvExamQuestionscorevalue, tvExamAddnegativemark;

    private TextView tvExamName, tvExamClass, tvSubjectName, tvExamPassingpercent, tvExamCategory, tvExamExammode,
            tvExamExamduration, tvExamAttemptcount, tvExamschedule, tvExamStartdate, tvExamStartTime, tvExamAssessor;

    private Spinner spExamClassroom, spExamSubjectname, spExamSubjecttopic, spExamPassingpercent, spExamExamCategory, spExamExammode,
            spExamExamduration, spExamAssessor, spExamQuestionScore;
    private ToggleButton tbExamSelectexamfor;
    private EditText etExamName, etExamStartdate, etExamStartTime, etExamAttemptcount, etExamAddnegativemark;
    private CheckBox cbExamStartdateNotify, cbExamEnddateNotify;
    private RadioGroup radioDeclareresult, radioNegativemarking, radioExamRandomQuestion, radioExamUsescore;
    private LinearLayout llAddQuestionscore, llAddNegativeMark, llExamStartdate, llExamStartTime, llTopicSpinner;
    private Button btnExamSave, btnExamSetquestion, btnExamCancel;
    private RichTextEditor rteTrialExam;
    private ScrollView svCreateExam;


    private MyTypeFace myTypeFace;
    private static int PASSINGPERCENT_INTERVAL = 5, PASSINGPERCENT_STARTVALUE = 30, PASSINGPERCENT_ENDVALUE = 99;
    private static int EXAMDURATION_INTERVAL = 30, EXAMDURATION_STARTVALUE = 30, EXAMDURATION_ENDVALUE = 300;
    private static int QUESTIONSCORE_INERVAL = 1, QUESTIONSCORE_STARTVALUE = 1, QUESTIONSCORE_ENDVALUE = 5;

    String examStartDate = "";
    private InputValidator inputValidator;
    List<String> topicdefaultList = new ArrayList<>();
    private List<String> arrListDefalt, arrListPassingPercent, arrListExamDuration, arrListExamMode, arrListExamCategory, arrListExamAssessor;

    private ArrayList<Topics> arrListTopic;
    private ArrayList<Classrooms> arrListClassRooms;
    private ArrayList<Subjects> arrListSubject;
    public List<String> arrListQuestionScore;

    /**
     * Fragment Args
     */
    public static String ARG_IS_CREATE_EXAM = "isCreateExam";


    public AssignmentExamFragment() {
        // Required empty public constructor
    }

    public static AssignmentExamFragment newInstance(Fragment fragment, Context mContext, Bundle bundleArgument) {
        AssignmentExamFragment assignmentExamFragment = new AssignmentExamFragment();

        if (bundleArgument != null) {
            assignmentExamFragment.setArguments(bundleArgument);
            assignmentExamFragment.getArguments().putBoolean(ARG_IS_CREATE_EXAM, false);
        } else {
            Bundle bundle = new Bundle();
            bundle.putBoolean(ARG_IS_CREATE_EXAM, true);
            assignmentExamFragment.setArguments(bundle);
        }

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

        myTypeFace = new MyTypeFace(mContext);
        inputValidator = new InputValidator(mContext);
        llExamStartTime = (LinearLayout) view.findViewById(R.id.ll_exam_startTime);
        llTopicSpinner = (LinearLayout) view.findViewById(R.id.ll_topic_spinner);

        spExamQuestionScore = (Spinner) view.findViewById(R.id.spExamQuestionScore);
        tvExamName = (TextView) view.findViewById(R.id.tv_exam_name);
        tvExamClass = (TextView) view.findViewById(R.id.tv_exam_class);
        tvSubjectName = (TextView) view.findViewById(R.id.tv_subject_name);
        tvExamPassingpercent = (TextView) view.findViewById(R.id.tv_exam_passingpercent);
        tvExamCategory = (TextView) view.findViewById(R.id.tv_exam_category);
        tvExamExammode = (TextView) view.findViewById(R.id.tv_exam_exammode);
        tvExamExamduration = (TextView) view.findViewById(R.id.tv_exam_examduration);
        tvExamAttemptcount = (TextView) view.findViewById(R.id.tv_exam_attemptcount);
        tvExamschedule = (TextView) view.findViewById(R.id.tv_exam_examschedule);
        tvExamStartdate = (TextView) view.findViewById(R.id.tv_exam_startdate);
        tvExamStartTime = (TextView) view.findViewById(R.id.tv_exam_startTime);
        tvExamAssessor = (TextView) view.findViewById(R.id.tv_exam_assessor);

        tvExamName.setTypeface(myTypeFace.getRalewayRegular());
        tvExamClass.setTypeface(myTypeFace.getRalewayRegular());
        tvSubjectName.setTypeface(myTypeFace.getRalewayRegular());
        tvExamPassingpercent.setTypeface(myTypeFace.getRalewayRegular());
        tvExamCategory.setTypeface(myTypeFace.getRalewayRegular());
        tvExamExammode.setTypeface(myTypeFace.getRalewayRegular());
        tvExamExamduration.setTypeface(myTypeFace.getRalewayRegular());
        tvExamAttemptcount.setTypeface(myTypeFace.getRalewayRegular());
        tvExamschedule.setTypeface(myTypeFace.getRalewayRegular());
        tvExamStartdate.setTypeface(myTypeFace.getRalewayRegular());
        tvExamStartTime.setTypeface(myTypeFace.getRalewayRegular());

        spExamAssessor = (Spinner) view.findViewById(R.id.sp_exam_assessor);
        tvExamTitle = (TextView) view.findViewById(R.id.tv_exam_title);
        tvExamExamfor = (TextView) view.findViewById(R.id.tv_exam_examfor);
        tvExamExaminstruction = (TextView) view.findViewById(R.id.tv_exam_examinstruction);
        tvExamDeclareresult = (TextView) view.findViewById(R.id.tv_exam_declareresult);
        tvExamNegativemarking = (TextView) view.findViewById(R.id.tv_exam_negativemarking);
        tvExamRandomquestion = (TextView) view.findViewById(R.id.tv_exam_randomquestion);
        tvExamUsescore = (TextView) view.findViewById(R.id.tv_exam_usescore);
        tvExamQuestionscorevalue = (TextView) view.findViewById(R.id.tv_exam_questionscorevalue);
        tvExamAddnegativemark = (TextView) view.findViewById(R.id.tv_exam_addnegativemark);

        tvExamTitle.setTypeface(myTypeFace.getRalewayRegular());
        tvExamExamfor.setTypeface(myTypeFace.getRalewayRegular());
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
        etExamStartTime = (EditText) view.findViewById(R.id.et_exam_startTime);
        etExamAttemptcount = (EditText) view.findViewById(R.id.et_exam_attemptcount);
        etExamAddnegativemark = (EditText) view.findViewById(R.id.et_exam_addnegativemark);

        etExamName.setTypeface(myTypeFace.getRalewayRegular());
        etExamStartdate.setTypeface(myTypeFace.getRalewayRegular());
        etExamStartTime.setTypeface(myTypeFace.getRalewayRegular());
        etExamAddnegativemark.setTypeface(myTypeFace.getRalewayRegular());

        cbExamStartdateNotify = (CheckBox) view.findViewById(R.id.cb_exam_startdate_notify);
        cbExamEnddateNotify = (CheckBox) view.findViewById(R.id.cb_exam_enddate_notify);

        radioDeclareresult = (RadioGroup) view.findViewById(R.id.radio_declareresult);
        radioNegativemarking = (RadioGroup) view.findViewById(R.id.radio_negativemarking);
        radioExamRandomQuestion = (RadioGroup) view.findViewById(R.id.radio_exam_random_question);
        radioExamUsescore = (RadioGroup) view.findViewById(R.id.radio_exam_usescore);


        radioDeclareresult.check(R.id.radio_btn_declareresult_yes);
        radioNegativemarking.check(R.id.radio_btn_negativemarking_yes);
        radioExamRandomQuestion.check(R.id.radio_btn_randomquestion_yes);
        radioExamUsescore.check(R.id.radio_btn_examuserscore_yes);

        svCreateExam = (ScrollView) view.findViewById(R.id.sv_create_exam);


        btnExamSave = (Button) view.findViewById(R.id.btn_exam_save);
        btnExamSetquestion = (Button) view.findViewById(R.id.btn_exam_setquestion);
        btnExamCancel = (Button) view.findViewById(R.id.btn_exam_cancel);

        btnExamSave.setTypeface(myTypeFace.getRalewayRegular());
        btnExamSetquestion.setTypeface(myTypeFace.getRalewayRegular());
        btnExamCancel.setTypeface(myTypeFace.getRalewayRegular());

        llAddQuestionscore = (LinearLayout) view.findViewById(R.id.ll_add_questionscore);
        llAddNegativeMark = (LinearLayout) view.findViewById(R.id.ll_add_negative_mark);

        llAddQuestionscore.setVisibility(View.VISIBLE);
        llAddNegativeMark.setVisibility(View.VISIBLE);

        llExamStartdate = (LinearLayout) view.findViewById(R.id.ll_exam_startdate);


        arrListDefalt = new ArrayList<String>();
        arrListDefalt.add(Utility.getString(R.string.select, mContext));


        topicdefaultList.add(Utility.getString(R.string.strtopic, mContext));

        /**
         * For Spinner of topic
         */
        Adapters.setUpSpinner(mContext, spExamSubjecttopic, topicdefaultList, Adapters.ADAPTER_NORMAL);


        getPassingPercentSpinnerValues();
        Adapters.setUpSpinner(mContext, spExamPassingpercent, arrListPassingPercent, Adapters.ADAPTER_NORMAL);

        getExamDurationSpinnerValues();
        Adapters.setUpSpinner(mContext, spExamExamduration, arrListExamDuration, Adapters.ADAPTER_NORMAL);

        arrListExamMode = new ArrayList<String>();
        arrListExamMode = Arrays.asList(getResources().getStringArray(R.array.exammode));
        Adapters.setUpSpinner(mContext, spExamExammode, arrListExamMode, Adapters.ADAPTER_NORMAL);


        arrListExamCategory = new ArrayList<String>();
        arrListExamCategory = Arrays.asList(getResources().getStringArray(R.array.examname));
        Adapters.setUpSpinner(mContext, spExamExamCategory, arrListExamCategory, Adapters.ADAPTER_NORMAL);


        btnExamSave.setOnClickListener(this);
        btnExamSetquestion.setOnClickListener(this);
        btnExamCancel.setOnClickListener(this);

        llExamStartdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    examStartDate = Utility.showDatePickerDob(mContext, etExamStartdate);
                    etExamStartdate.setError(null);
                }
                return true;
            }
        });

        llExamStartTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            etExamStartTime.setText(selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
                return true;

            }
        });

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
        rteTrialExam.hideMediaControls();


        arrListExamAssessor = new ArrayList<String>();
        arrListExamAssessor.add(getString(R.string.strexamassessor));
        arrListExamAssessor.add(getString(R.string.strnoassessor));
//        arrListExamAssessor.add(Global.strFullName);
        Adapters.setUpSpinner(mContext, spExamAssessor, arrListExamAssessor, Adapters.ADAPTER_NORMAL);

        getQuestionScoreSpinnerValues();
        Adapters.setUpSpinner(mContext, spExamQuestionScore, arrListQuestionScore, Adapters.ADAPTER_NORMAL);


        spExamSubjectname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (arrListSubject != null && position > 0) {
                    callApiGetTopics(Integer.parseInt(arrListSubject.get(position - 1).getId()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tbExamSelectexamfor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if (isCheck) {
                    llTopicSpinner.setVisibility(View.INVISIBLE);

                } else {
                    llTopicSpinner.setVisibility(View.VISIBLE);
                }

            }
        });

        spExamSubjecttopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (arrListTopic != null) {
                    if (position > 1) {
                        getArguments().putString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID, arrListTopic.get(position - 2).getId());

                        Debug.e("test topic by position>1:", "topic name:" + arrListTopic.get(position - 2).getTopicName());
                    } else {
                        getArguments().putString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID, "0");
                        Debug.e("test topic by position=1:", "topic name:" + spExamSubjecttopic.getSelectedItem().toString());
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /**
         * Checking args to know this is new assign exam or edit assignment
         *  if (getArguments() != null) means edit assign
         *  so disable exam mode and subject selection spinner
         */

        //For New Exam

        if (getArguments().getBoolean(ARG_IS_CREATE_EXAM)) {
            btnExamSetquestion.setVisibility(View.GONE);
            btnExamSave.setVisibility(View.VISIBLE);

        }
        //for edit exam
        else {
            setExamDetails();
            btnExamSetquestion.setVisibility(View.VISIBLE);
            spExamExammode.setEnabled(false);
            spExamSubjectname.setEnabled(false);
        }


        callApiGetClassrooms();
        callApiGetSubjects();


    }

    private void getQuestionScoreSpinnerValues() {
        arrListQuestionScore = new ArrayList<String>();
        arrListQuestionScore.add(Utility.getString(R.string.strquestionscore, mContext));
        for (int i = QUESTIONSCORE_STARTVALUE; i <= QUESTIONSCORE_ENDVALUE; i += QUESTIONSCORE_INERVAL) {
            arrListQuestionScore.add(String.valueOf(i));
        }
    }


    private void setExamDetails() {
        etExamName.setText(getArguments().getString(AssignmentsAdapter.ARG_EXAM_NAME));
        spExamPassingpercent.setSelection(arrListPassingPercent.indexOf(getArguments().getString(AssignmentsAdapter.ARG_EXAM_PASS_PERCENTAGE)));
        setExamType(getArguments().getString(AssignmentsAdapter.ARG_EXAM_TYPE));
        spExamExamCategory.setSelection(arrListExamCategory.indexOf(getArguments().getString(AssignmentsAdapter.ARG_EXAM_CATEGORY)));
        spExamExammode.setSelection(arrListExamMode.indexOf(getArguments().getString(AssignmentsAdapter.ARG_EXAM_MODE)));
        spExamExamduration.setSelection(arrListExamDuration.indexOf(getArguments().getString(AssignmentsAdapter.ARG_EXAM_DURATION)));

            /*we cant change the exam mode and subject for that particular exam if it once created*/
        if (getArguments().getBoolean(GetObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY)) {

            btnExamSetquestion.setVisibility(View.GONE);
            btnExamSave.setVisibility(View.VISIBLE);

        } else {

            btnExamSetquestion.setVisibility(View.VISIBLE);
            btnExamSave.setVisibility(View.VISIBLE);
            btnExamSave.setText(getString(R.string.streditexam));

        }
        etExamName.setText(getArguments().getString(AssignmentsAdapter.ARG_EXAM_NAME));
        spExamPassingpercent.setSelection(arrListPassingPercent.indexOf(getArguments().getString(AssignmentsAdapter.ARG_EXAM_PASS_PERCENTAGE)));
        spExamAssessor.setSelection(1);
        spExamExamCategory.setSelection(arrListExamCategory.indexOf(getArguments().getString(AssignmentsAdapter.ARG_EXAM_CATEGORY)));
        spExamExammode.setSelection(arrListExamMode.indexOf(getArguments().getString(AssignmentsAdapter.ARG_EXAM_MODE)));
        spExamExamduration.setSelection(arrListExamDuration.indexOf(getArguments().getString(AssignmentsAdapter.ARG_EXAM_DURATION)));

        etExamAttemptcount.setText(getArguments().getString(AssignmentsAdapter.ARG_EXAM_ATTEMPT_COUNT));
        etExamStartdate.setText(getArguments().getString(AssignmentsAdapter.ARG_EXAM_START_DATE));
        etExamStartTime.setText(getArguments().getString(AssignmentsAdapter.ARG_EXAM_START_TIME));

        if (getArguments().getBoolean(AssignmentsAdapter.ARG_EXAM_IS_DECLARE_RESULTS)) {
            ((RadioButton) radioDeclareresult.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) radioDeclareresult.getChildAt(1)).setChecked(true);
        }

        if (getArguments().getBoolean(AssignmentsAdapter.ARG_EXAM_IS_NEGATIVE_MARKING)) {
            ((RadioButton) radioNegativemarking.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) radioNegativemarking.getChildAt(1)).setChecked(true);
        }

        etExamAddnegativemark.setText(getArguments().getString(AssignmentsAdapter.ARG_EXAM_NEGATIVE_MARK_VALUE));

        if (getArguments().getBoolean(AssignmentsAdapter.ARG_EXAM_IS_RANDOM_QUESTION)) {
            ((RadioButton) radioExamRandomQuestion.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) radioExamRandomQuestion.getChildAt(1)).setChecked(true);
        }


        if (getArguments().getBoolean(AssignmentsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE)) {
            ((RadioButton) radioExamUsescore.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) radioExamUsescore.getChildAt(1)).setChecked(true);
        }


        rteTrialExam.setHtml(getArguments().getString(AssignmentsAdapter.ARG_EXAM_INSTRUCTIONS));
        spExamQuestionScore.setSelection(arrListQuestionScore.indexOf(getArguments().getString(AssignmentsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE)));

    }

    /*These method has to be change after api integration of these values*/
    private String getExamAssessorId() {

        if (spExamAssessor.getSelectedItemPosition() == 1) {
            return Global.strUserId;
        } else {
            return Global.strUserId;
        }

    }

    private void callApiGetClassrooms() {


        if (Utility.isConnected(mContext)) {
            try {
                //   ((TeacherHostActivity) getActivity()).showProgress();
                new WebserviceWrapper(mContext, null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_CLASSROOMS);
            } catch (Exception e) {
                Debug.e(TAG + Utility.getString(R.string.strerrormessage, mContext), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(mContext);
        }
    }

    private void callApiGetSubjects() {
        if (Utility.isConnected(mContext)) {
            try {
                //     ((TeacherHostActivity) getActivity()).showProgress();
                new WebserviceWrapper(mContext, null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_SUBJECT);
            } catch (Exception e) {
                Debug.e(TAG + Utility.getString(R.string.strerrormessage, mContext), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(mContext);
        }
    }


    private void callApiGetTopics(int subject_id) {
        if (Utility.isConnected(mContext)) {
            try {
                //    ((TeacherHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setSubjectId(String.valueOf(subject_id));
                new WebserviceWrapper(mContext, attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_TOPICS);
            } catch (Exception e) {
                Debug.e(TAG + Utility.getString(R.string.strerrormessage, mContext), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(mContext);
        }
    }


    private void callApiCreateExam() {

        if (Utility.isConnected(mContext)) {
            try {
                ((TeacherHostActivity) getActivity()).hideProgress();
                Attribute attribute = new Attribute();

                /*set exam id "0" if you are creating new and copy exam ,In case of edit set particular exam id*/
                attribute.setExamId("0");
                if (getBundleArguments().containsKey(GetObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY)) {
                    if (!getBundleArguments().getBoolean(GetObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY)) {
                        attribute.setExamId(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_ID));
                    }
                }
                attribute.setExamName(etExamName.getText().toString());
                attribute.setClassroomId(String.valueOf(spExamClassroom.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListClassRooms.
                        get(spExamClassroom.getSelectedItemPosition() - 1).getId()) : 0));
                attribute.setSubjectId(String.valueOf(spExamSubjectname.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListSubject.
                        get(spExamSubjectname.getSelectedItemPosition() - 1).getId()) : 0));
                attribute.setAttemptCount(String.valueOf(Integer.valueOf(etExamAttemptcount.getText().toString())));
                attribute.setExamType(getExamType());//subject or topic
                attribute.setExamCategory(arrListExamCategory.get(spExamExamCategory.getSelectedItemPosition()));//ISM Mock,wassce
                attribute.setExamMode(arrListExamMode.get(spExamExammode.getSelectedItemPosition()));//subjective/objective
                attribute.setPassingPercent(arrListPassingPercent.get(spExamPassingpercent.getSelectedItemPosition()));
                attribute.setExamDuration(arrListExamDuration.get(spExamExamduration.getSelectedItemPosition()));
                attribute.setExamInstruction(rteTrialExam.getHtml());
                attribute.setDeclareResults(getRadioGropuSelection(radioDeclareresult));
                attribute.setNegativeMarking(getRadioGropuSelection(radioNegativemarking));
                attribute.setRandomQuestion(getRadioGropuSelection(radioExamRandomQuestion));
                attribute.setExamStartDate(Utility.getDateInApiFormat(etExamStartdate.getText().toString()));
                attribute.setExamStartTime(etExamStartTime.getText().toString());
                attribute.setUserId(WebConstants.USER_ID_370);


                //new param
//                attribute.setExamAssessor(getExamAssessorId());
                attribute.setExamAssessor(WebConstants.USER_ID_370);


                attribute.setNegativeMarkValue(etExamAddnegativemark.getText().toString());
                attribute.setBookId(String.valueOf(0));

                //latest added params

                attribute.setUseQuestionScore(getRadioGropuSelection(radioExamUsescore));

                if (getExamType().equalsIgnoreCase("subject")) {
                    attribute.setTopicId("0");
                } else {
                    attribute.setTopicId(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID));
                }

                attribute.setCorrectAnswerScore(getQuestionScoreValue());

                new WebserviceWrapper(mContext, attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.CREATE_EXAM);
            } catch (Exception e) {
                Debug.e(TAG + Utility.getString(R.string.strerrormessage, mContext), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(mContext);
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
        if (value.equalsIgnoreCase("subject")) {
            tbExamSelectexamfor.setChecked(true);
            Utility.invisibleView(llTopicSpinner);
            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID, "0");
        } else if (value.equals("topic")) {
            tbExamSelectexamfor.setChecked(false);
            Utility.showView(llTopicSpinner);

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
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }


    private void onResponseGetClassrooms(Object object, Exception error) {
        try {
            //   ((TeacherHostActivity) getActivity()).hideProgress();
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
                    if (getBundleArguments() != null) {
                        spExamClassroom.setSelection(classrooms.indexOf(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_CLASSROOM_NAME)));
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
            //  ((TeacherHostActivity) getActivity()).hideProgress();
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

                    if (getBundleArguments() != null) {
                        spExamSubjectname.setSelection(subjects.indexOf(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_SUBJECT_NAME)));

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
            //   ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListTopic = new ArrayList<>();
                    arrListTopic.addAll(responseHandler.getTopics());
                    List<String> topics_name = new ArrayList<String>();
                    topics_name.add(Utility.getString(R.string.strtopic, mContext));
                    topics_name.add(Utility.getString(R.string.strnone, mContext));
                    for (Topics topic : arrListTopic) {
                        topics_name.add(topic.getTopicName());
                    }

                    Adapters.setUpSpinner(mContext, spExamSubjecttopic, topics_name, Adapters.ADAPTER_NORMAL);

                    if (getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID).equalsIgnoreCase("0")) {
                        spExamSubjecttopic.setSelection(1);
                    } else {
                        for (int i = 0; i < arrListTopic.size(); i++) {
                            if (arrListTopic.get(i).getId().equalsIgnoreCase(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID))) {
                                spExamSubjecttopic.setSelection(topics_name.indexOf(arrListTopic.get(i).getTopicName()));
                            }
                        }

                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Adapters.setUpSpinner(mContext, spExamSubjecttopic, topicdefaultList, Adapters.ADAPTER_NORMAL);
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
            //    ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    Utility.showToast(Utility.getString(R.string.msg_success_createexam, mContext), mContext);
                    btnExamSetquestion.setVisibility(View.VISIBLE);
                    getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_ID, responseHandler.getCreateExam().get(0).getExamId());
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


    private void getPassingPercentSpinnerValues() {
        arrListPassingPercent = new ArrayList<String>();
        arrListPassingPercent.add(Utility.getString(R.string.strpassingpercent, mContext));
        for (int i = PASSINGPERCENT_STARTVALUE; i < PASSINGPERCENT_ENDVALUE; i += PASSINGPERCENT_INTERVAL) {
            arrListPassingPercent.add(String.valueOf(i));
        }
    }

    private void getExamDurationSpinnerValues() {
        arrListExamDuration = new ArrayList<String>();
        arrListExamDuration.add(Utility.getString(R.string.strexamduration, mContext));
        for (int i = EXAMDURATION_STARTVALUE; i < EXAMDURATION_ENDVALUE; i += EXAMDURATION_INTERVAL) {
            arrListExamDuration.add(String.valueOf(i));
        }

    }

    private void backToTrialScreen() {
        ((TeacherHostActivity) mContext).onBackPressed();
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
            ((CreateExamAssignmentContainerFragment) fragmentContext).hideTopBar();
            getFragmentManager().beginTransaction().
                    replace(R.id.fl_fragment_assignment_container, AddQuestionContainerFragment.newInstance(getBundleArguments()))
                    .commit();
        } else if (v == btnExamCancel) {
            backToTrialScreen();
        }

    }

    private void setBundleArguments() {
        try {
            //exam name
            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_NAME, etExamName.getText().toString());
            //classroomid
            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_CLASSROOM_ID, String.valueOf(spExamClassroom.getSelectedItemPosition() > 0 ?
                    Integer.parseInt(arrListClassRooms.get(spExamClassroom.getSelectedItemPosition() - 1).getId()) : 0));


            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_CLASSROOM_NAME, String.valueOf(spExamClassroom.getSelectedItemPosition() > 0 ?
                    arrListClassRooms.get(spExamClassroom.getSelectedItemPosition() - 1).getClassName() : 0));
            //subject id
            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_SUBJECT_ID, String.valueOf(spExamSubjectname.getSelectedItemPosition() > 0 ?
                    Integer.parseInt(arrListSubject.get(spExamSubjectname.getSelectedItemPosition() - 1).getId()) : 0));

            //topicid
//            getBundleArguments().putString(ARG_EXAM_TOPIC_ID, String.valueOf(spExamSubjecttopic.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListTopic.
//                    get(spExamSubjecttopic.getSelectedItemPosition() - 1).getId()) : 0));
            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID, getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID));
            //book id
            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_BOOK_ID, "0");
            //subject/topic
            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_TYPE, getExamType());
            //ISMmock /wassce
            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_CATEGORY, arrListExamCategory.get(spExamExamCategory.getSelectedItemPosition()));

            //subject name
            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_SUBJECT_NAME, arrListSubject.get(spExamSubjectname.getSelectedItemPosition() - 1).getSubjectName());
            //subjective/objective
            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_MODE,
                    arrListExamMode.get(spExamExammode.getSelectedItemPosition()));

            //exam duration
            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_DURATION,
                    arrListExamDuration.get(spExamExamduration.getSelectedItemPosition()));

            getBundleArguments().putString(AssignmentsAdapter.ARG_ASSIGNMENT_NO, "0");
            //pass percentage
            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_PASS_PERCENTAGE, arrListPassingPercent.get(spExamPassingpercent.getSelectedItemPosition()));

            //exam created date
            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_CREATED_DATE, etExamStartdate.getText().toString());

            getBundleArguments().putBoolean(AssignmentsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE,
                    ((RadioButton) radioExamUsescore.getChildAt(0)).isChecked() ? true : false);

            //getQuestion score
            getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE,
                    arrListQuestionScore.get(spExamQuestionScore.getSelectedItemPosition()));

        } catch (Exception e) {
            Debug.e(TAG, "SetBundleArgumentsException : " + e.toString());
        }
    }

    private String getQuestionScoreValue() {
        if (((RadioButton) radioExamUsescore.getChildAt(0)).isChecked()) {
            return arrListQuestionScore.get(spExamQuestionScore.getSelectedItemPosition());
        } else {
            return "0";
        }
    }


    /**
     * Local Validations
     */

    //local field validations
    private String strValidationMsg;

    private boolean isInputsValid() {

//        & inputValidator.validateStringPresence(etExamStartdate) & inputValidator.validateStringPresence(etExamStartTime)
        return inputValidator.validateStringPresence(etExamName) & inputValidator.validateStringPresence(etExamAttemptcount)
                && checkOtherInputs() && checkRadioButtonInputs();
    }

    private boolean checkRadioButtonInputs() {
        strValidationMsg = "";
//        if (isDeclareResultOption() & isNegativeMarkAdded() & isRandomQuestionOption() & isScoreAdded()) {
        if (isNegativeMarkAdded()) {
            svCreateExam.fullScroll(ScrollView.FOCUS_UP);
            return true;
        } else {
            svCreateExam.fullScroll(ScrollView.FOCUS_DOWN);
            return false;
        }
    }


    private boolean isDeclareResultOption() {
        if (radioDeclareresult.getCheckedRadioButtonId() == -1) {
            strValidationMsg += Utility.getString(R.string.msg_validation_declare_results, mContext);
            return false;
        } else {
            return true;
        }
    }

//    private boolean isScoreAdded() {
//        if (radioExamUsescore.getCheckedRadioButtonId() == R.id.radio_btn_examuserscore_yes) {
//            return inputValidator.validateStringPresence(etExamQuestionscorevalue);
//        } else {
//            return true;
//        }
//    }

    private boolean isRandomQuestionOption() {
        if (radioExamRandomQuestion.getCheckedRadioButtonId() == -1) {
            strValidationMsg += Utility.getString(R.string.msg_validation_random_question, mContext);
            return false;
        } else {
            return true;
        }
    }

    private boolean isNegativeMarkAdded() {
        if (radioNegativemarking.getCheckedRadioButtonId() == R.id.radio_btn_negativemarking_yes) {
            return inputValidator.validateStringPresence(etExamAddnegativemark);
        } else {
            return true;
        }
    }

    private boolean checkOtherInputs() {

        strValidationMsg = "";
        if (isClassroomSet() & isSubjectSet() & isPassingPercentSet() & isExamNameSet() & isExamModeSet() & isExamDurationSet() & isTextSetInRichTextEditor()) {
            return true;
        } else {
            Utility.alert(mContext, null, strValidationMsg);
            return false;
        }
    }

    private boolean isTextSetInRichTextEditor() {
        if (rteTrialExam.getHtml().trim().length() > 0) {
            return true;
        } else {
            strValidationMsg += Utility.getString(R.string.msg_validation_add_text_rich_editor, mContext);
            return false;
        }
    }

    private boolean isExamDurationSet() {
        if (arrListExamDuration != null && arrListExamDuration.size() == 0 || spExamExamduration.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += Utility.getString(R.string.msg_validation_set_exam_duration, mContext);
            return false;
        }
    }

    private boolean isExamModeSet() {
        if (spExamExammode.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += Utility.getString(R.string.msg_validation_set_exam_mode, mContext);
            return false;
        }
    }

    private boolean isExamNameSet() {
        if (spExamExamCategory.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += Utility.getString(R.string.msg_validation_set_exam_name, mContext);
            return false;
        }
    }

    private boolean isPassingPercentSet() {
        if (arrListPassingPercent != null && arrListPassingPercent.size() == 0 || spExamPassingpercent.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += Utility.getString(R.string.msg_validation_set_passingpercent, mContext);
            return false;
        }
    }

    private boolean isSubjectSet() {
        if (arrListSubject != null && arrListSubject.size() == 0 || spExamSubjectname.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += Utility.getString(R.string.msg_validation_set_subject, mContext);
            return false;
        }
    }

    private boolean isClassroomSet() {
        if (arrListClassRooms != null && arrListClassRooms.size() == 0 || spExamClassroom.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += Utility.getString(R.string.msg_validation_set_classroom, mContext);
            return false;
        }
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }


}
