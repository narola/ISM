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
import android.widget.Toast;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.Adapters;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.adapter.QuestionBankListAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.AuthorBook;
import com.ism.author.ws.model.Courses;
import com.ism.author.ws.model.Questions;

import java.util.ArrayList;
import java.util.Arrays;
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
    //    private List<Topics> arrListTopic;
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

        arrListSort = new ArrayList<String>();
        arrListSort = Arrays.asList(getResources().getStringArray(R.array.questionsSorting));
        Adapters.setUpSpinner(getActivity(), spQuestionlistSort, arrListSort, Adapters.ADAPTER_SMALL);

        arrListDefalt = new ArrayList<String>();
        arrListDefalt.add(getString(R.string.strtopic));
        Adapters.setUpSpinner(getActivity(), spQuestionlistSort, arrListDefalt, Adapters.ADAPTER_SMALL);

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


        spQuestionlistAuthorBooks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//
                if (arrListAuthorBooks != null && position > 0) {
                    /*this is to check that question are of this exam*/
                    if (arrListAuthorBooks.get(position - 1).getBookId().equals(getArguments().getString(ExamsAdapter.ARG_EXAM_BOOK_ID))) {
                        questionBankListAdapter.canAddToPreview = true;
                    } else {
                        questionBankListAdapter.canAddToPreview = false;
                    }

                    if (arrListQuestions.size() > 0 && arrListAuthorBooks != null) {
                        filterResults(Integer.parseInt(arrListAuthorBooks.get(position - 1).getBookId()));
                    }


                } else {
                    Adapters.setUpSpinner(getActivity(), spQuestionlistSort, arrListDefalt, Adapters.ADAPTER_SMALL);
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

//                if (arrListQuestions.size() > 0 && arrListTopic != null && position > 0) {
//
//                    if (position == 1) {
//                        filterResults(spQuestionlistAuthorBooks.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListAuthorBooks.
//                                get(spQuestionlistAuthorBooks.getSelectedItemPosition() - 1).getId()) : 0, null);
//                    } else {
//                        filterResults(spQuestionlistAuthorBooks.getSelectedItemPosition() > 0 ? Integer.parseInt(arrListAuthorBooks.
//                                get(spQuestionlistAuthorBooks.getSelectedItemPosition() - 1).getId()) : 0, arrListTopic.get(position - 2).getId());
//                    }
//
//                }
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
                attribute.setUserId("52");
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETBOOKSFORAUTHOR);
            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }

