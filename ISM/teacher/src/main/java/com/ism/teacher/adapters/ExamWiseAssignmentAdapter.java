package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.teacher.ISMTeacher;
import com.ism.teacher.R;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.model.EvaluationModel;
import com.ism.teacher.views.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;


/**
 * these is the assignment subjects adapter
 */
public class ExamWiseAssignmentAdapter extends RecyclerView.Adapter<ExamWiseAssignmentAdapter.ViewHolder> {

    private static final String TAG = ExamWiseAssignmentAdapter.class.getSimpleName();

    Context mContext;
    ArrayList<EvaluationModel> listOfAssignments = new ArrayList<EvaluationModel>();
    Fragment mFragment;
    MyTypeFace myTypeFace;
    private ImageLoader imageLoader;


    public ExamWiseAssignmentAdapter(Context context, Fragment fragment) {
        this.mContext = context;
        this.mFragment = fragment;
        myTypeFace = new MyTypeFace(context);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.assignment_student_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView img_student;
        TextView txt_studentName, txt_roll_number_student, txt_submission_date, txt_assessed_status, txt_submission_date_label, txt_assessed_status_label;
        ImageView img_chat_with_student, img_message_student;


        public ViewHolder(View itemView) {
            super(itemView);

            img_student = (CircleImageView) itemView.findViewById(R.id.img_student);
            txt_studentName = (TextView) itemView.findViewById(R.id.txt_studentName);

            txt_roll_number_student = (TextView) itemView.findViewById(R.id.txt_roll_number_student);
            txt_submission_date = (TextView) itemView.findViewById(R.id.txt_submission_date);
            txt_assessed_status = (TextView) itemView.findViewById(R.id.txt_assessed_status);

            txt_submission_date_label = (TextView) itemView.findViewById(R.id.txt_submission_date_label);
            txt_assessed_status_label = (TextView) itemView.findViewById(R.id.txt_assessed_status_label);

            img_chat_with_student = (ImageView) itemView.findViewById(R.id.img_chat_with_student);
            img_message_student = (ImageView) itemView.findViewById(R.id.img_message_student);

        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txt_studentName.setText(listOfAssignments.get(position).getFull_name());
        holder.txt_submission_date.setText(Utility.getFormattedDate("dd-MMM-yyyy", listOfAssignments.get(position).getSubmission_date()));
        holder.txt_assessed_status.setText(listOfAssignments.get(position).getExam_status());

         imageLoader.displayImage(WebConstants.USER_IMAGES+"Users_Images/" +listOfAssignments.get(position).getProfile_pic(), holder.img_student, ISMTeacher.options);

    }


    public void addAll(ArrayList<EvaluationModel> evaluationModelArrayList) {

        try {
            this.listOfAssignments.clear();
            this.listOfAssignments.addAll(evaluationModelArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        Log.e("size......list", "" + listOfAssignments.size());
        return listOfAssignments.size();
//        return 5;
    }


}
