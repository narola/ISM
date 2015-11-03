package com.ism.author.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.author.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.Utility.InputValidator;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.adapter.Adapters;
import com.ism.author.constant.WebConstants;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.model.CreateAssignmentRequest;
import com.ism.author.model.Data;
import com.ism.author.model.GetTopicsRequest;
import com.ism.author.model.ResponseObject;
import com.ism.author.ws.WebserviceWrapper;
import com.narola.kpa.richtexteditor.view.RichTextEditor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;


/**
 * Created by c166 on 28/10/15.
 */
public class TrialActivityFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = TrialActivityFragment.class.getSimpleName();
    private View view;
    private FragmentListener fragListener;

    public static TrialActivityFragment newInstance() {
        TrialActivityFragment trialActivityFragment = new TrialActivityFragment();
        return trialActivityFragment;
    }

    public TrialActivityFragment() {
        // Required empty public constructor
    }

    TextView tvActivityTitle, tvActivityAssignmentname, tvActivityCoursename, tvActivityClass, tvActivitySubject, tvActivitySubmissiondate, tvActivityTopic;
    EditText etActivityAssignmentname, etActivityCoursename, etActivitySubmissionDate;
    Button btnActivitySave, btnActivityCancel;
    Spinner spActivityClass, spActivitySubject, spActivityTopic;
    RichTextEditor rteTrialActivity;
    private ArrayList<Data> arrListClassRooms;
    private ArrayList<Data> arrListSubject;
    private ArrayList<Data> arrListTopic;
    private List<String> arrListDefalt;
    private DatePickerDialog datePickerDob;
    private Calendar calDob;
    private String strDob, strAssignmenttext = "";
    private long lngMaxDob;
    MyTypeFace myTypeFace;
    private InputValidator inputValidator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trial_activity, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());
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


        tvActivityTitle.setTypeface(myTypeFace.getRalewayRegular());
        tvActivityAssignmentname.setTypeface(myTypeFace.getRalewayRegular());
        tvActivityCoursename.setTypeface(myTypeFace.getRalewayRegular());
        tvActivityClass.setTypeface(myTypeFace.getRalewayRegular());
        tvActivitySubject.setTypeface(myTypeFace.getRalewayRegular());
        tvActivitySubmissiondate.setTypeface(myTypeFace.getRalewayRegular());
        etActivitySubmissionDate.setTypeface(myTypeFace.getRalewayRegular());
        etActivityAssignmentname.setTypeface(myTypeFace.getRalewayRegular());
        etActivityCoursename.setTypeface(myTypeFace.getRalewayRegular());
        btnActivitySave.setTypeface(myTypeFace.getRalewayRegular());
        btnActivityCancel.setTypeface(myTypeFace.getRalewayRegular());
        tvActivityTopic.setTypeface(myTypeFace.getRalewayRegular());


        btnActivitySave.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   if (isInputsValid()) {
                                                       callApiCreateAssignment();
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
                    showDatePickerDob();
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

                    if (Utility.isOnline(getActivity())) {

                        callApiGetTopics(Integer.parseInt(arrListSubject.get(position - 1).getId()));
                    } else {
                        Utility.toastOffline(getActivity());
                    }
                } else {
                    com.ism.author.adapter.Adapters.setUpSpinner(getActivity(), spActivityTopic, arrListDefalt);
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


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TrialAddNewFragment.FRAGMENT_TRIAL_ACTIVITY);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(TrialAddNewFragment.FRAGMENT_TRIAL_ACTIVITY);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    private void callApiGetClassRooms() {

        if (Utils.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETCLASSROOMS);
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
                        .execute(WebserviceWrapper.GETSUBJECT);
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
                GetTopicsRequest getTopicsRequest = new GetTopicsRequest();
                getTopicsRequest.setSubject_id(subject_id);
                new WebserviceWrapper(getActivity(), getTopicsRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETTOPICS);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }

    private void callApiCreateAssignment() {

        if (Utils.isInternetConnected(getActivity())) {

            try {

                CreateAssignmentRequest createAssignmentRequest = new CreateAssignmentRequest();
                createAssignmentRequest.setUser_id(WebConstants.TEST_USER_ID);
                createAssignmentRequest.setSubmission_date(strDob);
                createAssignmentRequest.setClassroom_id(spActivityClass.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListClassRooms.
                        get(spActivityClass.getSelectedItemPosition() - 1).getId()) : 0);
                createAssignmentRequest.setSubject_id(spActivitySubject.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListSubject.
                        get(spActivitySubject.getSelectedItemPosition() - 1).getId()) : 0);

                if (arrListTopic.size() > 1) {
                    createAssignmentRequest.setTopic_id(spActivityTopic.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListTopic.
                            get(spActivityTopic.getSelectedItemPosition() - 1).getId()) : 0);
                }
                createAssignmentRequest.setAssignment_text(strAssignmenttext);

                new WebserviceWrapper(getActivity(), createAssignmentRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.CREATEASSIGNMENT);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
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
            if (apiMethodName == WebserviceWrapper.GETCLASSROOMS) {

                ResponseObject callGetClassRoomsResponseObject = (ResponseObject) object;
                if (callGetClassRoomsResponseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && callGetClassRoomsResponseObject != null) {
                    arrListClassRooms = new ArrayList<Data>();
                    arrListClassRooms.addAll(callGetClassRoomsResponseObject.getData());
                    List<String> classrooms = new ArrayList<String>();
                    classrooms.add(getString(R.string.select));
                    for (Data classroom : arrListClassRooms) {
                        classrooms.add(classroom.getClassName());

                    }
                    Adapters.setUpSpinner(getActivity(), spActivityClass, classrooms);
                    callApiGetSubjects();

                } else {
                    Utils.showToast(callGetClassRoomsResponseObject.getMessage(), getActivity());
                }

            } else if (apiMethodName == WebserviceWrapper.GETSUBJECT) {

                ResponseObject callGetSubjectResponseObject = (ResponseObject) object;
                if (callGetSubjectResponseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && callGetSubjectResponseObject != null) {

                    arrListSubject = new ArrayList<Data>();
                    arrListSubject.addAll(callGetSubjectResponseObject.getData());
                    List<String> subjects = new ArrayList<String>();
                    subjects.add(getString(R.string.select));
                    for (Data subject : arrListSubject) {
                        subjects.add(subject.getSubjectName());

                    }

                    Adapters.setUpSpinner(getActivity(), spActivitySubject, subjects);

                } else {
                    Utils.showToast(callGetSubjectResponseObject.getMessage(), getActivity());
                }

            } else if (apiMethodName == WebserviceWrapper.GETTOPICS) {

                ResponseObject callGetTopicsResponseObject = (ResponseObject) object;
                if (callGetTopicsResponseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && callGetTopicsResponseObject != null) {

                    arrListTopic = new ArrayList<Data>();
                    arrListTopic.addAll(callGetTopicsResponseObject.getData());
                    List<String> topics = new ArrayList<String>();
                    topics.add(getString(R.string.select));
                    for (Data topic : arrListTopic) {
                        topics.add(topic.getTopicName());

                    }
                    Adapters.setUpSpinner(getActivity(), spActivityTopic, topics);

                } else {

                    Adapters.setUpSpinner(getActivity(), spActivityTopic, arrListDefalt);
                    Utils.showToast(callGetTopicsResponseObject.getMessage(), getActivity());
                }

            } else if (apiMethodName == WebserviceWrapper.CREATEASSIGNMENT) {
                ResponseObject createAssignmentResponseObject = (ResponseObject) object;
                if (createAssignmentResponseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && createAssignmentResponseObject != null) {
                    backToTrialScreen();
                    Utils.showToast(createAssignmentResponseObject.getMessage(), getActivity());

                } else {
                    Utils.showToast(createAssignmentResponseObject.getMessage(), getActivity());
                }

            }


        } catch (Exception e) {
            Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
        }
    }

    private void showDatePickerDob() {
        try {
            if (calDob == null) {
                calDob = Calendar.getInstance();
                calDob.add(Calendar.YEAR, -3);
                lngMaxDob = calDob.getTimeInMillis();
            }
            datePickerDob = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calDob.set(Calendar.YEAR, year);
                    calDob.set(Calendar.MONTH, monthOfYear);
                    calDob.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    strDob = Utility.formatDateApi(calDob.getTime());
                    etActivitySubmissionDate.setText(Utility.formatDateDisplay(calDob.getTime()));
                }
            }, calDob.get(Calendar.YEAR), calDob.get(Calendar.MONTH), calDob.get(Calendar.DAY_OF_MONTH));
            datePickerDob.getDatePicker().setMaxDate(lngMaxDob);
            datePickerDob.show();
        } catch (Exception e) {
            Log.e(TAG, "showDatePickerDob Exception : " + e.toString());
        }
    }


    private void backToTrialScreen() {

        ((AuthorHostActivity) getActivity()).onBackPressed();
    }


}


