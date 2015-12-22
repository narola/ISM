package com.ism.teacher.adapters.tutorial;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.object.Global;
import com.ism.teacher.views.CircleImageView;

/**
 * Created by c75 on 21/12/15.
 */
public class PastTutorialAdapter extends RecyclerView.Adapter<PastTutorialAdapter.ViewHolder> {

    private static final String TAG = PastTutorialAdapter.class.getSimpleName();
    Context mContext;


    public PastTutorialAdapter(Context context) {
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgGroup;
        TextView tvGroupName, tvCourseName, tvExamName, tvScheduledDate, tvScheduledDateLabel, tvScore, tvScoreLabel;

        public ViewHolder(View itemView) {
            super(itemView);
            imgGroup = (CircleImageView) itemView.findViewById(R.id.img_group);
            tvGroupName = (TextView) itemView.findViewById(R.id.tv_group_name);
            tvCourseName = (TextView) itemView.findViewById(R.id.tv_group_class_name);
            tvExamName = (TextView) itemView.findViewById(R.id.tv_exam_name);
            tvScheduledDate = (TextView) itemView.findViewById(R.id.tv_scheduled_date);
            tvScheduledDateLabel = (TextView) itemView.findViewById(R.id.tv_scheduled_date_label);
            tvScore = (TextView) itemView.findViewById(R.id.tv_score);
            tvScoreLabel = (TextView) itemView.findViewById(R.id.tv_score_label);


            tvGroupName.setTypeface(Global.myTypeFace.getRalewayBold());
            tvCourseName.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvExamName.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvScheduledDate.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvScheduledDateLabel.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvScore.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvScoreLabel.setTypeface(Global.myTypeFace.getRalewayRegular());


        }
    }

    @Override
    public PastTutorialAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View assignments_view = inflater.inflate(R.layout.row_past_tutorials, parent, false);
        ViewHolder viewHolder = new ViewHolder(assignments_view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PastTutorialAdapter.ViewHolder holder, int position) {
//        Global.imageLoader.displayImage(WebConstants.USER_IMAGES + "Users_Images/" + arrListExamSubmittor.get(position).getStudentProfilePic(), holder.imgGroup, ISMTeacher.options);

//        holder.tvGroupName.setText();
//        holder.tvCourseName.setText();
        holder.tvExamName.setText(mContext.getResources().getString(R.string.strexamname) + ": ");
        holder.tvExamName.append(Utility.getSpannableString("ISM Mock", mContext.getResources().getColor(R.color.color_green_exam_name)));
    }

    @Override
    public int getItemCount() {
        return 5;
    }


}
