package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.ClipboardManager;
import android.content.Context;
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
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.model.Evaluation;
import com.ism.teacher.ws.model.Questions;

import java.util.ArrayList;

/**
 * Created by c75 on 16/11/15.
 */
public class SubjectiveQuestionListAdapter extends RecyclerView.Adapter<SubjectiveQuestionListAdapter.ViewHolder> {

    private static String TAG = SubjectiveQuestionListAdapter.class.getSimpleName();

    Fragment mFragment;
    Context context;
    public static MyTypeFace myTypeFace;
    ResponseHandler responseObject, studentEvalResObj;

    ArrayList<Evaluation> evaluationArrayList = new ArrayList<>();
    ArrayList<Questions> arrayListSubjectiveQuestions = new ArrayList<>();

    private LayoutInflater inflater;


    public SubjectiveQuestionListAdapter(ResponseHandler responseObject, Context context, Fragment fragment, ResponseHandler studentEvalResObj) {
        this.responseObject = responseObject;
        this.context = context;
        this.mFragment = fragment;
        this.studentEvalResObj = studentEvalResObj;
        myTypeFace = new MyTypeFace(context);
        addAll(responseObject.getExamQuestions().get(0).getQuestions());
    }


    public SubjectiveQuestionListAdapter(Context mContext, Fragment mFragment) {
        this.context = mContext;
        inflater = LayoutInflater.from(mContext);
        myTypeFace = new MyTypeFace(mContext);
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
        TextView tvSubjectiveQuestionNo, tvSubjectiveQuestion, tvSubjectiveQuestionAns, tvSubjectiveQuesEvaluationNotes;
        EditText etAddComment;

        //Textviews for answer rating
        TextView tvScoreExcellent, tvScoreGood, tvScoreFair, tvScoreAverage, tvScorePoor, tvScoreIncorrect;
        ImageView imgCopyComments, imgDeleteComments;

        TextView[] scoreTextArray;

        public ViewHolder(View itemView) {
            super(itemView);
            try {
                tvSubjectiveQuestionNo = (TextView) itemView.findViewById(R.id.tv_subjective_question_no);
                tvSubjectiveQuestion = (TextView) itemView.findViewById(R.id.tv_subjective_question);
                tvSubjectiveQuestionAns = (TextView) itemView.findViewById(R.id.tv_subjective_question_ans);
                tvSubjectiveQuesEvaluationNotes = (TextView) itemView.findViewById(R.id.tv_subjective_ques_evaluation_notes);
                etAddComment = (EditText) itemView.findViewById(R.id.et_add_comment);

                tvScoreExcellent = (TextView) itemView.findViewById(R.id.tv_score_excellent);
                tvScoreGood = (TextView) itemView.findViewById(R.id.tv_score_good);
                tvScoreFair = (TextView) itemView.findViewById(R.id.tv_score_fair);
                tvScoreAverage = (TextView) itemView.findViewById(R.id.tv_score_average);
                tvScorePoor = (TextView) itemView.findViewById(R.id.tv_score_poor);
                tvScoreIncorrect = (TextView) itemView.findViewById(R.id.tv_score_incorrect);

                imgCopyComments = (ImageView) itemView.findViewById(R.id.img_copy_comment);
                imgDeleteComments = (ImageView) itemView.findViewById(R.id.img_delete_comment);

                tvSubjectiveQuestionNo.setTypeface(myTypeFace.getRalewayBold());
                tvSubjectiveQuestion.setTypeface(myTypeFace.getRalewayRegular());
                tvSubjectiveQuestionAns.setTypeface(myTypeFace.getRalewayThin());


                tvScoreExcellent.setTypeface(myTypeFace.getRalewayRegular());
                tvScoreGood.setTypeface(myTypeFace.getRalewayRegular());
                tvScoreFair.setTypeface(myTypeFace.getRalewayRegular());
                tvScoreAverage.setTypeface(myTypeFace.getRalewayRegular());
                tvScorePoor.setTypeface(myTypeFace.getRalewayRegular());
                tvScoreIncorrect.setTypeface(myTypeFace.getRalewayRegular());

                scoreTextArray = new TextView[]{tvScoreExcellent, tvScoreGood, tvScoreFair, tvScoreAverage, tvScorePoor, tvScoreIncorrect};


            } catch (Exception e) {
                Debug.i(TAG, "ViewHolder Exceptions :" + e.toString());
            }


        }
    }

