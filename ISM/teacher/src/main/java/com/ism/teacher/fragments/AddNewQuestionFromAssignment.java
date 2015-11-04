package com.ism.teacher.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.ism.teacher.R;
import com.ism.teacher.adapters.Adapters;
import com.ism.teacher.helper.MyTypeFace;

import java.util.Arrays;
import java.util.List;

/**
 * Created by c75 on 02/11/15.
 */
public class AddNewQuestionFromAssignment extends Fragment {

    Fragment mFragment;

    private static final String TAG = AddNewQuestionFromAssignment.class.getSimpleName();
    private View view;
    private MyTypeFace myTypeFace;
    private static final String ARG_FRAGMENT = "fragment";


    private EditText etQuestionTitle, etAnswerBox, etEvaluationNotes, etSolution;
    private ImageView imgEditQuestion, imgCopyQuestion, imgDeleteQuestion;
    private Spinner spQuestionType;
    private Button btnSaveQuestion, btnSaveNAddmoreQuestion;
    private CheckBox chkAddQuestionToPreview;

    private List<String> arrayListQuestionType;


   /* public static AddNewQuestionFromAssignment newInstance(int fragment) {
        AddNewQuestionFromAssignment addNewQuestionFromAssignment = new AddNewQuestionFromAssignment();
        Bundle args = new Bundle();
        args.putInt(ARG_FRAGMENT, fragment);
        addNewQuestionFromAssignment.setArguments(args);
        return addNewQuestionFromAssignment;
    }
*/
    public AddNewQuestionFromAssignment(Fragment fragment) {
        this.mFragment=fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_new_question_from_assignment, container, false);
        initGlobal();

        return view;
    }

    private void initGlobal() {
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
        Adapters.setUpSpinner(getActivity(), spQuestionType, arrayListQuestionType);

    }
}
