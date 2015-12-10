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
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.model.Exams;

import java.util.ArrayList;

/**
 * Created by c166 on 09/11/15.
 */
public class ExamsAdapter extends RecyclerView.Adapter<ExamsAdapter.ViewHolder> {

    private static final String TAG = ExamsAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Exams> arrListExams = new ArrayList<Exams>();
    private LayoutInflater inflater;
    private MyTypeFace myTypeFace;


    /*this bundle arguments are use for both in edit exam and create exam we have to set it both the places*/
    public static String ARG_EXAM_ID = "examId";
    public static String ARG_EXAM_NAME = "examName";
    public static String ARG_EXAM_CLASSROOM_ID = "examClassRoomId";
    public static String ARG_EXAM_CLASSROOM_NAME = "examClassRoomName";
    //    public static String ARG_EXAM_SUBJECT_ID = "examSubjectId";
//    public static String ARG_EXAM_SUBJECT_NAME = "examSubjectName";
//    public static String ARG_EXAM_TOPIC_ID = "examTopicId";
//    public static String ARG_EXAM_TOPIC_NAME = "examTopicName";
    public static String ARG_EXAM_BOOK_ID = "examBookId";
    public static String ARG_EXAM_BOOK_NAME = "examBookName";
    public static String ARG_EXAM_CATEGORY = "examCategory";
    public static String ARG_EXAM_TYPE = "examType";
    public static String ARG_EXAM_MODE = "examMode";
    public static String ARG_EXAM_DURATION = "examDuration";
    public static String ARG_EXAM_NO = "examNo";
    public static String ARG_EXAM_PASS_PERCENTAGE = "examPassPercentage";
    public static String ARG_EXAM_QUESTION_SCORE = "examQuestionScore";
    public static String ARG_EXAM_CREATED_DATE = "examCreatedDate";
    public static String ARG_ISLOAD_FRAGMENTFOREVALUATION = "examIsLoadFragmentForEvaluation";


    public ExamsAdapter(Context mContext) {
        this.mContext = mContext;
        myTypeFace = new MyTypeFace(mContext);
        this.inflater = LayoutInflater.from(mContext);
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
            holder.tvExamBookName.setTypeface(myTypeFace.getRalewayBold());
            holder.tvExamName.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvExamDate.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvExamClassName.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvExamNoofAssessed.setTypeface(myTypeFace.getRalewayBold());
            holder.tvExamAssessed.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvExamNoofUnassessed.setTypeface(myTypeFace.getRalewayBold());
            holder.tvExamUnassessed.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvExamNoofQuestion.setTypeface(myTypeFace.getRalewayBold());
            holder.tvExamQuestion.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvExamType.setTypeface(myTypeFace.getRalewayRegular());


            holder.tvExamBookName.setText(arrListExams.get(position).getBookName());
            holder.tvExamName.setText(arrListExams.get(position).getExamName());
            holder.tvExamClassName.setText(arrListExams.get(position).getClassroomName());
            holder.tvExamDate.setText(mContext.getString(R.string.strassignmentdatecolon));
            holder.tvExamDate.append(" : " + Utils.getDateInApiFormat(arrListExams.get(position).getExamCreatedDate()));


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


            if (arrListExams.get(position).getExamMode().equalsIgnoreCase("subjective")) {
                holder.tvExamUnassessed.setText(mContext.getString(R.string.strunasssessed));
                if (arrListExams.get(position).getTotalUnAssessed() == null || arrListExams.get(position).getTotalUnAssessed().equals("")) {
                    holder.tvExamNoofUnassessed.setText("0");
                } else {
                    holder.tvExamNoofUnassessed.setText(arrListExams.get(position).getTotalUnAssessed());
                }
            } else if (arrListExams.get(position).getExamMode().equalsIgnoreCase("objective")) {
                holder.tvExamUnassessed.setText(mContext.getString(R.string.stravgscore));

                if (arrListExams.get(position).getAverageScore() == null || arrListExams.get(position).getAverageScore().equals("")) {
                    holder.tvExamNoofUnassessed.setText("0" + " %");
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

            final Bundle bundleExamDetails = new Bundle();
            bundleExamDetails.putString(ARG_EXAM_ID, arrListExams.get(position).getExamId());
            bundleExamDetails.putString(ARG_EXAM_NAME, arrListExams.get(position).getExamName());
            bundleExamDetails.putString(ARG_EXAM_CLASSROOM_ID, arrListExams.get(position).getClassroomId());
            bundleExamDetails.putString(ARG_EXAM_CLASSROOM_NAME, arrListExams.get(position).getClassroomName());
//            bundleExamDetails.putString(ARG_EXAM_SUBJECT_ID, arrListExams.get(position).getSubjectId());
//            bundleExamDetails.putString(ARG_EXAM_SUBJECT_NAME, arrListExams.get(position).getSubjectName());
//            bundleExamDetails.putString(ARG_EXAM_TOPIC_ID, WebConstants.TEST_TOPIC_ID);
//            bundleExamDetails.putString(ARG_EXAM_TOPIC_NAME, "");
            bundleExamDetails.putString(ARG_EXAM_BOOK_ID, arrListExams.get(position).getBookId());
            bundleExamDetails.putString(ARG_EXAM_BOOK_NAME, arrListExams.get(position).getBookName());
            bundleExamDetails.putString(ARG_EXAM_CATEGORY, arrListExams.get(position).getExamCategory());
            bundleExamDetails.putString(ARG_EXAM_TYPE, arrListExams.get(position).getExamType());
            bundleExamDetails.putString(ARG_EXAM_MODE, arrListExams.get(position).getExamMode());
            bundleExamDetails.putString(ARG_EXAM_DURATION, arrListExams.get(position).getDuration());
            bundleExamDetails.putInt(ARG_EXAM_NO, position);
            bundleExamDetails.putString(ARG_EXAM_PASS_PERCENTAGE, arrListExams.get(position).getPassPercentage());
            bundleExamDetails.putString(ARG_EXAM_QUESTION_SCORE, "0");
            bundleExamDetails.putString(ARG_EXAM_CREATED_DATE, arrListExams.get(position).getExamCreatedDate());


            holder.llExamContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    bundleExamDetails.putBoolean(ARG_ISLOAD_FRAGMENTFOREVALUATION, true);
                    ((AuthorHostActivity) mContext).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_ASSIGNMENT_SUBMITTOR, bundleExamDetails);


                }
            });

            holder.llViewExamQuestions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    bundleExamDetails.putBoolean(ARG_ISLOAD_FRAGMENTFOREVALUATION, false);
                    ((AuthorHostActivity) mContext).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_GET_OBJECTIVE_ASSIGNMENT_QUESTIONS,
                            bundleExamDetails);
                }
            });


        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return arrListExams.size();
    }

    public void addAll(ArrayList<Exams> exams) {
        try {
            this.arrListExams.clear();
            this.arrListExams.addAll(exams);
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
            tvExamName = (TextView) itemView.findViewById(R.id.tv_exam_name);
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


        }
    }


}
