package com.ism.author.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.object.Global;
import com.ism.author.ws.model.Exams;

import java.util.ArrayList;

/**
 * Created by c166 on 11/12/15.
 */
public class MyThirtyAdapter extends RecyclerView.Adapter<MyThirtyAdapter.ViewHolder> {

    private static final String TAG = MyThirtyAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Exams> arrListExams = new ArrayList<Exams>();
    private LayoutInflater inflater;


    public MyThirtyAdapter(Context mContext) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = inflater.inflate(R.layout.row_mythirty, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {

            holder.tvExamBookName.setTypeface(Global.myTypeFace.getRalewayBold());
            holder.tvExamType.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvExamNoofStudent.setTypeface(Global.myTypeFace.getRalewayBold());
            holder.tvExamNoofStudentAvg.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvExamNoofStudentAttempted.setTypeface(Global.myTypeFace.getRalewayBold());
            holder.tvExamAttempted.setTypeface(Global.myTypeFace.getRalewayRegular());

            holder.tvExamBookName.setText(arrListExams.get(position).getBookName());
            holder.tvExamType.setText(arrListExams.get(position).getExamMode());
            holder.tvExamNoofStudent.setText(arrListExams.get(position).getTotalStudent() + " " + mContext.getString(R.string.strstudent));
            holder.tvExamNoofStudentAvg.setText(mContext.getString(R.string.stravg) + " " + arrListExams.get(position).getAverageScore());
            holder.tvExamNoofStudentAttempted.setText(arrListExams.get(position).getTotalStudentAttempted());

            if (position % 2 == 0) {
                holder.llTopExam.setBackgroundResource(R.drawable.img_bg_mythirty_pink);
            } else {
                holder.llTopExam.setBackgroundResource(R.drawable.img_bg_mythirty_yellow);
            }

            final Bundle bundleExamDetails = new Bundle();

            bundleExamDetails.putString(ExamsAdapter.ARG_EXAM_ID, arrListExams.get(position).getExamId());
            bundleExamDetails.putString(ExamsAdapter.ARG_EXAM_NAME, arrListExams.get(position).getExamName());
            bundleExamDetails.putString(ExamsAdapter.ARG_EXAM_CLASSROOM_ID, arrListExams.get(position).getClassroomId());
            bundleExamDetails.putString(ExamsAdapter.ARG_EXAM_CLASSROOM_NAME, arrListExams.get(position).getClassroomName());
            bundleExamDetails.putString(ExamsAdapter.ARG_EXAM_BOOK_ID, arrListExams.get(position).getBookId());
            bundleExamDetails.putString(ExamsAdapter.ARG_EXAM_BOOK_NAME, arrListExams.get(position).getBookName());
            bundleExamDetails.putString(ExamsAdapter.ARG_EXAM_CATEGORY, arrListExams.get(position).getExamCategory());
            bundleExamDetails.putString(ExamsAdapter.ARG_EXAM_TYPE, arrListExams.get(position).getExamType());
            bundleExamDetails.putString(ExamsAdapter.ARG_EXAM_MODE, arrListExams.get(position).getExamMode());
            bundleExamDetails.putString(ExamsAdapter.ARG_EXAM_DURATION, arrListExams.get(position).getDuration());
            bundleExamDetails.putInt(ExamsAdapter.ARG_EXAM_NO, position);
            bundleExamDetails.putString(ExamsAdapter.ARG_EXAM_PASS_PERCENTAGE, arrListExams.get(position).getPassPercentage());
            bundleExamDetails.putString(ExamsAdapter.ARG_EXAM_QUESTION_SCORE, "0");
            bundleExamDetails.putString(ExamsAdapter.ARG_EXAM_CREATED_DATE, arrListExams.get(position).getExamCreatedDate());
            bundleExamDetails.putInt(ExamsAdapter.ARG_FRAGMENT_TYPE, AuthorHostActivity.currentMainFragment);

            holder.llExamContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    bundleExamDetails.putBoolean(ExamsAdapter.ARG_ISLOAD_FRAGMENTFOREVALUATION, false);
                    ((AuthorHostActivity) mContext).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_GET_OBJECTIVE_ASSIGNMENT_QUESTIONS,
                            bundleExamDetails);
                    ((AuthorHostActivity) mContext).loadFragmentInRightContainer(AuthorHostActivity.FRAGMENT_STUDENT_ATTEMPTED_ASSIGNMENT, bundleExamDetails);
                }
            });
        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }
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

    @Override
    public int getItemCount() {
        return arrListExams.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvExamBookName, tvExamType, tvExamNoofStudent, tvExamNoofStudentAvg,
                tvExamNoofStudentAttempted, tvExamAttempted;
        LinearLayout llExamContainer, llTopExam;

        public ViewHolder(View itemView) {
            super(itemView);

            llTopExam = (LinearLayout) itemView.findViewById(R.id.ll_top_exam);
            tvExamBookName = (TextView) itemView.findViewById(R.id.tv_exam_book_name);
            tvExamType = (TextView) itemView.findViewById(R.id.tv_exam_type);
            llExamContainer = (LinearLayout) itemView.findViewById(R.id.ll_exam_container);
            llTopExam = (LinearLayout) itemView.findViewById(R.id.ll_top_exam);
            tvExamNoofStudent = (TextView) itemView.findViewById(R.id.tv_exam_noof_student);
            tvExamNoofStudentAvg = (TextView) itemView.findViewById(R.id.tv_exam_noof_student_avg);
            tvExamNoofStudentAttempted = (TextView) itemView.findViewById(R.id.tv_exam_noof_student_attempted);
            tvExamAttempted = (TextView) itemView.findViewById(R.id.tv_exam_attempted);
        }
    }

}
