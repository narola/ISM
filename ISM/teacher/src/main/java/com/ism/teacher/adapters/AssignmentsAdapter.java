package com.ism.teacher.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
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
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.fragments.office.AllAssignmentsFragment;
import com.ism.teacher.fragments.office.TeacherOfficeFragment;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.model.Exams;

import java.util.ArrayList;


/**
 * these is the assignment subjects adapter
 */
public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.ViewHolder> {

    private static final String TAG = AssignmentsAdapter.class.getSimpleName();
    Context mContext;
    ArrayList<Exams> arrayListAssignments = new ArrayList<Exams>();
    Fragment mFragment;


    /*this bundle arguments are use for both in edit exam and create exam we have to set it both the places*/
    public static String ARG_EXAM_ID = "examId";
    public static String ARG_EXAM_NAME = "examName";
    public static String ARG_EXAM_CLASSROOM_ID = "examClassRoomId";
    public static String ARG_EXAM_CLASSROOM_NAME = "examClassRoomName";
    public static String ARG_EXAM_SUBJECT_ID = "examSubjectId";
    public static String ARG_EXAM_SUBJECT_NAME = "examSubjectName";
    public static String ARG_EXAM_TOPIC_ID = "examTopicId";
    public static String ARG_EXAM_TOPIC_NAME = "examTopicName";
    public static String ARG_EXAM_BOOK_ID = "examBookId";
    public static String ARG_EXAM_BOOK_NAME = "examBookName";
    public static String ARG_EXAM_CATEGORY = "examCategory";
    public static String ARG_EXAM_TYPE = "examType";
    public static String ARG_EXAM_MODE = "examMode";
    public static String ARG_EXAM_DURATION = "examDuration";
    public static String ARG_ASSIGNMENT_NO = "examNo";
    public static String ARG_EXAM_PASS_PERCENTAGE = "examPassPercentage";
    public static String ARG_EXAM_CREATED_DATE = "examCreatedDate";
    public static String ARG_ISLOAD_FRAGMENTFOREVALUATION = "examIsLoadFragmentForEvaluation";

    //new params
    public static String ARG_EXAM_ATTEMPT_COUNT = "examAttemptCount";
    public static String ARG_EXAM_INSTRUCTIONS = "examInstructions";
    public static String ARG_EXAM_IS_RANDOM_QUESTION = "examIsRandomQuestion";
    public static String ARG_EXAM_IS_NEGATIVE_MARKING = "examIsNegativeMarking";
    public static String ARG_EXAM_NEGATIVE_MARK_VALUE = "examNegativeMarkValue";
    public static String ARG_EXAM_IS_USE_QUESTION_SCORE = "examIsUseQuestionScore";
    public static String ARG_EXAM_CORRECT_ANSWER_SCORE = "examCorrectAnswerScore";
    public static String ARG_EXAM_IS_DECLARE_RESULTS = "examIsDeclareResults";
    public static String ARG_EXAM_ASSESSOR = "examAssessor";
    public static String ARG_EXAM_START_DATE = "examStartDate";
    public static String ARG_EXAM_START_TIME = "examStartTime";
    public static String ARG_FRAGMENT_TYPE = "fragmentType";

    FragmentManager fragmentManager;

    public AssignmentsAdapter(Context context, Fragment fragment) {
        this.mContext = context;
        this.mFragment = fragment;
        fragmentManager = ((Activity) mContext).getFragmentManager();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View assignments_view = inflater.inflate(R.layout.row_assignment, parent, false);
        ViewHolder viewHolder = new ViewHolder(assignments_view);
        return viewHolder;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llParentAssignment, llViewQuestions;
        RelativeLayout rlTopAssignment;
        TextView txtExamName, txtAssignmentClassName, txtAssignmentDate, txtNumberAssessedQuestion, txtNumberUnassessedQuestion, txtNumberTotalQuestions;
        TextView txtAssignmentSubject, txtAssessedLabel, txtUnassessedLabel, txtQuestionLabel, txtAssignmentType;

        public ViewHolder(View itemView) {
            super(itemView);

            llParentAssignment = (LinearLayout) itemView.findViewById(R.id.ll_parent_assignment);
            llViewQuestions = (LinearLayout) itemView.findViewById(R.id.ll_view_questions);
            rlTopAssignment = (RelativeLayout) itemView.findViewById(R.id.rl_results);

            txtAssignmentSubject = (TextView) itemView.findViewById(R.id.txt_assignment_subject);
            txtExamName = (TextView) itemView.findViewById(R.id.txt_exam_name);
            txtAssignmentClassName = (TextView) itemView.findViewById(R.id.txt_assignment_class_name);
            txtAssignmentDate = (TextView) itemView.findViewById(R.id.txt_assignment_date);
            txtNumberAssessedQuestion = (TextView) itemView.findViewById(R.id.txt_number_assessed_question);
            txtNumberUnassessedQuestion = (TextView) itemView.findViewById(R.id.txt_number_unassessed_question);
            txtNumberTotalQuestions = (TextView) itemView.findViewById(R.id.txt_number_total_questions);
            txtAssignmentType = (TextView) itemView.findViewById(R.id.txt_assignment_type);
            txtAssessedLabel = (TextView) itemView.findViewById(R.id.txt_assessed_label);
            txtUnassessedLabel = (TextView) itemView.findViewById(R.id.txt_unassessed_label);
            txtQuestionLabel = (TextView) itemView.findViewById(R.id.txt_question_label);

            //        holder.txtExamName.setTypeface(Global.myTypeFace.getRalewayRegular());
//        holder.txtAssignmentDate.setTypeface(Global.myTypeFace.getRalewayRegular());
//        holder.txtAssignmentClassName.setTypeface(Global.myTypeFace.getRalewayRegular());
//        holder.txtAssessedLabel.setTypeface(Global.myTypeFace.getRalewayRegular());
//        holder.txtNumberAssessedQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());
//        holder.txtUnassessedLabel.setTypeface(Global.myTypeFace.getRalewayRegular());
//        holder.txtNumberUnassessedQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());
//        holder.txtQuestionLabel.setTypeface(Global.myTypeFace.getRalewayRegular());
//        holder.txtAssignmentType.setTypeface(Global.myTypeFace.getRalewayRegular());

        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txtAssignmentSubject.setTypeface(Global.myTypeFace.getRalewayBold());


        holder.txtAssignmentSubject.setText(arrayListAssignments.get(position).getSubjectName());
        holder.txtExamName.setText(arrayListAssignments.get(position).getExamName());
        holder.txtAssignmentClassName.setText(arrayListAssignments.get(position).getClassroomName());
        holder.txtAssignmentDate.setText(Html.fromHtml("<font color='#0E970C'>Assignment Date:" + "</font>" + (Utility.getFormattedDate("dd-MMM-yyyy", arrayListAssignments.get(position).getExamCreatedDate()))));

        holder.txtNumberAssessedQuestion.setText(arrayListAssignments.get(position).getTotalAssessed());
        holder.txtNumberUnassessedQuestion.setText(arrayListAssignments.get(position).getTotalUnassessed());
        holder.txtNumberTotalQuestions.setText(arrayListAssignments.get(position).getTotalQuestion());
        holder.txtAssignmentType.setText(Html.fromHtml("<font color='#77C2EA'>Assignment Type:" + arrayListAssignments.get(position).getExamMode() + "</font>"));


        if (position % 2 == 0) {
            holder.rlTopAssignment.setBackgroundResource(R.drawable.bg_subject_red);
        } else {
            holder.rlTopAssignment.setBackgroundResource(R.drawable.bg_subject_yellow);
        }

        if (arrayListAssignments.get(position).getExamMode().equalsIgnoreCase(mContext.getString(R.string.strobjective))) {
            holder.txtUnassessedLabel.setText(mContext.getString(R.string.straverage_score));
        } else {
            holder.txtUnassessedLabel.setText(mContext.getString(R.string.strunasssessed));
        }


        holder.llParentAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setBundleArguments(position);
                getBundleArguments().putBoolean(ARG_ISLOAD_FRAGMENTFOREVALUATION, true);

                TeacherOfficeFragment teacherOfficeFragment = (TeacherOfficeFragment) fragmentManager.findFragmentByTag(AppConstant.FRAGMENT_TAG_TEACHER_OFFICE);
                teacherOfficeFragment.loadFragmentInTeacherOffice(TeacherOfficeFragment.FRAGMENT_ASSIGNMENT_SUBMITTER);

                //  getFragment().loadOfficeSubmitter(getBundleArguments());
            }
        });

        holder.llViewQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setBundleArguments(position);
                getBundleArguments().putBoolean(ARG_ISLOAD_FRAGMENTFOREVALUATION, false);

                TeacherOfficeFragment teacherOfficeFragment = (TeacherOfficeFragment) fragmentManager.findFragmentByTag(AppConstant.FRAGMENT_TAG_TEACHER_OFFICE);
                teacherOfficeFragment.loadFragmentInTeacherOffice(TeacherOfficeFragment.FRAGMENT_OBJECTIVE_QUESTIONS_VIEW);

            }
        });

    }

    public void setBundleArguments(int position) {
        getBundleArguments().putString(ARG_EXAM_ID, arrayListAssignments.get(position).getExamId());
        getBundleArguments().putString(ARG_EXAM_NAME, arrayListAssignments.get(position).getExamName());
        getBundleArguments().putString(ARG_EXAM_CLASSROOM_ID, arrayListAssignments.get(position).getClassroomId());
        getBundleArguments().putString(ARG_EXAM_CLASSROOM_NAME, arrayListAssignments.get(position).getClassroomName());
        getBundleArguments().putString(ARG_EXAM_SUBJECT_ID, arrayListAssignments.get(position).getSubjectId());
        getBundleArguments().putString(ARG_EXAM_SUBJECT_NAME, arrayListAssignments.get(position).getSubjectName());
        getBundleArguments().putString(ARG_EXAM_TOPIC_ID, arrayListAssignments.get(position).getTopicId());
        getBundleArguments().putString(ARG_EXAM_BOOK_ID, arrayListAssignments.get(position).getBookId());
        getBundleArguments().putString(ARG_EXAM_CATEGORY, arrayListAssignments.get(position).getExamCategory());
        getBundleArguments().putString(ARG_EXAM_TYPE, arrayListAssignments.get(position).getExamType());
        getBundleArguments().putString(ARG_EXAM_MODE, arrayListAssignments.get(position).getExamMode());
        getBundleArguments().putString(ARG_EXAM_DURATION, arrayListAssignments.get(position).getDuration());
        getBundleArguments().putInt(ARG_ASSIGNMENT_NO, position);
        getBundleArguments().putString(ARG_EXAM_PASS_PERCENTAGE, arrayListAssignments.get(position).getPassPercentage());
        getBundleArguments().putString(ARG_EXAM_CORRECT_ANSWER_SCORE, arrayListAssignments.get(position).getCorrectAnswerScore());
        getBundleArguments().putString(ARG_EXAM_CREATED_DATE, arrayListAssignments.get(position).getExamCreatedDate());

        //new
        getBundleArguments().putString(ARG_EXAM_ATTEMPT_COUNT, arrayListAssignments.get(position).getAttemptCount());
        getBundleArguments().putString(ARG_EXAM_INSTRUCTIONS, arrayListAssignments.get(position).getExamInstructions());
        getBundleArguments().putString(ARG_EXAM_IS_RANDOM_QUESTION, arrayListAssignments.get(position).getRandomQuestion());
        getBundleArguments().putString(ARG_EXAM_IS_NEGATIVE_MARKING, arrayListAssignments.get(position).getNegativeMarking());
        getBundleArguments().putString(ARG_EXAM_NEGATIVE_MARK_VALUE, arrayListAssignments.get(position).getNegativeMarkValue());
        getBundleArguments().putString(ARG_EXAM_IS_USE_QUESTION_SCORE, arrayListAssignments.get(position).getUseQuestionScore());
        getBundleArguments().putString(ARG_EXAM_CORRECT_ANSWER_SCORE, arrayListAssignments.get(position).getCorrectAnswerScore());
        getBundleArguments().putString(ARG_EXAM_IS_DECLARE_RESULTS, arrayListAssignments.get(position).getDeclareResults());

        getBundleArguments().putString(ARG_EXAM_ASSESSOR, arrayListAssignments.get(position).getExamAssessor());
        getBundleArguments().putString(ARG_EXAM_START_DATE, arrayListAssignments.get(position).getExamStartDate());
        getBundleArguments().putString(ARG_EXAM_START_TIME, arrayListAssignments.get(position).getExamStartTime());

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


    private AllAssignmentsFragment getFragment() {
        return (AllAssignmentsFragment) mFragment;
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) mContext).getBundle();
    }
}
