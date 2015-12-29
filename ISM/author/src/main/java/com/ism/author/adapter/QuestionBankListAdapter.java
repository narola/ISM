package com.ism.author.adapter;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.HtmlImageGetter;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.fragment.createquestion.AddQuestionContainerFragment;
import com.ism.author.object.Global;
import com.ism.author.ws.model.Answers;
import com.ism.author.ws.model.Questions;

import java.util.ArrayList;

/**
 * these adapter class is to set the list of questionbank.
 */
public class QuestionBankListAdapter extends RecyclerView.Adapter<QuestionBankListAdapter.ViewHolder> implements HtmlImageGetter.RefreshDataAfterLoadImage {


    private static final String TAG = QuestionBankListAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<Questions> arrListQuestions = new ArrayList<Questions>();
    Fragment mFragment;
    private LayoutInflater inflater;

    public int MAX_LINES_VISIBLE = 6;

    public QuestionBankListAdapter(Context context, Fragment fragment) {
        this.mContext = context;
        this.mFragment = fragment;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = inflater.inflate(R.layout.row_questionlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        try {
            holder.tvQuestionNo.setText(mContext.getString(R.string.strquestion) + " " + (position + 1));
            holder.tvQuestionNo.setTypeface(Global.myTypeFace.getRalewayBold());
            holder.tvQuestionNo.setPaintFlags(holder.tvQuestionNo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            holder.tvQuestionBookName.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvQuestionBookName.setText(mContext.getString(R.string.strbook));

            holder.tvQuestionBookName.append(Utility.getSpannableString(" " + arrListQuestions.get(position).getBookName(),
                    mContext.getResources().getColor(R.color.color_green)));

            holder.tvQuestionCreatedby.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvQuestionCreatedby.setText(mContext.getString(R.string.strcreatedby));

            holder.tvQuestionCreatedby.append(Utility.getSpannableString(" " + arrListQuestions.get(position).getQuestionCreatorName(),
                    mContext.getResources().getColor(R.color.color_green)));


            holder.tvQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());


            if (arrListQuestions.get(position).getSpan() == null) {

                arrListQuestions.get(position).setSpan(Html.fromHtml(arrListQuestions.get(position).getQuestionText(),
                        new HtmlImageGetter(50, 50, mContext, (HtmlImageGetter.RefreshDataAfterLoadImage) this
                        ), null));
            } else {

                holder.tvQuestion.setText(Html.fromHtml(arrListQuestions.get(position).getQuestionText(),
                        new HtmlImageGetter(50, 50, mContext, null
                        ), null));
            }

            if (arrListQuestions.get(position).getQuestionCreatorId().equals(Global.strUserId)) {
                holder.imgQuestionEdit.setVisibility(View.VISIBLE);
            } else {
                holder.imgQuestionEdit.setVisibility(View.GONE);
            }

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

                            if (getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_MODE).equalsIgnoreCase
                                    (mContext.getString(R.string.strobjective))) {
                                isValidationForAddToPreview(arrListQuestions.get(position), holder.chkSelectQuestion);
                            } else {
                                Utility.alert(mContext, null, mContext.getString(R.string.msg_validation_addsubjective_question));
                            }

                        } else if (arrListQuestions.get(position).getQuestionFormat().equalsIgnoreCase
                                (mContext.getString(R.string.strquestionformatdescriptive))) {

                            if (getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_MODE).equalsIgnoreCase
                                    (mContext.getString(R.string.strsubjective))) {
                                isValidationForAddToPreview(arrListQuestions.get(position), holder.chkSelectQuestion);
                            } else {
                                Utility.alert(mContext, null, mContext.getString(R.string.msg_validation_addobjective_question));
                            }


                        } else if (arrListQuestions.get(position).getQuestionFormat().equalsIgnoreCase
                                (mContext.getString(R.string.strquestionformatfillups))) {
                            if (getBaseFragment().getBundleArguments().getString(ExamsAdapter.ARG_EXAM_MODE).equalsIgnoreCase
                                    (mContext.getString(R.string.strsubjective))) {
                                isValidationForAddToPreview(arrListQuestions.get(position), holder.chkSelectQuestion);
                            } else {
                                Utility.alert(mContext, null, mContext.getString(R.string.msg_validation_addobjective_question));
                            }
                        }
                        notifyDataSetChanged();
                    } else {

                        holder.chkSelectQuestion.setChecked(arrListQuestions.get(position).getIsQuestionAddedInPreview());
                        Utility.alert(mContext, null, mContext.getString(R.string.msg_validation_add_question));

                    }

                }
            });

            holder.imgQuestionEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openAddEditQuestionFragment(position, false);
                }
            });

            holder.imgQuestionCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openAddEditQuestionFragment(position, true);
                }
            });


        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }

    }


    private void isValidationForAddToPreview(Questions question, CheckBox checkBox) {
        if (!checkForQuestionPresence(question.getQuestionId())) {
            if (checkBox.isChecked()) {
                question.setIsQuestionAddedInPreview(true);
                getBaseFragment().getListOfPreviewQuestionsToAdd().add(question);
            } else {
                question.setIsQuestionAddedInPreview(false);
                getBaseFragment().getListOfPreviewQuestionsToAdd().remove(question);
            }
        } else {
            question.setIsQuestionAddedInPreview(true);
        }
    }

    private Boolean checkForQuestionPresence(String questionId) {
        Boolean isPresent = false;
        for (Questions question : getBaseFragment().getListOfPreviewQuestion()) {
            if (question.getQuestionId().equals(questionId)) {
                isPresent = true;
                break;
            }

        }
        return isPresent;
    }

    private void openAddEditQuestionFragment(int position, Boolean isCopy) {
        getBaseFragment().setDataOnFragmentFlip(arrListQuestions.get(position), true, isCopy);
    }


    public void addAll(ArrayList<Questions> arrListQuestionBank) {
        try {
            this.copyListOfQuestions = arrListQuestionBank;
            this.arrListQuestions.clear();
            this.arrListQuestions.addAll(arrListQuestionBank);
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

        TextView tvQuestionNo, tvQuestionBookName, tvQuestionCreatedby, tvQuestion, tvQuestionAns;
        LinearLayout llQuestionAnswers;
        ImageView imgDropdownViewAnswer, imgQuestionEdit, imgQuestionCopy, imgQuestionAddtofavourite;
        CheckBox chkSelectQuestion;

        public ViewHolder(View itemView) {
            super(itemView);

            tvQuestionNo = (TextView) itemView.findViewById(R.id.tv_question_no);
            tvQuestionBookName = (TextView) itemView.findViewById(R.id.tv_question_book_name);
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


    private View getAnsInflaterView(Answers answers, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v;
        v = layoutInflater.inflate(R.layout.row_mcq_question_answer, null, false);
        TextView tvMcqQuestionAns = (TextView) v.findViewById(R.id.tv_mcq_question_ans);
        tvMcqQuestionAns.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvMcqQuestionAns.setText(Utils.formatHtml(Utils.getCharForNumber(position + 1) + ": " + answers.getChoiceText()));
        return v;
    }


    private AddQuestionContainerFragment getBaseFragment() {
        return (AddQuestionContainerFragment) mFragment;
    }


    public ArrayList<Questions> copyListOfQuestions;

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


    public Boolean canAddToPreview = false;

    @Override
    public void refreshData() {


        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {

                notifyDataSetChanged();
                Debug.e(TAG, "notify data called");

            }
        };

        handler.post(r);
    }
}
