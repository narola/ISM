package com.ism.author.fragment.createexam;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.InputValidator;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.Adapters;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Classrooms;
import com.ism.author.ws.model.Subjects;
import com.ism.author.ws.model.Topics;
import com.narola.kpa.richtexteditor.view.RichTextEditor;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by c166 on 28/10/15.
 */
public class CreateAssignmentFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {


    private static final String TAG = CreateAssignmentFragment.class.getSimpleName();
    private View view;

    public static CreateAssignmentFragment newInstance() {
        CreateAssignmentFragment createAssignmentFragment = new CreateAssignmentFragment();
        return createAssignmentFragment;
    }

    public CreateAssignmentFragment() {
        // Required empty public constructor
    }

    private TextView tvActivityTitle, tvActivityAssignmentname, tvActivityCoursename, tvActivityClass, tvActivitySubject,
            tvActivitySubmissiondate, tvActivityTopic, tvActivitySave, tvActivityCancel;
    private EditText etActivityAssignmentname, etActivityCoursename, etActivitySubmissionDate;
    private Spinner spActivityClass, spActivitySubject, spActivityTopic;
    private ArrayList<Classrooms> arrListClassRooms;
    private ArrayList<Subjects> arrListSubject;
    private ArrayList<Topics> arrListTopic;
    private List<String> arrListDefalt;
    private String strSubmissionDate;

    private InputValidator inputValidator;

    private RichTextEditor rteTrialActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_assignment, container, false);

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

        tvActivitySave = (TextView) view.findViewById(R.id.tv_activity_save);
        tvActivityCancel = (TextView) view.findViewById(R.id.tv_activity_cancel);

        spActivityClass = (Spinner) view.findViewById(R.id.sp_activity_class);
        spActivitySubject = (Spinner) view.findViewById(R.id.sp_activity_subject);
        spActivityTopic = (Spinner) view.findViewById(R.id.sp_activity_topic);


        tvActivityTitle.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvActivityAssignmentname.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvActivityCoursename.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvActivityClass.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvActivitySubject.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvActivitySubmissiondate.setTypeface(Global.myTypeFace.getRalewayRegular());
        etActivitySubmissionDate.setTypeface(Global.myTypeFace.getRalewayRegular());
        etActivityAssignmentname.setTypeface(Global.myTypeFace.getRalewayRegular());
        etActivityCoursename.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvActivitySave.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvActivityCancel.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvActivityTopic.setTypeface(Global.myTypeFace.getRalewayRegular());


        tvActivitySave.setOnClickListener(this);
        tvActivityCancel.setOnClickListener(this);

        etActivitySubmissionDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    strSubmissionDate = Utils.showDatePickerDob(getActivity(), etActivitySubmissionDate);

                }
                return true;
            }
        });

        rteTrialActivity = (RichTextEditor) view.findViewById(R.id.rte_trial_activity);
        rteTrialActivity.getRichEditor().setEditorFontSize(20);
        rteTrialActivity.hideMediaControls();


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
                    Adapters.setUpSpinner(getActivity(), spActivityTopic, arrListDefalt, Global.myTypeFace.getRalewayRegular(), R.layout.simple_spinner);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        arrListDefalt = new ArrayList<String>();
        arrListDefalt.add(getString(R.string.strtopic));
        Adapters.setUpSpinner(getActivity(), spActivityTopic, arrListDefalt, Global.myTypeFace.getRalewayRegular(), R.layout.simple_spinner);


        callApiGetClassRooms();
        callApiGetSubjects();
    }

    private void callApiGetClassRooms() {

        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                new WebserviceWrapper(getActivity(), new Attribute(), (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETALLCLASSROOMS);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }

    private void callApiGetSubjects() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                new WebserviceWrapper(getActivity(), new Attribute(), (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETALLSUBJECT);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }

    private void callApiGetTopics(int subject_id) {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
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

    private void callApiCreateAssignment() {

        if (Utility.isConnected(getActivity())) {

            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                attribute.setSubmissionDate(strSubmissionDate);
                attribute.setClassroomId(String.valueOf(spActivityClass.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListClassRooms.
                        get(spActivityClass.getSelectedItemPosition() - 1).getId()) : 0));
                attribute.setSubjectId(String.valueOf(spActivitySubject.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListSubject.
                        get(spActivitySubject.getSelectedItemPosition() - 1).getId()) : 0));

                if (arrListTopic.size() > 1) {
                    attribute.setTopicId(String.valueOf(spActivityTopic.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListTopic.
                            get(spActivityTopic.getSelectedItemPosition() - 1).getId()) : 0));
                }
                attribute.setAssignmentText(rteTrialActivity.getHtml());

                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.CREATEASSIGNMENT);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }

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
            strValidationMsg += getString(R.string.msg_validation_set_classroom);
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

        if (rteTrialActivity.getHtml().length() > 0) {
            return true;
        } else {
            strValidationMsg += getString(R.string.msg_validation_add_text_rich_editor);
            return false;
        }

    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {

        try {
            switch (apiCode) {
                case WebConstants.GETALLCLASSROOMS:
                    onResponseGetClassrooms(object, error);
                    break;
                case WebConstants.GETALLSUBJECT:
                    onResponseGetSubjects(object, error);
                    break;
                case WebConstants.GETTOPICS:
                    onResponseGetTopics(object, error);
                    break;
                case WebConstants.CREATEASSIGNMENT:
                    onResponseCreateAssignment(object, error);
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
                    classrooms.add(getString(R.string.select));
                    for (Classrooms classroom : arrListClassRooms) {
                        classrooms.add(classroom.getClassName());

                    }
                    Adapters.setUpSpinner(getActivity(), spActivityClass, classrooms, Global.myTypeFace.getRalewayRegular(), R.layout.simple_spinner);

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
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    arrListSubject = new ArrayList<Subjects>();
                    arrListSubject.addAll(responseHandler.getSubjects());
                    List<String> subjects = new ArrayList<String>();
                    subjects.add(getString(R.string.select));
                    for (Subjects subject : arrListSubject) {
                        subjects.add(subject.getSubjectName());

                    }
                    Adapters.setUpSpinner(getActivity(), spActivitySubject, subjects, Global.myTypeFace.getRalewayRegular(), R.layout.simple_spinner);
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
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListTopic = new ArrayList<Topics>();
                    arrListTopic.addAll(responseHandler.getTopics());
                    List<String> topics = new ArrayList<String>();
                    topics.add(getString(R.string.select));
                    for (Topics topic : arrListTopic) {
                        topics.add(topic.getTopicName());

                    }
                    Adapters.setUpSpinner(getActivity(), spActivityTopic, topics, Global.myTypeFace.getRalewayRegular(), R.layout.simple_spinner);
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Adapters.setUpSpinner(getActivity(), spActivityTopic, arrListDefalt, Global.myTypeFace.getRalewayRegular(), R.layout.simple_spinner);
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetTopics api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetTopics Exception : " + e.toString());
        }
    }

    private void onResponseCreateAssignment(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    backToTrialScreen();
                    Utils.showToast(getActivity().getString(R.string.msg_success_createassignment), getActivity());
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseCreateAssignment api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseCreateAssignment Exception : " + e.toString());
        }
    }


    private void backToTrialScreen() {
        ((AuthorHostActivity) getActivity()).onBackPressed();
    }


    @Override
    public void onClick(View v) {
        if (v == tvActivitySave) {
            if (isInputsValid()) {
                callApiCreateAssignment();
            }
        } else if (v == tvActivityCancel) {
            backToTrialScreen();
        }

    }
}


