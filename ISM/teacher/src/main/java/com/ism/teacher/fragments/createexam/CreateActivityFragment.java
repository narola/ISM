package com.ism.teacher.fragments.createexam;


import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.constants.WebConstants;
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
import java.util.Calendar;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by c166 on 28/10/15.
 */
public class CreateActivityFragment extends Fragment implements WebserviceWrapper.WebserviceResponse,View.OnClickListener{


    private static final String TAG = CreateActivityFragment.class.getSimpleName();
    private View view;

    TextView tvActivityTitle, tvActivityAssignmentname, tvActivityCoursename, tvActivityClass, tvActivitySubject, tvActivitySubmissiondate, tvActivityTopic;
    EditText etActivityAssignmentname, etActivityCoursename, etActivitySubmissionDate;
    Button btnActivitySave, btnActivityCancel;
    Spinner spActivityClass, spActivitySubject, spActivityTopic;
    RichTextEditor rteTrialActivity;
    private ArrayList<Classrooms> arrListClassRooms;
    private ArrayList<Subjects> arrListSubject;
    private ArrayList<Topics> arrListTopic;
    private List<String> arrListDefalt;
    private DatePickerDialog datePickerDob;
    private Calendar calDob;
    private String strDob="", strAssignmenttext = "",strSubmissionDate="";
    private long lngMaxDob;
    private InputValidator inputValidator;


    public static CreateActivityFragment newInstance() {
        CreateActivityFragment createActivityFragment = new CreateActivityFragment();
        return createActivityFragment;
    }

    public CreateActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignment_activity, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

        inputValidator = new InputValidator(getActivity());

        tvActivityTitle = (TextView) view.findViewById(R.id.tv_activity_title);
        tvActivityAssignmentname = (TextView) view.findViewById(R.id.tv_activity_assignmentname);
        tvActivityCoursename = (TextView) view.findViewById(R.id.tv_activity_coursename);
        tvActivityClass = (TextView) view.findViewById(R.id.tv_activity_class);
        tvActivitySubject = (TextView) view.findViewById(R.id.tv_activity_subject);
        tvActivitySubmissiondate = (TextView) view.findViewById(R.id.tv_activity_submissiondate);
        tvActivityTopic = (TextView) view.findViewById(R.id.tv_activity_topic);

        etActivitySubmissionDate = (EditText) view.findViewById(R.id.et_activity_submissiondate);
        etActivityAssignmentname = (EditText) view.findViewById(R.id.et_activity_assignmentname);
        etActivityCoursename = (EditText) view.findViewById(R.id.et_activity_coursename);

        btnActivitySave = (Button) view.findViewById(R.id.btn_activity_save);
        btnActivityCancel = (Button) view.findViewById(R.id.btn_activity_cancel);

        spActivityClass = (Spinner) view.findViewById(R.id.sp_activity_class);
        spActivitySubject = (Spinner) view.findViewById(R.id.sp_activity_subject);
        spActivityTopic = (Spinner) view.findViewById(R.id.sp_activity_topic);

        rteTrialActivity = (RichTextEditor) view.findViewById(R.id.rte_trial_activity);



        tvActivityTitle.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvActivityAssignmentname.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvActivityCoursename.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvActivityClass.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvActivitySubject.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvActivitySubmissiondate.setTypeface(Global.myTypeFace.getRalewayRegular());
        etActivitySubmissionDate.setTypeface(Global.myTypeFace.getRalewayRegular());
        etActivityAssignmentname.setTypeface(Global.myTypeFace.getRalewayRegular());
        etActivityCoursename.setTypeface(Global.myTypeFace.getRalewayRegular());
        btnActivitySave.setTypeface(Global.myTypeFace.getRalewayRegular());
        btnActivityCancel.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvActivityTopic.setTypeface(Global.myTypeFace.getRalewayRegular());


        btnActivitySave.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   if (isInputsValid()) {
                                                       //callApiCreateAssignment();
                                                   }


                                               }
                                           }
        );

        btnActivityCancel.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     backToTrialScreen();
                                                 }
                                             }
        );


        etActivitySubmissionDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    strSubmissionDate = Utility.showDatePickerDob(getActivity(), etActivitySubmissionDate);
                }
                return true;
            }
        });


        rteTrialActivity.getRichEditor().setEditorFontSize(25);

        rteTrialActivity.getRichEditor().setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {

                strAssignmenttext = text;

            }
        });

        spActivitySubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (arrListSubject != null && position > 0) {

                    if (Utility.isConnected(getActivity())) {

                        callApiGetTopics(Integer.parseInt(arrListSubject.get(position - 1).getId()));
                    } else {
                        Utility.toastOffline(getActivity());
                    }
                } else {
                    Adapters.setUpSpinner(getActivity(), spActivityTopic, arrListDefalt);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        arrListDefalt = new ArrayList<String>();
        arrListDefalt.add(getString(R.string.select));
        Adapters.setUpSpinner(getActivity(), spActivityTopic, arrListDefalt);

        callApiGetClassRooms();
    }


    private void callApiGetClassRooms() {

        if (Utility.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_CLASSROOMS);
            } catch (Exception e) {
                //   Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
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
                // Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
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
                //  Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.showToast(getActivity().getResources().getString(R.string.no_internet), getActivity());
        }

    }

