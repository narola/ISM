package com.ism.author.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.object.MyTypeFace;
import com.ism.author.model.Data;
import com.ism.author.model.FragmentArgument;

import java.util.ArrayList;

/**
 * Created by c166 on 09/11/15.
 */
public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {

    private static final String TAG = AssignmentAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Data> listOfAssignments = new ArrayList<Data>();
    private MyTypeFace myTypeFace;
    private FragmentArgument fragmentArgument;
    private LayoutInflater inflater;


    public AssignmentAdapter(Context mContext) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.fragmentArgument = new FragmentArgument();
        this.myTypeFace = new MyTypeFace(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = inflater.inflate(R.layout.row_assessment, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            holder.tvAssignmentSubjectName.setTypeface(myTypeFace.getRalewayBold());
            holder.tvAssignmentCourseName.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvAssignmentDate.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvAssignmentClassName.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvAssignmentNoofAssessed.setTypeface(myTypeFace.getRalewayBold());
            holder.tvAssignmentAssessed.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvAssignmentNoofUnassessed.setTypeface(myTypeFace.getRalewayBold());
            holder.tvAssignmentUnassessed.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvAssignmentNoofQuestion.setTypeface(myTypeFace.getRalewayBold());
            holder.tvAssignmentQuestion.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvAssignmentType.setTypeface(myTypeFace.getRalewayRegular());


            holder.tvAssignmentSubjectName.setText(listOfAssignments.get(position).getSubjectName());
            holder.tvAssignmentClassName.setText(listOfAssignments.get(position).getClassroomName());
            holder.tvAssignmentDate.setText(mContext.getString(R.string.strassignmentdatecolon));
            holder.tvAssignmentDate.append(Utility.getSpannableString(" " + listOfAssignments.get(position).getPassPercentage(),
                    mContext.getResources().getColor(R.color.color_black)));

            holder.tvAssignmentNoofAssessed.setText(listOfAssignments.get(position).getTotal_student());
            holder.tvAssignmentNoofQuestion.setText(listOfAssignments.get(position).getTotal_student());

            if (listOfAssignments.get(position).getExamMode().equalsIgnoreCase("subjective")) {
                holder.tvAssignmentUnassessed.setText(mContext.getString(R.string.strunasssessed));
                holder.tvAssignmentNoofUnassessed.setText(listOfAssignments.get(position).getTotal_student());

            } else if (listOfAssignments.get(position).getExamMode().equalsIgnoreCase("objective")) {
                holder.tvAssignmentUnassessed.setText(mContext.getString(R.string.stravgscore));
                holder.tvAssignmentNoofUnassessed.setText(listOfAssignments.get(position).getTotal_student() + " " + mContext.getString(R.string.strpercent));
            }


            holder.tvAssignmentType.setText(mContext.getString(R.string.strassignmenttype) + " " + listOfAssignments.get(position).getExamMode());

            if (position % 2 == 0) {
                holder.rlTopAssignment.setBackgroundResource(R.drawable.bg_subject_red);
            } else {
                holder.rlTopAssignment.setBackgroundResource(R.drawable.bg_subject_yellow);
            }

            holder.llAssignmentContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    fragmentArgument.getFragmentArgumentObject().setExamId(listOfAssignments.get(position).getExamID());
                    fragmentArgument.getFragmentArgumentObject().setExamName(listOfAssignments.get(position).getExamName());
                    fragmentArgument.getFragmentArgumentObject().setClassroomId(listOfAssignments.get(position).getClassroomId());
                    fragmentArgument.getFragmentArgumentObject().setStudentName(listOfAssignments.get(position).getStudentName());
                    fragmentArgument.getFragmentArgumentObject().setPassPercentage(listOfAssignments.get(position).getPassPercentage());
                    fragmentArgument.getFragmentArgumentObject().setExamType(listOfAssignments.get(position).getExam_type());
                    fragmentArgument.getFragmentArgumentObject().setExamMode(listOfAssignments.get(position).getExamMode());
                    fragmentArgument.getFragmentArgumentObject().setDuration(listOfAssignments.get(position).getDuration());
                    fragmentArgument.getFragmentArgumentObject().setAssignmentNo(position);
                    fragmentArgument.getFragmentArgumentObject().setAssignmentName(listOfAssignments.get(position).getExamName());


                    ((AuthorHostActivity) mContext).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_ASSIGNMENT_SUBMITTOR, fragmentArgument);


                }
            });

            holder.llViewAssignmentQuestions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((AuthorHostActivity) mContext).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_GET_OBJECTIVE_ASSIGNMENT_QUESTIONS,
                            null);
                }
            });


        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }


    }

    @Override
    public int getItemCount() {
        return listOfAssignments.size();
    }

    public void addAll(ArrayList<Data> data) {
        try {
            this.listOfAssignments.clear();
            this.listOfAssignments.addAll(data);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlTopAssignment;
        TextView tvAssignmentSubjectName, tvAssignmentCourseName, tvAssignmentDate, tvAssignmentClassName, tvAssignmentNoofAssessed,
                tvAssignmentAssessed, tvAssignmentNoofUnassessed, tvAssignmentUnassessed, tvAssignmentNoofQuestion, tvAssignmentQuestion,
                tvAssignmentType;
        LinearLayout llAssignmentContainer, llViewAssignmentQuestions;

        public ViewHolder(View itemView) {
            super(itemView);

            rlTopAssignment = (RelativeLayout) itemView.findViewById(R.id.rl_top_assignment);
            tvAssignmentSubjectName = (TextView) itemView.findViewById(R.id.tv_assignment_subject_name);
            tvAssignmentCourseName = (TextView) itemView.findViewById(R.id.tv_assignment_course_name);
            tvAssignmentDate = (TextView) itemView.findViewById(R.id.tv_assignment_date);
            tvAssignmentClassName = (TextView) itemView.findViewById(R.id.tv_assignment_class_name);
            tvAssignmentNoofAssessed = (TextView) itemView.findViewById(R.id.tv_assignment_noof_assessed);
            tvAssignmentAssessed = (TextView) itemView.findViewById(R.id.tv_assignment_assessed);
            tvAssignmentNoofUnassessed = (TextView) itemView.findViewById(R.id.tv_assignment_noof_unassessed);
            tvAssignmentUnassessed = (TextView) itemView.findViewById(R.id.tv_assignment_unassessed);
            tvAssignmentNoofQuestion = (TextView) itemView.findViewById(R.id.tv_assignment_noof_question);
            tvAssignmentQuestion = (TextView) itemView.findViewById(R.id.tv_assignment_question);
            tvAssignmentType = (TextView) itemView.findViewById(R.id.tv_assignment_type);
            llAssignmentContainer = (LinearLayout) itemView.findViewById(R.id.ll_assignment_container);
            llViewAssignmentQuestions = (LinearLayout) itemView.findViewById(R.id.ll_view_assignment_questions);


        }
    }


}
