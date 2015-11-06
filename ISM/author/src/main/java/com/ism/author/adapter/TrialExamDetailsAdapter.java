package com.ism.author.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.Data;
import com.ism.author.model.ResponseObject;

import java.util.ArrayList;

/**
 * Created by c162 on 05/11/15.
 */
public class TrialExamDetailsAdapter extends RecyclerView.Adapter<TrialExamDetailsAdapter.ViewHolder> {
    private String studentName;
    ResponseObject responseObject, studentEvalResObj;
    Context context;
    LayoutInflater inflater;
    public static MyTypeFace myTypeFace;
    Fragment fragment;
    private static String TAG = TrialExamDetailsAdapter.class.getSimpleName();
    int asciiChar = 65;
    ArrayList<Data> dataArrayList = new ArrayList<Data>();

    public TrialExamDetailsAdapter(ResponseObject studentEvalResObj) {
        this.studentEvalResObj = studentEvalResObj;
    }

    public TrialExamDetailsAdapter(ResponseObject responseObject, Context context, Fragment fragment,ResponseObject studentEvalResObj) {
        this.responseObject = responseObject;
        this.context = context;
        this.fragment = fragment;
        this.studentEvalResObj = studentEvalResObj;
        inflater = LayoutInflater.from(context);
        myTypeFace = new MyTypeFace(context);
    }

    @Override
    public TrialExamDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_trial_exam_details, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrialExamDetailsAdapter.ViewHolder holder, int position) {
        try {
            ArrayList<Data> arrayList = new ArrayList<Data>();
            arrayList = responseObject.getData().get(0).getQuestions();
            int qno = position + 1;
            //holder.txtQuestionNo.setText("Questions " + qno);
            holder.txtQuestionNo.setText(Html.fromHtml("<u>" + "Questions " + qno + "</u>"));
            holder.txtQuestionText.setText(arrayList.get(position).getQuestionText());
            holder.txtQuestionText.setText(arrayList.get(position).getQuestionText());
            asciiChar = 65;
            for (int i = 0; i < arrayList.get(position).getAnswers().size(); i++) {
                setOptions(arrayList.get(position).getAnswers().get(i).getChoiceText(), holder.textViewOptions[i]);
                //holder.txtOptionA.setVisibility(View.VISIBLE);
                Debug.i(TAG, "Choice " + i + " :" + arrayList.get(position).getAnswers().get(i).getChoiceText());
                // holder.textViewOptionsIds[i].setText(arrayList.get(position).getAnswers().get(i).getChoiceText());
                if (arrayList.get(position).getAnswers().get(i).getIsRight().equals("1")) {
                    holder.txtQuestionsAnswer.setText(arrayList.get(position).getAnswers().get(i).getChoiceText());
                }
            }
            if (studentEvalResObj != null) {
                int j=0;
                holder.txtStudentAnswered.setVisibility(View.VISIBLE);
                holder.txtStudentNameAnswer.setVisibility(View.VISIBLE);

                dataArrayList = studentEvalResObj.getData().get(0).getEvaluations();
                holder.txtStudentNameAnswer.setText( " Answer :");
//                    while (arrayList.get(position).getQuestionId() != dataArrayList.get(j).getQuestionId()) {
//                        j=j+1;
//                    }
                if(dataArrayList.get(j).getQuestionId()!=null){
                    holder.txtStudentAnswered.setText(dataArrayList.get(position).getStudentResponse());
                }
                else{
                    holder.txtStudentAnswered.setText(" not answered");
                    // holder.txtStudentAnswered.setText(dataArrayList.get(position).getStudentResponse()+" not answered");
                }

                notifyDataSetChanged();
            }

        } catch (Exception e) {
            Debug.i(TAG, "BindViewHolder Exceptions :" + e.toString());
        }

    }

    private void setOptions(String choiceText, TextView txtOption) {
        txtOption.setText(Character.toString((char) asciiChar++) + ": " + choiceText);
        txtOption.setVisibility(View.VISIBLE);
    }


    @Override
    public long getItemId(int position) {
        return responseObject.getData().get(0).getQuestions().size();
    }

    @Override
    public int getItemCount() {
        return responseObject.getData().get(0).getQuestions().size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestionNo, txtQuestionText, txtQuestionsSolution, txtQuestionsEvaluations, txtQuestionsAnswer, txtCorrectAnswer, txtStudentNameAnswer, txtStudentAnswered, txtOptionA, txtOptionB, txtOptionC, txtOptionD, txtOptionE, txtOptionF;
        LinearLayout llOptions;
        EditText etEvaluationsNotes;
        TextView textViewOptions[];//={txtOptionA.getId(),txtOptionB.getId(),txtOptionC.getId(),txtOptionD.getId(),txtOptionE.getId(),txtOptionF.getId()};

        public ViewHolder(View itemView) {
            super(itemView);
            try {
                txtQuestionNo = (TextView) itemView.findViewById(R.id.txt_question_no);
                txtQuestionText = (TextView) itemView.findViewById(R.id.txt_question_text);
                txtQuestionsSolution = (TextView) itemView.findViewById(R.id.txt_solution);
                txtCorrectAnswer = (TextView) itemView.findViewById(R.id.txt_correct_answer);
                txtQuestionsAnswer = (TextView) itemView.findViewById(R.id.txt_answer);
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
                txtQuestionsEvaluations.setTypeface(myTypeFace.getRalewayBold());


            } catch (Exception e) {
                Debug.i(TAG, "ViewHolder Exceptions :" + e.toString());
            }


        }
    }

}
