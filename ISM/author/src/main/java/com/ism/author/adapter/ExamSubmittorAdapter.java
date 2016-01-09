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
import com.ism.author.utility.Debug;
import com.ism.author.utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.object.Global;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.model.Examsubmittor;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c166 on 10/11/15.
 */
public class ExamSubmittorAdapter extends RecyclerView.Adapter<ExamSubmittorAdapter.ViewHolder> {

    private static final String TAG = ExamSubmittorAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Examsubmittor> arrListExamSubmittor = new ArrayList<Examsubmittor>();
    private ImageLoader imageLoader;
    private LayoutInflater inflater;

    public static String ARG_STUDENT_ID = "studenId";
    public static String ARG_STUDENT_POSITION = "studenPosition";
    public static String ARG_STUDENT_PROFILE_PIC = "studentProfilePic";
    public static String ARG_STUDENT_NAME = "studentName";


    public ExamSubmittorAdapter(Context mContext) {
        this.mContext = mContext;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this.mContext));
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

            imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png",
                    holder.imgExamSubmittorDp, ISMAuthor.options);

            holder.tvExamSubmittorName.setText(arrListExamSubmittor.get(position).getStudentName());
            holder.tvExamSubmittorRollno.setText(mContext.getString(R.string.strrollno) + " " + arrListExamSubmittor.get(position).getStudentId());/*this data set is left*/
            if (arrListExamSubmittor.get(position).getSubmissionDate() != null) {
                holder.tvExamSubmissionDate.setText(Utility.getFormattedDate("dd-MMM-yyyy", arrListExamSubmittor.get(position).getSubmissionDate()));
            }
            if (arrListExamSubmittor.get(position).getExamStatus().equalsIgnoreCase("finished")) {
                holder.tvExamStatus.setText(arrListExamSubmittor.get(position).getExamStatus());
            } else {
                holder.tvExamStatus.setText(mContext.getResources().getString(R.string.strunasssessed));
            }

            holder.llExamSubmittorContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getBundleArguments().putString(ARG_STUDENT_ID, arrListExamSubmittor.get(position).getStudentId());
                    getBundleArguments().putInt(ARG_STUDENT_POSITION, position);
                    getBundleArguments().putString(ARG_STUDENT_PROFILE_PIC, arrListExamSubmittor.get(position).getStudentProfilePic());
                    getBundleArguments().putString(ARG_STUDENT_NAME, arrListExamSubmittor.get(position).getStudentName());


                    if (getBundleArguments().getString(ExamsAdapter.ARG_EXAM_MODE).equalsIgnoreCase("subjective")) {
                        ((AuthorHostActivity) mContext).loadFragmentInMainContainer
                                (AuthorHostActivity.FRAGMENT_SUBJECTIVE_ASSIGNMENT_QUESTIONS_CONTAINER);
                    } else if (getBundleArguments().getString(ExamsAdapter.ARG_EXAM_MODE).equalsIgnoreCase("objective")) {

                        ((AuthorHostActivity) mContext).loadFragmentInMainContainer
                                (AuthorHostActivity.FRAGMENT_OBJECTIVE_ASSIGNMENT_QUESTIONS);

                         /*if arguments are there then call the students in the right fragment*/
//                        if (bundleArgument != null) {
                        ((AuthorHostActivity) mContext).loadFragmentInRightContainer(AuthorHostActivity.FRAGMENT_STUDENT_ATTEMPTED_ASSIGNMENT);
//                        }

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


    private Bundle getBundleArguments() {
        return ((AuthorHostActivity) mContext).getBundle();
    }

}
