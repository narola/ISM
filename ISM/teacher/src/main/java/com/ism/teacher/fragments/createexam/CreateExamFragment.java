package com.ism.teacher.fragments.createexam;


import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.ism.teacher.fragments.assesment.ObjectiveAssignmentQuestionsFragment;
import com.ism.teacher.fragments.createquestion.AddQuestionContainerFragment;
import com.ism.teacher.helper.InputValidator;
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

public class CreateExamFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {

    private View view;
    private Context mContext;
    private Fragment fragmentContext;

    private static final String TAG = CreateExamFragment.class.getSimpleName();
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
    private TextView tvExamSave, tvExamSetquestion, tvExamCancel;
    private RichTextEditor rteTrialExam;
    private ScrollView svCreateExam;


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
    private Fragment mFragment;

    /**
     * Fragment Args
     */
    public static String ARG_IS_CREATE_EXAM = "isCreateExam";


    public CreateExamFragment() {
        // Required empty public constructor
    }

    public static CreateExamFragment newInstance(Fragment fragment, Context mContext) {
        CreateExamFragment createExamFragment = new CreateExamFragment();

        createExamFragment.mContext = mContext;
        createExamFragment.fragmentContext = fragment;
        createExamFragment.mFragment = fragment;
        return createExamFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignment_exam, container, false);

        initGlobal();
        return view;
    }

    private void initGlobal() {

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

        tvExamName.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamClass.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvSubjectName.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamPassingpercent.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamCategory.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamExammode.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamExamduration.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamAttemptcount.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamschedule.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamStartdate.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamStartTime.setTypeface(Global.myTypeFace.getRalewayRegular());

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

        tvExamTitle.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamExamfor.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamExaminstruction.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamDeclareresult.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamNegativemarking.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamRandomquestion.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamUsescore.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamQuestionscorevalue.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamAddnegativemark.setTypeface(Global.myTypeFace.getRalewayRegular());


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

        etExamName.setTypeface(Global.myTypeFace.getRalewayRegular());
        etExamStartdate.setTypeface(Global.myTypeFace.getRalewayRegular());
        etExamStartTime.setTypeface(Global.myTypeFace.getRalewayRegular());
        etExamAddnegativemark.setTypeface(Global.myTypeFace.getRalewayRegular());

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


        tvExamSave = (TextView) view.findViewById(R.id.tv_exam_save);
        tvExamSetquestion = (TextView) view.findViewById(R.id.tv_exam_setquestion);
        tvExamCancel = (TextView) view.findViewById(R.id.tv_exam_cancel);

        tvExamSave.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamSetquestion.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvExamCancel.setTypeface(Global.myTypeFace.getRalewayRegular());

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


        tvExamSave.setOnClickListener(this);
        tvExamSetquestion.setOnClickListener(this);
        tvExamCancel.setOnClickListener(this);

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
        rteTrialExam.getRichEditor().setEditorFontSize(20);
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
                        getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID, arrListTopic.get(position - 2).getId());

                        Debug.e("test topic by position>1:", "topic name:" + arrListTopic.get(position - 2).getTopicName());
                    } else {
                        getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID, "0");
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
         *  if (getBaseFragment().getBundleArguments()() != null) means edit assign
         *  so disable exam mode and subject selection spinner
         */

        //For New Exam

        if (getBaseFragment().getBundleArguments().getBoolean(ARG_IS_CREATE_EXAM)) {
            tvExamSetquestion.setVisibility(View.GONE);
            tvExamSave.setVisibility(View.VISIBLE);

        }
        //for edit exam
        else {
            setExamDetails();
            tvExamSetquestion.setVisibility(View.VISIBLE);
            spExamExammode.setEnabled(false);
            spExamSubjectname.setEnabled(false);
        }


        callApiGetClassrooms();
        callApiGetSubjects();


    }

    private CreateExamAssignmentContainerFragment getBaseFragment() {
        return (CreateExamAssignmentContainerFragment) mFragment;
    }

    private void getQuestionScoreSpinnerValues() {
        arrListQuestionScore = new ArrayList<String>();
        arrListQuestionScore.add(Utility.getString(R.string.strquestionscore, mContext));
        for (int i = QUESTIONSCORE_STARTVALUE; i <= QUESTIONSCORE_ENDVALUE; i += QUESTIONSCORE_INERVAL) {
            arrListQuestionScore.add(String.valueOf(i));
        }
    }


    private void setExamDetails() {
        etExamName.setText(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_NAME));
        spExamPassingpercent.setSelection(arrListPassingPercent.indexOf(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_PASS_PERCENTAGE)));
        setExamType(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_TYPE));
        spExamExamCategory.setSelection(arrListExamCategory.indexOf(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_CATEGORY)));
        spExamExammode.setSelection(arrListExamMode.indexOf(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_MODE)));
        spExamExamduration.setSelection(arrListExamDuration.indexOf(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_DURATION)));

            /*we cant change the exam mode and subject for that particular exam if it once created*/
        if (getBaseFragment().getBundleArguments().getBoolean(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY)) {

            tvExamSetquestion.setVisibility(View.GONE);
            tvExamSave.setVisibility(View.VISIBLE);

        } else {

            tvExamSetquestion.setVisibility(View.VISIBLE);
            tvExamSave.setVisibility(View.VISIBLE);
            tvExamSave.setText(getString(R.string.streditexam));

        }
        etExamName.setText(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_NAME));
        spExamPassingpercent.setSelection(arrListPassingPercent.indexOf(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_PASS_PERCENTAGE)));
        spExamAssessor.setSelection(1);
        spExamExamCategory.setSelection(arrListExamCategory.indexOf(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_CATEGORY)));
        spExamExammode.setSelection(arrListExamMode.indexOf(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_MODE)));
        spExamExamduration.setSelection(arrListExamDuration.indexOf(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_DURATION)));

        etExamAttemptcount.setText(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_ATTEMPT_COUNT));
        etExamStartdate.setText(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_START_DATE));
        etExamStartTime.setText(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_START_TIME));

        if (getBaseFragment().getBundleArguments().getBoolean(AssignmentsAdapter.ARG_EXAM_IS_DECLARE_RESULTS)) {
            ((RadioButton) radioDeclareresult.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) radioDeclareresult.getChildAt(1)).setChecked(true);
        }

        if (getBaseFragment().getBundleArguments().getBoolean(AssignmentsAdapter.ARG_EXAM_IS_NEGATIVE_MARKING)) {
            ((RadioButton) radioNegativemarking.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) radioNegativemarking.getChildAt(1)).setChecked(true);
        }

        etExamAddnegativemark.setText(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_NEGATIVE_MARK_VALUE));

        if (getBaseFragment().getBundleArguments().getBoolean(AssignmentsAdapter.ARG_EXAM_IS_RANDOM_QUESTION)) {
            ((RadioButton) radioExamRandomQuestion.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) radioExamRandomQuestion.getChildAt(1)).setChecked(true);
        }


        if (getBaseFragment().getBundleArguments().getBoolean(AssignmentsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE)) {
            ((RadioButton) radioExamUsescore.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) radioExamUsescore.getChildAt(1)).setChecked(true);
        }


        rteTrialExam.setHtml(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_INSTRUCTIONS));
        spExamQuestionScore.setSelection(arrListQuestionScore.indexOf(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE)));

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
                new WebserviceWrapper(mContext, new Attribute(), (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
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
                new WebserviceWrapper(mContext, new Attribute(), (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
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
                if (getBaseFragment().getBundleArguments().containsKey(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY)) {
                    if (!getBaseFragment().getBundleArguments().getBoolean(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY)) {
                        attribute.setExamId(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_ID));
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
                    attribute.setTopicId(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID));
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
            getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID, "0");
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
                    if (getBaseFragment().getBundleArguments() != null) {
                        spExamClassroom.setSelection(classrooms.indexOf(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_CLASSROOM_NAME)));
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

                    if (getBaseFragment().getBundleArguments() != null) {
                        spExamSubjectname.setSelection(subjects.indexOf(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_SUBJECT_NAME)));

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

                    if (getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID).equalsIgnoreCase("0")) {
                        spExamSubjecttopic.setSelection(1);
                    } else {
                        for (int i = 0; i < arrListTopic.size(); i++) {
                            if (arrListTopic.get(i).getId().equalsIgnoreCase(getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID))) {
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
                    tvExamSetquestion.setVisibility(View.VISIBLE);
                    getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_ID, responseHandler.getCreateExam().get(0).getExamId());
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
        if (v == tvExamSave) {
            if (isInputsValid()) {
                callApiCreateExam();
            } else {
                svCreateExam.fullScroll(ScrollView.FOCUS_UP);
            }
        } else if (v == tvExamSetquestion) {
            ((CreateExamAssignmentContainerFragment) fragmentContext).hideTopBar();
            getFragmentManager().beginTransaction().
                    replace(R.id.fl_fragment_assignment_container, AddQuestionContainerFragment.newInstance())
                    .commit();
        } else if (v == tvExamCancel) {
            backToTrialScreen();
        }

    }

    private void setBundleArguments() {
        try {
            //exam name
            getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_NAME, etExamName.getText().toString());
            //classroomid
            getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_CLASSROOM_ID, String.valueOf(spExamClassroom.getSelectedItemPosition() > 0 ?
                    Integer.parseInt(arrListClassRooms.get(spExamClassroom.getSelectedItemPosition() - 1).getId()) : 0));


            getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_CLASSROOM_NAME, String.valueOf(spExamClassroom.getSelectedItemPosition() > 0 ?
                    arrListClassRooms.get(spExamClassroom.getSelectedItemPosition() - 1).getClassName() : 0));
            //subject id
            getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_SUBJECT_ID, String.valueOf(spExamSubjectname.getSelectedItemPosition() > 0 ?
                    Integer.parseInt(arrListSubject.get(spExamSubjectname.getSelectedItemPosition() - 1).getId()) : 0));

            //topicid
