package com.ism.author.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.object.Global;
import com.ism.author.utility.Debug;
import com.ism.author.utility.Utility;

import io.realm.RealmResults;
import realmhelper.AuthorHelper;

/**
 * Created by c166 on 09/11/15.
 */
public class ExamsAdapter extends RecyclerView.Adapter<ExamsAdapter.ViewHolder> {

    private static final String TAG = ExamsAdapter.class.getSimpleName();
    private Context mContext;
    private RealmResults<model.Exam> arrListExams = null;
    private LayoutInflater inflater;
    private AuthorHelper authorHelper;


    /*this bundle arguments are use for both in edit exam and create exam we have to set it both the places*/
    public static String ARG_EXAM_ID = "examId";
    public static String ARG_EXAM_NAME = "examName";
    public static String ARG_EXAM_CLASSROOM_ID = "examClassRoomId";
    public static String ARG_EXAM_CLASSROOM_NAME = "examClassRoomName";
    public static String ARG_EXAM_BOOK_ID = "examBookId";
    public static String ARG_EXAM_BOOK_NAME = "examBookName";
    public static String ARG_EXAM_CATEGORY = "examCategory";
    public static String ARG_EXAM_MODE = "examMode";
    public static String ARG_EXAM_PASS_PERCENTAGE = "examPassPercentage";
    public static String ARG_EXAM_DURATION = "examDuration";
    public static String ARG_EXAM_ATTEMPT_COUNT = "examAttemptCount";
    public static String ARG_EXAM_INSTRUCTIONS = "examInstructions";
    public static String ARG_EXAM_IS_RANDOM_QUESTION = "examIsRandomQuestion";
    public static String ARG_EXAM_IS_NEGATIVE_MARKING = "examIsNegativeMarking";
    public static String ARG_EXAM_NEGATIVE_MARK_VALUE = "examNegativeMarkValue";
    public static String ARG_EXAM_IS_USE_QUESTION_SCORE = "examIsUseQuestionScore";
    public static String ARG_EXAM_CORRECT_ANSWER_SCORE = "examCorrectAnswerScore";
    public static String ARG_EXAM_IS_DECLARE_RESULTS = "examIsDeclareResults";
    public static String ARG_EXAM_ASSESSOR = "examAssessor";
    public static String ARG_EXAM_START_DATE = "examStartDate";
    public static String ARG_EXAM_START_TIME = "examStartTime";
    public static String ARG_EXAM_CREATED_DATE = "examCreatedDate";
    public static String ARG_EXAM_NO = "examNo";
    public static String ARG_ISLOAD_FRAGMENTFOREVALUATION = "examIsLoadFragmentForEvaluation";
    public static String ARG_FRAGMENT_TYPE = "fragmentType";


