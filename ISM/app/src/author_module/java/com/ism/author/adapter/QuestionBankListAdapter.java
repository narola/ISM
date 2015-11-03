package com.ism.author.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.author.model.Data;
import com.ism.object.MyTypeFace;

import java.util.ArrayList;

/**
 * these adapter class is to set the list of questionbank.
 */
public class QuestionBankListAdapter extends RecyclerView.Adapter<QuestionBankListAdapter.ViewHolder> {


    private static final String TAG = QuestionBankListAdapter.class.getSimpleName();

    Context mContext;
    ArrayList<Data> listOfQuestions = new ArrayList<Data>();
    MyTypeFace myTypeFace;


    public QuestionBankListAdapter(Context context) {
        this.mContext = context;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        myTypeFace = new MyTypeFace(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_questionlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        holder.tv_question_no.setText(holder.tv_question_no.getText() + " " + (position + 1));
        holder.tv_question_no.setTypeface(myTypeFace.getRalewayBold());
        holder.tv_question_no.setPaintFlags(holder.tv_question_no.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        holder.tv_question_category.setTypeface(myTypeFace.getRalewayRegular());
        holder.tv_question_category.setText(holder.tv_question_category.getText());
        String category = " " + listOfQuestions.get(position).getSubjectName();
        SpannableString f = new SpannableString(category);
        f.setSpan(new ForegroundColorSpan(Color.GREEN), 0,
                category.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tv_question_category.append(f);

//        holder.tv_question_createdby.setTypeface(myTypeFace.getRalewayRegular());
//        holder.tv_question_createdby.setText(holder.tv_question_createdby.getText());
//        String created_by = " " + listOfQuestions.get(position).getQuestionCreatorName();
//        f = new SpannableString(created_by);
//        f.setSpan(new ForegroundColorSpan(Color.GREEN), 0,
//                category.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        holder.tv_question_createdby.append(f);
//
//        holder.tv_question.setTypeface(myTypeFace.getRalewayRegular());
//        holder.tv_question.setText(listOfQuestions.get(position).getQuestionText());


    }


    public void addAll(ArrayList<Data> data) {
        try {
            this.listOfQuestions.clear();
            this.listOfQuestions.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listOfQuestions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_question_no, tv_question_category, tv_question_createdby, tv_question, tv_question_ans;
        LinearLayout ll_question_answers;
        ImageView img_dropdown_view_answer, img_question_edit, img_question_copy, img_question_addtofavourite;
        CheckBox chk_select_question;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_question_no = (TextView) itemView.findViewById(R.id.tv_question_no);
            tv_question_category = (TextView) itemView.findViewById(R.id.tv_question_category);
            tv_question_createdby = (TextView) itemView.findViewById(R.id.tv_question_createdby);
            tv_question = (TextView) itemView.findViewById(R.id.tv_question);
            tv_question_ans = (TextView) itemView.findViewById(R.id.tv_question_ans);

            ll_question_answers = (LinearLayout) itemView.findViewById(R.id.ll_question_answers);

            img_dropdown_view_answer = (ImageView) itemView.findViewById(R.id.img_dropdown_view_answer);
            img_question_edit = (ImageView) itemView.findViewById(R.id.img_question_edit);
            img_question_copy = (ImageView) itemView.findViewById(R.id.img_question_copy);
            img_question_addtofavourite = (ImageView) itemView.findViewById(R.id.img_question_addtofavourite);

            chk_select_question = (CheckBox) itemView.findViewById(R.id.chk_select_question);


        }
    }
}
