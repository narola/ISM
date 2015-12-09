package com.ism.author.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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
import com.ism.author.adapter.QuestionBankListAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.AuthorBook;
import com.ism.author.ws.model.Courses;
import com.ism.author.ws.model.Questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * these fragment is for getting the questionbank.
 */
public class QuestionListFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {


    private static final String TAG = QuestionListFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;

    public QuestionListFragment() {
    }

    @SuppressLint("ValidFragment")
    public QuestionListFragment(Fragment fragment, Bundle bundleArguments) {
        this.mFragment = fragment;
        this.setArguments(bundleArguments);
    }

    private Spinner spQuestionlistFilter, spQuestionlistAuthorBooks, spQuestionlistSort;
    private List<String> arrListFilter, arrListDefalt, arrListSort;
    private List<AuthorBook> arrListAuthorBooks;
    private List<Courses> arrListCourses;
    private EditText etSearchQuestions;
    private TextView tvQuestionlistTitle, tvQuestionlistAddNewQuestion, tvQuestionlistAddPreview;
    private RecyclerView rvQuestionlist;
    private QuestionBankListAdapter questionBankListAdapter;
    private ArrayList<Questions> arrListQuestions = new ArrayList<Questions>();
    private MyTypeFace myTypeFace;
    private ImageView imgSearchQuestions;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionlist, container, false);

