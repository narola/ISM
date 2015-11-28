package com.ism.author.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.InputValidator;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.Adapters;
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.autocomplete.ContactsCompletionView;
import com.ism.author.autocomplete.FilteredArrayAdapter;
import com.ism.author.autocomplete.TokenCompleteTextView;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.model.HashTagsModel;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.AnswerChoices;
import com.ism.author.ws.model.Answers;
import com.ism.author.ws.model.HashTags;
import com.ism.author.ws.model.Questions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by c166 on 31/10/15.
 */
public class QuestionAddEditFragment extends Fragment implements TokenCompleteTextView.TokenListener, View.OnClickListener, WebserviceWrapper.WebserviceResponse {

    private static final String TAG = QuestionAddEditFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;

    public QuestionAddEditFragment() {
    }

    @SuppressLint("ValidFragment")
    public QuestionAddEditFragment(Fragment fragment, Bundle bundleArguments) {
        this.mFragment = fragment;
        this.setArguments(bundleArguments);
    }

    /*these is for the tag add functionality.*/
    private ContactsCompletionView tagsView;
    private HashTagsModel[] tags;
    private ArrayAdapter<HashTagsModel> tagsAdapter;

    /*these sre for the xml views*/
    private TextView tvAddquestionHeader, tvAddquestionTitle, tvAddquestionType, tvAddquestionCategory, tvEvaluationNote1, tvEvaluationNote2,
            tvAddquestionSave, tvAddquestionSaveAddmore, tvAddquestionGotoquestionbank;
    private ImageView imgEditQuestion, imgCopyQuestion, imgDeleteQuestion, imgSelectImage, imgPlay;
    private EditText etAddquestionTitle, etAddquestionAnswer, etEvaluationNote1, etEvaluationNote2;
    private Spinner spAddquestionType;
    private CheckBox chkAddquestionPreview;
    List<String> arrListQuestionType;
    private LinearLayout llAddMcqanswer;
    private RelativeLayout rlSelectImage;

    MyTypeFace myTypeFace;
    private InputValidator inputValidator;
    private Boolean isAddMore = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question_addedit, container, false);
