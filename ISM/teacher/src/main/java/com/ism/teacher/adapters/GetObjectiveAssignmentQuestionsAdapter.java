package com.ism.teacher.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.ws.model.Answers;
import com.ism.teacher.ws.model.Evaluation;
import com.ism.teacher.ws.model.Questions;

import java.util.ArrayList;

/**
 * This fragment is used to display the list of objective question
 */
public class GetObjectiveAssignmentQuestionsAdapter extends RecyclerView.Adapter<GetObjectiveAssignmentQuestionsAdapter.ViewHolder> {
    private static final String TAG = GetObjectiveAssignmentQuestionsAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Questions> arrListQuestions = new ArrayList<Questions>();
    private MyTypeFace myTypeFace;
    private LayoutInflater inflater;


    public GetObjectiveAssignmentQuestionsAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        myTypeFace = new MyTypeFace(context);
    }

    @Override
    public GetObjectiveAssignmentQuestionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_objective_assignment_questions, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtQuestionNo, txtQuestionText, txtCorrectAnswer, txtAnswer, txtStudentnameAnswer, txtStudentAnswer, txtEvoluationsNotes,
                txtSolution;
        EditText etEvoluationsNotes, etSolution;
        LinearLayout llQuestionsOptions, llQuestionsEvaluationContainer;


        public ViewHolder(View itemView) {
            super(itemView);
            try {

                txtQuestionNo = (TextView) itemView.findViewById(R.id.txt_question_no);
                txtQuestionText = (TextView) itemView.findViewById(R.id.txt_question_text);
                txtCorrectAnswer = (TextView) itemView.findViewById(R.id.txt_correct_answer);
                txtAnswer = (TextView) itemView.findViewById(R.id.txt_answer);
                txtStudentnameAnswer = (TextView) itemView.findViewById(R.id.txt_studentname_answer);
                txtStudentAnswer = (TextView) itemView.findViewById(R.id.txt_student_answer);
                txtEvoluationsNotes = (TextView) itemView.findViewById(R.id.txt_evoluations_notes);
                txtSolution = (TextView) itemView.findViewById(R.id.txt_solution);

                etEvoluationsNotes = (EditText) itemView.findViewById(R.id.et_evoluations_notes);
                etSolution = (EditText) itemView.findViewById(R.id.et_solution);

                llQuestionsOptions = (LinearLayout) itemView.findViewById(R.id.ll_questions_options);

                llQuestionsEvaluationContainer = (LinearLayout) itemView.findViewById(R.id.ll_questions_evaluation_container);

            } catch (Exception e) {
                Debug.e(TAG, "ViewHolder Exceptions :" + e.toString());
            }
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {

            holder.txtQuestionNo.setTypeface(myTypeFace.getRalewayBold());
            holder.txtQuestionText.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtCorrectAnswer.setTypeface(myTypeFace.getRalewayBold());
            holder.txtAnswer.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtStudentnameAnswer.setTypeface(myTypeFace.getRalewayBold());
            holder.txtStudentAnswer.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtEvoluationsNotes.setTypeface(myTypeFace.getRalewayBold());
            holder.txtSolution.setTypeface(myTypeFace.getRalewayBold());

            holder.etEvoluationsNotes.setTypeface(myTypeFace.getRalewayRegular());
            holder.etSolution.setTypeface(myTypeFace.getRalewayRegular());

            holder.txtQuestionNo.setText(mContext.getString(R.string.strquestion) + " " + (position + 1));
            holder.txtQuestionNo.setTypeface(myTypeFace.getRalewayBold());
            holder.txtQuestionNo.setPaintFlags(holder.txtQuestionNo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            holder.txtQuestionText.setText(Utility.formatHtml(arrListQuestions.get(position).getQuestionText()));

            holder.llQuestionsOptions.removeAllViews();
            if (holder.llQuestionsOptions.getChildCount() == 0) {
                for (int i = 0; i < arrListQuestions.get(position).getAnswers().size(); i++) {
                    View ansView = getAnsInflaterView(arrListQuestions.get(position).getAnswers().get(i), i);
                    holder.llQuestionsOptions.addView(ansView);
                }
            }

            if (evaluationList.size() > 0) {
                holder.llQuestionsEvaluationContainer.setVisibility(View.VISIBLE);

                for (int i = 0; i < arrListQuestions.get(position).getAnswers().size(); i++) {
                    if (arrListQuestions.get(position).getAnswers().get(i).getIsRight().equals("1")) {
                        holder.txtAnswer.setText(Utility.formatHtml(Utility.getCharForNumber(i + 1) + ". " + arrListQuestions.get(position).getAnswers().get(position).getChoiceText()));
                        break;
                    }
                }

                holder.txtStudentAnswer.setText(Utility.formatHtml(evaluationList.get(position).getStudentResponse()));
                holder.etEvoluationsNotes.setText(Utility.formatHtml(arrListQuestions.get(position).getEvaluationNotes()));
                holder.etSolution.setText(Utility.formatHtml(arrListQuestions.get(position).getSolution()));

            } else {
                holder.llQuestionsEvaluationContainer.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            Debug.i(TAG, "BindViewHolder Exceptions:" + e.getLocalizedMessage());
        }

    }

    @Override
    public int getItemCount() {
        return arrListQuestions.size();
    }

    public void addAll(ArrayList<Questions> listOfQuestions) {
        try {
            this.arrListQuestions.clear();
            this.arrListQuestions.addAll(listOfQuestions);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }

    private View getAnsInflaterView(Answers answer, int position) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v;
        v = layoutInflater.inflate(R.layout.row_mcq_question_answer, null, false);
        TextView tvMcqQuestionAns = (TextView) v.findViewById(R.id.tv_mcq_question_ans);
        tvMcqQuestionAns.setTypeface(myTypeFace.getRalewayRegular());
        tvMcqQuestionAns.setText(Utility.formatHtml(Utility.getCharForNumber(position + 1) + ": " + answer.getChoiceText()));

        return v;
    }


    ArrayList<Evaluation> evaluationList = new ArrayList<Evaluation>();

    public void setEvaluationData(ArrayList<Evaluation> evaluationList) {
        this.evaluationList = evaluationList;
        notifyDataSetChanged();

    }


}
