package com.ism.teacher.fragments;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.autocomplete.ContactsCompletionView;
import com.ism.teacher.autocomplete.FilteredArrayAdapter;
import com.ism.teacher.autocomplete.TokenCompleteTextView;
import com.ism.teacher.helper.InputValidator;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.model.TagsModel;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.model.Answers;
import com.ism.teacher.ws.model.QuestionBank;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by c166 on 31/10/15.
 */
public class QuestionAddEditFragment extends Fragment implements TokenCompleteTextView.TokenListener, View.OnClickListener {

    private static final String TAG = QuestionAddEditFragment.class.getSimpleName();

    Fragment mFragment;
    private static final String ARG_FRAGMENT = "fragment";
    public static final String QUESTION_TYPE_TEXT = "Fill ups";
    public static final String QUESTION_TYPE_MCQ = "MCQ";
    public static final String QUESTION_TYPE_PARAGRAPH = "descriptive";


    //Views
    private View view;
    private EditText etQuestionTitle, etAnswerBox, etEvaluationNotes, etSolution;
    private ImageView imgEditQuestion, imgCopyQuestion, imgDeleteQuestion, img_add_pics, img_play;
    private Spinner spQuestionType;
    private Button btnSaveQuestion, btnSaveNAddmoreQuestion;
    private CheckBox chkAddQuestionToPreview;
    private LinearLayout llAddMcqanswer;
    private RelativeLayout rl_image;
    private TextView tvAddquestionGotoquestionbank;

    //Add Tag Functionality
    private TagsModel[] tags;
    private ArrayAdapter<TagsModel> tagsAdapter;
    private ContactsCompletionView tagsView;


    //List
    private List<String> arrayListQuestionType = new ArrayList<>();

    //Objects
    MyTypeFace myTypeFace;
    QuestionBank objData = new QuestionBank();
    private ImageLoader imageLoader;
    private InputValidator inputValidator;

    public QuestionAddEditFragment(Fragment fragment) {
        this.mFragment = fragment;
    }