//            getBundleArguments().putString(ARG_EXAM_TOPIC_ID, String.valueOf(spExamSubjecttopic.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListTopic.
//                    get(spExamSubjecttopic.getSelectedItemPosition() - 1).getId()) : 0));
            getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID, getBaseFragment().getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID));
            //book id
            getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_BOOK_ID, "0");
            //subject/topic
            getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_TYPE, getExamType());
            //ISMmock /wassce
            getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_CATEGORY, arrListExamCategory.get(spExamExamCategory.getSelectedItemPosition()));

            //subject name
            getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_SUBJECT_NAME, arrListSubject.get(spExamSubjectname.getSelectedItemPosition() - 1).getSubjectName());
            //subjective/objective
            getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_MODE,
                    arrListExamMode.get(spExamExammode.getSelectedItemPosition()));

            //exam duration
            getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_DURATION,
                    arrListExamDuration.get(spExamExamduration.getSelectedItemPosition()));

            getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_ASSIGNMENT_NO, "0");
            //pass percentage
            getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_PASS_PERCENTAGE, arrListPassingPercent.get(spExamPassingpercent.getSelectedItemPosition()));

            //exam created date
            getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_CREATED_DATE, etExamStartdate.getText().toString());

            getBaseFragment().getBundleArguments().putBoolean(AssignmentsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE,
                    ((RadioButton) radioExamUsescore.getChildAt(0)).isChecked() ? true : false);

            //getQuestion score
            getBaseFragment().getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE,
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


}
