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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ism.teacher.model.AnswersModel;
import com.ism.teacher.model.Data;
import com.ism.teacher.model.TagsModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by c166 on 31/10/15.
 */
public class QuestionAddEditFragment extends Fragment implements TokenCompleteTextView.TokenListener, View.OnClickListener {

    private static final String TAG = QuestionAddEditFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;
    private static final String ARG_FRAGMENT = "fragment";

    //Views
    private EditText etQuestionTitle, etAnswerBox, etEvaluationNotes, etSolution;
    private ImageView imgEditQuestion, imgCopyQuestion, imgDeleteQuestion;
    private Spinner spQuestionType;
    private Button btnSaveQuestion, btnSaveNAddmoreQuestion;
    private CheckBox chkAddQuestionToPreview;

    private List<String> arrayListQuestionType;

    private LinearLayout llAddMcqanswer;

    RelativeLayout rl_image;
    ImageView img_add_pics, img_play;

    //Add Tag Functionality
    private TagsModel[] tags;
    private ArrayAdapter<TagsModel> tagsAdapter;
    private ContactsCompletionView completionView;

    ArrayList<Data> arrayListQuestion = new ArrayList<>();

    Data objData = new Data();

    public static final String QUESTION_TYPE_TEXT = "Fill ups";
    public static final String QUESTION_TYPE_MCQ = "MCQ";
    public static final String QUESTION_TYPE_PARAGRAPH = "descriptive";

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

        completionView = (ContactsCompletionView) view.findViewById(R.id.searchTagView);
        completionView.setAdapter(tagsAdapter);
        completionView.setTokenListener(this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);


        rl_image = (RelativeLayout) view.findViewById(R.id.rl_image);
        etQuestionTitle = (EditText) view.findViewById(R.id.et_question_title);
        etAnswerBox = (EditText) view.findViewById(R.id.et_answer_box);
        etEvaluationNotes = (EditText) view.findViewById(R.id.et_evaluation_notes);
        etSolution = (EditText) view.findViewById(R.id.et_solution);

        img_add_pics = (ImageView) view.findViewById(R.id.img_add_pics);
        img_play = (ImageView) view.findViewById(R.id.img_play);

        imgEditQuestion = (ImageView) view.findViewById(R.id.img_edit_question);
        imgCopyQuestion = (ImageView) view.findViewById(R.id.img_copy_question);
        imgDeleteQuestion = (ImageView) view.findViewById(R.id.img_delete_question);

        spQuestionType = (Spinner) view.findViewById(R.id.sp_question_type);
        chkAddQuestionToPreview = (CheckBox) view.findViewById(R.id.chk_add_to_preview);

        btnSaveQuestion = (Button) view.findViewById(R.id.btn_save_question);
        btnSaveNAddmoreQuestion = (Button) view.findViewById(R.id.btn_save_n_addmore_question);

        arrayListQuestionType = Arrays.asList(getResources().getStringArray(R.array.question_type));
        Adapters.setUpSpinner(getActivity(), spQuestionType, arrayListQuestionType, Adapters.ADAPTER_SMALL);

        llAddMcqanswer = (LinearLayout) view.findViewById(R.id.ll_add_mcqanswer);

        btnSaveQuestion.setOnClickListener(this);
        btnSaveNAddmoreQuestion.setOnClickListener(this);