    public QuestionAddEditFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question_addedit, container, false);

        initGlobal();

        Utility.showToast("THE QUESTION ADD EDIT FRAGMENT CALLED", getActivity());
        return view;

    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());
        inputValidator = new InputValidator(getActivity());
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

        tags = new TagsModel[]{
                new TagsModel("Physics", "1"),
                new TagsModel("Biology", "2"),
                new TagsModel("Chemistry", "3"),
                new TagsModel("Maths", "4"),
                new TagsModel("Social Science", "5"),
                new TagsModel("Drawing", "6")
        };

        tagsAdapter = new FilteredArrayAdapter<TagsModel>(getActivity(), R.layout.tag_search_layout, tags) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {

                    LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.tag_search_layout, parent, false);
                }

                TagsModel tagModel = getItem(position);

                ((TextView) convertView.findViewById(R.id.tv_tag_name)).setText(tagModel.getTagName());

                return convertView;
            }

            @Override
            protected boolean keepObject(TagsModel tagModel, String mask) {
                mask = mask.toLowerCase();
                return tagModel.getTagName().toLowerCase().startsWith(mask);
            }
        };

        tagsView = (ContactsCompletionView) view.findViewById(R.id.searchTagView);
        tagsView.setAdapter(tagsAdapter);
        tagsView.setTokenListener(this);
        tagsView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);


        rl_image = (RelativeLayout) view.findViewById(R.id.rl_image);
        rl_image.setOnClickListener(this);
        etQuestionTitle = (EditText) view.findViewById(R.id.et_question_title);
        etAnswerBox = (EditText) view.findViewById(R.id.et_answer_box);
        etEvaluationNotes = (EditText) view.findViewById(R.id.et_evaluation_notes);
        etSolution = (EditText) view.findViewById(R.id.et_solution);

        img_add_pics = (ImageView) view.findViewById(R.id.img_add_pics);
        img_play = (ImageView) view.findViewById(R.id.img_play);

        imgEditQuestion = (ImageView) view.findViewById(R.id.img_edit_question);
        imgCopyQuestion = (ImageView) view.findViewById(R.id.img_copy_question);
        imgDeleteQuestion = (ImageView) view.findViewById(R.id.img_delete_question);

        tvAddquestionGotoquestionbank = (TextView) view.findViewById(R.id.tv_addquestion_gotoquestionbank);
        tvAddquestionGotoquestionbank.setOnClickListener(this);

        spQuestionType = (Spinner) view.findViewById(R.id.sp_question_type);
        chkAddQuestionToPreview = (CheckBox) view.findViewById(R.id.chk_add_to_preview);

        btnSaveQuestion = (Button) view.findViewById(R.id.btn_save_question);
        btnSaveNAddmoreQuestion = (Button) view.findViewById(R.id.btn_save_n_addmore_question);

        arrayListQuestionType.add(getString(R.string.strquestiontype));
        arrayListQuestionType = Arrays.asList(getResources().getStringArray(R.array.question_type));
        Adapters.setUpSpinner(getActivity(), spQuestionType, arrayListQuestionType, Adapters.ADAPTER_SMALL);

        llAddMcqanswer = (LinearLayout) view.findViewById(R.id.ll_add_mcqanswer);

        btnSaveQuestion.setOnClickListener(this);
        btnSaveNAddmoreQuestion.setOnClickListener(this);


        for (int i = 0; i <= 1; i++) {
            llAddMcqanswer.addView(getMcqAnswerView(i));
        }


        spQuestionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    llAddMcqanswer.setVisibility(View.GONE);
                    etAnswerBox.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    llAddMcqanswer.setVisibility(View.GONE);
                    etAnswerBox.setVisibility(View.VISIBLE);
                } else if (position == 3) {
                    llAddMcqanswer.setVisibility(View.VISIBLE);
                    etAnswerBox.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void setViewForAddEditQuestion() {

        if (getFragment().getIsSetQuestionData()) {
            setQuestionData(getFragment().getQuestionData());
        } else {
            clearViewsData();
            for (int i = 0; i <= 1; i++) {
                llAddMcqanswer.addView(getMcqAnswerView(i));
            }
        }

    }

    private void clearViewsData() {

        etQuestionTitle.setText("");
        spQuestionType.setSelection(1);
        etAnswerBox.setText("");
        llAddMcqanswer.removeAllViews();
        tagsView.clear();
        etEvaluationNotes.setText("");
        etSolution.setText("");

    }


    /*these is for set question questionData for copy and edit question.*/

    public void setQuestionData(QuestionBank data) {

        Utility.showToast("SETDATACALLED", getActivity());

        try {
            if (data != null) {
                objData = data;

                if (data.getQuestionText() != null) {
                    etQuestionTitle.setText(Utility.formatHtml(data.getQuestionText()));
                }
                //  imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", img_add_pics, ISMTeacher.options);
                setSpinnerData(data.getQuestionFormat());
                setMcqAnswers(data);
                if (data.getEvaluationNotes() != null) {
                    etEvaluationNotes.setText(data.getEvaluationNotes());
                    etSolution.setText(data.getEvaluationNotes());
                }

            }


        } catch (Exception e) {
            Debug.e(TAG, "isSetQuestionData Exception : " + e.toString());
        }

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
            Utility.showToast(getActivity().getResources().getString(R.string.msg_mcq_ans_limit), getActivity());
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

    private void setSpinnerData(String questionType) {
        if (questionType.equalsIgnoreCase(QUESTION_TYPE_MCQ)) {
            spQuestionType.setSelection(3);
            questionQuestionAnswersModelArrayList.clear();
            llAddMcqanswer.removeAllViews();
            llAddMcqanswer.setVisibility(View.VISIBLE);
            etAnswerBox.setVisibility(View.GONE);
        } else if (questionType.equalsIgnoreCase(QUESTION_TYPE_PARAGRAPH)) {
            spQuestionType.setSelection(1);
            llAddMcqanswer.setVisibility(View.GONE);
            etAnswerBox.setVisibility(View.VISIBLE);
        } else if (questionType.equalsIgnoreCase(QUESTION_TYPE_TEXT)) {
            spQuestionType.setSelection(2);
            llAddMcqanswer.setVisibility(View.GONE);
            etAnswerBox.setVisibility(View.VISIBLE);
        }
    }


    ArrayList<Answers> questionQuestionAnswersModelArrayList = new ArrayList<>();

    private void setMcqAnswers(QuestionBank data) {
        questionQuestionAnswersModelArrayList.addAll(data.getAnswers());
        for (int i = 0; i < data.getAnswers().size(); i++) {
            llAddMcqanswer.addView(setMCQ(i, data.getAnswers().get(i).getChoiceText(), data.getAnswers().get(i).getIsRight().equals("1") ? true : false));
        }
    }

    /*this method is for inflating views for edit and copy question*/
    private View setMCQ(int position, String text, Boolean isActivated) {
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
        } else if (position == questionQuestionAnswersModelArrayList.size() - 1) {
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


    /**
     * Add New Question Functionality
     */


    //intiGlobalEnds

    Uri selectedUri = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            selectedUri = data.getData();
            //Do whatever that you desire here. or leave this blank
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
                img_add_pics.setImageURI(selectedUri);
                Utility.showToast("Image", getActivity());

            } else if (mimeType.startsWith("video")) {

                MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
                mMediaMetadataRetriever.setDataSource(getActivity(), selectedUri);
                Bitmap bitmap = mMediaMetadataRetriever.getFrameAtTime(1 * 1000);

                img_add_pics.setImageBitmap(bitmap);
                img_play.setVisibility(View.VISIBLE);

            }

        }
    }


    private View getMcqAnswerView(final int position) {
        View v = null;
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        v = layoutInflater.inflate(R.layout.row_mcq_answer, null, false);

        final ImageView imgAnsRadio = (ImageView) v.findViewById(R.id.img_ans_radio);
        EditText etAddMcqAnswer = (EditText) v.findViewById(R.id.et_add_mcq_answer);
        final ImageView imgAddMcqRow = (ImageView) v.findViewById(R.id.img_add_mcq_row);
        final ImageView imgRemoveMcqRow = (ImageView) v.findViewById(R.id.img_remove_mcq_row);

        etAddMcqAnswer.setText("Ans:::" + position);
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
                int viewPositionToAdd = (Integer) imgRemoveMcqRow.getTag();
                imgAddMcqRow.setVisibility(View.GONE);
                imgRemoveMcqRow.setVisibility(View.VISIBLE);

                if (llAddMcqanswer.getChildCount() < 6) {
                    llAddMcqanswer.addView(getMcqAnswerView(viewPositionToAdd + 1));
                } else {

                }
            }
        });

        imgRemoveMcqRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int viewPositionToRemove = (Integer) imgRemoveMcqRow.getTag();
                llAddMcqanswer.removeViewAt(viewPositionToRemove);
                refreshViewPositions();

            }
        });

        return v;
    }


    @Override
    public void onTokenAdded(Object token) {
        updateTokenConfirmation();
    }

    @Override
    public void onTokenRemoved(Object token) {
        updateTokenConfirmation();
    }

    private void updateTokenConfirmation() {
        StringBuilder sb = new StringBuilder("Current tokens:\n");
        for (Object token : tagsView.getObjects()) {
            sb.append(token.toString());
            sb.append("\n");
        }
        Utility.showToast("The Tokens Are::::" + sb, getActivity());
    }

    private Boolean isInputsValid() {

        return inputValidator.validateStringPresence(etQuestionTitle) & inputValidator.validateStringPresence(etEvaluationNotes)
                & inputValidator.validateStringPresence(tagsView)
                & inputValidator.validateStringPresence(etSolution) && checkSpinnerDataValidation();

    }


    private Boolean checkSpinnerDataValidation() {

        int spPosition = spQuestionType.getSelectedItemPosition();
        Boolean isValidate = false;
        switch (spPosition) {
            case 1:
                isValidate = inputValidator.validateStringPresence(etAnswerBox);
                break;
            case 2:
                isValidate = inputValidator.validateStringPresence(etAnswerBox);
                break;
            case 3:
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
        if (v == btnSaveQuestion) {
            if (isInputsValid()) {
                callApiAddQuestion();
            }


        } else if (v == btnSaveNAddmoreQuestion) {
            if (isInputsValid()) {

                callApiAddQuestion();
            }
            clearViewsData();


        } else if (v == rl_image) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("*/*");
            startActivityForResult(intent, 1);

        } else if (v == tvAddquestionGotoquestionbank) {

            getFragment().flipCard();


        }

    }

    private void callApiAddQuestion() {

        if (Utility.isOnline(getActivity())) {
            try {
                Attribute attribute = new Attribute();
//                requestObject.setBankQuestionId("2");
                attribute.setQuestionCreatorId("109");
                attribute.setQuestionCreatorName("");
                attribute.setQuestionFormat(getQuestionFormat());
                attribute.setQuestionHint("test");
                attribute.setQuestionText("test");
                attribute.setQuestionAssetsLink("");
                attribute.setQuestionImageLink("");
                attribute.setEvaluationNotes(etEvaluationNotes.getText().toString());
                attribute.setSolution(etSolution.getText().toString());
                attribute.setTopicId("43");
                attribute.setSubjectId("3");
                attribute.setSubjectName("MatheMatics");
                attribute.setClassroomId("2");
                attribute.setBookId("2");
                if (getQuestionFormat().equalsIgnoreCase("mcq")) {
                    questionQuestionAnswersModelArrayList.clear();
                    for (int i = 0; i < llAddMcqanswer.getChildCount(); i++) {
                        View v = llAddMcqanswer.getChildAt(i);
                        Answers questionQuestionAnswersModel = new Answers();
                        questionQuestionAnswersModel.setQuestionId("2");
                        questionQuestionAnswersModel.setChoiceText(((EditText) v.findViewById(R.id.et_add_mcq_answer)).getText().toString());
                        questionQuestionAnswersModel.setIsRight(getIsSelected((ImageView) v.findViewById(R.id.img_ans_radio)));
                        questionQuestionAnswersModel.setImageLink("");
                        questionQuestionAnswersModel.setVideoLink("");
                        questionQuestionAnswersModel.setAudioLink("");
                        questionQuestionAnswersModelArrayList.add(questionQuestionAnswersModel);
                    }
                    attribute.setAnswers(questionQuestionAnswersModelArrayList);
                }
//                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
//                        .execute(WebConstants.GETALLFEEDS);

                if (getFragment().getIsSetQuestionData()) {

                    getFragment().setQuestionDataAfterEditQuestion();

                } else {

                }

            } catch (Exception e) {
                Debug.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }

    }


    private String getQuestionFormat() {

        String questionFormat = "";
        int spPosition = spQuestionType.getSelectedItemPosition();
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


}
