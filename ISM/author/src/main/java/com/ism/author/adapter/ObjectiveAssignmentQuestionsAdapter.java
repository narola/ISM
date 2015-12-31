package com.ism.author.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.HtmlImageGetter;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.object.Global;
import com.ism.author.ws.model.Answers;
import com.ism.author.ws.model.Evaluation;
import com.ism.author.ws.model.Questions;

import java.util.ArrayList;

/**
 * Created by c166 on 16/11/15.
 */
public class ObjectiveAssignmentQuestionsAdapter extends RecyclerView.Adapter<ObjectiveAssignmentQuestionsAdapter.ViewHolder> implements HtmlImageGetter.RefreshDataAfterLoadImage {

    private static final String TAG = ObjectiveAssignmentQuestionsAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Questions> arrListQuestions = new ArrayList<Questions>();
    private LayoutInflater inflater;


    public ObjectiveAssignmentQuestionsAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_objective_assignment_questions, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {

            holder.txtQuestionNo.setTypeface(Global.myTypeFace.getRalewayBold());
            holder.txtQuestionText.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.txtCorrectAnswer.setTypeface(Global.myTypeFace.getRalewayBold());
            holder.txtAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.txtStudentnameAnswer.setTypeface(Global.myTypeFace.getRalewayBold());
            holder.txtStudentAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.txtEvoluationsNotes.setTypeface(Global.myTypeFace.getRalewayBold());
            holder.txtSolution.setTypeface(Global.myTypeFace.getRalewayBold());

            holder.tvEvoluationsNotes.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvSolution.setTypeface(Global.myTypeFace.getRalewayRegular());

            holder.txtQuestionNo.setText(mContext.getString(R.string.strquestion) + " " + (position + 1));
            holder.txtQuestionNo.setTypeface(Global.myTypeFace.getRalewayBold());
            holder.txtQuestionNo.setPaintFlags(holder.txtQuestionNo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//            holder.txtQuestionText.setText(Html.fromHtml(arrListQuestions.get(position).getQuestionText(), new HtmlImageGetter(50, 50, mContext, null), null));


            if (arrListQuestions.get(position).getQuestionText().contains("img") || arrListQuestions.get(position).getQuestionText().contains("http:")
                    || arrListQuestions.get(position).getQuestionText().contains("https:")) {
                if (arrListQuestions.get(position).getSpan() == null) {

                    arrListQuestions.get(position).setSpan(Html.fromHtml(arrListQuestions.get(position).getQuestionText(),
                            new HtmlImageGetter(50, 50, mContext, (HtmlImageGetter.RefreshDataAfterLoadImage) this
                            ), null));
                } else {

                    holder.txtQuestionText.setText(Html.fromHtml(arrListQuestions.get(position).getQuestionText(),
                            new HtmlImageGetter(50, 50, mContext, null
                            ), null));
                }
            } else {
                holder.txtQuestionText.setText(Html.fromHtml(arrListQuestions.get(position).getQuestionText()));
            }

            holder.tvEvoluationsNotes.setText(Utils.formatHtml(arrListQuestions.get(position).getEvaluationNotes()));
            holder.tvSolution.setText(Utils.formatHtml(arrListQuestions.get(position).getSolution()));
            if (getBundleArguments().containsKey(AssignmentSubmittorAdapter.ARG_STUDENT_NAME)) {
                holder.txtStudentnameAnswer.setText(getBundleArguments().getString(AssignmentSubmittorAdapter.ARG_STUDENT_NAME) + " " +
                        mContext.getString(R.string.stranswer));

            }

            if (getBundleArguments().getString(ExamsAdapter.ARG_EXAM_MODE).equalsIgnoreCase(mContext.getString(R.string.strsubjective))) {
                holder.llQuestionsOptions.setVisibility(View.GONE);
                holder.llAnswerContainer.setVisibility(View.GONE);
                holder.llEvaluationContainer.setVisibility(View.VISIBLE);

                if (getBundleArguments().getInt(ExamsAdapter.ARG_FRAGMENT_TYPE) == AuthorHostActivity.FRAGMENT_TRIAL) {

                }


            } else if (getBundleArguments().getString(ExamsAdapter.ARG_EXAM_MODE).equalsIgnoreCase(mContext.getString(R.string.strobjective))) {
                holder.llQuestionsOptions.setVisibility(View.VISIBLE);
                holder.llAnswerContainer.setVisibility(View.VISIBLE);
                holder.llEvaluationContainer.setVisibility(View.GONE);
                holder.llQuestionsOptions.removeAllViews();

                if (holder.llQuestionsOptions.getChildCount() == 0) {
                    for (int i = 0; i < arrListQuestions.get(position).getAnswers().size(); i++) {
                        View ansView = getAnsInflaterView(arrListQuestions.get(position).getAnswers().get(i), i);
                        holder.llQuestionsOptions.addView(ansView);
                    }
                }

                holder.txtAnswer.setText("");
                if (arrListQuestions.get(position).getAnswers() != null) {
                    for (int i = 0; i < arrListQuestions.get(position).getAnswers().size(); i++) {
                        if (arrListQuestions.get(position).getAnswers().get(i).getIsRight().equals("1")) {
                            holder.txtAnswer.setText(Utils.formatHtml(Utils.getCharForNumber(i + 1) + ". " +
                                    arrListQuestions.get(position).getAnswers().get(i).getChoiceText()));
                            break;
                        }
                    }
                }

                if (getBundleArguments().getBoolean(ExamsAdapter.ARG_ISLOAD_FRAGMENTFOREVALUATION) ||
                        getBundleArguments().getInt(ExamsAdapter.ARG_FRAGMENT_TYPE) == AuthorHostActivity.FRAGMENT_TRIAL) {
                    holder.llEvaluationContainer.setVisibility(View.VISIBLE);
                } else {
                    holder.llEvaluationContainer.setVisibility(View.GONE);
                    holder.txtStudentnameAnswer.setVisibility(View.GONE);
                    holder.txtStudentAnswer.setVisibility(View.GONE);
                }
            }

            holder.txtStudentAnswer.setText("");
            if (evaluationList != null) {
                if (evaluationList.size() > 0 && position < evaluationList.size()) {
                    if (getBundleArguments().getString(ExamsAdapter.ARG_EXAM_MODE).equalsIgnoreCase(mContext.getString(R.string.strobjective))) {
                        for (int j = 0; j < arrListQuestions.get(position).getAnswers().size(); j++) {
                            if (evaluationList.get(position).getStudentResponse().equalsIgnoreCase
                                    (arrListQuestions.get(position).getAnswers().get(j).getId())) {
                                holder.txtStudentAnswer.setText(Utils.formatHtml(Utils.getCharForNumber(j + 1) + ". " +
                                        arrListQuestions.get(position).getAnswers().get(j).getChoiceText()));
                                break;
                            }
                        }
                    } else if (getBundleArguments().getString(ExamsAdapter.ARG_EXAM_MODE).equalsIgnoreCase(mContext.getString(R.string.strsubjective))) {


                    }
                }
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


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtQuestionNo, txtQuestionText, txtCorrectAnswer, txtAnswer, txtStudentnameAnswer, txtStudentAnswer, txtEvoluationsNotes,
                txtSolution, tvEvoluationsNotes, tvSolution;

        LinearLayout llQuestionsOptions, llAnswerContainer, llEvaluationContainer, llCorrectAnswer;

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

                tvEvoluationsNotes = (TextView) itemView.findViewById(R.id.tv_evoluations_notes);
                tvSolution = (TextView) itemView.findViewById(R.id.tv_title_solution);

                llQuestionsOptions = (LinearLayout) itemView.findViewById(R.id.ll_questions_options);
                llAnswerContainer = (LinearLayout) itemView.findViewById(R.id.ll_answer_container);
                llEvaluationContainer = (LinearLayout) itemView.findViewById(R.id.ll_evaluation_container);

                llCorrectAnswer = (LinearLayout) itemView.findViewById(R.id.ll_correct_answer);

            } catch (Exception e) {
                Debug.e(TAG, "ViewHolder Exceptions :" + e.toString());
            }
        }
    }


    private View getAnsInflaterView(Answers answer, int position) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v;
        v = layoutInflater.inflate(R.layout.row_mcq_question_answer, null, false);
        TextView tvMcqQuestionAns = (TextView) v.findViewById(R.id.tv_mcq_question_ans);
        tvMcqQuestionAns.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvMcqQuestionAns.setText(Utils.formatHtml(Utils.getCharForNumber(position + 1) + ": " + answer.getChoiceText()));

        return v;
    }


    ArrayList<Evaluation> evaluationList = new ArrayList<Evaluation>();

    public void setEvaluationData(ArrayList<Evaluation> evaluationList) {
        this.evaluationList = evaluationList;
        notifyDataSetChanged();

    }

    private Bundle getBundleArguments() {
        return ((AuthorHostActivity) mContext).getBundle();
    }


    @Override
    public void refreshData() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                notifyDataSetChanged();
                Debug.e(TAG, "notify data called");
            }
        };

        handler.post(r);
    }

}
