package com.ism.author.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.Data;

import java.util.ArrayList;

/**
 * Created by c166 on 17/11/15.
 */
public class SubjectiveQuestionListAdapter extends RecyclerView.Adapter<SubjectiveQuestionListAdapter.ViewHolder> {

    private static final String TAG = SubjectiveQuestionListAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<Data> listOfQuestions = new ArrayList<Data>();
    private MyTypeFace myTypeFace;
    private LayoutInflater inflater;
    private Fragment mFragment;


    public SubjectiveQuestionListAdapter(Context mContext, Fragment mFragment) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        myTypeFace = new MyTypeFace(mContext);
        this.mFragment = mFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_subjective_assignment_questions, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {

            holder.tv_subjective_question_no.setTypeface(myTypeFace.getRalewayBold());
            holder.tv_subjective_question_score.setTypeface(myTypeFace.getRalewayBold());
            holder.tv_subjective_question_comment.setTypeface(myTypeFace.getRalewayBold());

            holder.tv_subjective_question.setTypeface(myTypeFace.getRalewayRegular());
            holder.tv_subjective_question_ans_title.setTypeface(myTypeFace.getRalewayRegular());
            holder.tv_subjective_question_ans.setTypeface(myTypeFace.getRalewayRegular());
            holder.tv_subjective_ques_evaluation_notes_title.setTypeface(myTypeFace.getRalewayRegular());
            holder.tv_subjective_ques_evaluation_notes.setTypeface(myTypeFace.getRalewayRegular());
            holder.tv_score_excellent.setTypeface(myTypeFace.getRalewayRegular());
            holder.tv_score_good.setTypeface(myTypeFace.getRalewayRegular());
            holder.tv_score_fair.setTypeface(myTypeFace.getRalewayRegular());
            holder.tv_score_average.setTypeface(myTypeFace.getRalewayRegular());
            holder.tv_score_poor.setTypeface(myTypeFace.getRalewayRegular());
            holder.tv_score_incorrect.setTypeface(myTypeFace.getRalewayRegular());


        } catch (Exception e) {
            Debug.i(TAG, "BindViewHolder Exceptions:" + e.getLocalizedMessage());
        }

    }

    @Override
    public int getItemCount() {
        return listOfQuestions.size();
    }

    public void addAll(ArrayList<Data> data) {
        try {
            this.listOfQuestions.clear();
            this.listOfQuestions.addAll(data);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_subjective_question_no, tv_subjective_question_score, tv_subjective_question_comment, tv_subjective_question,
                tv_subjective_question_ans_title, tv_subjective_question_ans, tv_subjective_ques_evaluation_notes_title,
                tv_subjective_ques_evaluation_notes, tv_score_excellent, tv_score_good, tv_score_fair, tv_score_average, tv_score_poor,
                tv_score_incorrect;
        ImageView img_copy_comment, img_delete_comment;
        EditText et_add_comment;


        public ViewHolder(View itemView) {
            super(itemView);
            try {
                tv_subjective_question_no = (TextView) itemView.findViewById(R.id.tv_subjective_question_no);
                tv_subjective_question_score = (TextView) itemView.findViewById(R.id.tv_subjective_question_score);
                tv_subjective_question_comment = (TextView) itemView.findViewById(R.id.tv_subjective_question_comment);
                tv_subjective_question = (TextView) itemView.findViewById(R.id.tv_subjective_question);
                tv_subjective_question_ans_title = (TextView) itemView.findViewById(R.id.tv_subjective_question_ans_title);
                tv_subjective_question_ans = (TextView) itemView.findViewById(R.id.tv_subjective_question_ans);
                tv_subjective_ques_evaluation_notes_title = (TextView) itemView.findViewById(R.id.tv_subjective_ques_evaluation_notes_title);
                tv_subjective_ques_evaluation_notes = (TextView) itemView.findViewById(R.id.tv_subjective_ques_evaluation_notes);
                tv_score_excellent = (TextView) itemView.findViewById(R.id.tv_score_excellent);
                tv_score_good = (TextView) itemView.findViewById(R.id.tv_score_good);
                tv_score_fair = (TextView) itemView.findViewById(R.id.tv_score_fair);
                tv_score_average = (TextView) itemView.findViewById(R.id.tv_score_average);
                tv_score_poor = (TextView) itemView.findViewById(R.id.tv_score_poor);
                tv_score_incorrect = (TextView) itemView.findViewById(R.id.tv_score_incorrect);

                img_copy_comment = (ImageView) itemView.findViewById(R.id.img_copy_comment);
                img_delete_comment = (ImageView) itemView.findViewById(R.id.img_delete_comment);

                et_add_comment = (EditText) itemView.findViewById(R.id.et_add_comment);


            } catch (Exception e) {
                Debug.e(TAG, "ViewHolder Exceptions :" + e.toString());
            }
        }
    }
}