//        Utils.showToast("THE QUESTION LIST FRAGMENT CALLED", getActivity());
        initGlobal();
        return view;
    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());
        imgSearchQuestions = (ImageView) view.findViewById(R.id.img_search_questions);

        spQuestionlistFilter = (Spinner) view.findViewById(R.id.sp_questionlist_filter);
        spQuestionlistAuthorBooks = (Spinner) view.findViewById(R.id.sp_questionlist_author_books);
        spQuestionlistSort = (Spinner) view.findViewById(R.id.sp_questionlist_sorting);

        etSearchQuestions = (EditText) view.findViewById(R.id.et_search_questions);

        tvQuestionlistTitle = (TextView) view.findViewById(R.id.tv_questionlist_title);
        tvQuestionlistAddNewQuestion = (TextView) view.findViewById(R.id.tv_questionlist_add_new_question);
        tvQuestionlistAddPreview = (TextView) view.findViewById(R.id.tv_questionlist_add_preview);

        rvQuestionlist = (RecyclerView) view.findViewById(R.id.rv_questionlist);
        questionBankListAdapter = new QuestionBankListAdapter(getActivity(), mFragment);
        rvQuestionlist.setAdapter(questionBankListAdapter);
        rvQuestionlist.setLayoutManager(new LinearLayoutManager(getActivity()));


        etSearchQuestions.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistTitle.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistAddNewQuestion.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistAddPreview.setTypeface(myTypeFace.getRalewayRegular());
        tvQuestionlistAddNewQuestion.setOnClickListener(this);
        tvQuestionlistAddPreview.setOnClickListener(this);


        arrListFilter = new ArrayList<String>();
        arrListFilter.add(getString(R.string.strfilters));
        arrListFilter = Arrays.asList(getResources().getStringArray(R.array.questionsfilter));
        Adapters.setUpSpinner(getActivity(), spQuestionlistFilter, arrListFilter, Adapters.ADAPTER_SMALL);
        spQuestionlistFilter.setSelection(1);


        arrListSort = new ArrayList<String>();
        arrListSort = Arrays.asList(getResources().getStringArray(R.array.questionsSorting));
        Adapters.setUpSpinner(getActivity(), spQuestionlistSort, arrListSort, Adapters.ADAPTER_SMALL);
        spQuestionlistSort.setSelection(1);


        imgSearchQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etSearchQuestions.getVisibility() == View.VISIBLE) {
//                    startSlideAnimation(etSearchMystudents, 0, etSearchMystudents.getWidth(), 0, 0);
//                    startSlideAnimation(imgSearchMystudents, -imgSearchMystudents.getWidth(), 0, 0, 0);
                    etSearchQuestions.setVisibility(View.GONE);
                    questionBankListAdapter.filter("");
                    etSearchQuestions.setText("");

                } else {
                    startSlideAnimation(etSearchQuestions, etSearchQuestions.getWidth(), 0, 0, 0);
                    startSlideAnimation(imgSearchQuestions, etSearchQuestions.getWidth(), 0, 0, 0);
                    etSearchQuestions.setVisibility(View.VISIBLE);
                    Utility.showSoftKeyboard(etSearchQuestions, getActivity());
                }
            }
        });

        etSearchQuestions.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                questionBankListAdapter.filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spQuestionlistAuthorBooks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 1) {
                    if (arrListAuthorBooks.get(position - 1).getBookId().equalsIgnoreCase(getArguments().getString(ExamsAdapter.ARG_EXAM_BOOK_ID))) {
                        questionBankListAdapter.canAddToPreview = true;
                    } else {
                        questionBankListAdapter.canAddToPreview = false;
                    }
                    spQuestionlistFilter.setSelection(1);
                    spQuestionlistSort.setSelection(1);
                    filterBooks(arrListAuthorBooks.get(position - 1).getBookId());
                } else {
                    clearFilters();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spQuestionlistFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 1) {
                    filterQuestions(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spQuestionlistSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 1) {
                    sortQuestions(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        callApiGetQuestionBank();
        callApiGetAuthorBooks();
    }

    private void startSlideAnimation(final View view, int fromX, int toX, int fromY, int toY) {
        TranslateAnimation slideOutAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        slideOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slideOutAnimation.setDuration(500);
        slideOutAnimation.setFillAfter(true);
        view.startAnimation(slideOutAnimation);
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
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    private void callApiGetQuestionBank() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                attribute.setRole(Global.role);

                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETQUESTIONBANK);

            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GETBOOKSFORAUTHOR:
                    onResponseGetBooksForAuthor(object, error);
                    break;

                case WebConstants.GETQUESTIONBANK:
                    onResponseGetQuestionBank(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
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
                    authorBooks.add(getString(R.string.strbookname));
//                    authorBooks.add(getString(R.string.strall));
                    for (AuthorBook authorbook : arrListAuthorBooks) {
                        authorBooks.add(authorbook.getBookName());

                    }
                    Adapters.setUpSpinner(getActivity(), spQuestionlistAuthorBooks, authorBooks, Adapters.ADAPTER_SMALL);

                    if (getArguments() != null) {
                        Debug.e(TAG, "THE BOOK NAME IS" + getArguments().getString(ExamsAdapter.ARG_EXAM_BOOK_NAME));
                        spQuestionlistAuthorBooks.setSelection(authorBooks.indexOf(getArguments().getString(ExamsAdapter.ARG_EXAM_BOOK_NAME)));
                    }


                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetBooksForAuthor api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetBooksForAuthor Exception : " + e.toString());
        }
    }

    private void onResponseGetQuestionBank(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListQuestions.addAll(responseHandler.getQuestionBank());
                    setQuestionData(arrListQuestions);

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetCourses api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetCourses Exception : " + e.toString());
        }
    }


    private void setQuestionData(ArrayList<Questions> questions) {
        questionBankListAdapter.addAll(questions);
        Debug.e(TAG, "The no of questions are::" + questions.size());
//        filterBooks(getArguments().getString(ExamsAdapter.ARG_EXAM_BOOK_ID));
        if (getArguments() != null) {
            if (getArguments().containsKey(GetObjectiveAssignmentQuestionsFragment.ARG_ARR_LIST_QUESTIONS)) {
                ArrayList<Questions> arrListExamQuestions = getArguments().
                        getParcelableArrayList(GetObjectiveAssignmentQuestionsFragment.ARG_ARR_LIST_QUESTIONS);
                Debug.e(TAG, "THE NO OF QUESTION IS QUESTION BANK ARE::" + questions.size());
                Debug.e(TAG, "THE NO OF QUESTIONS OF EXAM ARE::" + arrListExamQuestions.size());
                updateQuestionStatusAfterSetDataOfExam(arrListExamQuestions);
            }
        }


    }

    @Override
    public void onClick(View v) {
        if (v == tvQuestionlistAddPreview) {

            if (getFragment().getListOfPreviewQuestionsToAdd().size() > 0) {

                Debug.e(TAG, "The size of preview questions is" + getFragment().getListOfPreviewQuestion().size());
                getFragment().addQuestionsToPreviewFragment();
                getFragment().getListOfPreviewQuestionsToAdd().clear();
            } else {
//                Utils.showToast(getResources().getString(R.string.msg_select_question_to_add_to_preview), getActivity());
                Utility.alert(getActivity(), null, getResources().getString(R.string.msg_select_question_to_add_to_preview));
            }
        } else if (v == tvQuestionlistAddNewQuestion) {
            getFragment().setDataOnFragmentFlip(null, false, true);
        }
    }

    public void updateViewAfterDeleteInPreviewQuestion(String questionId) {
        for (Questions question : arrListQuestions) {
            if (questionId.equals(question.getQuestionId())) {
                arrListQuestions.get(arrListQuestions.indexOf(question)).setIsQuestionAddedInPreview(false);
                break;
            }
        }
        questionBankListAdapter.addAll(arrListQuestions);
        questionBankListAdapter.notifyDataSetChanged();

        filterResultsAfterAddEditDelete();
    }

    /*this method is for edit question data after it successfully edited */
    public void updateQuestionDataAfterEditQuestion(Questions prevQuestionData, Questions updatedQuestionData, Boolean isChecked) {
        for (Questions questions : arrListQuestions) {
            if (questions.getQuestionId().equals(prevQuestionData.getQuestionId())) {
                if (isChecked) {
                    updatedQuestionData.setIsQuestionAddedInPreview(true);
                }
                arrListQuestions.set(arrListQuestions.indexOf(questions), updatedQuestionData);
                break;
            }
        }
        questionBankListAdapter.addAll(arrListQuestions);
        questionBankListAdapter.notifyDataSetChanged();

        filterResultsAfterAddEditDelete();
    }

    public void addQuestionDataAfterAddQuestion(Questions question) {
        question.setIsQuestionAddedInPreview(true);
//        arrListQuestions.add(0, question);
        arrListQuestions.add(question);
        questionBankListAdapter.addAll(arrListQuestions);
        questionBankListAdapter.notifyDataSetChanged();

        filterResultsAfterAddEditDelete();
    }


    /*this method is to retain the filter status after question added,deleted and updated*/
    private void filterResultsAfterAddEditDelete() {


        /*this is to retain the status of filter after add edit*/
        filterBooks(spQuestionlistAuthorBooks.getSelectedItemPosition() > 0 ?
                arrListAuthorBooks.get(spQuestionlistAuthorBooks.getSelectedItemPosition() - 1).getBookId() : "0");
        filterQuestions(spQuestionlistFilter.getSelectedItemPosition());
        sortQuestions(spQuestionlistSort.getSelectedItemPosition());

    }

    private AddQuestionContainerFragment getFragment() {
        return (AddQuestionContainerFragment) mFragment;
    }

    public void updateQuestionStatusAfterSetDataOfExam(ArrayList<Questions> arrListExamQuestions) {
        try {
            getFragment().setListOfExamQuestions(arrListExamQuestions);
            for (int i = 0; i < arrListExamQuestions.size(); i++) {
                Debug.e(TAG, "THE EXAM QUESTION ID IS::::" + arrListExamQuestions.get(i).getQuestionId());
                for (int j = 0; j < arrListQuestions.size(); j++) {
                    Debug.e(TAG, "THE QUESTION LIST QUESTION ID IS====" + arrListQuestions.get(j).getQuestionId());
                    if (arrListExamQuestions.get(i).getQuestionId().equals(arrListQuestions.get(j).getQuestionId())) {
                        Debug.e(TAG, "The position of exam question in question bank list is" + j);
                        arrListQuestions.get(j).setIsQuestionAddedInPreview(true);
                        break;
                    }
                }
            }
            questionBankListAdapter.addAll(arrListQuestions);
            questionBankListAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Questions> copylistOfQuestionBank = new ArrayList<Questions>();
    public ArrayList<Questions> filterlistOfQuestionBank = new ArrayList<Questions>();

    private void filterBooks(String bookId) {

        copylistOfQuestionBank.clear();
        if (arrListQuestions.size() > 0) {
            for (Questions wp : arrListQuestions) {
                if (wp.getBookId().equalsIgnoreCase(bookId)) {
                    copylistOfQuestionBank.add(wp);
                }
            }
            questionBankListAdapter.addAll(copylistOfQuestionBank);

            if (!(copylistOfQuestionBank.size() > 0)) {
//                Utils.showToast(getString(R.string.msg_validation_no_exams_filter), getActivity());
                Utility.alert(getActivity(), null, getString(R.string.msg_validation_no_exams_filter));
            }
        }
    }

    private void clearFilters() {
        questionBankListAdapter.addAll(arrListQuestions);
    }


    private void filterQuestions(int filterType) {
        try {
            switch (filterType) {
                case 1:
//                    questionBankListAdapter.addAll(arrListQuestions);
//                    questionBankListAdapter.notifyDataSetChanged();
                    questionBankListAdapter.addAll(copylistOfQuestionBank);
                    questionBankListAdapter.notifyDataSetChanged();
                    break;
                case 2:
//                    copylistOfQuestionBank.clear();
//                    if (arrListQuestions.size() > 0) {
//                        for (Questions wp : arrListQuestions) {
//                            if (wp.getQuestionFormat().equalsIgnoreCase(getString(R.string.strquestionformatdescriptive))) {
//                                copylistOfQuestionBank.add(wp);
//                            }
//                        }
//                        questionBankListAdapter.addAll(copylistOfQuestionBank);
//                    }
                    filterlistOfQuestionBank.clear();
                    if (copylistOfQuestionBank.size() > 0) {
                        for (Questions wp : copylistOfQuestionBank) {
                            if (wp.getQuestionFormat().equalsIgnoreCase(getString(R.string.strquestionformatdescriptive))) {
                                filterlistOfQuestionBank.add(wp);
                            }
                        }
                        questionBankListAdapter.addAll(filterlistOfQuestionBank);
                    }

                    if (!(filterlistOfQuestionBank.size() > 0)) {
//                        Utils.showToast(getString(R.string.msg_validation_no_questions_filter), getActivity());
                        Utility.alert(getActivity(), null, getString(R.string.msg_validation_no_questions_filter));
                    }
                    break;
                case 3:
                    filterlistOfQuestionBank.clear();
                    if (copylistOfQuestionBank.size() > 0) {
                        for (Questions wp : copylistOfQuestionBank) {
                            if (wp.getQuestionFormat().equalsIgnoreCase(getString(R.string.strquestionformatmcq))) {
                                filterlistOfQuestionBank.add(wp);
                            }
                        }
                        questionBankListAdapter.addAll(filterlistOfQuestionBank);
                    }

                    if (!(filterlistOfQuestionBank.size() > 0)) {
//                        Utils.showToast(getString(R.string.msg_validation_no_questions_filter), getActivity());
                        Utility.alert(getActivity(), null, getString(R.string.msg_validation_no_questions_filter));
                    }
                    break;
                case 4:
                    break;
            }

            /*whenever you change the data filteration get it in ascending oredr*/
            spQuestionlistSort.setSelection(1);
        } catch (Exception e) {
            Debug.e(TAG, "onFilterQuestions Exception : " + e.toString());
        }
    }


    /**
     * Perform sorting always on latestlistOfQuestionBank to sort the latest list after filter.
     *
     * @param typeOfSort=SORT_UP or SORT_DOWN
     */
    public static final int SORT_UP = 1, SORT_DOWN = 2;

    private void sortQuestions(int typeOfSort) {

        if (spQuestionlistFilter.getSelectedItemPosition() == 2 || spQuestionlistFilter.getSelectedItemPosition() == 3) {
            if (typeOfSort == SORT_UP) {
                // Debug.e("Sort_up====================", "sort up");
                Collections.sort(filterlistOfQuestionBank);
            } else {
//                Debug.e("Sort_down====================", "sort down");
                Collections.sort(filterlistOfQuestionBank, Collections.reverseOrder());
            }
            questionBankListAdapter.addAll(filterlistOfQuestionBank);
            questionBankListAdapter.notifyDataSetChanged();
        } else if (spQuestionlistFilter.getSelectedItemPosition() == 1) {
            if (copylistOfQuestionBank.size() > 0) {
                if (typeOfSort == SORT_UP) {
                    // Debug.e("Sort_all_questions_up====================", "sort up");
                    Collections.sort(copylistOfQuestionBank);

                } else {
//                Debug.e("Sort_all_questions down====================", "sort down");
                    Collections.sort(copylistOfQuestionBank, Collections.reverseOrder());
                }
                questionBankListAdapter.addAll(copylistOfQuestionBank);
                questionBankListAdapter.notifyDataSetChanged();
            }
        }
//        //handling for sorting questions based on subjective and objective
//        if (filterlistOfQuestionBank.size() > 0) {
//            if (typeOfSort == SORT_UP) {
//                // Debug.e("Sort_up====================", "sort up");
//                Collections.sort(filterlistOfQuestionBank);
//            } else {
////                Debug.e("Sort_down====================", "sort down");
//                Collections.sort(filterlistOfQuestionBank, Collections.reverseOrder());
//            }
//            questionBankListAdapter.addAll(filterlistOfQuestionBank);
//            questionBankListAdapter.notifyDataSetChanged();
//        }
//        //sorting for all question based on subjects
//        else {
//            if (copylistOfQuestionBank.size() > 0) {
//                if (typeOfSort == SORT_UP) {
//                    // Debug.e("Sort_all_questions_up====================", "sort up");
//                    Collections.sort(copylistOfQuestionBank);
//
//                } else {
////                Debug.e("Sort_all_questions down====================", "sort down");
//                    Collections.sort(copylistOfQuestionBank, Collections.reverseOrder());
//                }
//                questionBankListAdapter.addAll(copylistOfQuestionBank);
//                questionBankListAdapter.notifyDataSetChanged();
//            }
//        }

    }


}