//    private void callApiCreateAssignment() {
//
//        if (Utility.isInternetConnected(getActivity())) {
//
//            try {
//
//                Attribute attribute=new Attribute();
//
//                attribute.setUser_id(WebConstants.USER_ID_370);
//                attribute.setSubmission_date(strDob);
//                attribute.setClassroom_id(spActivityClass.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListClassRooms.
//                        get(spActivityClass.getSelectedItemPosition() - 1).getId()) : 0);
//                attribute.setSubject_id(spActivitySubject.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListSubject.
//                        get(spActivitySubject.getSelectedItemPosition() - 1).getId()) : 0);
//
//                if (arrListTopic.size() > 1) {
//                    attribute.setTopic_id(spActivityTopic.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListTopic.
//                            get(spActivityTopic.getSelectedItemPosition() - 1).getId()) : 0);
//                }
//                attribute.setAssignment_text(strAssignmenttext);
//                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
//                        .execute(WebConstants.CREATE_ASSIGNMENT);
//            } catch (Exception e) {
////                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
//            }
//        } else {
//            Utility.showToast(getActivity().getResources().getString(R.string.no_internet), getActivity());
//        }
//
//    }

    private String strValidationMsg;

    private boolean isInputsValid() {
        return inputValidator.validateStringPresence(etActivityAssignmentname) & inputValidator.validateStringPresence(etActivityCoursename)
                & inputValidator.validateStringPresence(etActivitySubmissionDate) &&
                checkOtherInputs();
    }


    private boolean checkOtherInputs() {
        strValidationMsg = "";
        if (isClassroomSet() & isSubjectSet() & isTextSetInRichTextEditor()) {
            return true;
        } else {
            Utility.alert(getActivity(), null, strValidationMsg);
            return false;
        }
    }


    private boolean isClassroomSet() {
        if (arrListClassRooms != null && arrListClassRooms.size() == 0 || spActivityClass.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getActivity().getResources().getString(R.string.msg_validation_set_classroom);
            return false;
        }
    }

    private boolean isSubjectSet() {

        if (arrListSubject != null && arrListSubject.size() == 0 || spActivitySubject.getSelectedItemPosition() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_set_subject);
            return false;
        }

    }


    private boolean isTopicSet() {
        return true;
    }


    private boolean isTextSetInRichTextEditor() {

        if (strAssignmenttext.trim().length() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_add_text_rich_editor);
            return false;
        }

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

                case WebConstants.CREATE_ASSIGNMENT:
                    onResponseCreateAssignment(object);
                    break;
            }

        } catch (Exception e) {
//            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
        }
    }

    private void onResponseCreateAssignment(Object object) {
        ResponseHandler createAssignmentResponseHandler = (ResponseHandler) object;
        if (createAssignmentResponseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
            backToTrialScreen();
            Utility.showToast(createAssignmentResponseHandler.getMessage(), getActivity());

        } else {
            Utility.showToast(createAssignmentResponseHandler.getMessage(), getActivity());
        }

    }

    private void onResponseGetTopics(Object object) {

        ResponseHandler responseHandler = (ResponseHandler) object;
        if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

            arrListTopic = new ArrayList<>();
            arrListTopic.addAll(responseHandler.getTopics());
            List<String> topics = new ArrayList<String>();
            topics.add(getString(R.string.select));
            for (Topics topic : arrListTopic) {
                topics.add(topic.getTopicName());

            }
            Adapters.setUpSpinner(getActivity(), spActivityTopic, topics, Adapters.ADAPTER_NORMAL);

        } else {

            Adapters.setUpSpinner(getActivity(), spActivityTopic, arrListDefalt);
            Utility.showToast(responseHandler.getMessage(), getActivity());
        }
    }

    private void onResponseGetSubject(Object object) {

        ResponseHandler responseHandler = (ResponseHandler) object;
        if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

            arrListSubject = new ArrayList<>();
            arrListSubject.addAll(responseHandler.getSubjects());
            List<String> subjects = new ArrayList<String>();
            subjects.add(getString(R.string.select));
            for (Subjects subject : arrListSubject) {
                subjects.add(subject.getSubjectName());

            }

            Adapters.setUpSpinner(getActivity(), spActivitySubject, subjects, Adapters.ADAPTER_NORMAL);

        } else {
            Utility.showToast(responseHandler.getMessage(), getActivity());
        }
    }

    private void onResponseGetClassRooms(Object object) {
        ResponseHandler responseHandler = (ResponseHandler) object;
        if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
            arrListClassRooms = new ArrayList<>();
            arrListClassRooms.addAll(responseHandler.getClassrooms());
            List<String> classrooms = new ArrayList<String>();
            classrooms.add(getString(R.string.select));
            for (Classrooms classroom : arrListClassRooms) {
                classrooms.add(classroom.getClassName());

            }
            Adapters.setUpSpinner(getActivity(), spActivityClass, classrooms, Adapters.ADAPTER_NORMAL);
            callApiGetSubjects();

        } else {
            Utility.showToast(responseHandler.getMessage(), getActivity());
        }

    }


    private void backToTrialScreen() {

        ((TeacherHostActivity) getActivity()).onBackPressed();
    }


    @Override
    public void onClick(View v) {
        if (v == btnActivitySave) {
            if (isInputsValid()) {
                //callApiCreateAssignment();
            }
        } else if (v == btnActivityCancel) {
            backToTrialScreen();
        }
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }
}


