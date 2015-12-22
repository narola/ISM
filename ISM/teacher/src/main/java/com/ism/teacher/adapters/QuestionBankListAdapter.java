package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.fragments.AddQuestionContainerFragment;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.model.Answers;
import com.ism.teacher.ws.model.Questions;

import java.util.ArrayList;

/**
 * these adapter class is to set the list of questionbank.
 */
public class QuestionBankListAdapter extends RecyclerView.Adapter<QuestionBankListAdapter.ViewHolder> {


    private static final String TAG = QuestionBankListAdapter.class.getSimpleName();

    Context mContext;
    ArrayList<Questions> arrListQuestions = new ArrayList<>();
    ArrayList<Questions> copyListOfQuestions = new ArrayList<>();

    public int MAX_LINES_VISIBLE = 6;
    Fragment mFragment;
    public Boolean canAddToPreview = false;

    public QuestionBankListAdapter(Context context, Fragment fragment) {
        this.mContext = context;
        this.mFragment = fragment;

    }

    public void addAll(ArrayList<Questions> data) {
        try {
            this.copyListOfQuestions = data;
            this.arrListQuestions.clear();
            this.arrListQuestions.addAll(data);

        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View questionListview = inflater.inflate(R.layout.row_questionlist_teacher, parent, false);
        ViewHolder viewHolder = new ViewHolder(questionListview);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionNo, tvQuestionCategory, tvQuestionCreatedby, tvQuestionAns, tvQuestion;
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
        holder.tvQuestionNo.setTypeface(Global.myTypeFace.getRalewayBold());
        holder.tvQuestionNo.setPaintFlags(holder.tvQuestionNo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        holder.tvQuestionCategory.setTypeface(Global.myTypeFace.getRalewayRegular());
        holder.tvQuestionCategory.setText(mContext.getString(R.string.strcategory));

        holder.tvQuestionCategory.append(Utility.getSpannableString(" " + arrListQuestions.get(position).getSubjectName(),
                mContext.getResources().getColor(R.color.color_green)));

        holder.tvQuestionCreatedby.setTypeface(Global.myTypeFace.getRalewayRegular());
        holder.tvQuestionCreatedby.setText(mContext.getString(R.string.strcreatedby));

        holder.tvQuestionCreatedby.append(Utility.getSpannableString(" " + arrListQuestions.get(position).getQuestionCreatorName(),
                mContext.getResources().getColor(R.color.color_green)));
        holder.tvQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());


        holder.tvQuestion.setText(Utility.formatHtml(arrListQuestions.get(position).getQuestionText()));
        /**
         * hide the edit question
         */

        if (arrListQuestions.get(position).getQuestionCreatorId().equalsIgnoreCase(WebConstants.USER_ID_370)) {
            holder.imgQuestionEdit.setVisibility(View.VISIBLE);
        } else {
            holder.imgQuestionEdit.setVisibility(View.GONE);
        }

        /**
         * For inflating options for A,B,C,D....for question options
         */

        if (!arrListQuestions.get(position).getQuestionFormat().equalsIgnoreCase("mcq")) {
            holder.tvQuestionAns.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvQuestionAns.setText(arrListQuestions.get(position).getSolution());
        } else {
            holder.llQuestionAnswers.removeAllViews();
            if (holder.llQuestionAnswers.getChildCount() == 0) {
                for (int i = 0; i < arrListQuestions.get(position).getAnswers().size(); i++) {
                    View ansView = getAnsInflaterView(arrListQuestions.get(position).getAnswers().get(i), i);
                    holder.llQuestionAnswers.addView(ansView);
                }

            }
        }

        /**
         * For managing visibility of options (based on mcq or descriptive.
         * If mcq then options will be visible
         */

        holder.imgDropdownViewAnswer.setSelected(arrListQuestions.get(position).getIsDropdownOpen());
        holder.imgDropdownViewAnswer.setActivated(arrListQuestions.get(position).getIsDropdownOpen());
        if (arrListQuestions.get(position).getIsDropdownOpen()) {
            if (!arrListQuestions.get(position).getQuestionFormat().equalsIgnoreCase("mcq")) {
                holder.tvQuestionAns.setVisibility(View.VISIBLE);
                holder.llQuestionAnswers.setVisibility(View.GONE);
            } else {
                holder.tvQuestionAns.setVisibility(View.GONE);
                holder.llQuestionAnswers.setVisibility(View.VISIBLE);
            }
        } else {
            holder.tvQuestionAns.setVisibility(View.GONE);
            holder.llQuestionAnswers.setVisibility(View.GONE);
        }


        holder.imgDropdownViewAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.imgDropdownViewAnswer.setSelected(!holder.imgDropdownViewAnswer.isSelected());
                arrListQuestions.get(position).setIsDropdownOpen(holder.imgDropdownViewAnswer.isSelected());
                holder.imgDropdownViewAnswer.setActivated(holder.imgDropdownViewAnswer.isSelected());

                if (holder.imgDropdownViewAnswer.isActivated()) {
                    holder.tvQuestion.setMaxLines(Integer.MAX_VALUE);
                    holder.tvQuestion.setEllipsize(null);

                } else {
                    holder.tvQuestion.setMaxLines(MAX_LINES_VISIBLE);
                    holder.tvQuestion.setEllipsize(TextUtils.TruncateAt.END);

                }

                notifyDataSetChanged();
            }
        });

        holder.chkSelectQuestion.setChecked(arrListQuestions.get(position).getIsQuestionAddedInPreview());

        holder.chkSelectQuestion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (canAddToPreview) {

                    if (arrListQuestions.get(position).getQuestionFormat().equalsIgnoreCase(mContext.getString(R.string.strquestionformatmcq))) {

                        if (getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_MODE).equalsIgnoreCase
                                (mContext.getString(R.string.strobjective))) {
                            isValidationForAddToPreview(arrListQuestions.get(position), holder.chkSelectQuestion);
                        } else {
                            Utility.showToast(mContext.getString(R.string.msg_validation_addsubjective_question), mContext);
                        }

                    } else if (arrListQuestions.get(position).getQuestionFormat().equalsIgnoreCase
                            (mContext.getString(R.string.strquestionformatdescriptive))) {

                        if (getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_MODE).equalsIgnoreCase
                                (mContext.getString(R.string.strsubjective))) {
                            isValidationForAddToPreview(arrListQuestions.get(position), holder.chkSelectQuestion);
                        } else {
                            Utility.showToast(mContext.getString(R.string.msg_validation_addobjective_question), mContext);
                        }


                    } else if (arrListQuestions.get(position).getQuestionFormat().equalsIgnoreCase
                            (mContext.getString(R.string.strquestionformatfillups))) {
                        if (getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_MODE).equalsIgnoreCase
                                (mContext.getString(R.string.strsubjective))) {
                            isValidationForAddToPreview(arrListQuestions.get(position), holder.chkSelectQuestion);
                        } else {
                            Utility.showToast(mContext.getString(R.string.msg_validation_addobjective_question), mContext);
                        }
                    }
                    notifyDataSetChanged();
                } else {
                    holder.chkSelectQuestion.setChecked(arrListQuestions.get(position).getIsQuestionAddedInPreview());
                    Utility.showToast(mContext.getString(R.string.msg_validation_add_question), mContext);
                }

            }
        });

        holder.imgQuestionCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddEditQuestionFragment(position, true);

            }
        });

        holder.imgQuestionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openAddEditQuestionFragment(position, false);

            }
        });

    }


    private void openAddEditQuestionFragment(int position, Boolean isCopy) {
        getFragment().setDataOnFragmentFlip(arrListQuestions.get(position), true, isCopy);
    }

    @Override
    public int getItemCount() {
        return arrListQuestions.size();
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

    public void filter(CharSequence charText) {
        arrListQuestions.clear();
        if (charText.length() == 0) {
            arrListQuestions.addAll(copyListOfQuestions);
        } else {
            for (Questions wp : copyListOfQuestions) {
                if (Utility.containsString(wp.getQuestionText(), charText.toString(), false)) {
                    arrListQuestions.add(wp);
                }
            }
            if (arrListQuestions.size() == 0) {
            }
        }
        notifyDataSetChanged();
    }

    private AddQuestionContainerFragment getFragment() {
        return (AddQuestionContainerFragment) mFragment;
    }


    private void isValidationForAddToPreview(Questions question, CheckBox checkBox) {
        if (!checkForQuestionPresence(question.getQuestionId())) {
            if (checkBox.isChecked()) {
                question.setIsQuestionAddedInPreview(true);
                getFragment().getListOfPreviewQuestionsToAdd().add(question);
            } else {
                question.setIsQuestionAddedInPreview(false);
                getFragment().getListOfPreviewQuestionsToAdd().remove(question);
            }
        } else {
            question.setIsQuestionAddedInPreview(true);
        }
    }

    private Boolean checkForQuestionPresence(String questionId) {
        Boolean isPresent = false;
        for (Questions question : getFragment().getListOfPreviewQuestion()) {
            if (question.getQuestionId().equals(questionId)) {
                isPresent = true;
                break;
            }

        }
        return isPresent;
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) mContext).getBundle();
    }
}
