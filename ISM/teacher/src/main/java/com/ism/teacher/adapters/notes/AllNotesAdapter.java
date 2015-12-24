package com.ism.teacher.adapters.notes;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.fragments.office.TeacherOfficeFragment;
import com.ism.teacher.object.Global;

/**
 * Created by c75 on 21/12/15.
 */
public class AllNotesAdapter extends RecyclerView.Adapter<AllNotesAdapter.ViewHolder> {

    private static final String TAG = AllNotesAdapter.class.getSimpleName();
    Context mContext;
    FragmentManager fragmentManager;

    public AllNotesAdapter(Context context) {
        this.mContext = context;
        fragmentManager = ((Activity) mContext).getFragmentManager();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlNotes;
        LinearLayout llParentNotes;
        TextView tvNotesSubject, tvExamName, tvNotesClassName, tvUpdatedDate, tvNotes, tvNotesLabel, tvTopics, tvTopicsLabel;

        public ViewHolder(View itemView) {
            super(itemView);
            rlNotes = (RelativeLayout) itemView.findViewById(R.id.rl_notes);
            llParentNotes = (LinearLayout) itemView.findViewById(R.id.ll_parent_notes);

            tvNotesSubject = (TextView) itemView.findViewById(R.id.tv_notes_subject);
            tvExamName = (TextView) itemView.findViewById(R.id.tv_exam_name);
            tvNotesClassName = (TextView) itemView.findViewById(R.id.tv_notes_class_name);
            tvUpdatedDate = (TextView) itemView.findViewById(R.id.tv_updated_date);
            tvNotes = (TextView) itemView.findViewById(R.id.tv_notes);
            tvNotesLabel = (TextView) itemView.findViewById(R.id.tv_notes_label);
            tvTopics = (TextView) itemView.findViewById(R.id.tv_topics);
            tvTopicsLabel = (TextView) itemView.findViewById(R.id.tv_topics_label);

            tvNotesSubject.setTypeface(Global.myTypeFace.getRalewayBold());
            tvExamName.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvNotesClassName.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvUpdatedDate.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvNotes.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvNotesLabel.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvTopics.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvTopicsLabel.setTypeface(Global.myTypeFace.getRalewayRegular());

        }
    }

    @Override
    public AllNotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View assignments_view = inflater.inflate(R.layout.row_notes, parent, false);
        ViewHolder viewHolder = new ViewHolder(assignments_view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AllNotesAdapter.ViewHolder holder, int position) {
//        Global.imageLoader.displayImage(WebConstants.USER_IMAGES + "Users_Images/" + arrListExamSubmittor.get(position).getStudentProfilePic(), holder.imgUserPic, ISMTeacher.options);
//
//        holder.tvUsername.setText();
//        holder.tvSchoolname.setText();
        if (position % 2 == 0) {
            holder.rlNotes.setBackgroundResource(R.drawable.bg_subject_red);
        } else {
            holder.rlNotes.setBackgroundResource(R.drawable.bg_subject_yellow);
        }


        holder.llParentNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeacherOfficeFragment teacherOfficeFragment = (TeacherOfficeFragment) fragmentManager.findFragmentByTag(AppConstant.FRAGMENT_TAG_TEACHER_OFFICE);
                teacherOfficeFragment.loadFragmentInTeacherOffice(TeacherOfficeFragment.FRAGMENT_NOTES_ADD_EDIT);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 1;
    }


}
