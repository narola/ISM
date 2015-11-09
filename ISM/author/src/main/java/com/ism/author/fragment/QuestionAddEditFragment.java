package com.ism.author.fragment;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utils;
import com.ism.author.adapter.Adapters;
import com.ism.author.autocomplete.ContactsCompletionView;
import com.ism.author.autocomplete.FilteredArrayAdapter;
import com.ism.author.autocomplete.TokenCompleteTextView;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.Data;
import com.ism.author.model.QuestionAnswersModel;
import com.ism.author.model.TagsModel;
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
    private View view;
    Fragment mFragment;


    public QuestionAddEditFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    /*these is for the tag add functionality.*/
    private ContactsCompletionView tagsView;
    private TagsModel[] tags;
    private ArrayAdapter<TagsModel> tagsAdapter;

    /*these sre for the xml views*/

    private TextView tvAddquestionHeader, tvAddquestionTitle, tvAddquestionType, tvAddquestionCategory, tvEvaluationNote1, tvEvaluationNote2,
            tvAddquestionSave, tvAddquestionSaveAddmore;
    private ImageView imgEditQuestion, imgCopyQuestion, imgDeleteQuestion, imgSelectImage, imgPlay;
    private EditText etAddquestionTitle, etAddquestionAnswer, etEvaluationNote1, etEvaluationNote2;
    private Spinner spAddquestionType;
    private CheckBox chkAddquestionPreview;
    List<String> arrListQuestionType;
    private LinearLayout llAddMcqanswer;

    MyTypeFace myTypeFace;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question_addedit, container, false);
        Utils.showToast("THE QUESTION ADD EDIT FRAGMENT CALLED", getActivity());
        initGlobal();
        return view;
    }

    private void initGlobal() {

        myTypeFace = new MyTypeFace(getActivity());
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        //this is for to set tag..
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


        tvAddquestionHeader = (TextView) view.findViewById(R.id.tv_addquestion_header);
        tvAddquestionTitle = (TextView) view.findViewById(R.id.tv_addquestion_title);
        tvAddquestionType = (TextView) view.findViewById(R.id.tv_addquestion_type);
        tvAddquestionCategory = (TextView) view.findViewById(R.id.tv_addquestion_category);
        tvEvaluationNote1 = (TextView) view.findViewById(R.id.tv_evaluation_note1);
        tvEvaluationNote2 = (TextView) view.findViewById(R.id.tv_evaluation_note2);
        tvAddquestionSave = (TextView) view.findViewById(R.id.tv_addquestion_save);
        tvAddquestionSaveAddmore = (TextView) view.findViewById(R.id.tv_addquestion_save_addmore);
        tvAddquestionSave.setOnClickListener(this);
        tvAddquestionSaveAddmore.setOnClickListener(this);


        tvAddquestionHeader.setTypeface(myTypeFace.getRalewayRegular());
        tvAddquestionTitle.setTypeface(myTypeFace.getRalewayBold());
        tvAddquestionType.setTypeface(myTypeFace.getRalewayBold());
        tvAddquestionCategory.setTypeface(myTypeFace.getRalewayBold());
        tvEvaluationNote1.setTypeface(myTypeFace.getRalewayRegular());
        tvEvaluationNote2.setTypeface(myTypeFace.getRalewayRegular());
        tvAddquestionSave.setTypeface(myTypeFace.getRalewayRegular());
        tvAddquestionSaveAddmore.setTypeface(myTypeFace.getRalewayRegular());


        imgEditQuestion = (ImageView) view.findViewById(R.id.img_edit_question);
        imgCopyQuestion = (ImageView) view.findViewById(R.id.img_copy_question);
        imgDeleteQuestion = (ImageView) view.findViewById(R.id.img_delete_question);
        imgSelectImage = (ImageView) view.findViewById(R.id.img_select_image);
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
            sb.append(token.toString());
            sb.append("\n");
        }
        Utils.showToast("The Tokens Are::::" + sb, getActivity());
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

        if (((AddQuestionContainerFragment) mFragment).getIsSetQuestionData()) {
            setQuestionData(((AddQuestionContainerFragment) mFragment).getQuestionData());
        } else {
            clearViewsData();
            for (int i = 0; i <= 1; i++) {
                llAddMcqanswer.addView(getMcqAnswerView(i));
            }
        }

    }

    private void clearViewsData() {

        etAddquestionTitle.setText("");
        spAddquestionType.setSelection(1);
        etAddquestionAnswer.setText("");
        llAddMcqanswer.removeAllViews();
        tagsView.clear();
        tvEvaluationNote1.setText("");
        tvEvaluationNote2.setText("");

    }

    /*this method is for inflating views for addquestion*/
    private View getMcqAnswerView(final int position) {
        View v = null;
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        v = layoutInflater.inflate(R.layout.row_mcq_answer, null, false);

        ImageView imgAnsRadio = (ImageView) v.findViewById(R.id.img_ans_radio);
        EditText etAddMcqAnswer = (EditText) v.findViewById(R.id.et_add_mcq_answer);
        final ImageView imgAddMcqRow = (ImageView) v.findViewById(R.id.img_add_mcq_row);
        final ImageView imgRemoveMcqRow = (ImageView) v.findViewById(R.id.img_remove_mcq_row);
        if (position > 0) {
            imgAddMcqRow.setVisibility(View.VISIBLE);
        } else {
            imgAddMcqRow.setVisibility(View.GONE);
        }
        imgRemoveMcqRow.setTag(position);
        imgAddMcqRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (llAddMcqanswer.getChildCount() < 6) {
//                    int viewPositionToAdd = (Integer) imgRemoveMcqRow.getTag();
//                    imgAddMcqRow.setVisibility(View.GONE);
//                    imgRemoveMcqRow.setVisibility(View.VISIBLE);
//                    llAddMcqanswer.addView(getMcqAnswerView(viewPositionToAdd + 1));
//                } else {
//                    Utils.showToast(getActivity().getResources().getString(R.string.msg_mcq_ans_limit), getActivity());
//                }
                addRow((Integer) imgRemoveMcqRow.getTag(), imgAddMcqRow, imgRemoveMcqRow);

            }
        });
        imgRemoveMcqRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int viewPositionToRemove = (Integer) imgRemoveMcqRow.getTag();
