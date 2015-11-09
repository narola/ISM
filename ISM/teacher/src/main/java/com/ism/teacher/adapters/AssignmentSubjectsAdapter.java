package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.model.Data;

import java.util.ArrayList;


/**
 * these is the assignment subjects adapter
 */
public class AssignmentSubjectsAdapter extends RecyclerView.Adapter<AssignmentSubjectsAdapter.ViewHolder> {

    private static final String TAG = AssignmentSubjectsAdapter.class.getSimpleName();

    Context mContext;
    ArrayList<Data> listOfAssignments = new ArrayList<Data>();
    Fragment mFragment;


    public AssignmentSubjectsAdapter(Context context, Fragment fragment) {
        this.mContext = context;
        this.mFragment = fragment;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.assignment_subjects_row, parent, false);
//        View contactView = inflater.inflate(R.layout.assignment_student_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlTopAssignment;
        TextView txtAssignmentCourse, txtAssignmentClassName, txtAssignmentDate, txtNumberAssessedQuestion, txtNumberUnassessedQuestion, txtNumberTotalQuestions;
        TextView txtAssignmentSubject, txtAssessedLabel, txtUnassessedLabel, txtQuestionLabel, txtAssignmentType;

        public ViewHolder(View itemView) {
            super(itemView);

            rlTopAssignment = (RelativeLayout) itemView.findViewById(R.id.rl_top_assignment);
            txtAssignmentSubject = (TextView) itemView.findViewById(R.id.txt_assignment_subject);

            txtAssignmentCourse = (TextView) itemView.findViewById(R.id.txt_assignment_course);
            txtAssignmentClassName = (TextView) itemView.findViewById(R.id.txt_assignment_class_name);
            txtAssignmentDate = (TextView) itemView.findViewById(R.id.txt_assignment_date);

            txtNumberAssessedQuestion = (TextView) itemView.findViewById(R.id.txt_number_assessed_question);
            txtNumberUnassessedQuestion = (TextView) itemView.findViewById(R.id.txt_number_unassessed_question);
            txtNumberTotalQuestions = (TextView) itemView.findViewById(R.id.txt_number_total_questions);
            txtAssignmentType = (TextView) itemView.findViewById(R.id.txt_assignment_type);


            txtAssessedLabel = (TextView) itemView.findViewById(R.id.txt_assessed_label);
            txtUnassessedLabel = (TextView) itemView.findViewById(R.id.txt_unassessed_label);
            txtQuestionLabel = (TextView) itemView.findViewById(R.id.txt_question_label);


        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

       /* holder.txtAssignmentSubject.setText(listOfAssignments.get(position).getSubject_name());
        //holder.txtAssignmentCourse.setText(listOfAssignments.get(position).getClass_name());
        holder.txtAssignmentClassName.setText(listOfAssignments.get(position).getClass_name());
//        holder.txtAssignmentDate.setText(listOfAssignments.get(position).);

//        holder.txtNumberAssessedQuestion.setText(listOfAssignments.get(position).);
//        holder.txtNumberUnassessedQuestion.setText(listOfAssignments.get(position).);
//        holder.txtNumberTotalQuestions.setText(listOfAssignments.get(position).);
//
        holder.txtAssignmentType.setText(listOfAssignments.get(position).getExam_mode());*/


        if(position % 2 == 0){
            holder.rlTopAssignment.setBackgroundResource(R.drawable.bg_subject_red);
        }
        else {
            holder.rlTopAssignment.setBackgroundResource(R.drawable.bg_subject_yellow);
        }


    }


    public void addAll(ArrayList<Data> data) {

        try {
            this.listOfAssignments.clear();
            this.listOfAssignments.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 5;
    }


}
