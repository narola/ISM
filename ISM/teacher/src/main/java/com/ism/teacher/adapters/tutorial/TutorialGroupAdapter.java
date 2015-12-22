package com.ism.teacher.adapters.tutorial;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.teacher.ISMTeacher;
import com.ism.teacher.R;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.object.Global;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.model.GroupMembers;

import java.util.ArrayList;

/**
 * Created by c75 on 21/12/15.
 */
public class TutorialGroupAdapter extends RecyclerView.Adapter<TutorialGroupAdapter.ViewHolder> {

    private static final String TAG = TutorialGroupAdapter.class.getSimpleName();
    Context mContext;

    ArrayList<GroupMembers> arrListGroupMembers=new ArrayList<>();

    public TutorialGroupAdapter(Context context) {
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgMemberPic;
        TextView tvMembername, tvMemberSchool;

        public ViewHolder(View itemView) {
            super(itemView);
            imgMemberPic = (CircleImageView) itemView.findViewById(R.id.img_member_pic);
            tvMembername = (TextView) itemView.findViewById(R.id.tv_membername);
            tvMemberSchool = (TextView) itemView.findViewById(R.id.tv_member_school);

            tvMembername.setTypeface(Global.myTypeFace.getRalewayBold());
            tvMemberSchool.setTypeface(Global.myTypeFace.getRalewayRegular());
        }
    }

    @Override
    public TutorialGroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View assignments_view = inflater.inflate(R.layout.row_tutorial_group, parent, false);
        ViewHolder viewHolder = new ViewHolder(assignments_view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TutorialGroupAdapter.ViewHolder holder, int position) {
        Global.imageLoader.displayImage(WebConstants.USER_IMAGES + arrListGroupMembers.get(position).getMemberProfilePic(), holder.imgMemberPic, ISMTeacher.options);

        holder.tvMembername.setText(arrListGroupMembers.get(position).getMemberName());
        holder.tvMemberSchool.setText(arrListGroupMembers.get(position).getMemberSchool());
    }

    @Override
    public int getItemCount() {
        return arrListGroupMembers.size();
    }

    public void addAll(ArrayList<GroupMembers> groupMembers) {

        try {
            this.arrListGroupMembers.clear();
            this.arrListGroupMembers.addAll(groupMembers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

}
