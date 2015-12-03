package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.fragments.GetSubjectiveAssignmentQuestionsFragment;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
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
    public static MyTypeFace myTypeFace;

    private int lastSelected = -1;
    ResponseHandler resObjStudentAttempted;
    private ResponseHandler responseObjectEval;

    //test
    public String student_name = "", student_id = "", student_profile_pic = "";
    int position_of_student;

    ArrayList<Examsubmittor> arrayListStudents = new ArrayList<>();
    ArrayList<Examsubmittor> copyListOfStudents = new ArrayList<>();
    public static String ARG_ARR_LIST_STUDENTS = "arrListStudents";
    private LayoutInflater inflater;

//    public MyStudentsAdapter(ResponseHandler resObjStudentAttempted, Context context, Fragment fragment) {
//        this.resObjStudentAttempted = resObjStudentAttempted;
//        this.mFragment = fragment;
//        this.mContext = context;
//        imageLoader = ImageLoader.getInstance();
//        myTypeFace = new MyTypeFace(context);
//    }


    public MyStudentsAdapter(Context mContext, Fragment mFragment) {

        this.mContext = mContext;
        this.mFragment = mFragment;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        myTypeFace = new MyTypeFace(mContext);
        inflater = LayoutInflater.from(mContext);

    }

    public void addAll(ArrayList<Examsubmittor> examSubmittor) {
        try {
            this.arrayListStudents.clear();
            this.arrayListStudents.addAll(examSubmittor);
            this.copyListOfStudents = examSubmittor;
            getBundleArgument().putParcelableArrayList(ARG_ARR_LIST_STUDENTS, examSubmittor);

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

            txtStudentName.setTypeface(myTypeFace.getRalewayRegular());
            txtStudentRollno.setTypeface(myTypeFace.getRalewayRegular());

        }
    }

    @Override
    public void onBindViewHolder(final MyStudentsAdapter.ViewHolder holder, final int position) {
        try {
            holder.txtStudentName.setText(arrayListStudents.get(position).getStudentName());

            imageLoader.displayImage(WebConstants.USER_IMAGES + arrayListStudents.get(position).getStudentProfilePic(), holder.imgStudentPic, ISMTeacher.options);

            if (getBundleArgument().getString(AssignmentSubmitterAdapter.ARG_STUDENT_ID).equals(arrayListStudents.get(position).getStudentId())) {
                holder.txtStudentName.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            } else {
                holder.txtStudentName.setTextColor(mContext.getResources().getColor(R.color.color_gray));
            }

            holder.llMyStudentsContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBundleArgument(position);
                    getFragmnet().loadStudentEvaluationData(arrayListStudents.get(position).getStudentId());

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setValuesForSubjectiveFragment(int position) {
        student_name = arrayListStudents.get(position).getStudentName();
        student_id = arrayListStudents.get(position).getStudentId();
        student_profile_pic = arrayListStudents.get(position).getStudentProfilePic();
        position_of_student = position;

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

    private void callAPIStudentEvaluations(String student_id, String exam_id) {

        try {
            if (Utility.isInternetConnected(mContext)) {
                ((TeacherHostActivity) mContext).showProgress();
                Attribute attribute = new Attribute();
                attribute.setStudentId(student_id);
                attribute.setExamId(exam_id);

                Log.e("subjective exam evaluation ", "student_id:" + student_id + "examid" + exam_id);

                new WebserviceWrapper(mContext, attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_EXAM_EVALUATIONS);
            } else {
                Utility.showToast(mContext.getString(R.string.strnetissue), mContext);
            }

        } catch (Exception e) {
            Debug.i(TAG, "callAPIStudentEvaluations Exceptions: " + e.getLocalizedMessage());
        }
    }


    @Override
    public int getItemCount() {

        return arrayListStudents.size();
    }


    public void setBundleArgument(int position) {

        getBundleArgument().putInt(AssignmentSubmitterAdapter.ARG_STUDENT_POSITION, position);
        getBundleArgument().putString(AssignmentSubmitterAdapter.ARG_STUDENT_PROFILE_PIC,
                arrayListStudents.get(position).getStudentProfilePic());
        getBundleArgument().putString(AssignmentSubmitterAdapter.ARG_STUDENT_NAME,
                arrayListStudents.get(position).getStudentName());
        getBundleArgument().putString(AssignmentSubmitterAdapter.ARG_STUDENT_ID,
                arrayListStudents.get(position).getStudentId());

        notifyDataSetChanged();

    }


    private Bundle getBundleArgument() {
        return ((GetSubjectiveAssignmentQuestionsFragment) mFragment).getArguments();

    }

    private GetSubjectiveAssignmentQuestionsFragment getFragmnet() {
        return (GetSubjectiveAssignmentQuestionsFragment) mFragment;
    }


}
