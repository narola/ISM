package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.fragments.AddQuestionContainerFragment;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.ws.model.Answers;
import com.ism.teacher.ws.model.Questions;

import java.util.ArrayList;

/**
 * these adapter class is to set the list of questionbank.
 */
public class QuestionBankListAdapter extends RecyclerView.Adapter<QuestionBankListAdapter.ViewHolder> {


    private static final String TAG = QuestionBankListAdapter.class.getSimpleName();

    Context mContext;
    ArrayList<Questions> listOfQuestions = new ArrayList<>();
    MyTypeFace myTypeFace;

    Fragment mFragment;
    boolean dropdownFlag = false;

    public QuestionBankListAdapter(Context context, Fragment fragment) {
        this.mContext = context;
        this.mFragment = fragment;

    }

    public void addAll(ArrayList<Questions> data) {
        try {
            this.listOfQuestions.clear();
            this.listOfQuestions.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        myTypeFace = new MyTypeFace(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View questionListview = inflater.inflate(R.layout.row_questionlist_teacher, parent, false);
        ViewHolder viewHolder = new ViewHolder(questionListview);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionNo, tvQuestionCategory, tvQuestionCreatedby, tvQuestion, tvQuestionAns;
        LinearLayout llQuestionAnswers;
        ImageView imgDropdownViewAnswer, imgQuestionEdit, imgQuestionCopy, imgQuestionAddtofavourite;
        CheckBox chkSelectQuestion;

        public ViewHolder(View itemView) {
            super(itemView);

            tvQuestionNo = (TextView) itemView.findViewById(R.id.tv_question_no);
            tvQuestionCategory = (TextView) itemView.findViewById(R.id.tv_question_category);
            tvQuestionCreatedby = (TextView) itemView.findViewById(R.id.tv_question_createdby);
            tvQuestion = (TextView) itemView.findViewById(R.id.tv_question);
            tvQuestionAns = (TextView) itemView.findViewById(R.id.tv_question_ans);

            llQuestionAnswers = (LinearLayout) itemView.findViewById(R.id.ll_question_answers);

            imgDropdownViewAnswer = (ImageView) itemView.findViewById(R.id.img_dropdown_view_answer);
            imgQuestionEdit = (ImageView) itemView.findViewById(R.id.img_question_edit);
            imgQuestionCopy = (ImageView) itemView.findViewById(R.id.img_question_copy);
            imgQuestionAddtofavourite = (ImageView) itemView.findViewById(R.id.img_question_addtofavourite);

            chkSelectQuestion = (CheckBox) itemView.findViewById(R.id.chk_select_question);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tvQuestionNo.setText(mContext.getString(R.string.strquestion) + " " + (position + 1));
        holder.tvQuestionNo.setTypeface(myTypeFace.getRalewayBold());
        holder.tvQuestionNo.setPaintFlags(holder.tvQuestionNo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        holder.tvQuestionCategory.setTypeface(myTypeFace.getRalewayRegular());
        holder.tvQuestionCategory.setText(mContext.getString(R.string.strcategory));

        holder.tvQuestionCategory.append(Utility.getSpannableString(" " + listOfQuestions.get(position).getSubjectName(),
                mContext.getResources().getColor(R.color.color_green)));

        holder.tvQuestionCreatedby.setTypeface(myTypeFace.getRalewayRegular());
        holder.tvQuestionCreatedby.setText(mContext.getString(R.string.strcreatedby));

        holder.tvQuestionCreatedby.append(Utility.getSpannableString(" " + listOfQuestions.get(position).getQuestionCreatorName(),
                mContext.getResources().getColor(R.color.color_green)));


        holder.tvQuestion.setTypeface(myTypeFace.getRalewayRegular());
        holder.tvQuestion.setText(Utility.formatHtml(listOfQuestions.get(position).getQuestionText()));


        holder.imgDropdownViewAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!dropdownFlag) {
                    if (!listOfQuestions.get(position).getQuestionFormat().equalsIgnoreCase("mcq")) {

                        holder.tvQuestionAns.setTypeface(myTypeFace.getRalewayRegular());
                        holder.tvQuestionAns.setText(listOfQuestions.get(position).getSolution());
                        holder.tvQuestionAns.setVisibility(View.VISIBLE);

                    } else {

                        holder.llQuestionAnswers.removeAllViews();
                        if (holder.llQuestionAnswers.getChildCount() == 0) {
                            for (int i = 0; i < listOfQuestions.get(position).getAnswers().size(); i++) {
                                View ansView = getAnsInflaterView(listOfQuestions.get(position).getAnswers().get(i), i);
                                holder.llQuestionAnswers.addView(ansView);
                            }
                        }

                        holder.llQuestionAnswers.setVisibility(View.VISIBLE);

                    }
                    holder.imgDropdownViewAnswer.setBackgroundResource(R.drawable.dropdown_close);
                    dropdownFlag = true;

                } else {
                    holder.imgDropdownViewAnswer.setBackgroundResource(R.drawable.dropdown_open);
                    dropdownFlag = false;

                    if (!listOfQuestions.get(position).getQuestionFormat().equalsIgnoreCase("mcq")) {
                        holder.tvQuestionAns.setVisibility(View.GONE);
                    } else {
                        holder.llQuestionAnswers.setVisibility(View.GONE);
                        holder.llQuestionAnswers.removeAllViews();
                    }

                }

            }
        });

        holder.chkSelectQuestion.setChecked(listOfQuestions.get(position).getIsQuestionAddedInPreview());

        holder.chkSelectQuestion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (!((AddQuestionContainerFragment) mFragment).previewQuestionFragment.listOfPreviewQuestions.contains(listOfQuestions.get(position))) {


                    if (holder.chkSelectQuestion.isChecked()) {
                        listOfQuestions.get(position).setIsQuestionAddedInPreview(true);
                        ((AddQuestionContainerFragment) mFragment).listOfPreviewQuestionsToAdd.add(listOfQuestions.get(position));

                    } else {
                        listOfQuestions.get(position).setIsQuestionAddedInPreview(false);
                        ((AddQuestionContainerFragment) mFragment).listOfPreviewQuestionsToAdd.remove(listOfQuestions.get(position));
                    }

                } else {
                    listOfQuestions.get(position).setIsQuestionAddedInPreview(true);
                }
                notifyDataSetChanged();


            }
        });

        holder.imgQuestionCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((AddQuestionContainerFragment) mFragment).setQuestionData(listOfQuestions.get(position));
                ((AddQuestionContainerFragment) mFragment).setIsSetQuestionData(true);
                ((AddQuestionContainerFragment) mFragment).flipCard();

            }
        });

        holder.imgQuestionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ((AddQuestionContainerFragment) mFragment).setQuestionData(listOfQuestions.get(position));
                ((AddQuestionContainerFragment) mFragment).setIsSetQuestionData(true);
                ((AddQuestionContainerFragment) mFragment).flipCard();

            }
        });


    }


    @Override
    public int getItemCount() {
        return listOfQuestions.size();
    }


    private View getAnsInflaterView(Answers answer, int position) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v;
        v = layoutInflater.inflate(R.layout.row_mcq_question_answer, null, false);
        TextView tvMcqQuestionAns = (TextView) v.findViewById(R.id.tv_mcq_question_ans);
        tvMcqQuestionAns.setTypeface(myTypeFace.getRalewayRegular());
        tvMcqQuestionAns.setText(Utility.formatHtml(Utility.getCharForNumber(position + 1) + ": " + answer.getChoiceText()));

        return v;
    }


}
