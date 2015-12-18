package com.ism.teacher.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.teacher.ISMTeacher;
import com.ism.teacher.R;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.fragments.GetObjectiveAssignmentQuestionsFragment;
import com.ism.teacher.fragments.GetSubjectiveAssignmentQuestionsFragment;
import com.ism.teacher.fragments.TeacherOfficeFragment;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.model.Examsubmittor;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * these is the assignment subjects adapter
 */
public class AssignmentSubmitterAdapter extends RecyclerView.Adapter<AssignmentSubmitterAdapter.ViewHolder> {

    private static final String TAG = AssignmentSubmitterAdapter.class.getSimpleName();

    Context mContext;
    ArrayList<Examsubmittor> arrListExamSubmittor = new ArrayList<>();
    MyTypeFace myTypeFace;
    private ImageLoader imageLoader;
    // String exam_mode = "";
    public static String EXAM_OBJECTIVE = "objective";
    public static String EXAM_SUBJECTIVE = "subjective";
    String examid = "";
//    private Bundle bundleArgument;

    FragmentManager fragmentManager;

    public static String ARG_STUDENT_ID = "studenId";
    public static String ARG_STUDENT_POSITION = "studenPosition";
    public static String ARG_STUDENT_PROFILE_PIC = "studentProfilePic";
    public static String ARG_STUDENT_NAME = "studentName";


    public AssignmentSubmitterAdapter(Context mContext) {
        this.mContext = mContext;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this.mContext));
        myTypeFace = new MyTypeFace(mContext);

//        if (bundleArgument != null) {
//            examid = bundleArgument.getString(AssignmentsAdapter.ARG_EXAM_ID);
//            exam_mode = bundleArgument.getString(AssignmentsAdapter.ARG_EXAM_MODE);
//        }
        //Get FragmentManager
        fragmentManager = ((Activity) mContext).getFragmentManager();
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
        LinearLayout ll_parent_student_row;


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
            ll_parent_student_row = (LinearLayout) itemView.findViewById(R.id.ll_parent_student_row);
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txt_studentName.setText(arrListExamSubmittor.get(position).getStudentName());
        holder.txt_submission_date.setText(Utility.getFormattedDate("dd-MMM-yyyy", arrListExamSubmittor.get(position).getSubmissionDate()));
        holder.txt_assessed_status.setText(arrListExamSubmittor.get(position).getExamStatus());

        imageLoader.displayImage(WebConstants.USER_IMAGES + "Users_Images/" + arrListExamSubmittor.get(position).getStudentProfilePic(), holder.img_student, ISMTeacher.options);


        holder.ll_parent_student_row.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                getBundleArguments().putString(ARG_STUDENT_ID, arrListExamSubmittor.get(position).getStudentId());
                getBundleArguments().putInt(ARG_STUDENT_POSITION, position);
                getBundleArguments().putString(ARG_STUDENT_PROFILE_PIC, arrListExamSubmittor.get(position).getStudentProfilePic());
                getBundleArguments().putString(ARG_STUDENT_NAME, arrListExamSubmittor.get(position).getStudentName());
                getBundleArguments().putString(AssignmentsAdapter.ARG_EXAM_TYPE, getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_TYPE));

                getBundleArguments().putBoolean(AssignmentsAdapter.ARG_ISLOAD_FRAGMENTFOREVALUATION, true);

                /**
                 * For objective Questions
                 * Call  GetObjectiveAssignmentQuestionsFragment
                 */

                if (getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_MODE).equalsIgnoreCase(EXAM_OBJECTIVE)) {
                    TeacherOfficeFragment.current_fragment = TeacherOfficeFragment.FRAGMENT_GET_OBJECTIVE_QUESTIONS_VIEW;
                    fragmentManager.beginTransaction().replace(R.id.fl_teacher_office_home, GetObjectiveAssignmentQuestionsFragment.newInstance(getBundleArguments()), AppConstant.FRAGMENT_TAG_VIEW_ASSIGNMENT_QUESTION).commit();
                }

                /**
                 * For subjective Questions
                 * Call  GetSubjectiveAssignmentQuestionsFragment divided into three parts
                 * Students,Subjective Ques with evaluation and palette of answers
                 */

                else if (getBundleArguments().getString(AssignmentsAdapter.ARG_EXAM_MODE).equalsIgnoreCase(EXAM_SUBJECTIVE)) {

                    fragmentManager.beginTransaction().replace(R.id.fl_teacher_office_home, GetSubjectiveAssignmentQuestionsFragment.newInstance(getBundleArguments())).commit();
                }
            }
        });
    }


    public void addAll(ArrayList<Examsubmittor> evaluationModelArrayList) {

        try {
            this.arrListExamSubmittor.clear();
            this.arrListExamSubmittor.addAll(evaluationModelArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrListExamSubmittor.size();
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) mContext).getBundle();
    }

}
