package com.ism.author.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.utility.Debug;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.object.Global;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.model.Examsubmittor;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c166 on 19/11/15.
 */
public class StudentAttemptedAssignmentAdapter extends RecyclerView.Adapter<StudentAttemptedAssignmentAdapter.ViewHolder> {

    private static final String TAG = StudentAttemptedAssignmentAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Examsubmittor> arrListExamSubmittor = new ArrayList<Examsubmittor>();

    private ImageLoader imageLoader;
    private LayoutInflater inflater;


    public StudentAttemptedAssignmentAdapter(Context mContext) {
        this.mContext = mContext;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = inflater.inflate(R.layout.row_student_attempted, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        try {

            imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png",
                    holder.imgUserPic, ISMAuthor.options);



            holder.txtStudentName.setText(arrListExamSubmittor.get(position).getStudentName());
//            holder.txtStudentSchool.setText(arrListExamSubmittor.get(position).getSchoolName());
            holder.txtStudentMarks.setText(arrListExamSubmittor.get(position).getEvaluationScore());

            if (getBundleArguments().containsKey(ExamSubmittorAdapter.ARG_STUDENT_ID)) {
                if (getBundleArguments().getString(ExamSubmittorAdapter.ARG_STUDENT_ID).equals(arrListExamSubmittor.get(position).getStudentId())) {
                    holder.llMain.setBackgroundColor(mContext.getResources().getColor(R.color.fragment_background_color));
                    holder.txt_bottom_line.setBackgroundColor(mContext.getResources().getColor(R.color.color_blue));
                } else {
                    holder.llMain.setBackgroundColor(mContext.getResources().getColor(R.color.color_white));
                    holder.txt_bottom_line.setBackgroundColor(mContext.getResources().getColor(R.color.border_gray));
                }
            }

            holder.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*this is to select the current student*/
                    setBundleArgument(position);
                    notifyDataSetChanged();
                    ((AuthorHostActivity) mContext).loadStudentEvaluationData();

                }
            });

//
        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }

    }

    @Override
    public int getItemCount() {
        return arrListExamSubmittor.size();
    }

    public void addAll(ArrayList<Examsubmittor> examSubmittors) {
        try {
            this.arrListExamSubmittor.clear();
            this.arrListExamSubmittor.addAll(examSubmittors);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgUserPic;
        TextView txtStudentName, txtStudentSchool, txtStudentMarks, txtStudentClass, txt_bottom_line;
        LinearLayout llMain;

        public ViewHolder(View convertView) {
            super(convertView);
            try {

                imgUserPic = (CircleImageView) convertView.findViewById(R.id.img_user_pic);
                txtStudentName = (TextView) convertView.findViewById(R.id.txt_student_name);
                txtStudentSchool = (TextView) convertView.findViewById(R.id.txt_student_school);
                txtStudentMarks = (TextView) convertView.findViewById(R.id.txt_student_marks);
                txtStudentClass = (TextView) convertView.findViewById(R.id.txt_student_class);
                llMain = (LinearLayout) convertView.findViewById(R.id.ll_main);
                txt_bottom_line = (TextView) convertView.findViewById(R.id.txt_bottom_line);


                txtStudentName.setTypeface(Global.myTypeFace.getRalewayBold());
                txtStudentSchool.setTypeface(Global.myTypeFace.getRalewayRegular());
                txtStudentMarks.setTypeface(Global.myTypeFace.getRalewayRegular());
                txtStudentClass.setTypeface(Global.myTypeFace.getRalewayRegular());

            } catch (Exception e) {
                Debug.e(TAG, "ViewHolder Exceptions :" + e.toString());
            }
        }
    }

    public void setBundleArgument(int position) {
        getBundleArguments().putInt(ExamSubmittorAdapter.ARG_STUDENT_POSITION, position);
        getBundleArguments().putString(ExamSubmittorAdapter.ARG_STUDENT_PROFILE_PIC,
                arrListExamSubmittor.get(position).getStudentProfilePic());
        getBundleArguments().putString(ExamSubmittorAdapter.ARG_STUDENT_NAME,
                arrListExamSubmittor.get(position).getStudentName());
        getBundleArguments().putString(ExamSubmittorAdapter.ARG_STUDENT_ID,
                arrListExamSubmittor.get(position).getStudentId());
        notifyDataSetChanged();
    }


    private Bundle getBundleArguments() {
        return ((AuthorHostActivity) mContext).getBundle();
    }
}
