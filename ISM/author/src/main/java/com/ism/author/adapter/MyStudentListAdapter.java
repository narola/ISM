package com.ism.author.adapter;

import android.app.Fragment;
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
import com.ism.author.utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.fragment.assessment.subjectiveassessment.SubjectiveAssignmentQuestionsContainerFragment;
import com.ism.author.object.Global;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.model.Examsubmittor;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c166 on 17/11/15.
 */
public class MyStudentListAdapter extends RecyclerView.Adapter<MyStudentListAdapter.ViewHolder> {

    private static final String TAG = MyStudentListAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Examsubmittor> arrListExamSubmittor = new ArrayList<Examsubmittor>();
    private ImageLoader imageLoader;
    private Fragment mFragment;
    private LayoutInflater inflater;
    public static String ARG_ARR_LIST_STUDENTS = "arrListStudents";


    public MyStudentListAdapter(Context mContext, Fragment mFragment) {

        this.mContext = mContext;
        this.mFragment = mFragment;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        inflater = LayoutInflater.from(mContext);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = inflater.inflate(R.layout.row_my_students, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {

            imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png",
                    holder.imgStudentProfilePic, ISMAuthor.options);

            holder.tvStudentName.setText(arrListExamSubmittor.get(position).getStudentName());
            holder.tvStudentRollNo.setText(mContext.getResources().getString(R.string.strrollno) + (position + 1));

            if (getBundleArgument().getString(ExamSubmittorAdapter.ARG_STUDENT_ID).equals(arrListExamSubmittor.get(position).getStudentId())) {
                holder.tvStudentName.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            } else {
                holder.tvStudentName.setTextColor(mContext.getResources().getColor(R.color.color_gray));
            }

            holder.llMyStudentsContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBundleArgument(position);
                    getFragmnet().loadStudentEvaluationData(arrListExamSubmittor.get(position).getStudentId());
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

    public void addAll(ArrayList<Examsubmittor> examSubmittor) {
        try {
            this.arrListExamSubmittor.clear();
            this.arrListExamSubmittor.addAll(examSubmittor);
            this.copyListOfStudents = examSubmittor;
            getBundleArgument().putParcelableArrayList(ARG_ARR_LIST_STUDENTS, examSubmittor);

        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView imgStudentProfilePic;
        public TextView tvStudentName, tvStudentRollNo;
        public LinearLayout llMyStudentsContainer;

        public ViewHolder(View convertView) {
            super(convertView);
            try {

                imgStudentProfilePic = (CircleImageView) convertView.findViewById(R.id.img_student_profile_pic);
                tvStudentName = (TextView) convertView.findViewById(R.id.tv_student_name);
                tvStudentRollNo = (TextView) convertView.findViewById(R.id.tv_student_roll_no);
                llMyStudentsContainer = (LinearLayout) convertView.findViewById(R.id.ll_my_students_container);


                tvStudentName.setTypeface(Global.myTypeFace.getRalewayRegular());
                tvStudentRollNo.setTypeface(Global.myTypeFace.getRalewayRegular());

            } catch (Exception e) {
                Debug.e(TAG, "ViewHolder Exceptions :" + e.toString());
            }
        }
    }

    ArrayList<Examsubmittor> copyListOfStudents;

    public void filter(CharSequence charText) {

        arrListExamSubmittor.clear();
        if (charText.length() == 0) {
            arrListExamSubmittor.addAll(copyListOfStudents);
        } else {
            for (Examsubmittor wp : copyListOfStudents) {
                if (Utility.containsString(wp.getStudentName(), charText.toString(), false)) {
                    arrListExamSubmittor.add(wp);
                }
            }
            if (arrListExamSubmittor.size() == 0) {
            }
        }
        notifyDataSetChanged();
    }

    public void setBundleArgument(int position) {
        getBundleArgument().putInt(ExamSubmittorAdapter.ARG_STUDENT_POSITION, position);
        getBundleArgument().putString(ExamSubmittorAdapter.ARG_STUDENT_PROFILE_PIC,
                arrListExamSubmittor.get(position).getStudentProfilePic());
        getBundleArgument().putString(ExamSubmittorAdapter.ARG_STUDENT_NAME,
                arrListExamSubmittor.get(position).getStudentName());
        getBundleArgument().putString(ExamSubmittorAdapter.ARG_STUDENT_ID,
                arrListExamSubmittor.get(position).getStudentId());
        notifyDataSetChanged();
    }


    private Bundle getBundleArgument() {
        return ((AuthorHostActivity) mContext).getBundle();

    }

    private SubjectiveAssignmentQuestionsContainerFragment getFragmnet() {
        return (SubjectiveAssignmentQuestionsContainerFragment) mFragment;
    }

}
