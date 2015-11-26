package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
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
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.fragments.ExamObjectiveDetailFragment;
import com.ism.teacher.fragments.ExamSubjectiveDetailFragment;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.model.Data;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.model.Examsubmittor;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;


/**
 * these is the assignment subjects adapter
 */
public class ExamWiseAssignmentAdapter extends RecyclerView.Adapter<ExamWiseAssignmentAdapter.ViewHolder> {

    private static final String TAG = ExamWiseAssignmentAdapter.class.getSimpleName();

    Context mContext;
    ArrayList<Examsubmittor> listOfAssignments = new ArrayList<>();
    Fragment mFragment;
    MyTypeFace myTypeFace;
    private ImageLoader imageLoader;
    String exam_mode = "";
    public static String EXAM_OBJECTIVE = "objective";
    public static String EXAM_SUBJECTIVE = "subjective";
    String examid = "";

    ExamObjectiveDetailFragment examObjectiveDetailFragment;
    ExamSubjectiveDetailFragment examSubjectiveDetailFragment;

    public ExamWiseAssignmentAdapter(String examid, Context context, Fragment fragment, String exam_mode) {
        this.mContext = context;
        this.mFragment = fragment;
        myTypeFace = new MyTypeFace(context);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        this.exam_mode = exam_mode;
        this.examid = examid;

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

        holder.txt_studentName.setText(listOfAssignments.get(position).getStudentName());
        holder.txt_submission_date.setText(Utility.getFormattedDate("dd-MMM-yyyy", listOfAssignments.get(position).getSubmissionDate()));
        holder.txt_assessed_status.setText(listOfAssignments.get(position).getExamStatus());

        imageLoader.displayImage(WebConstants.USER_IMAGES + "Users_Images/" + listOfAssignments.get(position).getStudentProfilePic(), holder.img_student, ISMTeacher.options);


        holder.ll_parent_student_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exam_mode.equalsIgnoreCase(EXAM_OBJECTIVE)) {

                    //Objective Question + Evaluation api(student id)
                    // last param is true because we have to call evaluation along with subjective ques list+ student id
                    examObjectiveDetailFragment = new ExamObjectiveDetailFragment(mContext, examid, listOfAssignments.get(position).getStudentId(), true);
                    mFragment.getFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, examObjectiveDetailFragment).commit();

                } else if (exam_mode.equalsIgnoreCase(EXAM_SUBJECTIVE)) {

                    //Subjective Question + Evaluation api(student id)
                    // last param is true because we have to call evaluation along with subjective ques list+ student id
                    examSubjectiveDetailFragment = new ExamSubjectiveDetailFragment(mContext, examid, WebConstants.STUDENT_ID_1, true,listOfAssignments.get(position).getStudentName());
//                    examSubjectiveDetailFragment = new ExamSubjectiveDetailFragment(mContext, examid, listOfAssignments.get(position).getStudentId(), true);
                    mFragment.getFragmentManager().beginTransaction().replace(R.id.fl_teacher_office_home, examSubjectiveDetailFragment).commit();

                }
            }
        });
    }


    public void addAll(ArrayList<Examsubmittor> evaluationModelArrayList) {

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
