package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.teacher.ISMTeacher;
import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.fragments.assesment.SubjectiveQuestionsContainerFragment;
import com.ism.teacher.object.Global;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.model.Examsubmittor;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c75 on 10/11/15.
 */
public class MyStudentsAdapter extends RecyclerView.Adapter<MyStudentsAdapter.ViewHolder> {
    private static final String TAG = MyStudentsAdapter.class.getSimpleName();
    Fragment mFragment;
    Context mContext;
    ImageLoader imageLoader;


    ArrayList<Examsubmittor> arrayListStudents = new ArrayList<>();
    ArrayList<Examsubmittor> copyListOfStudents = new ArrayList<>();
    public static String ARG_ARR_LIST_STUDENTS = "arrListStudents";
    private LayoutInflater inflater;

//    public MyStudentsAdapter(ResponseHandler resObjStudentAttempted, Context mContext, Fragment fragment) {
//        this.resObjStudentAttempted = resObjStudentAttempted;
//        this.mFragment = fragment;
//        this.mContext = mContext;
//        imageLoader = ImageLoader.getInstance();
//        myTypeFace = new MyTypeFace(mContext);
//    }


    public MyStudentsAdapter(Context mContext, Fragment mFragment) {

        this.mContext = mContext;
        this.mFragment = mFragment;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        inflater = LayoutInflater.from(mContext);

    }

    public void addAll(ArrayList<Examsubmittor> examSubmittor) {
        try {
            this.arrayListStudents.clear();
            this.arrayListStudents.addAll(examSubmittor);
            this.copyListOfStudents = examSubmittor;
            getSubjectiveQuestionContainerFragment().getBundleArguments().putParcelableArrayList(ARG_ARR_LIST_STUDENTS, examSubmittor);

        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }


    @Override
    public MyStudentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View students_row = inflater.inflate(R.layout.row_my_students, parent, false);
        ViewHolder viewHolder = new ViewHolder(students_row);
        return viewHolder;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtStudentName, txtStudentRollno;
        CircleImageView imgStudentPic;
        ImageView imgSeparator;
        LinearLayout llMyStudentsContainer;

        public ViewHolder(View itemView) {
            super(itemView);

            txtStudentName = (TextView) itemView.findViewById(R.id.txt_student_name);
            txtStudentRollno = (TextView) itemView.findViewById(R.id.txt_student_rollno);
            imgStudentPic = (CircleImageView) itemView.findViewById(R.id.img_student_pic);
            imgSeparator = (ImageView) itemView.findViewById(R.id.img_separator);

            llMyStudentsContainer = (LinearLayout) itemView.findViewById(R.id.ll_mystudent_container);

            txtStudentName.setTypeface(Global.myTypeFace.getRalewayRegular());
            txtStudentRollno.setTypeface(Global.myTypeFace.getRalewayRegular());

        }
    }

    @Override
    public void onBindViewHolder(final MyStudentsAdapter.ViewHolder holder, final int position) {
        try {
            holder.txtStudentName.setText(arrayListStudents.get(position).getStudentName());
            holder.txtStudentRollno.setText(mContext.getResources().getString(R.string.strrollno) + (position + 1));

            Global.imageLoader.displayImage(WebConstants.USER_IMAGES + arrayListStudents.get(position).getStudentProfilePic(), holder.imgStudentPic, ISMTeacher.options);

            if (getSubjectiveQuestionContainerFragment().getBundleArguments().getString(AssignmentSubmitterAdapter.ARG_STUDENT_ID).equals(arrayListStudents.get(position).getStudentId())) {
                holder.txtStudentName.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            } else {
                holder.txtStudentName.setTextColor(mContext.getResources().getColor(R.color.color_gray));
            }

            holder.llMyStudentsContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBundleArgument(position);
                    getSubjectiveQuestionContainerFragment().loadStudentEvaluationData(arrayListStudents.get(position).getStudentId());

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void filter(CharSequence charText) {

        arrayListStudents.clear();
        if (charText.length() == 0) {
            arrayListStudents.addAll(copyListOfStudents);
        } else {
            for (Examsubmittor wp : copyListOfStudents) {
                if (Utility.containsString(wp.getStudentName(), charText.toString(), false)) {
                    arrayListStudents.add(wp);
                }
            }
            if (arrayListStudents.size() == 0) {
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {

        return arrayListStudents.size();
    }


    public void setBundleArgument(int position) {

        getSubjectiveQuestionContainerFragment().getBundleArguments().putInt(AssignmentSubmitterAdapter.ARG_STUDENT_POSITION, position);
        getSubjectiveQuestionContainerFragment().getBundleArguments().putString(AssignmentSubmitterAdapter.ARG_STUDENT_PROFILE_PIC,
                arrayListStudents.get(position).getStudentProfilePic());
        getSubjectiveQuestionContainerFragment().getBundleArguments().putString(AssignmentSubmitterAdapter.ARG_STUDENT_NAME,
                arrayListStudents.get(position).getStudentName());
        getSubjectiveQuestionContainerFragment().getBundleArguments().putString(AssignmentSubmitterAdapter.ARG_STUDENT_ID,
                arrayListStudents.get(position).getStudentId());

        notifyDataSetChanged();

    }


    private SubjectiveQuestionsContainerFragment getSubjectiveQuestionContainerFragment() {
        return (SubjectiveQuestionsContainerFragment) mFragment;
    }

}
