package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.fragments.SubjectiveQuestionsContainerFragment;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.model.QuestionPalette;
import com.ism.teacher.ws.model.Questions;

import java.util.ArrayList;


public class QuestionPaletteAdapter extends RecyclerView.Adapter<QuestionPaletteAdapter.ViewHolder> {

    private static final String TAG = QuestionPaletteAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Questions> arrListQuestions = new ArrayList<Questions>();
    private ArrayList<QuestionPalette> arrListQuestionPalette = new ArrayList<QuestionPalette>();
    private Fragment mFragment;
    private LayoutInflater inflater;

    public QuestionPaletteAdapter(Context mContext, Fragment mFragment) {
        this.mContext = mContext;
        this.mFragment = mFragment;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = inflater.inflate(R.layout.row_question_palette, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            holder.tvQuestionStatus.setText("" + (position + 1));
            holder.tvQuestionStatus.setTypeface(Global.myTypeFace.getRalewayRegular());


            setQuestionStatus(holder.tvQuestionStatus, getQuestionStatus(arrListQuestions.get(position).getQuestionId()));

            holder.tvQuestionStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getBaseFragment().scrollToSpecificQuestion(position);

                }
            });
        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }

    }

    private void setQuestionStatus(TextView tv, String status) {

        try {
            switch (status) {
                case "A":
                    tv.setBackgroundResource(R.drawable.img_answered);
                    break;
                case "N":
                    tv.setBackgroundResource(R.drawable.img_not_answered);
                    break;
                case "S":
                    tv.setText("");
                    tv.setBackgroundResource(R.drawable.img_skipped);
                    break;
                case "R":
                    tv.setBackgroundResource(R.drawable.img_skipped);
                    break;
            }

        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }

    }

    private String getQuestionStatus(String questionId) {
        String questionStatus = "N";


        for (QuestionPalette questionPalette : arrListQuestionPalette) {

            if (questionPalette.getQuestionId().equals(questionId)) {
                questionStatus = questionPalette.getValue();
                break;
            }
        }

        return questionStatus;
    }

    @Override
    public int getItemCount() {
        return arrListQuestions.size();
    }

    public void addAll(ArrayList<Questions> arrListQuestions, ArrayList<QuestionPalette> arrListQuestionPalette) {
        try {

            this.arrListQuestions.clear();
            this.arrListQuestions.addAll(arrListQuestions);
            this.arrListQuestionPalette.clear();
            this.arrListQuestionPalette.addAll(arrListQuestionPalette);

        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestionStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvQuestionStatus = (TextView) itemView.findViewById(R.id.tv_question_status);

        }
    }

    private SubjectiveQuestionsContainerFragment getBaseFragment() {
        return (SubjectiveQuestionsContainerFragment) mFragment;
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) mContext).getBundle();
    }
}
