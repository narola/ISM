package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.Adapters;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.AuthorBook;
import com.ism.author.ws.model.Classrooms;
import com.ism.author.ws.model.Exams;
import com.ism.author.ws.model.Questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by c166 on 09/11/15.
 */
public class ExamsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = ExamsFragment.class.getSimpleName();
    private View view;
    private RecyclerView rvExamsList;
    private ExamsAdapter examsAdapter;
    private ArrayList<Exams> arrListExams = new ArrayList<Exams>();
    private ArrayList<Exams> copyListExams = new ArrayList<>();
    private MyTypeFace myTypeFace;
    private FragmentListener fragListener;
    private Spinner spExamAuthorBooks, spExamClass, spExamEvaluationStatus;
    private ImageView imgToggleList;
    private ArrayList<AuthorBook> arrListAuthorBooks;
    private ArrayList<Classrooms> arrListClassRooms;
    private List<String> arrListAssessment;

    private TextView txtSubmissionDate, tvNoExamMsg;
    private EditText etExamStartdate, etExamEnddate;
    private String strExamStartDate = "", strExamEndDate = "";


    public static ExamsFragment newInstance() {
        ExamsFragment examsFragment = new ExamsFragment();
        return examsFragment;
    }

    public ExamsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exams, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {
        myTypeFace = new MyTypeFace(getActivity());

        rvExamsList = (RecyclerView) view.findViewById(R.id.rv_exams_list);
        examsAdapter = new ExamsAdapter(getActivity());

        rvExamsList.setHasFixedSize(true);
        rvExamsList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvExamsList.setAdapter(examsAdapter);

        spExamAuthorBooks = (Spinner) view.findViewById(R.id.sp_exam_authorbooks);
        spExamClass = (Spinner) view.findViewById(R.id.sp_exam_class);
        spExamEvaluationStatus = (Spinner) view.findViewById(R.id.sp_exam_evaluation_status);


        arrListAssessment = new ArrayList<String>();
        arrListAssessment = Arrays.asList(getResources().getStringArray(R.array.assessment_type));
        Adapters.setUpSpinner(getActivity(), spExamEvaluationStatus, arrListAssessment, Adapters.ADAPTER_SMALL);

        txtSubmissionDate = (TextView) view.findViewById(R.id.txt_submission_date);
        tvNoExamMsg = (TextView) view.findViewById(R.id.tv_no_exam_msg);
        etExamStartdate = (EditText) view.findViewById(R.id.et_exam_startdate);
        etExamEnddate = (EditText) view.findViewById(R.id.et_exam_startTime);


        txtSubmissionDate.setTypeface(myTypeFace.getRalewayRegular());
        tvNoExamMsg.setTypeface(myTypeFace.getRalewayRegular());
        etExamStartdate.setTypeface(myTypeFace.getRalewayRegular());
        etExamEnddate.setTypeface(myTypeFace.getRalewayRegular());

        etExamStartdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    strExamStartDate = Utils.showDatePickerDob(getActivity(), etExamStartdate);
                }
                return true;
            }
        });

        etExamEnddate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    strExamEndDate = Utils.showDatePickerDob(getActivity(), etExamEnddate);
                }
                return true;
            }
        });

        callApiGetAuthorBooks();
        callApiGetClassrooms();
        callApiGetAllAssignments();


        /**
         * ItemselectedListener to handle filter based on particular spinner
         */

        AdapterView.OnItemSelectedListener spinnerListenerforFilters = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                filterAssignmentResults(adapterView, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };

        spExamAuthorBooks.setOnItemSelectedListener(spinnerListenerforFilters);
        spExamClass.setOnItemSelectedListener(spinnerListenerforFilters);
        spExamEvaluationStatus.setOnItemSelectedListener(spinnerListenerforFilters);
    }


    private void filterAssignmentResults(View view, int position) {
        if (view == spExamAuthorBooks) {

            if (arrListAuthorBooks != null && position > 0) {
                spExamClass.setSelection(0);
                spExamEvaluationStatus.setSelection(0);
                if (position > 1) {
                    /**
                     * Position-2 because two static elements are added in the spinner in beginning
                     * so to fetch the first element from arraylist at index zero we are doing position-2
                     */
                    filterAuthorBookWiseAssignments(arrListAuthorBooks.get(position - 2).getBookId());
                } else {
                    clearFilters();
                }
            }
        } else if (view == spExamClass) {
            if (arrListClassRooms != null && position > 0) {
                spExamAuthorBooks.setSelection(0);
                spExamEvaluationStatus.setSelection(0);
                if (position > 1) {
                    filterClassroomWiseAssignments(arrListClassRooms.get(position - 2).getId());

                } else {
                    clearFilters();
                }
            }
        } else if (view == spExamEvaluationStatus) {
            if (position > 1) {
                spExamAuthorBooks.setSelection(0);
                spExamClass.setSelection(0);
                filterAssessedNotAssessedAssignments(arrListAssessment.get(position));

            } else {
                clearFilters();
            }
        }

    }

    private void filterAuthorBookWiseAssignments(String bookId) {
        copyListExams.clear();
        if (arrListExams.size() > 0) {
            for (Exams wp : arrListExams) {
                if (wp.getBookId().equalsIgnoreCase(bookId)) {
                    copyListExams.add(wp);
                }
            }
            examsAdapter.addAll(copyListExams);
            if (!(copyListExams.size() > 0)) {
                Utils.showToast(getString(R.string.msg_validation_no_exams_filter), getActivity());
            }
        }
    }

    private void clearFilters() {
        examsAdapter.addAll(arrListExams);
    }

    private void filterClassroomWiseAssignments(String classroom_id) {
        copyListExams.clear();
        if (arrListExams.size() > 0) {
            for (Exams wp : arrListExams) {
                if (wp.getClassroomId().equalsIgnoreCase(classroom_id)) {
                    copyListExams.add(wp);
                }
            }

            examsAdapter.addAll(copyListExams);
            if (!(copyListExams.size() > 0)) {
                Utils.showToast(getString(R.string.msg_validation_no_exams_filter), getActivity());
            }
        }
    }

    private void filterAssessedNotAssessedAssignments(String evaluation_status) {
        copyListExams.clear();
        if (arrListExams.size() > 0) {
            for (Exams wp : arrListExams) {
                if (!wp.getEvaluationStatus().equals("") && wp.getEvaluationStatus().equalsIgnoreCase(evaluation_status)) {
                    copyListExams.add(wp);
                }
            }
            examsAdapter.addAll(copyListExams);
            if (!(copyListExams.size() > 0)) {
                Utils.showToast(getString(R.string.msg_validation_no_exams_filter), getActivity());
            }

        }
    }

    private void callApiGetAllAssignments() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute request = new Attribute();
                request.setUserId(Global.strUserId);
                request.setRole(Global.role);
                new WebserviceWrapper(getActivity(), request, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETALLASSIGNMENTS);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }

    private void callApiGetClassrooms() {

        if (Utility.isConnected(getActivity())) {
            try {

                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETCLASSROOMS);

            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }

    private void callApiGetAuthorBooks() {

        if (Utility.isConnected(getActivity())) {
            try {

                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETBOOKSFORAUTHOR);
            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GETALLASSIGNMENTS:
                    onResponseGetAllAssignments(object, error);
                    break;

                case WebConstants.GETCLASSROOMS:
                    onResponseGetClassrooms(object, error);
                    break;

                case WebConstants.GETBOOKSFORAUTHOR:
                    onResponseGetAuthorBooks(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetAllAssignments(Object object, Exception error) {

        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    if (responseHandler.getExams().size() > 0) {
                        arrListExams.addAll(responseHandler.getExams());
                        examsAdapter.addAll(arrListExams);
                        examsAdapter.notifyDataSetChanged();
                        tvNoExamMsg.setVisibility(View.GONE);
                    } else {
                        tvNoExamMsg.setVisibility(View.VISIBLE);
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());

                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllAssignments api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllAssignments Exception : " + e.toString());
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
                    classrooms.add(getString(R.string.strclass));
                    classrooms.add(getString(R.string.strall));
                    for (Classrooms classroom : arrListClassRooms) {
                        classrooms.add(classroom.getClassName());

                    }
                    Adapters.setUpSpinner(getActivity(), spExamClass, classrooms, Adapters.ADAPTER_SMALL);
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

    private void onResponseGetAuthorBooks(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    arrListAuthorBooks = new ArrayList<AuthorBook>();
                    arrListAuthorBooks.addAll(responseHandler.getAuthorBook());
                    List<String> authorBooks = new ArrayList<String>();
                    authorBooks.add(getString(R.string.strbookname));
                    authorBooks.add(getString(R.string.strall));
                    for (AuthorBook authorBook : arrListAuthorBooks) {
                        authorBooks.add(authorBook.getBookName());

                    }
                    Adapters.setUpSpinner(getActivity(), spExamAuthorBooks, authorBooks, Adapters.ADAPTER_SMALL);
                    spExamAuthorBooks.setSelection(1);
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAuthorBooks api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAuthorBooks Exception : " + e.toString());
        }
    }


    public ArrayList<Questions> copylistOfQuestionBank = new ArrayList<Questions>();


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_ASSESSMENT);
                Debug.i(TAG, "attach");
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_ASSESSMENT);
                Debug.i(TAG, "detach");
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


}