//    private void callApiGetTopics(int subject_id) {
//        if (Utility.isConnected(getActivity())) {
//            try {
//                ((AuthorHostActivity) getActivity()).showProgress();
//                Attribute attribute = new Attribute();
//                attribute.setSubjectId(String.valueOf(subject_id));
//                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
//                        .execute(WebConstants.GETTOPICS);
//            } catch (Exception e) {
//                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
//            }
//        } else {
//            Utility.toastOffline(getActivity());
//        }
//    }


    private void callApiGetQuestionBank() {
        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId("370");
                attribute.setRole("3");

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


//    private void onResponseGetTopics(Object object, Exception error) {
//        try {
//            ((AuthorHostActivity) getActivity()).hideProgress();
//            if (object != null) {
//                ResponseHandler responseHandler = (ResponseHandler) object;
//                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
//                    arrListTopic = new ArrayList<Topics>();
//                    arrListTopic.addAll(responseHandler.getTopics());
//                    List<String> topics = new ArrayList<String>();
//                    topics.add(getString(R.string.select));
//                    topics.add(getString(R.string.strall));
//                    for (Topics topic : arrListTopic) {
//                        topics.add(topic.getTopicName());
//
//                    }
//                    Adapters.setUpSpinner(getActivity(), spQuestionlistSort, topics, Adapters.ADAPTER_SMALL);
//                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
//                    Adapters.setUpSpinner(getActivity(), spQuestionlistSort, arrListDefalt, Adapters.ADAPTER_SMALL);
//                    Utils.showToast(responseHandler.getMessage(), getActivity());
//                }
//            } else if (error != null) {
//                Debug.e(TAG, "onResponseGetTopics api Exception : " + error.toString());
//            }
//        } catch (Exception e) {
//            Debug.e(TAG, "onResponseGetTopics Exception : " + e.toString());
//        }
//    }


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
        filterResults(Integer.valueOf(getArguments().getString(ExamsAdapter.ARG_EXAM_BOOK_ID)));
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
                Utils.showToast(getResources().getString(R.string.msg_select_question_to_add_to_preview), getActivity());
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

    public void updateQuestionDataAfterEditQuestion(Questions prevQuestionData, Questions updatedQuestionData) {
        for (Questions questions : arrListQuestions) {
            if (questions.getQuestionId().equals(prevQuestionData.getQuestionId())) {
                updatedQuestionData.setIsQuestionAddedInPreview(true);
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
        arrListQuestions.add(0, question);
        questionBankListAdapter.addAll(arrListQuestions);
        questionBankListAdapter.notifyDataSetChanged();

        filterResultsAfterAddEditDelete();
    }

    private void filterResultsAfterAddEditDelete() {

        /*this is to retain the status of filter after add edit*/

        etSearchQuestions.setText("");
        filterResults(spQuestionlistAuthorBooks.getSelectedItemPosition() > 0 ?
                Integer.parseInt(arrListAuthorBooks.get(spQuestionlistAuthorBooks.getSelectedItemPosition() - 1).getBookId()) : 0);

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

    private void filterResults(int bookId) {

        copylistOfQuestionBank.clear();

        if (arrListQuestions.size() > 0) {
            for (Questions wp : arrListQuestions) {
                int count = 0;
                //filter based on subject id and topic id based on subjects
//                if (topicId != null && !topicId.equalsIgnoreCase("")) {
                if (wp.getSubjectId().equalsIgnoreCase(Integer.toString(bookId))) {
                    Debug.e(TAG + "filter success", "" + count++);
                    copylistOfQuestionBank.add(wp);
                }
//                }
                //filter based on only subject id (after args passed from assignment exam subject id
                else {
                    if (wp.getSubjectId().equalsIgnoreCase(Integer.toString(bookId))) {
                        Debug.e(TAG + "filter success", "" + count++);
                        copylistOfQuestionBank.add(wp);
                    }
                }
            }
            if (copylistOfQuestionBank.size() > 0) {
                questionBankListAdapter.addAll(copylistOfQuestionBank);
            } else {
                questionBankListAdapter.addAll(copylistOfQuestionBank);
                Toast.makeText(getActivity(), "No Questions Found to Filter", Toast.LENGTH_SHORT).show();
            }


        }
    }


    private void filterQuestions(int filterType) {

        if (filterType == 1) {
            questionBankListAdapter.addAll(arrListQuestions);
            questionBankListAdapter.notifyDataSetChanged();

        } else if (filterType == 2) {
            copylistOfQuestionBank.clear();
            if (arrListQuestions.size() > 0) {
                for (Questions wp : arrListQuestions) {
                    if (wp.getQuestionFormat().equalsIgnoreCase(getString(R.string.strquestionformatdescriptive))) {
                        copylistOfQuestionBank.add(wp);
                    }
                }
                questionBankListAdapter.addAll(copylistOfQuestionBank);
            }

        } else if (filterType == 3) {
            copylistOfQuestionBank.clear();
            if (arrListQuestions.size() > 0) {
                for (Questions wp : arrListQuestions) {
                    if (wp.getQuestionFormat().equalsIgnoreCase(getString(R.string.strquestionformatmcq))) {
                        copylistOfQuestionBank.add(wp);
                    }
                }
                questionBankListAdapter.addAll(copylistOfQuestionBank);
            }
        } else if (filterType == 4) {

        }
    }

    private void sortQuestions(int sortType) {
        if (sortType == 1) {


        } else if (sortType == 2) {

        }

    }

    private void filterFavouriteQuestiuons() {

    }


}
