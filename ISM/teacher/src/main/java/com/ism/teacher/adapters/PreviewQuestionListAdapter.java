package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.fragments.AddQuestionFragmentTeacher;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.model.AnswersModel;
import com.ism.teacher.model.Data;

import java.util.ArrayList;

/**
 * these adapter class is to set the list of previewquestions.
 */
public class PreviewQuestionListAdapter extends RecyclerView.Adapter<PreviewQuestionListAdapter.ViewHolder> {


    private static final String TAG = PreviewQuestionListAdapter.class.getSimpleName();

    Context mContext;
    ArrayList<Data> listOfPreviewQuestions = new ArrayList<Data>();
    MyTypeFace myTypeFace;
    Fragment mFragment;


    public PreviewQuestionListAdapter(Context context, Fragment fragment) {
        this.mContext = context;
        this.mFragment = fragment;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        myTypeFace = new MyTypeFace(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_preview_question, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tvPreviewQuestionNo.setText(mContext.getString(R.string.strquestion) + " " + (position + 1));
        holder.tvPreviewQuestionNo.setTypeface(myTypeFace.getRalewayBold());
        holder.tvPreviewQuestionNo.setPaintFlags(holder.tvPreviewQuestionNo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        holder.tvPreviewQuestion.setTypeface(myTypeFace.getRalewayRegular());
        holder.tvPreviewQuestion.setText(listOfPreviewQuestions.get(position).getQuestionText());

        if (!listOfPreviewQuestions.get(position).getQuestionFormat().equalsIgnoreCase("mcq")) {

            holder.tvPreviewQuestionAns.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvPreviewQuestionAns.setText(listOfPreviewQuestions.get(position).getSolution());


            holder.llPreviewQuestionAnswers.setVisibility(View.GONE);
            holder.tvPreviewQuestionAns.setVisibility(View.VISIBLE);


        } else {

            holder.llPreviewQuestionAnswers.removeAllViews();
            if (holder.llPreviewQuestionAnswers.getChildCount() == 0) {
                for (int i = 0; i < listOfPreviewQuestions.get(position).getAnswers().size(); i++) {
                    View ansView = getAnsInflaterView(listOfPreviewQuestions.get(position).getAnswers().get(i), i);
                    holder.llPreviewQuestionAnswers.addView(ansView);
                }
            }

            holder.llPreviewQuestionAnswers.setVisibility(View.VISIBLE);
            holder.tvPreviewQuestionAns.setVisibility(View.GONE);
        }


        holder.imgPreviewQuestionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ((AddQuestionFragmentTeacher) mFragment).questionListFragment.updateViewAfterDeleteInPreviewQuestion(listOfPreviewQuestions.get(position));
                listOfPreviewQuestions.remove(listOfPreviewQuestions.get(position));
                notifyDataSetChanged();

            }
        });


    }


    public void addAll(ArrayList<Data> data) {
        try {
//            this.listOfPreviewQuestions.clear();
            this.listOfPreviewQuestions.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return listOfPreviewQuestions.size();
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

    private View getAnsInflaterView(AnswersModel answer, int position) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v;
        v = layoutInflater.inflate(R.layout.row_mcq_question_answer, null, false);
        TextView tvMcqQuestionAns = (TextView) v.findViewById(R.id.tv_mcq_question_ans);
        tvMcqQuestionAns.setTypeface(myTypeFace.getRalewayRegular());
        tvMcqQuestionAns.setText(Utility.getCharForNumber(position + 1) + ": " + answer.getChoiceText());

        return v;
    }
}
