package com.ism.author.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.views.CircleImageView;
import com.ism.author.object.MyTypeFace;
import com.ism.author.model.Data;
import com.ism.author.model.FragmentArgument;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c166 on 10/11/15.
 */
public class AssignmentSubmittorAdapter extends RecyclerView.Adapter<AssignmentSubmittorAdapter.ViewHolder> {

    private static final String TAG = AssignmentSubmittorAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Data> listOfStudents = new ArrayList<Data>();
    private MyTypeFace myTypeFace;
    private ImageLoader imageLoader;
    private FragmentArgument fragmentArgument;
    private LayoutInflater inflater;


    public AssignmentSubmittorAdapter(Context mContext, FragmentArgument fragmentArgument) {
        this.mContext = mContext;
        this.fragmentArgument = fragmentArgument;
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

            holder.tvAssignmentSubmittorName.setText(listOfStudents.get(position).getFullName());
            holder.tvAssignmentSubmittorRollno.setText(mContext.getString(R.string.strrollno) + " " + listOfStudents.get(position).getRoleId());/*this data set is left*/
            holder.tvAssignmentSubmissionDate.setText(Utility.getFormattedDate("dd-MMM-yyyy", listOfStudents.get(position).getSubmissionDate()));
            holder.tvAssessmentStatus.setText(listOfStudents.get(position).getExamStatus());

            holder.llAssignmentSubmittorContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fragmentArgument.getFragmentArgumentObject().setStudentId(listOfStudents.get(position).getStudentId());
                    fragmentArgument.getFragmentArgumentObject().setPosition(position);
                    fragmentArgument.getFragmentArgumentObject().setProfilePic(listOfStudents.get(position).getProfilePic());
                    fragmentArgument.getFragmentArgumentObject().setStudentName(listOfStudents.get(position).getFullName());

                    if (fragmentArgument.getFragmentArgumentObject().getExamMode().equalsIgnoreCase("subjective")) {

                        ((AuthorHostActivity) mContext).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_GET_SUBJECTIVE_ASSIGNMENT_QUESTIONS, fragmentArgument);

                    } else if (fragmentArgument.getFragmentArgumentObject().getExamMode().equalsIgnoreCase("objective")) {

                        ((AuthorHostActivity) mContext).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_GET_OBJECTIVE_ASSIGNMENT_QUESTIONS, fragmentArgument);

                    }


                }
            });

        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }


    }

    @Override
    public int getItemCount() {
        return listOfStudents.size();
    }

    public void addAll(ArrayList<Data> data) {
        try {
            this.listOfStudents.clear();
            this.listOfStudents.addAll(data);
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
