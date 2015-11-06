package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Utils;
import com.ism.author.adapter.Adapters;
import com.ism.author.autocomplete.ContactsCompletionView;
import com.ism.author.autocomplete.FilteredArrayAdapter;
import com.ism.author.autocomplete.TokenCompleteTextView;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.TagsModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by c166 on 31/10/15.
 */
public class QuestionAddEditFragment extends Fragment implements TokenCompleteTextView.TokenListener {

    private static final String TAG = QuestionAddEditFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;

    public QuestionAddEditFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    /*these is for the tag add functionality.*/
    private ContactsCompletionView completionView;
    private TagsModel[] tags;
    private ArrayAdapter<TagsModel> tagsAdapter;

    /*these sre for the xml views*/

    private TextView tvAddquestionHeader, tvAddquestionTitle, tvAddquestionType, tvAddquestionCategory, tvEvaluationNote1, tvEvaluationNote2,
            tvAddquestionSave, tvAddquestionSaveAddmore;
    private ImageView imgEditQuestion, imgCopyQuestion, imgDeleteQuestion, imgSelectImage;
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

        completionView = (ContactsCompletionView) view.findViewById(R.id.searchTagView);
        completionView.setAdapter(tagsAdapter);
        completionView.setTokenListener(this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);


        tvAddquestionHeader = (TextView) view.findViewById(R.id.tv_addquestion_header);
        tvAddquestionTitle = (TextView) view.findViewById(R.id.tv_addquestion_title);
        tvAddquestionType = (TextView) view.findViewById(R.id.tv_addquestion_type);
        tvAddquestionCategory = (TextView) view.findViewById(R.id.tv_addquestion_category);
        tvEvaluationNote1 = (TextView) view.findViewById(R.id.tv_evaluation_note1);
        tvEvaluationNote2 = (TextView) view.findViewById(R.id.tv_evaluation_note2);
        tvAddquestionSave = (TextView) view.findViewById(R.id.tv_addquestion_save);
        tvAddquestionSaveAddmore = (TextView) view.findViewById(R.id.tv_addquestion_save_addmore);


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


        for (int i = 0; i <= 1; i++) {
            llAddMcqanswer.addView(getMcqAnswerView(i));
        }


    }


    private void updateTokenConfirmation() {
        StringBuilder sb = new StringBuilder("Current tokens:\n");
        for (Object token : completionView.getObjects()) {
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


        imgAddMcqRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.showToast("The position is" + position, getActivity());

                if (position == 1) {
                    imgAddMcqRow.setVisibility(View.GONE);
                    imgRemoveMcqRow.setVisibility(View.GONE);
                } else {
                    imgAddMcqRow.setVisibility(View.GONE);
                    imgRemoveMcqRow.setVisibility(View.VISIBLE);
                }
                llAddMcqanswer.addView(getMcqAnswerView(position + 1));


            }
        });

        imgRemoveMcqRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showToast("The position is" + position, getActivity());

                llAddMcqanswer.removeViewAt(position);
            }
        });

        return v;
    }
}
