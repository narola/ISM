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
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.object.MyTypeFace;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.model.Examsubmittor;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c166 on 10/11/15.
 */
public class AssignmentSubmittorAdapter extends RecyclerView.Adapter<AssignmentSubmittorAdapter.ViewHolder> {

    private static final String TAG = AssignmentSubmittorAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Examsubmittor> arrListExamSubmittor = new ArrayList<Examsubmittor>();
    private MyTypeFace myTypeFace;
    private ImageLoader imageLoader;
    private Bundle bundleArgument;
    private LayoutInflater inflater;

    public static String ARG_STUDENT_ID = "studenId";
    public static String ARG_STUDENT_POSITION = "studenPosition";
    public static String ARG_STUDENT_PROFILE_PIC = "studentProfilePic";
    public static String ARG_STUDENT_NAME = "studentName";


    public AssignmentSubmittorAdapter(Context mContext, Bundle bundleArgument) {
        this.mContext = mContext;
        this.bundleArgument = bundleArgument;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this.mContext));
        myTypeFace = new MyTypeFace(mContext);
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = inflater.inflate(R.layout.row_assignment_submittor, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            holder.tvAssignmentSubmittorName.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvAssignmentSubmittorRollno.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvAssignmentSubmissionDate.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvSubmissionDate.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvAssessmentStatus.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvStatus.setTypeface(myTypeFace.getRalewayRegular());

            imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png",
                    holder.imgAssignmentSubmittorDp, ISMAuthor.options);

            holder.tvAssignmentSubmittorName.setText(arrListExamSubmittor.get(position).getStudentName());
            holder.tvAssignmentSubmittorRollno.setText(mContext.getString(R.string.strrollno) + " " + arrListExamSubmittor.get(position).getStudentId());/*this data set is left*/
            holder.tvAssignmentSubmissionDate.setText(Utility.getFormattedDate("dd-MMM-yyyy", arrListExamSubmittor.get(position).getSubmissionDate()));
            if (arrListExamSubmittor.get(position).getExamStatus().equalsIgnoreCase("finished")) {
                holder.tvAssessmentStatus.setText(arrListExamSubmittor.get(position).getExamStatus());
            } else {
                holder.tvAssessmentStatus.setText(mContext.getResources().getString(R.string.strunasssessed));
            }

            holder.llAssignmentSubmittorContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    bundleArgument.putString(ARG_STUDENT_ID, arrListExamSubmittor.get(position).getStudentId());
                    bundleArgument.putInt(ARG_STUDENT_POSITION, position);
                    bundleArgument.putString(ARG_STUDENT_PROFILE_PIC, arrListExamSubmittor.get(position).getStudentProfilePic());
                    bundleArgument.putString(ARG_STUDENT_NAME, arrListExamSubmittor.get(position).getStudentName());


                    if (bundleArgument.getString(ExamsAdapter.ARG_EXAM_MODE).equalsIgnoreCase("subjective")) {
                        ((AuthorHostActivity) mContext).loadFragmentInMainContainer
                                (AuthorHostActivity.FRAGMENT_GET_SUBJECTIVE_ASSIGNMENT_QUESTIONS, bundleArgument);
                    } else if (bundleArgument.getString(ExamsAdapter.ARG_EXAM_MODE).equalsIgnoreCase("objective")) {
                        ((AuthorHostActivity) mContext).loadFragmentInMainContainer
                                (AuthorHostActivity.FRAGMENT_GET_OBJECTIVE_ASSIGNMENT_QUESTIONS, bundleArgument);
                    }


                }
            });

        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }


    }

    @Override
    public int getItemCount() {
        return arrListExamSubmittor.size();
    }

    public void addAll(ArrayList<Examsubmittor> examsubmittor) {
        try {
            this.arrListExamSubmittor.clear();
            this.arrListExamSubmittor.addAll(examsubmittor);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgAssignmentSubmittorDp;
        TextView tvAssignmentSubmittorName, tvAssignmentSubmittorRollno, tvAssignmentSubmissionDate, tvSubmissionDate, tvAssessmentStatus,
                tvStatus;
        ImageView imgAssignmentSubmittorChat, imgAssignmentSubmissionMsg;
        LinearLayout llAssignmentSubmittorContainer;


        public ViewHolder(View itemView) {
            super(itemView);

            imgAssignmentSubmittorDp = (CircleImageView) itemView.findViewById(R.id.img_assignment_submittor_dp);
            imgAssignmentSubmittorChat = (ImageView) itemView.findViewById(R.id.img_assignment_submittor_chat);
            imgAssignmentSubmissionMsg = (ImageView) itemView.findViewById(R.id.img_assignment_submission_msg);

            tvAssignmentSubmittorName = (TextView) itemView.findViewById(R.id.tv_assignment_submittor_name);
            tvAssignmentSubmittorRollno = (TextView) itemView.findViewById(R.id.tv_assignment_submittor_rollno);
            tvAssignmentSubmissionDate = (TextView) itemView.findViewById(R.id.tv_assignment_submission_date);
            tvSubmissionDate = (TextView) itemView.findViewById(R.id.tv_submission_date);
            tvAssessmentStatus = (TextView) itemView.findViewById(R.id.tv_assessment_status);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_status);

            llAssignmentSubmittorContainer = (LinearLayout) itemView.findViewById(R.id.ll_assignment_submittor_container);


        }
    }


}
