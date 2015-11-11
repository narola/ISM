package com.ism.author.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.helper.CircleImageView;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.Data;
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


    public AssignmentSubmittorAdapter(Context mContext) {
        this.mContext = mContext;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this.mContext));
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        myTypeFace = new MyTypeFace(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_assignment_submittor, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {
            holder.tv_assignment_submittor_name.setTypeface(myTypeFace.getRalewayRegular());
            holder.tv_assignment_submittor_rollno.setTypeface(myTypeFace.getRalewayRegular());
            holder.tv_assignment_submission_date.setTypeface(myTypeFace.getRalewayRegular());
            holder.tv_submission_date.setTypeface(myTypeFace.getRalewayRegular());
            holder.tv_assessment_status.setTypeface(myTypeFace.getRalewayRegular());
            holder.tv_status.setTypeface(myTypeFace.getRalewayRegular());

            imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png",
                    holder.img_assignment_submittor_dp, ISMAuthor.options);

            holder.tv_assignment_submittor_name.setText(listOfStudents.get(position).getFullName());
            holder.tv_assignment_submittor_rollno.setText(mContext.getString(R.string.strrollno) + " " + listOfStudents.get(position).getRoleId());/*this data set is left*/
            holder.tv_assignment_submission_date.setText(Utility.getFormattedDate("dd-MMM-yyyy", listOfStudents.get(position).getSubmissionDate()));
            holder.tv_assessment_status.setText(listOfStudents.get(position).getExamStatus());

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

        CircleImageView img_assignment_submittor_dp;
        TextView tv_assignment_submittor_name, tv_assignment_submittor_rollno, tv_assignment_submission_date, tv_submission_date, tv_assessment_status,
                tv_status;
        ImageView img_assignment_submittor_chat, img_assignment_submission_msg;


        public ViewHolder(View itemView) {
            super(itemView);

            img_assignment_submittor_dp = (CircleImageView) itemView.findViewById(R.id.img_assignment_submittor_dp);
            img_assignment_submittor_chat = (ImageView) itemView.findViewById(R.id.img_assignment_submittor_chat);
            img_assignment_submission_msg = (ImageView) itemView.findViewById(R.id.img_assignment_submission_msg);

            tv_assignment_submittor_name = (TextView) itemView.findViewById(R.id.tv_assignment_submittor_name);
            tv_assignment_submittor_rollno = (TextView) itemView.findViewById(R.id.tv_assignment_submittor_rollno);
            tv_assignment_submission_date = (TextView) itemView.findViewById(R.id.tv_assignment_submission_date);
            tv_submission_date = (TextView) itemView.findViewById(R.id.tv_submission_date);
            tv_assessment_status = (TextView) itemView.findViewById(R.id.tv_assessment_status);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);


        }
    }

}