        rl_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("*/*");
                startActivityForResult(intent, 1);
            }
        });

        for (int i = 0; i <= 1; i++) {
            llAddMcqanswer.addView(getMcqAnswerView(i));
        }


    }


    /*these is for set question questionData for copy and edit question.*/
    private ImageLoader imageLoader;

    public void setQuestionData(Data data) {

        Utility.showToast("SETDATACALLED", getActivity());

        try {
            if (data != null) {
                objData = data;

                if (data.getQuestionText() != null) {
                    etQuestionTitle.setText(Utility.formatHtml(data.getQuestionText()));
                }
                //  imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", img_add_pics, ISMTeacher.options);
                setSpinnerData(data.getQuestionFormat());

                if (data.getAnswers().size() > 0) {
                    loadMCQOptions(data.getAnswers());
                }

            }


        } catch (Exception e) {
            Debug.e(TAG, "isSetQuestionData Exception : " + e.toString());
        }


    }

    private void setSpinnerData(String questionType) {

        if (questionType.equalsIgnoreCase(QUESTION_TYPE_MCQ)) {

            spQuestionType.setSelection(2);
            etAnswerBox.setVisibility(View.GONE);


        } else if (questionType.equalsIgnoreCase(QUESTION_TYPE_TEXT)) {

            spQuestionType.setSelection(0);

        } else if (questionType.equalsIgnoreCase(QUESTION_TYPE_PARAGRAPH)) {

            spQuestionType.setSelection(1);

        }


    }


    private void loadMCQOptions(ArrayList<AnswersModel> arrayListAnswers) {
        for (int count = 0; count < arrayListAnswers.size(); count++) {
            llAddMcqanswer.addView(getMcqTestAnswerView(arrayListAnswers, count));
        }

    }


    private View getMcqTestAnswerView(ArrayList<AnswersModel> arrayListAnswers, int count) {
        View v = null;
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        v = layoutInflater.inflate(R.layout.row_mcq_answer, null, false);

        ImageView imgAnsRadio = (ImageView) v.findViewById(R.id.img_ans_radio);
        EditText etAddMcqAnswer = (EditText) v.findViewById(R.id.et_add_mcq_answer);
        final ImageView imgAddMcqRow = (ImageView) v.findViewById(R.id.img_add_mcq_row);
        final ImageView imgRemoveMcqRow = (ImageView) v.findViewById(R.id.img_remove_mcq_row);

        if (count >= arrayListAnswers.size()) {
            etAddMcqAnswer.setText("");
            imgRemoveMcqRow.setVisibility(View.VISIBLE);

        } else {
            etAddMcqAnswer.setText(arrayListAnswers.get(count).getChoiceText());
            imgRemoveMcqRow.setVisibility(View.GONE);
            Log.e("option value", "" + arrayListAnswers.get(count).getChoiceText());

        }

        if (count > 0) {
            imgAddMcqRow.setVisibility(View.VISIBLE);
        } else {
            imgAddMcqRow.setVisibility(View.GONE);
        }
        imgRemoveMcqRow.setTag(count);
        imgAddMcqRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int viewPositionToAdd = (Integer) imgRemoveMcqRow.getTag();
                imgAddMcqRow.setVisibility(View.GONE);
                imgRemoveMcqRow.setVisibility(View.VISIBLE);
//                imgRemoveMcqRow.setVisibility(View.VISIBLE);

                if (llAddMcqanswer.getChildCount() < 6) {
                    llAddMcqanswer.addView(getMcqTestAnswerView(objData.getAnswers(), viewPositionToAdd + 1));
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

        ImageView imgAnsRadio = (ImageView) v.findViewById(R.id.img_ans_radio);
        EditText etAddMcqAnswer = (EditText) v.findViewById(R.id.et_add_mcq_answer);
        final ImageView imgAddMcqRow = (ImageView) v.findViewById(R.id.img_add_mcq_row);
        final ImageView imgRemoveMcqRow = (ImageView) v.findViewById(R.id.img_remove_mcq_row);

        etAddMcqAnswer.setText("Ans:::" + position);
        if (position > 0) {
            imgAddMcqRow.setVisibility(View.VISIBLE);
        } else {
            imgAddMcqRow.setVisibility(View.GONE);
        }
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


    private void refreshViewPositions() {
        for (int i = 0; i < llAddMcqanswer.getChildCount(); i++) {
            View childView = llAddMcqanswer.getChildAt(i);
            EditText etAddMcqAnswer = (EditText) childView.findViewById(R.id.et_add_mcq_answer);
            ImageView imgRemoveMcqRow = (ImageView) childView.findViewById(R.id.img_remove_mcq_row);
            // etAddMcqAnswer.setText("Ans:::" + i);
            imgRemoveMcqRow.setTag(i);
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

    private void updateTokenConfirmation() {
        StringBuilder sb = new StringBuilder("Current tokens:\n");
        for (Object token : completionView.getObjects()) {
            sb.append(token.toString());
            sb.append("\n");
        }
        Utility.showToast("The Tokens Are::::" + sb, getActivity());
    }


    @Override
    public void onClick(View view) {
        if (view == btnSaveQuestion) {
            ((AddQuestionContainerFragment) mFragment).flipCard();
        }
    }
}
