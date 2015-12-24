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
import com.ism.teacher.ws.model.Group;

import java.util.ArrayList;

/**
 * Created by c75 on 21/12/15.
 */
public class PastTutorialAdapter extends RecyclerView.Adapter<PastTutorialAdapter.ViewHolder> {

    private static final String TAG = PastTutorialAdapter.class.getSimpleName();
    Context mContext;

    ArrayList<Group> arrayListGroups = new ArrayList<>();

    public PastTutorialAdapter(Context context) {
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgGroup;
        TextView tvGroupName, tvGroupClassName, tvExamName, tvScheduledDate, tvScheduledDateLabel, tvScore, tvScoreLabel;

        public ViewHolder(View itemView) {
            super(itemView);
            imgGroup = (CircleImageView) itemView.findViewById(R.id.img_group);
            tvGroupName = (TextView) itemView.findViewById(R.id.tv_group_name);
            tvGroupClassName = (TextView) itemView.findViewById(R.id.tv_group_class_name);
            tvExamName = (TextView) itemView.findViewById(R.id.tv_exam_name);
            tvScheduledDate = (TextView) itemView.findViewById(R.id.tv_scheduled_date);
            tvScheduledDateLabel = (TextView) itemView.findViewById(R.id.tv_scheduled_date_label);
            tvScore = (TextView) itemView.findViewById(R.id.tv_score);
            tvScoreLabel = (TextView) itemView.findViewById(R.id.tv_score_label);


            tvGroupName.setTypeface(Global.myTypeFace.getRalewayBold());
            tvGroupClassName.setTypeface(Global.myTypeFace.getRalewayRegular());
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
//        Global.imageLoader.displayImage(WebConstants.USER_IMAGES + "Users_Images/" + arrayListGroups.get(position).getStudentProfilePic(), holder.imgGroup, ISMTeacher.options);

        holder.tvGroupName.setText(arrayListGroups.get(position).getGroupName());
        holder.tvGroupClassName.setText("(" + arrayListGroups.get(position).getGroupClass() + ")");
        holder.tvExamName.setText(mContext.getResources().getString(R.string.strexamname) + ": ");
        holder.tvExamName.append(Utility.getSpannableString(arrayListGroups.get(position).getExamName(), mContext.getResources().getColor(R.color.color_green_exam_name)));
        holder.tvScore.setText(arrayListGroups.get(position).getGroupScore());

        //  holder.tvScheduledDate.setText();
    }

    @Override
    public int getItemCount() {
        return arrayListGroups.size();
    }

    public void addAll(ArrayList<Group> groups) {

        try {
            this.arrayListGroups.clear();
            this.arrayListGroups.addAll(groups);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

}
