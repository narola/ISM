package com.ism.author.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.utility.Debug;
import com.ism.author.utility.Utility;
import com.ism.author.views.CircleImageView;

import io.realm.RealmList;
import model.authormodel.ROExamSubmittor;

/**
 * Created by c166 on 10/11/15.
 */
public class ExamSubmittorAdapter extends RecyclerView.Adapter<ExamSubmittorAdapter.ViewHolder> {

    private static final String TAG = ExamSubmittorAdapter.class.getSimpleName();
    private Context mContext;
    private RealmList<ROExamSubmittor> arrListExamSubmittor = null;
    private LayoutInflater inflater;

    public static String ARG_STUDENT_ID = "studenId";
    public static String ARG_STUDENT_POSITION = "studenPosition";
    public static String ARG_STUDENT_PROFILE_PIC = "studentProfilePic";
    public static String ARG_STUDENT_NAME = "studentName";


    public ExamSubmittorAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = inflater.inflate(R.layout.row_exam_submittor, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {


            if (arrListExamSubmittor.get(position).getStudentProfilePic() != null && arrListExamSubmittor.get(position).getStudentProfilePic() != "") {
                Global.imageLoader.displayImage(WebConstants.USER_IMAGES + arrListExamSubmittor.get(position).getStudentProfilePic(),
                        holder.imgExamSubmittorDp, ISMAuthor.options);
            } else {
                holder.imgExamSubmittorDp.setImageResource(R.drawable.userdp);
            }


            holder.tvExamSubmittorName.setText(arrListExamSubmittor.get(position).getStudentName());
            holder.tvExamSubmittorRollno.setText(mContext.getString(R.string.strrollno) + " " + arrListExamSubmittor.get(position).getStudentId());/*this data set is left*/
            if (arrListExamSubmittor.get(position).getSubmissionDate() != null) {
                holder.tvExamSubmissionDate.setText(Utility.getDateFromRealm(arrListExamSubmittor.get(position).getSubmissionDate().toString()));
            }
            if (arrListExamSubmittor.get(position).getExamStatus().equalsIgnoreCase(mContext.getResources().getString(R.string.strfinished))) {

                holder.tvExamStatus.setText(arrListExamSubmittor.get(position).getExamStatus());

            } else {
                holder.tvExamStatus.setText(mContext.getResources().getString(R.string.strunasssessed));
            }

            holder.llExamSubmittorContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setBundleArguments(position);
                    if (getBundleArguments().getString(ExamsAdapter.ARG_EXAM_MODE).equalsIgnoreCase("subjective")) {

                        ((AuthorHostActivity) mContext).loadFragmentInMainContainer
                                (AuthorHostActivity.FRAGMENT_SUBJECTIVE_ASSIGNMENT_QUESTIONS_CONTAINER);

                    } else if (getBundleArguments().getString(ExamsAdapter.ARG_EXAM_MODE).equalsIgnoreCase("objective")) {

                        ((AuthorHostActivity) mContext).loadFragmentInMainContainer
                                (AuthorHostActivity.FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS);
                        ((AuthorHostActivity) mContext).loadFragmentInRightContainer(AuthorHostActivity.FRAGMENT_STUDENT_ATTEMPTED_ASSIGNMENT);

                    }


                }
            });

        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }


    }

    @Override
    public int getItemCount() {

        if (arrListExamSubmittor != null) {
            return arrListExamSubmittor.size();
        } else {
            return 0;
        }
    }

    public void addAll(RealmList<ROExamSubmittor> arrListExamSubmittors) {
        try {
            this.arrListExamSubmittor = arrListExamSubmittors;
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgExamSubmittorDp;
        TextView tvExamSubmittorName, tvExamSubmittorRollno, tvExamSubmissionDate, tvSubmissionDate, tvExamStatus,
                tvStatus;
        ImageView imgExamSubmittorChat, imgExamSubmissionMsg;
        LinearLayout llExamSubmittorContainer;


        public ViewHolder(View itemView) {
            super(itemView);

            imgExamSubmittorDp = (CircleImageView) itemView.findViewById(R.id.img_exam_submittor_dp);
            imgExamSubmittorChat = (ImageView) itemView.findViewById(R.id.img_exam_submittor_chat);
            imgExamSubmissionMsg = (ImageView) itemView.findViewById(R.id.img_exam_submission_msg);

            tvExamSubmittorName = (TextView) itemView.findViewById(R.id.tv_exam_submittor_name);
            tvExamSubmittorRollno = (TextView) itemView.findViewById(R.id.tv_exam_submittor_rollno);
            tvExamSubmissionDate = (TextView) itemView.findViewById(R.id.tv_exam_submission_date);
            tvSubmissionDate = (TextView) itemView.findViewById(R.id.tv_submission_date);
            tvExamStatus = (TextView) itemView.findViewById(R.id.tv_assessment_status);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_status);

            llExamSubmittorContainer = (LinearLayout) itemView.findViewById(R.id.ll_exam_submittor_container);

            tvExamSubmittorName.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvExamSubmittorRollno.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvExamSubmissionDate.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvSubmissionDate.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvExamStatus.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvStatus.setTypeface(Global.myTypeFace.getRalewayRegular());


        }
    }


    private void setBundleArguments(int position) {


        Debug.e(TAG, "The Student id is::" + String.valueOf(arrListExamSubmittor.get(position).getStudentId()));
        getBundleArguments().putString(ARG_STUDENT_ID, String.valueOf(arrListExamSubmittor.get(position).getStudentId()));
        getBundleArguments().putInt(ARG_STUDENT_POSITION, position);
        getBundleArguments().putString(ARG_STUDENT_PROFILE_PIC, arrListExamSubmittor.get(position).getStudentProfilePic());
        getBundleArguments().putString(ARG_STUDENT_NAME, arrListExamSubmittor.get(position).getStudentName());
    }

    private Bundle getBundleArguments() {
        return ((AuthorHostActivity) mContext).getBundle();
    }

}
