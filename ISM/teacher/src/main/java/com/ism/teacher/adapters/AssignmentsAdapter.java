package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.fragments.GetAssignmentsSubmitterFragment;
import com.ism.teacher.fragments.GetObjectiveAssignmentQuestionsFragment;
import com.ism.teacher.fragments.GetSubjectiveAssignmentQuestionsFragment;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.ws.model.Exams;

import java.util.ArrayList;


/**
 * these is the assignment subjects adapter
 */
public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.ViewHolder> {

    private static final String TAG = AssignmentsAdapter.class.getSimpleName();

    public static String EXAM_OBJECTIVE = "objective";
    public static String EXAM_SUBJECTIVE = "subjective";
    Context mContext;
    ArrayList<Exams> arrayListAssignments = new ArrayList<Exams>();
    Fragment mFragment;
    MyTypeFace myTypeFace;


    public static String ARG_EXAM_ID = "examId";
    public static String ARG_EXAM_NAME = "examName";
    public static String ARG_CLASSROOM_ID = "classRoomId";
    public static String ARG_CLASSROOM_NAME = "classRoomName";
    public static String ARG_EXAM_CATEGORY = "examCategory";
    public static String ARG_SUBJECT_NAME = "subjectName";
    public static String ARG_PASS_PERCENTAGE = "passPercentage";
    public static String ARG_EXAM_TYPE = "examType";
    public static String ARG_EXAM_MODE = "examMode";
    public static String ARG_EXAM_DURATION = "examDuration";
    public static String ARG_ASSIGNMENT_NO = "examAssignmentNo";
    public static String ARG_ISLOAD_FRAGMENTFOREVALUATION = "examIsLoadFragmentForEvaluation";


    public AssignmentsAdapter(Context context, Fragment fragment) {
        this.mContext = context;
        this.mFragment = fragment;
        myTypeFace = new MyTypeFace(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View assignments_view = inflater.inflate(R.layout.assignment_subjects_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(assignments_view);
        return viewHolder;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llParentAssignment, llViewQuestions, llAssessedQuestion, llUnassessedQuestion;
        RelativeLayout rlTopAssignment;
        TextView txtAssignmentCourse, txtAssignmentClassName, txtAssignmentDate, txtNumberAssessedQuestion, txtNumberUnassessedQuestion, txtNumberTotalQuestions;
        TextView txtAssignmentSubject, txtAssessedLabel, txtUnassessedLabel, txtQuestionLabel, txtAssignmentType;

        public ViewHolder(View itemView) {
            super(itemView);

            llParentAssignment = (LinearLayout) itemView.findViewById(R.id.ll_parent_assignment);

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

        holder.txtAssignmentSubject.setText(arrayListAssignments.get(position).getSubjectName());
        //holder.txtAssignmentCourse.setText(arrayListAssignments.get(position).getClass_name());
        holder.txtAssignmentClassName.setText(arrayListAssignments.get(position).getClassroomName());
//        holder.txtAssignmentDate.setText(arrayListAssignments.get(position).);

//        holder.txtNumberAssessedQuestion.setText(arrayListAssignments.get(position).);
//        holder.txtNumberUnassessedQuestion.setText(arrayListAssignments.get(position).);
//        holder.txtNumberTotalQuestions.setText(arrayListAssignments.get(position).);
        holder.txtAssignmentType.setText(Html.fromHtml("<font color='#77C2EA'>Assignment Type:" + arrayListAssignments.get(position).getExamMode() + "</font>"));


        if (position % 2 == 0) {
            holder.rlTopAssignment.setBackgroundResource(R.drawable.bg_subject_red);
        } else {
            holder.rlTopAssignment.setBackgroundResource(R.drawable.bg_subject_yellow);
        }

        if (arrayListAssignments.get(position).getExamMode().equalsIgnoreCase("objective")) {
            holder.txtUnassessedLabel.setText("Average Score");
        } else {
            holder.txtUnassessedLabel.setText("Unassessed");
        }


        final Bundle bundleAssignmentDetails = new Bundle();
        bundleAssignmentDetails.putString(ARG_EXAM_ID, arrayListAssignments.get(position).getExamId());
        //bundleAssignmentDetails.putString(ARG_EXAM_ID, "9");
        bundleAssignmentDetails.putString(ARG_EXAM_NAME, arrayListAssignments.get(position).getExamName());
        bundleAssignmentDetails.putString(ARG_CLASSROOM_ID, arrayListAssignments.get(position).getClassroomId());
        bundleAssignmentDetails.putString(ARG_EXAM_CATEGORY, arrayListAssignments.get(position).getExamCategory());
        bundleAssignmentDetails.putString(ARG_SUBJECT_NAME, arrayListAssignments.get(position).getSubjectName());
        bundleAssignmentDetails.putString(ARG_PASS_PERCENTAGE, arrayListAssignments.get(position).getPassPercentage());
        bundleAssignmentDetails.putString(ARG_EXAM_TYPE, arrayListAssignments.get(position).getExamType());
        bundleAssignmentDetails.putString(ARG_EXAM_MODE, arrayListAssignments.get(position).getExamMode());
        bundleAssignmentDetails.putString(ARG_EXAM_DURATION, arrayListAssignments.get(position).getDuration());
        bundleAssignmentDetails.putString(ARG_CLASSROOM_NAME, arrayListAssignments.get(position).getClassroomName());
        bundleAssignmentDetails.putInt(ARG_ASSIGNMENT_NO, position);


        holder.llParentAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundleAssignmentDetails.putBoolean(ARG_ISLOAD_FRAGMENTFOREVALUATION, true);
                mFragment.getFragmentManager().beginTransaction().
                        replace(R.id.fl_teacher_office_home, GetAssignmentsSubmitterFragment.newInstance(bundleAssignmentDetails)).commit();
            }
        });

        holder.llViewQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (arrayListAssignments.get(position).getExamMode().equalsIgnoreCase(EXAM_OBJECTIVE)) {

                    bundleAssignmentDetails.putBoolean(ARG_ISLOAD_FRAGMENTFOREVALUATION, false);
                    mFragment.getFragmentManager().beginTransaction().
                            replace(R.id.fl_teacher_office_home, GetObjectiveAssignmentQuestionsFragment.newInstance(bundleAssignmentDetails)).commit();


                } else if (arrayListAssignments.get(position).getExamMode().equalsIgnoreCase(EXAM_SUBJECTIVE)) {

                    bundleAssignmentDetails.putBoolean(ARG_ISLOAD_FRAGMENTFOREVALUATION, false);
                    mFragment.getFragmentManager().beginTransaction().
                            replace(R.id.fl_teacher_office_home, GetSubjectiveAssignmentQuestionsFragment.newInstance(bundleAssignmentDetails)).commit();

                }

            }
        });


    }


    public void addAll(ArrayList<Exams> exams) {

        try {
            this.arrayListAssignments.clear();
            this.arrayListAssignments.addAll(exams);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayListAssignments.size();
    }


}
