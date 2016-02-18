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
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.object.Global;
import com.ism.author.utility.Debug;
import com.ism.author.utility.HtmlImageGetter;
import com.ism.author.utility.Utility;

import io.realm.RealmList;
import model.ROAnswerChoices;
import model.ROEvaluation;
import model.ROQuestions;

/**
 * Created by c166 on 16/11/15.
 */
public class ObjectiveAssignmentQuestionsAdapter extends RecyclerView.Adapter<ObjectiveAssignmentQuestionsAdapter.ViewHolder>
        implements HtmlImageGetter.RefreshDataAfterLoadImage {

    private static final String TAG = ObjectiveAssignmentQuestionsAdapter.class.getSimpleName();
    private Context mContext;
    private RealmList<ROQuestions> arrListQuestions = null;
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


            holder.txtQuestionNo.setText(mContext.getString(R.string.strquestion) + " " + (position + 1));
            holder.txtQuestionNo.setPaintFlags(holder.txtQuestionNo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


            if (arrListQuestions.get(position).getQuestionText().contains("img") || arrListQuestions.get(position).getQuestionText().contains("http:")
                    || arrListQuestions.get(position).getQuestionText().contains("https:")) {


//                if (arrListQuestions.get(position).getSpan() == null) {
//
//                    arrListQuestions.get(position).setSpan(Html.fromHtml(arrListQuestions.get(position).getQuestionText(),
//                            new HtmlImageGetter(50, 50, mContext, (HtmlImageGetter.RefreshDataAfterLoadImage) this
//                            ), null));
//                } else {
//
//                    holder.txtQuestionText.setText(Html.fromHtml(arrListQuestions.get(position).getQuestionText(),
//                            new HtmlImageGetter(50, 50, mContext, null
//                            ), null));
//                }


                /**
                 * this is to handle imageload from url in textview android.
                 */
                if (holder.txtQuestionText.getTag() == null) {

                    holder.txtQuestionText.setText(Html.fromHtml(arrListQuestions.get(position).getQuestionText()));
                    Html.fromHtml(arrListQuestions.get(position).getQuestionText(),
                            new HtmlImageGetter(60, 60, mContext, (HtmlImageGetter.RefreshDataAfterLoadImage) this
                            ), null);
                    holder.txtQuestionText.setTag(String.valueOf(position));

                } else {
                    holder.txtQuestionText.setText(Html.fromHtml(arrListQuestions.get(position).getQuestionText(),
                            new HtmlImageGetter(60, 60, mContext, null
                            ), null));
                }
            } else {
                holder.txtQuestionText.setText(Html.fromHtml(arrListQuestions.get(position).getQuestionText()));
            }

            holder.tvEvoluationsNotes.setText(Utility.formatHtml(arrListQuestions.get(position).getEvaluationNotes()));
            holder.tvSolution.setText(Utility.formatHtml(arrListQuestions.get(position).getSolution()));

            /**
             * set the stududent name in case of evaluation.
             */
            if (getBundleArguments().containsKey(ExamSubmittorAdapter.ARG_STUDENT_NAME)) {
                holder.txtStudentnameAnswer.setText(getBundleArguments().getString(ExamSubmittorAdapter.ARG_STUDENT_NAME) + " " +
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


                /**
                 * inflate MCQ options view
                 */
                if (holder.llQuestionsOptions.getChildCount() == 0) {
                    for (int i = 0; i < arrListQuestions.get(position).getRoAnswerChoices().size(); i++) {
                        View ansView = getAnsInflaterView(arrListQuestions.get(position).getRoAnswerChoices().get(i), i);
                        holder.llQuestionsOptions.addView(ansView);
                    }
                }


                /**
                 * Set the right answer form the available MCQ options.
                 */
                holder.txtAnswer.setText("");

                if (arrListQuestions.get(position).getRoAnswerChoices() != null) {
                    for (int i = 0; i < arrListQuestions.get(position).getRoAnswerChoices().size(); i++) {
                        if (arrListQuestions.get(position).getRoAnswerChoices().get(i).isRight()) {

                            holder.txtAnswer.setText(Utility.formatHtml(Utility.getCharForNumber(i + 1) + ". " +
                                    arrListQuestions.get(position).getRoAnswerChoices().get(i).getChoiceText()));
                            break;
                        }
                    }
                }


                /**
                 * check for that this adapter is called for the evaluation or to just view questions.
                 */
                if (getBundleArguments().getBoolean(ExamsAdapter.ARG_ISLOAD_FRAGMENTFOREVALUATION) ||
                        getBundleArguments().getInt(ExamsAdapter.ARG_FRAGMENT_TYPE) == AuthorHostActivity.FRAGMENT_TRIAL) {
                    holder.llEvaluationContainer.setVisibility(View.VISIBLE);
                } else {
                    holder.llEvaluationContainer.setVisibility(View.GONE);
                    holder.txtStudentnameAnswer.setVisibility(View.GONE);
                    holder.txtStudentAnswer.setVisibility(View.GONE);
                }
            }


            /**
             * This is to set the student answer data.
             */
            holder.txtStudentAnswer.setText("");

            if (evaluationList != null) {
                if (evaluationList.size() > 0 && position < evaluationList.size()) {

                    if (getBundleArguments().getString(ExamsAdapter.ARG_EXAM_MODE).equalsIgnoreCase(mContext.getString(R.string.strobjective))) {

                        for (int j = 0; j < arrListQuestions.get(position).getRoAnswerChoices().size(); j++) {

                            if (evaluationList.get(position).getStudentResposne().equalsIgnoreCase(
                                    String.valueOf(arrListQuestions.get(position).getRoAnswerChoices().get(j).getAnswerChoicesId()))) {
                                holder.txtStudentAnswer.setText(Utility.formatHtml(Utility.getCharForNumber(j + 1) + ". " +
                                        arrListQuestions.get(position).getRoAnswerChoices().get(j).getChoiceText()));
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
        if (arrListQuestions != null) {
            return arrListQuestions.size();
        } else {
            return 0;
        }
    }

    public void addAll(RealmList<ROQuestions> arrListQuestions) {
        try {
            this.arrListQuestions = arrListQuestions;
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtQuestionNo, txtQuestionText, txtCorrectAnswer, txtAnswer, txtStudentnameAnswer, txtStudentAnswer, txtEvoluationsNotesTitle,
                txtSolutionTitle, tvEvoluationsNotes, tvSolution;

        LinearLayout llQuestionsOptions, llAnswerContainer, llEvaluationContainer, llCorrectAnswer;

        public ViewHolder(View itemView) {
            super(itemView);
            try {
                txtQuestionNo = (TextView) itemView.findViewById(R.id.txt_question_no);
                txtQuestionText = (TextView) itemView.findViewById(R.id.txt_question_text);
                txtCorrectAnswer = (TextView) itemView.findViewById(R.id.txt_correct_answer);
                txtAnswer = (TextView) itemView.findViewById(R.id.txt_author_answer);
                txtStudentnameAnswer = (TextView) itemView.findViewById(R.id.txt_studentname_answer);
                txtStudentAnswer = (TextView) itemView.findViewById(R.id.txt_student_answer);
                txtEvoluationsNotesTitle = (TextView) itemView.findViewById(R.id.txt_evoluations_notes_title);
                txtSolutionTitle = (TextView) itemView.findViewById(R.id.txt_solution_title);

                tvEvoluationsNotes = (TextView) itemView.findViewById(R.id.tv_evoluations_notes);
                tvSolution = (TextView) itemView.findViewById(R.id.tv_title_solution);

                llQuestionsOptions = (LinearLayout) itemView.findViewById(R.id.ll_questions_options);
                llAnswerContainer = (LinearLayout) itemView.findViewById(R.id.ll_answer_container);
                llEvaluationContainer = (LinearLayout) itemView.findViewById(R.id.ll_evaluation_container);

                llCorrectAnswer = (LinearLayout) itemView.findViewById(R.id.ll_correct_answer);


                txtQuestionNo.setTypeface(Global.myTypeFace.getRalewayBold());
                txtQuestionText.setTypeface(Global.myTypeFace.getRalewayRegular());
                txtCorrectAnswer.setTypeface(Global.myTypeFace.getRalewayBold());
                txtAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());
                txtStudentnameAnswer.setTypeface(Global.myTypeFace.getRalewayBold());
                txtStudentAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());
                txtEvoluationsNotesTitle.setTypeface(Global.myTypeFace.getRalewayBold());
                txtSolutionTitle.setTypeface(Global.myTypeFace.getRalewayBold());
                tvEvoluationsNotes.setTypeface(Global.myTypeFace.getRalewayRegular());
                tvSolution.setTypeface(Global.myTypeFace.getRalewayRegular());


            } catch (Exception e) {
                Debug.e(TAG, "ViewHolder Exceptions :" + e.toString());
            }
        }
    }


    private View getAnsInflaterView(ROAnswerChoices answer, int position) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v;
        v = layoutInflater.inflate(R.layout.row_mcq_question_answer, null, false);
        TextView tvMcqQuestionAns = (TextView) v.findViewById(R.id.tv_mcq_question_ans);
        tvMcqQuestionAns.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvMcqQuestionAns.setText(Utility.formatHtml(Utility.getCharForNumber(position + 1) + ": " + answer.getChoiceText()));

        return v;
    }


    RealmList<ROEvaluation> evaluationList = new RealmList<ROEvaluation>();

    public void setEvaluationData(RealmList<ROEvaluation> evaluationList) {

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