    public ExamsAdapter(Context mContext, AuthorHelper authorHelper) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.authorHelper = authorHelper;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = inflater.inflate(R.layout.row_exams, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {


            holder.tvExamBookName.setText(arrListExams.get(position).getAuthorBook().getBook().getBookName());
            holder.tvExamName.setText(arrListExams.get(position).getExamName());
            holder.tvExamClassName.setText(arrListExams.get(position).getClassroom().getClassName());
            holder.tvExamDate.setText(mContext.getString(R.string.strassignmentdatecolon) + " ");
            holder.tvExamDate.append(Utility.getSpannableString(Utility.getDateFromRealm(arrListExams.get(position).getCreatedDate().toString()),
                    mContext.getResources().getColor(R.color.color_gray1)));


            if (arrListExams.get(position).getTotalAssessed() == null || arrListExams.get(position).getTotalAssessed().equals("")) {
                holder.tvExamNoofAssessed.setText("0");
            } else {
                holder.tvExamNoofAssessed.setText(arrListExams.get(position).getTotalAssessed());
            }


            if (arrListExams.get(position).getTotalQuestion() == null || arrListExams.get(position).getTotalQuestion().equals("")) {
                holder.tvExamNoofQuestion.setText("0");
            } else {
                holder.tvExamNoofQuestion.setText(arrListExams.get(position).getTotalQuestion());
            }


            if (arrListExams.get(position).getExamMode().equalsIgnoreCase(mContext.getString(R.string.strsubjective))) {
                holder.tvExamUnassessed.setText(mContext.getString(R.string.strunasssessed));
                if (arrListExams.get(position).getTotalUnassessed() == null || arrListExams.get(position).getTotalUnassessed().equals("")) {
                    holder.tvExamNoofUnassessed.setText("0");
                } else {
                    holder.tvExamNoofUnassessed.setText(arrListExams.get(position).getTotalUnassessed());
                }
            } else if (arrListExams.get(position).getExamMode().equalsIgnoreCase(mContext.getString(R.string.strobjective))) {
                holder.tvExamUnassessed.setText(mContext.getString(R.string.stravgscore));

                if (arrListExams.get(position).getAverageScore() == null || arrListExams.get(position).getAverageScore().equals("")) {
                    holder.tvExamNoofUnassessed.setText("0 " + mContext.getString(R.string.strpercent));
                } else {
                    holder.tvExamNoofUnassessed.setText(arrListExams.get(position).getAverageScore() + mContext.getString(R.string.strpercent));
                }
            }

            holder.tvExamType.setText(mContext.getString(R.string.strassignmenttype) + " " + arrListExams.get(position).getExamMode());

            if (position % 2 == 0) {
                holder.rlTopExam.setBackgroundResource(R.drawable.bg_subject_red);
            } else {
                holder.rlTopExam.setBackgroundResource(R.drawable.bg_subject_yellow);
            }


            holder.llExamContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    setBundleArguments(position);
                    getBundleArguments().putBoolean(ARG_ISLOAD_FRAGMENTFOREVALUATION, true);
                    ((AuthorHostActivity) mContext).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_ASSIGNMENT_SUBMITTOR);


                }
            });

            holder.llViewExamQuestions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    setBundleArguments(position);
                    getBundleArguments().putBoolean(ARG_ISLOAD_FRAGMENTFOREVALUATION, false);
                    ((AuthorHostActivity) mContext).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS);
                }
            });


        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }
    }

    @Override
    public int getItemCount() {

        if (arrListExams != null) {
            return arrListExams.size();
        } else {
            return 0;
        }
    }

    public void addAll(RealmResults<model.Exam> arrListExams) {
        try {
            this.arrListExams = arrListExams;
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlTopExam;
        TextView tvExamBookName, tvExamName, tvExamDate, tvExamClassName, tvExamNoofAssessed,
                tvExamAssessed, tvExamNoofUnassessed, tvExamUnassessed, tvExamNoofQuestion, tvExamQuestion,
                tvExamType;
        LinearLayout llExamContainer, llViewExamQuestions;

        public ViewHolder(View itemView) {
            super(itemView);

            rlTopExam = (RelativeLayout) itemView.findViewById(R.id.rl_top_exam);
            tvExamBookName = (TextView) itemView.findViewById(R.id.tv_exam_book_name);
            tvExamName = (TextView) itemView.findViewById(R.id.tv_exam_type);
            tvExamDate = (TextView) itemView.findViewById(R.id.tv_exam_date);
            tvExamClassName = (TextView) itemView.findViewById(R.id.tv_exam_class_name);
            tvExamNoofAssessed = (TextView) itemView.findViewById(R.id.tv_exam_noof_assessed);
            tvExamAssessed = (TextView) itemView.findViewById(R.id.tv_exam_assessed);
            tvExamNoofUnassessed = (TextView) itemView.findViewById(R.id.tv_exam_noof_unassessed);
            tvExamUnassessed = (TextView) itemView.findViewById(R.id.tv_exam_unassessed);
            tvExamNoofQuestion = (TextView) itemView.findViewById(R.id.tv_exam_noof_question);
            tvExamQuestion = (TextView) itemView.findViewById(R.id.tv_exam_question);
            tvExamType = (TextView) itemView.findViewById(R.id.tv_assignment_type);
            llExamContainer = (LinearLayout) itemView.findViewById(R.id.ll_exam_container);
            llViewExamQuestions = (LinearLayout) itemView.findViewById(R.id.ll_view_exam_questions);


            tvExamBookName.setTypeface(Global.myTypeFace.getRalewayBold());
            tvExamName.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvExamDate.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvExamClassName.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvExamNoofAssessed.setTypeface(Global.myTypeFace.getRalewayBold());
            tvExamAssessed.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvExamNoofUnassessed.setTypeface(Global.myTypeFace.getRalewayBold());
            tvExamUnassessed.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvExamNoofQuestion.setTypeface(Global.myTypeFace.getRalewayBold());
            tvExamQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvExamType.setTypeface(Global.myTypeFace.getRalewayRegular());


        }
    }


    private void setBundleArguments(int position) {

        getBundleArguments().putString(ARG_EXAM_ID, String.valueOf(arrListExams.get(position).getExamId()));
        getBundleArguments().putString(ARG_EXAM_NAME, arrListExams.get(position).getExamName());
        getBundleArguments().putString(ARG_EXAM_CLASSROOM_ID, String.valueOf(arrListExams.get(position).getClassroom().getClassName()));
        getBundleArguments().putString(ARG_EXAM_CLASSROOM_NAME, arrListExams.get(position).getClassroom().getClassName());
        getBundleArguments().putString(ARG_EXAM_BOOK_ID, String.valueOf(arrListExams.get(position).getAuthorBook().getBook().getBookId()));
        getBundleArguments().putString(ARG_EXAM_BOOK_NAME, arrListExams.get(position).getAuthorBook().getBook().getBookName());
        getBundleArguments().putString(ARG_EXAM_CATEGORY, arrListExams.get(position).getExamCategory());
        getBundleArguments().putString(ARG_EXAM_MODE, arrListExams.get(position).getExamMode());
        getBundleArguments().putString(ARG_EXAM_PASS_PERCENTAGE, arrListExams.get(position).getPassPercentage());
        getBundleArguments().putString(ARG_EXAM_DURATION, arrListExams.get(position).getDuration());
        getBundleArguments().putString(ARG_EXAM_ATTEMPT_COUNT, arrListExams.get(position).getAttemptCount());
        getBundleArguments().putString(ARG_EXAM_INSTRUCTIONS, arrListExams.get(position).getInstructions());
        getBundleArguments().putBoolean(ARG_EXAM_IS_RANDOM_QUESTION, arrListExams.get(position).isRandomQuestion());
        getBundleArguments().putBoolean(ARG_EXAM_IS_NEGATIVE_MARKING, arrListExams.get(position).isNegativeMarking());
        getBundleArguments().putString(ARG_EXAM_NEGATIVE_MARK_VALUE, arrListExams.get(position).getNegativeMarkValue());
        getBundleArguments().putBoolean(ARG_EXAM_IS_USE_QUESTION_SCORE, arrListExams.get(position).isUseQuestionScore());
        getBundleArguments().putString(ARG_EXAM_CORRECT_ANSWER_SCORE, arrListExams.get(position).getCorrectAnswerScore());
        getBundleArguments().putBoolean(ARG_EXAM_IS_DECLARE_RESULTS, arrListExams.get(position).isDeclareResults());
        getBundleArguments().putString(ARG_EXAM_ASSESSOR, arrListExams.get(position).getExamAssessor());
        getBundleArguments().putString(ARG_EXAM_START_DATE, arrListExams.get(position).getExamStartDate().toString());
        getBundleArguments().putString(ARG_EXAM_START_TIME, arrListExams.get(position).getExamStartTime());
        getBundleArguments().putString(ARG_EXAM_CREATED_DATE, arrListExams.get(position).getCreatedDate().toString());
        getBundleArguments().putInt(ARG_EXAM_NO, position);
        getBundleArguments().putInt(ARG_FRAGMENT_TYPE, AuthorHostActivity.currentMainFragment);

    }


    private Bundle getBundleArguments() {
        return ((AuthorHostActivity) mContext).getBundle();
    }

}