    @Override
    public void onBindViewHolder(final SubjectiveQuestionListAdapter.ViewHolder holder, int position) {
        try {
//            ArrayList<Data> arrayList = new ArrayList<Data>();
//            arrayList = responseHandler.getData().get(0).getQuestions();
            int qno = position + 1;
            //holder.tvSubjectiveQuestionNo.setText("Questions " + qno);
            holder.tvSubjectiveQuestionNo.setText(Html.fromHtml("QUESTION: " + qno));

            holder.tvSubjectiveQuestion.setText(arrayListSubjectiveQuestions.get(position).getQuestionText());

            if (studentEvalResObj != null) {

                evaluationArrayList = studentEvalResObj.getExamEvaluation().get(0).getEvaluation();
                if (evaluationArrayList.size() > 0) {

//                    if (evaluationList.get(position).getStudentResponse() != null) {
//                        holder.tvSubjectiveQuestionAns.setText(Utility.formatHtml(evaluationList.get(position).getStudentResponse()));
//                    }
                    if (arrayListSubjectiveQuestions.get(position).getQuestionId().equalsIgnoreCase(evaluationArrayList.get(position).getQuestionId())) {
                        holder.tvSubjectiveQuestionAns.setText(evaluationArrayList.get(position).getStudentResponse());
                    }

                    for (int i = 0; i < holder.scoreTextArray.length; i++) {
                        holder.scoreTextArray[i].setActivated(false);
                    }

                    int score = Integer.valueOf(evaluationArrayList.get(position).getEvaluationScore());
                    if (score >= 5) {
                        setEvaluationSCore(holder.tvScoreExcellent);
                    } else if (score == 4) {
                        setEvaluationSCore(holder.tvScoreGood);
                    } else if (score == 3) {
                        setEvaluationSCore(holder.tvScoreFair);
                    } else if (score == 2) {
                        setEvaluationSCore(holder.tvScoreAverage);
                    } else if (score == 1) {
                        setEvaluationSCore(holder.tvScorePoor);
                    } else if (score == 0) {
                        setEvaluationSCore(holder.tvScoreIncorrect);
                    }

                }

                notifyDataSetChanged();

            }

            holder.tvScoreExcellent.setTag(holder.scoreTextArray);
            holder.tvScoreExcellent.setOnClickListener(evaluationClickListener);
            holder.tvScoreGood.setTag(holder.scoreTextArray);
            holder.tvScoreGood.setOnClickListener(evaluationClickListener);
            holder.tvScoreFair.setTag(holder.scoreTextArray);
            holder.tvScoreFair.setOnClickListener(evaluationClickListener);
            holder.tvScoreAverage.setTag(holder.scoreTextArray);
            holder.tvScoreAverage.setOnClickListener(evaluationClickListener);
            holder.tvScorePoor.setTag(holder.scoreTextArray);
            holder.tvScorePoor.setOnClickListener(evaluationClickListener);
            holder.tvScoreIncorrect.setTag(holder.scoreTextArray);
            holder.tvScoreIncorrect.setOnClickListener(evaluationClickListener);

            holder.imgDeleteComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.etAddComment.setText("");

                }
            });

            holder.imgCopyComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(holder.etAddComment.getText());
                    Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            Debug.i(TAG, "BindViewHolder Exceptions :" + e.getLocalizedMessage());
        }
    }

    private void setEvaluationSCore(TextView tv) {
        tv.setActivated(true);
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

    @Override
    public int getItemCount() {
         return arrayListSubjectiveQuestions.size();
    }


    ArrayList<Evaluation> evaluationList = new ArrayList<Evaluation>();

    public void setEvaluationData(ArrayList<Evaluation> evaluationList) {
        this.evaluationList = evaluationList;
        notifyDataSetChanged();
    }
}
