package com.ism.author.fragment.createquestion;

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

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.HtmlImageGetter;
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
import com.ism.author.dialog.AddQuestionTextDialog;
import com.ism.author.model.HashTagsModel;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.MediaUploadAttribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.AnswerChoices;
import com.ism.author.ws.model.Answers;
import com.ism.author.ws.model.HashTags;
import com.ism.author.ws.model.Questions;
import com.ism.author.ws.model.Tags;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by c166 on 31/10/15.
 */
public class QuestionAddEditFragment extends Fragment implements TokenCompleteTextView.TokenListener, View.OnClickListener,
        WebserviceWrapper.WebserviceResponse, AddQuestionTextDialog.SelectMediaListener, AddQuestionTextDialog.AddTextListener {

    private static final String TAG = QuestionAddEditFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;

    public QuestionAddEditFragment() {
    }

    @SuppressLint("ValidFragment")
    public QuestionAddEditFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    /*these is for the tag add functionality.*/
    /*for hashtag you have to manage "hashtagname:hashtagid:status" structure if insert hashtag status=1 and if delete hashtag status=0 */
    /*if you create new hashtag sent hashtag id 0 otherwise tag id get from api*/
    private ContactsCompletionView tagsView = null;
    private HashTagsModel[] tags;
    private ArrayAdapter<HashTagsModel> tagsAdapter;
    private List<HashTagsModel> listOfDeletedHashTag = new ArrayList<HashTagsModel>();

    /*these sre for the xml views*/
    private TextView tvAddquestionHeader, tvAddquestionTitle, tvAddquestionType, tvAddquestionCategory, tvEvaluationNote1, tvSolution,
            tvAddquestionSave, tvAddquestionSaveAddmore, tvAddquestionGotoquestionbank, tvAddquestionAdvance, tvAddquestionAnswer,
            tvAddquestionScore;
    private ImageView imgSelectImage, imgPlay, imgHelp, imgDelete, imageValidationQuestionType;
    private EditText etAddquestionTitle, etEvaluationNote1, etSolution;
    private Spinner spAddquestionType, spExamQuestionScore;
    private CheckBox chkAddquestionPreview;
    List<String> arrListQuestionType, arrListQuestionScore;
    private LinearLayout llAddMcqanswer, llAddQuestionscore;
    private RelativeLayout rlSelectImage;

    private InputValidator inputValidator;
    private Boolean isAddMore = false;


    private final int SELECT_PHOTO = 1, SELECT_VIDEO = 2;
    private static int QUESTIONSCORE_INERVAL = 1, QUESTIONSCORE_STARTVALUE = 1, QUESTIONSCORE_ENDVALUE = 5;
    Uri selectedUri = null, mediaUri = null;
    String mediaType;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question_addedit, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {

        inputValidator = new InputValidator(getActivity());
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

        tvAddquestionHeader = (TextView) view.findViewById(R.id.tv_addquestion_header);
        tvAddquestionTitle = (TextView) view.findViewById(R.id.tv_addquestion_title);
        tvAddquestionType = (TextView) view.findViewById(R.id.tv_addquestion_type);
        tvAddquestionCategory = (TextView) view.findViewById(R.id.tv_addquestion_category);
        tvEvaluationNote1 = (TextView) view.findViewById(R.id.tv_evaluation_note1);
        tvSolution = (TextView) view.findViewById(R.id.tv_solution);
        tvAddquestionSave = (TextView) view.findViewById(R.id.tv_addquestion_save);
        tvAddquestionSaveAddmore = (TextView) view.findViewById(R.id.tv_addquestion_save_addmore);
        tvAddquestionGotoquestionbank = (TextView) view.findViewById(R.id.tv_addquestion_gotoquestionbank);
        tvAddquestionAdvance = (TextView) view.findViewById(R.id.tv_addquestion_advance);
        tvAddquestionScore = (TextView) view.findViewById(R.id.tv_addquestion_score);
        tvAddquestionSave.setOnClickListener(this);
        tvAddquestionSaveAddmore.setOnClickListener(this);
        tvAddquestionGotoquestionbank.setOnClickListener(this);
        tvAddquestionAdvance.setOnClickListener(this);
        tvAddquestionAdvance.setPaintFlags(tvAddquestionAdvance.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        tvAddquestionHeader.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvAddquestionTitle.setTypeface(Global.myTypeFace.getRalewayBold());
        tvAddquestionType.setTypeface(Global.myTypeFace.getRalewayBold());
        tvAddquestionCategory.setTypeface(Global.myTypeFace.getRalewayBold());
        tvEvaluationNote1.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvSolution.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvAddquestionSave.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvAddquestionSaveAddmore.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvAddquestionGotoquestionbank.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvAddquestionAdvance.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvAddquestionScore.setTypeface(Global.myTypeFace.getRalewayBold());

        imgSelectImage = (ImageView) view.findViewById(R.id.img_select_image);
        imgPlay = (ImageView) view.findViewById(R.id.img_play);
        imgHelp = (ImageView) view.findViewById(R.id.img_help);
        imgDelete = (ImageView) view.findViewById(R.id.img_delete);
        imageValidationQuestionType = (ImageView) view.findViewById(R.id.image_validation_questionType);
        imgHelp.setOnClickListener(this);
        imgDelete.setOnClickListener(this);
        imageValidationQuestionType.setOnClickListener(this);


        etAddquestionTitle = (EditText) view.findViewById(R.id.et_addquestion_title);
        tvAddquestionAnswer = (TextView) view.findViewById(R.id.tv_addquestion_answer);
        etEvaluationNote1 = (EditText) view.findViewById(R.id.et_evaluation_note1);
        etSolution = (EditText) view.findViewById(R.id.et_solution);


        etAddquestionTitle.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvAddquestionAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());
        etEvaluationNote1.setTypeface(Global.myTypeFace.getRalewayRegular());
        etSolution.setTypeface(Global.myTypeFace.getRalewayRegular());

        spAddquestionType = (Spinner) view.findViewById(R.id.sp_addquestion_type);
        spExamQuestionScore = (Spinner) view.findViewById(R.id.sp_exam_question_score);

        getQuestionScoreSpinnerValues();
        Adapters.setUpSpinner(getActivity(), spExamQuestionScore, arrListQuestionScore, Adapters.ADAPTER_SMALL);

        llAddMcqanswer = (LinearLayout) view.findViewById(R.id.ll_add_mcqanswer);
        llAddQuestionscore = (LinearLayout) view.findViewById(R.id.ll_add_questionscore);
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
                    if (getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_MODE).equalsIgnoreCase(getString(R.string.strsubjective))) {
                        chkAddquestionPreview.setEnabled(true);
                        chkAddquestionPreview.setChecked(true);
                    } else {
                        chkAddquestionPreview.setEnabled(false);
                        chkAddquestionPreview.setChecked(false);
                    }

                } else if (position == 3) {

                    llAddMcqanswer.setVisibility(View.VISIBLE);
                    tvAddquestionAnswer.setVisibility(View.GONE);
                    if (getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_MODE).equalsIgnoreCase(getString(R.string.strobjective))) {
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent returnedIntent) {
        super.onActivityResult(requestCode, resultCode, returnedIntent);

        if (returnedIntent != null) {

            switch (requestCode) {
                case SELECT_PHOTO:
                    if (resultCode == getActivity().RESULT_OK) {
                        try {
                            if (addQuestionTextDialog != null && addQuestionTextDialog.isShowing()) {
                                final Uri imageUri = returnedIntent.getData();
                                String imgPath = Utility.getRealPathFromURI(imageUri, getActivity());
                                addQuestionTextDialog.insertImage(imgPath);
                            } else {
                                selectedUri = returnedIntent.getData();
                                imgSelectImage.setImageURI(selectedUri);
                                imgPlay.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case SELECT_VIDEO:

                    if (resultCode == getActivity().RESULT_OK) {
                        try {
                            if (addQuestionTextDialog != null && addQuestionTextDialog.isShowing()) {

                                final Uri videoUri = returnedIntent.getData();
                                String videoPath = Utility.getRealPathFromURI(videoUri, getActivity());
                                addQuestionTextDialog.insertVideo(videoPath);
                            } else {
                                selectedUri = returnedIntent.getData();
                                MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
                                mMediaMetadataRetriever.setDataSource(getActivity(), selectedUri);
                                Bitmap bitmap = mMediaMetadataRetriever.getFrameAtTime(1 * 1000);
                                imgSelectImage.setImageBitmap(bitmap);
                                imgPlay.setVisibility(View.VISIBLE);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    break;

                case AppConstant.REQUEST_CODE_PICK_FROM_GALLERY:

                    if (returnedIntent != null) {
                        selectedUri = returnedIntent.getData();
                        String[] columns = {MediaStore.Images.Media.DATA,
                                MediaStore.Images.Media.MIME_TYPE};
                        Cursor cursor = getActivity().getContentResolver().query(selectedUri, columns, null, null, null);
                        cursor.moveToFirst();

                        int mimeTypeColumnIndex = cursor.getColumnIndex(columns[1]);
                        String mimeType = cursor.getString(mimeTypeColumnIndex);
                        cursor.close();

                        if (mimeType.startsWith("image")) {
                            try {
                                mediaType = AppConstant.MEDIATYPE_IMAGE;
                                imgSelectImage.setImageURI(selectedUri);
                                imgPlay.setVisibility(View.GONE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (mimeType.startsWith("video")) {
                            try {
                                mediaType = AppConstant.MEDIATYPE_VIDEO;
                                MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
                                mMediaMetadataRetriever.setDataSource(getActivity(), selectedUri);
                                Bitmap bitmap = mMediaMetadataRetriever.getFrameAtTime(1 * 1000);
                                imgSelectImage.setImageBitmap(bitmap);
                                imgPlay.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    break;
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
        if (getBaseFragment().getIsSetQuestionData()) {
            setQuestionData(getBaseFragment().getQuestionData());
        } else {
            clearViewsData();
        }
    }

    private void clearViewsData() {
        etAddquestionTitle.setText("");
        tvAddquestionAnswer.setText("");
        llAddMcqanswer.removeAllViews();
        if (tagsView != null) {
            if (tagsView.getObjects().size() > 0) {
                tagsView.clear();
            }
        }
        etEvaluationNote1.setText("");
        etSolution.setText("");
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
        spExamQuestionScore.setSelection(1);
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
//            Utils.showToast(getActivity().getResources().getString(R.string.msg_mcq_ans_limit), getActivity());
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


    /*these is for set question questionData for copy and edit question.*/
    private ImageLoader imageLoader;

    public void setQuestionData(Questions questions) {
        clearViewsData();
        try {

            if (questions.getIsQuestionAddedInPreview()) {
                chkAddquestionPreview.setChecked(true);
                chkAddquestionPreview.setEnabled(false);
            }
            /*check that if user edit question then disable the formatting of question type.*/
            if (getBaseFragment().getIsSetQuestionData() && !getBaseFragment().getIsCopy()) {
                imageValidationQuestionType.setVisibility(View.VISIBLE);
            } else {
                imageValidationQuestionType.setVisibility(View.GONE);
            }
            setSpinnerData(questions.getQuestionFormat());
            if (questions.getQuestionText() != null) {
                etAddquestionTitle.setText(Utils.formatHtml(questions.getQuestionText()));
            }

            if (questions.getEvaluationNotes() != null) {
                etEvaluationNote1.setText(questions.getEvaluationNotes());
                etSolution.setText(questions.getEvaluationNotes());
            }
            if (questions.getQuestionImageLink() != null && !questions.getQuestionImageLink().equals("")) {
                imageLoader.displayImage(WebConstants.QUESTION_IMAGES + questions.getQuestionImageLink(),
                        imgSelectImage, ISMAuthor.options);
            }
            spExamQuestionScore.setSelection(arrListQuestionScore.indexOf(questions.getQuestionScore()));

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
//                & inputValidator.validateStringPresence(etSolution) && checkSpinnerDataValidation();
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


    AddQuestionTextDialog addQuestionTextDialog;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_addquestion_save:
                isAddMore = false;
                if (isInputsValid()) {
                    callApiTempCreateQuestion();
                }
                break;
            case R.id.tv_addquestion_save_addmore:
//            if (getBaseFragment().getIsSetQuestionData() && !getBaseFragment().getIsCopy()) {
//                Debug.e(TAG, "QUESTION EDIT CALLED");
//                getBaseFragment().setQuestionDataAfterEditQuestion(getBaseFragment().getQuestionData(),
//                        makeQuestionData(getBaseFragment().getQuestionData().getQuestionId()), chkAddquestionPreview.isChecked());
//                clearViewsData();
//            } else {
//                Debug.e(TAG, "QUESTION ADD CALLED");
//                if (isInputsValid()) {
//                    callApiCreateQuestion();
//                }
//            }
                isAddMore = true;
                if (isInputsValid()) {
                    callApiTempCreateQuestion();
                }
                break;

            case R.id.rl_select_image:

//                openImage();

                /**
                 * code to pick source from gallery.
                 */
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("video/*, images/*");
                startActivityForResult(intent, AppConstant.REQUEST_CODE_PICK_FROM_GALLERY);
                break;

            case R.id.tv_addquestion_gotoquestionbank:
                getBaseFragment().flipCard();
                break;

            case R.id.tv_addquestion_advance:

//                htmlText = Html.toHtml(etAddquestionTitle.getText());
//                htmlText = htmlText.replace("<p dir=\"ltr\"><img", "<img");
//                htmlText = htmlText.replace(".png\"></p>", ".png\">");

                addQuestionTextDialog = new AddQuestionTextDialog(getActivity(), (AddQuestionTextDialog.SelectMediaListener) this,
                        (AddQuestionTextDialog.AddTextListener) this, getHtmlQuestionText());
                addQuestionTextDialog.show();
                break;

            case R.id.img_help:
//                showHelpInstruction();
                Utility.alert(getActivity(), null, getActivity().getResources().getString(R.string.msg_help_add_advance_question));
                break;

            case R.id.img_delete:

                imgSelectImage.setImageDrawable(null);
                imgSelectImage.setImageBitmap(null);
                selectedUri = null;
                break;

            case R.id.image_validation_questionType:
                Utility.alert(getActivity(), null, getString(R.string.msg_validation_question_type));

        }

    }


    private String getHtmlQuestionText() {


        Debug.e(TAG, "Original text of edittext is:::::" + Html.toHtml(etAddquestionTitle.getText()));

        htmlText = Html.toHtml(etAddquestionTitle.getText());
        htmlText = htmlText.replace("<p dir=\"ltr\"><img", "<img");
        htmlText = htmlText.replace("<p dir=\"ltr\"", "<p");
        htmlText = htmlText.replace(".png\"></p>", ".png\">");
        htmlText = htmlText.replace(".jpeg\"></p>", ".jpeg\">");
        htmlText = htmlText.replace(".jpg\"></p>", ".jpg\">");

        Debug.e(TAG, "Formatted text of edittext is:::::" + htmlText);

        return htmlText;
    }


    private void callAPiGetAllHashTag() {
        if (Utility.isConnected(getActivity())) {
            ((AuthorHostActivity) getActivity()).showProgress();
            try {
                ((AuthorHostActivity) getActivity()).showProgress();
                new WebserviceWrapper(getActivity(), new Attribute(), (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETALLHASHTAG);
            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }

    ArrayList<AnswerChoices> arrListAnswerChioces = new ArrayList<AnswerChoices>();

//    private void callApiCreateQuestion() {
//        if (Utility.isConnected(getActivity())) {
//
//            try {
//                ((AuthorHostActivity) getActivity()).showProgress();
//                Debug.e(TAG, "The user id is::" + Global.strUserId);
//                Debug.e(TAG, "The question text is::" + Html.toHtml(etAddquestionTitle.getText()));
//                Debug.e(TAG, "The subject id is::" + "0");
//                Debug.e(TAG, "The question score is::" + arrListQuestionScore.get(spExamQuestionScore.getSelectedItemPosition()));
//                Debug.e(TAG, "The question format is::" + getQuestionFormat());
//                Debug.e(TAG, "The evaluation notes is::" + etEvaluationNote1.getText().toString());
//                Debug.e(TAG, "The solution  is::" + etSolution.getText().toString());
//                Debug.e(TAG, "The topic id  is::" + "0");
//                Debug.e(TAG, "The classroom id  is::" + getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_CLASSROOM_ID));
//                Debug.e(TAG, "The book id  is::" + getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_BOOK_ID));
//
//                /*if you edit question you have to pass question idfrom the question data and in
//                add question you have to pass question id 0 */
//                Attribute attribute = new Attribute();
//                attribute.setUserId(Global.strUserId);
//                if (getBaseFragment().getIsSetQuestionData() && !getBaseFragment().getIsCopy()) {
//                    /*for edit question*/
//                    Debug.e(TAG, "The question id is::" + getBaseFragment().getQuestionData().getQuestionId());
//                    attribute.setQuestionid(getBaseFragment().getQuestionData().getQuestionId());
//
//                } else {
//                    /*for add question*/
//                    Debug.e(TAG, "The question id is::" + "0");
//                    attribute.setQuestionid("0");
//
//                }
//
//                attribute.setSubjectId("0");
//                attribute.setTopicId("0");
//                attribute.setBookId(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_BOOK_ID));
//                attribute.setClassroomId(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_CLASSROOM_ID));
//                attribute.setQuestionText(Html.toHtml(etAddquestionTitle.getText()));
//                attribute.setQuestionScore(arrListQuestionScore.get(spExamQuestionScore.getSelectedItemPosition()));
//                attribute.setQuestionFormat(getQuestionFormat());
//                attribute.setEvaluationNotes(etEvaluationNote1.getText().toString());
//                attribute.setSolution(etSolution.getText().toString());
//
//
//                if (getQuestionFormat().equalsIgnoreCase(getString(R.string.strquestionformatmcq))) {
//                    arrListAnswerChioces.clear();
//                    for (int i = 0; i < llAddMcqanswer.getChildCount(); i++) {
//                        View v = llAddMcqanswer.getChildAt(i);
//                        AnswerChoices answerChoices = new AnswerChoices();
//                        answerChoices.setChoiceText(((EditText) v.findViewById(R.id.et_add_mcq_answer)).getText().toString());
//                        answerChoices.setIsRight(getIsSelected((ImageView) v.findViewById(R.id.img_ans_radio)));
//                        arrListAnswerChioces.add(answerChoices);
//                    }
//                    attribute.setAnswerChoices(arrListAnswerChioces);
//                }
//
//                StringBuilder sb = new StringBuilder();
//                List<HashTagsModel> list = tagsView.getObjects();
//                for (int i = 0; i < list.size(); i++) {
//                    sb.append(list.get(i).getTagName() + ":" + list.get(i).getTagId() + ":1");
//                    sb.append(",");
//
//                }
//
//                if (listOfDeletedHashTag.size() > 0) {
//                    for (int i = 0; i < listOfDeletedHashTag.size(); i++) {
//                        sb.append(listOfDeletedHashTag.get(i).getTagName() + ":" + listOfDeletedHashTag.get(i).getTagId() + ":0");
//                        if (i < listOfDeletedHashTag.size() - 1) {
//                            sb.append(",");
//                        }
//                    }
//
//                } else {
//                    sb.substring(0, sb.toString().length() - 1);
//                }
//                Debug.e(TAG, "The HashTags Are:::" + sb.toString());
//
//                Utils.showToast("The HashTags are::" + sb.toString(), getActivity());
//
//
//                attribute.setHashtagData(sb.toString());
//                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
//                        .execute(WebConstants.TEMPCREATEQUESTION);
//
//
//            } catch (Exception e) {
//                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
//            }
//        } else {
//            Utility.toastOffline(getActivity());
//        }
//
//    }


    private void callApiTempCreateQuestion() {


        Debug.e(TAG, "The Question Text is::::" + getHtmlQuestionText());

        if (Utility.isConnected(getActivity())) {
            ((AuthorHostActivity) getActivity()).showProgress();
            try {
                Attribute attribute = new Attribute();


                MediaUploadAttribute userIdParam = new MediaUploadAttribute();
                userIdParam.setParamName("user_id");
                userIdParam.setParamValue(Global.strUserId);

                MediaUploadAttribute questionIdParam = new MediaUploadAttribute();
                questionIdParam.setParamName("question_id");

                if (getBaseFragment().getIsSetQuestionData() && !getBaseFragment().getIsCopy()) {
                    /*for edit question*/
                    questionIdParam.setParamValue(getBaseFragment().getQuestionData().getQuestionId());
                } else {
                    /*for add question*/
                    questionIdParam.setParamValue("0");
                }
                questionIdParam.setParamValue("229");


                MediaUploadAttribute questionTextParam = new MediaUploadAttribute();
                questionTextParam.setParamName("question_text");
                questionTextParam.setParamValue(getHtmlQuestionText());

                MediaUploadAttribute subjectIdParam = new MediaUploadAttribute();
                subjectIdParam.setParamName("subject_id");
                subjectIdParam.setParamValue("0");

                MediaUploadAttribute questionScoreParam = new MediaUploadAttribute();
                questionScoreParam.setParamName("question_score");
                questionScoreParam.setParamValue(arrListQuestionScore.get(spExamQuestionScore.getSelectedItemPosition()));


                MediaUploadAttribute questionFormatParam = new MediaUploadAttribute();
                questionFormatParam.setParamName("question_format");
                questionFormatParam.setParamValue(getQuestionFormat());


                MediaUploadAttribute evaluationNotesParam = new MediaUploadAttribute();
                evaluationNotesParam.setParamName("evaluation_notes");
                evaluationNotesParam.setParamValue(etEvaluationNote1.getText().toString());

                MediaUploadAttribute solutionParam = new MediaUploadAttribute();
                solutionParam.setParamName("solution");
                solutionParam.setParamValue(etSolution.getText().toString());


                MediaUploadAttribute topicIdParam = new MediaUploadAttribute();
                topicIdParam.setParamName("topic_id");
                topicIdParam.setParamValue("0");


                MediaUploadAttribute classroomIdParam = new MediaUploadAttribute();
                classroomIdParam.setParamName("classroom_id");
                classroomIdParam.setParamValue("0");

//
//                String test = "[ {\"choice_text\" :\"java1\", \"is_right\":1, \"id\":669 }," +
//                        "{\"choice_text\" : \"java3\",  \"is_right\":0,   \"id\":670},,{\"choice_text\" : \"java3\",  \"is_right\":0,  \"id\":670}]";
//
//                MediaUploadAttribute answerChoicesParam = new MediaUploadAttribute();
//                answerChoicesParam.setParamName("answer_choices");
//                answerChoicesParam.setParamValue(test);
//                attribute.getArrListParam().add(answerChoicesParam);

//                if (getQuestionFormat().equalsIgnoreCase(getString(R.string.strquestionformatmcq))) {
//                    getMcqAnswers();
//                    answerChoicesParam.setParamName("answer_choices");
//                    answerChoicesParam.setArrListMcqAnswerValue(arrListAnswerChioces);
//                    attribute.getArrListParam().add(answerChoicesParam);
//                }


                MediaUploadAttribute hashTagParam = new MediaUploadAttribute();
                hashTagParam.setParamName("hashtag_data");
                hashTagParam.setParamValue(getHashTagData());


                MediaUploadAttribute numberOfImagesParam = new MediaUploadAttribute();
                numberOfImagesParam.setParamName("number_of_images");
                numberOfImagesParam.setParamValue(String.valueOf(imageSources.size()));


                MediaUploadAttribute htmlTextParam = new MediaUploadAttribute();
                htmlTextParam.setParamName("html_text");
                htmlTextParam.setParamValue(getHtmlQuestionText());


                MediaUploadAttribute bookParam = new MediaUploadAttribute();
                bookParam.setParamName("book_id");
                bookParam.setParamValue(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_BOOK_ID));


                attribute.getArrListParam().add(userIdParam);
                attribute.getArrListParam().add(questionIdParam);
                attribute.getArrListParam().add(questionTextParam);
                attribute.getArrListParam().add(subjectIdParam);
                attribute.getArrListParam().add(questionScoreParam);
                attribute.getArrListParam().add(questionFormatParam);
                attribute.getArrListParam().add(evaluationNotesParam);
                attribute.getArrListParam().add(solutionParam);
                attribute.getArrListParam().add(topicIdParam);
                attribute.getArrListParam().add(classroomIdParam);
                attribute.getArrListParam().add(hashTagParam);
                attribute.getArrListParam().add(numberOfImagesParam);
                attribute.getArrListParam().add(htmlTextParam);
                attribute.getArrListParam().add(bookParam);


                addImage();
                if (imageSources.size() > 0) {
                    for (int i = 0; i < imageSources.size(); i++) {
                        MediaUploadAttribute mediaFileParam = new MediaUploadAttribute();
                        mediaFileParam.setParamName("" + i);
                        mediaFileParam.setFileName(imageSources.get(i));
                        attribute.getArrListFile().add(mediaFileParam);
                    }
                }


                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.TEMPCREATEQUESTION);
            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }

    private void getMcqAnswers() {
        arrListAnswerChioces.clear();
        for (int i = 0; i < llAddMcqanswer.getChildCount(); i++) {
            View v = llAddMcqanswer.getChildAt(i);
            AnswerChoices answerChoices = new AnswerChoices();
            answerChoices.setChoiceText(((EditText) v.findViewById(R.id.et_add_mcq_answer)).getText().toString());
            answerChoices.setIsRight(getIsSelected((ImageView) v.findViewById(R.id.img_ans_radio)));
            arrListAnswerChioces.add(answerChoices);
        }

    }

    private String getHashTagData() {
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
        return sb.toString();
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


    private void callApiUploadMediaForQuestion(String questionId, String mediaType, String fileName) {
//        new MediaUploader(getActivity()).new MediaUploaderCaller().execute(fileName);
        if (Utility.isConnected(getActivity())) {
            ((AuthorHostActivity) getActivity()).showProgress();
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

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.TEMPCREATEQUESTION:
                    onReponseCreateQuestion(object, error);
                    break;

                case WebConstants.GETALLHASHTAG:
                    onResponseGetAllHashTag(object, error);
                    break;

                case WebConstants.UPLOADMEDIAFORQUESTION:
                    onResponseUploadMediaForQuestion(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }


    private ArrayList<HashTags> arrListTags = new ArrayList<HashTags>();

    ResponseHandler responseHandlerCreateQuestion;

    private void onReponseCreateQuestion(Object object, Exception error) {

        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                responseHandlerCreateQuestion = (ResponseHandler) object;
                if (responseHandlerCreateQuestion.getStatus().equals(ResponseHandler.SUCCESS)) {

                    Debug.e(TAG, "The Question Id Is::" + responseHandlerCreateQuestion.getQuestion().get(0).getQuestionId());
                    if (getBaseFragment().getIsSetQuestionData() && !getBaseFragment().getIsCopy()) {

                        Utils.showToast(getString(R.string.question_edit_success), getActivity());

                        getBaseFragment().setQuestionDataAfterEditQuestion(getBaseFragment().getQuestionData(),
                                makeQuestionData(responseHandlerCreateQuestion.getQuestion().get(0).getQuestionId(), ""),
                                chkAddquestionPreview.isChecked());
                    } else {

                        Utils.showToast(getString(R.string.question_add_success), getActivity());

                        getBaseFragment().addQuestionDataAfterAddQuestion(makeQuestionData(responseHandlerCreateQuestion.getQuestion().get(0).getQuestionId(), ""),
                                chkAddquestionPreview.isChecked());

                    }
                    if (isAddMore) {
                        clearViewsData();
                    }

                    if (selectedUri != null) {
                        Debug.e(TAG, "Thefile path is:" + Utility.getRealPathFromURI(selectedUri, getActivity()));
                        callApiUploadMediaForQuestion(responseHandlerCreateQuestion.getQuestion().get(0).getQuestionId(), mediaType,
                                Utility.getRealPathFromURI(selectedUri, getActivity()));
                    }
                } else if (responseHandlerCreateQuestion.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandlerCreateQuestion.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseCreateQuestions api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseCreateQuestions Exception : " + e.toString());
        }
    }

    private void onResponseUploadMediaForQuestion(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    Debug.e(TAG, "The Image url is::" + responseHandler.getFileUploadResponse().getImageLink() + " question id is: " +
                            responseHandler.getFileUploadResponse().getQuestion_id());
                    Utils.showToast(getString(R.string.msg_success_imgupload_question), getActivity());

                    getBaseFragment().setQuestionDataAfterEditQuestion(getBaseFragment().getQuestionData(),
                            makeQuestionData(responseHandler.getFileUploadResponse().getQuestion_id(), responseHandler.getFileUploadResponse().getImageLink()),
                            chkAddquestionPreview.isChecked());

//                    } else {
//                        getBaseFragment().addQuestionDataAfterAddQuestion(makeQuestionData(responseHandler.getFileUploadResponse().getQuestion_id(),
//                                        responseHandler.getFileUploadResponse().getImageLink()),
//                                chkAddquestionPreview.isChecked());
//
//                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseUploadMediaForQuestion api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseUploadMediaForQuestion Exception : " + e.toString());
        }
    }

    private Questions makeQuestionData(String questionId, String imageLink) {
        Questions question = new Questions();
        try {
            question.setQuestionId(questionId);
            question.setQuestionCreatorName(Global.strFullName);
            question.setQuestionCreatorId(Global.strUserId);
            question.setQuestionFormat(getQuestionFormat());
            question.setQuestionText(responseHandlerCreateQuestion.getQuestion().get(0).getRichTextEditorImages());


            question.setQuestionAssetsLink("");
            if (imageLink != null && !imageLink.equals("")) {
                question.setQuestionImageLink(imageLink);
            } else {
                question.setQuestionImageLink("");

            }
            question.setEvaluationNotes(etEvaluationNote1.getText().toString());
            question.setSolution(etSolution.getText().toString());
            if (getBaseFragment().getBundleArguments() != null) {
                question.setTopicId("0");
                question.setSubjectId("0");
                question.setSubjectName("");
                question.setClassroomId(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_CLASSROOM_ID));
                question.setBookId(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_BOOK_ID));
                question.setBookName(getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_BOOK_NAME));
                question.setQuestionScore(arrListQuestionScore.get(spExamQuestionScore.getSelectedItemPosition()));
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
                    setHashTagsList();

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

    /**
     * method to setup hashtag list data
     */
    private void setHashTagsList() {
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
    }


    public void updateAddToPreviewCheckBoxStatus() {
        chkAddquestionPreview.setEnabled(true);
        chkAddquestionPreview.setChecked(false);
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

    @Override
    public void ImagePicker() {
        openImage();
    }

    @Override
    public void VideoPicker() {
        openVideo();
    }

    @Override
    public void SetText(String text) {
        htmlText = text;
        richEditText = text;
        etAddquestionTitle.setText(Html.fromHtml(text, new HtmlImageGetter(50, 50), null));

        Debug.e(TAG, "RichEditor text is:::::::::::" + text);
        Debug.e(TAG, "Text Of Edittext is:::::::::::" + Html.toHtml(etAddquestionTitle.getText()));

    }


    private void getQuestionScoreSpinnerValues() {
        arrListQuestionScore = new ArrayList<String>();
        arrListQuestionScore.add(Utility.getString(R.string.strquestionscore, getActivity()));
        for (int i = QUESTIONSCORE_STARTVALUE; i <= QUESTIONSCORE_ENDVALUE; i += QUESTIONSCORE_INERVAL) {
            arrListQuestionScore.add(String.valueOf(i));
        }
    }


    String htmlText;
    String richEditText;
    ArrayList<String> imageSources = new ArrayList<String>();


    public void addImage() {

        Debug.e(TAG, "The RichEditText Is:::" + richEditText);

        String richEditText;
        imageSources.clear();
        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();

            XmlPullParser xpp = factory.newPullParser();
            richEditText = getHtmlQuestionText().replace("&nbsp;", " ");
            xpp.setInput(new StringReader(richEditText));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG && "img".equals(xpp.getName())) {
                    //found an image start tag, extract the attribute 'src' from here..

                    if (xpp.getAttributeValue(0).contains("file://")) {
                        String path = xpp.getAttributeValue(0).replace("file://", "");
                        imageSources.add(path);
                    }
                }
                eventType = xpp.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private AddQuestionContainerFragment getBaseFragment() {
        return (AddQuestionContainerFragment) mFragment;
    }


    //intiGlobalEnds


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (data != null) {
//            selectedUri = data.getData();
//
//            String[] columns = {MediaStore.Images.Media.DATA,
//                    MediaStore.Images.Media.MIME_TYPE};
//
//            Cursor cursor = getActivity().getContentResolver().query(selectedUri, columns, null, null, null);
//            cursor.moveToFirst();
//
//            int pathColumnIndex = cursor.getColumnIndex(columns[0]);
//            int mimeTypeColumnIndex = cursor.getColumnIndex(columns[1]);
//
//            String contentPath = cursor.getString(pathColumnIndex);
//            String mimeType = cursor.getString(mimeTypeColumnIndex);
//            cursor.close();
//
//            if (mimeType.startsWith("image")) {
//
//                imgSelectImage.setImageURI(selectedUri);
//                imgPlay.setVisibility(View.GONE);
//
//            } else if (mimeType.startsWith("video")) {
//
//                MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
//                mMediaMetadataRetriever.setDataSource(getActivity(), selectedUri);
//                Bitmap bitmap = mMediaMetadataRetriever.getFrameAtTime(1 * 1000);
//                imgSelectImage.setImageBitmap(bitmap);
//                imgPlay.setVisibility(View.VISIBLE);
//
//            }
//
//        }
//    }
}