//                llAddMcqanswer.removeViewAt(viewPositionToRemove);
//                refreshViewPositions();
                removeRow((Integer) imgRemoveMcqRow.getTag());

            }
        });

        return v;
    }

    /*this method is for inflating views for edit and copy question*/
    private View setMCQ(int position, String text) {
        View v = null;
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        v = layoutInflater.inflate(R.layout.row_mcq_answer, null, false);

        ImageView imgAnsRadio = (ImageView) v.findViewById(R.id.img_ans_radio);
        EditText etAddMcqAnswer = (EditText) v.findViewById(R.id.et_add_mcq_answer);
        final ImageView imgAddMcqRow = (ImageView) v.findViewById(R.id.img_add_mcq_row);
        final ImageView imgRemoveMcqRow = (ImageView) v.findViewById(R.id.img_remove_mcq_row);
        etAddMcqAnswer.setText(text);

        if (position < 1) {
            imgAddMcqRow.setVisibility(View.GONE);
            imgRemoveMcqRow.setVisibility(View.GONE);
        } else if (position == questionAnswersModelArrayList.size() - 1) {
            imgAddMcqRow.setVisibility(View.VISIBLE);
            imgRemoveMcqRow.setVisibility(View.GONE);
        } else {
            imgAddMcqRow.setVisibility(View.GONE);
            imgRemoveMcqRow.setVisibility(View.VISIBLE);
        }
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
            View childView = llAddMcqanswer.getChildAt(i);
            ImageView imgRemoveMcqRow = (ImageView) childView.findViewById(R.id.img_remove_mcq_row);
            imgRemoveMcqRow.setTag(i);
        }

    }


    /*these is for set question questionData for copy and edit question.*/
    private ImageLoader imageLoader;

    public void setQuestionData(Data data) {
        Utils.showToast("SETDATACALLED", getActivity());
        try {
            if (data.getQuestionText() != null) {
                etAddquestionTitle.setText(Utils.formatHtml(data.getQuestionText()));
            }
            imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", imgSelectImage, ISMAuthor.options);
            setSpinnerData(data.getQuestionFormat());
            setMcqAnswers(data);
            etEvaluationNote1.setText(data.getEvaluationNotes());
            etEvaluationNote2.setText(data.getEvaluationNotes());
        } catch (Exception e) {
            Debug.e(TAG, "isSetQuestionData Exception : " + e.toString());
        }
    }


    private void setSpinnerData(String questionType) {
        if (questionType.equalsIgnoreCase("MCQ")) {
            spAddquestionType.setSelection(3);
            questionAnswersModelArrayList.clear();
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


    ArrayList<QuestionAnswersModel> questionAnswersModelArrayList = new ArrayList<QuestionAnswersModel>();

    private void setMcqAnswers(Data data) {
        questionAnswersModelArrayList.addAll(data.getAnswers());
        for (int i = 0; i < data.getAnswers().size(); i++) {
            llAddMcqanswer.addView(setMCQ(i, data.getAnswers().get(i).getChoiceText()));
        }
    }

    @Override
    public void onClick(View v) {
        if (v == tvAddquestionSave) {
            ((AddQuestionContainerFragment) mFragment).flipCard();



        } else if (v == tvAddquestionSaveAddmore) {

            ((AddQuestionContainerFragment) mFragment).flipCard();

        } else if (v == imgSelectImage) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("*/*");
            startActivityForResult(intent, 1);
        }

    }
}
