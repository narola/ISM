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
import com.ism.author.Utility.Utility;
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
    private MyTypeFace myTypeFace;
    private LayoutInflater inflater;

    public static String ARG_EXAM_ID = "examId";
    public static String ARG_EXAM_NAME = "examName";
    public static String ARG_CLASSROOM_ID = "classRoomId";
    public static String ARG_SUBJECT_NAME = "subjectName";
    public static String ARG_PASS_PERCENTAGE = "passPercentage";
    public static String ARG_EXAM_TYPE = "examType";
    public static String ARG_EXAM_MODE = "examMode";
    public static String ARG_EXAM_DURATION = "examDuration";
    public static String ARG_ASSIGNMENT_NO = "examAssignmentNo";


    public ExamsAdapter(Context mContext) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.myTypeFace = new MyTypeFace(mContext);
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
            holder.tvExamSubjectName.setTypeface(myTypeFace.getRalewayBold());
            holder.tvExamCourseName.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvExamDate.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvExamClassName.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvExamNoofAssessed.setTypeface(myTypeFace.getRalewayBold());
            holder.tvExamAssessed.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvExamNoofUnassessed.setTypeface(myTypeFace.getRalewayBold());
            holder.tvExamUnassessed.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvExamNoofQuestion.setTypeface(myTypeFace.getRalewayBold());
            holder.tvExamQuestion.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvExamType.setTypeface(myTypeFace.getRalewayRegular());


            holder.tvExamSubjectName.setText(arrListExams.get(position).getSubjectName());
            holder.tvExamCourseName.setText(arrListExams.get(position).getClassroomName());
            holder.tvExamClassName.setText(arrListExams.get(position).getClassroomName());
            holder.tvExamDate.setText(mContext.getString(R.string.strassignmentdatecolon));
            holder.tvExamDate.append(Utility.getSpannableString(" " + arrListExams.get(position).getPassPercentage(),
                    mContext.getResources().getColor(R.color.color_black)));

            holder.tvExamNoofAssessed.setText(arrListExams.get(position).getTotalStudent());
            holder.tvExamNoofAssessed.setText("1");

            holder.tvExamNoofQuestion.setText(arrListExams.get(position).getTotalQuestion());


            if (arrListExams.get(position).getExamMode().equalsIgnoreCase("subjective")) {
                holder.tvExamUnassessed.setText(mContext.getString(R.string.strunasssessed));
                holder.tvExamNoofUnassessed.setText(arrListExams.get(position).getTotalStudent());
                holder.tvExamNoofUnassessed.setText("1");

            } else if (arrListExams.get(position).getExamMode().equalsIgnoreCase("objective")) {
                holder.tvExamUnassessed.setText(mContext.getString(R.string.stravgscore));
                holder.tvExamNoofUnassessed.setText(arrListExams.get(position).getTotalStudent() + mContext.getString(R.string.strpercent));
                holder.tvExamNoofUnassessed.setText("1  " + mContext.getString(R.string.strpercent));
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

                    Bundle bundleExamDetails = new Bundle();
                    bundleExamDetails.putString(ARG_EXAM_ID, arrListExams.get(position).getExamId());
                    bundleExamDetails.putString(ARG_EXAM_NAME, arrListExams.get(position).getExamName());
                    bundleExamDetails.putString(ARG_CLASSROOM_ID, arrListExams.get(position).getClassroomId());
                    bundleExamDetails.putString(ARG_SUBJECT_NAME, arrListExams.get(position).getSubjectName());
                    bundleExamDetails.putString(ARG_PASS_PERCENTAGE, arrListExams.get(position).getPassPercentage());
                    bundleExamDetails.putString(ARG_EXAM_TYPE, arrListExams.get(position).getExamType());
                    bundleExamDetails.putString(ARG_EXAM_MODE, arrListExams.get(position).getExamMode());
                    bundleExamDetails.putString(ARG_EXAM_DURATION, arrListExams.get(position).getDuration());
                    bundleExamDetails.putInt(ARG_ASSIGNMENT_NO, position);

                    ((AuthorHostActivity) mContext).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_ASSIGNMENT_SUBMITTOR, bundleExamDetails);


                }
            });

            holder.llViewExamQuestions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((AuthorHostActivity) mContext).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_GET_OBJECTIVE_ASSIGNMENT_QUESTIONS,
                            null);
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
        TextView tvExamSubjectName, tvExamCourseName, tvExamDate, tvExamClassName, tvExamNoofAssessed,
                tvExamAssessed, tvExamNoofUnassessed, tvExamUnassessed, tvExamNoofQuestion, tvExamQuestion,
                tvExamType;
        LinearLayout llExamContainer, llViewExamQuestions;

        public ViewHolder(View itemView) {
            super(itemView);

            rlTopExam = (RelativeLayout) itemView.findViewById(R.id.rl_top_exam);
            tvExamSubjectName = (TextView) itemView.findViewById(R.id.tv_exam_subject_name);
            tvExamCourseName = (TextView) itemView.findViewById(R.id.tv_exam_course_name);
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
