package com.ism.author.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.fragment.assessment.objectiveassessment.ObjectiveAssignmentQuestionsFragment;
import com.ism.author.object.Global;
import com.ism.author.ws.model.Exams;

import java.util.ArrayList;

/**
 * Created by c166 on 11/12/15.
 */
public class TrialAdapter extends RecyclerView.Adapter<TrialAdapter.ViewHolder> {

    private static final String TAG = TrialAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Exams> arrListExams = new ArrayList<Exams>();
    private LayoutInflater inflater;


    public TrialAdapter(Context mContext) {
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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

            if (position == 0 || position % 3 == 0) {
                holder.llTopExam.setBackgroundResource(R.drawable.img_bg_mythirty_yellow);
            } else if (position == 1 || (position - 1) % 3 == 0) {
                holder.llTopExam.setBackgroundResource(R.drawable.img_bg_mythirty_pink);
            } else if (position == 2 || (position - 2) % 3 == 0) {
                holder.llTopExam.setBackgroundResource(R.drawable.img_bg_mythirty_blue);
            }


            holder.llExamContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setBundleArguments(position);

                    getBundleArguments().putBoolean(ExamsAdapter.ARG_ISLOAD_FRAGMENTFOREVALUATION, false);
                    ((AuthorHostActivity) mContext).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS);
                    ((AuthorHostActivity) mContext).loadFragmentInRightContainer(AuthorHostActivity.FRAGMENT_STUDENT_ATTEMPTED_ASSIGNMENT);
                }
            });


            holder.llExamEditOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showExamEditOptions(v);
                }
            });


        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }
    }

    AlertDialog dialog;


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
        LinearLayout llExamContainer, llTopExam, llExamEditOptions;

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
            llExamEditOptions = (LinearLayout) itemView.findViewById(R.id.ll_exam_edit_options);
        }
    }


    private void setBundleArguments(int position) {

        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_ID, arrListExams.get(position).getExamId());
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_NAME, arrListExams.get(position).getExamName());
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_CLASSROOM_ID, arrListExams.get(position).getClassroomId());
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_CLASSROOM_NAME, arrListExams.get(position).getClassroomName());
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_BOOK_ID, arrListExams.get(position).getBookId());
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_BOOK_NAME, arrListExams.get(position).getBookName());
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_CATEGORY, arrListExams.get(position).getExamCategory());
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_MODE, arrListExams.get(position).getExamMode());
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_PASS_PERCENTAGE, arrListExams.get(position).getPassPercentage());
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_DURATION, arrListExams.get(position).getDuration());
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_ATTEMPT_COUNT, arrListExams.get(position).getAttemptCount());
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_INSTRUCTIONS, arrListExams.get(position).getExamInstructions());
        getBundleArguments().putBoolean(ExamsAdapter.ARG_EXAM_IS_RANDOM_QUESTION, arrListExams.get(position).getRandomQuestion().
                equalsIgnoreCase(mContext.getString(R.string.stryes)) ? true : false);
        getBundleArguments().putBoolean(ExamsAdapter.ARG_EXAM_IS_NEGATIVE_MARKING, arrListExams.get(position).getNegativeMarking().
                equalsIgnoreCase(mContext.getString(R.string.stryes)) ? true : false);
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_NEGATIVE_MARK_VALUE, arrListExams.get(position).getNegativeMarkValue());
        getBundleArguments().putBoolean(ExamsAdapter.ARG_EXAM_IS_USE_QUESTION_SCORE, arrListExams.get(position).getUseQuestionScore().
                equalsIgnoreCase(mContext.getString(R.string.stryes)) ? true : false);
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_CORRECT_ANSWER_SCORE, arrListExams.get(position).getCorrectAnswerScore());
        getBundleArguments().putBoolean(ExamsAdapter.ARG_EXAM_IS_DECLARE_RESULTS, arrListExams.get(position).getDeclareResults().
                equalsIgnoreCase(mContext.getString(R.string.stryes)) ? true : false);
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_ASSESSOR, arrListExams.get(position).getExamAssessor());
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_START_DATE, arrListExams.get(position).getExamStartDate());
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_START_TIME, arrListExams.get(position).getExamStartTime());
        getBundleArguments().putString(ExamsAdapter.ARG_EXAM_CREATED_DATE, arrListExams.get(position).getExamCreatedDate());
        getBundleArguments().putInt(ExamsAdapter.ARG_EXAM_NO, position);
        getBundleArguments().putInt(ExamsAdapter.ARG_FRAGMENT_TYPE, AuthorHostActivity.currentMainFragment);

    }


    private void showExamEditOptions(View v) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_exam_edit_options, null);

        TextView tvScheduleExam = (TextView) view.findViewById(R.id.tv_schedule_exam);
        tvScheduleExam.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvScheduleExam.setOnClickListener(itemClickListener);

        TextView tvCopyExam = (TextView) view.findViewById(R.id.tv_copy_exam);
        tvCopyExam.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvCopyExam.setOnClickListener(itemClickListener);

        TextView tvEditExam = (TextView) view.findViewById(R.id.tv_edit_exam);
        tvEditExam.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvEditExam.setOnClickListener(itemClickListener);

        final PopupWindow popupExamOptions = new PopupWindow(view, 250, 350, true);
        popupExamOptions.setOutsideTouchable(true);
        popupExamOptions.setBackgroundDrawable(new BitmapDrawable());

        popupExamOptions.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        popupExamOptions.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
        popupExamOptions.showAsDropDown(v, 20, -90);
    }

    View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.tv_schedule_exam:

                    break;

                case R.id.tv_copy_exam:
                    getBundleArguments().putBoolean(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY, true);
                    setExamQuestions();
                    break;

                case R.id.tv_edit_exam:
                    getBundleArguments().putBoolean(ObjectiveAssignmentQuestionsFragment.ARG_EXAM_ISCOPY, false);
                    setExamQuestions();
                    break;

            }

        }
    };


    private void setExamQuestions() {

    }


    private Bundle getBundleArguments() {
        return ((AuthorHostActivity) mContext).getBundle();
    }
}
