package com.ism.teacher.adapters.tutorial;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.fragments.tutorial.scheduleexam.ScheduleTutorialExamContainerFragment;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.model.Answers;
import com.ism.teacher.ws.model.Questions;

import java.util.ArrayList;

/**
 * these adapter class is to set the list of previewquestions.
 */
public class TutorialPreviewQuestionListAdapter extends RecyclerView.Adapter<TutorialPreviewQuestionListAdapter.ViewHolder> {

    private static final String TAG = TutorialPreviewQuestionListAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Questions> arrListQuestions = new ArrayList<Questions>();
    Fragment mFragment;
    private LayoutInflater inflater;


    public TutorialPreviewQuestionListAdapter(Context context, Fragment fragment) {
        this.mContext = context;
        this.mFragment = fragment;
        inflater = LayoutInflater.from(mContext);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = inflater.inflate(R.layout.row_preview_question, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tvPreviewQuestionNo.setText(mContext.getString(R.string.strquestion) + " " + (position + 1));
        holder.tvPreviewQuestionNo.setTypeface(Global.myTypeFace.getRalewayBold());
        holder.tvPreviewQuestionNo.setPaintFlags(holder.tvPreviewQuestionNo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        holder.tvPreviewQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());
        holder.tvPreviewQuestion.setText(Utility.formatHtml(arrListQuestions.get(position).getQuestionText()));

        if (arrListQuestions.get(position).getQuestionCreatorId().equals(WebConstants.USER_ID_370)) {
            holder.imgPreviewQuestionEdit.setVisibility(View.VISIBLE);
        } else {
            holder.imgPreviewQuestionEdit.setVisibility(View.GONE);
        }

        if (!arrListQuestions.get(position).getQuestionFormat().equalsIgnoreCase("mcq")) {

            holder.tvPreviewQuestionAns.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvPreviewQuestionAns.setText(arrListQuestions.get(position).getSolution());


            holder.llPreviewQuestionAnswers.setVisibility(View.GONE);
            holder.tvPreviewQuestionAns.setVisibility(View.VISIBLE);


        } else {

            holder.llPreviewQuestionAnswers.removeAllViews();
            if (holder.llPreviewQuestionAnswers.getChildCount() == 0) {

                if (arrListQuestions.get(position).getAnswers() != null) {
                    for (int i = 0; i < arrListQuestions.get(position).getAnswers().size(); i++) {
                        View ansView = getAnsInflaterView(arrListQuestions.get(position).getAnswers().get(i), i);
                        holder.llPreviewQuestionAnswers.addView(ansView);
                    }
                }
            }

            holder.llPreviewQuestionAnswers.setVisibility(View.VISIBLE);
            holder.tvPreviewQuestionAns.setVisibility(View.GONE);
        }


        holder.imgPreviewQuestionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragment().updateQuestionListviewAfterDeleteQuestionInPreview(arrListQuestions.get(position));
                arrListQuestions.remove(arrListQuestions.get(position));
                notifyDataSetChanged();
            }
        });

        holder.imgPreviewQuestionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddEditQuestionFragment(position, false);
            }
        });

        holder.imgPreviewQuestionCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddEditQuestionFragment(position, true);
            }
        });



    }

    private void openAddEditQuestionFragment(int position, Boolean isCopy) {
        getFragment().setDataOnFragmentFlip(arrListQuestions.get(position), true, isCopy);
    }


    public void addAll(ArrayList<Questions> data) {
        try {
            this.arrListQuestions.clear();
            this.arrListQuestions.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return arrListQuestions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPreviewQuestionNo, tvPreviewQuestion, tvPreviewQuestionAns;
        LinearLayout llPreviewQuestionAnswers;
        ImageView imgPreviewQuestionMove, imgPreviewQuestionEdit, imgPreviewQuestionCopy, imgPreviewQuestionDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            tvPreviewQuestionNo = (TextView) itemView.findViewById(R.id.tv_preview_question_no);
            tvPreviewQuestion = (TextView) itemView.findViewById(R.id.tv_preview_question);
            tvPreviewQuestionAns = (TextView) itemView.findViewById(R.id.tv_preview_question_ans);

            llPreviewQuestionAnswers = (LinearLayout) itemView.findViewById(R.id.ll_preview_question_answers);

            imgPreviewQuestionMove = (ImageView) itemView.findViewById(R.id.img_preview_question_move);
            imgPreviewQuestionEdit = (ImageView) itemView.findViewById(R.id.img_preview_question_edit);
            imgPreviewQuestionCopy = (ImageView) itemView.findViewById(R.id.img_preview_question_copy);
            imgPreviewQuestionDelete = (ImageView) itemView.findViewById(R.id.img_preview_question_delete);


        }
    }

    private View getAnsInflaterView(Answers answer, int position) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v;
        v = layoutInflater.inflate(R.layout.row_mcq_question_answer, null, false);
        TextView tvMcqQuestionAns = (TextView) v.findViewById(R.id.tv_mcq_question_ans);
        tvMcqQuestionAns.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvMcqQuestionAns.setText(Utility.formatHtml(Utility.getCharForNumber(position + 1) + ": " + answer.getChoiceText()));

        return v;
    }

    private ScheduleTutorialExamContainerFragment getFragment() {
        return (ScheduleTutorialExamContainerFragment) mFragment;
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) mContext).getBundle();
    }
}
