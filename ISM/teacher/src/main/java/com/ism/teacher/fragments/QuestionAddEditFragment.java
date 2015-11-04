package com.ism.teacher.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Utils;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.multiautocomplete.ChipsAdapter;
import com.ism.teacher.multiautocomplete.ChipsItem;
import com.ism.teacher.multiautocomplete.ChipsMultiAutoCompleteTextview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by c166 on 31/10/15.
 */
public class QuestionAddEditFragment extends Fragment {


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

    private LinearLayout ll_questions_options;
    int row_count = 2;
    ImageView imgAdd, img_remove_row;
    ChipsMultiAutoCompleteTextview autoCompleteTextview;
    List<String> arrCountry;

    public QuestionAddEditFragment(Fragment fragment) {
        this.mFragment = fragment;
    }


    public QuestionAddEditFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_new_question_from_assignment, container, false);
        initGlobal();

        Utils.showToast("THE QUESTION ADD EDIT FRAGMENT CALLED", getActivity());
        return view;

    }

    private void initGlobal() {
        autoCompleteTextview = (ChipsMultiAutoCompleteTextview) view.findViewById(R.id.autoCompleteTextview);
        // arrCountry=Arrays.asList(getResources().getStringArray(R.array.country));

        String values[] = getActivity().getResources().getStringArray(R.array.country);

        ArrayList<ChipsItem> arrCountry = new ArrayList<ChipsItem>();


        for(int i=0;i<values.length;i++){
            arrCountry.add(new ChipsItem(values[i]));
        }

        ChipsAdapter chipsAdapter = new ChipsAdapter(getActivity(), arrCountry);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        autoCompleteTextview.setAdapter(chipsAdapter);

        etQuestionTitle = (EditText) view.findViewById(R.id.et_question_title);
        etAnswerBox = (EditText) view.findViewById(R.id.et_answer_box);
        etEvaluationNotes = (EditText) view.findViewById(R.id.et_evaluation_notes);
        etSolution = (EditText) view.findViewById(R.id.et_solution);

        imgEditQuestion = (ImageView) view.findViewById(R.id.img_edit_question);
        imgCopyQuestion = (ImageView) view.findViewById(R.id.img_copy_question);
        imgDeleteQuestion = (ImageView) view.findViewById(R.id.img_delete_question);

        spQuestionType = (Spinner) view.findViewById(R.id.sp_question_type);
        chkAddQuestionToPreview = (CheckBox) view.findViewById(R.id.chk_add_to_preview);

        btnSaveQuestion = (Button) view.findViewById(R.id.btn_save_question);
        btnSaveNAddmoreQuestion = (Button) view.findViewById(R.id.btn_save_n_addmore_question);

        arrayListQuestionType = Arrays.asList(getResources().getStringArray(R.array.question_type));
        Adapters.setUpExamTypeSpinner(getActivity(), spQuestionType, arrayListQuestionType);

        ll_questions_options = (LinearLayout) view.findViewById(R.id.ll_questions_options);

        spQuestionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //For MCQ
                if (spQuestionType.getSelectedItemPosition() == 0) {
                    etAnswerBox.setVisibility(View.VISIBLE);
                    etAnswerBox.setText("");
                    etAnswerBox.setMinLines(2);

                    ll_questions_options.removeAllViews();

                } else if (spQuestionType.getSelectedItemPosition() == 1) {
                    etAnswerBox.setVisibility(View.VISIBLE);
                    etAnswerBox.setText("");
                    etAnswerBox.setMinLines(5);
                    ll_questions_options.removeAllViews();
                } else {
                    etAnswerBox.setVisibility(View.GONE);
                    for (int j = 1; j <= 2; j++) {
                        View mcq = getMCQInflaterView(j);
                        ll_questions_options.addView(mcq);
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private View getMCQInflaterView(int row) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View v;
        v = layoutInflater.inflate(R.layout.question_options_layout, null, false);
        EditText editText = (EditText) v.findViewById(R.id.et_question_options);
        imgAdd = (ImageView) v.findViewById(R.id.img_add_row);
        img_remove_row = (ImageView) v.findViewById(R.id.img_remove_row);

        if (row == row_count) {
            imgAdd.setVisibility(View.VISIBLE);
        } else {
            imgAdd.setVisibility(View.INVISIBLE);
            img_remove_row.setVisibility(View.INVISIBLE);
        }

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imgAdd.setVisibility(View.INVISIBLE);
                img_remove_row.setVisibility(View.INVISIBLE);
                row_count++;
                View v = getMCQInflaterView(row_count);
                ll_questions_options.addView(v);
                imgAdd.setVisibility(View.VISIBLE);
                img_remove_row.setVisibility(View.VISIBLE);


            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imgAdd.setVisibility(View.INVISIBLE);
                img_remove_row.setVisibility(View.INVISIBLE);
                row_count++;
                View v = getMCQInflaterView(row_count);
                ll_questions_options.addView(v);
                imgAdd.setVisibility(View.VISIBLE);
                img_remove_row.setVisibility(View.VISIBLE);
            }
        });

       /* img_remove_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_questions_options.removeViewAt(row_count);
                row_count--;
            }
        });*/


        return v;
    }


}
