package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.fragments.TeacherExamWiseAssignments;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.model.Data;

import java.util.ArrayList;


/**
 * these is the assignment subjects adapter
 */
public class AssignmentSubjectsAdapter extends RecyclerView.Adapter<AssignmentSubjectsAdapter.ViewHolder> {

    private static final String TAG = AssignmentSubjectsAdapter.class.getSimpleName();

    public static String EXAM_OBJECTIVE="objective";
    public static String EXAM_SUBJECTIVE="subjective";
    Context mContext;
    ArrayList<Data> listOfAssignments = new ArrayList<Data>();
    Fragment mFragment;
    MyTypeFace myTypeFace;


    public AssignmentSubjectsAdapter(Context context, Fragment fragment) {
        this.mContext = context;
        this.mFragment = fragment;
        myTypeFace = new MyTypeFace(context);

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

        LinearLayout ll_parent_assignment, llViewQuestions, llAssessedQuestion, llUnassessedQuestion;
        RelativeLayout rlTopAssignment;
        TextView txtAssignmentCourse, txtAssignmentClassName, txtAssignmentDate, txtNumberAssessedQuestion, txtNumberUnassessedQuestion, txtNumberTotalQuestions;
        TextView txtAssignmentSubject, txtAssessedLabel, txtUnassessedLabel, txtQuestionLabel, txtAssignmentType;

        public ViewHolder(View itemView) {
            super(itemView);

            ll_parent_assignment = (LinearLayout) itemView.findViewById(R.id.ll_parent_assignment);

            llAssessedQuestion = (LinearLayout) itemView.findViewById(R.id.ll_assessed_question);
            llUnassessedQuestion = (LinearLayout) itemView.findViewById(R.id.ll_unassessed_question);
            llViewQuestions = (LinearLayout) itemView.findViewById(R.id.ll_view_questions);

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
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txtAssignmentSubject.setText(listOfAssignments.get(position).getSubject_name());
        //holder.txtAssignmentCourse.setText(listOfAssignments.get(position).getClass_name());
        holder.txtAssignmentClassName.setText(listOfAssignments.get(position).getClassroom_name());
//        holder.txtAssignmentDate.setText(listOfAssignments.get(position).);

//        holder.txtNumberAssessedQuestion.setText(listOfAssignments.get(position).);
//        holder.txtNumberUnassessedQuestion.setText(listOfAssignments.get(position).);
//        holder.txtNumberTotalQuestions.setText(listOfAssignments.get(position).);
        holder.txtAssignmentType.setText(Html.fromHtml("<font color='#77C2EA'>Assignment Type:" + listOfAssignments.get(position).getExam_mode() + "</font>"));


        if (position % 2 == 0) {
            holder.rlTopAssignment.setBackgroundResource(R.drawable.bg_subject_red);
        } else {
            holder.rlTopAssignment.setBackgroundResource(R.drawable.bg_subject_yellow);
        }

        if (listOfAssignments.get(position).getExam_mode().equalsIgnoreCase("objective")) {
            holder.txtUnassessedLabel.setText("Average Score");
        } else {
            holder.txtUnassessedLabel.setText("Unassessed");
        }


        holder.llAssessedQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getFragmentManager().beginTransaction().
                        replace(R.id.fl_teacher_office_home, new TeacherExamWiseAssignments(mFragment, listOfAssignments.get(position).getExam_id())).commit();
            }
        });

        holder.llUnassessedQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFragment.getFragmentManager().beginTransaction().
                        replace(R.id.fl_teacher_office_home, new TeacherExamWiseAssignments(mFragment, listOfAssignments.get(position).getExam_id())).commit();
            }
        });

        holder.llViewQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(listOfAssignments.get(position).getExam_mode().equalsIgnoreCase(EXAM_OBJECTIVE))
//
//
//                mFragment.getFragmentManager().beginTransaction().
//                        replace(R.id.fl_teacher_office_home, new TeacherExamWiseAssignments(mFragment, listOfAssignments.get(position).getExam_id())).commit();
                // send the value of exam_id,role_id
                if(listOfAssignments.get(position).getExam_mode().equalsIgnoreCase(EXAM_OBJECTIVE))
                {
                    ((TeacherHostActivity)mContext).loadFragmentInMainContainer(TeacherHostActivity.FRAGMENT_EXAM_OBJECTIVE_DETAILS);

                }

                else if(listOfAssignments.get(position).getExam_mode().equalsIgnoreCase(EXAM_SUBJECTIVE))
                {
                    ((TeacherHostActivity)mContext).loadFragmentInMainContainer(TeacherHostActivity.FRAGMENT_EXAM_SUBJECTIVE_DETAILS);


                }

            }
        });




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
        return listOfAssignments.size();
    }


}
