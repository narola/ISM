package com.ism.teacher.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.views.CircleImageView;

/**
 * Created by c75 on 21/12/15.
 */
public class TutorialGroupAdapter extends RecyclerView.Adapter<TutorialGroupAdapter.ViewHolder> {

    private static final String TAG = TutorialGroupAdapter.class.getSimpleName();
    Context mContext;


    public TutorialGroupAdapter(Context context) {
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgUserPic;
        TextView tvUsername, tvSchoolname;

        public ViewHolder(View itemView) {
            super(itemView);
            imgUserPic = (CircleImageView) itemView.findViewById(R.id.img_user_pic);
            tvUsername = (TextView) itemView.findViewById(R.id.tv_username);
            tvSchoolname = (TextView) itemView.findViewById(R.id.tv_schoolname);
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
//        Global.imageLoader.displayImage(WebConstants.USER_IMAGES + "Users_Images/" + arrListExamSubmittor.get(position).getStudentProfilePic(), holder.imgUserPic, ISMTeacher.options);
//
//        holder.tvUsername.setText();
//        holder.tvSchoolname.setText();

    }

    @Override
    public int getItemCount() {
        return 5;
    }


}
