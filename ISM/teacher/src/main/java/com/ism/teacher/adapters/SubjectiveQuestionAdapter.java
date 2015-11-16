package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.model.Data;
import com.ism.teacher.model.ResponseObject;

import java.util.ArrayList;

/**
 * Created by c75 on 16/11/15.
 */
public class SubjectiveQuestionAdapter extends RecyclerView.Adapter<SubjectiveQuestionAdapter.ViewHolder> {

    private static String TAG = SubjectiveQuestionAdapter.class.getSimpleName();

    Fragment mFragment;
    Context context;
    public static MyTypeFace myTypeFace;
    ResponseObject responseObject;

    ArrayList<Data> arrayListSubjectiveQuestions = new ArrayList<>();

    public SubjectiveQuestionAdapter(ResponseObject responseObject, Context context, Fragment fragment) {
        this.responseObject = responseObject;
        this.context = context;
        this.mFragment = fragment;
        myTypeFace = new MyTypeFace(context);
    }

    public void addAll(ArrayList<Data> data) {
        try {
            this.arrayListSubjectiveQuestions.clear();
            this.arrayListSubjectiveQuestions.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }


    @Override
    public SubjectiveQuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_subjective_questions, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestionNo, txtQuestionText, txtAnswer;
        EditText etEvaluationNotes, etComments;

        //Textviews for answer rating
        TextView txtExcellent, txtGood, txtFair, txtAverage, txtPoor, txtIncorrect;
        ImageView imgCopyComments, imgDeleteComments;

        public ViewHolder(View itemView) {
            super(itemView);
            try {
                txtQuestionNo = (TextView) itemView.findViewById(R.id.txt_question_no);
                txtQuestionText = (TextView) itemView.findViewById(R.id.txt_question_text);
                txtAnswer = (TextView) itemView.findViewById(R.id.txt_answer);
                etEvaluationNotes = (EditText) itemView.findViewById(R.id.et_evaluation_notes);
                etComments = (EditText) itemView.findViewById(R.id.et_comments);

                txtExcellent = (TextView) itemView.findViewById(R.id.txt_excellent);
                txtGood = (TextView) itemView.findViewById(R.id.txt_good);
                txtFair = (TextView) itemView.findViewById(R.id.txt_fair);
                txtAverage = (TextView) itemView.findViewById(R.id.txt_average);
                txtPoor = (TextView) itemView.findViewById(R.id.txt_poor);
                txtIncorrect = (TextView) itemView.findViewById(R.id.txt_incorrect);

                imgCopyComments = (ImageView) itemView.findViewById(R.id.img_copy_comments);
                imgDeleteComments = (ImageView) itemView.findViewById(R.id.img_delete_comments);


                txtQuestionNo.setTypeface(myTypeFace.getRalewayBold());
                txtQuestionText.setTypeface(myTypeFace.getRalewayRegular());
                txtAnswer.setTypeface(myTypeFace.getRalewayThin());


                txtExcellent.setTypeface(myTypeFace.getRalewayRegular());
                txtGood.setTypeface(myTypeFace.getRalewayRegular());
                txtFair.setTypeface(myTypeFace.getRalewayRegular());
                txtAverage.setTypeface(myTypeFace.getRalewayRegular());
                txtPoor.setTypeface(myTypeFace.getRalewayRegular());
                txtIncorrect.setTypeface(myTypeFace.getRalewayRegular());


                /*txtQuestionNo = (TextView) itemView.findViewById(R.id.txtQuestionNo);
                txtQuestionText = (TextView) itemView.findViewById(R.id.txtQuestionText);
                txtQuestionsSolution = (TextView) itemView.findViewById(R.id.txt_solution);
                txtCorrectAnswer = (TextView) itemView.findViewById(R.id.txt_correct_answer);
                txtQuestionsAnswer = (TextView) itemView.findViewById(R.id.txtAnswer);
                txtStudentNameAnswer = (TextView) itemView.findViewById(R.id.txt_studentname_answer);
                txtStudentAnswered = (TextView) itemView.findViewById(R.id.txt_student_answer);
                txtOptionA = (TextView) itemView.findViewById(R.id.txt_option_a);
                txtOptionB = (TextView) itemView.findViewById(R.id.txt_option_b);
                txtOptionC = (TextView) itemView.findViewById(R.id.txt_option_c);
                txtOptionD = (TextView) itemView.findViewById(R.id.txt_option_d);
                txtOptionE = (TextView) itemView.findViewById(R.id.txt_option_e);
                txtOptionF = (TextView) itemView.findViewById(R.id.txt_option_f);

                txtQuestionsEvaluations = (TextView) itemView.findViewById(R.id.txt_evoluations_notes);
                etEvaluationsNotes = (EditText) itemView.findViewById(R.id.et_evoluations_notes);
                textViewOptions = new TextView[]{txtOptionA, txtOptionB, txtOptionC, txtOptionD, txtOptionE, txtOptionF};
                txtQuestionNo.setTypeface(myTypeFace.getRalewayBold());
                txtQuestionText.setTypeface(myTypeFace.getRalewayRegular());
                txtOptionA.setTypeface(myTypeFace.getRalewayRegular());
                txtOptionB.setTypeface(myTypeFace.getRalewayRegular());
                txtOptionC.setTypeface(myTypeFace.getRalewayRegular());
                txtOptionD.setTypeface(myTypeFace.getRalewayRegular());
                txtOptionE.setTypeface(myTypeFace.getRalewayRegular());
                txtOptionF.setTypeface(myTypeFace.getRalewayRegular());

                txtCorrectAnswer.setTypeface(myTypeFace.getRalewayBold());
                txtQuestionsSolution.setTypeface(myTypeFace.getRalewayBold());
                txtQuestionsAnswer.setTypeface(myTypeFace.getRalewayBold());
                txtStudentNameAnswer.setTypeface(myTypeFace.getRalewayBold());
                txtQuestionsEvaluations.setTypeface(myTypeFace.getRalewayBold());*/


            } catch (Exception e) {
                Debug.i(TAG, "ViewHolder Exceptions :" + e.toString());
            }


        }
    }

    @Override
    public void onBindViewHolder(SubjectiveQuestionAdapter.ViewHolder holder, int position) {
        try
        {
            ArrayList<Data> arrayList = new ArrayList<Data>();
            arrayList = responseObject.getData().get(0).getQuestions();
            int qno = position + 1;
            //holder.txtQuestionNo.setText("Questions " + qno);
            holder.txtQuestionNo.setText(Html.fromHtml("QUESTION: " + qno + "</u>"));
            holder.txtQuestionText.setText(arrayList.get(position).getQuestionText());


        }
        catch (Exception e)
        {
            Debug.i(TAG, "BindViewHolder Exceptions :" + e.getLocalizedMessage());
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
