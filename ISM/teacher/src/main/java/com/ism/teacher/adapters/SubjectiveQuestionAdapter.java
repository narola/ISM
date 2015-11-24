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
import com.ism.teacher.ws.helper.ResponseHandler;

import java.util.ArrayList;

/**
 * Created by c75 on 16/11/15.
 */
public class SubjectiveQuestionAdapter extends RecyclerView.Adapter<SubjectiveQuestionAdapter.ViewHolder> {

    private static String TAG = SubjectiveQuestionAdapter.class.getSimpleName();

    Fragment mFragment;
    Context context;
    public static MyTypeFace myTypeFace;
    ResponseHandler responseObject, studentEvalResObj;

    ArrayList<Data> dataArrayList = new ArrayList<Data>();
    ArrayList<Data> arrayListSubjectiveQuestions = new ArrayList<Data>();

    //Boolean flags
    public boolean flag_excellent = false;
    public boolean flag_good = false;
    public boolean flag_fair = false;
    public boolean flag_average = false;
    public boolean flag_poor = false;
    public boolean flag_incorrect = false;

    public SubjectiveQuestionAdapter(ResponseHandler responseObject, Context context, Fragment fragment, ResponseHandler studentEvalResObj) {
        this.responseObject = responseObject;
        this.context = context;
        this.mFragment = fragment;
        this.studentEvalResObj = studentEvalResObj;
        myTypeFace = new MyTypeFace(context);
        addAll(responseObject.getData().get(0).getQuestions());
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
        TextView tvSubjectiveQuestionNo, tvSubjectiveQuestion, tvSubjectiveQuestionAns, tvSubjectiveQuesEvaluationNotes;
        EditText etAddComment;

        //Textviews for answer rating
        TextView tvScoreExcellent, tvScoreGood, tvScoreFair, tvScoreAverage, tvScorePoor, tvScoreIncorrect;
        ImageView imgCopyComments, imgDeleteComments;

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


            } catch (Exception e) {
                Debug.i(TAG, "ViewHolder Exceptions :" + e.toString());
            }


        }
    }

    @Override
    public void onBindViewHolder(final SubjectiveQuestionAdapter.ViewHolder holder, int position) {
        try {
//            ArrayList<Data> arrayList = new ArrayList<Data>();
//            arrayList = responseObject.getData().get(0).getQuestions();
            int qno = position + 1;
            //holder.tvSubjectiveQuestionNo.setText("Questions " + qno);
            holder.tvSubjectiveQuestionNo.setText(Html.fromHtml("QUESTION: " + qno));

            holder.tvSubjectiveQuestion.setText(arrayListSubjectiveQuestions.get(position).getQuestionText());

            if (studentEvalResObj != null) {
                dataArrayList = studentEvalResObj.getData().get(0).getArrayListEvaluation();

                if (arrayListSubjectiveQuestions.get(position).getQuestionId().equalsIgnoreCase(dataArrayList.get(position).getQuestionId())) {
                    holder.tvSubjectiveQuestionAns.setText(dataArrayList.get(position).getStudentResponse());
                    holder.tvSubjectiveQuesEvaluationNotes.setText(dataArrayList.get(position).getEvaluationNotes());
                }
                notifyDataSetChanged();

            }


        } catch (Exception e) {
            Debug.i(TAG, "BindViewHolder Exceptions :" + e.getLocalizedMessage());
        }
    }


    @Override
    public int getItemCount() {
        return responseObject.getData().get(0).getQuestions().size();
    }
}
