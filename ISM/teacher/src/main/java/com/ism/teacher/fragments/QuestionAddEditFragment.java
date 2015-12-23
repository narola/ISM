package com.ism.teacher.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
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

import com.ism.teacher.ISMTeacher;
import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.adapters.AssignmentsAdapter;
import com.ism.teacher.autocomplete.ContactsCompletionView;
import com.ism.teacher.autocomplete.FilteredArrayAdapter;
import com.ism.teacher.autocomplete.TokenCompleteTextView;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.dialog.AddQuestionTextDialog;
import com.ism.teacher.helper.InputValidator;
import com.ism.teacher.model.HashTagsModel;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.MediaUploadAttribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.AnswerChoices;
import com.ism.teacher.ws.model.Answers;
import com.ism.teacher.ws.model.HashTags;
import com.ism.teacher.ws.model.Questions;
import com.ism.teacher.ws.model.Tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionAddEditFragment extends Fragment implements TokenCompleteTextView.TokenListener, View.OnClickListener, WebserviceWrapper.WebserviceResponse,
        AddQuestionTextDialog.SelectMediaListener, AddQuestionTextDialog.AddTextListener {

    private static final String TAG = QuestionAddEditFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;

    /**
     * these is for the tag add functionality.
     * for hashtag you have to manage "hashtagname:hashtagid:status" structure if insert hashtag status=1 and if delete hashtag status=0
     * if you create new hashtag sent hashtag id 0 otherwise tag id get from api
     */
    private ContactsCompletionView tagsView;
    private HashTagsModel[] tags;
    private ArrayAdapter<HashTagsModel> tagsAdapter;
    private List<HashTagsModel> listOfDeletedHashTag = new ArrayList<HashTagsModel>();

    /*these sre for the xml views*/
    private TextView tvAddquestionHeader, tvAddquestionTitle, tvAddquestionType, tvAddquestionCategory, tvEvaluationNote1, tvEvaluationNote2,
            tvAddquestionSave, tvAddquestionSaveAddmore, tvAddquestionGotoquestionbank, tvAddquestionAdvance, tvAddquestionAnswer;
    private ImageView imgSelectImage, imgPlay, img_cancel, imgHelp, imageValidationQuestionType;
    private EditText etAddquestionTitle, etEvaluationNote1, etEvaluationNote2;
    private Spinner spAddquestionType;
    private CheckBox chkAddquestionPreview;
    List<String> arrListQuestionType;
    private LinearLayout llAddMcqanswer;
    private RelativeLayout rlSelectImage;

    private InputValidator inputValidator;
    private Boolean isAddMore = false;
    private Uri selectedUri = null;
    AddQuestionTextDialog addQuestionTextDialog;
    private final int SELECT_PHOTO = 1, SELECT_VIDEO = 2;


    public QuestionAddEditFragment() {
    }

    @SuppressLint("ValidFragment")
    public QuestionAddEditFragment(Fragment fragment, Bundle bundleArguments) {
        this.mFragment = fragment;
        this.setArguments(bundleArguments);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question_addedit, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {

        inputValidator = new InputValidator(getActivity());

        imageValidationQuestionType = (ImageView) view.findViewById(R.id.image_validation_questionType);
        imageValidationQuestionType.setOnClickListener(this);

        imgHelp = (ImageView) view.findViewById(R.id.img_help);
        img_cancel = (ImageView) view.findViewById(R.id.img_cancel);
        img_cancel.setOnClickListener(this);
        tvAddquestionAnswer = (TextView) view.findViewById(R.id.tv_addquestion_answer);
        tvAddquestionAdvance = (TextView) view.findViewById(R.id.tv_addquestion_advance);
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
        tvAddquestionAdvance.setOnClickListener(this);
        tvAddquestionAdvance.setPaintFlags(tvAddquestionAdvance.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tvAddquestionAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvAddquestionAdvance.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvAddquestionHeader.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvAddquestionTitle.setTypeface(Global.myTypeFace.getRalewayBold());
        tvAddquestionType.setTypeface(Global.myTypeFace.getRalewayBold());
        tvAddquestionCategory.setTypeface(Global.myTypeFace.getRalewayBold());
        tvEvaluationNote1.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvEvaluationNote2.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvAddquestionSave.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvAddquestionSaveAddmore.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvAddquestionGotoquestionbank.setTypeface(Global.myTypeFace.getRalewayRegular());


        imgSelectImage = (ImageView) view.findViewById(R.id.img_select_image);
        imgPlay = (ImageView) view.findViewById(R.id.img_play);

        etAddquestionTitle = (EditText) view.findViewById(R.id.et_addquestion_title);
        etEvaluationNote1 = (EditText) view.findViewById(R.id.et_evaluation_note1);
        etEvaluationNote2 = (EditText) view.findViewById(R.id.et_evaluation_note2);


        etAddquestionTitle.setTypeface(Global.myTypeFace.getRalewayRegular());
        etEvaluationNote1.setTypeface(Global.myTypeFace.getRalewayRegular());
        etEvaluationNote2.setTypeface(Global.myTypeFace.getRalewayRegular());


        spAddquestionType = (Spinner) view.findViewById(R.id.sp_addquestion_type);

        llAddMcqanswer = (LinearLayout) view.findViewById(R.id.ll_add_mcqanswer);
        rlSelectImage = (RelativeLayout) view.findViewById(R.id.rl_select_image);
        rlSelectImage.setOnClickListener(this);

        chkAddquestionPreview = (CheckBox) view.findViewById(R.id.chk_addquestion_preview);
        chkAddquestionPreview.setTypeface(Global.myTypeFace.getRalewayRegular());

        arrListQuestionType = new ArrayList<String>();
        arrListQuestionType.add(getString(R.string.strquestiontype));
        arrListQuestionType = Arrays.asList(getResources().getStringArray(R.array.question_type));
        Adapters.setUpSpinner(getActivity(), spAddquestionType, arrListQuestionType, Adapters.ADAPTER_SMALL);

        spAddquestionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     /*here i checked that if question is of related exam type then only add to preview otherwise not*/
                if (position == 1 || position == 2) {
                    llAddMcqanswer.setVisibility(View.GONE);
                    tvAddquestionAnswer.setVisibility(View.VISIBLE);
                    if (getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_MODE).equalsIgnoreCase(getString(R.string.strsubjective))) {
                        chkAddquestionPreview.setEnabled(true);
                        chkAddquestionPreview.setChecked(true);
                    } else {
                        chkAddquestionPreview.setEnabled(false);
                        chkAddquestionPreview.setChecked(false);
                    }
                } else if (position == 3) {
                    llAddMcqanswer.setVisibility(View.VISIBLE);
                    tvAddquestionAnswer.setVisibility(View.VISIBLE);
                    if (getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_MODE).equalsIgnoreCase(getString(R.string.strobjective))) {
                        chkAddquestionPreview.setEnabled(true);
                        chkAddquestionPreview.setChecked(false);
                    } else {
                        chkAddquestionPreview.setEnabled(true);
                        chkAddquestionPreview.setChecked(true);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        callAPiGetAllHashTag();
    }
    //intiGlobalEnds

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent returnedIntent) {
        super.onActivityResult(requestCode, resultCode, returnedIntent);


        if (returnedIntent != null) {
            selectedUri = returnedIntent.getData();
            if (requestCode == AppConstant.REQUEST_CODE_PICK_FROM_GALLERY) {

                if (returnedIntent != null) {
                    selectedUri = returnedIntent.getData();
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

                        try {
                            if (addQuestionTextDialog != null && addQuestionTextDialog.isShowing()) {

                                String imgPath = getRealPathFromURI(selectedUri);
                                addQuestionTextDialog.insertImage(imgPath);
                            } else {
                                imgSelectImage.setImageURI(selectedUri);
                                imgPlay.setVisibility(View.GONE);
                                img_cancel.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (mimeType.startsWith("video")) {

                        try {
                            if (addQuestionTextDialog != null && addQuestionTextDialog.isShowing()) {

                                String videoPath = getRealPathFromURI(selectedUri);
                                addQuestionTextDialog.insertVideo(videoPath);
                            } else {
                                MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
                                mMediaMetadataRetriever.setDataSource(getActivity(), selectedUri);
                                Bitmap bitmap = mMediaMetadataRetriever.getFrameAtTime(1 * 1000);
                                imgSelectImage.setImageBitmap(bitmap);
                                imgPlay.setVisibility(View.VISIBLE);
                                img_cancel.setVisibility(View.VISIBLE);

                            }

//                        richText.insertVideo(videoPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
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
        if (!((HashTagsModel) token).getTagId().equals("0")) {
            listOfDeletedHashTag.add((HashTagsModel) token);
        }
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
        tvAddquestionAnswer.setText("");
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
        chkAddquestionPreview.setEnabled(true);
        chkAddquestionPreview.setChecked(false);

        spAddquestionType.setSelection(1);
        imageValidationQuestionType.setVisibility(View.GONE);

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
        etAddMcqAnswer.setText(Utility.formatHtml(text));
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
            Utility.alert(getActivity(), null, getActivity().getResources().getString(R.string.msg_mcq_ans_limit));
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


    public void setQuestionData(Questions questions) {
        clearViewsData();
        try {

            if (questions.getIsQuestionAddedInPreview()) {
                chkAddquestionPreview.setChecked(true);
                chkAddquestionPreview.setEnabled(false);
            }
            /*check that if user edit question then disable the formatting of question type.*/
            if (getFragment().getIsSetQuestionData() && !getFragment().getIsCopy()) {

                imageValidationQuestionType.setVisibility(View.VISIBLE);

            } else {
                imageValidationQuestionType.setVisibility(View.GONE);
            }
            setSpinnerData(questions.getQuestionFormat());
            if (questions.getQuestionText() != null) {
                etAddquestionTitle.setText(Utility.formatHtml(questions.getQuestionText()));
            }

            if (questions.getEvaluationNotes() != null) {
                etEvaluationNote1.setText(questions.getEvaluationNotes());
                etEvaluationNote2.setText(questions.getEvaluationNotes());
            }

            if (questions.getQuestionImageLink() != null && !questions.getQuestionImageLink().equals("")) {
                Global.imageLoader.displayImage(WebConstants.Image_url + questions.getQuestionImageLink(), imgSelectImage, ISMTeacher.options);

                Debug.e(TAG, "============from set data =======================" + questions.getQuestionImageLink());
                img_cancel.setVisibility(View.VISIBLE);
            }

            setMcqAnswers(questions);
            setTags(questions);


        } catch (Exception e) {
            Debug.e(TAG, "isSetQuestionData Exception : " + e.toString());
        }
    }


    private void setSpinnerData(String questionType) {
        if (questionType.equalsIgnoreCase(getString(R.string.strquestionformatmcq))) {
            spAddquestionType.setSelection(3);
            llAddMcqanswer.setVisibility(View.VISIBLE);
            tvAddquestionAnswer.setVisibility(View.GONE);
        } else if (questionType.equalsIgnoreCase(getString(R.string.strquestionformatdescriptive))) {
            spAddquestionType.setSelection(1);
            llAddMcqanswer.setVisibility(View.GONE);
            tvAddquestionAnswer.setVisibility(View.VISIBLE);
        } else if (questionType.equalsIgnoreCase(getString(R.string.strquestionformatfillups))) {
            spAddquestionType.setSelection(2);
            llAddMcqanswer.setVisibility(View.GONE);
            tvAddquestionAnswer.setVisibility(View.VISIBLE);
        }
    }


    private void setMcqAnswers(Questions questions) {
        if (questions.getAnswers() != null) {
            if (questions.getAnswers().size() > 0) {
                llAddMcqanswer.removeAllViews();
            }
        }
        for (int i = 0; i < questions.getAnswers().size(); i++) {
            llAddMcqanswer.addView(setMCQ(i, questions.getAnswers().get(i).getChoiceText(),
                    questions.getAnswers().get(i).getIsRight().equals("1") ? true : false, questions.getAnswers().size()));
        }
    }

    private void setTags(Questions questions) {
        if (questions.getTags() != null) {
            if (questions.getTags().size() > 0) {
                for (int i = 0; i < questions.getTags().size(); i++) {
                    tagsView.addObject(new HashTagsModel(questions.getTags().get(i).getTagName(), questions.getTags().get(i).getTagId()));
                }
            }
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
                isValidate = true;
                break;
            case 2:
                isValidate = true;
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
            if (isInputsValid()) {
                callApiCreateQuestion();
            }

        } else if (v == tvAddquestionSaveAddmore) {

            isAddMore = true;
            if (isInputsValid()) {
                callApiCreateQuestion();
            }


        } else if (v == rlSelectImage) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("*/*");
            startActivityForResult(intent, AppConstant.REQUEST_CODE_PICK_FROM_GALLERY);

        } else if (v == tvAddquestionGotoquestionbank) {
            getFragment().flipCard();

        } else if (v == img_cancel) {
            if (selectedUri != null) {
                imgSelectImage.setImageDrawable(null);
                img_cancel.setVisibility(View.GONE);
                selectedUri = null;
            } else if (imgSelectImage.getDrawable() != null) {
                imgSelectImage.setImageDrawable(null);
                img_cancel.setVisibility(View.GONE);
            }
        } else if (v == tvAddquestionAdvance) {

            addQuestionTextDialog = new AddQuestionTextDialog(getActivity(), (AddQuestionTextDialog.SelectMediaListener) this,
                    (AddQuestionTextDialog.AddTextListener) this, Html.toHtml(etAddquestionTitle.getText()));
            addQuestionTextDialog.show();

        } else if (v == imgHelp) {
//            showHelpInstruction();
            Utility.alert(getActivity(), null, getActivity().getResources().getString(R.string.msg_help_add_advance_question));
        } else if (v == imageValidationQuestionType) {
            Utility.alert(getActivity(), null, getString(R.string.msg_validation_question_type));
        }

    }

    private void callApiUploadMediaForQuestion(String questionId, String mediaType, String fileName) {
        if (Utility.isConnected(getActivity())) {
            ((TeacherHostActivity) getActivity()).showProgress();
            try {
                Attribute attribute = new Attribute();
                MediaUploadAttribute questionIdParam = new MediaUploadAttribute();
                questionIdParam.setParamName("question_id");
                questionIdParam.setParamValue(questionId);
                attribute.getArrListParam().add(questionIdParam);


                MediaUploadAttribute mediaTypeParam = new MediaUploadAttribute();
                mediaTypeParam.setParamName("mediaType");
                mediaTypeParam.setParamValue(mediaType);
                attribute.getArrListParam().add(mediaTypeParam);


                MediaUploadAttribute mediaFileParam = new MediaUploadAttribute();
                mediaFileParam.setParamName("mediaFile");
                mediaFileParam.setFileName(fileName);
                attribute.getArrListFile().add(mediaFileParam);


                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.UPLOADMEDIAFORQUESTION);
            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    private void callAPiGetAllHashTag() {
        if (Utility.isConnected(getActivity())) {
            ((TeacherHostActivity) getActivity()).showProgress();
            try {
                new WebserviceWrapper(getActivity(), new Attribute(), (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_ALL_HASHTAG);

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
                ((TeacherHostActivity) getActivity()).showProgress();

                Debug.e(TAG, "The user id is::" + WebConstants.USER_ID_370);
                Debug.e(TAG, "The question text is::" + etAddquestionTitle.getText().toString());
                Debug.e(TAG, "The subject id is::" + getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_SUBJECT_ID));
                Debug.e(TAG, "The question score is::" + getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE));
                Debug.e(TAG, "The question format is::" + getQuestionFormat());
                Debug.e(TAG, "The evaluation notes is::" + etEvaluationNote1.getText().toString());
                Debug.e(TAG, "The solution  is::" + etEvaluationNote2.getText().toString());
                Debug.e(TAG, "The topic id  is::" + getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID));
                Debug.e(TAG, "The classroom id  is::" + getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_CLASSROOM_ID));
                Debug.e(TAG, "The book id  is::" + getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_BOOK_ID));

                Attribute attribute = new Attribute();
                attribute.setUserId(WebConstants.USER_ID_370);
                if (getFragment().getIsSetQuestionData() && !getFragment().getIsCopy()) {
                    /*for edit question*/
                    Debug.e(TAG, "The question id is::" + getFragment().getQuestionData().getQuestionId());
                    attribute.setQuestionid(getFragment().getQuestionData().getQuestionId());

                } else {
                    /*for add question*/
                    Debug.e(TAG, "The question id is::" + "0");
                    attribute.setQuestionid("0");
                }
                attribute.setQuestionText(etAddquestionTitle.getText().toString());
                attribute.setSubjectId(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_SUBJECT_ID));
                attribute.setQuestionScore(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE));
                attribute.setQuestionFormat(getQuestionFormat());
                attribute.setEvaluationNotes(etEvaluationNote1.getText().toString());
                attribute.setSolution(etEvaluationNote2.getText().toString());
                attribute.setTopicId(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID));
                attribute.setClassroomId(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_CLASSROOM_ID));
                attribute.setBookId(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_BOOK_ID));

//                attribute.setTags(arrListTags);

                if (getQuestionFormat().equalsIgnoreCase(getString(R.string.strquestionformatmcq))) {
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

                StringBuilder sb = new StringBuilder();
                List<HashTagsModel> list = tagsView.getObjects();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).getTagName() + ":" + list.get(i).getTagId() + ":1");
                    sb.append(",");

                }

                if (listOfDeletedHashTag.size() > 0) {
                    for (int i = 0; i < listOfDeletedHashTag.size(); i++) {
                        sb.append(listOfDeletedHashTag.get(i).getTagName() + ":" + listOfDeletedHashTag.get(i).getTagId() + ":0");
                        if (i < listOfDeletedHashTag.size() - 1) {
                            sb.append(",");
                        }
                    }

                } else {
                    sb.substring(0, sb.toString().length() - 1);
                }
                Debug.e(TAG, "The HashTags Are:::" + sb.toString());

                //   Utility.showToast("The HashTags are::" + sb.toString(), getActivity());


                attribute.setHashtagData(sb.toString());
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.TEMP_CREATE_QUESTION);

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
                questionFormat = getString(R.string.strquestionformatdescriptive);
                break;
            case 2:
                questionFormat = getString(R.string.strquestionformatdescriptive);
                break;
            case 3:
                questionFormat = getString(R.string.strquestionformatmcq).toUpperCase();
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
                case WebConstants.TEMP_CREATE_QUESTION:
                    onReponseCreateQuestion(object, error);
                    break;

                case WebConstants.GET_ALL_HASHTAG:
                    onResponseGetAllHashTag(object, error);
                    break;

                case WebConstants.SET_HASHTAG:
                    onResponseSetHashTag(object, error);
                    break;
                case WebConstants.UPLOADMEDIAFORQUESTION:
                    onResponseUploadMediaForQuestion(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseUploadMediaForQuestion(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    Debug.e(TAG, "The Image url is::" + responseHandler.getFileUploadResponse().getImageLink() + " question id is: " + responseHandler.getFileUploadResponse().getQuestion_id());
                    Utility.showToast(getString(R.string.msg_success_imgupload_question), getActivity());


                    if (getFragment().getIsSetQuestionData() && !getFragment().getIsCopy()) {
                        //  Utility.showToast(getString(R.string.question_edit_success), getActivity());
//                        Utility.alert(getActivity(), null, getActivity().getResources().getString(R.string.question_edit_success));
                        getFragment().setQuestionDataAfterEditQuestion(getFragment().getQuestionData(),
                                makeQuestionData(responseHandler.getFileUploadResponse().getQuestion_id(), responseHandler.getFileUploadResponse().getImageLink()),
                                chkAddquestionPreview.isChecked());
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseUploadMediaForQuestion api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseUploadMediaForQuestion Exception : " + e.toString());
        }
    }

    private ArrayList<HashTags> arrListTags = new ArrayList<HashTags>();

    private void onReponseCreateQuestion(Object object, Exception error) {

        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    Debug.e(TAG, "The Question Id Is::" + responseHandler.getQuestion().get(0).getQuestionId());
                    if (getFragment().getIsSetQuestionData() && !getFragment().getIsCopy()) {

                        Utility.showToast(getString(R.string.question_edit_success), getActivity());
                        getFragment().setQuestionDataAfterEditQuestion(getFragment().getQuestionData(),
                                makeQuestionData(responseHandler.getQuestion().get(0).getQuestionId(), ""),
                                chkAddquestionPreview.isChecked());
                    } else {

                        Utility.showToast(getString(R.string.question_add_success), getActivity());
                        getFragment().addQuestionDataAfterAddQuestion(makeQuestionData(responseHandler.getQuestion().get(0).getQuestionId(), ""),
                                chkAddquestionPreview.isChecked());

                    }
                    if (isAddMore) {
                        clearViewsData();
                    }

                    if (selectedUri != null) {
                        Debug.e(TAG, "Thefile path is:" + getRealPathFromURI(selectedUri));
                        callApiUploadMediaForQuestion(responseHandler.getQuestion().get(0).getQuestionId(), AppConstant.MEDIATYPE_IMAGE,
                                getRealPathFromURI(selectedUri));
                    }
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());

                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseCreateQuestions api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseCreateQuestions Exception : " + e.toString());
        }
    }


    private Questions makeQuestionData(String questionId, String imagelink) {
        Questions question = new Questions();
        try {
            question.setQuestionId(questionId);
            question.setQuestionCreatorName(WebConstants.TEST_USER_NAME);
            question.setQuestionCreatorId(WebConstants.USER_ID_370);
            question.setQuestionFormat(getQuestionFormat());
            question.setQuestionText(etAddquestionTitle.getText().toString());
            question.setQuestionAssetsLink("");

            if (imagelink != null && !imagelink.equals("")) {
                question.setQuestionImageLink(imagelink);
            } else {
                question.setQuestionImageLink("");

            }
            question.setEvaluationNotes(etEvaluationNote1.getText().toString());
            question.setSolution(etEvaluationNote2.getText().toString());

            if (getBundleArguments() != null) {
                question.setTopicId(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_TOPIC_ID));
                question.setSubjectId(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_SUBJECT_ID));
                question.setSubjectName(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_SUBJECT_NAME));
                question.setClassroomId(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_CLASSROOM_ID));
                question.setBookId(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_BOOK_ID));
                question.setQuestionScore(getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE));
            }
            ArrayList<Tags> arrListTags = new ArrayList<Tags>();
            List<HashTagsModel> list = tagsView.getObjects();
            for (int i = 0; i < list.size(); i++) {
                Tags tag = new Tags();
                tag.setTagId(list.get(i).getTagId());
                tag.setTagName(list.get(i).getTagName());
                arrListTags.add(tag);
            }
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
            question.setTags(arrListTags);
        } catch (Exception error) {
            Debug.e(TAG, "Custom Make Question Object Exception : " + error.toString());
        }
        return question;
    }


    private void onResponseGetAllHashTag(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
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
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllHashTags api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllHashTags Exception : " + e.toString());
        }
    }

    public void updateAddToPreviewCheckBoxStatus() {
        chkAddquestionPreview.setEnabled(true);
        chkAddquestionPreview.setChecked(false);
    }

    private void onResponseSetHashTag(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    Debug.e(TAG, "The Response Of Set HashTag is" + responseHandler.getMessage());
                    Utility.showToast(getString(R.string.msg_success_sethashtag), getActivity());
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseSetHashTags api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseSetHashTags Exception : " + e.toString());
        }
    }

    @Override
    public void SetText(String text) {
        etAddquestionTitle.setText(Utility.formatHtml(text));
    }

    @Override
    public void ImagePicker() {
        openImage();
    }

    @Override
    public void VideoPicker() {
        openVideo();
    }

    private void openImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    private void openVideo() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("video/*");
        startActivityForResult(photoPickerIntent, SELECT_VIDEO);

    }
    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }
}
