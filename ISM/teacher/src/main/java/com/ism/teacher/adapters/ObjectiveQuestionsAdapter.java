package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.fragments.StudentAttemptedFragment;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.model.Data;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.model.Evaluation;
import com.ism.teacher.ws.model.Questions;

import java.util.ArrayList;

/**
 * Created by c162 on 05/11/15.
 */
public class ObjectiveQuestionsAdapter extends RecyclerView.Adapter<ObjectiveQuestionsAdapter.ViewHolder> {
    private String studentName;
    ResponseHandler responseHandler, studentEvalResObj;
    Context context;
    LayoutInflater inflater;
    public static MyTypeFace myTypeFace;
    Fragment fragment;
    private static String TAG = ObjectiveQuestionsAdapter.class.getSimpleName();
    int asciiChar = 65;
    ArrayList<Evaluation> dataArrayList = new ArrayList<Evaluation>();

    public ObjectiveQuestionsAdapter(ResponseHandler studentEvalResObj) {
        this.studentEvalResObj = studentEvalResObj;
    }

    public ObjectiveQuestionsAdapter(ResponseHandler responseHandler, Context context, Fragment fragment, ResponseHandler studentEvalResObj) {
        this.responseHandler = responseHandler;
        this.context = context;
        this.fragment = fragment;
        this.studentEvalResObj = studentEvalResObj;
        inflater = LayoutInflater.from(context);
        myTypeFace = new MyTypeFace(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestionNo, txtQuestionText, txtQuestionsSolution, txtQuestionsEvaluations, txtQuestionsAnswer, txtCorrectAnswer,
                txtStudentNameAnswer, txtStudentAnswered, txtOptionA, txtOptionB, txtOptionC, txtOptionD, txtOptionE, txtOptionF;
        LinearLayout llOptions, llEvaluationRow, llCorrectAnswerRow;
        EditText etEvaluationsNotes;
        TextView textViewOptions[];//={txtOptionA.getId(),txtOptionB.getId(),txtOptionC.getId(),txtOptionD.getId(),txtOptionE.getId(),txtOptionF.getId()};

        public ViewHolder(View itemView) {
            super(itemView);
            try {

                llCorrectAnswerRow = (LinearLayout) itemView.findViewById(R.id.ll_correct_answer_row);
                llEvaluationRow = (LinearLayout) itemView.findViewById(R.id.ll_evaluation_row);

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


    @Override
    public ObjectiveQuestionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_objective_questions, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ObjectiveQuestionsAdapter.ViewHolder holder, int position) {
        try {
            ArrayList<Questions> arrayList = new ArrayList<>();
            arrayList = responseHandler.getExamQuestions().get(0).getQuestions();
            int qno = position + 1;
            holder.txtQuestionNo.setText(Html.fromHtml("<u>" + "Questions " + qno + "</u>"));
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
                int j = 0;
                holder.txtStudentAnswered.setVisibility(View.VISIBLE);
                holder.txtStudentNameAnswer.setVisibility(View.VISIBLE);

                dataArrayList = studentEvalResObj.getExamEvaluation().get(0).getEvaluation();

//                String[] studentName = studentEvalResObj.getData().get(0).getStudentName().split(" ");
//                if (studentName[0] != null)
//                    holder.txtStudentNameAnswer.setText(studentName[0] + " Answer :");
//                else {
//                    holder.txtStudentNameAnswer.setText("Answer :");
//                }

                Debug.i(TAG, "Position: " + position);

//                Log.e("question ids size:", ""+StudentAttemptedFragment.questionsID.size());

                for (int i = 0; i < StudentAttemptedFragment.questionsID.size(); i++) {
                    if (dataArrayList.get(position).getQuestionId().equals(StudentAttemptedFragment.questionsID.get(i))) {
                        holder.txtStudentAnswered.setText(dataArrayList.get(position).getStudentResponse());

                        Log.i(TAG, "Question Id: " + dataArrayList.get(position).getQuestionId() + "=" + (StudentAttemptedFragment.questionsID.get(i)));
                        break;
                    } else {
                        holder.txtStudentAnswered.setText(" not answered");
                        // holder.txtStudentAnswered.setText(evaluationArrayList.get(position).getStudentResponse()+" not answered");
                    }

                    Log.e("test", holder.txtStudentAnswered.getText().toString());
                }

                notifyDataSetChanged();
            }

        } catch (Exception e) {
            Debug.i(TAG, "BindViewHolder Exceptions :" + e.getLocalizedMessage());
        }

    }

    private void setOptions(String choiceText, TextView txtOption) {
        txtOption.setText(Character.toString((char) asciiChar++) + ": " + choiceText);
        txtOption.setVisibility(View.VISIBLE);
    }


    @Override
    public int getItemCount() {
        return responseHandler.getExamQuestions().get(0).getQuestions().size();
    }


}
