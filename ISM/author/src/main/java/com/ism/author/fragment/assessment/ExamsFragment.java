package com.ism.author.fragment.assessment;

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
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.Adapters;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.utility.Debug;
import com.ism.author.utility.Utility;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.BookData;
import com.ism.author.ws.model.Classrooms;
import com.ism.author.ws.model.Exams;
import com.ism.author.ws.model.Questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.RealmResults;
import model.AuthorBook;
import realmhelper.AuthorHelper;

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
    private FragmentListener fragListener;
    private Spinner spExamAuthorBooks, spExamClass, spExamEvaluationStatus;
    private ImageView imgToggleList;

    private List<String> arrListAssessment;

    private TextView txtSubmissionDate, tvNoDataMsg;
    private EditText etExamStartdate, etExamEnddate;
    private String strExamStartDate = "", strExamEndDate = "";
    private AuthorHelper authorHelper;
    private RealmResults<AuthorBook> arrListAuthorBooks = null;
    private RealmResults<model.Classrooms> arrListClassRooms = null;


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

        authorHelper = new AuthorHelper(getActivity());

        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);
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
        Adapters.setUpSpinner(getActivity(), spExamEvaluationStatus, arrListAssessment, Global.myTypeFace.getRalewayRegular(), R.layout.list_item_simple_light);

        txtSubmissionDate = (TextView) view.findViewById(R.id.txt_submission_date);
        etExamStartdate = (EditText) view.findViewById(R.id.et_exam_startdate);
        etExamEnddate = (EditText) view.findViewById(R.id.et_exam_startTime);


        txtSubmissionDate.setTypeface(Global.myTypeFace.getRalewayRegular());

        etExamStartdate.setTypeface(Global.myTypeFace.getRalewayRegular());
        etExamEnddate.setTypeface(Global.myTypeFace.getRalewayRegular());

        etExamStartdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    strExamStartDate = Utility.showDatePickerDob(getActivity(), etExamStartdate);
                }
                return true;
            }
        });

        etExamEnddate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    strExamEndDate = Utility.showDatePickerDob(getActivity(), etExamEnddate);
                }
                return true;
            }
        });


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


        setEmptyView(false);
        callApiGetAuthorBooks();
        callApiGetClassrooms();
        callApiGetAllExams();
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
                    filterAuthorBookWiseAssignments(String.valueOf(arrListAuthorBooks.get(position - 2).getBook().getBookId()));
                } else {
                    clearFilters();
                }
            }
        } else if (view == spExamClass) {
            if (arrListClassRooms != null && position > 0) {
                spExamAuthorBooks.setSelection(0);
                spExamEvaluationStatus.setSelection(0);
                if (position > 1) {
                    filterClassroomWiseAssignments(String.valueOf(arrListClassRooms.get(position - 2).getClassRoomId()));

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
            showMsgNoFilterData(false);
            for (Exams wp : arrListExams) {
                if (wp.getBookId().equalsIgnoreCase(bookId)) {
                    copyListExams.add(wp);
                }
            }
            examsAdapter.addAll(copyListExams);
            if (!(copyListExams.size() > 0)) {
                showMsgNoFilterData(true);
            }
        }
    }

    private void clearFilters() {
        showMsgNoFilterData(false);
        examsAdapter.addAll(arrListExams);
    }

    private void showMsgNoFilterData(Boolean showMsg) {

        tvNoDataMsg.setText(getResources().getString(R.string.no_filter_exams));
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setVisibility(showMsg ? View.VISIBLE : View.GONE);
        rvExamsList.setVisibility(showMsg ? View.GONE : View.VISIBLE);
    }

    private void filterClassroomWiseAssignments(String classroom_id) {
        copyListExams.clear();
        if (arrListExams.size() > 0) {
            showMsgNoFilterData(false);
            for (Exams wp : arrListExams) {
                if (wp.getClassroomId().equalsIgnoreCase(classroom_id)) {
                    copyListExams.add(wp);
                }
            }

            examsAdapter.addAll(copyListExams);
            if (!(copyListExams.size() > 0)) {
                showMsgNoFilterData(true);
            }
        }
    }

    private void filterAssessedNotAssessedAssignments(String evaluation_status) {
        copyListExams.clear();
        if (arrListExams.size() > 0) {
            showMsgNoFilterData(false);
            for (Exams wp : arrListExams) {
                if (!wp.getEvaluationStatus().equals("") && wp.getEvaluationStatus().equalsIgnoreCase(evaluation_status)) {
                    copyListExams.add(wp);
                }
            }
            examsAdapter.addAll(copyListExams);
            if (!(copyListExams.size() > 0)) {
                showMsgNoFilterData(true);
            }

        }
    }

    private void callApiGetAllExams() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                attribute.setRole(Global.role);
                attribute.setExamCategory("");
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETALLEXAMS);
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
                new WebserviceWrapper(getActivity(), new Attribute(), (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETALLCLASSROOMS);
            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            setUpClassrooms();
        }

    }

    private void callApiGetAuthorBooks() {

        if (Utility.isConnected(getActivity())) {
            try {
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETBOOKSBYAUTHOR);
            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            setUpAuthorBooksData();
        }

    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GETALLEXAMS:
                    onResponseGetAllExams(object, error);
                    break;

                case WebConstants.GETALLCLASSROOMS:
                    onResponseGetClassrooms(object, error);
                    break;

                case WebConstants.GETBOOKSBYAUTHOR:
                    onResponseGetAuthorBooks(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetAllExams(Object object, Exception error) {

        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    if (responseHandler.getExams().size() > 0) {

                        addExams(responseHandler.getExams());


                        arrListExams.addAll(responseHandler.getExams());
                        examsAdapter.addAll(arrListExams);
                        examsAdapter.notifyDataSetChanged();
                        setEmptyView(false);

                    } else {

                        setEmptyView(true);

                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());

                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllExams api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllExams Exception : " + e.toString());
        }
    }

    private void onResponseGetClassrooms(Object object, Exception error) {
        try {

            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    addClassRooms(responseHandler.getClassrooms());
                    setUpClassrooms();

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
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

                    addAuthorBooks(responseHandler.getAuthorBook());
                    setUpAuthorBooksData();

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAuthorBooks api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAuthorBooks Exception : " + e.toString());
        }
    }


    private void addAuthorBooks(ArrayList<BookData> arrListAuthorBooks) {
        if (arrListAuthorBooks.size() > 0) {
            for (BookData authorBook : arrListAuthorBooks) {
                authorHelper.addAuthorBooks(Global.getRealmDataModel.getRealmAuthorBook(authorBook, arrListAuthorBooks.indexOf(authorBook)));
            }
        }
    }

    private void addClassRooms(ArrayList<Classrooms> arrListClassrooms) {

        if (arrListClassrooms.size() > 0) {
            for (Classrooms classroom : arrListClassrooms) {
                authorHelper.addClassrooms(Global.getRealmDataModel.getRealmClassroom(classroom));
            }
        }

    }


    private void addExams(ArrayList<Exams> arrListExams) {

        if (arrListExams.size() > 0) {
            for (Exams exam : arrListExams) {
                authorHelper.addExams(Global.getRealmDataModel.getRealmExam(exam, authorHelper));
            }
        }

    }

    private void setUpAuthorBooksData() {

        arrListAuthorBooks = authorHelper.getAuthorBooks();
        List<String> authorBooks = new ArrayList<String>();
        authorBooks.add(getString(R.string.strbookname));
        authorBooks.add(getString(R.string.strall));
        for (model.AuthorBook authorBook : arrListAuthorBooks) {
            authorBooks.add(authorBook.getBook().getBookName());

        }
        Adapters.setUpSpinner(getActivity(), spExamAuthorBooks, authorBooks, Global.myTypeFace.getRalewayRegular(), R.layout.list_item_simple_light);
        spExamAuthorBooks.setSelection(1);
    }

    private void setUpClassrooms() {

        arrListClassRooms = authorHelper.getClassrooms();
        List<String> classrooms = new ArrayList<String>();
        classrooms.add(getString(R.string.strclass));
        classrooms.add(getString(R.string.strall));
        for (model.Classrooms classroom : arrListClassRooms) {
            classrooms.add(classroom.getClassName());

        }
        Adapters.setUpSpinner(getActivity(), spExamClass, classrooms, Global.myTypeFace.getRalewayRegular(), R.layout.list_item_simple_light);
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
                authorHelper.realm.close();
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }
    /*remove the arguments which are not necessary here*/

    public void onBackClick() {

        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_ID);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_NAME);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_CLASSROOM_ID);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_CLASSROOM_NAME);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_BOOK_ID);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_BOOK_NAME);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_CATEGORY);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_MODE);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_PASS_PERCENTAGE);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_DURATION);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_ATTEMPT_COUNT);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_INSTRUCTIONS);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_IS_RANDOM_QUESTION);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_IS_NEGATIVE_MARKING);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_NEGATIVE_MARK_VALUE);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_IS_DECLARE_RESULTS);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_ASSESSOR);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_START_DATE);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_START_TIME);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_CREATED_DATE);
        getBundleArguments().remove(ExamsAdapter.ARG_EXAM_NO);
        getBundleArguments().remove(ExamsAdapter.ARG_FRAGMENT_TYPE);
        getBundleArguments().remove(ExamsAdapter.ARG_ISLOAD_FRAGMENTFOREVALUATION);


        ((AuthorHostActivity) getActivity()).handleBackClick(AppConstant.FRAGMENT_ASSESSMENT);
    }


    public Bundle getBundleArguments() {
        return ((AuthorHostActivity) getActivity()).getBundle();
    }


    private void setEmptyView(boolean isEnable) {

        tvNoDataMsg.setText(getResources().getString(R.string.no_exams));
        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvExamsList.setVisibility(isEnable ? View.GONE : View.VISIBLE);

    }
}
