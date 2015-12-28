package com.ism.teacher.adapters.results;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.object.Global;

/**
 * Created by c75 on 21/12/15.
 */
public class AllResultsAdapter extends RecyclerView.Adapter<AllResultsAdapter.ViewHolder> {

    private static final String TAG = AllResultsAdapter.class.getSimpleName();
    Context mContext;


    public AllResultsAdapter(Context context) {
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlResults;
        TextView tvSubjectName, tvExamName, tvClassName, tvSubmissionDate, tvAssignmentNo, tvFreezeQuestion, tvTotalSubmissionsEvaluated;

        public ViewHolder(View itemView) {
            super(itemView);
            rlResults = (RelativeLayout) itemView.findViewById(R.id.rl_notes);

            tvSubjectName = (TextView) itemView.findViewById(R.id.tv_subject_name);
            tvExamName = (TextView) itemView.findViewById(R.id.tv_exam_name);
            tvClassName = (TextView) itemView.findViewById(R.id.tv_class_name);
            tvSubmissionDate = (TextView) itemView.findViewById(R.id.tv_submission_date);
            tvAssignmentNo = (TextView) itemView.findViewById(R.id.tv_assignment_no);
            tvFreezeQuestion = (TextView) itemView.findViewById(R.id.tv_freeze_question);
            tvTotalSubmissionsEvaluated = (TextView) itemView.findViewById(R.id.tv_total_submissions_evaluated);

            tvSubjectName.setTypeface(Global.myTypeFace.getRalewayBold());
            tvExamName.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvClassName.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvSubmissionDate.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvAssignmentNo.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvFreezeQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvTotalSubmissionsEvaluated.setTypeface(Global.myTypeFace.getRalewayRegular());
        }
    }

    @Override
    public AllResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View assignments_view = inflater.inflate(R.layout.row_results, parent, false);
        ViewHolder viewHolder = new ViewHolder(assignments_view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AllResultsAdapter.ViewHolder holder, int position) {
//        Global.imageLoader.displayImage(WebConstants.USER_IMAGES + "Users_Images/" + arrListExamSubmittor.get(position).getStudentProfilePic(), holder.imgUserPic, ISMTeacher.options);
//
//        holder.tvUsername.setText();
//        holder.tvSchoolname.setText();

        if (position % 2 == 0) {
            holder.tvFreezeQuestion.setBackgroundResource(R.drawable.btn_freeze_result_green);
            holder.rlResults.setBackgroundResource(R.drawable.bg_subject_red);
        } else {
            holder.tvFreezeQuestion.setBackgroundResource(R.drawable.btn_freeze_result_red);
            holder.rlResults.setBackgroundResource(R.drawable.bg_subject_yellow);
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }


}