//        Utils.showToast("THE QUESTION ADD EDIT FRAGMENT CALLED", getActivity());
        initGlobal();
        return view;
    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());
        inputValidator = new InputValidator(getActivity());
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

        tvAddquestionHeader = (TextView) view.findViewById(R.id.tv_addquestion_header);
        tvAddquestionTitle = (TextView) view.findViewById(R.id.tv_addquestion_title);
        tvAddquestionType = (TextView) view.findViewById(R.id.tv_addquestion_type);
        tvAddquestionCategory = (TextView) view.findViewById(R.id.tv_addquestion_category);
        tvEvaluationNote1 = (TextView) view.findViewById(R.id.tv_evaluation_note1);
        tvEvaluationNote2 = (TextView) view.findViewById(R.id.tv_evaluation_note2);
        tvAddquestionSave = (TextView) view.findViewById(R.id.tv_addquestion_save);
        tvAddquestionSaveAddmore = (TextView) view.findViewById(R.id.tv_addquestion_save_addmore);
        tvAddquestionGotoquestionbank = (TextView) view.findViewById(R.id.tv_addquestion_gotoquestionbank);
        tvAddquestionSave.setOnClickListener(this);
        tvAddquestionSaveAddmore.setOnClickListener(this);
        tvAddquestionGotoquestionbank.setOnClickListener(this);


        tvAddquestionHeader.setTypeface(myTypeFace.getRalewayRegular());
        tvAddquestionTitle.setTypeface(myTypeFace.getRalewayBold());
        tvAddquestionType.setTypeface(myTypeFace.getRalewayBold());
        tvAddquestionCategory.setTypeface(myTypeFace.getRalewayBold());
        tvEvaluationNote1.setTypeface(myTypeFace.getRalewayRegular());
        tvEvaluationNote2.setTypeface(myTypeFace.getRalewayRegular());
        tvAddquestionSave.setTypeface(myTypeFace.getRalewayRegular());
        tvAddquestionSaveAddmore.setTypeface(myTypeFace.getRalewayRegular());
        tvAddquestionGotoquestionbank.setTypeface(myTypeFace.getRalewayRegular());


        imgEditQuestion = (ImageView) view.findViewById(R.id.img_edit_question);
        imgCopyQuestion = (ImageView) view.findViewById(R.id.img_copy_question);
        imgDeleteQuestion = (ImageView) view.findViewById(R.id.img_delete_question);
        imgSelectImage = (ImageView) view.findViewById(R.id.img_select_image);
        imgPlay = (ImageView) view.findViewById(R.id.img_play);
        imgSelectImage.setOnClickListener(this);


        etAddquestionTitle = (EditText) view.findViewById(R.id.et_addquestion_title);
        etAddquestionAnswer = (EditText) view.findViewById(R.id.et_addquestion_answer);
        etEvaluationNote1 = (EditText) view.findViewById(R.id.et_evaluation_note1);
        etEvaluationNote2 = (EditText) view.findViewById(R.id.et_evaluation_note2);


        etAddquestionTitle.setTypeface(myTypeFace.getRalewayRegular());
        etAddquestionAnswer.setTypeface(myTypeFace.getRalewayRegular());
        etEvaluationNote1.setTypeface(myTypeFace.getRalewayRegular());
        etEvaluationNote2.setTypeface(myTypeFace.getRalewayRegular());


        spAddquestionType = (Spinner) view.findViewById(R.id.sp_addquestion_type);

        llAddMcqanswer = (LinearLayout) view.findViewById(R.id.ll_add_mcqanswer);
        rlSelectImage = (RelativeLayout) view.findViewById(R.id.rl_select_image);
        rlSelectImage.setOnClickListener(this);

        chkAddquestionPreview = (CheckBox) view.findViewById(R.id.chk_addquestion_preview);
        chkAddquestionPreview.setTypeface(myTypeFace.getRalewayRegular());

        arrListQuestionType = new ArrayList<String>();
        arrListQuestionType.add(getString(R.string.strquestiontype));
        arrListQuestionType = Arrays.asList(getResources().getStringArray(R.array.question_type));
        Adapters.setUpSpinner(getActivity(), spAddquestionType, arrListQuestionType, Adapters.ADAPTER_SMALL);

        spAddquestionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 1) {
                    llAddMcqanswer.setVisibility(View.GONE);
                    etAddquestionAnswer.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    llAddMcqanswer.setVisibility(View.GONE);
                    etAddquestionAnswer.setVisibility(View.VISIBLE);
                } else if (position == 3) {
                    llAddMcqanswer.setVisibility(View.VISIBLE);
                    etAddquestionAnswer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        imgSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        callAPiGetAllHashTag();
    }


    //intiGlobalEnds

    Uri selectedUri = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            selectedUri = data.getData();
            String[] columns = {MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.MIME_TYPE};

            Cursor cursor = getActivity().getContentResolver().query(selectedUri, columns, null, null, null);
            cursor.moveToFirst();

            int pathColumnIndex = cursor.getColumnIndex(columns[0]);
            int mimeTypeColumnIndex = cursor.getColumnIndex(columns[1]);

            String contentPath = cursor.getString(pathColumnIndex);
            String mimeType = cursor.getString(mimeTypeColumnIndex);
            cursor.close();

            if (mimeType.startsWith("image")) {

                imgSelectImage.setImageURI(selectedUri);
                imgPlay.setVisibility(View.GONE);

            } else if (mimeType.startsWith("video")) {

                MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
                mMediaMetadataRetriever.setDataSource(getActivity(), selectedUri);
                Bitmap bitmap = mMediaMetadataRetriever.getFrameAtTime(1 * 1000);
                imgSelectImage.setImageBitmap(bitmap);
                imgPlay.setVisibility(View.VISIBLE);

            }

        }
    }


    private void updateTokenConfirmation() {
        StringBuilder sb = new StringBuilder("Current tokens:\n");
        for (Object token : tagsView.getObjects()) {
            List<HashTagsModel> list = tagsView.getObjects();
            sb.append(token.toString());
            sb.append("\n");
        }
    }

    @Override
    public void onTokenAdded(Object token) {
        updateTokenConfirmation();
    }

    @Override
    public void onTokenRemoved(Object token) {
        updateTokenConfirmation();
    }


    public void setViewForAddEditQuestion() {
        if (getFragment().getIsSetQuestionData()) {
            setQuestionData(getFragment().getQuestionData());
        } else {
            clearViewsData();
        }
    }

    private void clearViewsData() {
        etAddquestionTitle.setText("");
        spAddquestionType.setSelection(1);
        etAddquestionAnswer.setText("");
        llAddMcqanswer.removeAllViews();

        if (tagsView.getObjects().size() > 0) {
            tagsView.clear();
        }
        etEvaluationNote1.setText("");
        etEvaluationNote2.setText("");
        imgSelectImage.setImageDrawable(null);
        imgSelectImage.setImageBitmap(null);
        arrListAnswerChioces.clear();
        arrListTags.clear();
        for (int i = 0; i <= 1; i++) {
            llAddMcqanswer.addView(getMcqAnswerView(i));
        }

    }

    /*this method is for inflating views for addquestion*/
    private View getMcqAnswerView(final int position) {
        View v = null;
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        v = layoutInflater.inflate(R.layout.row_mcq_answer, null, false);

        final ImageView imgAnsRadio = (ImageView) v.findViewById(R.id.img_ans_radio);
        final ImageView imgAddMcqRow = (ImageView) v.findViewById(R.id.img_add_mcq_row);
        final ImageView imgRemoveMcqRow = (ImageView) v.findViewById(R.id.img_remove_mcq_row);
        if (position > 0) {
            imgAddMcqRow.setVisibility(View.VISIBLE);
        } else {
            imgAddMcqRow.setVisibility(View.GONE);
        }
        imgAnsRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAnswers();
                imgAnsRadio.setActivated(!imgAnsRadio.isActivated());

            }
        });
        imgRemoveMcqRow.setTag(position);
        imgAddMcqRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRow((Integer) imgRemoveMcqRow.getTag(), imgAddMcqRow, imgRemoveMcqRow);

            }
        });
        imgRemoveMcqRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeRow((Integer) imgRemoveMcqRow.getTag());

            }
        });
        return v;
    }

    /*this method is for inflating views for edit and copy question*/
    private View setMCQ(int position, String text, Boolean isActivated, int listSize) {
        View v = null;
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        v = layoutInflater.inflate(R.layout.row_mcq_answer, null, false);

        final ImageView imgAnsRadio = (ImageView) v.findViewById(R.id.img_ans_radio);
        EditText etAddMcqAnswer = (EditText) v.findViewById(R.id.et_add_mcq_answer);
        final ImageView imgAddMcqRow = (ImageView) v.findViewById(R.id.img_add_mcq_row);
        final ImageView imgRemoveMcqRow = (ImageView) v.findViewById(R.id.img_remove_mcq_row);
        etAddMcqAnswer.setText(Utils.formatHtml(text));
        imgAnsRadio.setActivated(isActivated);

        if (position < 1) {
            imgAddMcqRow.setVisibility(View.GONE);
            imgRemoveMcqRow.setVisibility(View.GONE);
        } else if (position == listSize - 1) {
            imgAddMcqRow.setVisibility(View.VISIBLE);
            imgRemoveMcqRow.setVisibility(View.GONE);
        } else {
            imgAddMcqRow.setVisibility(View.GONE);
            imgRemoveMcqRow.setVisibility(View.VISIBLE);
        }
        imgRemoveMcqRow.setTag(position);

        imgAnsRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectAnswers();
                imgAnsRadio.setActivated(!imgAnsRadio.isActivated());

            }
        });
        imgAddMcqRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRow((Integer) imgRemoveMcqRow.getTag(), imgAddMcqRow, imgRemoveMcqRow);
            }
        });
        imgRemoveMcqRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeRow((Integer) imgRemoveMcqRow.getTag());

            }
        });
        return v;
    }

    private void unselectAnswers() {
        for (int i = 0; i < llAddMcqanswer.getChildCount(); i++) {
            ImageView imgRadio = (ImageView) llAddMcqanswer.getChildAt(i).findViewById(R.id.img_ans_radio);
            imgRadio.setActivated(false);
        }
    }

    /*this is to add row in inflater view*/
    private void addRow(int viewPositionToAdd, ImageView imgAddMcqRow, ImageView imgRemoveMcqRow) {
        if (llAddMcqanswer.getChildCount() < 6) {
            imgAddMcqRow.setVisibility(View.GONE);
            imgRemoveMcqRow.setVisibility(View.VISIBLE);
            llAddMcqanswer.addView(getMcqAnswerView(viewPositionToAdd + 1));
        } else {
            Utils.showToast(getActivity().getResources().getString(R.string.msg_mcq_ans_limit), getActivity());
        }
    }

    /*this is to remove row in inflater view*/
    private void removeRow(int viewPositionToRemove) {
        llAddMcqanswer.removeViewAt(viewPositionToRemove);
        refreshViewPositions();

    }

    /*this is to refresh view positions after remove row in inflater view*/
    private void refreshViewPositions() {
        for (int i = 0; i < llAddMcqanswer.getChildCount(); i++) {
            ImageView imgRemoveMcqRow = (ImageView) llAddMcqanswer.getChildAt(i).findViewById(R.id.img_remove_mcq_row);
            imgRemoveMcqRow.setTag(i);
        }

    }


    /*these is for set question questionData for copy and edit question.*/
    private ImageLoader imageLoader;


    public void setQuestionData(Questions questions) {
        clearViewsData();
        try {
            if (questions.getQuestionText() != null) {
                etAddquestionTitle.setText(Utils.formatHtml(questions.getQuestionText()));
            }
            imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png",
                    imgSelectImage, ISMAuthor.options);
            setSpinnerData(questions.getQuestionFormat());
            setMcqAnswers(questions);

            if (questions.getEvaluationNotes() != null) {
                etEvaluationNote1.setText(questions.getEvaluationNotes());
                etEvaluationNote2.setText(questions.getEvaluationNotes());
            }

            if (questions.getTags() != null) {
                if (questions.getTags().size() > 0) {
                    for (int i = 0; i < questions.getTags().size(); i++) {
                        tagsView.addObject(new HashTagsModel(questions.getTags().get(i).getTagName(), questions.getTags().get(i).getTagId()));
                    }
                }
            }

        } catch (Exception e) {
            Debug.e(TAG, "isSetQuestionData Exception : " + e.toString());
        }
    }


    private void setSpinnerData(String questionType) {
        if (questionType.equalsIgnoreCase("MCQ")) {
            spAddquestionType.setSelection(3);
//            arrListAnswers.clear();
            llAddMcqanswer.removeAllViews();
            llAddMcqanswer.setVisibility(View.VISIBLE);
            etAddquestionAnswer.setVisibility(View.GONE);
        } else if (questionType.equalsIgnoreCase("descriptive")) {
            spAddquestionType.setSelection(1);
            llAddMcqanswer.setVisibility(View.GONE);
            etAddquestionAnswer.setVisibility(View.VISIBLE);
        } else if (questionType.equalsIgnoreCase("'Fill ups'")) {
            spAddquestionType.setSelection(2);
            llAddMcqanswer.setVisibility(View.GONE);
            etAddquestionAnswer.setVisibility(View.VISIBLE);
        }
    }


    private void setMcqAnswers(Questions questions) {

        Debug.e(TAG, "The size of answer list is" + questions.getAnswers().size());

        for (int i = 0; i < questions.getAnswers().size(); i++) {
            llAddMcqanswer.addView(setMCQ(i, questions.getAnswers().get(i).getChoiceText(),
                    questions.getAnswers().get(i).getIsRight().equals("1") ? true : false, questions.getAnswers().size()));
        }
    }


    private Boolean isInputsValid() {
//        return inputValidator.validateStringPresence(etAddquestionTitle) & inputValidator.validateStringPresence(etEvaluationNote1)
//                & inputValidator.validateStringPresence(tagsView)
//                & inputValidator.validateStringPresence(etEvaluationNote2) && checkSpinnerDataValidation();
        return inputValidator.validateStringPresence(etAddquestionTitle) && checkSpinnerDataValidation();

    }


    private Boolean checkSpinnerDataValidation() {

        int spPosition = spAddquestionType.getSelectedItemPosition();
        Boolean isValidate = false;
        switch (spPosition) {
            case 1:
                isValidate = inputValidator.validateStringPresence(etAddquestionAnswer);
                break;
            case 2:
                isValidate = inputValidator.validateStringPresence(etAddquestionAnswer);
                break;
            case 3:
                isValidate = true;
                for (int i = 0; i < llAddMcqanswer.getChildCount(); i++) {
                    EditText etMcqAnswer = (EditText) llAddMcqanswer.getChildAt(i).findViewById(R.id.et_add_mcq_answer);
                    isValidate = isValidate & inputValidator.validateStringPresence(etMcqAnswer);
                }
                isValidate = isValidate & checkMcqAnswerSelection();
                break;
        }
        return isValidate;
    }

    private Boolean checkMcqAnswerSelection() {
        Boolean isAnswerSelect = false;
        for (int i = 0; i < llAddMcqanswer.getChildCount(); i++) {
            ImageView etMcqAnswer = (ImageView) llAddMcqanswer.getChildAt(i).findViewById(R.id.img_ans_radio);
            if (etMcqAnswer.isActivated()) {
                isAnswerSelect = true;
                break;
            }
        }
        if (!isAnswerSelect) {
            Utility.alert(getActivity(), null, getActivity().getString(R.string.msg_validation_select_answer));
        }
        return isAnswerSelect;

    }


    @Override
    public void onClick(View v) {
        if (v == tvAddquestionSave) {

            isAddMore = false;
            if (getFragment().getIsSetQuestionData() && !getFragment().getIsCopy()) {
                Debug.e(TAG, "QUESTION EDIT CALLED");
                getFragment().setQuestionDataAfterEditQuestion(getFragment().getQuestionData(),
                        makeQuestionData(getFragment().getQuestionData().getQuestionId()), chkAddquestionPreview.isChecked());
            } else {
                Debug.e(TAG, "QUESTION ADD CALLED");
                if (isInputsValid()) {
                    callApiCreateQuestion();
                }
            }


            /*this is only for testing*/


        } else if (v == tvAddquestionSaveAddmore) {
            isAddMore = true;
            if (getFragment().getIsSetQuestionData() && !getFragment().getIsCopy()) {
                Debug.e(TAG, "QUESTION EDIT CALLED");
                getFragment().setQuestionDataAfterEditQuestion(getFragment().getQuestionData(),
                        makeQuestionData(getFragment().getQuestionData().getQuestionId()), chkAddquestionPreview.isChecked());
                clearViewsData();
            } else {
                Debug.e(TAG, "QUESTION ADD CALLED");
                if (isInputsValid()) {
                    callApiCreateQuestion();
                }
            }
        } else if (v == rlSelectImage) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("*/*");
            startActivityForResult(intent, 1);

        } else if (v == tvAddquestionGotoquestionbank) {
            getFragment().flipCard();

        }

    }


    private void callAPiGetAllHashTag() {
        if (Utility.isConnected(getActivity())) {
            ((AuthorHostActivity) getActivity()).showProgress();
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                new WebserviceWrapper(getActivity(), null, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETALLHASHTAG);
            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    private void callApiSetHashTag(String questionId) {
        if (Utility.isConnected(getActivity())) {
            ((AuthorHostActivity) getActivity()).showProgress();
            try {
                Attribute attribute = new Attribute();

                StringBuilder sb = new StringBuilder();
                List<HashTagsModel> list = tagsView.getObjects();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).getTagName() + ":" + list.get(i).getTagId());
                    if (i < list.size() - 1) {
                        sb.append(",");
                    }

                }

                Utils.showToast("The tokens are::" + sb.toString(), getActivity());
                attribute.setHashtagData(sb.toString());
                attribute.setResourceId("1");
                attribute.setResourceType(AppConstant.RESOURCE_TYPE_QUESTION);

                ((AuthorHostActivity) getActivity()).showProgress();
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.SETHASHTAG);
            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }

    ArrayList<AnswerChoices> arrListAnswerChioces = new ArrayList<AnswerChoices>();

    private void callApiCreateQuestion() {

        if (Utility.isConnected(getActivity())) {
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                Debug.e(TAG, "The user id is::" + WebConstants.TEST_USER_ID);
                Debug.e(TAG, "The question text is::" + etAddquestionTitle.getText().toString());
                Debug.e(TAG, "The subject id is::" + getArguments().getString(CreateExamFragment.ARG_EXAM_SUBJECT_ID));
                Debug.e(TAG, "The question score is::" + getArguments().getString(CreateExamFragment.ARG_EXAM_QUESTION_SCORE));
                Debug.e(TAG, "The question format is::" + getQuestionFormat());
                Debug.e(TAG, "The evaluation notes is::" + etEvaluationNote1.getText().toString());
                Debug.e(TAG, "The solution  is::" + etEvaluationNote2.getText().toString());
                Debug.e(TAG, "The topic id  is::" + getArguments().getString(CreateExamFragment.ARG_EXAM_TOPIC_ID));
                Debug.e(TAG, "The classroom id  is::" + getArguments().getString(CreateExamFragment.ARG_EXAM_CLASSROOM_ID));
                Debug.e(TAG, "The book id  is::" + getArguments().getString(CreateExamFragment.ARG_EXAM_BOOK_ID));

                Attribute attribute = new Attribute();
                attribute.setUserId(WebConstants.TEST_USER_ID);
                attribute.setQuestionText(etAddquestionTitle.getText().toString());
                attribute.setSubjectId(getArguments().getString(CreateExamFragment.ARG_EXAM_SUBJECT_ID));
                attribute.setQuestionScore(getArguments().getString(CreateExamFragment.ARG_EXAM_QUESTION_SCORE));
                attribute.setQuestionFormat(getQuestionFormat());
                attribute.setEvaluationNotes(etEvaluationNote1.getText().toString());
                attribute.setSolution(etEvaluationNote2.getText().toString());
                attribute.setTopicId(getArguments().getString(CreateExamFragment.ARG_EXAM_TOPIC_ID));
                attribute.setClassroomId(getArguments().getString(CreateExamFragment.ARG_EXAM_CLASSROOM_ID));
                attribute.setBookId(getArguments().getString(CreateExamFragment.ARG_EXAM_BOOK_ID));

//                attribute.setTags(arrListTags);

                if (getQuestionFormat().equalsIgnoreCase("mcq")) {
                    arrListAnswerChioces.clear();
                    for (int i = 0; i < llAddMcqanswer.getChildCount(); i++) {
                        View v = llAddMcqanswer.getChildAt(i);
                        AnswerChoices answerChoices = new AnswerChoices();
                        answerChoices.setChoiceText(((EditText) v.findViewById(R.id.et_add_mcq_answer)).getText().toString());
                        answerChoices.setIsRight(getIsSelected((ImageView) v.findViewById(R.id.img_ans_radio)));
                        arrListAnswerChioces.add(answerChoices);
                    }
                    attribute.setAnswerChoices(arrListAnswerChioces);
                }

                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.CREATEQUESTION);


            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }


    private String getQuestionFormat() {

        String questionFormat = "";
        int spPosition = spAddquestionType.getSelectedItemPosition();
        switch (spPosition) {
            case 1:
                questionFormat = "text";
                break;
            case 2:
                questionFormat = "text";
                break;
            case 3:
                questionFormat = "MCQ";
                break;
        }

        return questionFormat;
    }

    private String getIsSelected(ImageView imageView) {

        if (imageView.isActivated()) {
            return "1";
        } else {
            return "0";
        }

    }

    private AddQuestionContainerFragment getFragment() {
        return (AddQuestionContainerFragment) mFragment;
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.CREATEQUESTION:
                    onReponseCreateQuestion(object, error);
                    break;

                case WebConstants.GETALLHASHTAG:
                    onResponseGetAllHashTag(object, error);
                    break;

                case WebConstants.SETHASHTAG:
                    onResponseSetHashTag(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }


    private ArrayList<HashTags> arrListTags = new ArrayList<HashTags>();

    private void onReponseCreateQuestion(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    Utils.showToast(getString(R.string.msg_success_addquestion), getActivity());
                    callApiSetHashTag(responseHandler.getQuestion().get(0).getQuestionId());

                    if (getFragment().getIsSetQuestionData() && !getFragment().getIsCopy()) {

                        Utils.showToast("QUESTION EDIT CALLED", getActivity());
                        getFragment().setQuestionDataAfterEditQuestion(getFragment().getQuestionData(),
                                makeQuestionData(responseHandler.getQuestion().get(0).getQuestionId()),
                                chkAddquestionPreview.isChecked());
                    } else {

                        Utils.showToast("QUESTION ADD CALLED", getActivity());
                        /*this is for add question data*/
                        getFragment().addQuestionDataAfterAddQuestion(makeQuestionData(responseHandler.getQuestion().get(0).getQuestionId()),
                                chkAddquestionPreview.isChecked());

                        if (isAddMore) {
                            clearViewsData();
                        }
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseCreateQuestions api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseCreateQuestions Exception : " + e.toString());
        }
    }

    private Questions makeQuestionData(String questionId) {
        Questions question = new Questions();
        try {
            question.setQuestionId(questionId);
            question.setQuestionCreatorName(WebConstants.TEST_USER_NAME);
            question.setQuestionCreatorId(WebConstants.TEST_USER_ID);
            question.setQuestionFormat(getQuestionFormat());
            question.setQuestionText(etAddquestionTitle.getText().toString());
            question.setQuestionAssetsLink("");
            question.setQuestionImageLink("");
            question.setEvaluationNotes(etEvaluationNote1.getText().toString());
            question.setSolution(etEvaluationNote2.getText().toString());
            question.setSolution(etEvaluationNote2.getText().toString());
            if (getArguments() != null) {
                question.setTopicId(getArguments().getString(CreateExamFragment.ARG_EXAM_TOPIC_ID));
                question.setSubjectId(getArguments().getString(CreateExamFragment.ARG_EXAM_SUBJECT_ID));
                question.setSubjectName(getArguments().getString(ExamsAdapter.ARG_SUBJECT_NAME));
                question.setClassroomId(getArguments().getString(CreateExamFragment.ARG_EXAM_CLASSROOM_ID));
                question.setBookId(getArguments().getString(CreateExamFragment.ARG_EXAM_BOOK_ID));
            }

//                     question.setTags(arrListTags);

//        for (int i = 0; i < arrListAnswerChioces.size(); i++) {
//            Answers answers = new Answers();
//            answers.setChoiceText(arrListAnswerChioces.get(i).getChoiceText());
//            answers.setIsRight((arrListAnswerChioces.get(i).getIsRight()));
//            arrListAnswers.add(answers);
//        }

            ArrayList<Answers> arrListAnswers = new ArrayList<Answers>();
            if (getQuestionFormat().equalsIgnoreCase("mcq")) {
                arrListAnswers.clear();
                for (int i = 0; i < llAddMcqanswer.getChildCount(); i++) {
                    View v = llAddMcqanswer.getChildAt(i);
                    Answers answers = new Answers();
                    answers.setChoiceText(((EditText) v.findViewById(R.id.et_add_mcq_answer)).getText().toString());
                    answers.setIsRight(getIsSelected((ImageView) v.findViewById(R.id.img_ans_radio)));
                    arrListAnswers.add(answers);
                }
            }

            question.setAnswers(arrListAnswers);


        } catch (Exception error) {
            Debug.e(TAG, "Customely Make Question Object Exception : " + error.toString());
        }

        return question;
    }


    private void onResponseGetAllHashTag(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrListTags = responseHandler.getTags();
                    tags = new HashTagsModel[arrListTags.size()];
                    for (int i = 0; i < arrListTags.size(); i++) {
                        HashTagsModel hashTagsModel = new HashTagsModel(arrListTags.get(i).getTag(), arrListTags.get(i).getTagId());
                        tags[i] = hashTagsModel;
                    }
                    tagsAdapter = new FilteredArrayAdapter<HashTagsModel>(getActivity(), R.layout.tag_search_layout, tags) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            if (convertView == null) {

                                LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                                convertView = l.inflate(R.layout.tag_search_layout, parent, false);
                            }
                            HashTagsModel tagModel = getItem(position);
                            ((TextView) convertView.findViewById(R.id.tv_tag_name)).setText(tagModel.getTagName());
                            return convertView;
                        }

                        @Override
                        protected boolean keepObject(HashTagsModel tagModel, String mask) {
                            mask = mask.toLowerCase();
                            return tagModel.getTagName().toLowerCase().startsWith(mask);
                        }
                    };
                    tagsView = (ContactsCompletionView) view.findViewById(R.id.searchTagView);
                    tagsView.setAdapter(tagsAdapter);
                    tagsView.setTokenListener(this);
                    tagsView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllHashTags api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllHashTags Exception : " + e.toString());
        }
    }


    private void onResponseSetHashTag(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    Utils.showToast(getString(R.string.msg_success_sethashtag), getActivity());
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseSetHashTags api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseSetHashTags Exception : " + e.toString());
        }
    }

}
