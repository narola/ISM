package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.HtmlImageGetter;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.model.Evaluation;
import com.ism.teacher.ws.model.Questions;

import java.util.ArrayList;

/**
 * Created by c75 on 16/11/15.
 */

public class SubjectiveQuestionListAdapter extends RecyclerView.Adapter<SubjectiveQuestionListAdapter.ViewHolder> {

    private static String TAG = SubjectiveQuestionListAdapter.class.getSimpleName();

    Fragment mFragment;
    Context mContext;

    ArrayList<Evaluation> evaluationList = new ArrayList<Evaluation>();
    ArrayList<Questions> arrayListSubjectiveQuestions = new ArrayList<>();

    private LayoutInflater inflater;


    public SubjectiveQuestionListAdapter(Context mContext, Fragment mFragment) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.mFragment = mFragment;
    }

    public void addAll(ArrayList<Questions> data) {
        try {
            this.arrayListSubjectiveQuestions.clear();
            this.arrayListSubjectiveQuestions.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    @Override
    public SubjectiveQuestionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_subjective_questions, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSubjectiveQuestionNo, tvSubjectiveQuestionScore, tvSubjectiveQuestionComment, tvSubjectiveQuestion,
                tvSubjectiveQuestionAnsTitle, tvSubjectiveQuestionAns, tvSubjectiveQuesEvaluationNotesTitle,
                tvSubjectiveQuesEvaluationNotes, tvScoreExcellent, tvScoreGood, tvScoreFair, tvScoreAverage, tvScorePoor,
                tvScoreIncorrect;
        ImageView imgCopyComment, imgDeleteComment;
        EditText etAddComment;

        TextView[] scoreTextArray;


        public ViewHolder(View itemView) {
            super(itemView);
            try {
                tvSubjectiveQuestionNo = (TextView) itemView.findViewById(R.id.tv_subjective_question_no);
                tvSubjectiveQuestionScore = (TextView) itemView.findViewById(R.id.tv_subjective_question_score);
                tvSubjectiveQuestionComment = (TextView) itemView.findViewById(R.id.tv_subjective_question_comment);
                tvSubjectiveQuestion = (TextView) itemView.findViewById(R.id.tv_subjective_question);
                tvSubjectiveQuestionAnsTitle = (TextView) itemView.findViewById(R.id.tv_subjective_question_ans_title);
                tvSubjectiveQuestionAns = (TextView) itemView.findViewById(R.id.tv_subjective_question_ans);
                tvSubjectiveQuesEvaluationNotesTitle = (TextView) itemView.findViewById(R.id.tv_subjective_ques_evaluation_notes_title);
                tvSubjectiveQuesEvaluationNotes = (TextView) itemView.findViewById(R.id.tv_subjective_ques_evaluation_notes);
                tvScoreExcellent = (TextView) itemView.findViewById(R.id.tv_score_excellent);
                tvScoreGood = (TextView) itemView.findViewById(R.id.tv_score_good);
                tvScoreFair = (TextView) itemView.findViewById(R.id.tv_score_fair);
                tvScoreAverage = (TextView) itemView.findViewById(R.id.tv_score_average);
                tvScorePoor = (TextView) itemView.findViewById(R.id.tv_score_poor);
                tvScoreIncorrect = (TextView) itemView.findViewById(R.id.tv_score_incorrect);

                imgCopyComment = (ImageView) itemView.findViewById(R.id.img_copy_comment);
                imgDeleteComment = (ImageView) itemView.findViewById(R.id.img_delete_comment);

                etAddComment = (EditText) itemView.findViewById(R.id.et_add_comment);
                scoreTextArray = new TextView[]{tvScoreExcellent, tvScoreGood, tvScoreFair, tvScoreAverage, tvScorePoor, tvScoreIncorrect};


            } catch (Exception e) {
                Debug.e(TAG, "ViewHolder Exceptions :" + e.toString());
            }
        }
    }

    @Override
    public void onBindViewHolder(final SubjectiveQuestionListAdapter.ViewHolder holder, int position) {
        try {

            holder.tvSubjectiveQuestionNo.setTypeface(Global.myTypeFace.getRalewayBold());
            holder.tvSubjectiveQuestionScore.setTypeface(Global.myTypeFace.getRalewayBold());
            holder.tvSubjectiveQuestionComment.setTypeface(Global.myTypeFace.getRalewayBold());

            holder.tvSubjectiveQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvSubjectiveQuestionAnsTitle.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvSubjectiveQuestionAns.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvSubjectiveQuesEvaluationNotesTitle.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvSubjectiveQuesEvaluationNotes.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvScoreExcellent.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvScoreGood.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvScoreFair.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvScoreAverage.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvScorePoor.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvScoreIncorrect.setTypeface(Global.myTypeFace.getRalewayRegular());


            holder.tvSubjectiveQuestionNo.setText(mContext.getResources().getString(R.string.strquestion) + " : " + (position + 1));

            /**
             * Loading image in question
             */
            if (arrayListSubjectiveQuestions.get(position).getQuestionText().contains("img") || arrayListSubjectiveQuestions.get(position).getQuestionText().contains("http:")
                    || arrayListSubjectiveQuestions.get(position).getQuestionText().contains("https:")) {

                if (arrayListSubjectiveQuestions.get(position).getSpan() == null) {

                    arrayListSubjectiveQuestions.get(position).setSpan(Html.fromHtml(arrayListSubjectiveQuestions.get(position).getQuestionText(),
                            new HtmlImageGetter(50, 50, mContext, (HtmlImageGetter.RefreshDataAfterLoadImage) this
                            ), null));
                } else {

                    holder.tvSubjectiveQuestion.setText(Html.fromHtml(arrayListSubjectiveQuestions.get(position).getQuestionText(),
                            new HtmlImageGetter(50, 50, mContext, null
                            ), null));
                }
            } else {
                holder.tvSubjectiveQuestion.setText(Html.fromHtml(arrayListSubjectiveQuestions.get(position).getQuestionText()));
            }


            holder.tvSubjectiveQuesEvaluationNotes.setText(arrayListSubjectiveQuestions.get(position).getEvaluationNotes());


            for (int i = 0; i < holder.scoreTextArray.length; i++) {
                holder.scoreTextArray[i].setActivated(false);
                holder.scoreTextArray[i].setTag(holder.scoreTextArray);
                holder.scoreTextArray[i].setOnClickListener(evaluationClickListener);
            }

            if (evaluationList.size() > 0) {

                int evaluationIndex = checkIsQuestionEvaluated(arrayListSubjectiveQuestions.get(position).getQuestionId());
                if (evaluationIndex != -1) {
                    if (evaluationList.get(position).getStudentResponse() != null) {
                        holder.tvSubjectiveQuestionAns.setText(Utility.formatHtml(evaluationList.get(evaluationIndex).getStudentResponse()));
                    }

                    int score = Integer.valueOf(evaluationList.get(evaluationIndex).getEvaluationScore());
                    if (score >= 5) {
                        setEvaluationScore(holder.tvScoreExcellent);
                    } else if (score == 4) {
                        setEvaluationScore(holder.tvScoreGood);
                    } else if (score == 3) {
                        setEvaluationScore(holder.tvScoreFair);
                    } else if (score == 2) {
                        setEvaluationScore(holder.tvScoreAverage);
                    } else if (score == 1) {
                        setEvaluationScore(holder.tvScorePoor);
                    } else if (score == 0) {
                        setEvaluationScore(holder.tvScoreIncorrect);
                    }
                } else {


                }

            }

            holder.imgDeleteComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.etAddComment.setText("");
                }
            });

            holder.imgCopyComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(holder.etAddComment.getText());
                    Toast.makeText(mContext, "Copied", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            Debug.i(TAG, "BindViewHolder Exceptions:" + e.getLocalizedMessage());
        }

    }


    View.OnClickListener evaluationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView[] scoreTextArray = (TextView[]) v.getTag();

            for (int i = 0; i < scoreTextArray.length; i++) {
                if (v == scoreTextArray[i]) {
                    scoreTextArray[i].setActivated(true);
                } else {
                    scoreTextArray[i].setActivated(false);
                }
            }

        }
    };


    public void setEvaluationData(ArrayList<Evaluation> evaluationList) {
        this.evaluationList = evaluationList;
        notifyDataSetChanged();
    }


    private void setEvaluationScore(TextView tv) {
        tv.setActivated(true);
    }


    private int checkIsQuestionEvaluated(String questionId) {

        int evaluationIndex = -1;

        for (Evaluation evaluation : evaluationList) {
            if (evaluation.getQuestionId() != null && evaluation.getQuestionId().contains(questionId)) {
                evaluationIndex = evaluationList.indexOf(evaluation);
                break;
            }
        }

        return evaluationIndex;
    }

    @Override
    public int getItemCount() {
        return arrayListSubjectiveQuestions.size();
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) mContext).getBundle();
    }
}
