package com.ism.author.fragment.createexam;


import android.app.Fragment;
import android.app.TimePickerDialog;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.InputValidator;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.Adapters;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.fragment.ObjectiveAssignmentQuestionsFragment;
import com.ism.author.object.Global;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.AuthorBook;
import com.ism.author.ws.model.Classrooms;
import com.narola.kpa.richtexteditor.view.RichTextEditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by c166 on 28/10/15.
 */
public class CreateExamFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {

    private static final String TAG = CreateExamFragment.class.getSimpleName();
    private View view;
    private Fragment mFragment;
    public static String ARG_IS_CREATE_EXAM = "isCreateExam";

    public static CreateExamFragment newInstance(Fragment fragment) {
        CreateExamFragment createExamFragment = new CreateExamFragment();
        createExamFragment.mFragment = fragment;
        return createExamFragment;
    }

    public CreateExamFragment() {
        // Required empty public constructor
    }

    private TextView tvExamTitle, tvExamExamschedule, tvExamExaminstruction, tvExamDeclareresult,
            tvExamNegativemarking, tvExamRandomquestion, tvExamUsescore, tvExamQuestionscorevalue, tvExamAddnegativemark,
            tvExamName, tvExamClass, tvExamBookname, tvExamPassingpercent, tvExamCategory, tvExamExammode,
            tvExamExamduration, tvExamAttemptcount, tvExamschedule, tvExamStartdate, tvExamStartTime, tvExamAssessor;
    private Spinner spExamClassroom, spExamBookname, spExamPassingpercent, spExamExamCategory, spExamExammode,
            spExamExamduration, spExamAssessor, spExamQuestionScore;
    private ArrayList<Classrooms> arrListClassRooms;
    private ArrayList<AuthorBook> arrListAuthorBooks;
    private List<String> arrListDefalt, arrListPassingPercent, arrListExamDuration, arrListExamMode,
            arrListExamCategory, arrListExamAssessor, arrListQuestionScore, arrListNegativeMarking;
    private EditText etExamName, etExamStartdate, etExamStartTime, etExamAttemptcount, etExamAddnegativemark;
    private CheckBox cbExamStartdateNotify, cbExamEnddateNotify;
    private RadioGroup radioDeclareresult, radioNegativemarking, radioExamRandomQuestion, radioExamUsescore;
    private RadioButton radio_btn_declareresult_yes, radio_btn_declareresult_no, radio_btn_negativemarking_yes, radio_btn_negativemarking_no,
            radio_btn_randomquestion_yes, radio_btn_randomquestion_no, radio_btn_examuserscore_yes, radio_btn_examuserscore_no;
    private LinearLayout llAddQuestionscore, llAddNegativeMark, llExamStartdate, llExamStartTime;
    private Button btnExamSave, btnExamSetquestion, btnExamCancel;
    private ScrollView svCreateExam;
    private MyTypeFace myTypeFace;
    private InputValidator inputValidator;
    private RichTextEditor rteTrialExam;
    String examStartDate = "";
    private static int PASSINGPERCENT_INTERVAL = 5, PASSINGPERCENT_STARTVALUE = 30, PASSINGPERCENT_ENDVALUE = 99;
    private static int EXAMDURATION_INTERVAL = 5, EXAMDURATION_STARTVALUE = 30, EXAMDURATION_ENDVALUE = 300;
    private static int QUESTIONSCORE_INERVAL = 1, QUESTIONSCORE_STARTVALUE = 1, QUESTIONSCORE_ENDVALUE = 5;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_exam, container, false);

        initGlobal();
        return view;
    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());
        inputValidator = new InputValidator(getActivity());

        rteTrialExam = (RichTextEditor) view.findViewById(R.id.rte_trial_exam);
        rteTrialExam.getRichEditor().setEditorFontSize(20);
        rteTrialExam.hideMediaControls();

        tvExamTitle = (TextView) view.findViewById(R.id.tv_exam_title);

        tvExamExamschedule = (TextView) view.findViewById(R.id.tv_exam_examschedule);
        tvExamExaminstruction = (TextView) view.findViewById(R.id.tv_exam_examinstruction);
        tvExamDeclareresult = (TextView) view.findViewById(R.id.tv_exam_declareresult);
        tvExamNegativemarking = (TextView) view.findViewById(R.id.tv_exam_negativemarking);
        tvExamRandomquestion = (TextView) view.findViewById(R.id.tv_exam_randomquestion);
        tvExamUsescore = (TextView) view.findViewById(R.id.tv_exam_usescore);
        tvExamQuestionscorevalue = (TextView) view.findViewById(R.id.tv_exam_questionscorevalue);
        tvExamAddnegativemark = (TextView) view.findViewById(R.id.tv_exam_addnegativemark);

        tvExamName = (TextView) view.findViewById(R.id.tv_exam_type);
        tvExamClass = (TextView) view.findViewById(R.id.tv_exam_class);
        tvExamBookname = (TextView) view.findViewById(R.id.tv_exam_bookname);
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
        tvExamBookname.setTypeface(myTypeFace.getRalewayRegular());
        tvExamPassingpercent.setTypeface(myTypeFace.getRalewayRegular());
        tvExamCategory.setTypeface(myTypeFace.getRalewayRegular());
        tvExamExammode.setTypeface(myTypeFace.getRalewayRegular());
        tvExamExamduration.setTypeface(myTypeFace.getRalewayRegular());
        tvExamAttemptcount.setTypeface(myTypeFace.getRalewayRegular());
        tvExamschedule.setTypeface(myTypeFace.getRalewayRegular());
        tvExamStartdate.setTypeface(myTypeFace.getRalewayRegular());
        tvExamStartTime.setTypeface(myTypeFace.getRalewayRegular());

        tvExamTitle.setTypeface(myTypeFace.getRalewayRegular());
        tvExamExamschedule.setTypeface(myTypeFace.getRalewayRegular());
        tvExamExaminstruction.setTypeface(myTypeFace.getRalewayRegular());
        tvExamDeclareresult.setTypeface(myTypeFace.getRalewayRegular());
        tvExamNegativemarking.setTypeface(myTypeFace.getRalewayRegular());
        tvExamRandomquestion.setTypeface(myTypeFace.getRalewayRegular());
        tvExamUsescore.setTypeface(myTypeFace.getRalewayRegular());
        tvExamQuestionscorevalue.setTypeface(myTypeFace.getRalewayRegular());
        tvExamAddnegativemark.setTypeface(myTypeFace.getRalewayRegular());
        tvExamAssessor.setTypeface(myTypeFace.getRalewayRegular());

        spExamClassroom = (Spinner) view.findViewById(R.id.sp_exam_classroom);
        spExamBookname = (Spinner) view.findViewById(R.id.sp_exam_bookname);
        spExamPassingpercent = (Spinner) view.findViewById(R.id.sp_exam_passingpercent);
        spExamExamCategory = (Spinner) view.findViewById(R.id.sp_exam_examcategory);
        spExamExammode = (Spinner) view.findViewById(R.id.sp_exam_exammode);
        spExamExamduration = (Spinner) view.findViewById(R.id.sp_exam_examduration);
        spExamAssessor = (Spinner) view.findViewById(R.id.sp_exam_assessor);
        spExamQuestionScore = (Spinner) view.findViewById(R.id.sp_exam_question_score);

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

        radio_btn_declareresult_yes = (RadioButton) view.findViewById(R.id.radio_btn_declareresult_yes);
        radio_btn_declareresult_no = (RadioButton) view.findViewById(R.id.radio_btn_declareresult_no);

        radio_btn_negativemarking_yes = (RadioButton) view.findViewById(R.id.radio_btn_negativemarking_yes);
        radio_btn_negativemarking_no = (RadioButton) view.findViewById(R.id.radio_btn_negativemarking_no);

        radio_btn_randomquestion_yes = (RadioButton) view.findViewById(R.id.radio_btn_randomquestion_yes);
        radio_btn_randomquestion_no = (RadioButton) view.findViewById(R.id.radio_btn_randomquestion_no);

        radio_btn_examuserscore_yes = (RadioButton) view.findViewById(R.id.radio_btn_examuserscore_yes);
        radio_btn_examuserscore_no = (RadioButton) view.findViewById(R.id.radio_btn_examuserscore_no);


        radio_btn_declareresult_yes.setTypeface(myTypeFace.getRalewayRegular());
        radio_btn_declareresult_no.setTypeface(myTypeFace.getRalewayRegular());
        radio_btn_negativemarking_yes.setTypeface(myTypeFace.getRalewayRegular());
        radio_btn_negativemarking_no.setTypeface(myTypeFace.getRalewayRegular());
        radio_btn_randomquestion_yes.setTypeface(myTypeFace.getRalewayRegular());
        radio_btn_randomquestion_no.setTypeface(myTypeFace.getRalewayRegular());
        radio_btn_examuserscore_yes.setTypeface(myTypeFace.getRalewayRegular());
        radio_btn_examuserscore_no.setTypeface(myTypeFace.getRalewayRegular());


        svCreateExam = (ScrollView) view.findViewById(R.id.sv_create_exam);


        btnExamSave = (Button) view.findViewById(R.id.btn_exam_save);
        btnExamSetquestion = (Button) view.findViewById(R.id.btn_exam_setquestion);
        btnExamCancel = (Button) view.findViewById(R.id.btn_exam_cancel);

        btnExamSave.setTypeface(myTypeFace.getRalewayRegular());
        btnExamSetquestion.setTypeface(myTypeFace.getRalewayRegular());
        btnExamCancel.setTypeface(myTypeFace.getRalewayRegular());

        btnExamSave.setOnClickListener(this);
        btnExamSetquestion.setOnClickListener(this);
        btnExamCancel.setOnClickListener(this);

        llAddQuestionscore = (LinearLayout) view.findViewById(R.id.ll_add_questionscore);
        llAddNegativeMark = (LinearLayout) view.findViewById(R.id.ll_add_negative_mark);

        llAddQuestionscore.setVisibility(View.VISIBLE);
        llAddNegativeMark.setVisibility(View.VISIBLE);

        llExamStartdate = (LinearLayout) view.findViewById(R.id.ll_exam_startdate);
        llExamStartTime = (LinearLayout) view.findViewById(R.id.ll_exam_startTime);


        arrListDefalt = new ArrayList<String>();
        arrListDefalt.add(Utility.getString(R.string.select, getActivity()));


        getPassingPercentSpinnerValues();
        Adapters.setUpSpinner(getActivity(), spExamPassingpercent, arrListPassingPercent, Adapters.ADAPTER_NORMAL);

        getExamDurationSpinnerValues();
        Adapters.setUpSpinner(getActivity(), spExamExamduration, arrListExamDuration, Adapters.ADAPTER_NORMAL);

        getQuestionScoreSpinnerValues();
        Adapters.setUpSpinner(getActivity(), spExamQuestionScore, arrListQuestionScore, Adapters.ADAPTER_NORMAL);

        arrListExamMode = new ArrayList<String>();
        arrListExamMode = Arrays.asList(getResources().getStringArray(R.array.exammode));
        Adapters.setUpSpinner(getActivity(), spExamExammode, arrListExamMode, Adapters.ADAPTER_NORMAL);


        arrListExamCategory = new ArrayList<String>();
        arrListExamCategory = Arrays.asList(getResources().getStringArray(R.array.examname));
        Adapters.setUpSpinner(getActivity(), spExamExamCategory, arrListExamCategory, Adapters.ADAPTER_NORMAL);


        arrListExamAssessor = new ArrayList<String>();
        arrListExamAssessor.add(getString(R.string.strexamassessor));
        arrListExamAssessor.add(getString(R.string.strnoassessor));
        arrListExamAssessor.add(Global.strFullName);
        Adapters.setUpSpinner(getActivity(), spExamAssessor, arrListExamAssessor, Adapters.ADAPTER_NORMAL);


        llExamStartdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    examStartDate = Utils.showDatePickerDob(getActivity(), etExamStartdate);
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


        if (getBaseFragment().getBundleArguments().getBoolean(ARG_IS_CREATE_EXAM)) {

            btnExamSetquestion.setVisibility(View.GONE);
            btnExamSave.setVisibility(View.VISIBLE);

        } else {
            setExamDetails();
        }
        callApiGetClassrooms();
        callApiGetAuthorBooks();


    }


    private void setExamDetails() {

        spExamBookname.setEnabled(false);
        spExamExammode.setEnabled(false);
            /*we cant change the exam mode and subject for that particular exam if it once created*/
        if (getBaseFragment().getBundleArguments().getBoolean(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY)) {
            btnExamSetquestion.setVisibility(View.GONE);
            btnExamSave.setVisibility(View.VISIBLE);
        } else {
            btnExamSetquestion.setVisibility(View.VISIBLE);
            btnExamSave.setVisibility(View.VISIBLE);
            btnExamSave.setText(getString(R.string.streditexam));
        }
        etExamName.setText(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_NAME));
        spExamPassingpercent.setSelection(arrListPassingPercent.indexOf(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_PASS_PERCENTAGE)));
        spExamAssessor.setSelection(1);
        spExamExamCategory.setSelection(arrListExamCategory.indexOf(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_CATEGORY)));
        spExamExammode.setSelection(arrListExamMode.indexOf(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_MODE)));
        spExamExamduration.setSelection(arrListExamDuration.indexOf(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_DURATION)));
        etExamAttemptcount.setText(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ATTEMPT_COUNT));
        etExamStartdate.setText(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_START_DATE));
        etExamStartTime.setText(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_START_TIME));

        if (getBaseFragment().getBundleArguments().getBoolean(ExamsAdapter.ARG_EXAM_IS_DECLARE_RESULTS)) {
            ((RadioButton) radioDeclareresult.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) radioDeclareresult.getChildAt(1)).setChecked(true);
        }

        if (getBaseFragment().getBundleArguments().getBoolean(ExamsAdapter.ARG_EXAM_IS_NEGATIVE_MARKING)) {
            ((RadioButton) radioNegativemarking.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) radioNegativemarking.getChildAt(1)).setChecked(true);
        }

        etExamAddnegativemark.setText(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_NEGATIVE_MARK_VALUE));

        if (getBaseFragment().getBundleArguments().getBoolean(ExamsAdapter.ARG_EXAM_IS_RANDOM_QUESTION)) {
            ((RadioButton) radioExamRandomQuestion.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) radioExamRandomQuestion.getChildAt(1)).setChecked(true);
        }


        if (getBaseFragment().getBundleArguments().getBoolean(ExamsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE)) {
            ((RadioButton) radioExamUsescore.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) radioExamUsescore.getChildAt(1)).setChecked(true);
        }
        spExamQuestionScore.setSelection(arrListQuestionScore.indexOf(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE)));
    }

    private void callApiGetClassrooms() {

        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETALLCLASSROOMS);
            } catch (Exception e) {
                Debug.e(TAG + Utility.getString(R.string.strerrormessage, getActivity()), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }

    private void callApiGetAuthorBooks() {

        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETBOOKSFORAUTHOR);
            } catch (Exception e) {
                Debug.e(TAG + Utility.getString(R.string.strerrormessage, getActivity()), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    /*These method has to be change after api integration of these values*/
    private String getExamAssessorId() {

        if (spExamAssessor.getSelectedItemPosition() == 1) {
            return Global.strUserId;
        } else {
            return Global.strUserId;
        }

    }


    private void callApiCreateExam() {

        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();

                /*set exam id "0" if you are creating new and copy exam ,In case of edit set particular exam id*/
                attribute.setExamId("0");
                if (getBaseFragment().getBundleArguments().containsKey(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY)) {
                    if (!getBaseFragment().getBundleArguments().getBoolean(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY)) {
                        attribute.setExamId(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_ID));
                    }
                }


                attribute.setExamName(etExamName.getText().toString());
                attribute.setClassroomId(String.valueOf(spExamClassroom.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListClassRooms.
                        get(spExamClassroom.getSelectedItemPosition() - 1).getId()) : 0));
                attribute.setBookId(String.valueOf(spExamBookname.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListAuthorBooks.
                        get(spExamBookname.getSelectedItemPosition() - 1).getBookId()) : 0));
                attribute.setPassingPercent(arrListPassingPercent.get(spExamPassingpercent.getSelectedItemPosition()));
                attribute.setExamAssessor(getExamAssessorId());
                attribute.setExamCategory(arrListExamCategory.get(spExamExamCategory.getSelectedItemPosition()));
                attribute.setExamMode(arrListExamMode.get(spExamExammode.getSelectedItemPosition()));
                attribute.setExamDuration(arrListExamDuration.get(spExamExamduration.getSelectedItemPosition()));
                attribute.setAttemptCount(String.valueOf(Integer.valueOf(etExamAttemptcount.getText().toString())));
                attribute.setExamStartDate(Utils.getDateInApiFormat(etExamStartdate.getText().toString()));
                attribute.setExamStartTime(etExamStartTime.getText().toString());
                attribute.setDeclareResults(getRadioGropuSelection(radioDeclareresult));
                attribute.setNegativeMarking(getRadioGropuSelection(radioNegativemarking));
                attribute.setNegativeMarkValue(getNegativeMarkValue());
                attribute.setRandomQuestion(getRadioGropuSelection(radioExamRandomQuestion));
                attribute.setUseQuestionScore(getRadioGropuSelection(radioExamUsescore));
                attribute.setCorrectAnswerScore(getQuestionScoreValue());
                attribute.setExamInstruction(rteTrialExam.getHtml());
                attribute.setUserId(Global.strUserId);

                /*Insert subject id=0,topic_id=0 and exam_type=topic default value in author module*/
                attribute.setSubjectId("0");
                attribute.setTopicId("0");
                attribute.setExamType("topic");

                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.CREATEEXAM);
            } catch (Exception e) {
                Debug.e(TAG + Utility.getString(R.string.strerrormessage, getActivity()), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    /*methods for value validation*/
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
            strValidationMsg += Utility.getString(R.string.msg_validation_declare_results, getActivity());
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
            strValidationMsg += Utility.getString(R.string.msg_validation_random_question, getActivity());
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

//        & isTextSetInRichTextEditor()
        strValidationMsg = "";
        if (isClassroomSet() & isSubjectSet() & isPassingPercentSet() & isExamNameSet() & isExamModeSet() & isExamDurationSet()
                & isExamAssessorSelected()) {
            return true;
        } else {
            Utility.alert(getActivity(), null, strValidationMsg);
            return false;
        }
    }

    private boolean isTextSetInRichTextEditor() {
        if ((rteTrialExam.getHtml().toString() != null) & (rteTrialExam.getHtml().toString().length() > 0)) {
            return true;
        } else {
            strValidationMsg += Utility.getString(R.string.msg_validation_add_text_rich_editor, getActivity());
            return false;
        }
    }

    private boolean isExamDurationSet() {
        if (arrListExamDuration != null && arrListExamDuration.size() == 0 || spExamExamduration.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += Utility.getString(R.string.msg_validation_set_exam_duration, getActivity());
            return false;
        }
    }

    private boolean isExamModeSet() {
        if (spExamExammode.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += Utility.getString(R.string.msg_validation_set_exam_mode, getActivity());
            return false;
        }
    }

    private boolean isExamNameSet() {
        if (spExamExamCategory.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += Utility.getString(R.string.msg_validation_set_exam_name, getActivity());
            return false;
        }
    }

    private boolean isPassingPercentSet() {
        if (arrListPassingPercent != null && arrListPassingPercent.size() == 0 || spExamPassingpercent.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += Utility.getString(R.string.msg_validation_set_passingpercent, getActivity());
            return false;
        }
    }

    private boolean isSubjectSet() {
        if (arrListAuthorBooks != null && arrListAuthorBooks.size() == 0 || spExamBookname.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += Utility.getString(R.string.msg_validation_set_subject, getActivity());
            return false;
        }
    }

    private boolean isClassroomSet() {
        if (arrListClassRooms != null && arrListClassRooms.size() == 0 || spExamClassroom.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += Utility.getString(R.string.msg_validation_set_classroom, getActivity());
            return false;
        }
    }

    private boolean isExamAssessorSelected() {
        if (arrListExamAssessor != null && arrListExamAssessor.size() == 0 || spExamAssessor.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += Utility.getString(R.string.msg_validation_set_exam_assessor, getActivity());
            return false;
        }
    }


    private String getRadioGropuSelection(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        Debug.e(TAG, "The radio button selection value is:::" + ((RadioButton) view.findViewById(selectedId)).getText().toString().toLowerCase());
        return ((RadioButton) view.findViewById(selectedId)).getText().toString().toLowerCase();
    }

    private String getNegativeMarkValue() {

        if (((RadioButton) radioNegativemarking.getChildAt(0)).isChecked()) {
            return etExamAddnegativemark.getText().toString();
        } else {
            return "0";
        }
    }


    private String getQuestionScoreValue() {
        if (((RadioButton) radioExamUsescore.getChildAt(0)).isChecked()) {
            return arrListQuestionScore.get(spExamQuestionScore.getSelectedItemPosition());
        } else {
            return "0";
        }
    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GETALLCLASSROOMS:
                    onResponseGetClassrooms(object, error);
                    break;
                case WebConstants.GETBOOKSFORAUTHOR:
//                    onResponseGetSubjects(object, error);
                    onResponseGetBooksForAuthor(object, error);
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
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    arrListClassRooms = new ArrayList<Classrooms>();
                    arrListClassRooms.addAll(responseHandler.getClassrooms());
                    List<String> classrooms = new ArrayList<String>();
                    classrooms.add(Utility.getString(R.string.strclass, getActivity()));
                    for (Classrooms classroom : arrListClassRooms) {
                        classrooms.add(classroom.getClassName());

                    }
                    Adapters.setUpSpinner(getActivity(), spExamClassroom, classrooms, Adapters.ADAPTER_NORMAL);
//                    if (!getBaseFragment().getBundleArguments().getBoolean(ARG_IS_CREATE_EXAM)) {
                    spExamClassroom.setSelection(classrooms.indexOf(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_CLASSROOM_NAME)));
//                    }


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


    private void onResponseGetBooksForAuthor(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    arrListAuthorBooks = new ArrayList<AuthorBook>();
                    arrListAuthorBooks.addAll(responseHandler.getAuthorBook());
                    List<String> authorBooks = new ArrayList<String>();
                    authorBooks.add(Utility.getString(R.string.strbookname, getActivity()));
                    for (AuthorBook authorBook : arrListAuthorBooks) {
                        authorBooks.add(authorBook.getBookName());
                    }
                    Adapters.setUpSpinner(getActivity(), spExamBookname, authorBooks, Adapters.ADAPTER_NORMAL);


//                    if (!getBaseFragment().getBundleArguments().getBoolean(ARG_IS_CREATE_EXAM)) {
                    spExamBookname.setSelection(authorBooks.indexOf(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_BOOK_NAME)));
//                    }
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


    private void onResponseCreateExam(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    if (getBaseFragment().getBundleArguments().containsKey(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY)) {
                        if (!getBaseFragment().getBundleArguments().getBoolean(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY)) {
                            Utils.showToast(Utility.getString(R.string.msg_success_editexam, getActivity()), getActivity());
                        } else {
                            Utils.showToast(Utility.getString(R.string.msg_success_createexam, getActivity()), getActivity());
                        }
                    } else {
                        Utils.showToast(Utility.getString(R.string.msg_success_createexam, getActivity()), getActivity());
                    }

                    svCreateExam.fullScroll(ScrollView.FOCUS_UP);
                    btnExamSetquestion.setVisibility(View.VISIBLE);
                    btnExamSave.setVisibility(View.GONE);
                    Debug.e(TAG, "The Created ExamId is::" + responseHandler.getCreateExam().get(0).getExamId());
                    getBaseFragment().getBundleArguments().putString(ExamsAdapter.ARG_EXAM_ID, responseHandler.getCreateExam().get(0).getExamId());
                    setBundleArguments();
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
        arrListPassingPercent.add(Utility.getString(R.string.strpassingpercent, getActivity()));
        for (int i = PASSINGPERCENT_STARTVALUE; i < PASSINGPERCENT_ENDVALUE; i += PASSINGPERCENT_INTERVAL) {
            arrListPassingPercent.add(String.valueOf(i));
        }
    }

    private void getExamDurationSpinnerValues() {
        arrListExamDuration = new ArrayList<String>();
        arrListExamDuration.add(Utility.getString(R.string.strexamduration, getActivity()));
        for (int i = EXAMDURATION_STARTVALUE; i < EXAMDURATION_ENDVALUE; i += EXAMDURATION_INTERVAL) {
            arrListExamDuration.add(String.valueOf(i));
        }

    }

    private void getQuestionScoreSpinnerValues() {
        arrListQuestionScore = new ArrayList<String>();
        arrListQuestionScore.add(Utility.getString(R.string.strquestionscore, getActivity()));
        for (int i = QUESTIONSCORE_STARTVALUE; i <= QUESTIONSCORE_ENDVALUE; i += QUESTIONSCORE_INERVAL) {
            arrListQuestionScore.add(String.valueOf(i));
        }
    }

    private void getNegativeMarkingSpinnerValues() {
        arrListNegativeMarking = new ArrayList<String>();
        arrListNegativeMarking.add(Utility.getString(R.string.strnegativemarking, getActivity()));
        for (int i = QUESTIONSCORE_STARTVALUE; i <= QUESTIONSCORE_ENDVALUE; i += QUESTIONSCORE_INERVAL) {
            arrListNegativeMarking.add(String.valueOf(i));
        }
    }


    private void backToTrialScreen() {
//        ((AuthorHostActivity) mContext).onBackPressed();
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.btn_exam_save:
                if (isInputsValid()) {
                    callApiCreateExam();
                }
                break;
            case R.id.btn_exam_setquestion:
                if (getBaseFragment().getBundleArguments() != null) {
                    setBundleArguments();
                }
                ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_ADDQUESTION_CONTAINER);
                break;
            case R.id.btn_exam_cancel:
                getBaseFragment().onBackClick();
                break;
        }


    }

    private void setBundleArguments() {


        try {
            getBaseFragment().getBundleArguments().putString(ExamsAdapter.ARG_EXAM_NAME, etExamName.getText().toString());
            getBaseFragment().getBundleArguments().putString(ExamsAdapter.ARG_EXAM_CLASSROOM_ID, String.valueOf(spExamClassroom.getSelectedItemPosition() > 0 ?
                    Integer.parseInt(arrListClassRooms.get(spExamClassroom.getSelectedItemPosition() - 1).getId()) : 0));
            getBaseFragment().getBundleArguments().putString(ExamsAdapter.ARG_EXAM_CLASSROOM_NAME, String.valueOf(spExamClassroom.getSelectedItemPosition() > 0 ?
                    arrListClassRooms.get(spExamClassroom.getSelectedItemPosition() - 1).getClassName() : 0));
            getBaseFragment().getBundleArguments().putString(ExamsAdapter.ARG_EXAM_BOOK_ID, String.valueOf(spExamBookname.getSelectedItemPosition() > 0 ?
                    Integer.parseInt(arrListAuthorBooks.get(spExamBookname.getSelectedItemPosition() - 1).getBookId()) : 0));
            getBaseFragment().getBundleArguments().putString(ExamsAdapter.ARG_EXAM_BOOK_NAME, String.valueOf(spExamBookname.getSelectedItemPosition() > 0 ?
                    arrListAuthorBooks.get(spExamBookname.getSelectedItemPosition() - 1).getBookName() : 0));
            getBaseFragment().getBundleArguments().putString(ExamsAdapter.ARG_EXAM_CATEGORY, arrListExamCategory.get(spExamExamCategory.getSelectedItemPosition()));
            getBaseFragment().getBundleArguments().putString(ExamsAdapter.ARG_EXAM_MODE,
                    arrListExamMode.get(spExamExammode.getSelectedItemPosition()));
            getBaseFragment().getBundleArguments().putString(ExamsAdapter.ARG_EXAM_PASS_PERCENTAGE, arrListPassingPercent.get(spExamPassingpercent.getSelectedItemPosition()));
            getBaseFragment().getBundleArguments().putString(ExamsAdapter.ARG_EXAM_DURATION,
                    arrListExamDuration.get(spExamExamduration.getSelectedItemPosition()));
            getBaseFragment().getBundleArguments().putBoolean(ExamsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE,
                    ((RadioButton) radioExamUsescore.getChildAt(0)).isChecked() ? true : false);
            getBaseFragment().getBundleArguments().putString(ExamsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE,
                    arrListQuestionScore.get(spExamQuestionScore.getSelectedItemPosition()));

        } catch (Exception e) {
            Debug.e(TAG, "SetBundleArgumentsException : " + e.toString());
        }

    }


    private CreateExamAssignmentContainerFragment getBaseFragment() {
        return (CreateExamAssignmentContainerFragment) mFragment;
    }


}
